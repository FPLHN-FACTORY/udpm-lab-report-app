import React from "react";
import { Modal, Row, Col, Input, Button, Select } from "antd";

import "./styleCreateClass.css";
import { useEffect, useState } from "react";
import moment from "moment";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../app/hook";
import LoadingIndicator from "../../../../helper/loading";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import { SetTeacherSemester } from "../../../../app/admin/ClassManager.reducer";
import { CreateClass } from "../../../../app/admin/ClassManager.reducer";

const { Option } = Select;

const ModalCreateProject = ({ visible, onCancel }) => {
  const [idSemesterSeach, setIdSemesterSearch] = useState("");
  const [semesterDataAll, setSemesterDataAll] = useState([]); // Dữ liệu semester
  const [loading, setLoading] = useState(true);
  const dispatch = useAppDispatch();
  const [idActivitiSearch, setIdActivitiSearch] = useState("");
  const [activityDataAll, setActivityDataAll] = useState([]); // Dữ liệu activity
  const [selectedItemsPerson, setSelectedItemsPerson] = useState("");
  const [teacherDataAll, setTeacherDataAll] = useState([]); // Dữ liệu teacherId và username
  const [name, setName] = useState("");
  const [code, setCode] = useState("");
  const [classPeriod, setClassPeriod] = useState("");
  const [startTime, setStartTime] = useState("");
  const [classSize, setClassSize] = useState(0);

  const [errorName, setErrorName] = useState("");
  const [errorCode, setErrorCode] = useState("");
  const [errorActivity, setErrorActivity] = useState("");
  const [errorClassPeriod, setErrorClassPeriod] = useState("");
  const [errorstartTime, setErrorStartTime] = useState("");
  const [errorActivitySelect, setErrorActivitySelect] = useState("");

  const cancelSuccess = () => {
    onCancel();
    setName("");
    setCode("");
    setClassPeriod("");
    setStartTime("");
    setSelectedItemsPerson("");
    setIdActivitiSearch("");
  };
  const listClassPeriod = [];

  for (let i = 1; i <= 10; i++) {
    listClassPeriod.push("" + i);
  }

  useEffect(() => {
    const fetchTeacherData = async () => {
      const responseTeacherData = await ClassAPI.fetchAllTeacher();
      const teacherData = responseTeacherData.data;
      setTeacherDataAll(teacherData);
    };
    fetchTeacherData();
  }, []);

  useEffect(() => {
    const featchDataSemester = async () => {
      try {
        setLoading(false);
        const responseClassAll = await ClassAPI.fetchAllSemester();
        const listClassAll = responseClassAll.data;
        dispatch(SetTeacherSemester(listClassAll.data));
        if (listClassAll.data.length > 0) {
          setIdSemesterSearch(listClassAll.data[0].id);
        } else {
          setIdSemesterSearch("null");
        }
        setSemesterDataAll(listClassAll.data);
        setLoading(true);
      } catch (error) {
        alert("Vui lòng F5 lại trang !");
      }
    };
    featchDataSemester();
  }, []);

  useEffect(() => {
    const featchDataActivity = async (idSemesterSeach) => {
      console.log(idSemesterSeach);
      await ClassAPI.getAllActivityByIdSemester(idSemesterSeach).then(
        (respone) => {
          setActivityDataAll(respone.data.data);
          setLoading(true);
        }
      );
    };
    featchDataActivity(idSemesterSeach);
  }, [idSemesterSeach]);

  const handleSelectPersonChange = (value) => {
    setSelectedItemsPerson(value);
  };

  const create = () => {
    let check = 0;
    if (name.trim() === "") {
      setErrorName("Tên Lớp không được để trống");
      check++;
    } else {
      setErrorName("");
    }

    if (code.trim() === "") {
      setErrorCode("Mã Lớp không để trống");
      check++;
    } else {
      setErrorCode("");
    }

    if (classPeriod.trim() === "") {
      setErrorClassPeriod("Ca Lớp không để trống");
      check++;
    } else {
      setErrorClassPeriod("");
    }
    if (startTime === "") {
      setErrorStartTime("Thời gian bắt đầu không được để trống");
      check++;
    } else {
      setErrorStartTime("");
    }
    if (idActivitiSearch === "") {
      setErrorActivitySelect("Hoạt động bắt đầu không được để trống");
      check++;
    } else {
      setErrorActivitySelect("");
    }

    if (check === 0) {
      let obj = {
        name: name,
        code: code,
        classSize: classSize,
        classPeriod: classPeriod,
        startTime: moment(startTime, "YYYY-MM-DD").valueOf(),
        teacherId: selectedItemsPerson,
        activityId: idActivitiSearch,
      };

      ClassAPI.create(obj).then(
        (response) => {
          toast.success("Thêm thành công!");
          dispatch(CreateClass(response.data.data));
          cancelSuccess();
        },
        (error) => {
          toast.error(error.response.data.message);
        }
      );
    }
  };

  const handleSelectChange = (value) => {
    setClassPeriod(value);
  };

  return (
    <>
      <Modal
        visible={visible}
        onCancel={onCancel}
        width={650}
        footer={null}
        className="modal_show_detail"
      >
        <div>
          <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
            <span style={{ fontSize: "18px" }}>Thêm Lớp Học</span>
          </div>

          <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
            <Row style={{ marginBottom: "15px" }}>
              <Col span={16}>
                Semester:{" "}
                <Select
                  showSearch
                  value={idSemesterSeach}
                  onChange={(value) => {
                    setIdSemesterSearch(value);
                  }}
                >
                  <Option value="NULL">Chọn 1 học kì</Option>

                  {semesterDataAll.map((semester) => (
                    <Option key={semester.id} value={semester.id}>
                      {semester.name}
                    </Option>
                  ))}
                </Select>
              </Col>
              <Col span={16}>
                Hoạt Động:{" "}
                <Select
                  showSearch
                  value={idActivitiSearch}
                  onChange={(value) => {
                    setIdActivitiSearch(value);
                  }}
                >
                  <Option value="">Chọn 1 hoạt động</Option>
                  {activityDataAll.map((activity) => (
                    <Option key={activity.id} value={activity.id}>
                      {activity.name}
                    </Option>
                  ))}
                </Select>
                <span className="error">{errorActivitySelect}</span>
              </Col>
            </Row>
            <Col span={16} style={{ marginTop: "15px" }}>
              GVHD:{" "}
              <Select
                showSearch
                placeholder="Select a person "
                value={selectedItemsPerson}
                onChange={handleSelectPersonChange}
                style={{
                  height: "50%",
                  marginRight: "210px",
                }}
              >
                <Option value="NULL">Chọn 1 GVHD</Option>

                {teacherDataAll.map((teacher) => (
                  <Option key={teacher.id} value={teacher.id}>
                    {teacher.username}
                  </Option>
                ))}
              </Select>
            </Col>
            <Col span={16} style={{ marginTop: "15px", marginLeft: "227px" }}>
              {" "}
              <span className="notBlank">*</span>
              <span>Thời gian bắt đầu:</span> <br />
              <Input
                value={startTime}
                style={{
                  height: "60%",
                  marginRight: "210px",
                }}
                onChange={(e) => {
                  setStartTime(e.target.value);
                }}
                type="date"
              />
              <span className="error">{errorstartTime}</span>
            </Col>

            <Row style={{ marginBottom: "15px" }}>
              <Col span={16} style={{ marginRight: "150px" }}>
                <span>Mã Lớp</span> <br />
                <Input
                  value={code}
                  onChange={(e) => {
                    setCode(e.target.value);
                  }}
                  type="text"
                />
                <span className="error">{errorCode}</span>
              </Col>
              <Col span={16}>
                Ca Học:{" "}
                <Select
                  showSearch
                  placeholder="Select a class Period"
                  value={classPeriod}
                  onChange={handleSelectChange}
                  style={{ height: "50%" }}
                >
                  <Option value="">Tất Cả</Option>
                  {listClassPeriod.map((value) => {
                    return (
                      <Option value={value} key={value}>
                        {value}
                      </Option>
                    );
                  })}
                </Select>
                <span className="error">{errorClassPeriod}</span>
              </Col>
            </Row>
            <Row gutter={16} style={{ marginBottom: "15px" }}>
              <Col span={10}>
                <span>Tên Lớp</span> <br />
                <Input
                  value={name}
                  onChange={(e) => {
                    setName(e.target.value);
                  }}
                  type="text"
                />
                <span className="error">{errorName}</span>
              </Col>
            </Row>
          </div>

          <div style={{ textAlign: "right" }}>
            <div style={{ paddingTop: "15px" }}>
              <Button
                style={{
                  marginRight: "5px",
                  backgroundColor: "rgb(61, 139, 227)",
                  color: "white",
                }}
                onClick={create}
              >
                Thêm
              </Button>
              <Button
                style={{
                  backgroundColor: "red",
                  color: "white",
                }}
                onClick={onCancel}
              >
                Hủy
              </Button>
            </div>
          </div>
        </div>
      </Modal>
    </>
  );
};
export default ModalCreateProject;
