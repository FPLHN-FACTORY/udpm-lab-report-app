import { useParams } from "react-router-dom";
import "./style-attendance-detail-class.css";
import { Link } from "react-router-dom";
import { ControlOutlined } from "@ant-design/icons";
import { useAppDispatch } from "../../../../app/hook";
import { Table } from "antd";
import { useEffect, useState } from "react";
import { StAttendanceAPI } from "../../../../api/student/StAttendanceAPI";
import { sinhVienCurrent } from "../../../../helper/inForUser";
import { convertMeetingPeriodToTime } from "../../../../helper/util.helper";
import LoadingIndicator from "../../../../helper/loading";
import { SetTTrueToggle } from "../../../../app/student/StCollapsedSlice.reducer";

const StAttendanceDetailClass = () => {
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const { id } = useParams();
  const [listAttendance, setListAttendance] = useState([]);
  const [attendanceRequest, setAttendanceRequest] = useState({
    idStudent: sinhVienCurrent.id,
    idClass: id,
  });
  const [isLoading, setIsLoading] = useState(false);

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
      title: "Thời gian",
      dataIndex: "timePeriod",
      key: "timePeriod",
      render: (text, record) => {
        return <span>{convertMeetingPeriodToTime(record.meetingPeriod)}</span>;
      },
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
      render: (text, record) => (
        <span>
          {record.status === 1 ? (
            <span style={{ color: "red" }}>Vắng mặt</span>
          ) : record.status === 0 ? (
            <span style={{ color: "green" }}>Có mặt</span>
          ) : (
            <span style={{ color: "orange" }}>Not yet</span>
          )}
        </span>
      ),
    },
  ];

  useEffect(() => {
    setIsLoading(true);
    fetchData(id);
  }, [id]);

  const fetchData = async (id) => {
    try {
      await StAttendanceAPI.getAllAttendanceById(attendanceRequest).then(
        (respone) => {
          setListAttendance(respone.data);
          setIsLoading(false);
        }
      );
    } catch {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  return (
    <div style={{ paddingTop: "35px" }}>
      {isLoading && <LoadingIndicator />}
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
          <div
            style={{ fontSize: "16px", marginTop: "15px", marginBottom: "8px" }}
          >
            Danh sách điểm danh:
          </div>
          <div>
            <Table
              dataSource={listAttendance}
              columns={columns}
              key={"key"}
              pagination={{ pageSize: 8 }}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default StAttendanceDetailClass;
