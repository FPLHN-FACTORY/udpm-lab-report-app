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
  convertHourAndMinuteToString,
  convertMeetingPeriodToTime,
  convertStatusMeetingByDateAndPeriod,
} from "../../../../helper/util.helper";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTableList } from "@fortawesome/free-solid-svg-icons";
import { Badge, Col, Empty, Row, Tag } from "antd";

const MeetingInMyClass = () => {
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const [loading, setLoading] = useState(false);
  const { idClass } = useParams();
  const [classDetail, setClassDetail] = useState({});
  const navigate = useNavigate();
  useEffect(() => {
    window.scrollTo(0, 0);
    featchMeeting(idClass);
    featchClass(idClass);
  }, []);
  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassDetail(responese.data.data);
        document.title = "Buổi học | " + responese.data.data.code;
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
  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    const format = `${day}/${month}/${year}`;
    return format;
  };
  const dataMeeting = useAppSelector(GetMeeting);
  return (
    <div className="teacher-meeting">
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
              </span>
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
                    <div className="box-card-te">
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
                                <span>Online</span>
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
                            {!convertStatusMeetingByDateAndPeriod(
                              record.meetingDate,
                              record.meetingPeriod
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
                                  {convertLongToDate(record.meetingDate)}
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
