import React, { useEffect, useState } from "react";
import { Button, Col, Collapse, Row } from "antd";
import "./style-collapse-meeting.css";
import TextArea from "antd/es/input/TextArea";
import { StudentMeetingAPI } from "../../../../../../api/student/StMeetingAPI";
import { useParams } from "react-router";
import { toast } from "react-toastify";
import LoadingIndicator from "../../../../../../helper/loading";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUsersRectangle } from "@fortawesome/free-solid-svg-icons";
const { Panel } = Collapse;

const CollapseMeeting = ({ items }) => {
  const [activePanel, setActivePanel] = useState(null);
  const [edit, setEdit] = useState(false);
  const { idMeeting } = useParams();
  const [idTeamDetail, setIdTeamDetail] = useState("");
  const [objDetail, setObjDetail] = useState({});
  const [loading, setLoading] = useState(true);
  const [descriptionsHomeWork, setDescriptionsHomeWork] = useState("");
  const [descriptionsNote, setDescriptionsNote] = useState("");

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - chi tiết buổi học";
    setDescriptionsHomeWork("");
    setDescriptionsNote("");
    setLoading(true);
  }, [items]);

  const clear = () => {
    setEdit(false);
    setActivePanel(null);
  };
  useEffect(() => {
    featchHomeWorkNote(idTeamDetail);
  }, [idTeamDetail, activePanel]);

  const toggleCard = (index, item) => {
    setEdit(false);
    setActivePanel(index);
    setIdTeamDetail(item.id);
  };

  const callToggleCardForAllItems = () => {
    items.forEach((item, index) => toggleCard(index, item));
  };

  useEffect(() => {
    callToggleCardForAllItems();
  }, [items]);

  const featchHomeWorkNote = async (idTeam) => {
    setLoading(false);
    try {
      let data = {
        idTeam: idTeam,
        idMeeting: idMeeting,
      };
      await StudentMeetingAPI.getDetailHomeWorkAndNoteByIdMeetingandIdTeam(
        data
      ).then((response) => {
        if (response.data.data === null) {
          let dataNew = {
            idHomeWork: "",
            idNote: "",
          };
          setDescriptionsHomeWork("");
          setDescriptionsNote("");
        } else {
          setDescriptionsHomeWork(response.data.data.descriptionsHomeWork);
          setDescriptionsNote(response.data.data.descriptionsNote);
        }
        setLoading(true);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  return (
    <div className="lesson-information">
      {items.map((item, index) => (
        <div className="info-team">
          <span className="info-heading" style={{ marginLeft: "40px" }}>
            Thông tin nhóm:
          </span>
          <div
            className="group-info"
            style={{ marginLeft: "40px", marginRight: "40px" }}
          >
            <span className="group-info-item">
              Tên nhóm: {item != null ? item.name : ""}
            </span>
            <span className="group-info-item">
              Tên đề tài: {item != null ? item.subjectName : ""}
            </span>
          </div>
        </div>
      ))}
      <div
        className="info-content"
        style={{ marginLeft: "40px", marginRight: "40px" }}
      >
        <Row gutter={16}>
          <Col span={12}>
            {" "}
            <span style={{ color: "black", fontWeight: "bold" }}>
              Nhận xét:
            </span>
            <TextArea
              style={{ marginTop: "10px" }}
              rows={4}
              value={descriptionsNote}
              readOnly
            />
          </Col>
          <Col span={12}>
            {" "}
            <span
              style={{
                color: "black",
                fontFamily: "unset",
                fontWeight: "bold",
              }}
            >
              Bài tập về nhà:
            </span>
            <TextArea
              style={{ marginTop: "10px" }}
              rows={4}
              value={descriptionsHomeWork}
              readOnly
            />
          </Col>
        </Row>
      </div>
    </div>
  );
};
export default CollapseMeeting;
