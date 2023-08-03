import React, { useState } from "react";
import { Layout, Space } from "antd";
import SidebarAdminComponent from "../../component/admin/SidebarAdmin";
import HeaderAdminComponent from "../../component/admin/HeaderAdmin";
import "./style.css";

const { Content, Sider } = Layout;

const DashBoardAdmin = ({ children }) => {
  const [collapsed, setCollapsed] = useState(false);

  const toggleCollapsed = () => {
    setCollapsed(!collapsed);
  };

  document.querySelector("body").style.backgroundImage = "url()";

  return (
    <Space direction="vertical" style={{ width: "100%" }} size={[0, 48]}>
      <Layout className="adminLayout" style={{ backgroundColor: "white" }}>
        <Sider
          width={250}
          collapsed={collapsed}
          style={{
            height: "100vh",
            position: "fixed",
            left: 0,
          }}
        >
          <SidebarAdminComponent
            collapsed={collapsed}
            toggleCollapsed={toggleCollapsed}
          />
        </Sider>
        <Layout
          style={{
            transition: "margin-left 0.2s",
            minHeight: "100vh",
          }}
        >
          <HeaderAdminComponent
            collapsed={collapsed}
            toggleCollapsed={toggleCollapsed}
          />
          <Content
            style={{
              marginLeft: collapsed ? 80 : 250,
              backgroundColor: "#f8f8f8",
              minHeight: "calc(100vh)",
              paddingTop: "65px",
              paddingLeft: "45px",
            }}
          >
            {children}
          </Content>
        </Layout>
      </Layout>
    </Space>
  );
};

export default DashBoardAdmin;
