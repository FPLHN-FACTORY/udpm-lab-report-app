import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-register-class.css";
import { faLineChart, faRegistered } from "@fortawesome/free-solid-svg-icons";

const StRegisterClass = () => {
  return (
    <div className="box-general">
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon icon={faRegistered} style={{ marginRight: "8px" }} />{" "}
          Đăng ký lớp học
        </span>
      </div>
      <div className="box-son-general"></div>
    </div>
  );
};

export default StRegisterClass;
