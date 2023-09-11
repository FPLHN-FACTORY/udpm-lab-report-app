import { useParams } from "react-router-dom";
import "./style-point-detail-class.css";
import { Link } from "react-router-dom";
import { ControlOutlined } from "@ant-design/icons";
import { useAppDispatch } from "../../../../app/hook";
import { useState, useEffect } from "react"; // Import useState và useEffect
import { StPointDetailAPI } from "../../../../api/student/StPointDetailAPI";
import { sinhVienCurrent } from "../../../../helper/inForUser";

const StPointDetailClass = () => {
  const dispatch = useAppDispatch();
  const { id } = useParams();
  const [pointStudent, setPointStudent] = useState([]);
  const [noPointMessage, setNoPointMessage] = useState(""); // Thêm state để lưu thông báo khi không có điểm

  useEffect(() => {
    LoadPointData();
  }, []);

  const LoadPointData = () => {
    StPointDetailAPI.getPointDetail(id)
      .then((response) => {
        if (response.data.data) {
          // Kiểm tra nếu có dữ liệu điểm
          setPointStudent(response.data.data);
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
                <span className="info-heading">Thông tin đầu điểm:</span>
                <div className="group-info">
                  <span
                    className="group-info-item"
                    style={{ marginTop: "10px", marginBottom: "15px" }}
                  >
                    Họ và Tên:{" "}
                    {sinhVienCurrent.name != null ? sinhVienCurrent.name : ""}
                  </span>
                  {noPointMessage ? (
                    // Hiển thị thông báo nếu không có điểm
                    <span className="group-info-item" style={{ color: "red" }}>
                      {noPointMessage}
                    </span>
                  ) : (
                    <>
                      <span className="group-info-item">
                        Điểm giai đoạn 1:{" "}
                        {pointStudent.checkPointPhase1 != null
                          ? pointStudent.checkPointPhase1
                          : ""}
                      </span>
                      <span className="group-info-item">
                        Điểm giai đoạn 2:{" "}
                        {pointStudent.checkPointPhase2 != null
                          ? pointStudent.checkPointPhase2
                          : ""}
                      </span>
                      <span className="group-info-item">
                        Điểm final:{" "}
                        {pointStudent.finalPoint != null
                          ? pointStudent.finalPoint
                          : ""}
                      </span>
                    </>
                  )}
                </div>
              </div>
            </>
          </div>
        </div>
      </div>
    </div>
  );
};

export default StPointDetailClass;
