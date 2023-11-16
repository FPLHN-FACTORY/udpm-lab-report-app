import { Modal, Row, Col, Input, Button, message } from "antd";
import { useEffect, useState } from "react";
import { AdLevelAPI } from "../../../../api/admin/AdLevelManagerAPI";
import { useAppSelector } from "../../../../app/hook";
import {
  AddLevel,
  GetLevel,
} from "../../../../app/admin/AdLevelManager.reducer";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../app/hook";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../app/common/Loading.reducer";

const ModalCreateLevel = ({
  visible,
  onCancel,
  changeTotalsPage,
  totalPages,
  size,
}) => {
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("aaa");
  const dispatch = useAppDispatch();
  const data = useAppSelector(GetLevel);
  useEffect(() => {
    return () => {
      setName("");
      setErrorName();
    };
  }, [visible]);

  const create = () => {
    let check = 0;
    if (name.trim() === "") {
      setErrorName("Tên Level không được để trống");
      check++;
    } else {
      setErrorName("");
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
        name: name,
      };

      AdLevelAPI.addLevel(obj).then(
        (response) => {
          message.success("Thêm thành công!");
          dispatch(AddLevel(response.data.data));
          dispatch(SetLoadingFalse());
          if (data != null) {
            if (data.length + 1 > size) {
              changeTotalsPage(totalPages + 1);
            } else if (data.length + 1 === 1) {
              changeTotalsPage(1);
            }
          }
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
        className="modal_show_detail_create_level"
      >
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Thêm mới Level</span>
        </div>
        <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span style={{ color: "red" }}>(*) </span>
              <span>Tên Level:</span> <br />
              <Input
                value={name}
                placeholder="Nhập tên level"
                onChange={(e) => {
                  setName(e.target.value);
                }}
                type="text"
              />
              <span style={{ color: "red" }}>{errorName}</span>
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
              onClick={create}
            >
              Thêm
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

export default ModalCreateLevel;
