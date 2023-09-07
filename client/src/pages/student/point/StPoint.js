import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-point.css";
import {
  faAtlas,
  faLineChart,
  faMarker,
  faRegistered,
} from "@fortawesome/free-solid-svg-icons";

const StPoint = () => {
  return (
    <div className="box-general">
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon icon={faMarker} style={{ marginRight: "8px" }} />{" "}
          Điểm
        </span>
      </div>
      <div className="box-son-general"></div>
    </div>
  );
};

export default StPoint;
