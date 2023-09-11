import { useParams } from "react-router-dom";
import "./style-attendance-detail-class.css";
import { Link } from "react-router-dom";
import { ControlOutlined } from "@ant-design/icons";
import { useAppDispatch } from "../../../../app/hook";
import { Table } from "antd";
import { useEffect, useState } from "react";
import { StAttendanceAPI } from "../../../../api/student/StAttendanceAPI";
import { sinhVienCurrent } from "../../../../helper/inForUser";

const StAttendanceDetailClass = () => {
  const dispatch = useAppDispatch();
  const { id } = useParams();
  const [listAttendance, setListAttendance] = useState([]);
  const [attendanceRequest, setAttendanceRequest] = useState({
    idStudent: sinhVienCurrent.id,
    idClass: id,
  });
  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const format = `${date.getDate()}/${
      date.getMonth() + 1
    }/${date.getFullYear()}`;
    return format;
  };

  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => <>{index + 1}</>,
    },
    {
      title: "Buổi học",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Ngày học",
      dataIndex: "meetingDate",
      key: "meetingDate",
      render: (meetingDate) => <span>{convertLongToDate(meetingDate)}</span>,
    },
    {
      title: "Ca",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",
      render: (meetingPeriod) => <span>{meetingPeriod + 1}</span>,
    },
    {
      title: "Hình thức",
      dataIndex: "typeMeeting",
      key: "typeMeeting",
      render: (typeMeeting) =>
        typeMeeting === 0 ? <span>Online</span> : <span>Offline</span>,
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      render: (status) =>
        status != null ? (
          status === 0 ? (
            <span style={{ color: "green" }}>Có mặt</span>
          ) : (
            <span style={{ color: "red" }}>Vắng mặt</span>
          )
        ) : (
          ""
        ),
    },
  ];

  useEffect(() => {
    fetchData(id);
    console.log(id);
  }, [id]);

  const fetchData = async (id) => {
    try {
      await StAttendanceAPI.getAllAttendanceById(attendanceRequest).then(
        (respone) => {
          setListAttendance(respone.data);
          console.log(respone.data);
        }
      );
    } catch {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  return (
    <div style={{ paddingTop: "35px" }}>
      <div className="title-student-my-class">
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{ fontSize: "18px", marginLeft: "10px", fontWeight: "500" }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - Điểm danh</span>
        </span>
      </div>
      <div className="box-students-detail-my-class" style={{ padding: "20px" }}>
        <div
          className="button-menu-student-detail-my-class"
          style={{ minHeight: "600px" }}
        >
          <div>
            <Link
              className="custom-link"
              to={`/student/my-class/post/${id}`}
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              BÀI ĐĂNG
            </Link>
            <Link
              to={`/student/my-class/team/${id}`}
              className="custom-link"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              THÔNG TIN LỚP HỌC
            </Link>
            <Link
              className="custom-link"
              to={`/student/my-class/meeting/${id}`}
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              DANH SÁCH BUỔI HỌC
            </Link>
            <Link
              id="menu-checked"
              to={`/student/my-class/attendance/${id}`}
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              ĐIỂM DANH
            </Link>
            <Link
              className="custom-link"
              to={`/student/my-class/point/${id}`}
              style={{
                fontSize: "16px",
                fontWeight: "bold",
                paddingLeft: "10px",
                paddingRight: "10px",
              }}
            >
              ĐIỂM
            </Link>
            <hr />
          </div>
          <Table dataSource={listAttendance} columns={columns} key={"key"} />
        </div>
      </div>
    </div>
  );
};

export default StAttendanceDetailClass;
