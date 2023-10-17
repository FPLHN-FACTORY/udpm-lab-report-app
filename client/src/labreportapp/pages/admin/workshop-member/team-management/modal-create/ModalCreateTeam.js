import { Modal, Row, Col, Input, Button, Select } from "antd";
import { useEffect, useState } from "react";
import { AdTeamAPI } from "../../../../../api/admin/AdTeamAPI";
import { AddTeam } from "../../../../../app/admin/AdTeamSlice.reducer";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../../app/hook";
import TextArea from "antd/es/input/TextArea";
import moment from "moment";

const { Option } = Select;

const ModalCreateTeam = ({ visible, onCancel }) => {
  const [name, setName] = useState("");
  const [subjectName, setSubjectName] = useState("");
  const [errorName, setErrorName] = useState("Vui lòng không để trống");
  const [errorSubjectName, setErrorSubjectName] = useState("Vui lòng không để trống");
  const dispatch = useAppDispatch();

  useEffect(() => {
    return () => {
      setName("");
      setErrorName("");
      setSubjectName("");
      setErrorSubjectName("");
    };
  }, [visible]);

  const create = () => {
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
    if (subjectName.trim() === "") {
      setErrorSubjectName("Đề tài không được để trống");
      check++;
    } else {
      setErrorSubjectName("");
      if (subjectName.trim().length > 500) {
        setErrorSubjectName("Đề tài không quá 500 ký tự");
        check++;
      } else {
        setErrorSubjectName("");
      }
    }
    if (check === 0) {
      let obj = {
        name: name,
        subjectName: subjectName,
      };

      AdTeamAPI.addTeam(obj).then(
        (response) => {
          toast.success("Thêm nhóm thành công!");
          dispatch(AddTeam(response.data.data));
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
          <span style={{ fontSize: "18px" }}>Thêm mới nhóm</span>
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
                type="text"
              />
              <span style={{ color: "red" }}>{errorName}</span>
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Tên đề tài:</span> <br />
              <TextArea
                value={subjectName}
                onChange={(e) => {
                  setSubjectName(e.target.value);
                }}
                type="text"
              />
              <span style={{ color: "red" }}>{errorSubjectName}</span>
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

export default ModalCreateTeam;