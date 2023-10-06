import React, { useState } from "react";
import { Layout, Space } from "antd";
import SidebarAdminComponent from "../../component/admin/SidebarAdmin";
import HeaderAdminComponent from "../../component/admin/HeaderAdmin";
import "./style.css";
import { useAppDispatch, useAppSelector } from "../../app/hook";
import {
  GetAdCollapsed,
  Toggle,
} from "../../app/admin/AdCollapsedSlice.reducer";

const { Content, Sider } = Layout;

const DashBoardAdmin = ({ children }) => {
  const collapsed = useAppSelector(GetAdCollapsed);
  const dispatch = useAppDispatch();

  const toggleCollapsed = () => {
    dispatch(Toggle(!collapsed));
  };

  document.querySelector("body").style.backgroundImage = "url()";

  return (
    <Space direction="vertical" style={{ width: "100%" }} size={[0, 48]}>
      <Layout className="adminLayout" style={{ backgroundColor: "white" }}>
        <Sider
          width={250}
          collapsed={collapsed}
          style={{
            height: "calc(100vh - 121px)",
            position: "fixed",
            left: 0,
            paddingBottom: "20px",
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
