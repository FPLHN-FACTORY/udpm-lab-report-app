import { useParams } from "react-router-dom";
import "./styleMeetingInMyClass.css";
import { Link } from "react-router-dom";
import {
  BookOutlined,
  ControlOutlined,
  UnorderedListOutlined,
} from "@ant-design/icons";
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
import { convertMeetingPeriodToTime } from "../../../../helper/util.helper";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTableList } from "@fortawesome/free-solid-svg-icons";

const MeetingInMyClass = () => {
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const [loading, setLoading] = useState(false);
  const { idClass } = useParams();
  const [countMeeting, setCountMeeting] = useState(0);
  const [classDetail, setClassDetail] = useState({});
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - Buổi học";
    featchCountMeeting(idClass);
    featchMeeting(idClass);
    featchClass(idClass);
  }, []);
  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassDetail(responese.data.data);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
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
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const featchCountMeeting = async (idClass) => {
    setLoading(false);
    try {
      await TeacherMeetingAPI.countMeetingByIdClass(idClass).then(
        (responese) => {
          setCountMeeting(responese.data.data);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const format = `${date.getDate()}/${
      date.getMonth() + 1
    }/${date.getFullYear()}`;
    return format;
  };
  const dataMeeting = useAppSelector(GetMeeting);
  return (
    <div>
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
                  backgroundColor: "#007bff",
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
            <div>
              <div style={{ margin: "15px 0px 15px 15px" }}>
                <span style={{ fontSize: "17px", fontWeight: 500 }}>
                  <FontAwesomeIcon
                    icon={faTableList}
                    style={{
                      marginRight: "10px",
                      fontSize: "20px",
                    }}
                  />
                  Danh sách buổi học : {countMeeting} buổi học
                </span>
              </div>
              <div className="data-table" style={{ minHeight: "380px" }}>
                {dataMeeting.length > 0 ? (
                  <>
                    {dataMeeting.map((record) => (
                      <Link
                        to={`/teacher/my-class/meeting/detail/${record.id}`}
                        key={record.id}
                      >
                        <div className="box-card">
                          <div className="title-left">
                            <div className="flex-container">
                              <div className="title-icon">
                                <div className="box-icon">
                                  <BookOutlined
                                    style={{ color: "white", fontSize: 21 }}
                                  />
                                </div>
                              </div>
                              <p
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
                              </p>
                            </div>
                          </div>
                          <div className="title-right">
                            <div>
                              <span>
                                Ngày dạy:
                                <span
                                  style={{
                                    color: "red",
                                    fontWeight: "500",
                                  }}
                                >
                                  {" " + convertLongToDate(record.meetingDate)}{" "}
                                  - <span>Ca </span>
                                  {record.meetingPeriod + 1}
                                  <span>
                                    {" "}
                                    (
                                    {convertMeetingPeriodToTime(
                                      record.meetingPeriod
                                    )}
                                    )
                                  </span>
                                </span>
                              </span>
                            </div>
                          </div>
                        </div>
                      </Link>
                    ))}
                  </>
                ) : (
                  <>
                    <p
                      style={{
                        textAlign: "center",
                        marginTop: "100px",
                        fontSize: "15px",
                        color: "red",
                      }}
                    >
                      Không có buổi học
                    </p>
                  </>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MeetingInMyClass;
