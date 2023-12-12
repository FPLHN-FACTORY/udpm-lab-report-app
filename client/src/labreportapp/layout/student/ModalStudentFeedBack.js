import React, { useEffect, useState } from "react";
import { Modal, Button, Input, message, Tabs, Switch } from "antd";
import "react-toastify/dist/ReactToastify.css";
import "./style-modal-student-feedback.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheckDouble } from "@fortawesome/free-solid-svg-icons";
import { StFeedBackAPI } from "../../api/student/StFeedBackAPI";
import { convertHourAndMinuteToString } from "../../helper/util.helper";
import { convertLongToDate } from "../../helper/convertDate";
import LoadingIndicatorNoOverlay from "../../helper/loadingNoOverlay";
import { arrayQuestion } from "../../helper/util.helper";
import TabPane from "antd/es/tabs/TabPane";
import { Rate } from "antd";

const desc = ["1 điểm", " 2 điểm", "3 điểm", "4 điểm", "5 điểm"];

const ModalStudentFeedBack = ({ visible, setVisible }) => {
  const [isLoading, setIsLoading] = useState(false);
  const [semester, setSemester] = useState(null);
  const [listFeedBack, setListFeedBack] = useState([]);
  const [isBothTextAreasFilled, setIsBothTextAreasFilled] = useState(false);
  const initialRatings = listFeedBack.map(() => 0);
  const [ratings, setRatings] = useState(initialRatings);

  const handleFeedbackChange = (index, value) => {
    const updatedFeedback = [...listFeedBack];
    updatedFeedback[index].descriptions = value;
    setListFeedBack(updatedFeedback);
  };

  const handlePointChange = (index, indexRadio, value) => {
    const updatedFeedback = [...listFeedBack];
    if (indexRadio === 0) {
      updatedFeedback[index].rateQuestion1 = value;
    } else if (indexRadio === 1) {
      updatedFeedback[index].rateQuestion2 = value;
    } else if (indexRadio === 2) {
      updatedFeedback[index].rateQuestion3 = value;
    } else if (indexRadio === 3) {
      updatedFeedback[index].rateQuestion4 = value;
    } else if (indexRadio === 4) {
      updatedFeedback[index].rateQuestion5 = value;
    }
    setListFeedBack(updatedFeedback);
  };

  const handleShowChange = (index, checked) => {
    const updatedFeedback = [...listFeedBack];
    updatedFeedback[index].status = checked;
  };

  const loadDataClassFeedback = () => {
    StFeedBackAPI.getAllClassFeedback().then((response) => {
      const newData = response.data.data.map((item) => ({
        ...item,
        classId: item.id,
        rateQuestion1: 0,
        rateQuestion2: 0,
        rateQuestion3: 0,
        rateQuestion4: 0,
        rateQuestion5: 0,
        status: false,
        descriptions: "",
      }));
      setListFeedBack(newData);
    });
  };

  const loadDataSemester = () => {
    StFeedBackAPI.getSemesterCurrent().then((response) => {
      setSemester(response.data.data);
    });
  };

  useEffect(() => {
    const allTextAreasFilled = listFeedBack.every(
      (feedback) =>
        feedback.rateQuestion1 !== 0 &&
        feedback.rateQuestion2 !== 0 &&
        feedback.rateQuestion3 !== 0 &&
        feedback.rateQuestion4 !== 0 &&
        feedback.rateQuestion5 !== 0 &&
        feedback.status !== null
    );
    setIsBothTextAreasFilled(allTextAreasFilled);
  }, [listFeedBack]);

  const createFeedback = () => {
    if (!isBothTextAreasFilled) {
      message.warning("5 câu hỏi không được để trống vote sao !");
    } else {
      let obj = {
        listFeedBack: listFeedBack,
      };
      setIsLoading(true);
      StFeedBackAPI.createFeedBack(obj).then((response) => {
        message.success("Feedback thành công !");
        setIsLoading(false);
        setVisible(false);
      });
    }
  };

  useEffect(() => {
    if (visible) {
      loadDataClassFeedback();
      loadDataSemester();
    }
  }, [visible]);

  return (
    <div className="overlay">
      {isLoading && <LoadingIndicatorNoOverlay />}
      <Modal
        open={visible}
        width={1250}
        onCancel={null}
        footer={null}
        style={{ top: "53px" }}
        className="modal_show_detail_project_student_feedback"
      >
        <div>
          <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
            <span style={{ fontSize: "18px" }}>Feedback</span>
          </div>
          <div
            style={{
              marginTop: "15px",
              borderBottom: "1px solid gray",
              paddingBottom: "15px",
              minHeight: 400,
            }}
          >
            <div>
              <span style={{ fontSize: 16 }}>
                Danh sách lớp học của bạn trong học kỳ{" "}
                {semester != null ? semester.name : ""}:{" "}
              </span>
              <span style={{ color: "red", float: "right" }}>
                (*)Vui lòng điền và chọn đầy đủ feedback của các lớp
              </span>
            </div>
            <Tabs animated={true} hideAdd>
              {listFeedBack.map((tab, index) => (
                <TabPane
                  closable={false}
                  key={tab.id}
                  tab={
                    <span style={{ fontWeight: "500", fontSize: "20px" }}>
                      {tab.code}
                    </span>
                  }
                >
                  <div className="info-team" style={{ marginTop: 10 }}>
                    <span className="info-heading">Thông tin lớp học:</span>
                    <div className="group-info">
                      <span
                        className="group-info-item"
                        style={{ marginTop: "10px", marginBottom: "15px" }}
                      >
                        Mã lớp: {tab != null ? tab.code : ""}
                      </span>

                      <span
                        className="group-info-item"
                        style={{ marginTop: "13px", marginBottom: "15px" }}
                      >
                        Thời gian bắt đầu:{" "}
                        {tab != null ? convertLongToDate(tab.startTime) : ""}
                      </span>
                      <span
                        className="group-info-item"
                        style={{ marginTop: "13px", marginBottom: "15px" }}
                      >
                        Ca học:{" "}
                        {tab != null ? (
                          <span>
                            {" "}
                            {tab.classPeriod} (
                            {convertHourAndMinuteToString(
                              tab.startHour,
                              tab.startMinute,
                              tab.endHour,
                              tab.endMinute
                            )}
                            )
                          </span>
                        ) : (
                          ""
                        )}
                      </span>
                      <span
                        className="group-info-item"
                        style={{ marginTop: "13px", marginBottom: "15px" }}
                      >
                        Level: {tab != null ? tab.level : ""}
                      </span>
                      <span
                        className="group-info-item"
                        style={{ marginTop: "13px", marginBottom: "15px" }}
                      >
                        Hoạt động: {tab != null ? tab.nameActivity : ""}
                      </span>
                      <span
                        className="group-info-item"
                        style={{ marginTop: "13px", marginBottom: "15px" }}
                      >
                        Giảng viên:{" "}
                        {tab != null &&
                        tab.nameTeacher != null &&
                        tab.userNameTeacher != null
                          ? tab.nameTeacher + " - " + tab.userNameTeacher
                          : "Chưa có giảng viên"}
                      </span>
                    </div>
                    <div style={{ padding: "10px 20px 10px 20px" }}>
                      <span style={{ paddingRight: "10px" }}>
                        Bạn có muốn công khai danh tính không ?
                      </span>
                      <Switch
                        checkedChildren="Công khai"
                        unCheckedChildren="Bảo mật"
                        onChange={(e) => handleShowChange(index, e)}
                      />
                      {arrayQuestion.map((question, indexRadio) => (
                        <div key={indexRadio} style={{ marginTop: 5 }}>
                          <span>{question}</span>
                          <br />
                          <span>
                            <Rate
                              tooltips={desc}
                              onChange={(e) =>
                                handlePointChange(index, indexRadio, e)
                              }
                              value={ratings[index]}
                            />
                          </span>
                        </div>
                      ))}
                      <span style={{ fontSize: 15 }}>
                        Góp ý thêm giúp giảng viên cải thiện :
                      </span>
                      <br />
                      <Input.TextArea
                        rows={3}
                        value={listFeedBack[index].descriptions}
                        onChange={(e) =>
                          handleFeedbackChange(index, e.target.value)
                        }
                        placeholder="Nhập góp ý"
                      />
                    </div>
                  </div>
                </TabPane>
              ))}
            </Tabs>
          </div>
          <div style={{ textAlign: "right" }}>
            <div style={{ paddingTop: "15px" }}>
              <Button
                style={{
                  marginRight: "5px",
                  backgroundColor: "rgb(61, 139, 227)",
                  color: "white",
                }}
                onClick={createFeedback}
              >
                {" "}
                <FontAwesomeIcon
                  icon={faCheckDouble}
                  style={{ marginRight: "5px" }}
                />
                Hoàn thành
              </Button>
            </div>
          </div>
        </div>
      </Modal>
    </div>
  );
};
export default ModalStudentFeedBack;
