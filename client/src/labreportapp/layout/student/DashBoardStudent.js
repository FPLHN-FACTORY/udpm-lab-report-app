import React, { useState } from "react";
import { Layout, Space } from "antd";
import SidebarStudentComponent from "../../component/student/SidebarStudent";
import HeaderStudentComponent from "../../component/student/HeaderStudent";
import "./style.css";
import { useAppDispatch, useAppSelector } from "../../app/hook";
import { GetStCollapsed, Toggle } from "../../app/student/StCollapsedSlice.reducer";

const { Content, Sider } = Layout;

const DashBoardStudent = ({ children }) => {
  const collapsed = useAppSelector(GetStCollapsed);
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
            height: "100vh",
            position: "fixed",
            left: 0,
          }}
        >
          <SidebarStudentComponent
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
          <HeaderStudentComponent
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

export default DashBoardStudent;