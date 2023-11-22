import { Modal, Row, Col, Input, Button, message } from "antd";
import { useEffect, useState } from "react";
import { AdMeetingPeriodConfigurationAPI } from "../../../../../api/admin/AdMeetingPeriodConfigurationAPI";
import { AddMeetingPeriodConfiguration } from "../../../../../app/admin/AdMeetingPeriodConfiguration.reducer";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../../app/hook";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../../app/common/Loading.reducer";

const ModalCreateMeetingPeriod = ({ visible, onCancel }) => {
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
  }, [visible]);

  const create = () => {
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

    if (startHour === null || startMinute === null) {
      setErrorStartHour("Giờ bắt đầu không được để trống");
      setErrorStartMinute("Phút bắt đầu không được để trống");
      check++;
    } else {
      setErrorStartHour("");
      setErrorStartMinute("");
    }

    if (endHour === null || endMinute === null) {
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
    }
    if (
      parseInt(startHour) === parseInt(endHour) &&
      parseInt(startMinute) >= parseInt(endMinute)
    ) {
      setErrorStartMinute("Phút bắt đầu phải nhỏ hơn phút kết thúc");
      setErrorEndMinute("Phút kết thúc phải lớn hơn phút bắt đầu");
      check++;
    }
    if (check === 0) {
      dispatch(SetLoadingTrue());
      let obj = {
        name: name,
        startHour: startHour,
        startMinute: startMinute,
        endHour: endHour,
        endMinute: endMinute,
      };

      AdMeetingPeriodConfigurationAPI.addMeetingPeriod(obj).then(
        (response) => {
          message.success("Thêm ca thành công !");
          dispatch(AddMeetingPeriodConfiguration(response.data.data));
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
        className="modal_show_detail_create_level"
      >
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Thêm mới Ca</span>
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
                  const newValue = Math.min(
                    Math.max(0, parseInt(e.target.value) || 0),
                    23
                  );
                  setStartHour(newValue);
                }}
                type="number"
                min={0}
                max={23}
              />
              <span style={{ color: "red" }}>{errorStartHour}</span>
            </Col>
            <Col span={12} style={{ padding: 5 }}>
              <span style={{ color: "red" }}>(*) </span>
              <span>Giờ kết thúc:</span> <br />
              <Input
                value={endHour}
                onChange={(e) => {
                  const newValue = Math.min(
                    Math.max(0, parseInt(e.target.value) || 0),
                    24
                  );
                  setEndHour(newValue);
                }}
                type="number"
                min={0}
                max={24}
              />
              <span style={{ color: "red" }}>{errorEndHour}</span>
            </Col>
            <Col span={12} style={{ padding: 5 }}>
              <span style={{ color: "red" }}>(*) </span>{" "}
              <span>Phút bắt đầu:</span> <br />
              <Input
                value={startMinute}
                onChange={(e) => {
                  const newValue = Math.min(
                    Math.max(0, parseInt(e.target.value) || 0),
                    59
                  );
                  setStartMinute(newValue);
                }}
                type="number"
                min={0}
                max={59}
              />
              <span style={{ color: "red" }}>{errorStartMinute}</span>
            </Col>
            <Col span={12} style={{ padding: 5 }}>
              <span style={{ color: "red" }}>(*) </span>{" "}
              <span>Phút kết thúc:</span> <br />
              <Input
                value={endMinute}
                onChange={(e) => {
                  const newValue = Math.min(
                    Math.max(0, parseInt(e.target.value) || 0),
                    59
                  );
                  setEndMinute(newValue);
                }}
                type="number"
                min={0}
                max={59}
              />
              <span style={{ color: "red" }}>{errorEndMinute}</span>
            </Col>
          </Row>
        </div>
        <div style={{ textAlign: "right" }}>
          <div style={{ paddingTop: "15px" }}>
            <Button
              className="btn_filter"
              onClick={create}
              style={{ marginRight: "15px", backgroundColor: "#E2B357" }}
            >
              <span> Thêm</span>
            </Button>
            <Button className="btn_clean" onClick={onCancel}>
              <span>Hủy</span>
            </Button>
          </div>
        </div>
      </Modal>
    </>
  );
};

export default ModalCreateMeetingPeriod;
