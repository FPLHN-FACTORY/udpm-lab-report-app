import React, { useState } from "react";
import { Layout, Menu, message } from "antd";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFolder,
  faTags,
  faChartLine,
  faUsers,
  faCogs,
  faBook,
  faLineChart,
  faHome,
  faMagicWandSparkles,
} from "@fortawesome/free-solid-svg-icons";

import "./style-sidebar.css";
import { ProjectOutlined } from "@ant-design/icons";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import { toast } from "react-toastify";

const { Sider } = Layout;
const { SubMenu } = Menu;

const SidebarTeacherComponent = ({ collapsed, toggleCollapsed }) => {
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
        <div style={{ marginBottom: 13, marginTop: 15 }}>
          <span style={{ marginLeft: 28 }}>
            <FontAwesomeIcon
              icon={faMagicWandSparkles}
              style={{ marginRight: 7, fontSize: 18 }}
            />
            {!collapsed && (
              <span style={{ fontSize: 15 }}>Hoạt động xưởng</span>
            )}
          </span>
        </div>
        <Menu.Item
          key="1"
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
          key="2"
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
          key="4"
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
              icon={faMagicWandSparkles}
              style={{ marginRight: 7, fontSize: 18 }}
            />
            {!collapsed && <span style={{ fontSize: 15 }}>Dự án xưởng</span>}
          </span>
        </div>
        <Menu.Item
          key="3"
          className="menu_custom"
          icon={<ProjectOutlined style={{ color: "rgb(226, 179, 87)" }} />}
        >
          <Link to="/teacher/my-project">Dự án tại xưởng</Link>
        </Menu.Item>
      </Menu>
    </Sider>
  );
};

export default SidebarTeacherComponent;
