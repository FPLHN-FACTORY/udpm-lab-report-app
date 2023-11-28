import { Modal, Row, Col, Input, Button, message } from "antd";
import { useEffect, useState } from "react";
import { AdLevelAPI } from "../../../../api/admin/AdLevelManagerAPI";
import { UpdateLevel } from "../../../../app/admin/AdLevelManager.reducer";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../app/hook";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../app/common/Loading.reducer";

const ModalUpdateLevel = ({ visible, onCancel, level }) => {
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("");
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (visible) {
      if (level !== null) {
        setName(level.name);
        return () => {
          setName("");
          setErrorName();
        };
      }
    } else {
      setName("");
      setErrorName();
    }
  }, [level, visible]);

  const update = () => {
    let check = 0;
    if (name.trim() === "" || name === null) {
      setErrorName("Tên Level không được để trống");
      check++;
    } else {
      if (name.trim().length > 500) {
        setErrorName("Tên Level không quá 500 ký tự");
        check++;
      } else {
        setErrorName("");
      }
    }
    if (check === 0) {
      dispatch(SetLoadingTrue());
      let obj = {
        id: level.id,
        name: name,
      };

      AdLevelAPI.updateLevel(obj, level.id).then(
        (response) => {
          message.success("Cập nhật thành công !");
          dispatch(UpdateLevel(response.data.data));
          dispatch(SetLoadingFalse());
          onCancel();
        },
        (error) => {}
      );
    }
  };
  return (
    <>
      <Modal
        visible={visible}
        onCancel={onCancel}
        width={750}
        footer={null}
        className="modal_show_detail_update_level"
      >
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Cập nhật Level</span>
        </div>
        <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Tên Level:</span> <br />
              <Input
                value={name}
                onChange={(e) => {
                  setName(e.target.value);
                }}
                type="text"
              />
              <span className="error">{errorName}</span>
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
    </>
  );
};
export default ModalUpdateLevel;
