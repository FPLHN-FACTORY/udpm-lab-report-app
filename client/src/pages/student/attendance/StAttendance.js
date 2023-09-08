import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-attendance.css";
import {
  faAtlas,
  faLineChart,
  faRegistered,
} from "@fortawesome/free-solid-svg-icons";

const StAttendance = () => {
  return (
    <div className="box-general">
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon icon={faAtlas} style={{ marginRight: "8px" }} /> Điểm
          danh
        </span>
      </div>
      <div className="box-son-general"></div>
    </div>
  );
};

export default StAttendance;
