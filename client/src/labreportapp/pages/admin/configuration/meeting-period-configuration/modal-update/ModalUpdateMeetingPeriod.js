import { Modal, Row, Col, Input, Button, message } from "antd";
import { useEffect, useState } from "react";
import { AdMeetingPeriodConfigurationAPI } from "../../../../../api/admin/AdMeetingPeriodConfigurationAPI";
import { useAppDispatch } from "../../../../../app/hook";
import { UpdateMeetingPeriodConfiguration } from "../../../../../app/admin/AdMeetingPeriodConfiguration.reducer";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../../app/common/Loading.reducer";

const ModalUpdateMeetingPeriod = ({ visible, onCancel, meetingPeriod }) => {
  const [name, setName] = useState("");
  const [startHour, setStartHour] = useState(0);
  const [startMinute, setStartMinute] = useState(0);
  const [endHour, setEndHour] = useState(0);
  const [endMinute, setEndMinute] = useState(0);
  const [errorName, setErrorName] = useState("");
  const [errorStartHour, setErrorStartHour] = useState("");
  const [errorEndHour, setErrorEndHour] = useState("");
  const [errorStartMinute, setErrorStartMinute] = useState("");
  const [errorEndMinute, setErrorEndMinute] = useState("");
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (meetingPeriod !== null) {
      setName(meetingPeriod.name);
      setStartHour(meetingPeriod.startHour);
      setStartMinute(meetingPeriod.startMinute);
      setEndHour(meetingPeriod.endHour);
      setEndMinute(meetingPeriod.endMinute);

      return () => {
        setName("");
        setStartHour(0);
        setStartMinute(0);
        setEndHour(0);
        setEndMinute(0);
        setErrorName("");
        setErrorStartHour("");
        setErrorEndHour("");
        setErrorStartMinute("");
        setErrorEndMinute("");
      };
    }
  }, [meetingPeriod, visible]);

  const update = () => {
    let check = 0;
    if (name.trim() === "") {
      setErrorName("Tên Ca không được để trống");
      check++;
    } else {
      if (name.trim().length > 500) {
        setErrorName("Tên Ca không quá 500 ký tự");
        check++;
      } else {
        setErrorName("");
      }
    }

    if (startHour === "" || startMinute === "") {
      setErrorStartHour("Giờ bắt đầu không được để trống");
      setErrorStartMinute("Phút bắt đầu không được để trống");
      check++;
    } else {
      setErrorStartHour("");
      setErrorStartMinute("");
    }

    if (endHour === "" || endMinute === "") {
      setErrorEndHour("Giờ kết thúc không được để trống");
      setErrorEndMinute("Phút kết thúc không được để trống");
      check++;
    } else {
      setErrorEndHour("");
      setErrorEndMinute("");
    }

    if (parseInt(startHour) > parseInt(endHour)) {
      setErrorStartHour("Giờ bắt đầu phải nhỏ hơn giờ kết thúc");
      setErrorEndHour("Giờ kết thúc phải lớn hơn giờ bắt đầu");
      check++;
    } else if (
      parseInt(startHour) === parseInt(endHour) &&
      parseInt(startMinute) >= parseInt(endMinute)
    ) {
      setErrorStartMinute("Phút bắt đầu phải nhỏ hơn phút kết thúc");
      setErrorEndMinute("Phút kết thúc phải lớn hơn phút bắt đầu");
      check++;
    } else {
      setErrorStartHour("");
      setErrorEndHour("");
      setErrorStartMinute("");
      setErrorEndMinute("");
    }

    if (parseInt(startHour) >= 24) {
      setErrorStartHour("Giờ bắt đầu phải nhỏ hơn 24 giờ");
      check++;
    } else if (parseInt(startMinute) >= 60) {
      setErrorStartMinute("Phút bắt đầu phải nhỏ hơn 60 phút");
      check++;
    } else {
      setErrorStartHour("");
      setErrorStartMinute("");
    }

    if (parseInt(endHour) >= 24) {
      setErrorEndHour("Giờ kết thúc phải nhỏ hơn 24 giờ");
      check++;
    } else if (parseInt(endMinute) >= 60) {
      setErrorEndMinute("Phút kết thúc phải nhỏ hơn 60 phút");
      check++;
    } else {
      setErrorEndHour("");
      setErrorEndMinute("");
    }

    if (!Number.isInteger(Number(startHour)) || Number(startHour) < 0) {
      setErrorStartHour("Giờ bắt đầu phải là số nguyên dương");
      check++;
    } else if (
      !Number.isInteger(Number(startMinute)) ||
      Number(startMinute) < 0
    ) {
      setErrorStartMinute("Phút bắt đầu phải là số nguyên dương");
      check++;
    } else {
      setErrorStartHour("");
      setErrorStartMinute("");
    }

    if (!Number.isInteger(Number(endHour)) || Number(endHour) < 0) {
      setErrorEndHour("Giờ kết thúc phải là số nguyên dương");
      check++;
    } else if (!Number.isInteger(Number(endMinute)) || Number(endMinute) < 0) {
      setErrorEndMinute("Phút kết thúc phải là số nguyên dương");
      check++;
    } else {
      setErrorEndHour("");
      setErrorEndMinute("");
    }

    if (check === 0) {
      dispatch(SetLoadingTrue());
      let obj = {
        id: meetingPeriod.id,
        name: name,
        startHour: startHour,
        startMinute: startMinute,
        endHour: endHour,
        endMinute: endMinute,
      };

      AdMeetingPeriodConfigurationAPI.updateMeetingPeriod(
        obj,
        meetingPeriod.id
      ).then(
        (response) => {
          message.success("Cập nhật thành công !");
          dispatch(UpdateMeetingPeriodConfiguration(response.data.data));
          dispatch(SetLoadingFalse());
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
        className="modal_show_detail_update_level"
      >
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Cập nhật Ca</span>
        </div>
        <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
          <Row style={{ marginBottom: "15px" }}>
            <Col span={24} style={{ padding: 5 }}>
              <span style={{ color: "red" }}>(*) </span>
              <span>Tên Ca:</span> <br />
              <Input
                value={name}
                placeholder="Nhập tên ca"
                onChange={(e) => {
                  setName(e.target.value);
                }}
                type="text"
              />
              <span style={{ color: "red" }}>{errorName}</span>
            </Col>
            <Col span={12} style={{ padding: 5 }}>
              <span style={{ color: "red" }}>(*) </span>{" "}
              <span>Giờ bắt đầu:</span> <br />
              <Input
                value={startHour}
                onChange={(e) => {
                  setStartHour(e.target.value);
                }}
                type="number"
              />
              <span style={{ color: "red" }}>{errorStartHour}</span>
            </Col>
            <Col span={12} style={{ padding: 5 }}>
              <span style={{ color: "red" }}>(*) </span>
              <span>Giờ kết thúc:</span> <br />
              <Input
                value={endHour}
                onChange={(e) => {
                  setEndHour(e.target.value);
                }}
                type="number"
              />
              <span style={{ color: "red" }}>{errorEndHour}</span>
            </Col>
            <Col span={12} style={{ padding: 5 }}>
              <span style={{ color: "red" }}>(*) </span>{" "}
              <span>Phút bắt đầu:</span> <br />
              <Input
                value={startMinute}
                onChange={(e) => {
                  setStartMinute(e.target.value);
                }}
                type="number"
              />
              <span style={{ color: "red" }}>{errorStartMinute}</span>
            </Col>
            <Col span={12} style={{ padding: 5 }}>
              <span style={{ color: "red" }}>(*) </span>{" "}
              <span>Phút kết thúc:</span> <br />
              <Input
                value={endMinute}
                onChange={(e) => {
                  setEndMinute(e.target.value);
                }}
                type="number"
              />
              <span style={{ color: "red" }}>{errorEndMinute}</span>
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
              onClick={update}
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
export default ModalUpdateMeetingPeriod;
