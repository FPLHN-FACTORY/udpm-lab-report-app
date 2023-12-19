import React from "react";
import { Modal, Row, Col, Input, Button, Select, message } from "antd";
import "./styleUpdateFiledClass.css";
import { useEffect, useState } from "react";
import moment from "moment";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import LoadingIndicatorNoOverlay from "../../../helper/loadingNoOverlay";
import {
  convertDateLongToString,
  convertHourAndMinuteToString,
} from "../../../helper/util.helper";
import { faHeartPulse } from "@fortawesome/free-solid-svg-icons";
import { TeacherMyClassAPI } from "../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherSemesterAPI } from "../../../api/teacher/semester/TeacherSemester.api";
import { TeacherActivityAPI } from "../../../api/teacher/activity/TeacherActivity.api";
import { UpdateFiledClass } from "../../../app/teacher/my-class/teacherMyClassSlice.reduce";
import { GetAdTeacher } from "../../../app/admin/AdTeacherSlice.reducer";
import { GetAdMeetingPeriod } from "../../../app/admin/AdMeetingPeriodSlice.reducer";
import { GetTeacherSemester } from "../../../app/teacher/semester/teacherSemesterSlice.reduce";

const { Option } = Select;

const ModalUpdateFiledClass = ({ visible, onCancel, id }) => {
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

  const [errorClassPeriod, setErrorClassPeriod] = useState("");
  const [classDetail, setClassDetail] = useState({});
  const [statusTeacherEdit, setStatusTeacherEdit] = useState("");
  const [statusClass, setStatusClass] = useState("");

  const listMeetingPeriod = useAppSelector(GetAdMeetingPeriod);
  const listTeacher = useAppSelector(GetAdTeacher);
  const [descriptions, setDescriptions] = useState("");
  const cancelSuccess = () => {
    onCancel();
    setName("");
    setCode("");
    setStatusClass("");
    setClassPeriod("");
    setStartTime("");
    setSelectedItemsPerson("");
    setIdActivitiSearch("");
    setLoading(false);
    setLoadingOverlay(false);
    setErrorClassPeriod("");
  };

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
      setErrorClassPeriod("");
    };
  }, [visible]);

  useEffect(() => {
    if (visible) {
      setLoading(true);
      const fetchDetail = (id) => {
        TeacherMyClassAPI.getClassDetailById(id).then(
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
            setStatusClass(respone.data.data.statusClass + "");
            setDescriptions(respone.data.data.descriptions);
          },
          (error) => {}
        );
      };
      fetchDetail(id);
    }
  }, [id, visible]);

  const listSemester = useAppSelector(GetTeacherSemester);

  useEffect(
    (id) => {
      const featchDataActivity = async (idSemesterSeach) => {
        await TeacherActivityAPI.getAllActivityByIdSemester(
          idSemesterSeach
        ).then((respone) => {
          setActivityDataAll(respone.data.data);
        });
      };
      featchDataActivity(idSemesterSeach);
    },
    [idSemesterSeach]
  );

  const handleSelectPersonChange = (value) => {
    setSelectedItemsPerson(value);
  };

  const update = async (id) => {
    try {
      let check = 0;

      if (classPeriod === "") {
        setErrorClassPeriod("Ca lớp không để trống");
        check++;
      } else {
        setErrorClassPeriod("");
      }
      if (check === 0) {
        setLoadingOverlay(true);
        let obj = {
          idClass: id,
          classPeriod: classPeriod,
          descriptions: descriptions !== null ? descriptions.trim() : "",
        };

        await TeacherMyClassAPI.updateFiledClass(obj).then((response) => {
          message.success("Cập nhật thành công !");
          setLoadingOverlay(faHeartPulse);
          dispatch(UpdateFiledClass(response.data.data));
          cancelSuccess();
        });
      }
    } catch (error) {
      cancelSuccess();
    }
  };

  const handleSelectChange = (value) => {
    setClassPeriod(value);
  };

  const filterTeacherOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };
  const filterOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };

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
            <Row gutter={16} style={{ marginBottom: "15px" }}>
              <Col span={12}>
                <span>Học kỳ: </span> <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  value={idSemesterSeach}
                  disabled={true}
                >
                  {listSemester != null &&
                    listSemester.map((semester) => (
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
              <Col span={12}>
                <span>Mã lớp:</span> <br />
                <Input
                  value={code}
                  style={{ width: "100%" }}
                  disabled={true}
                  type="text"
                />
              </Col>
            </Row>
            <Row gutter={16} style={{ marginBottom: "15px" }}>
              <Col span={12}>
                <span>Giảng viên:</span> <br />
                <Select
                  showSearch
                  value={selectedItemsPerson}
                  onChange={handleSelectPersonChange}
                  style={{ width: "100%" }}
                  filterOption={filterTeacherOptions}
                  disabled={true}
                >
                  <Option value="">Không có giảng viên</Option>
                  {listTeacher != null &&
                    listTeacher.map((teacher) => (
                      <Option key={teacher.id} value={teacher.id}>
                        {teacher.userName + " - " + teacher.name}
                      </Option>
                    ))}
                </Select>
              </Col>
              <Col span={12}>
                <span>Thời gian bắt đầu:</span> <br />
                <Input
                  value={startTime}
                  style={{ width: "100%", height: "32px" }}
                  type="date"
                  disabled={true}
                />
              </Col>
            </Row>
            <Row gutter={16} style={{ marginBottom: "15px" }}>
              <Col span={12}>
                <span>Hoạt động:</span> <br />
                <Select
                  showSearch
                  style={{ width: "100%", height: "32px" }}
                  value={idActivitiSearch}
                  disabled={true}
                >
                  {activityDataAll != null &&
                    activityDataAll.map((activity) => (
                      <Option key={activity.id} value={activity.id}>
                        {activity.name}
                      </Option>
                    ))}
                </Select>
              </Col>
              <Col span={12}>
                <span>Quyền giảng viên chỉnh sửa:</span> <br />
                <Select
                  style={{ width: "100%" }}
                  value={statusTeacherEdit}
                  disabled={true}
                >
                  <Option value="0">Cho phép</Option>
                  <Option value="1">Không cho phép</Option>
                </Select>
              </Col>
            </Row>
            <Row gutter={16} style={{ marginBottom: "15px" }}>
              <Col span={12}>
                <span style={{ color: "red" }}>(*) </span>{" "}
                <span>Ca học dự kiến:</span> <br />
                {listMeetingPeriod != null && (
                  <Select
                    showSearch
                    filterOption={filterOptions}
                    value={classPeriod}
                    onChange={handleSelectChange}
                    style={{ width: "100%" }}
                  >
                    {listMeetingPeriod.map((item) => {
                      return (
                        <Option value={item.id} key={item.id}>
                          {item.name} (
                          {convertHourAndMinuteToString(
                            item.startHour,
                            item.startMinute,
                            item.endHour,
                            item.endMinute
                          )}
                          )
                        </Option>
                      );
                    })}
                  </Select>
                )}
              </Col>
              <Col span={12}>
                <span>Trạng thái lớp học: </span>
                <br />
                <Select
                  style={{ width: "100%" }}
                  value={statusClass}
                  disabled={true}
                >
                  <Option value="0">Mở</Option>
                  <Option value="1">Đóng</Option>
                </Select>
              </Col>
            </Row>
            <Row style={{ marginBottom: "15px" }}>
              <span>Mô tả: </span>
              <br />
              <Col span={24}>
                <Input.TextArea
                  value={descriptions}
                  onChange={(e) => setDescriptions(e.target.value)}
                  placeholder="Nhập mô tả"
                  style={{ width: "100%" }}
                  autoSize={{
                    minRows: 3,
                  }}
                />
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

export default ModalUpdateFiledClass;
