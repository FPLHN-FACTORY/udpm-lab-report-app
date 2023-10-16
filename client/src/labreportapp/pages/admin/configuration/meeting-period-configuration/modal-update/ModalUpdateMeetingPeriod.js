import { Modal, Row, Col, Input, Button } from "antd";
import { useEffect, useState } from "react";
import { AdMeetingPeriodConfigurationAPI } from "../../../../../api/admin/AdMeetingPeriodConfigurationAPI";
import { useAppDispatch } from "../../../../../app/hook";
import {
    UpdateMeetingPeriodConfiguration
  } from "../../../../../app/admin/AdMeetingPeriodConfiguration.reducer";
  import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const ModalUpdateMeetingPeriod = ({ visible, onCancel, meetingPeriod }) => {
  const [name, setName] = useState("");
  const [startHour, setStartHour] = useState("");
  const [startMinute, setStartMinute] = useState("");
  const [endHour, setEndHour] = useState("");
  const [endMinute, setEndMinute] = useState("");
  const [errorName, setErrorName] = useState("");
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
        setErrorName();
      };
    }
  }, [meetingPeriod]);

  const update = () => {
    let check = 0;
    if (name.trim() === "") {
      setErrorName("Tên Ca không được để trống");
      check++;
    } else {
      setErrorName("");
    }
    if (name.trim().length > 500) {
      setErrorName("Tên Ca không quá 500 ký tự");
      check++;
    } else {
      setErrorName("");
    }

    if (check === 0) {
      let obj = {
        id: meetingPeriod.id,
        name: name,
        startHour: startHour,
        startMinute: startMinute,
        endHour: endHour,
        endMinute: endMinute,
      };

      AdMeetingPeriodConfigurationAPI.updateMeetingPeriod(obj, meetingPeriod.id).then(
        (response) => {
          toast.success("Cập nhật thành công!");
          dispatch(UpdateMeetingPeriodConfiguration(response.data.data));
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
          <span style={{ fontSize: "18px" }}>Cập nhật Level</span>
        </div>
        <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
        <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Tên Ca:</span> <br />
              <Input
                value={name}
                onChange={(e) => {
                  setName(e.target.value);
                }}
                type="text"
              />
              <span style={{ color: "red" }}>{errorName}</span>
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Giờ bắt đầu:</span> <br />
              <Input
                value={startHour}
                onChange={(e) => {
                  setStartHour(e.target.value);
                }}
                type="text"
              />
              <span style={{ color: "red" }}>{errorName}</span>
            </Col>
            <Col span={24}>
              <span>Giờ kết thúc:</span> <br />
              <Input
                value={endHour}
                onChange={(e) => {
                  setEndHour(e.target.value);
                }}
                type="text"
              />
              <span style={{ color: "red" }}>{errorName}</span>
            </Col>
            <Col span={24}>
              <span>Phút bắt đầu:</span> <br />
              <Input
                value={startMinute}
                onChange={(e) => {
                  setStartMinute(e.target.value);
                }}
                type="text"
              />
              <span style={{ color: "red" }}>{errorName}</span>
            </Col>
            <Col span={24}>
              <span>Phút kết thúc:</span> <br />
              <Input
                value={endMinute}
                onChange={(e) => {
                  setEndMinute(e.target.value);
                }}
                type="text"
              />
              <span style={{ color: "red" }}>{errorName}</span>
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
