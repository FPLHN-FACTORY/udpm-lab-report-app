import React from "react";
import { Modal, Row, Col, Input, Button, Select } from "antd";
import "./styleUpdateClass.css";
import { useEffect, useState } from "react";
import moment from "moment";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import LoadingIndicator from "../../../../helper/loading";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import { UpdateClass } from "../../../../app/admin/ClassManager.reducer";
import { CreateClass } from "../../../../app/admin/ClassManager.reducer";
import { GetAdTeacher } from "../../../../app/admin/AdTeacherSlice.reducer";
import { parseInt } from "lodash";

const { Option } = Select;

const ModalUpdateClass = ({ visible, onCancel, id }) => {
  const [loading, setLoading] = useState(true);
  const dispatch = useAppDispatch();
  const [idSemesterSeach, setIdSemesterSearch] = useState("");
  const [semesterDataAll, setSemesterDataAll] = useState([]); // Dữ liệu semester
  const [idActivitiSearch, setIdActivitiSearch] = useState("");
  const [activityDataAll, setActivityDataAll] = useState([]); // Dữ liệu activity
  const [selectedItemsPerson, setSelectedItemsPerson] = useState("");
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
  const [classDetail, setClassDetail] = useState({});

  const cancelSuccess = () => {
    onCancel();
    setName("");
    setCode("");
    setClassPeriod("");
    setStartTime("");
    setSelectedItemsPerson("");
    setIdActivitiSearch("");
    setErrorName("");
    setErrorCode("");
    setErrorActivity("");
    setErrorClassPeriod("");
    setErrorStartTime("");
    setErrorActivitySelect("");
  };

  const listClassPeriod = [];

  for (let i = 0; i <= 9; i++) {
    listClassPeriod.push("" + i);
  }

  useEffect(() => {
    if (visible) {
    }

    return () => {
      setName("");
      setCode("");
      setClassPeriod("");
      setStartTime("");
      setSelectedItemsPerson("");
      setIdActivitiSearch("");
      setErrorName("");
      setErrorCode("");
      setErrorActivity("");
      setErrorClassPeriod("");
      setErrorStartTime("");
      setErrorActivitySelect("");
    };
  }, [visible]);

  useEffect(() => {
    const fetchDetail = (id) => {
      ClassAPI.getAdClassDetailById(id).then(
        (respone) => {
          setClassDetail(respone.data.data);
          setIdSemesterSearch(respone.data.data.semesterId);
          setIdActivitiSearch(respone.data.data.activityId);
          setSelectedItemsPerson(
            respone.data.data.teacherId == null
              ? ""
              : respone.data.data.teacherId
          );
          setStartTime(
            moment(respone.data.data.startTime).format("YYYY-MM-DD")
          );
          setCode(respone.data.data.code);
          setClassPeriod(
            respone.data.data.classPeriod == null
              ? ""
              : respone.data.data.classPeriod + ""
          );
        },
        (error) => {}
      );
    };
    fetchDetail(id);
  }, [id, visible]);

  useEffect(() => {
    const featchDataSemester = async () => {
      try {
        setLoading(false);
        const responseClassAll = await ClassAPI.fetchAllSemester();
        const listClassAll = responseClassAll.data;
        setSemesterDataAll(listClassAll.data);
        setLoading(true);
      } catch (error) {
        alert("Vui lòng F5 lại trang !");
      }
    };
    featchDataSemester();
  }, []);

  useEffect(
    (id) => {
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
    },
    [idSemesterSeach]
  );

  const handleSelectPersonChange = (value) => {
    setSelectedItemsPerson(value);
  };

  const update = async (id) => {
    let check = 0;

    if (classPeriod === "") {
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
        classPeriod: classPeriod,
        startTime: moment(startTime, "YYYY-MM-DD").valueOf(),
        teacherId: selectedItemsPerson,
        activityId: idActivitiSearch,
      };

      await ClassAPI.update(id, obj).then((response) => {
        toast.success("Cập nhật thành công!");
        console.log("data: ", response.data.data);
        dispatch(UpdateClass(response.data.data));
        cancelSuccess();
      });
    }
  };

  const handleSelectChange = (value) => {
    setClassPeriod(value);
  };

  const teacherDataAll = useAppSelector(GetAdTeacher);

  const filterTeacherOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };

  return (
    <>
      <Modal
        visible={visible}
        onCancel={onCancel}
        width={800}
        footer={null}
        className="modal_show_detail_project"
      >
        <div>
          <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
            <span style={{ fontSize: "18px" }}>Cập nhật Lớp Học</span>
          </div>
          <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
            <Row style={{ marginBottom: "15px" }}>
              <Col span={12} style={{ paddingRight: "10px" }}>
                Semester: <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  value={idSemesterSeach}
                  onChange={(value) => {
                    setIdSemesterSearch(value);
                  }}
                  disabled
                >
                  {semesterDataAll.map((semester) => (
                    <Option key={semester.id} value={semester.id}>
                      {semester.name}
                    </Option>
                  ))}
                </Select>
              </Col>
              <Col span={12} style={{ paddingRight: "10px" }}>
                <span>Mã lớp:</span> <br />
                <Input
                  value={code}
                  style={{ width: "100%" }}
                  disabled={true}
                  type="text"
                />
                <span className="error">{errorCode}</span>
              </Col>
            </Row>
            <Row style={{ marginBottom: "15px" }}>
              <Col span={12} style={{ paddingRight: "10px" }}>
                Giảng viên: <br />
                <Select
                  showSearch
                  value={selectedItemsPerson}
                  onChange={handleSelectPersonChange}
                  style={{ width: "100%" }}
                  filterOption={filterTeacherOptions}
                >
                  <Option value="">Chọn 1 giảng viên</Option>

                  {teacherDataAll.map((teacher) => (
                    <Option key={teacher.id} value={teacher.id}>
                      {teacher.userName}
                    </Option>
                  ))}
                </Select>
              </Col>
              <Col span={12} style={{ paddingRight: "10px" }}>
                <span className="notBlank">(*) </span>
                <span>Thời gian bắt đầu:</span> <br />
                <Input
                  value={startTime}
                  onChange={(e) => {
                    setStartTime(e.target.value);
                  }}
                  style={{ width: "100%" }}
                  type="date"
                />
                <span className="error">{errorstartTime}</span>
              </Col>
            </Row>
            <Row style={{ marginBottom: "15px" }}>
              <Col span={12} style={{ paddingRight: "10px" }}>
                Hoạt Động: <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
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
              <Col span={12} style={{ paddingRight: "10px" }}>
                Ca học dự kiến: <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  value={classPeriod}
                  onChange={handleSelectChange}
                >
                  <Option value="">Chọn ca học dự kiến</Option>
                  {listClassPeriod.map((value) => {
                    return (
                      <Option value={value} key={value}>
                        {parseInt(value) + parseInt(1)}
                      </Option>
                    );
                  })}
                </Select>
                <span className="error">{errorClassPeriod}</span>
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
                onClick={() => update(id)}
              >
                Cập nhật
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

export default ModalUpdateClass;
