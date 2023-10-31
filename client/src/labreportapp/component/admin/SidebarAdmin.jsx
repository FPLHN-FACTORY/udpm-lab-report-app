import React from "react";
import { Layout, Menu } from "antd";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFolder,
  faTags,
  faUsers,
  faCogs,
  faBook,
  faLayerGroup,
  faFolderOpen,
  faLineChart,
  faTemperature0,
  faLevelUp,
  faPersonMilitaryPointing,
  faTeletype,
  faGroupArrowsRotate,
  faMagicWandSparkles,
  faChartColumn,
  faConciergeBell,
  faGripLinesVertical,
  faCog,
  faPeopleGroup,
  faPeopleLine,
  faClock,
  faChartLine,
  faChartArea,
  faChartBar,
  faNewspaper,
  faWindowRestore,
  faGraduationCap,
  faRunning,
  faChalkboard,
  faFile,
  faChartPie,
  faChartGantt,
  faProjectDiagram,
  faDiagramProject,
  faTasks,
  faFolderPlus,
  faUserTag,
  faObjectGroup,
  faDiagramSuccessor,
  faChalkboardTeacher,
  faFeed,
  faCheck,
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
        paddingBottom: "20px",
        position: "fixed",
        left: 0,
      }}
    >
      <Menu theme="light" mode="inline" style={{ paddingBottom: 80 }}>
        <div style={{ marginBottom: 15, marginTop: 15 }}>
          <span style={{ marginLeft: 28 }}>
            <FontAwesomeIcon
              icon={faNewspaper}
              style={{ marginRight: 7, fontSize: 18 }}
            />
            {!collapsed && (
              <span style={{ fontSize: 15 }}>Hoạt động xưởng</span>
            )}
          </span>
        </div>
        <Menu.SubMenu
          key="1"
          title="Quản lý chung"
          icon={
            <FontAwesomeIcon
              icon={faCogs}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Menu.Item
            key="2"
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
            key="3"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faLevelUp}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/level-management">Quản lý level</Link>
          </Menu.Item>
          <Menu.Item
            key="4"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faChalkboard}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/activity-management">Quản lý hoạt động</Link>{" "}
          </Menu.Item>
        </Menu.SubMenu>
        <Menu.SubMenu
          key="5"
          title="Cấu hình"
          icon={
            <FontAwesomeIcon
              icon={faCog}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
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
            <Link to="/admin/class-configuration">Cấu hình lớp học</Link>
          </Menu.Item>
          <Menu.Item
            key="7"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faClock}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/meeting-period-configuration">
              Cấu hình ca học
            </Link>
          </Menu.Item>
          <Menu.Item
            key="8"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faFile}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/template-report">Template báo cáo</Link>
          </Menu.Item>
        </Menu.SubMenu>
        <Menu.Item
          key="9"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faGraduationCap}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/admin/class-management">Quản lý lớp học</Link>
        </Menu.Item>{" "}
        <Menu.Item
          key="92"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faCheck}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/admin/feedback">Feedback</Link>
        </Menu.Item>
        <Menu.SubMenu
          key="10"
          title="Thống kê xưởng"
          icon={
            <FontAwesomeIcon
              icon={faChartColumn}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Menu.Item
            key="91"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faChalkboardTeacher}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/teacher-dashboard">Thống kê giảng viên</Link>
          </Menu.Item>
          <Menu.Item
            key="11"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faChartGantt}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/factory-deployment-statistics">
              Triển khai xưởng
            </Link>
          </Menu.Item>
        </Menu.SubMenu>
        {/* <Menu.Item
          key="12"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faChartPie}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/admin/track-activity-metrics">Chỉ số hoạt động</Link>
        </Menu.Item>
        */}
        <div style={{ marginBottom: 15, marginTop: 15 }}>
          <span style={{ marginLeft: 28 }}>
            <FontAwesomeIcon
              icon={faWindowRestore}
              style={{ marginRight: 7, fontSize: 18 }}
            />
            {!collapsed && <span style={{ fontSize: 15 }}>Dự án xưởng</span>}
          </span>
        </div>
        <Menu.SubMenu
          key="13"
          title="Quản lý chung"
          icon={
            <FontAwesomeIcon
              icon={faCogs}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Menu.Item
            key="15"
            icon={
              <FontAwesomeIcon
                icon={faFolderPlus}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/category-management"> Quản lý thể loại</Link>
          </Menu.Item>
          <Menu.Item
            key="16"
            icon={
              <FontAwesomeIcon
                icon={faTags}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/label-management">Quản lý nhãn</Link>
          </Menu.Item>
        </Menu.SubMenu>
        <Menu.SubMenu
          key="17"
          title="Cấu hình"
          icon={
            <FontAwesomeIcon
              icon={faCog}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Menu.Item
            key="18"
            icon={
              <FontAwesomeIcon
                icon={faUserTag}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/role-project-management">Vai trò trong dự án</Link>
          </Menu.Item>
        </Menu.SubMenu>
        <Menu.SubMenu
          key="19"
          title="Dự án xưởng"
          icon={
            <FontAwesomeIcon
              icon={faProjectDiagram}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Menu.Item
            key="20"
            icon={
              <FontAwesomeIcon
                icon={faObjectGroup}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/group-project-management">Quản lý nhóm dự án</Link>
          </Menu.Item>
          <Menu.Item
            key="21"
            icon={
              <FontAwesomeIcon
                icon={faDiagramSuccessor}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/project-management">Quản lý dự án</Link>
          </Menu.Item>
        </Menu.SubMenu>
        <Menu.Item
          key="21.1"
          icon={
            <FontAwesomeIcon
              icon={faChartBar}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/admin/project-statistics">Thống kê dự án</Link>
        </Menu.Item>
        <div style={{ marginBottom: 15, marginTop: 15 }}>
          <span style={{ marginLeft: 28 }}>
            <FontAwesomeIcon
              icon={faUsers}
              style={{ marginRight: 7, fontSize: 18 }}
            />
            {!collapsed && (
              <span style={{ fontSize: 15 }}>Thành viên xưởng</span>
            )}
          </span>
        </div>
        <Menu.Item
          key="22"
          icon={
            <FontAwesomeIcon
              icon={faPeopleLine}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/admin/team-management">Quản lý team</Link>
        </Menu.Item>
        <Menu.Item
          key="23"
          icon={
            <FontAwesomeIcon
              icon={faPeopleGroup}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/admin/member-management">Quản lý thành viên</Link>
        </Menu.Item>{" "}
        <Menu.Item
          key="24"
          icon={
            <FontAwesomeIcon
              icon={faUserTag}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/admin/role-factory-management">Vai trò trong xưởng</Link>
        </Menu.Item>{" "}
      </Menu>
    </Sider>
  );
};

export default SidebarAdminComponent;
