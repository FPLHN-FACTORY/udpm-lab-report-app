import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-class-configuration.css";
import { faCogs } from "@fortawesome/free-solid-svg-icons";

const ClassConfiguration = () => {
  return (
    <div className="box-general">
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon icon={faCogs} style={{ marginRight: "8px" }} /> Cấu
          hình lớp học
        </span>
      </div>
      <div className="box-son-general"></div>
    </div>
  );
};

export default ClassConfiguration;
