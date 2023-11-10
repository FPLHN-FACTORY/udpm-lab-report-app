import React, { useEffect, useState } from "react";
import { Layout, Menu, message } from "antd";
import { Link, useLocation } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faBook,
  faLineChart,
  faHome,
  faNewspaper,
  faWindowRestore,
  faPeopleLine,
  faPeopleGroup,
  faCheck,
} from "@fortawesome/free-solid-svg-icons";

import "./style-sidebar.css";
import { ProjectOutlined } from "@ant-design/icons";

const { Sider } = Layout;
const { SubMenu } = Menu;

const SidebarTeacherComponent = ({ collapsed, toggleCollapsed }) => {
  const location = useLocation();

  const [selectedKey, setSelectedKey] = useState("");

  useEffect(() => {
    setSelectedKey(location.pathname);
  }, [location]);

  return (
    <Sider
      trigger={null}
      collapsible
      collapsed={collapsed}
      theme="light"
      className="sidebar"
      width={250}
      style={{
        overflow: "auto",
        position: "fixed",
        left: 0,
      }}
    >
      <Menu theme="light" mode="inline" selectedKeys={selectedKey}>
        <div style={{ marginBottom: 13, marginTop: 15 }}>
          <span style={{ marginLeft: 28 }}>
            <FontAwesomeIcon
              icon={faNewspaper}
              style={{ marginRight: 7, fontSize: 18 }}
            />
            {!collapsed && (
              <span style={{ fontSize: 15, fontWeight: 700 }}>
                Hoạt động xưởng
              </span>
            )}
          </span>
        </div>
        <Menu.Item
          key="/teacher/schedule-today"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faBook}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/teacher/schedule-today">Lịch dạy</Link>
        </Menu.Item>
        <Menu.Item
          key="/teacher/my-class"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faHome}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/teacher/my-class">Lớp của tôi</Link>
        </Menu.Item>
        <Menu.Item
          key="/teacher/feedback"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faCheck}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/teacher/feedback">Feedback</Link>
        </Menu.Item>
        <Menu.Item
          key="/teacher/dashboard"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faLineChart}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/teacher/dashboard">Thống kê</Link>
        </Menu.Item>
        <div style={{ marginBottom: 17, marginTop: 15 }}>
          <span style={{ marginLeft: 28 }}>
            <FontAwesomeIcon
              icon={faWindowRestore}
              style={{ marginRight: 7, fontSize: 18 }}
            />
            {!collapsed && (
              <span style={{ fontSize: 15, fontWeight: 700 }}>Dự án xưởng</span>
            )}
          </span>
        </div>
        <Menu.Item
          key="/teacher/my-project"
          className="menu_custom"
          icon={<ProjectOutlined style={{ color: "rgb(226, 179, 87)" }} />}
        >
          <Link to="/teacher/my-project">Dự án</Link>
        </Menu.Item>
        <Menu.Item
          key="/teacher/team-factory"
          icon={
            <FontAwesomeIcon
              icon={faPeopleLine}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/teacher/team-factory">Danh sách team</Link>
        </Menu.Item>
        <Menu.Item
          key="/teacher/member-factory"
          icon={
            <FontAwesomeIcon
              icon={faPeopleGroup}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/teacher/member-factory">Thành viên xưởng</Link>
        </Menu.Item>{" "}
      </Menu>
    </Sider>
  );
};

export default SidebarTeacherComponent;
