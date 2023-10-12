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
      <Menu theme="light" mode="inline">
        <div style={{ marginBottom: 10, marginTop: 10 }}>
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
        <Menu.SubMenu
          key="1"
          title="Quản lý chung"
          icon={
            <FontAwesomeIcon
              icon={faGripLinesVertical}
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
                icon={faFolderOpen}
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
              icon={faConciergeBell}
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
                icon={faTemperature0}
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
              icon={faBook}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Link to="/admin/class-management">Quản lý lớp học</Link>
        </Menu.Item>
        <Menu.SubMenu
          key="10"
          title="Thống kê"
          icon={
            <FontAwesomeIcon
              icon={faChartColumn}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Menu.Item
            key="11"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faLineChart}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/factory-deployment-statistics">
              Thống kê triển khai xưởng
            </Link>
          </Menu.Item>
          <Menu.Item
            key="12"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faLineChart}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/track-activity-metrics">
              Theo dõi chỉ số hoạt động
            </Link>
          </Menu.Item>
        </Menu.SubMenu>
      </Menu>
      <div style={{ marginBottom: 10, marginTop: 10 }}>
        <span style={{ marginLeft: 28 }}>
          <FontAwesomeIcon
            icon={faCog}
            style={{ marginRight: 7, fontSize: 18 }}
          />
          {!collapsed && <span style={{ fontSize: 15 }}>Dự án xưởng</span>}
        </span>
      </div>
      <Menu theme="light" mode="inline" style={{ paddingBottom: 100 }}>
        <Menu.SubMenu
          key="13"
          title="Quản lý chung"
          icon={
            <FontAwesomeIcon
              icon={faGripLinesVertical}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Menu.Item
            key="14"
            icon={
              <FontAwesomeIcon
                icon={faTeletype}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/type-project-management">Quản lý loại dự án</Link>
          </Menu.Item>
          <Menu.Item
            key="15"
            icon={
              <FontAwesomeIcon
                icon={faFolder}
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
              icon={faConciergeBell}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Menu.Item
            key="18"
            icon={
              <FontAwesomeIcon
                icon={faPersonMilitaryPointing}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/role-management">Quyền trong dự án</Link>
          </Menu.Item>
        </Menu.SubMenu>
        <Menu.SubMenu
          key="19"
          title="Dự án xưởng"
          icon={
            <FontAwesomeIcon
              icon={faCogs}
              style={{ color: "rgb(226, 179, 87)" }}
            />
          }
        >
          <Menu.Item
            key="20"
            icon={
              <FontAwesomeIcon
                icon={faGroupArrowsRotate}
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
                icon={faCogs}
                style={{ color: "rgb(226, 179, 87)" }}
              />
            }
          >
            <Link to="/admin/project-management">Quản lý dự án</Link>
          </Menu.Item>
        </Menu.SubMenu>
        <div style={{ marginBottom: 16, marginTop: 15 }}>
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
        </Menu.Item>
      </Menu>
    </Sider>
  );
};

export default SidebarAdminComponent;
