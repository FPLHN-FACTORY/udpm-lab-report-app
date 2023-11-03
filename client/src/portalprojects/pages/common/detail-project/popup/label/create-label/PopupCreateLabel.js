import "./stylePopupCreateLabel.css";
import React, { useEffect, useRef } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTags, faUsers } from "@fortawesome/free-solid-svg-icons";
import { Button, Col, Input, Row, message } from "antd";
import { useState } from "react";
import { GetProject } from "../../../../../../app/reducer/detail-project/DPProjectSlice.reducer";
import { useAppDispatch, useAppSelector } from "../../../../../../app/hook";
import { getStompClient } from "../../../stomp-client-config/StompClientManager";
import Cookies from "js-cookie";

const PopupCreateLabel = ({ position, onClose }) => {
  const popupRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (!popupRef.current.contains(event.target)) {
        onClose();
      }
    };

    const handleEscapeKey = (event) => {
      if (event.key === "Escape") {
        onClose();
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    document.addEventListener("keydown", handleEscapeKey);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
      document.removeEventListener("keydown", handleEscapeKey);
    };
  }, [onClose]);

  const popupStyle = {
    position: "fixed",
    top: "30px",
    left: "65%",
    zIndex: 9999,
    width: "300px",
    backgroundColor: "white",
    padding: "10px",
    borderRadius: "15px",
    boxShadow: "0 2px 5px rgba(0, 0, 0, 0.3)",
  };

  const [title, setTitle] = useState("");
  const [color, setColor] = useState("#F87060");
  const detailProject = useAppSelector(GetProject);
  const stompClient = getStompClient();

  const colors = [
    "#F87060",
    "#FFC07F",
    "#FF9C9C",
    "#FF7F7F",
    "#FF9F3D",
    "#FF1A1A",
    "#9B59B6",
    "#F1C40F",
    "#FFBF00",
    "#70A6FF",
    "#C39CFF",
    "#8165FC",
    "#7EF9AF",
    "#FF6363",
    "#FF0000",
    "#2ECC71",
    "#3498DB",
    "#FF5733",
    "#F39C12",
    "#E74C3C",
    "#8E44AD",
    "#27AE60",
    "#2980B9",
    "#E67E22",
    "#E91E63",
    "#27AE60",
    "#FFA500",
    "#FF1493",
    "#FFD700",
    "#1E90FF",
    "#00FF7F",
    "#4682B4",
  ];

  const handleSelectColor = (color) => {
    setColor(color);
  };

  const handleAddLabel = () => {
    if (title.trim() === "") {
      message.error("Tên nhãn không được để trống");
      return;
    }
    let obj = {
      name: title,
      color: color,
      projectId: detailProject.id,
    };
    const bearerToken = Cookies.get("token");
    const headers = {
      Authorization: "Bearer " + bearerToken,
    };
    stompClient.send(
      "/action/create-label/" + detailProject.id,
      headers,
      JSON.stringify(obj)
    );

    onClose();
  };

  return (
    <div ref={popupRef} style={popupStyle} className="popup-period">
      <div className="popup-header">
        <h4>
          <FontAwesomeIcon icon={faTags} style={{ marginRight: "5px" }} />
          Thêm nhãn
        </h4>
        <span onClick={onClose} className="close-button">
          &times;
        </span>
      </div>
      <div style={{ marginTop: "10px", marginBottom: "6px" }}>
        <div className="box_selector">
          <div className="content_selector" style={{ backgroundColor: color }}>
            {title}
          </div>
        </div>
        <div style={{ marginTop: "10px" }}>
          <span style={{ fontSize: "14px" }}>Tiêu đề: </span> <br />
          <Input
            autoFocus={true}
            type="text"
            onChange={(e) => {
              setTitle(e.target.value);
            }}
            placeholder="Nhập tiêu đề của nhãn"
          />
        </div>
        <div style={{ marginTop: "10px" }}>
          <span style={{ fontSize: "14px" }}>Chọn màu: </span> <br />
          <div className="color-options-container">
            <Row gutter={[10, 10]}>
              {colors.map((color, index) => (
                <Col key={index} span={6}>
                  <div
                    className="color-option"
                    style={{ backgroundColor: color }}
                    onClick={() => {
                      handleSelectColor(color);
                    }}
                  />
                </Col>
              ))}
            </Row>
          </div>
        </div>
        <div style={{ marginTop: "10px" }}>
          <Button onClick={handleAddLabel} className="btn_add_label">
            Thêm nhãn
          </Button>
        </div>
      </div>
    </div>
  );
};

export default PopupCreateLabel;
