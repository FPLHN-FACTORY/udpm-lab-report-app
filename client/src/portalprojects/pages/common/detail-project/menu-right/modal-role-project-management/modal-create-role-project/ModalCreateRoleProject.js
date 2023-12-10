import { Button, Input, Modal, Radio, Row, message } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus, faUserTag } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { useAppSelector } from "../../../../../../app/hook";
import { GetProject } from "../../../../../../app/reducer/detail-project/DPProjectSlice.reducer";
import { useAppDispatch } from "../../../../../../../labreportapp/app/hook";
import { DetailProjectAPI } from "../../../../../../api/detail-project/detailProject.api";
import { AddMeRoleProject } from "../../../../../../app/reducer/detail-project/DPRoleProjectSlice.reducer";

const ModalCreateRoleProject = ({ visible, onCancel }) => {
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("");
  const [description, setDescription] = useState("");
  const [status, setStatus] = useState("1");
  const detailProject = useAppSelector(GetProject);
  const dispatch = useAppDispatch();

  useEffect(() => {
    setName("");
    setErrorName("");
    setDescription("");
    setStatus("1");
  }, [visible]);

  const create = () => {
    let check = 0;

    if (name.trim() === "") {
      setErrorName("Tên vai trò không được để trống");
      check++;
    } else {
      setErrorName("");
    }
    if (check === 0) {
      let obj = {
        name: name,
        description: description,
        roleDefault: parseInt(status),
        projectId: detailProject.id,
      };

      DetailProjectAPI.createRoleProject(obj).then(
        (response) => {
          dispatch(AddMeRoleProject(response.data.data));
          message.success("Thêm vai trò thành công !");
          onCancel();
        },
        (error) => {}
      );
    }
  };

  return (
    <>
      <Modal visible={visible} onCancel={onCancel} width={600} footer={null}>
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
            onClick={create}
          >
            <FontAwesomeIcon icon={faPlus} style={{ marginRight: 5 }} />
            Thêm
          </Button>
        </div>
      </Modal>
    </>
  );
};

export default ModalCreateRoleProject;
