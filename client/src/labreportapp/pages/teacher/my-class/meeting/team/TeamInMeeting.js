import { Row, Col } from "antd";
import { useNavigate, useParams } from "react-router-dom";
import "./styleTeamInMeeting.css";
import { BookOutlined, ControlOutlined } from "@ant-design/icons";
import LoadingIndicator from "../../../../../helper/loading";
import { useEffect, useState } from "react";
import { TeacherMeetingAPI } from "../../../../../api/teacher/meeting/TeacherMeeting.api";
import { TeacherTeamsAPI } from "../../../../../api/teacher/teams-class/TeacherTeams.api";
import CollapseTeam from "../team/collapse-team/CollapseTeam";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTableList } from "@fortawesome/free-solid-svg-icons";
import LoadingIndicatorNoOverlay from "../../../../../helper/loadingNoOverlay";

const TeamInMeeting = () => {
  const { idMeeting } = useParams();
  const [meeting, setMeeting] = useState({});
  const [team, setTeam] = useState([]);
  const [loading, setLoading] = useState(false);
  const [isLoadingWait, setIsLoadingWait] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    window.scrollTo(0, 0);
    setLoading(false);
    featchMeeting(idMeeting);
  }, []);

  const featchMeeting = async (idMeeting) => {
    setIsLoadingWait(false);
    try {
      await TeacherMeetingAPI.getDetailByIdMeeting(idMeeting).then(
        (response) => {
          setMeeting(response.data.data);
          featchTeams(response.data.data.idClass);
          document.title = "Bảng điều khiển - " + response.data.data.name;
        }
      );
    } catch (error) {
      setLoading(true);
      setTimeout(() => {
        navigate("/teacher/my-class");
      }, [1000]);
    }
  };
  const featchTeams = async (idClass) => {
    try {
      await TeacherTeamsAPI.getTeamsByIdClass(idClass, idMeeting).then(
        (responese) => {
          setTeam(responese.data.data);
          setLoading(true);
          setIsLoadingWait(true);
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
  return (
    <>
      {!loading && <LoadingIndicator />}
      {!isLoadingWait && loading && <LoadingIndicatorNoOverlay />}
      <div className="box-one">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <ControlOutlined style={{ fontSize: "23px" }} />
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
          </span>
        </div>
        <div
          className="box-two-student-in-my-class-son"
          style={{ minHeight: "505px", marginTop: "25px", width: "100%" }}
        >
          <div style={{ marginLeft: "30px" }}>
            <Row gutter={16}>
              <Col span={20}>
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
                    {meeting.name}
                    {": " + meeting.descriptions}
                  </span>
                </div>
              </Col>
              <Col span={4}>
                <div
                  style={{ lineHeight: "42px", color: "grey", float: "right" }}
                >
                  <span>
                    Thời gian: {convertLongToDate(meeting.meetingDate)} -{" "}
                    {meeting.meetingPeriod}
                  </span>
                </div>
              </Col>
            </Row>
            <hr />
          </div>
          <div
            className="row-meeting"
            style={{ width: "100%", padding: "2px" }}
          >
            <div style={{ margin: "0px 0px 20px 80px" }}>
              <span style={{ fontSize: "17px", fontWeight: "500px" }}>
                <FontAwesomeIcon
                  icon={faTableList}
                  style={{
                    marginRight: "10px",
                    fontSize: "20px",
                  }}
                />
                Danh sách nhóm
              </span>
            </div>
            <div style={{ width: "auto", padding: "10px" }}>
              <CollapseTeam team={team} featchMeeting={featchMeeting} />
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default TeamInMeeting;
