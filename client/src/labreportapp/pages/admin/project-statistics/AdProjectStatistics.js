import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-ad-project-statistics.css";
import { faLineChart } from "@fortawesome/free-solid-svg-icons";
import { useEffect } from "react";

const AdProjectStatistics = () => {
  useEffect(() => {
    document.title = "Thống kê dự án xưởng | Lab-Report-App";
  }, []);
  return (
    <div className="box-general" style={{ paddingTop: 50 }}>
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon icon={faLineChart} style={{ marginRight: "8px" }} />{" "}
          Thống kê dự án xưởng
        </span>
      </div>
      <div className="box-son-general"></div>
    </div>
  );
};

export default AdProjectStatistics;
