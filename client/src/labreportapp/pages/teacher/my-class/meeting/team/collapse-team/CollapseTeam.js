import React, { useEffect, useState } from "react";
import { Badge, Button, Col, Collapse, Empty, Row, Spin } from "antd";
import "./styleCollapseTeam.css";
import TextArea from "antd/es/input/TextArea";
import { TeacherMeetingAPI } from "../../../../../../api/teacher/meeting/TeacherMeeting.api";
import { TeacherTempalteReportAPI } from "../../../../../../api/teacher/template-report/TeacherTemplateReport.api";
import { TeacherMeetingHomeWorkNoteAPI } from "../../../../../../api/teacher/meeting/homework-note/TeacherMeetingHomeWorkNote.api";
import { useParams } from "react-router";
import { toast } from "react-toastify";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUsersRectangle } from "@fortawesome/free-solid-svg-icons";
import { convertCheckTimeCurrentAndMeetingDate } from "../../../../../../helper/util.helper";
const { Panel } = Collapse;

const CollapseTeam = ({ team, featchMeeting }) => {
  const [activePanel, setActivePanel] = useState(null);
  const [edit, setEdit] = useState(false);
  const { idMeeting } = useParams();
  const [idTeamDetail, setIdTeamDetail] = useState("");
  const [objDetail, setObjDetail] = useState({});
  const [template, setTempalte] = useState({});
  const [loading, setLoading] = useState(true);
  const [descriptionsHomeWork, setDescriptionsHomeWork] = useState("");
  const [descriptionsNote, setDescriptionsNote] = useState("");
  const [descriptionsReport, setDescriptionsReport] = useState("");
  const [dowloading, setDownloading] = useState(false);
  const [checkTime, setCheckTime] = useState(0);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - chi tiết buổi học";
    setDescriptionsHomeWork("");
    setDescriptionsNote("");
    setDescriptionsReport("");
    setLoading(true);
    featchTemplateReport();
  }, []);

  useEffect(() => {
    setDescriptionsHomeWork("");
    setDescriptionsNote("");
    setDescriptionsReport("");
    featchHomeWorkNote(idTeamDetail);
  }, [idTeamDetail, activePanel]);

  const toggleCard = (index, item) => {
    setEdit(false);
    setActivePanel(index);
    setIdTeamDetail(item.id);
  };
  const handleCancel = () => {
    setEdit(false);
    featchHomeWorkNote(idTeamDetail);
  };
  const clear = () => {
    setEdit(false);
    setActivePanel(null);
    setObjDetail({});
  };
  const featchTemplateReport = async () => {
    try {
      await TeacherTempalteReportAPI.getTemplateReport().then((response) => {
        setTempalte(response.data.data);
      });
    } catch (error) {
      console.log(error);
    }
  };
  const featchHomeWorkNote = async (idTeam) => {
    setDescriptionsHomeWork("");
    setDescriptionsNote("");
    setDescriptionsReport("");
    try {
      let data = {
        idTeam: idTeam,
        idMeeting: idMeeting,
      };
      await TeacherMeetingAPI.getDetailHomeWorkAndNoteByIdMeetingandIdTeam(
        data
      ).then((response) => {
        setDownloading(true);
        if (response.data.data === null) {
          const dataNew = {
            idHomeWork: null,
            idNote: null,
            idReport: null,
          };
          setDescriptionsHomeWork("");
          setDescriptionsNote("");
          setDescriptionsReport("");
          setObjDetail(dataNew);
        } else {
          setDescriptionsHomeWork(response.data.data.descriptionsHomeWork);
          setDescriptionsNote(response.data.data.descriptionsNote);
          setDescriptionsReport(response.data.data.descriptionsReport);
          setObjDetail(response.data.data);
        }

        setCheckTime(
          convertCheckTimeCurrentAndMeetingDate(
            response.data.data.meetingDate,
            response.data.data.meetingPeriod
          )
        );

        setDownloading(false);
      });
    } catch (error) {
      console.log(error);
    }
  };
  const update = async () => {
    try {
      let data = {
        idMeeting: idMeeting,
        idTeam: idTeamDetail,
        idHomeWork: objDetail.idHomeWork,
        descriptionsHomeWork: descriptionsHomeWork,
        idNote: objDetail.idNote,
        descriptionsNote: descriptionsNote,
        idReport: objDetail.idReport,
        descriptionsReport: descriptionsReport,
      };
      await TeacherMeetingHomeWorkNoteAPI.updateHomeWorkAndNote(data).then(
        (response) => {
          toast.success("Cập nhật thành công !");
          setDescriptionsHomeWork(response.data.data.descriptionsHomeWork);
          setDescriptionsNote(response.data.data.descriptionsNote);
          setDescriptionsReport(response.data.data.descriptionsReport);
          setObjDetail(response.data.data);
          featchMeeting(idMeeting);
        }
      );
    } catch (error) {
      alert(error.message);
    }
  };

  return (
    <div
      className="teacher-collapse"
      onClick={(e) => {
        e.stopPropagation();
        clear();
      }}
    >
      <Collapse
        bordered={false}
        accordion={true}
        ghost
        showArrow={true}
        className="panel-collap"
        style={{ width: "auto", minWidth: "auto" }}
      >
        {team.map((item, index) => (
          <Panel
            style={{
              width: "auto",
              minWidth: "auto",
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
              <>
                <div
                  className={`custom-collapse-header ${
                    activePanel === index ? "active" : ""
                  }`}
                >
                  <div className="title-left">
                    <div className="box-icon" style={{ float: "left" }}>
                      <FontAwesomeIcon
                        icon={faUsersRectangle}
                        style={{ color: "white", fontSize: "21px" }}
                      />
                    </div>

                    <span
                      style={{
                        lineHeight: "40px",
                        fontSize: "17px",
                        color: "black",
                        float: "left",
                      }}
                    >
                      {item.name}
                    </span>
                  </div>
                  <div style={{ justifyContent: "right" }}>
                    {(checkTime === 1 && item.descriptionsReport === null) ||
                    (checkTime === 1 && item.descriptionsReport === "") ? (
                      <Badge.Ribbon text={"Chưa báo cáo"} color="#E2B357" />
                    ) : (checkTime === 2 && item.descriptionsReport === null) ||
                      (checkTime === 2 && item.descriptionsReport === "") ? (
                      <Badge.Ribbon text={"Chưa báo cáo"} color="#E2B357" />
                    ) : (
                      ""
                    )}
                  </div>
                </div>
              </>
            }
            key={index}
          >
            <Spin spinning={dowloading} style={{ marginTop: "10px" }}>
              <div
                className="info-content"
                onClick={() => setEdit(true)}
                style={{ minWidth: "80%", width: "auto" }}
              >
                <Row gutter={16}>
                  <Col span={8}>
                    <span className="title-main">Nhận xét:</span>
                    <TextArea
                      rows={10}
                      placeholder="Nhập nhận xét"
                      value={descriptionsNote}
                      onChange={(e) => setDescriptionsNote(e.target.value)}
                      onClick={(e) => {
                        e.stopPropagation();
                        setEdit(true);
                      }}
                    />
                  </Col>
                  <Col span={8}>
                    <span className="title-main">Bài tập về nhà:</span>
                    <TextArea
                      rows={10}
                      placeholder="Nhập bài tập"
                      value={descriptionsHomeWork}
                      style={{ readOnly: edit && "false" }}
                      onChange={(e) => setDescriptionsHomeWork(e.target.value)}
                      onClick={(e) => {
                        e.stopPropagation();
                        setEdit(true);
                      }}
                    />
                  </Col>
                  <Col span={8}>
                    <span className="title-main">Báo cáo:</span>
                    <TextArea
                      rows={10}
                      placeholder="Báo cáo"
                      value={descriptionsReport}
                      style={{ readOnly: edit && "false" }}
                      onChange={(e) => setDescriptionsReport(e.target.value)}
                      onClick={(e) => {
                        e.stopPropagation();
                        setEdit(true);
                      }}
                    />
                  </Col>
                </Row>
                <Row gutter={16}>
                  <Col span={24}>
                    <span className="title-main">Template mẫu báo cáo:</span>
                    <TextArea rows={4} value={template.descriptions} />
                  </Col>
                </Row>
                {edit && (
                  <>
                    <div style={{ paddingTop: "15px", textAlign: "center" }}>
                      <Button
                        className="btn_filter"
                        style={{
                          marginRight: "15px",
                          width: "100px",
                        }}
                        onClick={handleCancel}
                      >
                        Hủy
                      </Button>{" "}
                      <Button
                        className="btn_clean"
                        style={{ width: "100px" }}
                        onClick={update}
                      >
                        Cập nhật
                      </Button>
                    </div>
                  </>
                )}
              </div>
            </Spin>
          </Panel>
        ))}
        {team.length === 0 && (
          <Empty
            imageStyle={{ height: "60px" }}
            description={<span>Không có dữ liệu</span>}
          />
        )}
      </Collapse>
    </div>
  );
};

export default CollapseTeam;
