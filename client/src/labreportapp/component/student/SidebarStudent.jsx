import React, { useState } from "react";
import { Layout, Menu } from "antd";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFolder,
  faTags,
  faChartLine,
  faUsers,
  faCogs,
  faCalendar,
  faHome,
  faCheckCircle,
  faMarker,
  faRegistered,
} from "@fortawesome/free-solid-svg-icons";

import "./style-sidebar.css";
import { ProjectOutlined } from "@ant-design/icons";

const { Sider } = Layout;
const { SubMenu } = Menu;

const SidebarStudentComponent = ({ collapsed, toggleCollapsed }) => {
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
      <Menu theme="light" mode="inline">
        <Menu.Item
          key="0"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faRegistered}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/student/register-class">Đăng ký lớp học</Link>
        </Menu.Item>
        <Menu.Item
          key="1"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faCalendar}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/student/schedule">Lịch học</Link>
        </Menu.Item>
        <Menu.Item
          key="2"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faHome}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/student/my-class">Lớp của tôi</Link>
        </Menu.Item>
        <Menu.Item
          key="9"
          className="menu_custom"
          icon={<ProjectOutlined style={{ color: "rgb(226, 179, 87)" }} />}
        >
          <Link to="/student/my-project">Dự án của tôi</Link>
        </Menu.Item>
        <Menu.Item
          key="3"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faCheckCircle}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/student/attendance">Điểm danh</Link>
        </Menu.Item>
        <Menu.Item
          key="4"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faMarker}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/student/point">Điểm</Link>
        </Menu.Item>
      </Menu>
    </Sider>
  );
};

export default SidebarStudentComponent;
