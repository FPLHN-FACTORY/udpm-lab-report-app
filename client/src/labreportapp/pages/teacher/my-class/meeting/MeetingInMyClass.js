import { useNavigate, useParams } from "react-router-dom";
import "./styleMeetingInMyClass.css";
import { Link } from "react-router-dom";
import { BookOutlined, ControlOutlined } from "@ant-design/icons";
import { TeacherMeetingAPI } from "../../../../api/teacher/meeting/TeacherMeeting.api";
import {
  SetMeeting,
  GetMeeting,
} from "../../../../app/teacher/meeting/teacherMeetingSlice.reduce";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import React from "react";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { SetTTrueToggle } from "../../../../app/teacher/TeCollapsedSlice.reducer";
import {
  convertDateLongToString,
  convertHourAndMinuteToString,
  convertStatusByHourMinute,
} from "../../../../helper/util.helper";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCalendarWeek,
  faDownload,
  faPaperPlane,
  faTableList,
} from "@fortawesome/free-solid-svg-icons";
import { Badge, Button, Col, Empty, Row, Tag, message } from "antd";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../app/common/Loading.reducer";

import ModalFileImportMeeting from "./import-excel/ModalFileImportMeeting";
import { TeacherMeetingPeriodAPI } from "../../../../api/teacher/meeting-period/TeacherMeetingPeriod.api";
import { SetAdMeetingPeriod } from "../../../../app/admin/AdMeetingPeriodSlice.reducer";
import { TeacherAPI } from "../../../../api/teacher/TeacherAPI.api";
import { SetAdTeacher } from "../../../../app/admin/AdTeacherSlice.reducer";
import ModalCreateMeetingRequest from "./create-meeting-request/ModalCreateMeetingRequest";
const MeetingInMyClass = () => {
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const [loading, setLoading] = useState(false);
  const { idClass } = useParams();
  const [classDetail, setClassDetail] = useState({});
  const navigate = useNavigate();
  const [showModalImport, setShowModalImport] = useState(false);
  const [lock, setLock] = useState(1);
  useEffect(() => {
    window.scrollTo(0, 0);
    featchDataMeetingPeriod();
    fetchDataTeacher();
    featchMeeting(idClass);
    featchClass(idClass);
    return () => {
      dispatch(SetMeeting([]));
    };
  }, []);

  const featchDataMeetingPeriod = async () => {
    try {
      await TeacherMeetingPeriodAPI.getPeriod().then((respone) => {
        dispatch(SetAdMeetingPeriod(respone.data.data));
      });
    } catch (error) {}
  };

  const fetchDataTeacher = async () => {
    const responseTeacherData = await TeacherAPI.getAllTeacher();
    const teacherData = responseTeacherData.data.data;
    dispatch(SetAdTeacher(teacherData));
  };

  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassDetail(responese.data.data);
        document.title = "Buổi học | " + responese.data.data.code;
        setLock(responese.data.data.statusClass);
      });
    } catch (error) {
      setTimeout(() => {
        navigate("/teacher/my-class");
      }, [1000]);
    }
  };
  const featchMeeting = async (idClass) => {
    setLoading(false);
    try {
      await TeacherMeetingAPI.getAllMeetingByIdClass(idClass).then(
        (responese) => {
          dispatch(SetMeeting(responese.data.data));
          setLoading(true);
        }
      );
    } catch (error) {}
  };

  const convertLongToDateTime = (dateLong) => {
    const date = new Date(dateLong);
    const format = `${date.getFullYear()}-${
      date.getMonth() + 1
    }-${date.getDay()}_${date.getHours()}_${date.getMinutes()}_${date.getSeconds()}`;
    return format;
  };

  const handleExport = async () => {
    try {
      dispatch(SetLoadingTrue());
      const response = await TeacherMeetingAPI.export(idClass);
      const blob = new Blob([response.data], {
        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
      });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download =
        "DanhSachBuoiHocYeuCau_" +
        convertLongToDateTime(new Date().getTime()) +
        ".xlsx";
      link.click();
      window.URL.revokeObjectURL(url);
      dispatch(SetLoadingFalse());
      message.success("Tải mẫu thành công !");
    } catch (error) {
      dispatch(SetLoadingFalse());
    }
  };

  const handleRedirectMeetingRequest = () => {
    navigate(`/teacher/my-class/meeting-request/` + idClass);
  };

  const handleCancelImport = () => {
    setShowModalImport(false);
  };
  const dataMeeting = useAppSelector(GetMeeting);
  const [showModalCreate, setShowModalCreate] = useState(false);

  const handleCancelModalCreate = () => {
    setShowModalCreate(false);
  };
  return (
    <div className="teacher-meeting">
      <ModalCreateMeetingRequest
        idClass={classDetail.id}
        visible={showModalCreate}
        onCancel={handleCancelModalCreate}
      />
      {!loading && <LoadingIndicator />}
      <div className="box-one">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <ControlOutlined style={{ fontSize: "22px" }} />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>{" "}
            <span style={{ color: "gray", fontSize: "14px" }}> - Buổi học</span>
          </span>
        </Link>
      </div>
      <div className="box-two-student-in-my-class">
        <div
          className="box-two-student-in-my-class-son"
          style={{ minHeight: "555px" }}
        >
          <div className="button-menu">
            <div>
              <Link
                to={`/teacher/my-class/post/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                BÀI ĐĂNG &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/students/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                THÔNG TIN LỚP HỌC &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/teams/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                QUẢN LÝ NHÓM &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/meeting/${idClass}`}
                id="menu-checked"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                BUỔI HỌC &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/attendance/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM DANH &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/point/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM &nbsp;
              </Link>
              <div
                className="box-center"
                style={{
                  height: "28.5px",
                  width: "auto",
                  backgroundColor: "rgb(38, 144, 214)",
                  color: "white",
                  borderRadius: "5px",
                  float: "right",
                }}
              >
                <span
                  style={{
                    fontSize: "14px",
                    padding: "15px",
                    fontWeight: 500,
                  }}
                >
                  {classDetail.code}
                </span>
              </div>
              <hr />
            </div>
          </div>
          <div className="menu-teacher-search">
            <div style={{ margin: "15px 0px 15px 15px" }}>
              <span style={{ fontSize: "17px", fontWeight: 500 }}>
                <FontAwesomeIcon
                  icon={faTableList}
                  style={{
                    marginRight: "10px",
                    fontSize: "20px",
                  }}
                />
                Danh sách buổi học :{" "}
                <span
                  style={{
                    padding: "5px",
                    backgroundColor: "rgb(38, 144, 214)",
                    color: "white",
                    fontSize: "15px",
                    borderRadius: "5px",
                    fontWeight: "400",
                  }}
                >
                  {dataMeeting != null ? dataMeeting.length : 0} buổi học
                </span>
              </span>{" "}
              <Button
                className="btn_clear"
                style={{
                  backgroundColor: "rgb(38, 144, 214)",
                  color: "white",
                  float: "right",
                  margin: "0px 10px",
                }}
                onClick={handleRedirectMeetingRequest}
              >
                <FontAwesomeIcon
                  icon={faCalendarWeek}
                  style={{ marginRight: "7px" }}
                />
                <span>Danh sách yêu cầu</span>
              </Button>
              {lock === 0 && (
                <Button
                  className="btn_clear"
                  style={{
                    backgroundColor: "rgb(38, 144, 214)",
                    color: "white",
                    margin: "0px 0px 0 10px",
                    float: "right",
                  }}
                  onClick={() => setShowModalImport(true)}
                >
                  <FontAwesomeIcon
                    icon={faPaperPlane}
                    style={{ marginRight: "7px" }}
                  />
                  Gửi yêu cầu tạo buổi học
                </Button>
              )}
              {lock === 0 && (
                <Button
                  className="btn_clear"
                  style={{
                    backgroundColor: "rgb(38, 144, 214)",
                    color: "white",
                    margin: "0px 0px 0 10px",
                    float: "right",
                  }}
                  onClick={() => setShowModalCreate(true)}
                >
                  <FontAwesomeIcon
                    icon={faPaperPlane}
                    style={{ marginRight: "7px" }}
                  />
                  Tạo buổi học yêu cầu
                </Button>
              )}
              {lock === 0 && (
                <Button
                  className="btn_clear"
                  style={{
                    backgroundColor: "rgb(38, 144, 214)",
                    color: "white",
                    float: "right",
                  }}
                  onClick={handleExport}
                >
                  <FontAwesomeIcon
                    icon={faDownload}
                    style={{ marginRight: "7px" }}
                  />
                  <span>Tải mẫu</span>
                </Button>
              )}
              <ModalFileImportMeeting
                idClass={idClass}
                visible={showModalImport}
                onCancel={handleCancelImport}
              />
            </div>
            <div
              className="data-table-te"
              style={{
                height: "auto",
                minHeight: "380px",
              }}
            >
              {dataMeeting.length > 0 ? (
                <>
                  {dataMeeting.map((record) => (
                    <div className="box-card-te" key={record.id}>
                      <Link
                        to={`/teacher/my-class/meeting/detail/${record.id}`}
                        key={record.id}
                      >
                        <Row gutter={24}>
                          <Col span={15}>
                            <div
                              className="box-icon"
                              style={{ marginLeft: "30px" }}
                            >
                              <BookOutlined
                                style={{ color: "white", fontSize: "21px" }}
                              />
                            </div>
                            <span
                              className="title-text"
                              style={{
                                fontSize: "16px",
                                color: "black",
                              }}
                            >
                              {record.name} {" - "}
                              {record.typeMeeting === 0 ? (
                                <span style={{ color: "red" }}>Online</span>
                              ) : (
                                <span>Offline</span>
                              )}
                              {" - "}
                              <span style={{ color: "red" }}>
                                {record.userNameTeacher}
                              </span>
                              {" - "}
                              <span style={{ color: "red" }}>
                                {record.statusMeeting === 0 ? (
                                  <Tag
                                    color="success"
                                    style={{
                                      textAlign: "center",
                                    }}
                                  >
                                    Buổi học
                                  </Tag>
                                ) : (
                                  <Tag
                                    color="warning"
                                    style={{
                                      textAlign: "center",
                                    }}
                                  >
                                    Buổi nghỉ
                                  </Tag>
                                )}
                              </span>
                            </span>
                          </Col>
                          <Col
                            span={9}
                            style={{
                              fontSize: "13px",
                            }}
                          >
                            {!convertStatusByHourMinute(
                              record.meetingDate,
                              record.startHour,
                              record.startMinute,
                              record.endHour,
                              record.endMinute
                            ) && (
                              <div>
                                <Badge.Ribbon
                                  color="rgb(38, 144, 214)"
                                  text={"Học xong"}
                                />
                              </div>
                            )}
                            <div style={{ color: "grey", paddingTop: "3px" }}>
                              {" "}
                              Ngày dạy:{" "}
                              <span
                                style={{
                                  color: "red",
                                  fontWeight: "500",
                                }}
                              >
                                <span>
                                  {convertDateLongToString(record.meetingDate)}
                                </span>
                                {record.meetingPeriod !== null && (
                                  <span>
                                    {" "}
                                    - {record.meetingPeriod} (
                                    {convertHourAndMinuteToString(
                                      record.startHour,
                                      record.startMinute,
                                      record.endHour,
                                      record.endMinute
                                    )}
                                    )
                                  </span>
                                )}
                              </span>
                            </div>
                          </Col>
                        </Row>
                      </Link>
                    </div>
                  ))}
                </>
              ) : (
                <Empty
                  imageStyle={{ height: 60 }}
                  description={<span>Không có dữ liệu</span>}
                />
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MeetingInMyClass;
