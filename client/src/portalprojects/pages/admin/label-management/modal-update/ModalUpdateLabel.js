import "./styleModalUpdateLabel.css";
import { Modal, Row, Col, Input, Button, message, ColorPicker } from "antd";
import { useEffect, useState } from "react";
import { LabelManagementAPI } from "../../../../api/label-management/labelManagement.api";
import { useAppDispatch } from "../../../../app/hook";
import { UpdateLabelManagement } from "../../../../app/reducer/admin/label-management/labelManagementSlice.reducer";
import { DownOutlined } from "@ant-design/icons";

const ModalUpdateLabel = ({ visible, onCancel, idLabel, label }) => {
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("");
  const [colorLabel, setColorLabel] = useState("rgb(0, 123, 255)");
  const dispatch = useAppDispatch();
  const [open, setOpen] = useState(false);
  useEffect(() => {
    if (visible && label != null) {
      setName(label.name);
      setColorLabel(label.colorLabel);
    }
    if (!visible) {
      setName("");
      setColorLabel("rgb(0, 123, 255)");
      setErrorName("");
    }
  }, [visible]);

  const handleColorChange = (color) => {
    const { r, g, b, a } = color;
    const rgbColor = `rgb(${Math.round(r)}, ${Math.round(g)}, ${Math.round(
      b
    )})`;
    setColorLabel(rgbColor);
  };

  const update = () => {
    let check = 0;

    if (name === "") {
      setErrorName("Tên nhãn không được để trống");
      check++;
    } else {
      setErrorName("");
    }

    if (check === 0) {
      let obj = {
        id: label.id,
        name: name,
        colorLabel: colorLabel,
      };
      LabelManagementAPI.update(obj).then(
        (response) => {
          message.success("Cập nhật thành công!");
          dispatch(UpdateLabelManagement(response.data.data));
          onCancel();
        },
        (error) => {}
      );
    }
  };
  return (
    <Modal visible={visible} onCancel={onCancel} width={750} footer={null}>
      {" "}
      <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
        <span style={{ fontSize: "18px" }}>Cập nhật nhãn</span>
      </div>
      <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
        <Row gutter={16} style={{ marginBottom: "15px" }}>
          <Col span={21}>
            <span>Tên nhãn:</span> <br />
            <Input
              value={name}
              onChange={(e) => {
                setName(e.target.value);
              }}
              type="text"
            />
            <span className="error">{errorName}</span>
          </Col>
          <Col span={3}>
            {" "}
            <span>Màu sắc:</span> <br />
            <ColorPicker
              open={open}
              value={colorLabel}
              onChange={(e) => {
                handleColorChange(e.metaColor);
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
          </Col>
        </Row>
      </div>
      <div style={{ textAlign: "right" }}>
        <div style={{ paddingTop: "15px" }}>
          <Button
            className="btn_filter"
            style={{
              backgroundColor: "red",
              color: "white",
              width: "100px",
            }}
            onClick={onCancel}
          >
            Hủy
          </Button>{" "}
          <Button
            className="btn_clean"
            style={{ width: "100px", marginLeft: "10px" }}
            onClick={update}
          >
            Cập nhật
          </Button>
        </div>
      </div>
    </Modal>
  );
};

export default ModalUpdateLabel;
