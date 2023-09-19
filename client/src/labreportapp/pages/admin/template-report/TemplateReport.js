import { faTemperature0 } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const TemplateReport = () => {
  return (
    <div className="box-general">
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon
            icon={faTemperature0}
            style={{ marginRight: "8px" }}
          />{" "}
          Template báo cáo
        </span>
      </div>
      <div className="box-son-general"></div>
    </div>
  );
};

export default TemplateReport;
