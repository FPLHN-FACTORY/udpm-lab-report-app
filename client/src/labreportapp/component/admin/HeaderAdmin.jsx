import React, { useState } from "react";
import { Layout, Input } from "antd";
import {
  SearchOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined,
} from "@ant-design/icons";
import "./style-header.css";
import CommonInforUser from "../common/CommonInforUser";
import logoUdpm3 from "../../assets/img/logo-udpm-3.png";
import logoBit from "../../assets/img/logo_bit.jpg";

const { Header } = Layout;

const HeaderAdminComponent = ({ collapsed, toggleCollapsed }) => {
  return (
    <div className="sticky-header">
      <Header
        className={collapsed ? "collapsed" : ""}
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          padding: "0 24px",
        }}
      >
        <div style={{ display: "flex", alignItems: "center" }}>
          <div className="title_logo">
            {" "}
            <img src={logoUdpm3} className="logo_project" /> LabReport
          </div>
          <div
            className="sidebar-toggle"
            onClick={toggleCollapsed}
            style={{
              cursor: "pointer",
              width: "32px",
              marginRight: "20px",
              height: "32px",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
              backgroundColor: "rgb(226, 179, 87)",
              borderRadius: "50%",
              color: "#fff",
            }}
          >
            {collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
          </div>
        </div>
        <div style={{ display: "flex", alignItems: "center" }}>
          <CommonInforUser />
        </div>
      </Header>
    </div>
  );
};

export default HeaderAdminComponent;
