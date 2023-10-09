import React, { useEffect, useState } from "react";
import { Button, Col, Row } from "antd";
import "./style-collapse-meeting.css";
import TextArea from "antd/es/input/TextArea";
import { StudentMeetingAPI } from "../../../../../../api/student/StMeetingAPI";
import { useParams } from "react-router";
import { toast } from "react-toastify";
import { StudentTempalteReportAPI } from "../../../../../../api/student/StTemplateReportAPI";
import LoadingIndicator from "../../../../../../helper/loading";

const CollapseMeeting = ({ items }) => {
  const [activePanel, setActivePanel] = useState(null);
  const [edit, setEdit] = useState(false);
  const { idMeeting } = useParams();
  const [objDetail, setObjDetail] = useState({});
  const [loading, setLoading] = useState(false);
  const [descriptionsHomeWork, setDescriptionsHomeWork] = useState("");
  const [descriptionsNote, setDescriptionsNote] = useState("");
  const [descriptionsReport, setDescriptionsReport] = useState("");
  const [template, setTempalte] = useState({});
  const [role, setRole] = useState(1);

  useEffect(() => {
    window.scrollTo(0, 0);
    setDescriptionsHomeWork("");
    setDescriptionsNote("");
    setDescriptionsReport("");
    featchTemplateReport();
  }, [items]);

  useEffect(() => {
    setLoading(false);
    if (items[0].classId != null && items[0].classId !== undefined) {
      const getRoleByIdStudent = () => {
        StudentMeetingAPI.getRoleByIdStudent(items[0].classId).then(
          (response) => {
            setRole(response.data.data);
          }
        );
      };
      setRole(getRoleByIdStudent());
    }
  }, [items]);

  const featchTemplateReport = async () => {
    setLoading(false);
    try {
      await StudentTempalteReportAPI.getTemplateReport().then((response) => {
        setTempalte(response.data.data);
        setTimeout(() => {
          setLoading(true);
        }, 2000);
      });
    } catch (error) {
      setLoading(true);
      console.log(error);
    }
  };

  useEffect(() => {
    if (items[0].id !== undefined) {
      featchHomeWorkNote(items[0].id);
    }
  }, [items[0].id]);

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
            idHomeWork: null,
            idNote: null,
            idReport: null,
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
      console.log(error);
    }
  };

  const update = async () => {
    try {
      let data = {
        idMeeting: idMeeting,
        idTeam: items[0].id,
        idHomeWork: objDetail.idHomeWork,
        descriptionsHomeWork: descriptionsHomeWork,
        idNote: objDetail.idNote,
        descriptionsNote: descriptionsNote,
        idReport: objDetail.idReport,
        descriptionsReport: descriptionsReport,
      };
      await StudentMeetingAPI.updateHomeWorkAndNote(data).then((response) => {
        toast.success("Cập nhật thành công");
        clear();
      });
    } catch (error) {
      console.log(error);
    }
  };

  const handleCancel = () => {
    setEdit(false);
    featchHomeWorkNote(items[0].id);
  };

  const clear = () => {
    setEdit(false);
    setActivePanel(null);
  };
  return (
    <div className="lesson-information">
      {!loading && <LoadingIndicator />}
      {items.map((item, index) => (
        <div className="info-team">
          <div className="info-heading" style={{ marginLeft: "40px" }}>
            Thông tin nhóm:
          </div>
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
            {role === 0 ? (
              <div
                className="info-content"
                style={{ marginLeft: "40px", marginRight: "40px" }}
              >
                <Row gutter={16}>
                  <Col span={8} style={{ marginTop: "10px" }}>
                    <span className="title-main">Nhận xét:</span>
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
                    <span className="title-main">Bài tập về nhà:</span>
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
                  <Col span={8} style={{ marginTop: "10px" }}>
                    <span className="title-main">Báo cáo:</span>
                    <TextArea
                      style={{ marginTop: "10px" }}
                      rows={10}
                      value={descriptionsReport}
                      onChange={(e) => setDescriptionsReport(e.target.value)}
                      onClick={(e) => {
                        e.stopPropagation();
                        setEdit(true);
                      }}
                    />
                  </Col>
                </Row>
                {edit && role === 0 && (
                  <>
                    <div style={{ marginTop: 15, textAlign: "right" }}>
                      {" "}
                      <Button
                        style={{
                          backgroundColor: "#E2B357",
                          color: "white",
                          marginRight: "15px",
                          width: "100px",
                        }}
                        onClick={handleCancel}
                      >
                        Hủy
                      </Button>
                      <Button
                        style={{
                          backgroundColor: "rgb(61, 139, 227)",
                          color: "white",

                          width: "100px",
                        }}
                        onClick={update}
                      >
                        Cập nhật
                      </Button>
                    </div>
                  </>
                )}
                <Row style={{ marginTop: 10 }}>
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
                    <span className="title-main">Nhận xét:</span>
                    <TextArea
                      style={{ marginTop: "10px" }}
                      rows={10}
                      value={descriptionsNote}
                      readOnly
                    />
                  </Col>
                  <Col span={8}>
                    {" "}
                    <span className="title-main">Bài tập về nhà:</span>
                    <TextArea
                      style={{ marginTop: "10px" }}
                      rows={10}
                      value={descriptionsHomeWork}
                      readOnly
                    />
                  </Col>
                  <Col span={8}>
                    {" "}
                    <span className="title-main">Báo cáo:</span>
                    <TextArea
                      style={{ marginTop: "10px" }}
                      rows={10}
                      value={descriptionsReport}
                      readOnly
                    />
                  </Col>{" "}
                </Row>
                <Row style={{ marginTop: 8 }}>
                  <div style={{ marginBottom: 8 }}>
                    {" "}
                    <span className="title-main">Template mẫu báo cáo:</span>
                  </div>{" "}
                  <TextArea rows={5} value={template.descriptions} />
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
