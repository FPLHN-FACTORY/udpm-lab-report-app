import React from "react";
import { Modal } from "antd";
import { useEffect, useState } from "react";

const ModalFullScreen = ({ visible, onCancel, item }) => {

  const [title, setTitle] = useState("");
  
  useEffect(() => {
    if (visible === true) {
      setTitle(item);
    } else {
      setTitle("");
    }
  }, [visible]);

  return (
    <Modal
      open={visible}
      onCancel={onCancel}
      width={"650px"}
      footer={null}
      bodyStyle={{ overflow: "hidden" }}
      style={{ top: "20%", height: "400px" }}
    >
      <div
        style={{
          paddingTop: "0",
          borderBottom: "1px solid black",
          textAlign: "center",
          lineHeight: "250px",
          height: "250px",
        }}
      >
        <span style={{ fontSize: "100px", fontWeight: 500 }}>{title}</span>
      </div>
      <br />
    </Modal>
  );
};

export default ModalFullScreen;
