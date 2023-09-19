import React, { useEffect, useState } from "react";
import { AdClassCongigurationAPI } from "../../../../api/admin/AdClassConfigurationAPI";
import { toast } from "react-toastify";
import { Button, Col, Input, Modal, Row } from "antd";

const ModalUpdateClassConfiguration = ({
  loadData,
  visible,
  onCancel,
  classConfiguration,
}) => {
  console.log(classConfiguration);
  const [classSizeMax, setClassSizeMax] = useState("");
  const [errorClassSizeMax, setErrorClassSizeMax] = useState("");

  useEffect(() => {
    if (visible) {
      setClassSizeMax(classConfiguration.classSizeMax);
      setErrorClassSizeMax("");
    } else {
      setClassSizeMax("");
      setErrorClassSizeMax("");
    }
  }, [visible, classConfiguration]);

  const update = () => {
    let check = 0;
    if (classSizeMax.trim() === "") {
      setErrorClassSizeMax("Số lượng không được để trống");
      check++;
    } else {
      setErrorClassSizeMax("");
    }
    if (check === 0) {
      let obj = {
        id: classConfiguration.id,
        classSizeMax: classSizeMax,
      };
      AdClassCongigurationAPI.update(obj).then(
        () => {
          toast.success("Cập nhật thành công!");
          onCancel();
          loadData();
        },
        (error) => {
          toast.error(error.response.data.message);
        }
      );
    }
  };

  return (
    <Modal
      visible={visible}
      onCancel={onCancel}
      width={750}
      footer={null}
      className="modal_show_detail"
    >
      <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
        <span style={{ fontSize: "18px" }}>Sửa hoạt động</span>
      </div>
      <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
        <Row style={{ marginTop: "15px" }}>
          <Col span={24}>
            <span>Số lượng sinh viên trong lớp:</span> <br />
            <Input
              value={classSizeMax}
              onChange={(e) => {
                setClassSizeMax(e.target.value);
              }}
              type="number"
            />
            <span className="error">{errorClassSizeMax}</span>
          </Col>
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
            onClick={update}
          >
            Cập nhật
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
    </Modal>
  );
};

export default ModalUpdateClassConfiguration;
