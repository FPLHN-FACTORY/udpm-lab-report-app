import { Row, Col, Table, Empty, Button, Popconfirm, Input } from "antd";
import { useParams } from "react-router";
import "./style-detail-meeting-attendance.css";
import {
  BookOutlined,
  ControlOutlined,
  EyeInvisibleFilled,
  SearchOutlined,
} from "@ant-design/icons";
import { Link } from "react-router-dom";
import { MeetingManagementAPI } from "../../../../../api/admin/meeting-management/MeetingManagementAPI";
import { useEffect, useState } from "react";
import { convertLongToDate } from "../../../../../helper/convertDate";
import {
  convertMeetingPeriodToNumber,
  convertMeetingPeriodToTime,
} from "../../../../../helper/util.helper";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowTrendDown, faSave } from "@fortawesome/free-solid-svg-icons";
import moment from "moment";
import LoadingIndicator from "../../../../../helper/loading";
import CustomSwitchAdmin from "./CustomSwitchAdmin";
import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
import {
  GetAdAttendanceDetailMeeting,
  SetAdAttendance,
} from "../../../../../app/admin/AdAttendanceDetailMeeting.reducer";
import {
  GetMeeting,
  SetMeeting,
} from "../../../../../app/admin/AdMeetingManagement.reducer";
import { toast } from "react-toastify";

