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
  faBook,
  faLayerGroup,
  faArrowRightToCity,
  faFolderOpen,
  faLineChart,
} from "@fortawesome/free-solid-svg-icons";

import "./style-sidebar.css";

const { Sider } = Layout;
const { SubMenu } = Menu;

const SidebarAdminComponent = ({ collapsed, toggleCollapsed }) => {
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
          key="1"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faLayerGroup}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/admin/semester-management">Quản lý học kỳ</Link>
        </Menu.Item>
        <Menu.Item
          key="2"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faFolderOpen}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/admin/activity-management">Quản lý hoạt động</Link>{" "}
        </Menu.Item>

        <Menu.Item
          key="3"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faBook}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/admin/class-management">Quản lý lớp học</Link>
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
          <Link to="/admin/dashboard">Thống kê triển khai xưởng</Link>
        </Menu.Item>
        <Menu.Item
          key="5"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faLineChart}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/admin/dashboard">Theo dõi chỉ số hoạt động</Link>
        </Menu.Item>
        <Menu.Item
          key="6"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faLayerGroup}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/admin/semester-management">Cấu hình lớp học</Link>
        </Menu.Item>
      </Menu>
    </Sider>
  );
};

export default SidebarAdminComponent;
