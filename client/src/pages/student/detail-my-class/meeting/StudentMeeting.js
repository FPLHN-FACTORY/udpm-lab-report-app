import { useParams } from "react-router-dom";
import "./style-student-meeting.css";
import { Link } from "react-router-dom";
import {
  BookOutlined,
  ControlOutlined,
  UnorderedListOutlined,
} from "@ant-design/icons";
import { StudentMeetingAPI } from "../../../../api/student/StMeetingAPI";
import {
  SetMeeting,
  GetMeeting,
} from "../../../../app/student/StTeacherMeetingSlice.reduce";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome } from "@fortawesome/free-solid-svg-icons";
import { SetTTrueToggle } from "../../../../app/student/StCollapsedSlice.reducer";
const StMeetingMyClass = () => {
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const [loading, setLoading] = useState(false);
  const { id } = useParams();
  const [countMeeting, setCountMeeting] = useState(0);
  const [classDetail, setClassDetail] = useState({});

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - buổi học";
    featchCountMeeting(id);
    featchMeeting(id);
  }, []);

  const featchMeeting = async (idClass) => {
    setLoading(false);
    try {
      await StudentMeetingAPI.getAllMeetingByIdClass(idClass).then(
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
      await StudentMeetingAPI.countMeetingByIdClass(idClass).then(
        (responese) => {
          setCountMeeting(responese.data.data);
          setLoading(true);
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
      <div style={{ paddingTop: "35px", minHeight: "580px" }}>
        {!loading && <LoadingIndicator />}
        <div className="title-student-my-class">
          <span style={{ paddingLeft: "20px" }}>
            <ControlOutlined style={{ fontSize: "22px" }} />
            <span
              style={{
                fontSize: "18px",
                marginLeft: "10px",
                fontWeight: "500",
              }}
            >
              Bảng điều khiển
            </span>
            <span style={{ color: "gray" }}> - Buổi học</span>
          </span>
        </div>
        <div
          className="box-students-detail-my-class"
          style={{ padding: "20px" }}
        >
          <div
            className="button-menu-student-detail-my-class"
            style={{ minHeight: "600px" }}
          >
            <div>
              <Link
                to={`/student/my-class/post/${id}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                  fontWeight: "bold",
                }}
              >
                BÀI ĐĂNG
              </Link>
              <Link
                to={`/student/my-class/team/${id}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                  fontWeight: "bold",
                }}
              >
                THÔNG TIN LỚP HỌC
              </Link>
              <Link
                id="menu-checked"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                  fontWeight: "bold",
                }}
              >
                DANH SÁCH BUỔI HỌC
              </Link>
              <Link
                className="custom-link"
                to={`/student/my-class/attendance/${id}`}
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM DANH
              </Link>
              <Link
                className="custom-link"
                to={`/student/my-class/point/${id}`}
                style={{
                  fontSize: "16px",
                  fontWeight: "bold",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                }}
              >
                ĐIỂM
              </Link>
              <hr />

              <div className="menu-student-search">
                <div>
                  <div style={{ margin: "25px 0px 20px 16px" }}>
                    {" "}
                    <span style={{ fontSize: "17px", fontWeight: "500" }}>
                      {" "}
                      <UnorderedListOutlined
                        style={{ marginRight: "10px", fontSize: "20px" }}
                      />
                      Danh sách buổi học : {countMeeting} buổi học
                    </span>
                  </div>
                  <div className="data-table">
                    {dataMeeting.length > 0 ? (
                      <>
                        {dataMeeting.map((record) => (
                          <Link
                            to={`/student/my-class/meeting/detail/${record.id}`}
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
                                      <span>Online</span>
                                    ) : (
                                      <span>Offline</span>
                                    )}
                                  </p>
                                </div>
                              </div>
                              <div className="title-right">
                                <div>
                                  {" "}
                                  <span>
                                    Thời gian:{" "}
                                    {convertLongToDate(record.meetingDate)} - Ca{" "}
                                    {record.meetingPeriod + 1}
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
          </div>
        </div>
      </div>
    </>
  );
};

export default StMeetingMyClass;
