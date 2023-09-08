import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-information-class.css";
import { faLineChart } from "@fortawesome/free-solid-svg-icons";
import { ControlOutlined } from "@ant-design/icons";
import { Link, useParams } from "react-router-dom";

const InformationClass = () => {
  const { id } = useParams();
  return (
    <div className="box-general-custom">
      <div className="title-meeting-managemnt-my-class">
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{
              fontSize: "18px",
              marginLeft: "10px",
              fontWeight: "500",
            }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - Thông tin lớp học</span>
        </span>
      </div>
      <div className="box-general" style={{ padding: 25, marginTop: 0 }}>
        <div className="box-son-general" style={{ marginTop: 0 }}>
          <div>
            <Link
              id="menu-checked"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                fontWeight: "bold",
              }}
            >
              THÔNG TIN LỚP HỌC &nbsp;
            </Link>
            <Link
              className="custom-link"
              to={`/admin/class-management/meeting-management/${id}`}
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                fontWeight: "bold",
              }}
            >
              QUẢN LÝ LỊCH HỌC &nbsp;
            </Link>

            <hr />
          </div>
        </div>
      </div>
    </div>
  );
};

export default InformationClass;
