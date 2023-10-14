import React from "react";
import { Modal, Row, Col, Input, Button, Select } from "antd";
import "./styleCreateClass.css";
import { useEffect, useState } from "react";
import moment from "moment";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import LoadingIndicator from "../../../../helper/loading";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import { CreateClass } from "../../../../app/admin/ClassManager.reducer";
import { GetAdTeacher } from "../../../../app/admin/AdTeacherSlice.reducer";
import { GetAdMeetingPeriod } from "../../../../app/admin/AdMeetingPeriodSlice.reducer";
import { convertHourAndMinuteToString } from "../../../../helper/util.helper";

const { Option } = Select;

const ModalCreateProject = ({ visible, onCancel }) => {
  const [idSemesterSeach, setIdSemesterSearch] = useState("");
  const [semesterDataAll, setSemesterDataAll] = useState([]); // Dữ liệu semester
  const [loading, setLoading] = useState(true);
  const dispatch = useAppDispatch();
  const [idActivitiSearch, setIdActivitiSearch] = useState("");
  const [activityDataAll, setActivityDataAll] = useState([]); // Dữ liệu activity
  const [selectedItemsPerson, setSelectedItemsPerson] = useState("");
  const [name, setName] = useState("");
  const [classPeriod, setClassPeriod] = useState("");
  const [startTime, setStartTime] = useState("");
  const [classSize, setClassSize] = useState(0);

  const [errorName, setErrorName] = useState("");
  const [errorActivity, setErrorActivity] = useState("");
  const [errorClassPeriod, setErrorClassPeriod] = useState("");
  const [errorstartTime, setErrorStartTime] = useState("");
  const [errorActivitySelect, setErrorActivitySelect] = useState("");
  const [statusTeacherEdit, setStatusTeacherEdit] = useState("1");

  const cancelSuccess = () => {
    onCancel();
    setName("");
    setClassPeriod("");
    setStartTime("");
    setSelectedItemsPerson("");
    setIdActivitiSearch("");
    setErrorName("");
    setErrorActivity("");
    setErrorClassPeriod("");
    setErrorStartTime("");
    setErrorActivitySelect("");
  };

  const listClassPeriod = [];

  for (let i = 0; i <= 6; i++) {
    listClassPeriod.push("" + i);
  }

  useEffect(() => {
    if (visible) {
    }

    return () => {
      setName("");
      setClassPeriod("");
      setStartTime("");
      setSelectedItemsPerson("");
      setIdActivitiSearch("");
      setErrorName("");
      setErrorActivity("");
      setErrorClassPeriod("");
      setErrorStartTime("");
      setErrorActivitySelect("");
    };
  }, [visible]);

  useEffect(() => {
    const featchDataSemester = async () => {
      try {
        setLoading(false);
        const responseClassAll = await ClassAPI.fetchAllSemester();
        const listSemester = responseClassAll.data;
        if (listSemester.data.length > 0) {
          listSemester.data.forEach((item) => {
            if (
              item.startTime <= new Date().getTime() &&
              new Date().getTime() <= item.endTime
            ) {
              setIdSemesterSearch(item.id);
            }
          });
        } else {
          setIdSemesterSearch(null);
        }
        setSemesterDataAll(listSemester.data);
        setLoading(true);
      } catch (error) {}
    };
    featchDataSemester();
  }, []);

  useEffect(() => {
    if (idSemesterSeach === "") {
      setIdActivitiSearch("none");
      setActivityDataAll([]);
    } else {
      const featchDataActivity = async (idSemesterSeach) => {
        console.log(idSemesterSeach);
        await ClassAPI.getAllActivityByIdSemester(idSemesterSeach).then(
          (respone) => {
            console.log(respone.data.data);
            if (respone.data.data.length === 0) {
              setIdActivitiSearch("none");
              setActivityDataAll([]);
            } else {
              setIdActivitiSearch("");
              setActivityDataAll(respone.data.data);
            }
          }
        );
      };
      featchDataActivity(idSemesterSeach);
    }
  }, [idSemesterSeach]);

  const handleSelectPersonChange = (value) => {
    setSelectedItemsPerson(value);
  };

  const create = () => {
    let check = 0;

    if (classPeriod.trim() === "") {
      setErrorClassPeriod("Ca lớp không để trống");
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
        statusTeacherEdit: parseInt(statusTeacherEdit),
      };

      ClassAPI.create(obj).then(
        (response) => {
          toast.success("Thêm thành công!");
          dispatch(CreateClass(response.data.data));
          cancelSuccess();
        },
        (error) => {}
      );
    }
  };

  const handleSelectChange = (value) => {
    setClassPeriod(value);
  };

  const teacherDataAll = useAppSelector(GetAdTeacher);

  const filterTeacherOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };

  const dataMeetingPeriod = useAppSelector(GetAdMeetingPeriod);

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
            <span style={{ fontSize: "18px" }}>Thêm Lớp Học</span>
          </div>
          <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
            <Row style={{ marginBottom: "15px" }}>
              <Col span={12} style={{ paddingRight: "10px" }}>
                <span style={{ color: "red" }}>(*) </span>Semester: <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  value={idSemesterSeach}
                  onChange={(value) => {
                    setIdSemesterSearch(value);
                  }}
                >
                  <Option value="">Chọn 1 học kì</Option>

                  {semesterDataAll.map((semester) => (
                    <Option key={semester.id} value={semester.id}>
                      {semester.name}
                    </Option>
                  ))}
                </Select>
              </Col>
              <Col span={12} style={{ paddingRight: "10px" }}>
                <span style={{ color: "red" }}>(*) </span> Hoạt Động: <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  value={idActivitiSearch}
                  onChange={(value) => {
                    setIdActivitiSearch(value);
                  }}
                >
                  {activityDataAll.length > 0 && (
                    <Option value="">Tất cả</Option>
                  )}
                  {activityDataAll.length === 0 && (
                    <Option value="none">Không có hoạt động</Option>
                  )}
                  {activityDataAll.map((activity) => (
                    <Option key={activity.id} value={activity.id}>
                      {activity.name}
                    </Option>
                  ))}
                </Select>
                <span className="error">{errorActivitySelect}</span>
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
                      {teacher.userName + " - " + teacher.name}
                    </Option>
                  ))}
                </Select>
              </Col>
              <Col span={12} style={{ paddingRight: "10px" }}>
                <span style={{ color: "red" }}>(*) </span>
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
              {" "}
              <Col span={12} style={{ paddingRight: "10px" }}>
                <span style={{ color: "red" }}>(*) </span> Ca học dự kiến:{" "}
                <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  value={classPeriod}
                  onChange={handleSelectChange}
                >
                  <Option value="">Chọn ca học</Option>
                  {dataMeetingPeriod.map((item) => {
                    return (
                      <Option value={item.id} key={item.id}>
                        {item.name} -{" "}
                        {convertHourAndMinuteToString(
                          item.startHour,
                          item.startMinute,
                          item.endHour,
                          item.endMinute
                        )}
                      </Option>
                    );
                  })}
                </Select>
                <span className="error">{errorClassPeriod}</span>
              </Col>
              <Col span={12} style={{ paddingRight: "10px" }}>
                <span style={{ color: "red" }}>(*) </span> Quyền giảng viên
                chỉnh sửa: <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  value={statusTeacherEdit}
                  onChange={(e) => {
                    setStatusTeacherEdit(e);
                  }}
                >
                  <Option value="1">Không cho phép</Option>
                  <Option value="0">Cho phép</Option>
                </Select>
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
