import { faObjectGroup } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-detail-group-project.css";
import React from "react";

const DetailGroupProject = () => {
  return (
    <div className="box-general" style={{ paddingTop: 50 }}>
      <div className="title_activity_management" style={{ marginTop: 0 }}>
        {" "}
        <FontAwesomeIcon icon={faObjectGroup} style={{ fontSize: "20px" }} />
        <span style={{ marginLeft: "10px" }}>
          Quản lý nhóm dự án / Chi tiết nhóm dự án
        </span>
      </div>
      <div
        className="box-son-general"
        style={{ minHeight: "400px", marginTop: "20px" }}
      >
        <div
          className="tittle__category"
          style={{ marginBottom: "20px" }}
        ></div>
      </div>
    </div>
  );
};

export default DetailGroupProject;
