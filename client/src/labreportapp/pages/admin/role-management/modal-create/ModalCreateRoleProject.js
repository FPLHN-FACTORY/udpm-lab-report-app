import { Modal, Row, Col, Input, Button, Select } from "antd";
import { useEffect, useState } from "react";
import { AdRoleProjectAPI } from "../../../../api/admin/AdRoleProjectAPI";
import { AddRoleProject } from "../../../../app/admin/AdRoleProjectSlice.reducer";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../app/hook";
import TextArea from "antd/es/input/TextArea";
import moment from "moment";

const { Option } = Select;

const ModalCreateRoleProject = ({ visible, onCancel }) => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [errorName, setErrorName] = useState("Vui lòng không để trống");
  const [errorDescription, setErrorDescription] = useState("Vui lòng không để trống");
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
      setErrorName("Tên Vai trò không được để trống");
      check++;
    } else {
      setErrorName("");
      if (name.trim().length > 500) {
        setErrorName("Tên Vai trò không quá 500 ký tự");
        check++;
      } else {
        setErrorName("");
      }
    }
    if (description.trim() === "") {
      setErrorDescription("Mô tả không được để trống");
      check++;
    } else {
      setErrorDescription("");
      if (description.trim().length > 500) {
        setErrorDescription("Mô tả không quá 500 ký tự");
        check++;
      } else {
        setErrorDescription("");
      }
    }
    if (check === 0) {
      let obj = {
        name: name,
        description: description,
      };

      AdRoleProjectAPI.addRoleProject(obj).then(
        (response) => {
          toast.success("Thêm Loại thành công!");
          dispatch(AddRoleProject(response.data.data));
          console.log(obj.idProject);
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
          <span style={{ fontSize: "18px" }}>Thêm mới Vai trò</span>
        </div>
        <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Tên Vai trò:</span> <br />
              <Input
                value={name}
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
                value={description}
                onChange={(e) => {
                  setDescription(e.target.value);
                }}
                type="text"
              />
              <span style={{ color: "red" }}>{errorDescription}</span>
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

export default ModalCreateRoleProject;