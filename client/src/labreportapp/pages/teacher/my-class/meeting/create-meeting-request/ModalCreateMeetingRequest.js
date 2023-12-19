import {
  Button,
  Col,
  DatePicker,
  Input,
  Modal,
  Row,
  Select,
  message,
} from "antd";
import { useState } from "react";
import "moment/locale/vi";
import { useEffect } from "react";
import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
import { convertHourAndMinuteToString } from "../../../../../helper/util.helper";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../../app/common/Loading.reducer";
import { TeacherMeetingRequestAPI } from "../../../../../api/teacher/meeting-request/TeacherMeeting.api";
import locale from "antd/es/date-picker/locale/vi_VN";
import dayjs from "dayjs";
import "dayjs/locale/vi";
import { GetAdTeacher } from "../../../../../app/admin/AdTeacherSlice.reducer";
import { GetAdMeetingPeriod } from "../../../../../app/admin/AdMeetingPeriodSlice.reducer";

const { Option } = Select;
dayjs.locale("vi");
const ModalCreateMeetingRequest = ({ idClass, visible, onCancel }) => {
  const [meetingPeriod, setMeetingPeriod] = useState("");
  const [typeMeeting, setTypeMeeting] = useState("0");
  const [name, setName] = useState("");
  const [meetingDate, setMeetingDate] = useState(null);
  const [errorMeetingPeriod, setErrorMeetingPeriod] = useState("");
  const [errorMeetingDate, setErrorMeetingDate] = useState("");
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (visible) {
      // setName(item.name);
      // setTypeMeeting(item.typeMeeting + "");
      // setMeetingPeriod(item.idMeetingPeriod);
      // setMeetingDate(item.meetingDate);
      // setErrorMeetingDate("");
      // setSelectedItemsPerson(item.teacherId);
      // setErrorTeacher("");
    }
    return () => {
      setErrorMeetingPeriod("");
      setName("");
      setErrorMeetingDate("");
      setMeetingDate(null);
      setTypeMeeting("0");
      setMeetingPeriod("");
    };
  }, [visible]);

  const update = () => {
    let check = 0;
    if (meetingDate === null) {
      setErrorMeetingDate("Ngày diễn ra không được để trống");
      ++check;
    } else {
      setErrorMeetingDate("");
      if (new Date(meetingDate).getTime() < new Date().getTime()) {
        setErrorMeetingDate("Ngày diễn ra không được nằm trong quá khứ");
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
    if (selectedItemsPerson === "") {
      setErrorTeacher("Giảng viên không được để trống");
      ++check;
    } else {
      setErrorTeacher("");
    }
    if (check === 0) {
      try {
        let obj = {
          idClass: idClass,
          meetingDate: meetingDate,
          meetingPeriod: meetingPeriod,
          typeMeeting: parseInt(typeMeeting),
          teacherId: selectedItemsPerson,
        };
        dispatch(SetLoadingTrue());
        TeacherMeetingRequestAPI.createMeetingRequest(obj).then((response) => {
          if (response.data.data != null) {
            message.success("Tạo buổi học yêu cầu thành công !");
          } else {
            message.error("Tạo buổi học yêu cầu thất bại !");
          }
          dispatch(SetLoadingFalse());
          onCancel();
        });
      } catch (error) {
        dispatch(SetLoadingFalse());
        onCancel();
      }
    }
  };

  const teacherDataAll = useAppSelector(GetAdTeacher);
  const [selectedItemsPerson, setSelectedItemsPerson] = useState("");
  const [errorTeacher, setErrorTeacher] = useState("");

  const handleSelectPersonChange = (e) => {
    setSelectedItemsPerson(e);
  };

  const filterTeacherOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };

  const dataMeetingPeriod = useAppSelector(GetAdMeetingPeriod);

  const onChangeDate = (date) => {
    if (date != null) {
      setMeetingDate(dayjs(date).valueOf());
    } else {
      setMeetingDate(null);
    }
  };

  return (
    <>
      <Modal
        open={visible}
        onCancel={onCancel}
        width={650}
        footer={null}
        className="modal_show_detail_meeting"
      >
        <div>
          <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
            <span style={{ fontSize: "18px" }}>Cập nhật buổi học yêu cầu</span>
          </div>
          <div style={{ marginTop: "5px" }}>
            <Row>
              <Col span={12} style={{ padding: "5px" }}>
                <span style={{ color: "red" }}>(*) </span>Ngày diễn ra:
                <br />
                <DatePicker
                  locale={locale}
                  value={meetingDate ? dayjs(meetingDate) : null}
                  format="DD/MM/YYYY"
                  onChange={onChangeDate}
                  style={{ width: "100%" }}
                  placeholder="Chọn ngày"
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
                  <Option value={""}>Chọn 1 ca học</Option>
                  {dataMeetingPeriod.map((item) => {
                    return (
                      <Option value={item.id} key={item.id}>
                        {item.name} -{" "}
                        {convertHourAndMinuteToString(
                          item.startHour,
                          item.startMinute,
                          item.endHour,
                          item.endMinute
                        )}
                      </Option>
                    );
                  })}
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
              </Col>
              <Col span={12} style={{ padding: "5px" }}>
                {" "}
                <span style={{ color: "red" }}>(*) </span>
                <span>Giảng viên dạy:</span> <br />
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
            </Row>
          </div>
          <div style={{ textAlign: "right" }}>
            <div style={{ paddingTop: "15px" }}>
              <Button
                style={{
                  marginRight: "5px",
                  backgroundColor: "rgb(61, 139, 227)",
                  color: "white",
                  width: "88px",
                }}
                onClick={update}
              >
                Tạo buổi học yêu cầu
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

export default ModalCreateMeetingRequest;
