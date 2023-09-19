import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-teacher-dashboard.css";
import {
  faAtlas,
  faLineChart,
  faMarker,
  faRegistered,
} from "@fortawesome/free-solid-svg-icons";

const TeacherDashboard = () => {
  return (
    <div className="box-general">
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon icon={faLineChart} style={{ marginRight: "8px" }} />{" "}
          Thống kê
        </span>
      </div>
      <div className="box-son-general"></div>
    </div>
  );
};

export default TeacherDashboard;
