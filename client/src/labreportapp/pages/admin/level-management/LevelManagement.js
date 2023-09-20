import {
  faFilter,
  faLevelUp,
  faLevelUpAlt,
  faPlus,
  faTemperature0,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Input } from "antd";
import "./style-level-management.css";

const LevelManagement = () => {
  return (
    <div className="box-general">
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon icon={faLevelUp} style={{ marginRight: "8px" }} />{" "}
          Quản lý level
        </span>
      </div>
      <div className="filter-level" style={{ marginBottom: "10px" }}>
        <FontAwesomeIcon icon={faFilter} style={{ fontSize: "20px" }} />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <div className="title__search">
          Tên level:{" "}
          <Input type="text" style={{ width: "300px", marginLeft: "5px" }} />
        </div>
        <div className="box_btn_filter">
          <Button className="btn_filter">Tìm kiếm</Button>
          <Button
            className="btn__clear"
            style={{ backgroundColor: "rgb(50, 144, 202)" }}
          >
            Làm mới bộ lọc
          </Button>
        </div>
      </div>
      <div
        className="box-son-general"
        style={{ minHeight: "400px", marginTop: "30px" }}
      >
        <div className="tittle__category">
          <div>
            {" "}
            {
              <FontAwesomeIcon
                icon={faLevelUpAlt}
                style={{ fontSize: "20px" }}
              />
            }
            <span style={{ fontSize: "18px", fontWeight: "500" }}>
              {" "}
              Danh sách level
            </span>
          </div>
          <div>
            <Button
              style={{
                color: "white",
                backgroundColor: "rgb(55, 137, 220)",
              }}
            >
              <FontAwesomeIcon
                icon={faPlus}
                size="1x"
                style={{
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: "5px",
                }}
              />{" "}
              Thêm level
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LevelManagement;

