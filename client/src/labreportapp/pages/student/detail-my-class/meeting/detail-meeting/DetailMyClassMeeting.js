import { Row, Col, Empty } from "antd";
import { useParams } from "react-router";
import "./style-detail-my-class-meeting.css";
import { BookOutlined, UnorderedListOutlined } from "@ant-design/icons";
import LoadingIndicator from "../../../../../helper/loading";
import { useEffect, useState } from "react";
import { StudentMeetingAPI } from "../../../../../api/student/StMeetingAPI";
import CollapseMeeting from "../detail-meeting/collapseMeeting/StCollapseMeeting";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";
import { faHome } from "@fortawesome/free-solid-svg-icons";
import { toast } from "react-toastify";
const StTeamMeeting = () => {
  const { idMeeting } = useParams();
  const [meeting, setMeeting] = useState({});
  const [team, setTeam] = useState(["a", "b"]);

  useEffect(() => {
    window.scrollTo(0, 0);
    featchMeeting(idMeeting);
  }, []);

  const featchMeeting = async (id) => {
    try {
      await StudentMeetingAPI.getDetailByIdMeeting(id).then((response) => {
        setMeeting(response.data.data);
        document.title = "Bảng điều khiển - " + response.data.data.name;
        featchTeams(response.data.data.idClass);
      });
    } catch (error) {
      toast.error(error.response.data.message);
    }
  };

  const featchTeams = async (id) => {
    try {
      await StudentMeetingAPI.getTeamInMeeting(id).then((responese) => {
        console.log(responese.data.data);
        setTeam(responese.data.data);
      });
    } catch (error) {
      console.log(error);
    }
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
      <div className="box-one">
        <Link style={{ color: "black" }}>
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
                    Thời gian: {convertLongToDate(meeting.meetingDate)} -{" "}
                    {meeting.meetingPeriod}
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
                <Empty
                  imageStyle={{ height: 60 }}
                  description={<span>Không có dữ liệu</span>}
                />
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
