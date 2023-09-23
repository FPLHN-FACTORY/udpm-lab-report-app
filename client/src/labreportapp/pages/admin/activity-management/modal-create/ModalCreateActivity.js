import "./styleModalCreateActivity.css";
import { Modal, Row, Col, Input, Button, DatePicker, Select } from "antd";
import { useEffect, useState } from "react";
import { useAppDispatch } from "../../../../app/hook";
import { toast } from "react-toastify";
import { ActivityManagementAPI } from "../../../../api/admin/activity-management/activityManagement.api";
import { CreateActivityManagement } from "../../../../app/admin/activity-management/activityManagementSlice.reducer";
import { Option } from "antd/es/mentions";

const ModalCreateActivity = ({
  visible,
  onCancel,
  listSemester,
  listLevel,
  fetchData,
}) => {
  const [name, setName] = useState("");
  const [code, setCode] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [level, setLevel] = useState("");
  const [semesterId, setSemesterId] = useState("");
  const [descriptions, setDescriptions] = useState("");
  const [errorName, setErrorName] = useState("");
  const [errorCode, setErrorCode] = useState("");
  const [errorStartTime, setErrorStartTime] = useState("");
  const [errorEndTime, setErrorEndTime] = useState("");
  const [errorLevel, setErrorLevel] = useState("");
  const [errorSemesterId, setErrorSemesterId] = useState("");
  const dispatch = useAppDispatch();

  useEffect(() => {
    if (visible === true) {
      if (listSemester.length > 0) {
        setSemesterId(listSemester[0].id);
      }
      return () => {
        setName("");
        setCode("");
        setStartTime("");
        setEndTime("");
        setLevel("");
        setSemesterId("");
        setErrorCode("");
        setErrorName("");
        setErrorStartTime("");
        setErrorEndTime("");
        setErrorLevel("");
        setErrorSemesterId("");
      };
    }
  }, [visible]);
  const create = () => {
    let check = 0;
    let checkDate = 0;
    if (name.trim() === "") {
      setErrorName("Tên không được để trống");
      check++;
    } else {
      setErrorName("");
    }
    if (code.trim() === "") {
      setErrorCode("Mã không được để trống");
      check++;
    } else {
      setErrorCode("");
    }
    if (startTime === "" || startTime === null) {
      setErrorStartTime("Hãy chọn thời gian bắt đầu");
      checkDate++;
      check++;
    } else {
      setErrorStartTime("");
    }

    if (endTime === "" || endTime === null) {
      setErrorEndTime("Hãy chọn thời gian kết thúc");
      checkDate++;
      check++;
    } else {
      setErrorEndTime("");
    }
    if (checkDate === 0) {
      if (new Date(startTime) > new Date(endTime)) {
        setErrorStartTime(
          "Thời gian bắt đầu không được lớn hơn thời gian kết thúc"
        );
        check++;
      }
    }
    if (level === "") {
      setErrorLevel("Hãy chọn cấp độ");
      check++;
    } else {
      setErrorLevel("");
    }

    const semesterNameItem = listSemester.find(
      (item) => item.id === semesterId
    );
    const levelNameItem = listLevel.find((item) => item.id === level);
    if (new Date(startTime) < new Date(semesterNameItem.startTime)) {
      setErrorStartTime("Thời gian hoạt động phải nằm trong thời gian kì học");
      check++;
    }
    if (new Date(endTime) > new Date(semesterNameItem.endTime)) {
      setErrorEndTime("Thời gian hoạt động phải nằm trong thời gian kì học");
      check++;
    }
    if (check === 0) {
      let obj = {
        name: name,
        code: code,
        startTime: startTime,
        endTime: endTime,
        level: level,
        semesterId: semesterId,
        descriptions: descriptions,
      };
      console.log(code, descriptions, level);
      ActivityManagementAPI.create(obj).then(
        (response) => {
          toast.success("Thêm thành công!");
          let objCreate = {
            ...response.data.data,
            nameSemester: semesterNameItem.name,
            levelNameItem: levelNameItem.name,
          };
          dispatch(CreateActivityManagement(objCreate));
          onCancel();
          fetchData();
        },
        (error) => {
          toast.error(error.response.data.message);
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
      className="modal_show_detail"
    >
      {" "}
      <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
        <span style={{ fontSize: "18px" }}>Thêm hoạt động</span>
      </div>
      <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
        <Row gutter={16} style={{ marginTop: "15px" }}>
          <Col span={24}>
            <span>Mã</span> <br />
            <Input
              value={code}
              onChange={(e) => {
                setCode(e.target.value);
              }}
              type="text"
            />
            <span className="error">{errorCode}</span>
          </Col>
        </Row>
        <Row gutter={16} style={{ marginTop: "15px" }}>
          <Col span={24}>
            <span>Tên:</span> <br />
            <Input
              value={name}
              onChange={(e) => {
                setName(e.target.value);
              }}
              type="text"
            />
            <span className="error">{errorName}</span>
          </Col>
        </Row>
        <Row gutter={16} style={{ marginTop: "15px" }}>
          <Col span={12}>
            <span>Thời gian bắt đầu:</span> <br />
            <Input
              value={startTime}
              onChange={(e) => {
                setStartTime(e.target.value);
              }}
              type="date"
            />
            <span className="error">{errorStartTime}</span>
          </Col>
          <Col span={12}>
            <span>Thời gian kết thúc:</span> <br />
            <Input
              value={endTime}
              onChange={(e) => {
                setEndTime(e.target.value);
              }}
              type="date"
            />
            <span className="error">{errorEndTime}</span>
          </Col>
        </Row>
        <Row gutter={16} style={{ marginTop: "15px" }}>
          <Col span={24}>
            <span>Cấp độ:</span> <br />
            <Select
              style={{ width: "100%" }}
              value={level}
              onChange={(value) => {
                setLevel(value);
              }}
            >
              {listLevel.map((value) => {
                return (
                  <Option value={value.id} key={value.id}>
                    {value.name}
                  </Option>
                );
              })}
            </Select>
            <span className="error">{errorLevel}</span>
          </Col>
        </Row>
        <Row gutter={16} style={{ marginTop: "15px", marginBottom: "15px" }}>
          <Col span={24}>
            <span>Tên học kỳ:</span> <br />
            <Select
              value={semesterId}
              onChange={(value) => {
                setSemesterId(value);
              }}
              style={{ marginTop: "6px", width: "100%" }}
            >
              {listSemester.map((value) => {
                return (
                  <Option value={value.id} key={value.id}>
                    {value.name}
                  </Option>
                );
              })}
            </Select>
            <span className="error">{errorSemesterId}</span>
          </Col>
        </Row>
        <Row gutter={16} style={{ marginTop: "15px" }}>
          <Col span={24}>
            <span>Mô tả</span> <br />
            <Input
              value={descriptions}
              onChange={(e) => {
                setDescriptions(e.target.value);
              }}
              type="text"
            />
          </Col>
        </Row>
        <br />
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
  );
};
export default ModalCreateActivity;
