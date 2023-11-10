import { Modal, Row, Col, Input, Button, message } from "antd";
import { useEffect, useState } from "react";
import { AdSemesterAPI } from "../../../../api/admin/AdSemesterAPI";
import moment from "moment";
import { useAppSelector } from "../../../../app/hook";
import { UpdateSemester } from "../../../../app/admin/AdSemester.reducer";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../app/hook";

const ModalUpdateSemester = ({ visible, onCancel, semester }) => {
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("");
  const [startTime, setStartTime] = useState("");
  const [errorStartTime, setErrorStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [errorEndTime, setErrorEndTime] = useState("");
  const [startTimeStudent, setStartTimeStudent] = useState("");
  const [errorStartTimeStudent, setErrorStartTimeStudent] = useState("");
  const [endTimeStudent, setEndTimeStudent] = useState("");
  const [errorEndTimeStudent, setErrorEndTimeStudent] = useState("");
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (semester !== null) {
      setName(semester.name);
      setStartTime(semester.startTime);
      setEndTime(semester.endTime);
      setStartTimeStudent(semester.startTimeStudent);
      setEndTimeStudent(semester.endTimeStudent);

      return () => {
        setName("");
        setStartTime("");
        setEndTime("");
        setErrorEndTime("");
        setErrorStartTime("");
        setStartTimeStudent("");
        setEndTimeStudent("");
        setErrorEndTimeStudent("");
        setErrorStartTimeStudent("");
        setErrorName();
      };
    }
  }, [semester]);

  const create = () => {
    let check = 0;
    if (name.trim() === "") {
      setErrorName("Tên học kỳ không được để trống");
      check++;
    } else {
      setErrorName("");
      if (name.trim().length > 500) {
        setErrorName("Tên học kỳ không quá 500 ký tự");
        check++;
      } else {
        setErrorName("");
      }
    }

    if (startTime === "") {
      setErrorStartTime("Thời gian bắt đầu không được để trống");
      check++;
    } else {
      setErrorStartTime("");
      if (new Date(startTime).getTime() > new Date(endTime).getTime()) {
        setErrorStartTime(
          "Thời gian bắt đầu không được lớn hơn thời gian kết thúc"
        );
        check++;
      } else {
        setErrorStartTime("");
      }
    }
    if (endTime === "") {
      setErrorEndTime("Thời gian kết thúc không được để trống");
      check++;
    } else {
      setErrorEndTime("");
    }

    if (startTimeStudent === "") {
      setErrorStartTimeStudent(
        "Thời gian sinh viên bắt đầu không được để trống"
      );
      check++;
    } else {
      setErrorStartTimeStudent("");
      if (
        new Date(startTimeStudent).getTime() >
        new Date(endTimeStudent).getTime()
      ) {
        setErrorStartTimeStudent(
          "Thời gian sinh viên bắt đầu không được lớn hơn thời gian sinh viên kết thúc"
        );
        check++;
      } else {
        setErrorStartTimeStudent("");
      }
      if (
        new Date(startTimeStudent).getTime() < new Date(startTime).getTime()
      ) {
        setErrorStartTimeStudent(
          "Thời gian sinh viên bắt đầu không được nhỏ hơn thời gian học kỳ bắt đầu"
        );
        check++;
      } else {
        setErrorStartTimeStudent("");
      }
    }
    if (endTimeStudent === "") {
      setErrorEndTimeStudent(
        "Thời gian sinh viên kết thúc không được để trống"
      );
      check++;
    } else {
      setErrorEndTimeStudent("");
      if (new Date(endTimeStudent).getTime() > new Date(endTime).getTime()) {
        setErrorEndTimeStudent(
          "Thời gian sinh viên kết thúc không được lớn hơn thời gian học kỳ kết thúc"
        );
        check++;
      } else {
        setErrorEndTimeStudent("");
      }
    }

    if (check === 0) {
      let obj = {
        id: semester.id,
        name: name,
        startTime: moment(startTime).valueOf(),
        endTime: moment(endTime).valueOf(),
        startTimeStudent: moment(startTimeStudent).valueOf(),
        endTimeStudent: moment(endTimeStudent).valueOf(),
      };

      AdSemesterAPI.updateSemester(obj, semester.id).then(
        (response) => {
          message.success("Cập nhật thành công!");
          dispatch(UpdateSemester(response.data.data));
          onCancel();
        },
        (error) => {}
      );
    }
  };

  return (
    <>
      <Modal
        visible={visible}
        onCancel={onCancel}
        width={750}
        footer={null}
        className="modal_show_detail_update_semester"
      >
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Cập nhật học kỳ</span>
        </div>
        <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span style={{ color: "red" }}>(*) </span>{" "}
              <span>Tên học kỳ:</span> <br />
              <Input
                value={name}
                onChange={(e) => {
                  setName(e.target.value);
                }}
                type="text"
              />
              <span className="error">{errorName}</span>
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={12}>
              <span style={{ color: "red" }}>(*) </span>
              <span>Thời gian bắt đầu:</span> <br />
              <Input
                value={moment(startTime).format("YYYY-MM-DD")}
                onChange={(e) => {
                  setStartTime(e.target.value);
                }}
                type="date"
              />
              <span className="error">{errorStartTime}</span>
            </Col>
            <Col span={12}>
              <span style={{ color: "red" }}>(*) </span>{" "}
              <span>Thời gian kết thúc:</span> <br />
              <Input
                value={moment(endTime).format("YYYY-MM-DD")}
                onChange={(e) => {
                  setEndTime(e.target.value);
                }}
                type="date"
              />
              <span className="error">{errorEndTime}</span>
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={12}>
              <span style={{ color: "red" }}>(*) </span>{" "}
              <span>Thời gian sinh viên bắt đầu:</span> <br />
              <Input
                value={moment(startTimeStudent).format("YYYY-MM-DD")}
                onChange={(e) => {
                  setStartTimeStudent(e.target.value);
                }}
                type="date"
              />
              <span className="error">{errorStartTimeStudent}</span>
            </Col>
            <Col span={12}>
              <span style={{ color: "red" }}>(*) </span>{" "}
              <span>Thời gian sinh viên kết thúc:</span> <br />
              <Input
                value={moment(endTimeStudent).format("YYYY-MM-DD")}
                onChange={(e) => {
                  setEndTimeStudent(e.target.value);
                }}
                type="date"
              />
              <span className="error">{errorEndTimeStudent}</span>
            </Col>
          </Row>
        </div>
        <div style={{ textAlign: "right" }}>
          <div style={{ paddingTop: "15px" }}>
            <Button
              style={{
                marginRight: "5px",
                backgroundColor: "rgb(61, 139, 227)",
                color: "white",
              }}
              onClick={create}
            >
              Cập nhật
            </Button>
            <Button
              style={{
                backgroundColor: "red",
                color: "white",
              }}
              onClick={onCancel}
            >
              Hủy
            </Button>
          </div>
        </div>
      </Modal>
    </>
  );
};

export default ModalUpdateSemester;
