import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-admin-dashboard.css";
import { faLineChart } from "@fortawesome/free-solid-svg-icons";
import { useEffect } from "react";

const AdFactoryDeploymentStatistics = () => {
  useEffect(() => {
    document.title = "Thống kê triển khai xưởng | Lab-Report-App";
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
              icon={faLineChart}
              style={{ marginRight: "8px" }}
            />{" "}
            Thống kê triển khai xưởng
          </span>
        </div>
      </div>
      <div className="box-general" style={{ paddingTop: 10, marginTop: 0 }}>
        <div className="box-son-general"></div>
      </div>
    </>
  );
};

export default AdFactoryDeploymentStatistics;
