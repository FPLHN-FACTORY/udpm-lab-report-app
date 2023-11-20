import React, { useEffect, useState } from "react";
import { AdClassCongigurationAPI } from "../../../../../api/admin/AdClassConfigurationAPI";
import { toast } from "react-toastify";
import { Button, Col, Input, Modal, Row, message } from "antd";
import { useAppDispatch } from "../../../../../app/hook";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../../app/common/Loading.reducer";

const ModalUpdateClassConfiguration = ({
  loadData,
  visible,
  onCancel,
  classConfiguration,
}) => {
  const [classSizeMax, setClassSizeMax] = useState("");
  const [errorClassSizeMax, setErrorClassSizeMax] = useState("");
  const [classSizeMin, setClassSizeMin] = useState("");
  const [errorClassSizeMin, setErrorClassSizeMin] = useState("");
  const [pointMin, setPointMin] = useState("");
  const [numberHoney, setNumberHoney] = useState("");
  const [errorNumberHoney, setErrorNumberHoney] = useState("");
  const [errorPointMin, setErrorPointMin] = useState("");
  const [maximumNumberOfBreaks, setMaximumNumberOfBreaks] = useState("");
  const [errorMaximumNumberOfBreaks, setErrorMaximumNumberOfBreaks] =
    useState("");

  useEffect(() => {
    if (visible && classConfiguration.length > 0) {
      setClassSizeMax(classConfiguration[1].chiSo);
      setClassSizeMin(classConfiguration[0].chiSo);
      setPointMin(classConfiguration[2].chiSo);
      setMaximumNumberOfBreaks(classConfiguration[3].chiSo);
      setNumberHoney(classConfiguration[4].chiSo);
      setErrorClassSizeMax("");
      setErrorClassSizeMin("");
      setErrorPointMin("");
      setErrorNumberHoney("");
      setErrorMaximumNumberOfBreaks("");
    } else {
      setClassSizeMax("");
      setClassSizeMin("");
      setPointMin("");
      setErrorNumberHoney("");
      setNumberHoney("");
      setMaximumNumberOfBreaks("");
      setErrorClassSizeMax("");
      setErrorClassSizeMin("");
      setErrorPointMin("");
      setErrorMaximumNumberOfBreaks("");
    }
  }, [visible, classConfiguration]);
  const dispatch = useAppDispatch();
  const update = () => {
    let check = 0;
    if (classSizeMax === null || classSizeMax === "") {
      setErrorClassSizeMax("Số lượng tối đa không được để trống");
      check++;
    } else if (!Number.isInteger(+classSizeMax) || +classSizeMax <= 0) {
      setErrorClassSizeMax("Số lượng tối đa phải là số tự nhiên lớn hơn 0");
      check++;
    } else {
      setErrorClassSizeMax("");
    }

    if (classSizeMin === null || classSizeMin === "") {
      setErrorClassSizeMin("Số lượng tối thiểu không được để trống");
      check++;
    } else if (!Number.isInteger(+classSizeMin) || +classSizeMin <= 0) {
      setErrorClassSizeMin("Số lượng tối thiểu phải là số tự nhiên lớn hơn 0");
      check++;
    } else {
      setErrorClassSizeMin("");
    }

    if (+classSizeMin >= +classSizeMax) {
      setErrorClassSizeMin(
        "Số lượng tối thiểu không được lớn hơn hoặc bằng số lượng tối đa"
      );
      check++;
    }
    if (pointMin === null || pointMin === "") {
      setErrorPointMin("Điểm tối thiểu không được để trống");
      check++;
    } else if (+pointMin < 0 || +pointMin > 10) {
      setErrorPointMin("Điểm tối thiểu phải nằm trong khoảng từ 0 đến 10");
      check++;
    } else {
      setErrorPointMin("");
    }
    if (maximumNumberOfBreaks === null || maximumNumberOfBreaks === "") {
      setErrorMaximumNumberOfBreaks("Tỉ lệ nghỉ không được để trống");
      check++;
    } else if (
      !Number.isInteger(+maximumNumberOfBreaks) ||
      +maximumNumberOfBreaks < 1 ||
      +maximumNumberOfBreaks > 100
    ) {
      setErrorMaximumNumberOfBreaks(
        "Tỉ lệ nghỉ phải là số tự nhiên trong khoảng từ 1 đến 100"
      );
      check++;
    } else {
      setErrorMaximumNumberOfBreaks("");
    }
    if (numberHoney === null || numberHoney === "") {
      setErrorNumberHoney("Tỉ lệ nghỉ không được để trống");
      check++;
    } else if (!Number.isInteger(+numberHoney) || +numberHoney < 1) {
      setErrorNumberHoney("Số lượng mật ong phải là số nguyên dương >= 1");
      check++;
    } else {
      setErrorNumberHoney("");
    }

    if (check === 0) {
      dispatch(SetLoadingTrue());
      let obj = {
        classSizeMin: +classSizeMin,
        classSizeMax: +classSizeMax,
        pointMin: +pointMin,
        maximumNumberOfBreaks: +maximumNumberOfBreaks,
        numberHoney: +numberHoney,
      };
      AdClassCongigurationAPI.update(obj).then(
        () => {
          message.success("Cập nhật thành công!");
          dispatch(SetLoadingFalse());
          onCancel();
          loadData();
        },
        (error) => {
          message.error(error.response.data.message);
        }
      );
    }
  };

  return (
    <Modal
      visible={visible}
      onCancel={onCancel}
      width={750}
      footer={null}
      className="modal_show_detail_config"
    >
      <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
        <span style={{ fontSize: "18px" }}>Sửa hoạt động</span>
      </div>
      <div style={{ marginTop: "15px" }}>
        <Row gutter={16} style={{ marginBottom: "15px" }}>
          <Col span={12}>
            <span>Số lượng sinh viên tối thiểu:</span> <br />
            <Input
              value={classSizeMin}
              onChange={(e) => {
                setClassSizeMin(e.target.value);
              }}
              type="number"
            />
            <span className="error">{errorClassSizeMin}</span>
          </Col>
          <Col span={12}>
            <span>Số lượng sinh viên tối đa:</span> <br />
            <Input
              value={classSizeMax}
              onChange={(e) => {
                setClassSizeMax(e.target.value);
              }}
              x
              type="number"
            />
            <span className="error">{errorClassSizeMax}</span>
          </Col>
        </Row>
      </div>
      <div style={{ marginTop: "15px" }}>
        <Row gutter={16} style={{ marginBottom: "15px" }}>
          <Col span={12}>
            <span>Điểm tối thiểu:</span> <br />
            <Input
              value={pointMin}
              onChange={(e) => {
                setPointMin(e.target.value);
              }}
              type="number"
            />
            <span className="error">{errorPointMin}</span>
          </Col>
          <Col span={12}>
            <span>Tỉ lệ nghỉ:</span> <br />
            <Input
              value={maximumNumberOfBreaks}
              onChange={(e) => {
                setMaximumNumberOfBreaks(e.target.value);
              }}
              type="number"
            />
            <span className="error">{errorMaximumNumberOfBreaks}</span>
          </Col>
        </Row>
      </div>
      <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
        <Row style={{ marginBottom: "15px" }}>
          <Col span={24}>
            <span>Số lượng mật ong:</span> <br />
            <Input
              value={numberHoney}
              onChange={(e) => {
                setNumberHoney(e.target.value);
              }}
              type="number"
            />
            <span className="error">{errorNumberHoney}</span>
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
  );
};

export default ModalUpdateClassConfiguration;
