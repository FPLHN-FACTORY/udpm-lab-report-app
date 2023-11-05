import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-admin-teacher-dashboard.css";
import {
  faChalkboardTeacher,
  faLineChart,
} from "@fortawesome/free-solid-svg-icons";
import { useEffect } from "react";

const AdminTeacherDashboard = () => {
  useEffect(() => {
    document.title = "Danh sách giảng viên | Lab-Report-App";
  }, []);
  return (
    <>
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon
              icon={faChalkboardTeacher}
              style={{ marginRight: "8px" }}
            />{" "}
            Danh sách giảng viên
          </span>
        </div>
      </div>
      <div className="box-general" style={{ paddingTop: 10, marginTop: 0 }}>
        <div className="box-son-general"></div>
      </div>
    </>
  );
};

export default AdminTeacherDashboard;
