import { Modal, Row, Col, Input, Button, Select, message } from "antd";
import { useEffect, useState } from "react";
import { AdTeamAPI } from "../../../../../api/admin/AdTeamAPI";
import { UpdateTeam } from "../../../../../app/admin/AdTeamSlice.reducer";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../../app/hook";
import TextArea from "antd/es/input/TextArea";
import moment from "moment";

const { Option } = Select;

const ModalUpdateTeam = ({ visible, onCancel, team }) => {
  const [name, setName] = useState("");
  const [descriptions, setDescription] = useState("");
  const [errorName, setErrorName] = useState("");
  const [errorDescription, setErrorDescription] = useState("");
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (team !== null) {
      setName(team.name);
      setDescription(team.descriptions);
    }

    return () => {
      setName("");
      setErrorName("");
      setDescription("");
      setErrorDescription();
    };
  }, [team, visible]);

  const update = () => {
    let check = 0;
    if (name.trim() === "") {
      setErrorName("Tên nhóm không được để trống");
      check++;
    } else {
      setErrorName("");
      if (name.trim().length > 500) {
        setErrorName("Tên nhóm không quá 500 ký tự");
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
        id: team.id,
        name: name,
        descriptions: descriptions,
      };

      AdTeamAPI.updateTeam(obj, team.id).then(
        (response) => {
          message.success("Cập nhật thành công!");
          dispatch(UpdateTeam(response.data.data));
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
          <span style={{ fontSize: "18px" }}>Cập nhật Nhóm</span>
        </div>
        <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Tên nhóm:</span> <br />
              <Input
                value={name}
                onChange={(e) => {
                  setName(e.target.value);
                }}
                placeholder="Nhập tên nhóm"
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
export default ModalUpdateTeam;