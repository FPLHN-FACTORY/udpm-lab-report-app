import React, { useState } from "react";
import { Radio, Button, Row, Card, Col } from "antd";
import "./style-role-selection.css"; // Import file CSS
import { Link, useNavigate } from "react-router-dom";
import { useAppSelector } from "../../app/hook";
import { GetUserCurrent } from "../../app/common/UserCurrent.reducer";
import ADMIN from "../../assets/img/t1.png";
import TEACHER from "../../assets/img/t2.png";
import STUDENT from "../../assets/img/t3.png";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faGraduationCap } from "@fortawesome/free-solid-svg-icons";
import { useEffect } from "react";

const RoleSelection = () => {
  const [selectedRole, setSelectedRole] = useState(null);

  const handleRoleChange = (e) => {
    setSelectedRole(e.target.value);
  };

  const navigate = useNavigate();

  const userCurrent = useAppSelector(GetUserCurrent);

  useEffect(() => {
    if (typeof userCurrent.role === "string") {
      navigate(userCurrent.role);
    }
  }, [userCurrent.role]);

  return (
    <div className="role-selection-container">
      <div className="role-selection-background"></div>
      <div className="role-selection-content">
        <div
          style={{
            color: "white",
            fontSize: 40,
            fontWeight: 700,
            textShadow: "2px 2px 4px rgba(0, 0, 0, 0.5)",
          }}
        >
          Module quản lý hoạt động xưởng bộ môn UDPM:
        </div>
        <Row
          justify="center"
          style={{ marginRight: "200px", marginLeft: "200px", marginTop: 20 }}
        >
          {" "}
          {/* Sử dụng justify để canh giữa các cột */}
          {userCurrent != null && (
            <>
              {userCurrent.role.includes("ADMIN") && (
                <Col
                  span={8}
                  style={{
                    padding: 50,
                    display: "flex",
                    justifyContent: "center",
                  }}
                >
                  <Link to="/admin">
                    <Card
                      hoverable
                      cover={
                        <img src={ADMIN} alt="Admin" style={{ height: 200 }} />
                      }
                      style={{
                        textAlign: "center",
                        height: 275,
                        width: 250,
                        backgroundColor: "transparent",
                        border: "none",
                      }}
                    >
                      <Radio.Button
                        value="ADMIN"
                        style={{
                          backgroundColor: "rgb(38, 144, 214)",
                          color: "white",
                        }}
                      >
                        <FontAwesomeIcon
                          icon={faGraduationCap}
                          style={{ marginRight: 5 }}
                        />
                        Quản trị viên
                      </Radio.Button>
                    </Card>
                  </Link>
                </Col>
              )}
              {userCurrent.role.includes("TEACHER") && (
                <Col
                  span={8}
                  style={{
                    padding: 50,
                    display: "flex",
                    justifyContent: "center",
                  }}
                >
                  <Link to="/teacher">
                    <Card
                      hoverable
                      cover={
                        <img
                          src={TEACHER}
                          alt="Teacher"
                          style={{ height: 200 }}
                        />
                      }
                      style={{
                        textAlign: "center",
                        height: 275,
                        width: 250,
                        backgroundColor: "transparent",
                        border: "none",
                      }}
                    >
                      <Radio.Button
                        style={{
                          backgroundColor: "rgb(38, 144, 214)",
                          color: "white",
                        }}
                        value="TEACHER"
                      >
                        <FontAwesomeIcon
                          icon={faGraduationCap}
                          style={{ marginRight: 5 }}
                        />{" "}
                        Giảng viên
                      </Radio.Button>
                    </Card>
                  </Link>
                </Col>
              )}
              {userCurrent.role.includes("STUDENT") && (
                <Col
                  span={8}
                  style={{
                    padding: 50,
                    display: "flex",
                    justifyContent: "center",
                  }}
                >
                  <Link to="/student">
                    <Card
                      hoverable
                      cover={
                        <img
                          src={STUDENT}
                          alt="Student"
                          style={{ height: 200 }}
                        />
                      }
                      style={{
                        textAlign: "center",
                        height: 275,
                        width: 250,
                        backgroundColor: "transparent",
                        border: "none",
                      }}
                    >
                      <Radio.Button
                        style={{
                          backgroundColor: "rgb(38, 144, 214)",
                          color: "white",
                        }}
                        value="STUDENT"
                      >
                        <FontAwesomeIcon
                          icon={faGraduationCap}
                          style={{ marginRight: 5 }}
                        />{" "}
                        Sinh viên
                      </Radio.Button>
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