const DetailMeetingAttendance = () => {
  const { id } = useParams();
  const [meeting, setMeeting] = useState("");
  const dispatch = useAppDispatch();
  const [listAttendance, setListAttendance] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const detailMeeting = () => {
    MeetingManagementAPI.detailMeeting(id).then((response) => {
      setMeeting(response.data.data);
      console.log(response.data.data);
      document.title =
        response.data.data.name + " | " + response.data.data.codeClass;
      loadDataAttendance(response.data.data.classId);
    });
  };

  useEffect(() => {
    setIsLoading(true);
    detailMeeting();

    return () => {
      dispatch(SetAdAttendance([]));
    };
  }, []);

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => <span>{index + 1}</span>,
      width: "3%",
    },
    {
      title: "Họ và tên",
      dataIndex: "name",
      key: "name",
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) =>
        record.name.toLowerCase().includes(value.toLowerCase()),
      sorter: (a, b) => a.name.localeCompare(b.name),
      width: "30%",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) =>
        record.email.toLowerCase().includes(value.toLowerCase()),
      sorter: (a, b) => a.email.localeCompare(b.email),
      width: "30%",
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      sorter: (a, b) => a.status - b.status,
      render: (text, record) => (
        <CustomSwitchAdmin
          leftLabel="Có mặt"
          rightLabel="Vắng mặt"
          status={record.status}
          items={record}
        />
      ),
    },
  ];

  const loadDataAttendance = (idClass) => {
    MeetingManagementAPI.getAttendanceByIdMeeting(id, idClass).then(
      (response) => {
        setListAttendance(response.data.data);
        dispatch(SetAdAttendance(response.data.data));
        setIsLoading(false);
      }
    );
  };

  const data = useAppSelector(GetAdAttendanceDetailMeeting);

  const saveAttendance = () => {
    let obj = {
      listAttendance: data,
    };
    MeetingManagementAPI.updateAttendance(obj).then((response) => {
      toast.success("Cập nhật thành công");
    });
  };

  return (
    <>
      {isLoading && <LoadingIndicator />}
      <div className="box-one">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <ControlOutlined style={{ fontSize: "23px" }} />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>{" "}
            <span style={{ color: "gray", fontSize: "14px" }}>
              {" "}
              - Chi tiết buổi học{" "}
            </span>
          </span>
        </Link>
      </div>
      <div className="box-two-student-in-my-class">
        <div>
          <Link
            to={`/admin/class-management/meeting-management/${meeting.classId}`}
            style={{ color: "black" }}
          >
            <span style={{ fontSize: "18px" }}>
              <BookOutlined
                style={{ color: "black", fontSize: 18, marginRight: "5px" }}
              />
              Danh sách buổi học
            </span>{" "}
          </Link>
          <span style={{ fontSize: "18px" }}> / </span>{" "}
          <span style={{ fontSize: "18px" }}>
            <BookOutlined
              style={{ color: "black", fontSize: 18, marginRight: "5px" }}
            />
            {meeting.name}
          </span>
        </div>
        <div
          className="box-two-student-in-my-class-son"
          style={{ minHeight: "505px", marginTop: "25px" }}
        >
          <div>
            <Row>
              <Col span={18}>
                <div className="title-left-meeting">
                  <div className="box-icon-detail">
                    <BookOutlined style={{ color: "white", fontSize: 21 }} />
                  </div>
                  <span
                    style={{
                      fontSize: "24px",
                      color: "#1967D2",
                    }}
                  >
                    {meeting.name}
                  </span>
                </div>
              </Col>
              <Col span={6}>
                <div
                  style={{
                    lineHeight: "42px",

                    float: "right",
                    color: "red",
                  }}
                >
                  Thời gian: {convertLongToDate(meeting.meetingDate)} - Ca:
                  {" " +
                    parseInt(
                      convertMeetingPeriodToNumber(meeting.meetingPeriod) + 1
                    )}{" "}
                  {" (" +
                    convertMeetingPeriodToTime(
                      convertMeetingPeriodToNumber(meeting.meetingPeriod)
                    ) +
                    ")"}
                </div>
              </Col>
            </Row>
            <hr />
          </div>
          <div className="row-meeting">
            <div className="meeting-container">
              {" "}
              {/* Sử dụng lớp CSS cho container */}
              <div className="meeting-title">
                {" "}
                {/* Sử dụng lớp CSS cho tiêu đề */}
                <EyeInvisibleFilled
                  style={{ marginRight: "15px", fontSize: "24px" }}
                />
                Thông tin chi tiết buổi học:
              </div>
              <div>
                <Row>
                  <Col span={12} style={{ padding: "5px" }}>
                    <div className="meeting-info">
                      {" "}
                      {/* Sử dụng lớp CSS cho thông tin */}
                      Tên buổi học:{" "}
                      <span style={{ color: "red" }}>{meeting.name}</span>
                    </div>
                  </Col>
                  <Col span={12} style={{ padding: "5px" }}>
                    <div className="meeting-info">
                      {" "}
                      {/* Sử dụng lớp CSS cho thông tin */}
                      Thời gian học:{" "}
                      <span style={{ color: "red" }}>
                        {moment(meeting.meetingDate).format("DD/MM/YYYY")}{" "}
                      </span>
                    </div>
                  </Col>
                  <Col span={12} style={{ padding: "5px" }}>
                    <div className="meeting-info">
                      {" "}
                      {/* Sử dụng lớp CSS cho thông tin */}
                      Ca học:{" "}
                      <span style={{ color: "red" }}>
                        {" "}
                        {parseInt(
                          convertMeetingPeriodToNumber(meeting.meetingPeriod) +
                            1
                        )}
                      </span>
                    </div>
                  </Col>
                  <Col span={12} style={{ padding: "5px" }}>
                    <div className="meeting-info">
                      {" "}
                      {/* Sử dụng lớp CSS cho thông tin */}
                      Hình thức:{" "}
                      <span style={{ color: "red" }}>
                        {meeting.typeMeeting === "OFFLINE"
                          ? "Offline"
                          : "Online"}
                      </span>
                    </div>
                  </Col>
                  <Col span={12} style={{ padding: "5px" }}>
                    <div className="meeting-info">
                      {" "}
                      {/* Sử dụng lớp CSS cho thông tin */}
                      Địa điểm:{" "}
                      <span style={{ color: "red" }}>
                        {" "}
                        {meeting.address == null || meeting.address === ""
                          ? "-"
                          : meeting.address}{" "}
                      </span>
                    </div>
                  </Col>
                  <Col span={12} style={{ padding: "5px" }}>
                    <div className="meeting-info">
                      {" "}
                      {/* Sử dụng lớp CSS cho thông tin */}
                      Giảng viên dạy:{" "}
                      <span style={{ color: "red" }}>
                        {meeting.userNameTeacher != null
                          ? meeting.userNameTeacher
                          : "Chưa có"}
                      </span>
                    </div>
                  </Col>
                  <Col span={12} style={{ padding: "5px" }}>
                    <div className="meeting-info">
                      {" "}
                      {/* Sử dụng lớp CSS cho thông tin */}
                      Lớp học:{" "}
                      <span style={{ color: "red" }}>{meeting.codeClass}</span>
                    </div>
                  </Col>
                  <Col span={12} style={{ padding: "5px" }}>
                    <div className="meeting-info">
                      {" "}
                      {/* Sử dụng lớp CSS cho thông tin */}
                      Trạng thái:{" "}
                      <span style={{ color: "red" }}>
                        {meeting.statusMeeting === "BUOI_HOC"
                          ? "Buổi học"
                          : meeting.statusMeeting === "BUOI_NGHI"
                          ? "Buổi nghỉ"
                          : ""}
                      </span>
                    </div>
                  </Col>
                  <Col span={24} style={{ padding: "5px" }}>
                    <div className="meeting-info">
                      {" "}
                      {/* Sử dụng lớp CSS cho thông tin */}
                      Mô tả: {meeting.descriptions}
                    </div>
                  </Col>
                </Row>
              </div>
            </div>
            <div style={{ marginTop: "20px" }}>
              <span style={{ fontSize: "18px" }}>
                {" "}
                <FontAwesomeIcon
                  icon={faArrowTrendDown}
                  style={{ marginRight: "5px" }}
                />
                Danh sách điểm danh:
              </span>
            </div>
            <div style={{ marginTop: "10px" }}>
              {listAttendance != null && listAttendance.length > 0 && (
                <Table
                  rowKey="id"
                  columns={columns}
                  dataSource={listAttendance}
                  pagination={false}
                />
              )}
              {(listAttendance == null || listAttendance.length === 0) && (
                <Empty
                  imageStyle={{ height: 60 }}
                  description={
                    <span style={{ color: "#007bff" }}>
                      Chưa có thông tin điểm danh của sinh viên !
                    </span>
                  }
                />
              )}
            </div>
          </div>
        </div>

        {listAttendance != null && listAttendance.length > 0 && (
          <div style={{ display: "flex", justifyContent: "center" }}>
            <Popconfirm
              title="Lưu điểm danh"
              description="Bạn có chắc chắn muốn lưu điểm danh cho buổi học này không?"
              onConfirm={() => {
                saveAttendance();
              }}
              okText="Yes"
              cancelText="No"
            >
              <Button className="btn_save_attendance_detail_meeting">
                <FontAwesomeIcon icon={faSave} style={{ marginRight: "5px" }} />
                Lưu điểm danh
              </Button>
            </Popconfirm>
          </div>
        )}
      </div>
    </>
  );
};

export default DetailMeetingAttendance;
