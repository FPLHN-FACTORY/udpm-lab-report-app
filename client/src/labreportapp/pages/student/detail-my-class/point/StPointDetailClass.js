import { useParams } from "react-router-dom";
import "./style-point-detail-class.css";
import { Link } from "react-router-dom";
import { ControlOutlined } from "@ant-design/icons";
import { useAppDispatch } from "../../../../app/hook";
import { useState, useEffect } from "react"; // Import useState và useEffect
import { StPointDetailAPI } from "../../../../api/student/StPointDetailAPI";
import { sinhVienCurrent } from "../../../../helper/inForUser";
import { Table } from "antd";
import LoadingIndicator from "../../../../helper/loading";
import { SetTTrueToggle } from "../../../../app/student/StCollapsedSlice.reducer";

const StPointDetailClass = () => {
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const { id } = useParams();
  const [pointStudent, setPointStudent] = useState([]);
  const [noPointMessage, setNoPointMessage] = useState(""); // Thêm state để lưu thông báo khi không có điểm
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setIsLoading(true);    document.title = "Bảng điều khiển - Điểm";
    LoadPointData();
  }, []);

  const LoadPointData = () => {
    StPointDetailAPI.getPointDetail(id)
      .then((response) => {
        if (response.data.data) {
          // Kiểm tra nếu có dữ liệu điểm
          setPointStudent(response.data.data);
          setIsLoading(false);
        } else {
          // Nếu không có điểm, cập nhật thông báo
          setNoPointMessage("Bạn chưa có điểm.");
        }
      })
      .catch((error) => {
        // Xử lý lỗi nếu có
        console.error("Lỗi khi tải dữ liệu điểm:", error);
      });
  };

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
    },
    {
      title: "Tên đầu điểm",
      dataIndex: "tenDauDiem",
      key: "tenDauDiem",
    },
    {
      title: "Trọng số",
      dataIndex: "trongSo",
      key: "trongSo",
    },
    {
      title: "Điểm",
      dataIndex: "diem",
      key: "diem",
    },
    {
      title: "Ghi chú",
      dataIndex: "ghiChu",
      key: "ghiChu",
    },
  ];

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
          <span style={{ color: "gray" }}> - Điểm</span>
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
              className="custom-link"
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
              id="menu-checked"
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
          <div>
            <>
              <div className="info-team">
                <span className="info-heading">Danh sách đầu điểm:</span>
              </div>
              <Table
                columns={columns}
                dataSource={pointStudent}
                key="stt"
                pagination={{ pageSize: 8 }}
              />
            </>
          </div>
        </div>
      </div>
    </div>
  );
};

export default StPointDetailClass;
