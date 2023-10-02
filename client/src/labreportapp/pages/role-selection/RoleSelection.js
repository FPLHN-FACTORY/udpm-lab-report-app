import React, { useState } from "react";
import { Radio, Button } from "antd";
import "./style-role-selection.css"; // Import file CSS
import { Link, useNavigate } from "react-router-dom";
import { portLabReportApp } from "../../helper/constants";
import { useAppSelector } from "../../app/hook";
import { GetUserCurrent } from "../../app/common/UserCurrent.reducer";

const RoleSelection = () => {
  const [selectedRole, setSelectedRole] = useState(null);

  const handleRoleChange = (e) => {
    setSelectedRole(e.target.value);
  };

  const userCurrent = useAppSelector(GetUserCurrent);

  return (
    <div className="role-selection-container">
      <div className="role-selection-background"></div>
      <div className="role-selection-content">
        <h1 style={{ color: "white" }}>
          Vai trò của bạn trong Hệ thống cổng thông tư / Quản lý xưởng bộ môn
          UDPM:
        </h1>
        <Radio.Group onChange={handleRoleChange} value={selectedRole}>
          {userCurrent != null && userCurrent.role.includes("ADMIN") && (
            <Link to={portLabReportApp + "admin"}>
              <Radio.Button value="ADMIN">Quản trị viên</Radio.Button>
            </Link>
          )}
          {userCurrent != null && userCurrent.role.includes("TEACHER") && (
            <Link to={portLabReportApp + "teacher"}>
              <Radio.Button value="TEACHER">Giảng viên</Radio.Button>
            </Link>
          )}
          {userCurrent != null && userCurrent.role.includes("STUDENT") && (
            <Link to={portLabReportApp + "student"}>
              <Radio.Button value="STUDENT">Sinh viên</Radio.Button>
            </Link>
          )}
        </Radio.Group>
      </div>
    </div>
  );
};

export default RoleSelection;
