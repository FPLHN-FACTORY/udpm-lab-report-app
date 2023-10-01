import React, { useEffect, useState } from "react";
import { Modal, Button, Select, Input } from "antd";
import "react-toastify/dist/ReactToastify.css";
import "./style-modal-student-feedback.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheckDouble } from "@fortawesome/free-solid-svg-icons";
import { StFeedBackAPI } from "../../api/student/StFeedBackAPI";
import { convertMeetingPeriodToNumber } from "../../helper/util.helper";
import { convertLongToDate } from "../../helper/convertDate";
import LoadingIndicatorNoOverlay from "../../helper/loadingNoOverlay";
import { toast } from "react-toastify";
import { sinhVienCurrent } from "../../helper/inForUser";

const { Option } = Select;

const ModalStudentFeedBack = ({ visible, setVisible }) => {
  const [listClassFeedBack, setListClassFeedBack] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [semester, setSemester] = useState(null);
  const [listFeedBack, setListFeedBack] = useState([]);
  const [isBothTextAreasFilled, setIsBothTextAreasFilled] = useState(false);
  useEffect(() => {
    const allTextAreasFilled = listFeedBack.every(
      (feedback) => feedback.descriptions.trim() !== ""
    );
    setIsBothTextAreasFilled(allTextAreasFilled);
  }, [listFeedBack]);

  const handleFeedbackChange = (index, value) => {
    const updatedFeedback = [...listFeedBack];
    updatedFeedback[index].descriptions = value;
    setListFeedBack(updatedFeedback);
  };
  const loadDataClassFeedback = () => {
    StFeedBackAPI.getAllClassFeedback().then((response) => {
      setListClassFeedBack(response.data.data);
      const newData = response.data.data.map((item) => ({
        classId: item.id,
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

  const createFeedback = () => {
    if (!isBothTextAreasFilled) {
      toast.error("Hãy nhập đày đủ feedback của các lớp");
      return;
    }
    let obj = {
      studentId: sinhVienCurrent.id,
      listFeedBack: listFeedBack,
    };
    setIsLoading(true);
    StFeedBackAPI.createFeedBack(obj).then((response) => {
      toast.success("FeedBack thành công");
      setIsLoading(false);
      setVisible(false);
    });
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
        visible={visible}
        width={1050}
        onCancel={null}
        footer={null}
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
            </div>
            <div>
              {listClassFeedBack.map((item, index) => (
                <div className="info-team" style={{ marginTop: 10 }}>
                  <span className="info-heading">Thông tin lớp học:</span>
                  <div className="group-info">
                    <span
                      className="group-info-item"
                      style={{ marginTop: "10px", marginBottom: "15px" }}
                    >
                      Mã lớp: {item != null ? item.code : ""}
                    </span>

                    <span
                      className="group-info-item"
                      style={{ marginTop: "13px", marginBottom: "15px" }}
                    >
                      Thời gian bắt đầu:{" "}
                      {item != null ? convertLongToDate(item.startTime) : ""}
                    </span>
                    <span
                      className="group-info-item"
                      style={{ marginTop: "13px", marginBottom: "15px" }}
                    >
                      Ca học:{" "}
                      {item != null
                        ? "Ca " + parseInt(item.classPeriod + 1)
                        : ""}
                    </span>
                    <span
                      className="group-info-item"
                      style={{ marginTop: "13px", marginBottom: "15px" }}
                    >
                      Level: {item != null ? item.level : ""}
                    </span>
                    <span
                      className="group-info-item"
                      style={{ marginTop: "13px", marginBottom: "15px" }}
                    >
                      Hoạt động: {item != null ? item.nameActivity : ""}
                    </span>
                    <span
                      className="group-info-item"
                      style={{ marginTop: "13px", marginBottom: "15px" }}
                    >
                      Giảng viên:{" "}
                      {item != null
                        ? item.nameTeacher + " - " + item.userNameTeacher
                        : ""}
                    </span>
                  </div>
                  <div style={{ marginTop: 5 }}>
                    <span style={{ fontSize: 15 }}>Nhập feedback:</span> <br />
                    <Input.TextArea
                      rows={8}
                      value={listFeedBack[index].descriptions}
                      onChange={(e) =>
                        handleFeedbackChange(index, e.target.value)
                      }
                      placeholder="Nhập feedback"
                    ></Input.TextArea>
                  </div>
                </div>
              ))}
            </div>
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
