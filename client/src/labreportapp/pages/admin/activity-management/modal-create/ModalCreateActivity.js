import "./styleModalCreateActivity.css";
import {
  Modal,
  Row,
  Col,
  Input,
  Button,
  DatePicker,
  Select,
  message,
} from "antd";
import { useEffect, useState } from "react";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { toast } from "react-toastify";
import { ActivityManagementAPI } from "../../../../api/admin/activity-management/activityManagement.api";
import {
  CreateActivityManagement,
  GetActivityManagement,
} from "../../../../app/admin/activity-management/activityManagementSlice.reducer";
import { Option } from "antd/es/mentions";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../app/common/Loading.reducer";
import dayjs from "dayjs";
const { RangePicker } = DatePicker;

const ModalCreateActivity = ({
  visible,
  onCancel,
  listSemester,
  listLevel,
  fetchData,
  changeTotalsPage,
  totalPages,
  size,
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
  const [errorTime, setErrorTime] = useState("");
  const [errorLevel, setErrorLevel] = useState("");
  const [errorSemesterId, setErrorSemesterId] = useState("");
  const [allowUseTrello, setAllowUseTrello] = useState("1");
  const dispatch = useAppDispatch();
  const data = useAppSelector(GetActivityManagement);

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
        setErrorTime("");
        setErrorLevel("");
        setErrorSemesterId("");
      };
    }
  }, [visible]);
  const create = () => {
    let check = 0;
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
      setErrorTime("Hãy chọn thời gian bắt đầu");
      check++;
    } else {
      setErrorTime("");
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
    if (startTime < semesterNameItem.startTime) {
      setErrorTime("Thời gian hoạt động phải nằm trong thời gian kì học");
      check++;
    }
    if (new Date(endTime) > semesterNameItem.endTime) {
      setErrorTime("Thời gian hoạt động phải nằm trong thời gian kì học");
      check++;
    }
    if (check === 0) {
      dispatch(SetLoadingTrue());

      let obj = {
        name: name,
        code: code,
        startTime: startTime,
        endTime: endTime,
        level: level,
        semesterId: semesterId,
        descriptions: descriptions,
        allowUseTrello: parseInt(allowUseTrello),
      };
      ActivityManagementAPI.create(obj).then(
        (response) => {
          message.success("Thêm thành công!");
          let objCreate = {
            ...response.data.data,
            nameSemester: semesterNameItem.name,
            levelNameItem: levelNameItem.name,
          };
          dispatch(CreateActivityManagement(objCreate));
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

  const handleDateChange = (e) => {
    if (e != null) {
      setStartTime(
        dayjs(e[0]).set({ hour: 0, minute: 0, second: 0 }).valueOf()
      );
      setEndTime(dayjs(e[1]).set({ hour: 0, minute: 0, second: 0 }).valueOf());
    } else {
      setStartTime(null);
      setEndTime(null);
    }
  };

  return (
    <Modal
      visible={visible}
      onCancel={onCancel}
      width={750}
      footer={null}
      className="modal_show_detail_create_activity"
    >
      {" "}
      <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
        <span style={{ fontSize: "18px" }}>Thêm hoạt động</span>
      </div>
      <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
        <Row style={{ marginTop: "15px" }}>
          <Col span={12} style={{ padding: "5px" }}>
            <span style={{ color: "red" }}>(*) </span>
            <span>Mã:</span> <br />
            <Input
              value={code}
              placeholder="Nhập mã hoạt động"
              onChange={(e) => {
                setCode(e.target.value);
              }}
              type="text"
            />
            <span className="error">{errorCode}</span>
          </Col>
          <Col span={12} style={{ padding: "5px" }}>
            <span style={{ color: "red" }}>(*) </span>
            <span>Tên hoạt động:</span> <br />
            <Input
              value={name}
              placeholder="Nhập tên hoạt động"
              onChange={(e) => {
                setName(e.target.value);
              }}
              type="text"
            />
            <span className="error">{errorName}</span>
          </Col>

          <Col span={24} style={{ padding: "5px" }}>
            <span className="notBlank">(*) </span>
            <span>Thời gian:</span> <br />
            <RangePicker
              style={{ width: "100%" }}
              format="DD-MM-YYYY"
              value={[
                startTime ? dayjs(startTime) : null,
                endTime ? dayjs(endTime) : null,
              ]}
              onChange={(e) => {
                handleDateChange(e);
              }}
            />{" "}
            <span className="error">{errorTime}</span>
          </Col>

          <Col span={12} style={{ padding: "5px" }}>
            <span style={{ color: "red" }}>(*) </span> Cấp độ:
            <Select
              style={{ width: "100%" }}
              value={level}
              onChange={(value) => {
                setLevel(value);
              }}
            >
              {" "}
              <Option value="">Hãy chọn cấp độ</Option>
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

          <Col span={12} style={{ padding: "5px" }}>
            <span style={{ color: "red" }}>(*) </span> Tên học kỳ:
            <Select
              value={semesterId}
              onChange={(value) => {
                setSemesterId(value);
              }}
              style={{ width: "100%" }}
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
          <Col span={24} style={{ padding: "5px" }}>
            <span style={{ color: "red" }}>(*) </span>Cho phép sử dụng trello
            kéo thả: <br />
            <Select
              value={allowUseTrello}
              style={{ width: "100%" }}
              onChange={(e) => {
                setAllowUseTrello(e);
              }}
            >
              <Option value="1">Không cho phép</Option>
              <Option value="0">Cho phép</Option>
            </Select>
          </Col>
          <Col span={24} style={{ padding: "5px" }}>
            <span>Mô tả:</span> <br />
            <Input.TextArea
              value={descriptions}
              rows={8}
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
