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
import { sinhVienCurrent } from "../../../../../../helper/inForUser";
import { StudentTempalteReportAPI } from "../../../../../../api/student/StTemplateReportAPI";

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
  const [descriptionsReport, setDescriptionsReport] = useState("");
  const [RoleStudent, setStudentRole] = useState([]);
  const [template, setTempalte] = useState({});


  useEffect(() => {
    window.scrollTo(0, 0);
    setDescriptionsHomeWork("");
    setDescriptionsNote("");
    setDescriptionsReport("");
    setLoading(true);
    featchTemplateReport();
  }, [items]);

  const clear = () => {
    setEdit(false);
    setActivePanel(null);
  };
  const featchTemplateReport = async () => {
    try {
      await StudentTempalteReportAPI.getTemplateReport().then((response) => {
        setTempalte(response.data.data);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
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
            idReport: "",
          };
          setObjDetail(dataNew);
          setDescriptionsHomeWork("");
          setDescriptionsNote("");
        } else {
          setDescriptionsHomeWork(response.data.data.descriptionsHomeWork);
          setDescriptionsNote(response.data.data.descriptionsNote);
          setDescriptionsReport(response.data.data.descriptionsReport);
          setObjDetail(response.data.data);
        }
        setLoading(true);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
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
        idStudent: sinhVienCurrent.id,
      };
      await StudentMeetingAPI.updateHomeWorkAndNote(data).then((response) => {
        toast.success("Lưu thành công");
        clear();
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  const handleCancel = () => {
    setEdit(false);
    featchHomeWorkNote(idTeamDetail);
  };

  useEffect(() => {
    if (items[0].classId != null && items[0].classId != undefined) {
      const getRoleByIdStudent = (idStudent) => {
        setLoading(false);

        StudentMeetingAPI.getRoleByIdStudent(idStudent, items[0].classId).then(
          (response) => {
            setStudentRole(response.data.data);
            console.log(response.data.data);
            setLoading(true);
          }
        );
      };
      getRoleByIdStudent(sinhVienCurrent.id);
    }
  }, [items]);

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
      <>
        <div>
          <>
            {RoleStudent === 0 ? (
              <div
                className="info-content"
                style={{ marginLeft: "40px", marginRight: "40px" }}
              >
                <Row gutter={16}>
                  <Col span={8} style={{ marginTop: "10px" }}>
                    {" "}
                    <span  className="title-main">Nhận xét:</span>
                    <TextArea
                      style={{ marginTop: "10px" }}
                      rows={10}
                      value={descriptionsNote}
                      onChange={(e) => setDescriptionsNote(e.target.value)}
                      onClick={(e) => {
                        e.stopPropagation();
                        setEdit(true);
                      }}
                    />
                  </Col>
                  <Col span={8} style={{ marginTop: "10px" }}>
                    {" "}
                    <span
                      className="title-main"
                    >
                      Bài tập về nhà:
                    </span>
                    <TextArea
                      style={{ marginTop: "10px" }}
                      rows={10}
                      value={descriptionsHomeWork}
                      onChange={(e) => setDescriptionsHomeWork(e.target.value)}
                      onClick={(e) => {
                        e.stopPropagation();
                        setEdit(true);
                      }}
                    />
                  </Col>
                  <Col span={8} style={{ marginTop: "15px" }}>
                    {" "}
                    <span
                      className="title-main"
                    >
                      Báo cáo:
                    </span>
                    <TextArea
                      style={{ marginTop: "10px" }}
                      rows={10}
                      value={descriptionsReport}
                      // onChange={(e) => setDescriptionsHomeWork(e.target.value)}
                      onChange={(e) => setDescriptionsReport(e.target.value)}
                      onClick={(e) => {
                      e.stopPropagation();
                      setEdit(true);
                    }}
                    />
                  </Col>
                  
                  {edit && (
                    <>
                      <div
                        style={{
                          paddingTop: "15px",
                          paddingLeft: "85%",
                          marginLeft: "40px",
                        }}
                      >
                        <Button
                          style={{
                            backgroundColor: "rgb(61, 139, 227)",
                            color: "white",
                            marginRight: "10px",
                          }}
                          onClick={update}
                        >
                          Lưu
                        </Button>
                        <Button
                          style={{
                            backgroundColor: "red",
                            color: "white",
                          }}
                          onClick={handleCancel}
                        >
                          Hủy
                        </Button>
                      </div>
                    </>
                  )}
                </Row>
                <Row gutter={16}>
                <Col span={24}>
                  <span className="title-main">Template mẫu báo cáo:</span>
                  <TextArea rows={5} value={template.descriptions} />
                </Col>
              </Row>
              </div>
            ) : (
              <div
                className="info-content"
                style={{ marginLeft: "40px", marginRight: "40px" }}
              >
                <Row gutter={16}>
                  <Col span={8}>
                    {" "}
                    <span  className="title-main">
                      Nhận xét:
                    </span>
                    <TextArea
                      style={{ marginTop: "10px" }}
                      rows={10}
                      value={descriptionsNote}
                      readOnly
                    />
                  </Col>
                  <Col span={8}>
                    {" "}
                    <span
                       className="title-main"
                    >
                      Bài tập về nhà:
                    </span>
                    <TextArea
                      style={{ marginTop: "10px" }}
                      rows={10}
                      value={descriptionsHomeWork}
                      readOnly
                    />
                  </Col>
                  <Col span={8} style={{ marginTop: "15px" }}>
                    {" "}
                    <span
                      className="title-main"
                    >
                      Báo cáo:
                    </span>
                    <TextArea
                      style={{ marginTop: "10px" }}
                      rows={10}
                      value={descriptionsReport}
                      readOnly
                    />
                  </Col>
                </Row>
                <Row gutter={16}>
                <Col span={24}>
                  <span className="title-main">Template mẫu báo cáo:</span>
                  <TextArea rows={5} value={template.descriptions} />
                </Col>
              </Row>
              </div>
            )}
          </>
        </div>
      </>
    </div>
  );
};
export default CollapseMeeting;
