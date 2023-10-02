import React, { useState } from "react";
import { Layout, Space } from "antd";
import SidebarTeacherComponent from "../../component/teacher/SidebarTeacher";
import HeaderTeacherComponent from "../../component/teacher/HeaderTeacher";
import "./style.css";
import { useAppDispatch, useAppSelector } from "../../app/hook";
import {
  GetTeCollapsed,
  Toggle,
} from "../../app/teacher/TeCollapsedSlice.reducer";

const { Content, Sider, Footer } = Layout;

const DashBoardTeacher = ({ children }) => {
  const collapsed = useAppSelector(GetTeCollapsed);
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
          <SidebarTeacherComponent
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
          <HeaderTeacherComponent
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

export default DashBoardTeacher;
