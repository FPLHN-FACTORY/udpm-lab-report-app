import React, { useEffect, useState } from "react";
import { Modal, Button, Select, Row, Col, Input } from "antd";
import "./style-create-meeting-auto.css";
import "react-toastify/dist/ReactToastify.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faRandom } from "@fortawesome/free-solid-svg-icons";
import { useParams } from "react-router";
import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
import { GetAdTeacher } from "../../../../../app/admin/AdTeacherSlice.reducer";
import moment from "moment";
import { MeetingManagementAPI } from "../../../../../api/admin/meeting-management/MeetingManagementAPI";

const { Option } = Select;

const ModalCreateMeetingAuto = ({ visible, onCancel, fetchData }) => {
  const [meetingPeriod, setMeetingPeriod] = useState("0");
  const [typeMeeting, setTypeMeeting] = useState("0");
  const [errorTypeMeeting, setErrorTypeMeeting] = useState("");
  const [meetingDate, setMeetingDate] = useState("");
  const [errorMeetingPeriod, setErrorMeetingPeriod] = useState("");
  const { id } = useParams();
  const dispatch = useAppDispatch();
  const [errorMeetingDate, setErrorMeetingDate] = useState("");

  const teacherDataAll = useAppSelector(GetAdTeacher);
  const [selectedItemsPerson, setSelectedItemsPerson] = useState("");
  const [errorTeacher, setErrorTeacher] = useState("");
  const [numberMeeting, setNumberMeeting] = useState("1");
  const [errorNumberMeeting, setErrorNumberMeeting] = useState("");
  const [numberDay, setNumberDay] = useState("1");
  const [errorNumberDay, setErrorNumberDay] = useState("");

  const handleSelectPersonChange = (e) => {
    setSelectedItemsPerson(e);
  };

  const filterTeacherOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };

  useEffect(() => {
    setMeetingPeriod("0");
    setTypeMeeting("0");
    setErrorTypeMeeting("");
    setMeetingDate("");
    setErrorMeetingPeriod("");
    setErrorMeetingDate("");
    setSelectedItemsPerson("");
    setNumberMeeting("1");
    setErrorNumberMeeting("");
    setNumberDay("1");
    setErrorNumberDay("");
  }, [visible]);

  const createMeetingAuto = () => {
    let check = 0;
    if (meetingDate === "") {
      setErrorMeetingDate("Ngày học không được để trống");
      ++check;
    } else {
      setErrorMeetingDate("");
      if (new Date(meetingDate).getTime() < new Date().getTime()) {
        setErrorMeetingDate("Ngày học không được nằm trong quá khứ");
        ++check;
      } else {
        setErrorMeetingDate("");
      }
    }
    if (meetingPeriod === "") {
      setErrorMeetingPeriod("Ca học không được để trống");
      ++check;
    } else {
      setErrorMeetingPeriod("");
    }
    if (typeMeeting === "") {
      setErrorTypeMeeting("Hình thức không được để trống");
      ++check;
    } else {
      setErrorTypeMeeting("");
    }
    if (numberMeeting === "") {
      setErrorNumberMeeting("Số buổi học không được để trống");
      ++check;
    } else {
      setErrorNumberMeeting("");
      if (parseInt(numberMeeting) < 0) {
        setErrorNumberMeeting("Số buổi học không được nhỏ hơn 0");
        ++check;
      } else {
        setErrorNumberMeeting("");
      }
    }

    if (numberDay === "") {
      setErrorNumberDay(
        "Số ngày cách nhau giữa 2 buổi gần nhất không được để trống"
      );
      ++check;
    } else {
      setErrorNumberDay("");
      if (parseInt(numberDay) < 0) {
        setErrorNumberDay(
          "Số ngày cách nhau giữa 2 buổi gần nhất không được nhỏ hơn 0"
        );
        ++check;
      } else {
        setErrorNumberDay("");
      }
    }

    if (check === 0) {
      let obj = {
        meetingDate: moment(meetingDate, "YYYY-MM-DD").valueOf(),
        meetingPeriod: parseInt(meetingPeriod),
        typeMeeting: parseInt(typeMeeting),
        classId: id,
        teacherId: selectedItemsPerson,
        numberMeeting: parseInt(numberMeeting),
        numberDay: parseInt(numberDay),
      };
      MeetingManagementAPI.createMeetingAuto(obj).then((response) => {
        fetchData();
        onCancel();
      });
    }
  };
  return (
    <>
      <Modal
        visible={visible}
        onCancel={onCancel}
        width={700}
        footer={null}
        className="modal_show_detail_project"
      >
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Tạo buổi học tự động</span>
        </div>
        <div
          style={{
            marginTop: "10px",
            borderBottom: "1px solid gray",
            paddingBottom: "12px",
          }}
        >
          <Row>
            <Col span={12} style={{ padding: "5px" }}>
              <span style={{ color: "red" }}>(*) </span>Ngày bắt đầu:
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
              </Select>{" "}
              <span style={{ color: "red" }}>{errorMeetingPeriod}</span>
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
              <span style={{ color: "red" }}>{errorTypeMeeting}</span>
            </Col>
            <Col span={12} style={{ padding: "5px" }}>
              Giảng viên dạy: <br />
              <Select
                showSearch
                value={selectedItemsPerson}
                onChange={handleSelectPersonChange}
                style={{ width: "100%" }}
                filterOption={filterTeacherOptions}
              >
                <Option value="">Chọn 1 giảng viên</Option>

                {teacherDataAll.map((teacher) => (
                  <Option key={teacher.id} value={teacher.id}>
                    {teacher.userName + " - " + teacher.name}
                  </Option>
                ))}
              </Select>{" "}
              <span style={{ color: "red" }}>{errorTeacher}</span>
            </Col>
            <Col span={12} style={{ padding: "5px" }}>
              <span style={{ color: "red" }}>(*) </span> Số buổi cần tạo: <br />
              <Input
                type="number"
                min={0}
                step={1}
                max={50}
                value={numberMeeting}
                onChange={(e) => {
                  setNumberMeeting(e.target.value);
                }}
              />
              <span style={{ color: "red" }}>{errorNumberMeeting}</span>
            </Col>
            <Col span={12} style={{ padding: "5px" }}>
              <span style={{ color: "red" }}>(*) </span>Số ngày cách nhau giữa 2
              buổi gần nhất: <br />
              <Input
                type="number"
                min={0}
                step={1}
                max={50}
                value={numberDay}
                onChange={(e) => {
                  setNumberDay(e.target.value);
                }}
              />
              <span style={{ color: "red" }}>{errorNumberDay}</span>
            </Col>
          </Row>
          <Row style={{ marginTop: "7px" }}>
            <span style={{ color: "red", fontSize: "14px" }}>
              (*) Lưu ý: Các buổi học tiếp theo khi tạo bằng chức năng này sẽ
              được tính là thời gian của buổi trước đó cộng với (Số ngày cách
              nhau giữa 2 buổi gần nhất) ngày và ca học, giảng viên, hình thức
              tất cả các buổi học được tạo sẽ giống nhau.
            </span>
          </Row>
        </div>

        <div>
          <div style={{ textAlign: "right" }}>
            <div style={{ paddingTop: "15px" }}>
              <Button
                style={{
                  marginRight: "5px",
                  backgroundColor: "rgb(61, 139, 227)",
                  color: "white",
                }}
                onClick={createMeetingAuto}
              >
                <FontAwesomeIcon
                  icon={faRandom}
                  style={{ marginRight: "5px" }}
                />
                Hoàn thành
              </Button>
              <Button
                style={{
                  backgroundColor: "red",
                  color: "white",
                  width: "88px",
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
export default ModalCreateMeetingAuto;
