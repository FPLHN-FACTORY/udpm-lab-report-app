import { Row, Col } from "antd";
import { useParams } from "react-router";
import "./styleTeamInMeeting.css";
import { BookOutlined, UnorderedListOutlined } from "@ant-design/icons";
import LoadingIndicator from "../../../../../helper/loading";
import { useEffect, useState } from "react";
import { TeacherMeetingAPI } from "../../../../../api/teacher/meeting/TeacherMeeting.api";
import { TeacherTeamsAPI } from "../../../../../api/teacher/teams-class/TeacherTeams.api";
import CollapseTeam from "../team/collapse-team/CollapseTeam";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";
import { faHome } from "@fortawesome/free-solid-svg-icons";

const TeamInMeeting = () => {
  const { idMeeting } = useParams();
  const [meeting, setMeeting] = useState({});
  const [team, setTeam] = useState([]);
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - buổi học";
    featchMeeting(idMeeting);
  }, []);

  const featchMeeting = async (id) => {
    setLoading(false);
    try {
      await TeacherMeetingAPI.getDetailByIdMeeting(id).then((response) => {
        setMeeting(response.data.data);
        featchTeams(response.data.data.idClass);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  const featchTeams = async (id) => {
    try {
      await TeacherTeamsAPI.getTeamsByIdClass(id).then((responese) => {
        setTeam(responese.data.data);
        setLoading(true);
      });
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
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="box-one">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <FontAwesomeIcon
              icon={faHome}
              style={{ color: "#00000", fontSize: "23px" }}
            />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>{" "}
            <span style={{ color: "gray", fontSize: "14px" }}>
              {" "}
              - Chi tiết buổi học
            </span>
          </span>
        </Link>
      </div>
      <div className="box-two-student-in-my-class">
        <div>
          <Link
            to={`/teacher/my-class/meeting/${meeting.idClass}`}
            style={{ color: "black" }}
          >
            <span style={{ fontSize: "18px" }}>
              <BookOutlined
                style={{ color: "black", fontSize: 18, marginRight: "5px" }}
              />
              Danh sách buổi học
            </span>{" "}
          </Link>
          <span style={{ fontSize: "18px" }}> / </span>{" "}
          <span style={{ fontSize: "18px" }}>
            <BookOutlined
              style={{ color: "black", fontSize: 18, marginRight: "5px" }}
            />
            {meeting.name}
          </span>{" "}
        </div>
        <div
          className="box-two-student-in-my-class-son"
          style={{ minHeight: "580px", marginTop: "25px" }}
        >
          <div style={{ marginLeft: "30px" }}>
            <Row gutter={16}>
              <Col span={20}>
                {" "}
                <div className="title-left-meeting">
                  <div className="box-icon-detail">
                    <BookOutlined style={{ color: "white", fontSize: 21 }} />
                  </div>
                  <span
                    style={{
                      fontSize: "24px",
                      color: "#1967D2",
                    }}
                  >
                    {meeting.name} : {meeting.descriptions}
                  </span>
                </div>
              </Col>
              <Col span={4}>
                <div
                  style={{ lineHeight: "42px", color: "grey", float: "right" }}
                >
                  <span>
                    Thời gian: {convertLongToDate(meeting.meetingDate)} - Ca{" "}
                    {meeting.meetingPeriod + 1}
                  </span>
                </div>
              </Col>
            </Row>
            <hr />
          </div>
          <div className="row-meeting">
            <div style={{ margin: "0px 0px 20px 80px" }}>
              {" "}
              <span style={{ fontSize: "17px", fontWeight: "500" }}>
                {" "}
                <UnorderedListOutlined
                  style={{ marginRight: "10px", fontSize: "20px" }}
                />
                Danh sách nhóm
              </span>
            </div>
            <CollapseTeam items={team}></CollapseTeam>
          </div>
        </div>
      </div>
    </>
  );
};

export default TeamInMeeting;
