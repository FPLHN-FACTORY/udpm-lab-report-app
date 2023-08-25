import { Row, Col } from "antd";
import { useParams } from "react-router";
import "./styleTeamInMeeting.css";
import {
  BookOutlined,
  ControlOutlined,
  UnorderedListOutlined,
} from "@ant-design/icons";
import LoadingIndicator from "../../../../../helper/loading";
import { useEffect, useState } from "react";
import { TeacherMeetingAPI } from "../../../../../api/teacher/meeting/TeacherMeeting.api";
import { TeacherTeamsAPI } from "../../../../../api/teacher/teams-class/TeacherTeams.api";
import CollapseTeam from "../team/collapse-team/CollapseTeam";
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
      <div className="title-teacher-my-class">
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{ fontSize: "18px", marginLeft: "10px", fontWeight: "500" }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - buổi học</span>
        </span>
      </div>
      <div className="box-filter">
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
                  {meeting.name}
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
    </>
  );
};

export default TeamInMeeting;
