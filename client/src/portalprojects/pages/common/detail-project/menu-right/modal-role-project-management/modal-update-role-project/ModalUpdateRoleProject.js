import { Button, Input, Modal, Radio, Row, message } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faEdit,
  faPencil,
  faPlus,
  faUserTag,
} from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { useAppDispatch } from "../../../../../../../labreportapp/app/hook";
import { DetailProjectAPI } from "../../../../../../api/detail-project/detailProject.api";
import { UpdateMeRoleProject } from "../../../../../../app/reducer/detail-project/DPRoleProjectSlice.reducer";

const ModalUpdateRoleProject = ({ visible, onCancel, item }) => {
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("");
  const [description, setDescription] = useState("");
  const [status, setStatus] = useState("1");
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (item != null) {
      setName(item.name);
      setDescription(item.description);
      setStatus(item.roleDefault + "");
    }

    return () => {
      setName("");
      setErrorName("");
      setDescription("");
      setStatus("0");
    };
  }, [visible]);

  const update = () => {
    let check = 0;

    if (name.trim() === "") {
      setErrorName("Tên vai trò không được để trống");
      check++;
    } else {
      setErrorName("");
    }
    if (check === 0) {
      let obj = {
        id: item.id,
        name: name,
        description: description,
        roleDefault: parseInt(status),
      };

      DetailProjectAPI.updateRoleProject(obj).then(
        (response) => {
          dispatch(UpdateMeRoleProject(response.data.data));
          message.success("Cập nhật thành công !");
          onCancel();
        },
        (error) => {}
      );
    }
  };
  return (
    <>
      <Modal open={visible} onCancel={onCancel} width={600} footer={null}>
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>
            <FontAwesomeIcon icon={faUserTag} style={{ marginRight: "7px" }} />
            Thêm vai trò
          </span>
        </div>
        <div style={{ marginTop: "15px" }}>
          <Row>
            Tên vai trò:
            <Input
              value={name}
              onChange={(e) => {
                setName(e.target.value);
              }}
              type="text"
              placeholder="Nhập tên vai trò"
            />
            <span style={{ color: "red" }}>{errorName}</span>
          </Row>
          <Row style={{ marginTop: 10 }}>
            Mô tả vai trò:
            <Input.TextArea
              value={description}
              onChange={(e) => {
                setDescription(e.target.value);
              }}
              rows={5}
              type="text"
              placeholder="Nhập mô tả vai trò"
            />
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
        <div style={{ display: "flex", justifyContent: "flex-end" }}>
          <Button
            style={{
              backgroundColor: "rgb(55, 137, 220)",
              color: "white",
            }}
            onClick={update}
          >
            <FontAwesomeIcon icon={faEdit} style={{ marginRight: 5 }} />
            Cập nhật
          </Button>
        </div>
      </Modal>
    </>
  );
};

export default ModalUpdateRoleProject;
