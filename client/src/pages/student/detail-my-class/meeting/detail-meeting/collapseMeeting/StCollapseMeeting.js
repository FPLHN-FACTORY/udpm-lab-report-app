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
        // console.log(items);
       
      }, []);

    const clear = () => {
        setEdit(false);
        setActivePanel(null);
        // setObjDetail({});
      };
    //   useEffect(() => {
    //     featchHomeWorkNote(idTeamDetail);
    //   }, [idTeamDetail, activePanel]);

    const toggleCard = (index, item) => {
        setEdit(false);
        setActivePanel(index);
        setIdTeamDetail(item.id);

      };

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
            //   setObjDetail(dataNew);
              setDescriptionsHomeWork("");
              setDescriptionsNote("");
            } else {
              setDescriptionsHomeWork(response.data.data.descriptionsHomeWork);
              setDescriptionsNote(response.data.data.descriptionsNote);
            //   setObjDetail(response.data.data);
            }
            setLoading(true);
          });
          console.log(data.idMeeting);


        } catch (error) {
          alert("Lỗi hệ thống, vui lòng F5 lại trang !");
        }
      };
    <div
      className="centered-collapse"
    //   onClick={(e) => {
    //     e.stopPropagation();
    //     clear();
    //   }} 
    >
      <Collapse bordered={false} accordion={true} ghost showArrow={true}>
        {items.map((item, index) => (
          <Panel
            style={{
              minWidth: "900px",
              boxShadow:
                activePanel === index
                  ? "0px 0px 10px rgba(0, 0, 0, 0.2)"
                  : "none",
              border: "none",
              borderBottom: "1px solid #ccc",
              borderRadius: activePanel === index ? "8px" : "",
            }}
            onClick={(e) => {
              e.stopPropagation();
              toggleCard(index, item);
            }}
            className={`box-title ${activePanel === index ? "active" : ""}`}
            header={
              <div
                className={`custom-collapse-header ${
                  activePanel === index ? "active" : ""
                }`}
                name="box-card"
              >
                <div className="title-left">
                  <div className="box-icon">
                    <FontAwesomeIcon
                      icon={faUsersRectangle}
                      style={{ color: "white", fontSize: 21 }}
                    />
                  </div>
                  <span
                    style={{
                      fontSize: "16px",
                      color: "black",
                    }}
                  >
                    {item.name}
                  </span>
                </div>
              </div>
            }
            key={index}
          >
            <div className="info-content" >
              <Row gutter={16}>
                <Col span={12}>
                  {" "}
                  <span style={{ color: "black" }}>Nhận xét:</span>
                  <TextArea
                    rows={4}
                    placeholder="Nhập nhận xét"
                    value={descriptionsNote}
                    onChange={(e) => setDescriptionsNote(e.target.value)}
                    // onClick={(e) => {
                    //   e.stopPropagation();
                    //   setEdit(true);
                    // }}
                  />
                </Col>
                <Col span={12}>
                  {" "}
                  <span style={{ color: "black", fontFamily: "unset" }}>
                    Bài tập về nhà:
                  </span>
                  <TextArea
                    rows={4}
                    placeholder="Nhập bài tập"
                    value={descriptionsHomeWork}
                    style={{ readOnly: edit && "false" }}
                    onChange={(e) => setDescriptionsHomeWork(e.target.value)}
                    // onClick={(e) => {
                    //   e.stopPropagation();
                    //   setEdit(true);
                    // }}
                  />
                </Col>
              </Row>
            </div>
            
          </Panel>
        ))}
      </Collapse>
     </div>
}
export default CollapseMeeting;
