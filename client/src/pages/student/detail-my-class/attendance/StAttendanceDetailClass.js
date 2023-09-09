import { useParams } from "react-router-dom";
import "./style-attendance-detail-class.css";
import { Link } from "react-router-dom";
import { ControlOutlined } from "@ant-design/icons";
import { useAppDispatch } from "../../../../app/hook";
import { Table } from "antd";
import { useEffect, useState } from "react";
import { StAttendanceAPI } from "../../../../api/student/StAttendanceAPI";

const StAttendanceDetailClass = () => {
  const dispatch = useAppDispatch();
  const { id } = useParams();
  const [attendanceDetail, setAttendanceDetail] = useState([]);
  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const format = `${date.getDate()}/${
      date.getMonth() + 1
    }/${date.getFullYear()}`;
    return format;
  };

  const columns = [
    {
      title: "Bài học",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Ngày",
      dataIndex: "meetingDate",
      key: "meetingDate",
      render: (meetingDate) => convertLongToDate(meetingDate),
    },
    {
      title: "Ca",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",
      render: (meetingPeriod) => meetingPeriod + 1,
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      render: (status) => (status === 0 ? "Có mặt" : "Vắng mặt"),
    },
  ];

  useEffect(() => {
    fetchData(id);
  }, [id]);

  const fetchData = async (id) => {
    try {
      await StAttendanceAPI.getAllAttendanceById(id).then((respone) => {
        setAttendanceDetail(respone.data);
        console.log(respone.data);
      });
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
          <Table dataSource={attendanceDetail} columns={columns} key={"key"} />
        </div>
      </div>
    </div>
  );
};

export default StAttendanceDetailClass;
