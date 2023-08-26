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
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome } from "@fortawesome/free-solid-svg-icons";

const MeetingInMyClass = () => {
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);
  const { idClass } = useParams();
  const [countMeeting, setCountMeeting] = useState(0);

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - buổi học";
    featchCountMeeting(idClass);
    featchMeeting(idClass);
  }, []);

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
    <>
      {!loading && <LoadingIndicator />}
      <div className="title-teacher-my-class">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <FontAwesomeIcon
              icon={faHome}
              style={{ color: "#00000", fontSize: "23px" }}
            />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>{" "}
            <span style={{ color: "gray", fontSize: "14px" }}> - buổi học</span>
          </span>
        </Link>
      </div>
      <div className="box-filter" style={{ minHeight: "580px" }}>
        <div className="button-menu-teacher">
          <div>
            <Link
              to={`/teacher/my-class/students/${idClass}`}
              className="custom-link"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
              }}
            >
              THÀNH VIÊN TRONG LỚP &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/students-in-class/${idClass}`}
              className="custom-link"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              ĐIỂM DANH &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/teams/${idClass}`}
              className="custom-link"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              QUẢN LÝ NHÓM &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/meeting/${idClass}`}
              id="menu-checked"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              BUỔI HỌC &nbsp;
            </Link>
            <hr />
          </div>
        </div>
        <div className="menu-teacher-search">
          <div
            className="box-center"
            style={{
              height: "30px",
              width: "100px",
              backgroundColor: "#007bff",
              color: "white",
              borderRadius: "5px",
              margin: "5px 0px 0px 91.5%",
            }}
          >
            {" "}
            <span style={{ fontSize: "14px" }}> {countMeeting} buổi học </span>
          </div>
          <div>
            <div style={{ marginLeft: "0px", marginBottom: "20px" }}>
              {" "}
              <span style={{ fontSize: "17px", fontWeight: "500" }}>
                {" "}
                <UnorderedListOutlined
                  style={{ marginRight: "10px", fontSize: "20px" }}
                />
                Danh sách buổi học
              </span>
            </div>
            <div className="data-table">
              {dataMeeting.length > 0 ? (
                <>
                  {dataMeeting.map((record) => (
                    <Link
                      to={`/teacher/my-class/meeting/detail/${record.id}`}
                      key={record.id}
                    >
                      <div role="button" className="box-card">
                        <div className="title-left">
                          <div className="flex-container">
                            {" "}
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
                                <span>online</span>
                              ) : (
                                <span>offline</span>
                              )}
                            </p>
                          </div>
                        </div>
                        <div className="title-right">
                          <div>
                            {" "}
                            <span>
                              Thời gian: {convertLongToDate(record.meetingDate)}{" "}
                              - Ca {record.meetingPeriod + 1}
                            </span>
                          </div>
                        </div>
                      </div>{" "}
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
    </>
  );
};

export default MeetingInMyClass;
