import {
  faPlus,
  faFilterCircleDollar,
  faChainSlash,
  faTableList,
  faPersonMilitaryPointing,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button } from "antd";
import { useEffect } from "react";
import "./style-role-management.css";
import React from "react";

const RoleManagement = () => {
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Quản lý vai trò | Lab-Report-App";
  }, []);

  return (
    <div className="box-general" style={{ paddingTop: 50 }}>
      <div className="title_activity_management" style={{ marginTop: 0 }}>
        {" "}
        <FontAwesomeIcon
          icon={faPersonMilitaryPointing}
          style={{ fontSize: "20px" }}
        />
        <span style={{ marginLeft: "10px" }}>Quản lý vai trò trong dự án</span>
      </div>
      <div className="filter-level" style={{ marginBottom: "10px" }}>
        <div className="box_btn_filter">
          <Button className="btn_filter" style={{ marginRight: "15px" }}>
            <FontAwesomeIcon
              icon={faFilterCircleDollar}
              style={{ marginRight: 5 }}
            />
            Tìm kiếm
          </Button>
          <Button
            className="btn__clear"
            style={{ backgroundColor: "rgb(50, 144, 202)" }}
          >
            <FontAwesomeIcon icon={faChainSlash} style={{ marginRight: 5 }} />
            Làm mới bộ lọc
          </Button>
        </div>
      </div>
      <div
        className="box-son-general"
        style={{ minHeight: "400px", marginTop: "30px" }}
      >
        <div className="tittle__category" style={{ marginBottom: "20px" }}>
          <div>
            <FontAwesomeIcon
              icon={faTableList}
              style={{
                marginRight: "10px",
                fontSize: "20px",
              }}
            />
            <span style={{ fontSize: "18px", fontWeight: "500" }}>
              {" "}
              Danh sách vai trò
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
              Thêm
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RoleManagement;
