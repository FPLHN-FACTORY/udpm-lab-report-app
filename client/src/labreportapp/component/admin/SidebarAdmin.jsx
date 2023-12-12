import React, { useEffect, useState } from "react";
import { Badge, Layout, Menu } from "antd";
import { Link, useLocation } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faTags,
  faUsers,
  faCogs,
  faLayerGroup,
  faLevelUp,
  faCog,
  faPeopleGroup,
  faPeopleLine,
  faClock,
  faChartBar,
  faNewspaper,
  faWindowRestore,
  faGraduationCap,
  faChalkboard,
  faFile,
  faChartGantt,
  faProjectDiagram,
  faFolderPlus,
  faUserTag,
  faObjectGroup,
  faDiagramSuccessor,
  faChalkboardTeacher,
  faCheck,
  faListAlt,
} from "@fortawesome/free-solid-svg-icons";
import logoBit from "../../assets/img/logo_bit_1.png";
import "./style-sidebar.css";
import { AdMeetingRequestAPI } from "../../api/admin/AdMeetingRequestAPI";
import { useAppDispatch, useAppSelector } from "../../app/hook";
import {
  GetAdCountApproveMeetingRequest,
  SetAdCountApproveMeetingRequest,
} from "../../app/admin/AdCountApproveMeetingRequest.reducer";

const { Sider } = Layout;
const { SubMenu } = Menu;

