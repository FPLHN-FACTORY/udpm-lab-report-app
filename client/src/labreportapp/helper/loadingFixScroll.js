import React, { useState, useEffect } from "react";
import { Spin } from "antd";
import { Loading3QuartersOutlined } from "@ant-design/icons";

const LoadingIndicatorNoOverlayFixScroll = () => {
  const [scrollPosition, setScrollPosition] = useState(0);

  useEffect(() => {
    const handleScroll = () => {
      setScrollPosition(window.scrollY);
    };
    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return (
    <div
      style={{
        position: "fixed",
        top: `${scrollPosition}px`,
        left: 0,
        right: 0,
        bottom: 0,
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        backgroundColor: "rgba(66, 66, 66, 0.475)",
        zIndex: 999999999,
      }}
    >
      <Spin
        indicator={<Loading3QuartersOutlined style={{ fontSize: 36 }} spin />}
      />{" "}
      <br />
      <span style={{ marginLeft: "10px", color: "white", fontSize: "15px" }}>
        Vui lòng chờ ...
      </span>
    </div>
  );
};

export default LoadingIndicatorNoOverlayFixScroll;
