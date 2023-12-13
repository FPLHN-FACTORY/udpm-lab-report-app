import { Modal, Row, Col, Input, Button, Select, message } from "antd";
import { useEffect, useState } from "react";
import { AdTeamAPI } from "../../../../../api/admin/AdTeamAPI";
import { AddTeam, GetTeam } from "../../../../../app/admin/AdTeamSlice.reducer";

import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
import TextArea from "antd/es/input/TextArea";
import moment from "moment";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../../app/common/Loading.reducer";

const { Option } = Select;

const ModalCreateTeam = ({
  visible,
  onCancel,
  changeTotalsPage,
  totalPages,
  size,
}) => {
  const [name, setName] = useState("");
  const [descriptions, setDescription] = useState("");
  const [errorName, setErrorName] = useState("");
  const [errorDescription, setErrorDescription] = useState("");
  const dispatch = useAppDispatch();
  const data = useAppSelector(GetTeam);
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
        name: name,
        descriptions: descriptions,
      };
      dispatch(SetLoadingTrue());
      AdTeamAPI.addTeam(obj).then(
        (response) => {
          message.success("Thêm nhóm thành công !");
          dispatch(AddTeam(response.data.data));
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
        open={visible}
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
                placeholder="Nhập tên nhóm"
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
                placeholder="Nhập mô tả"
                value={descriptions}
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

export default ModalCreateTeam;
