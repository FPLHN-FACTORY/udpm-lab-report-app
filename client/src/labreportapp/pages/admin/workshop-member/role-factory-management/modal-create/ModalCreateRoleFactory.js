import { Modal, Row, Col, Input, Button, Select, message, Radio } from "antd";
import { useEffect, useState } from "react";
import { AdRoleFactoryAPI } from "../../../../../api/admin/AdRoleFactoryAPI";
import { AddRoleFactory } from "../../../../../app/admin/AdRoleFactorySlice.reducer";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../../app/hook";
import TextArea from "antd/es/input/TextArea";
import moment from "moment";
import { parseInt } from "lodash";

const { Option } = Select;

const ModalCreateRoleFactory = ({ visible, onCancel }) => {
  const [name, setName] = useState("");
  const [status, setStatus] = useState("1");
  const [descriptions, setDescription] = useState("");
  const [errorName, setErrorName] = useState("");
  const [errorDescription, setErrorDescription] = useState("");
  const dispatch = useAppDispatch();

  useEffect(() => {
    return () => {
      setName("");
      setErrorName("");
      setDescription("");
      setErrorDescription("");
    };
  }, [visible]);

  const create = () => {
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
        name: name,
        descriptions: descriptions,
        roleDefault: parseInt(status),
      };

      AdRoleFactoryAPI.addRoleFactory(obj).then(
        (response) => {
          message.success("Thêm vai trò thành công!");
          dispatch(AddRoleFactory(response.data.data));
          console.log(obj.descriptions);
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
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Thêm mới vai trò</span>
        </div>
        <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Tên vai trò:</span> <br />
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
          </Row>
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

export default ModalCreateRoleFactory;
