import { Row, Col } from "antd";
import { useParams } from "react-router";
import "./style-detail-my-class-meeting.css";
import {
  BookOutlined,
  ControlOutlined,
  UnorderedListOutlined,
} from "@ant-design/icons";
import LoadingIndicator from "../../../../../helper/loading";
import { useEffect, useState } from "react";
import { StudentMeetingAPI } from "../../../../../api/student/StMeetingAPI";
import { StMyTeamClassAPI } from "../../../../../api/student/StTeamClass";

import { TeacherTeamsAPI } from "../../../../../api/teacher/teams-class/TeacherTeams.api";
import CollapseMeeting from "../detail-meeting/collapseMeeting/StCollapseMeeting";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";
import { faHome } from "@fortawesome/free-solid-svg-icons";
import { toast } from "react-toastify";
const StTeamMeeting = () => {
  const { idMeeting } = useParams();
  const [meeting, setMeeting] = useState({});
  const [team, setTeam] = useState(["a", "b"]);
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - buổi học";
    featchMeeting(idMeeting);
    // featchTeams(meeting.idClass)
  }, []);

  const featchMeeting = async (id) => {
    setLoading(false);
    try {
      await StudentMeetingAPI.getDetailByIdMeeting(id).then((response) => {
        setMeeting(response.data.data);
        // featchTeams(response.data.data.idClass);
      });
    } catch (error) {
      toast.error(error.response.data.message);
    }
  };

  useEffect(() => {
    featchTeams(meeting.idClass);
  }, [meeting]);
  const featchTeams = async (id) => {
    
    try {
      await StudentMeetingAPI.getTeamInMeeting(id).then((responese) => {
        // console.log(responese.data.data);
        setTeam(responese.data.data);
        // console.log(team);

        setTimeout(() => {
          setLoading(true);
        }, 250);
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
        <Link
          //  to="/teacher/my-class"
          style={{ color: "black" }}
        >
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
            to={`/student/my-class/meeting/${meeting.idClass}`}
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
            <div style={{ marginLeft: "40px" }}>
              {" "}
              <span style={{ fontSize: "17px", fontWeight: "500" }}>
                {" "}
                <UnorderedListOutlined
                  style={{ marginRight: "10px", fontSize: "20px" }}
                />
                Chi tiết buổi học
              </span>
            </div>
            {team.length === 0 && (
              <>
                <p
                  style={{
                    textAlign: "center",
                    marginTop: "100px",
                    fontSize: "15px",
                    color: "red",
                  }}
                >
                  Bạn chưa thuộc nhóm nào
                </p>
              </>
            )}
            {team.length > 0 && (
              <>
                <CollapseMeeting items={team}></CollapseMeeting>
              </>
            )}
          </div>
        </div>
      </div>
    </>
  );
};
export default StTeamMeeting;
