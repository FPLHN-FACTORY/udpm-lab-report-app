import React from "react";
import { Modal, Row, Col, Input, Button, Select, message } from "antd";
import "./styleUpdateClass.css";
import { useEffect, useState } from "react";
import moment from "moment";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import { UpdateClass } from "../../../../app/admin/ClassManager.reducer";
import { GetAdTeacher } from "../../../../app/admin/AdTeacherSlice.reducer";
import { parseInt } from "lodash";
import LoadingIndicatorNoOverlay from "../../../../helper/loadingNoOverlay";
import { GetAdMeetingPeriod } from "../../../../app/admin/AdMeetingPeriodSlice.reducer";
import {
  convertDateLongToString,
  convertHourAndMinuteToString,
} from "../../../../helper/util.helper";
import { faHeartPulse } from "@fortawesome/free-solid-svg-icons";

const { Option } = Select;

const ModalUpdateClass = ({ visible, onCancel, id }) => {
  const [loading, setLoading] = useState(false);
  const [loadingOverlay, setLoadingOverlay] = useState(false);
  const dispatch = useAppDispatch();
  const [idSemesterSeach, setIdSemesterSearch] = useState("");
  const [semesterDataAll, setSemesterDataAll] = useState([]);
  const [idActivitiSearch, setIdActivitiSearch] = useState("");
  const [activityDataAll, setActivityDataAll] = useState([]);
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
  const [statusTeacherEdit, setStatusTeacherEdit] = useState("");

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
    setLoading(false);
    setLoadingOverlay(false);
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
    if (visible) {
      setLoading(true);
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
              respone.data.data.classPeriodId == null
                ? ""
                : respone.data.data.classPeriodId + ""
            );
            setStatusTeacherEdit(respone.data.data.statusTeacherEdit + "");
            setLoading(false);
          },
          (error) => {}
        );
      };
      fetchDetail(id);
    }
  }, [id, visible]);

  useEffect(() => {
    const featchDataSemester = async () => {
      try {
        const responseClassAll = await ClassAPI.fetchAllSemester();
        const listClassAll = responseClassAll.data;
        setSemesterDataAll(listClassAll.data);
      } catch (error) {}
    };
    featchDataSemester();
  }, []);

  useEffect(
    (id) => {
      const featchDataActivity = async (idSemesterSeach) => {
        await ClassAPI.getAllActivityByIdSemester(idSemesterSeach).then(
          (respone) => {
            setActivityDataAll(respone.data.data);
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
    if (idActivitiSearch === "" || idActivitiSearch === "none") {
      setErrorActivitySelect("Hoạt động không được để trống");
      check++;
    } else {
      setErrorActivitySelect("");
    }

    if (check === 0) {
      setLoadingOverlay(true);
      let obj = {
        classPeriod: classPeriod,
        startTime: moment(startTime, "YYYY-MM-DD").valueOf(),
        teacherId: selectedItemsPerson,
        activityId: idActivitiSearch,
        statusTeacherEdit: parseInt(statusTeacherEdit),
      };

      await ClassAPI.update(id, obj).then((response) => {
        message.success("Cập nhật thành công !");
        setLoadingOverlay(faHeartPulse);
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

  const dataMeetingPeriod = useAppSelector(GetAdMeetingPeriod);

  return (
    <>
      {loading && <LoadingIndicatorNoOverlay />}
      {loadingOverlay && <LoadingIndicatorNoOverlay />}
      <Modal
        open={visible}
        onCancel={onCancel}
        width={800}
        footer={null}
        className="modal_show_detail_project"
      >
        <div>
          <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
            <span style={{ fontSize: "18px" }}>Cập nhật lớp học</span>
          </div>
          <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
            <Row style={{ marginBottom: "15px" }}>
              <Col span={12} style={{ paddingRight: "10px" }}>
                <span style={{ color: "red" }}>(*) </span> Học kỳ: <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  value={idSemesterSeach}
                  onChange={(value) => {
                    setIdSemesterSearch(value);
                  }}
                  disabled
                >
                  {semesterDataAll != null && semesterDataAll.map((semester) => (
                    <Option key={semester.id} value={semester.id}>
                      {semester.name +
                        " (" +
                        convertDateLongToString(semester.startTime) +
                        " - " +
                        convertDateLongToString(semester.endTime) +
                        ")"}
                    </Option>
                  ))}
                </Select>
              </Col>
              <Col span={12} style={{ paddingRight: "10px" }}>
                <span style={{ color: "red" }}>(*) </span> <span>Mã lớp:</span>{" "}
                <br />
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

                  {teacherDataAll != null && teacherDataAll.map((teacher) => (
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
                  <Option value="">Chọn 1 hoạt động</Option>
                  {activityDataAll != null && activityDataAll.map((activity) => (
                    <Option key={activity.id} value={activity.id}>
                      {activity.name}
                    </Option>
                  ))}
                </Select>
                <span className="error">{errorActivitySelect}</span>
              </Col>
              <Col span={12} style={{ paddingRight: "10px" }}>
                <span style={{ color: "red" }}>(*) </span> Ca học dự kiến:{" "}
                <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  value={classPeriod}
                  onChange={handleSelectChange}
                >
                  <Option value="">Chọn ca học dự kiến</Option>
                  {dataMeetingPeriod != null && dataMeetingPeriod.map((item) => {
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
              <Col
                span={12}
                style={{ paddingRight: "10px", marginTop: "15px" }}
              >
                <span style={{ color: "red" }}>(*) </span>Quyền giảng viên chỉnh
                sửa: <br />
                <Select
                  style={{ width: "100%" }}
                  value={statusTeacherEdit}
                  onChange={(e) => {
                    setStatusTeacherEdit(e);
                  }}
                >
                  <Option value="0">Cho phép</Option>
                  <Option value="1">Không cho phép</Option>
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
