import { Modal, Row, Col, Input, Button, message, DatePicker } from "antd";
import { useEffect, useState } from "react";
import { AdSemesterAPI } from "../../../../api/admin/AdSemesterAPI";
import { UpdateSemester } from "../../../../app/admin/AdSemester.reducer";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../app/hook";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../app/common/Loading.reducer";
import locale from "antd/es/date-picker/locale/vi_VN";
import dayjs from "dayjs";
import "dayjs/locale/vi";
const { RangePicker } = DatePicker;

const ModalUpdateSemester = ({ visible, onCancel, semester }) => {
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("");
  const [startTime, setStartTime] = useState("");
  const [errorTimeSemester, setErrorTimeSemester] = useState("");
  const [endTime, setEndTime] = useState("");
  const [startTimeStudent, setStartTimeStudent] = useState("");
  const [errorTimeStudent, setErrorTimeStudent] = useState("");
  const [endTimeStudent, setEndTimeStudent] = useState("");
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (semester !== null) {
      setName(semester.name);
      setStartTime(semester.startTime);
      setEndTime(semester.endTime);
      setStartTimeStudent(semester.startTimeStudent);
      setEndTimeStudent(semester.endTimeStudent);
    }
    return () => {
      setName("");
      setStartTime("");
      setEndTime("");
      setErrorTimeSemester("");
      setErrorName("");
      setStartTimeStudent("");
      setErrorTimeStudent("");
      setEndTimeStudent("");
    };
  }, [semester, visible]);

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

    if (startTime === "" || startTime === null) {
      setErrorTimeSemester("Thời gian không được để trống");
      check++;
    } else {
      setErrorTimeSemester("");
    }

    if (startTimeStudent === "" || startTimeStudent === null) {
      setErrorTimeStudent("Thời gian sinh viên không được để trống");
      check++;
    } else if (startTimeStudent !== "") {
      setErrorTimeStudent("");
      if (startTimeStudent < startTime) {
        setErrorTimeStudent(
          "Thời gian sinh viên bắt đầu không được bắt đầu trước thời gian học kỳ bắt đầu"
        );
        check++;
      }
      if (endTimeStudent > endTime) {
        setErrorTimeStudent(
          "Thời gian sinh viên kết thúc không được bắt đầu sau thời gian học kỳ kết thúc"
        );
        check++;
      }
    }

    if (check === 0) {
      dispatch(SetLoadingTrue());
      let obj = {
        id: semester.id,
        name: name,
        startTime: startTime,
        endTime: endTime,
        startTimeStudent: startTimeStudent,
        endTimeStudent: endTimeStudent,
      };

      AdSemesterAPI.updateSemester(obj, semester.id).then(
        (response) => {
          message.success("Cập nhật thành công !");
          dispatch(UpdateSemester(response.data.data));
          dispatch(SetLoadingFalse());
          onCancel();
        },
        (error) => {}
      );
    }
  };

  const handleDateChangeTimeStudent = (e) => {
    if (e != null) {
      setStartTimeStudent(
        dayjs(e[0]).set({ hour: 0, minute: 0, second: 0 }).valueOf()
      );
      setEndTimeStudent(
        dayjs(e[1]).set({ hour: 0, minute: 0, second: 0 }).valueOf()
      );
    } else {
      setStartTimeStudent(null);
      setEndTimeStudent(null);
    }
  };

  const handleDateChange = (e) => {
    if (e != null) {
      setStartTime(
        dayjs(e[0]).set({ hour: 0, minute: 0, second: 0 }).valueOf()
      );
      setEndTime(dayjs(e[1]).set({ hour: 0, minute: 0, second: 0 }).valueOf());
    } else {
      setStartTime(null);
      setEndTime(null);
    }
  };

  return (
    <>
      <Modal
        open={visible}
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
            <Col span={24}>
              <span className="notBlank">(*) </span>
              <span>Thời gian:</span> <br />
              <RangePicker
                locale={locale}
                style={{ width: "100%" }}
                format="DD-MM-YYYY"
                value={[
                  startTime ? dayjs(startTime) : null,
                  endTime ? dayjs(endTime) : null,
                ]}
                onChange={(e) => {
                  handleDateChange(e);
                }}
                placeholder={["Ngày bắt đầu", "Ngày kết thúc"]}
              />{" "}
              <span className="error">{errorTimeSemester}</span>
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span className="notBlank">(*) </span>
              <span>Thời gian sinh viên:</span> <br />
              <RangePicker
                locale={locale}
                style={{ width: "100%" }}
                format="DD-MM-YYYY"
                value={[
                  startTimeStudent ? dayjs(startTimeStudent) : null,
                  endTimeStudent ? dayjs(endTimeStudent) : null,
                ]}
                onChange={(e) => {
                  handleDateChangeTimeStudent(e);
                }}
                placeholder={["Ngày bắt đầu", "Ngày kết thúc"]}
              />{" "}
              <span className="error">{errorTimeStudent}</span>
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
