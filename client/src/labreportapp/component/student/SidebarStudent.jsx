import React, { useEffect, useState } from "react";
import { Layout, Menu } from "antd";
import { Link, useLocation } from "react-router-dom";
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
  faMagicWandSparkles,
  faPeopleLine,
  faPeopleGroup,
} from "@fortawesome/free-solid-svg-icons";

import "./style-sidebar.css";
import { ProjectOutlined } from "@ant-design/icons";

const { Sider } = Layout;
const { SubMenu } = Menu;

const SidebarStudentComponent = ({ collapsed, toggleCollapsed }) => {
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
              icon={faMagicWandSparkles}
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
          key="/student/register-class"
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
          key="/student/schedule"
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
          key="/student/my-class"
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
          key="/student/attendance"
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
          key="/student/point"
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
        <div style={{ marginBottom: 16, marginTop: 15 }}>
          <span style={{ marginLeft: 28 }}>
            <FontAwesomeIcon
              icon={faCogs}
              style={{ marginRight: 7, fontSize: 18 }}
            />
            {!collapsed && (
              <span style={{ fontSize: 15, fontWeight: 700 }}>Dự án xưởng</span>
            )}
          </span>
        </div>
        <Menu.Item
          key="/student/my-project"
          className="menu_custom"
          icon={<ProjectOutlined style={{ color: "rgb(226, 179, 87)" }} />}
        >
          <Link to="/student/my-project">Dự án</Link>
        </Menu.Item>
        <Menu.Item
          key="/student/team-factory"
          icon={
            <FontAwesomeIcon
              icon={faPeopleLine}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/student/team-factory">Danh sách team </Link>
        </Menu.Item>
        <Menu.Item
          key="/student/member-factory"
          icon={
            <FontAwesomeIcon
              icon={faPeopleGroup}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/student/member-factory">Thành viên xưởng</Link>
        </Menu.Item>{" "}
      </Menu>
    </Sider>
  );
};

export default SidebarStudentComponent;
