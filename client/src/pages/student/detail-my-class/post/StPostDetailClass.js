import { useParams } from "react-router-dom";
import "./style-post-detail-class.css";
import { Link } from "react-router-dom";
import { ControlOutlined } from "@ant-design/icons";
import { useAppDispatch } from "../../../../app/hook";

const StPostDetailClass = () => {
  const dispatch = useAppDispatch();
  const { id } = useParams();

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
          <span style={{ color: "gray" }}> - Bài đăng</span>
        </span>
      </div>
      <div className="box-students-detail-my-class" style={{ padding: "20px" }}>
        <div
          className="button-menu-student-detail-my-class"
          style={{ minHeight: "600px" }}
        >
          <div>
            <Link
              id="menu-checked"
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
        </div>
      </div>
    </div>
  );
};

export default StPostDetailClass;