const SidebarAdminComponent = ({ collapsed, toggleCollapsed }) => {
  const location = useLocation();
  const dispatch = useAppDispatch();

  const [selectedKey, setSelectedKey] = useState("");

  useEffect(() => {
    setSelectedKey(location.pathname);
  }, [location]);

  const countClassApprove = () => {
    AdMeetingRequestAPI.countClassHaveMeetingRequest().then((res) => {
      dispatch(SetAdCountApproveMeetingRequest(res.data.data));
    });
  };

  const numberClassApproved = useAppSelector(GetAdCountApproveMeetingRequest);

  useEffect(() => {
    countClassApprove();
  }, []);

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
        color: "white",
        background: "rgb(233, 192, 89)",
      }}
    >
      <Menu
        theme="light"
        mode="inline"
        style={{
          color: "white",
          paddingBottom: 80,
          background: "rgb(233, 192, 89)",
        }}
        selectedKeys={selectedKey}
      >
        <div style={{ marginBottom: 15, marginTop: 15 }}>
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
        <Menu.SubMenu
          key="1"
          title="Quản lý chung"
          icon={
            <FontAwesomeIcon
              icon={faCogs}
              style={{ color: "white" }}
            />
          }
        >
          <Menu.Item
            key="/admin/semester-management"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faLayerGroup}
                style={{ color: "white" }}
              />
            }
          >
            <Link to="/admin/semester-management">Quản lý học kỳ</Link>
          </Menu.Item>
          <Menu.Item
            key="/admin/level-management"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faLevelUp}
                style={{ color: "white" }}
              />
            }
          >
            <Link to="/admin/level-management">Quản lý level</Link>
          </Menu.Item>
          <Menu.Item
            key="/admin/activity-management"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faChalkboard}
                style={{ color: "white" }}
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
              style={{ color: "white" }}
            />
          }
        >
          <Menu.Item
            key="/admin/class-configuration"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faLayerGroup}
                style={{ color: "white" }}
              />
            }
          >
            <Link to="/admin/class-configuration">Cấu hình lớp học</Link>
          </Menu.Item>
          <Menu.Item
            key="/admin/meeting-period-configuration"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faClock}
                style={{ color: "white" }}
              />
            }
          >
            <Link to="/admin/meeting-period-configuration">
              Cấu hình ca học
            </Link>
          </Menu.Item>
          <Menu.Item
            key="/admin/template-report"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faFile}
                style={{ color: "white" }}
              />
            }
          >
            <Link to="/admin/template-report">Template báo cáo</Link>
          </Menu.Item>
        </Menu.SubMenu>
        <Menu.Item
          key="/admin/class-management"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faGraduationCap}
              style={{ color: "white" }}
            />
          }
        >
          <Link to="/admin/class-management">Quản lý lớp học</Link>
        </Menu.Item>{" "}
        <Menu.Item
          key="/admin/approve-meeting"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faWindowRestore}
              style={{ color: "white" }}
            />
          }
        >
          <Link to="/admin/approve-meeting">
            Phê duyệt lịch học
            <Badge
              count={numberClassApproved}
              offset={[10, 0]}
              style={{ marginTop: 0, right: -1, bottom: 6 }}
            ></Badge>
          </Link>{" "}
        </Menu.Item>{" "}
        <Menu.SubMenu
          key="911"
          title="Giảng viên & Feedback"
          icon={
            <FontAwesomeIcon
              icon={faListAlt}
              style={{ color: "white" }}
            />
          }
        >
          <Menu.Item
            key="/admin/teacher-dashboard"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faChalkboardTeacher}
                style={{ color: "white" }}
              />
            }
          >
            <Link to="/admin/teacher-dashboard">Danh sách giảng viên</Link>
          </Menu.Item>
          <Menu.Item
            key="/admin/feedback"
            className="menu_custom"
            icon={
              <FontAwesomeIcon
                icon={faCheck}
                style={{ color: "white" }}
              />
            }
          >
            <Link to="/admin/feedback">Feedback</Link>
          </Menu.Item>
        </Menu.SubMenu>
        <Menu.Item
          key="/admin/factory-deployment-statistics"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faChartGantt}
              style={{ color: "white" }}
            />
          }
        >
          <Link to="/admin/factory-deployment-statistics">
            Thống kê triển khai
          </Link>
        </Menu.Item>
        {/* <Menu.Item
          key="12"
          className="menu_custom"
          icon={
            <FontAwesomeIcon
              icon={faChartPie}
              style={{ color: "white" }}
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
            {!collapsed && (
              <span style={{ fontSize: 15, fontWeight: 700 }}>Dự án xưởng</span>
            )}
          </span>
        </div>
        <Menu.SubMenu
          key="13"
          title="Quản lý chung"
          icon={
            <FontAwesomeIcon
              icon={faCogs}
              style={{ color: "white" }}
            />
          }
        >
          <Menu.Item
            key="/admin/category-management"
            icon={
              <FontAwesomeIcon
                icon={faFolderPlus}
                style={{ color: "white" }}
              />
            }
          >
            <Link to="/admin/category-management"> Quản lý thể loại</Link>
          </Menu.Item>
          <Menu.Item
            key="/admin/label-management"
            icon={
              <FontAwesomeIcon
                icon={faTags}
                style={{ color: "white" }}
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
              style={{ color: "white" }}
            />
          }
        >
          <Menu.Item
            key="/admin/role-project-management"
            icon={
              <FontAwesomeIcon
                icon={faUserTag}
                style={{ color: "white" }}
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
              style={{ color: "white" }}
            />
          }
        >
          <Menu.Item
            key="/admin/group-project-management"
            icon={
              <FontAwesomeIcon
                icon={faObjectGroup}
                style={{ color: "white" }}
              />
            }
          >
            <Link to="/admin/group-project-management">Quản lý nhóm dự án</Link>
          </Menu.Item>
          <Menu.Item
            key="/admin/project-management"
            icon={
              <FontAwesomeIcon
                icon={faDiagramSuccessor}
                style={{ color: "white" }}
              />
            }
          >
            <Link to="/admin/project-management">Quản lý dự án</Link>
          </Menu.Item>
        </Menu.SubMenu>
        <Menu.Item
          key="/admin/project-statistics"
          icon={
            <FontAwesomeIcon
              icon={faChartBar}
              style={{ color: "white" }}
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
              <span style={{ fontSize: 15, fontWeight: 700 }}>
                Thành viên xưởng
              </span>
            )}
          </span>
        </div>
        <Menu.Item
          key="/admin/team-management"
          icon={
            <FontAwesomeIcon
              icon={faPeopleLine}
              style={{ color: "white" }}
            />
          }
        >
          <Link to="/admin/team-management">Quản lý team</Link>
        </Menu.Item>
        <Menu.Item
          key="/admin/member-management"
          icon={
            <FontAwesomeIcon
              icon={faPeopleGroup}
              style={{ color: "white" }}
            />
          }
        >
          <Link to="/admin/member-management">Quản lý thành viên</Link>
        </Menu.Item>{" "}
        <Menu.Item
          key="/admin/role-factory-management"
          icon={
            <FontAwesomeIcon
              icon={faUserTag}
              style={{ color: "white" }}
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
