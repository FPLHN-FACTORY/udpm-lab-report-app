import { Modal, Row, Col, Input, Button } from "antd";
import { useEffect, useState } from "react";
import { AdTypeProjectAPI } from "../../../../../api/admin/AdTypeProjectAPI";
import { useAppDispatch } from "../../../../../app/hook";
import {
    UpdateTypeProject
  } from "../../../../../app/admin/AdTypeProjectSlice.reducer";
  import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import TextArea from "antd/es/input/TextArea";

const ModalUpdateTypeProject = ({ visible, onCancel, typeProject }) => {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [errorName, setErrorName] = useState("Vui lòng không để trống");
    const [errorDescription, setErrorDescription] = useState("Vui lòng không để trống");
    const dispatch = useAppDispatch();

  useEffect(() => {
    if (typeProject !== null) {
      setName(typeProject.name);
      setDescription(typeProject.description);

      return () => {
      setName("");
      setErrorName();
      setDescription("");
      setErrorDescription();
      };
    }
  }, [typeProject]);

  const update = () => {
    let check = 0;
    if (name.trim() === "") {
      setErrorName("Tên Loại không được để trống");
      check++;
    } else {
      setErrorName("");
      if (name.trim().length > 500) {
        setErrorName("Tên Loại không quá 500 ký tự");
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
        id: typeProject.id,
        name: name,
        description: description,

      };

      AdTypeProjectAPI.updateTypeProject(obj, typeProject.id).then(
        (response) => {
          toast.success("Cập nhật thành công!");
          dispatch(UpdateTypeProject(response.data.data));
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
              <span>Tên Loại:</span> <br />
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
export default ModalUpdateTypeProject;