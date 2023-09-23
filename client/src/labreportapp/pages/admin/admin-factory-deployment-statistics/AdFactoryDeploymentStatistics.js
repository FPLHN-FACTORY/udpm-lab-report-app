import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-admin-dashboard.css";
import { faLineChart } from "@fortawesome/free-solid-svg-icons";
import { useEffect } from "react";

const AdFactoryDeploymentStatistics = () => {
  useEffect(() => {
    document.title = "Thống kê triển khai xưởng | Lab-Report-App";
  }, []);
  return (
    <div className="box-general">
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon icon={faLineChart} style={{ marginRight: "8px" }} />{" "}
          Thống kê triển khai xưởng
        </span>
      </div>
      <div className="box-son-general"></div>
    </div>
  );
};

export default AdFactoryDeploymentStatistics;
