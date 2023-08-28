import { Button, Col, Input, Modal, Row, Select } from "antd";
import "./style-modal-create-meeting.css";
import { useState } from "react";
import moment from "moment";
import { useParams } from "react-router";
import { useEffect } from "react";
import { MeetingManagementAPI } from "../../../../../api/admin/meeting-management/MeetingManagementAPI";
import { useAppDispatch } from "../../../../../app/hook";
import { toast } from "react-toastify";
import { CreateMeeting } from "../../../../../app/admin/AdMeetingManagement.reducer";

const { Option } = Select;

const ModalCreateMeeting = ({ visible, onCancel }) => {
  const [meetingPeriod, setMeetingPeriod] = useState("0");
  const [typeMeeting, setTypeMeeting] = useState("0");
  const [name, setName] = useState("");
  const [meetingDate, setMeetingDate] = useState("");
  const [address, setAddress] = useState("");
  const [descriptions, setDescriptions] = useState("");
  const { id } = useParams();
  const [errorName, setErrorName] = useState("");
  const dispatch = useAppDispatch();
  const [errorMeetingDate, setErrorMeetingDate] = useState("");

  useEffect(() => {
    setName("");
    setTypeMeeting("0");
    setMeetingPeriod("0");
    setMeetingDate("");
    setAddress("");
    setDescriptions("");
    setErrorName("");
    setErrorMeetingDate("");
  }, [visible]);

  const create = () => {
    let check = 0;
    if (name === "") {
      setErrorName("Tên buổi học không được để trống");
      ++check;
    } else {
      setErrorName("");
    }
    if (meetingDate === "") {
      setErrorMeetingDate("Ngày diễn ra không được để trống");
      ++check;
    } else {
      setErrorMeetingDate("");
    }
    if (check === 0) {
      let obj = {
        name: name,
        meetingDate: moment(meetingDate, "YYYY-MM-DD").valueOf(),
        meetingPeriod: parseInt(meetingPeriod),
        typeMeeting: parseInt(typeMeeting),
        address: address,
        descriptions: descriptions,
        classId: id,
      };

      MeetingManagementAPI.createMeeting(obj).then((response) => {
        dispatch(CreateMeeting(response.data.data));
        toast.success("Thêm thành công");
        onCancel();
      });
    }
  };

  return (
    <>
      <Modal
        visible={visible}
        onCancel={onCancel}
        width={650}
        footer={null}
        className="modal_show_detail"
      >
        <div>
          <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
            <span style={{ fontSize: "18px" }}>Thêm buổi học</span>
          </div>
          <div style={{ marginTop: "5px" }}>
            <Row>
              <Col span={24} style={{ padding: "5px" }}>
                <span style={{ color: "red" }}>(*) </span>Tên buổi học:
                <br />
                <Input
                  value={name}
                  onChange={(e) => {
                    setName(e.target.value);
                  }}
                  type="text"
                  placeholder="Nhập tên buổi học"
                />
                <span style={{ color: "red" }}>{errorName}</span>
              </Col>
              <Col span={12} style={{ padding: "5px" }}>
                <span style={{ color: "red" }}>(*) </span>Ngày diễn ra:
                <br />
                <Input
                  value={meetingDate}
                  onChange={(e) => {
                    setMeetingDate(e.target.value);
                  }}
                  type="date"
                />
                <span style={{ color: "red" }}>{errorMeetingDate}</span>
              </Col>
              <Col span={12} style={{ padding: "5px" }}>
                <span style={{ color: "red" }}>(*) </span>Ca học:
                <br />
                <Select
                  value={meetingPeriod}
                  onChange={(e) => {
                    setMeetingPeriod(e);
                  }}
                  style={{ width: "100%" }}
                >
                  <Option value="0">Ca 1</Option>
                  <Option value="1">Ca 2</Option>
                  <Option value="2">Ca 3</Option>
                  <Option value="3">Ca 4</Option>
                  <Option value="4">Ca 5</Option>
                  <Option value="5">Ca 6</Option>
                  <Option value="6">Ca 7</Option>
                  <Option value="7">Ca 8</Option>
                  <Option value="8">Ca 9</Option>
                  <Option value="9">Ca 10</Option>
                </Select>
              </Col>
              <Col span={12} style={{ padding: "5px" }}>
                <span style={{ color: "red" }}>(*) </span>Hình thức:
                <br />
                <Select
                  value={typeMeeting}
                  onChange={(e) => {
                    setTypeMeeting(e);
                  }}
                  style={{ width: "100%" }}
                >
                  <Option value="0">Online</Option>
                  <Option value="1">Offline</Option>
                </Select>
              </Col>
              <Col span={12} style={{ padding: "5px" }}>
                Địa điểm:
                <br />
                <Input
                  value={address}
                  onChange={(e) => {
                    setAddress(e.target.value);
                  }}
                  type="text"
                  placeholder="Nhập địa điểm của buổi học"
                />
              </Col>
              <Col span={24} style={{ padding: "5px" }}>
                Mô tả:
                <br />
                <Input.TextArea
                  placeholder="Nhập mô tả"
                  value={descriptions}
                  onChange={(e) => {
                    setDescriptions(e.target.value);
                  }}
                />
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
                Thêm
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
        </div>
      </Modal>
    </>
  );
};

export default ModalCreateMeeting;