import React, { useState } from "react";
import { Modal, Button, Row, Input, message } from "antd";
import { AdMeetingRequestAPI } from "../../../../api/admin/AdMeetingRequestAPI";

const ModalReasons = ({
  visible,
  onCancel,
  idClass,
  noApproveMeetingRequest,
}) => {
  const [reasons, setReasons] = useState("");

  const noApprove = () => {
    if (reasons.trim() === "") {
      message.error("Lí do từ chối không được để trống");
      return;
    }
    noApproveMeetingRequest();
    addReasons();
    onCancel();
  };

  const addReasons = () => {
    AdMeetingRequestAPI.addReasons(idClass, reasons).then(
      (response) => {},
      (error) => {}
    );
  };

  return (
    <>
      <Modal
        open={visible}
        onCancel={onCancel}
        width={800}
        footer={null}
        className="modal_show_detail_project"
      >
        <div>
          <div style={{ paddingTop: "0" }}>
            <span style={{ fontSize: "18px" }}>Lí do từ chối</span>
          </div>
          <div style={{ marginTop: "15px" }}>
            <Row>
              <Input.TextArea
                value={reasons}
                placeholder="Nhập lí do từ chối"
                rows={6}
                onChange={(e) => {
                  setReasons(e.target.value);
                }}
              ></Input.TextArea>
            </Row>
          </div>

          <div style={{ textAlign: "right" }}>
            <div style={{ paddingTop: "15px" }}>
              <Button
                style={{
                  marginRight: "5px",
                  backgroundColor: "rgb(61, 139, 227)",
                  color: "white",
                }}
                onClick={noApprove}
              >
                Từ chối
              </Button>
              <Button
                style={{
                  backgroundColor: "red",
                  color: "white",
                }}
                onClick={onCancel}
              >
                Hủy
              </Button>
            </div>
          </div>
        </div>
      </Modal>
    </>
  );
};
export default ModalReasons;
