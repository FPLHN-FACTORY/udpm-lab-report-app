import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-admin.track-activity-metrics.css";
import { faLineChart } from "@fortawesome/free-solid-svg-icons";
import { useEffect } from "react";

const AdminTrackActivityMetrics = () => {
  useEffect(() => {
    document.title = "Theo dõi chỉ số hoạt động | Lab-Report-App";
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
            Theo dõi chỉ số hoạt động
          </span>
        </div>
      </div>
      <div className="box-general" style={{ paddingTop: 10, marginTop: 0 }}>
        <div className="heading-box">
          <span style={{ fontSize: "20px", fontWeight: "500" }}></span>
        </div>
        <div className="box-son-general"></div>
      </div>
    </>
  );
};

export default AdminTrackActivityMetrics;
