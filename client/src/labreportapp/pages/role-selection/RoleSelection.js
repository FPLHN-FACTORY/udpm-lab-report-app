import React, { useState } from "react";
import { Radio, Button, Row, Card, Col } from "antd";
import "./style-role-selection.css"; // Import file CSS
import { Link, useNavigate } from "react-router-dom";
import { portLabReportApp } from "../../helper/constants";
import { useAppSelector } from "../../app/hook";
import { GetUserCurrent } from "../../app/common/UserCurrent.reducer";
import ADMIN from "../../assets/img/ADMIN.jpg";
import TEACHER from "../../assets/img/TEACHER.jpg";
import STUDENT from "../../assets/img/STUDENT.jpg";

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
        <div style={{ color: "white", fontSize: 35, fontWeight: 700 }}>
          Module quản lý xưởng bộ môn UDPM:
        </div>
        <Row justify="center">
          {" "}
          {/* Sử dụng justify để canh giữa các cột */}
          {userCurrent != null && (
            <>
              {userCurrent.role.includes("ADMIN") && (
                <Col span={8} style={{ padding: 100 }}>
                  <Link to="/admin">
                    <Card
                      hoverable
                      cover={<img src={ADMIN} alt="Admin" />}
                      style={{ textAlign: "center" }}
                    >
                      <Radio.Button value="ADMIN">Quản trị viên</Radio.Button>
                    </Card>
                  </Link>
                </Col>
              )}
              {userCurrent.role.includes("TEACHER") && (
                <Col span={8} style={{ padding: 100 }}>
                  <Link to="/teacher">
                    <Card
                      hoverable
                      cover={<img src={TEACHER} alt="Teacher" />}
                      style={{ textAlign: "center" }}
                    >
                      <Radio.Button value="TEACHER">Giảng viên</Radio.Button>
                    </Card>
                  </Link>
                </Col>
              )}
              {userCurrent.role.includes("STUDENT") && (
                <Col span={8} style={{ padding: 100 }}>
                  <Link to="/student">
                    <Card
                      hoverable
                      cover={<img src={STUDENT} alt="Student" />}
                      style={{ textAlign: "center" }}
                    >
                      <Radio.Button value="STUDENT">Sinh viên</Radio.Button>
                    </Card>
                  </Link>
                </Col>
              )}
            </>
          )}
        </Row>
      </div>
    </div>
  );
};

export default RoleSelection;
