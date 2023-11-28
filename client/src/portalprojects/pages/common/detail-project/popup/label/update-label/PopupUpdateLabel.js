import "./stylePopupUpdateLabel.css";
import React, { useEffect, useRef } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTags, faUsers } from "@fortawesome/free-solid-svg-icons";
import { Button, Col, ColorPicker, Input, Row, message } from "antd";
import { useState } from "react";
import { GetProject } from "../../../../../../app/reducer/detail-project/DPProjectSlice.reducer";
import { useAppDispatch, useAppSelector } from "../../../../../../app/hook";
import { getStompClient } from "../../../stomp-client-config/StompClientManager";
import Cookies from "js-cookie";
import { DownOutlined } from "@ant-design/icons";

const PopupUpdateLabel = ({ position, onClose, item }) => {
  const popupRef = useRef(null);
  const [colorLabel, setColorLabel] = useState(item.colorLabel);
  const [open, setOpen] = useState(false);
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

  const [title, setTitle] = useState(item.name);
  const [color, setColor] = useState(item.colorLabel);
  const detailProject = useAppSelector(GetProject);
  const stompClient = getStompClient();

  const handleSelectColor = (color) => {
    const { r, g, b, a } = color;
    const rgbColor = `rgb(${Math.round(r)}, ${Math.round(g)}, ${Math.round(
      b
    )})`;
    setColor(rgbColor);
  };

  const updateLabel = () => {
    if (title.trim() === "") {
      message.error("Tên nhãn không được để trống");
      return;
    }
    let obj = {
      id: item.id,
      name: title,
      color: color,
    };
    const bearerToken = Cookies.get("token");
    const headers = {
      Authorization: "Bearer " + bearerToken,
    };
    stompClient.send(
      "/action/update-label/" + detailProject.id,
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
          Cập nhật nhãn
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
            value={title}
            onChange={(e) => {
              setTitle(e.target.value);
            }}
            placeholder="Nhập tiêu đề của nhãn"
          />
        </div>
        <div style={{ marginTop: "10px" }}>
          <span style={{ fontSize: "14px" }}>Chọn màu: </span> <br />
          <div className="color-options-container">
            <ColorPicker
              open={open}
              getPopupContainer={(triggerNode) => triggerNode.parentNode}
              value={colorLabel}
              onChange={(e) => {
                handleSelectColor(e.metaColor);
              }}
              onOpenChange={setOpen}
              showText={() => (
                <DownOutlined
                  rotate={open ? 180 : 0}
                  style={{
                    color: "rgba(0, 0, 0, 0.25)",
                  }}
                />
              )}
            />
          </div>
        </div>
        <div style={{ marginTop: "10px" }}>
          <Button className="btn_add_label" onClick={updateLabel}>
            Cập nhật
          </Button>
        </div>
      </div>
    </div>
  );
};

export default PopupUpdateLabel;
