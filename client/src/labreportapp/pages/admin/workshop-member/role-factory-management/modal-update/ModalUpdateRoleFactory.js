import { Modal, Row, Col, Input, Button, Select, message, Radio } from "antd";
import { useEffect, useState } from "react";
import { AdRoleFactoryAPI } from "../../../../../api/admin/AdRoleFactoryAPI";
import { UpdateRoleFactory } from "../../../../../app/admin/AdRoleFactorySlice.reducer";

import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../../app/hook";
import TextArea from "antd/es/input/TextArea";
import moment from "moment";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../../app/common/Loading.reducer";

const { Option } = Select;

const ModalUpdateRoleFactory = ({ visible, onCancel, roleFactory }) => {
  const [name, setName] = useState("");
  const [descriptions, setDescription] = useState("");
  const [errorName, setErrorName] = useState("");
  const [status, setStatus] = useState("");
  const [errorDescription, setErrorDescription] = useState("");
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (roleFactory !== null) {
      setName(roleFactory.name);
      setDescription(roleFactory.descriptions);
      setStatus(roleFactory.roleDefault + "");
      return () => {
        setName("");
        setErrorName("");
        setDescription("");
        setErrorDescription();
      };
    }
  }, [roleFactory, visible]);

  const update = () => {
    let check = 0;
    if (name.trim() === "") {
      setErrorName("Tên vai trò không được để trống");
      check++;
    } else {
      setErrorName("");
      if (name.trim().length > 500) {
        setErrorName("Tên vai trò không quá 500 ký tự");
        check++;
      } else {
        setErrorName("");
      }
    }

    if (descriptions.trim().length > 500) {
      setErrorDescription("Mô tả không quá 500 ký tự");
      check++;
    } else {
      setErrorDescription("");
    }

    if (check === 0) {
      let obj = {
        id: roleFactory.id,
        name: name,
        descriptions: descriptions,
        roleDefault: parseInt(status),
      };
      dispatch(SetLoadingTrue());
      AdRoleFactoryAPI.updateRoleFactory(obj, roleFactory.id).then(
        (response) => {
          message.success("Cập nhật thành công !");
          dispatch(UpdateRoleFactory(response.data.data));
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
        open={visible}
        onCancel={onCancel}
        width={750}
        footer={null}
        className="modal_show_detail_update_level"
      >
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Cập nhật Nhóm</span>
        </div>
        <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Tên nhóm:</span> <br />
              <Input
                value={name}
                placeholder="Nhập tên vai trò"
                onChange={(e) => {
                  setName(e.target.value);
                }}
                type="text"
              />
              <span style={{ color: "red" }}>{errorName}</span>
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Mô tả:</span> <br />
              <TextArea
                value={descriptions}
                placeholder="Nhập mô tả"
                onChange={(e) => {
                  setDescription(e.target.value);
                }}
                type="text"
              />
              <span style={{ color: "red" }}>{errorDescription}</span>
            </Col>
          </Row>{" "}
          <Row
            style={{
              marginTop: 20,
              display: "flex",
              justifyContent: "center",
            }}
          >
            <div style={{ marginRight: 10, marginTop: 5 }}>Trạng thái:</div>
            <Radio.Group
              value={status}
              onChange={(e) => {
                setStatus(e.target.value);
              }}
            >
              <Radio.Button value="0">Mặc định</Radio.Button>
              <Radio.Button value="1">Không mặc định</Radio.Button>
            </Radio.Group>
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
export default ModalUpdateRoleFactory;
