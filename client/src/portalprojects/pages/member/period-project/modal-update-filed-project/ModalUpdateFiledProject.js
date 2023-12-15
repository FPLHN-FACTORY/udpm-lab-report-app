import {
  Modal,
  Row,
  Col,
  Input,
  Button,
  Select,
  Tooltip,
  Space,
  Spin,
  message,
  DatePicker,
} from "antd";
import { useEffect, useState } from "react";
import "./styleModalUpdateProject.css";
import { useAppDispatch } from "../../../../app/hook";
import "react-toastify/dist/ReactToastify.css";
import { ProjectManagementAPI } from "../../../../api/admin/project-management/projectManagement.api";
import { AdGroupProjectAPI } from "../../../../../labreportapp/api/admin/AdGroupProjectAPI";
import { CategoryProjectManagementAPI } from "../../../../api/admin/project-management/categoryProjectManagement.api";
import { SetProject } from "../../../../app/reducer/detail-project/DPProjectSlice.reducer";
import { SetProjectCustom } from "../../../../app/reducer/detail-project/DPDetailProjectCustom.reduce";
import { DetailProjectAPI } from "../../../../api/detail-project/detailProject.api";
import locale from "antd/es/date-picker/locale/vi_VN";
import dayjs from "dayjs";
import "dayjs/locale/vi";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../../labreportapp/app/common/Loading.reducer";
const { RangePicker } = DatePicker;
const { TextArea } = Input;
const { Option } = Select;

const ModalUpdateFiledProject = ({ visible, onCancel, idProject }) => {
  const dispatch = useAppDispatch();
  const [code, setCode] = useState("");
  const [errorCode, setErrorCode] = useState("");
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [errorTime, setErrorTime] = useState("");
  const [descriptions, setDescriptions] = useState("");
  const [errorDescriptions, setErrorDescriptions] = useState("");
  const [errorCategorys, setErrorCategorys] = useState("");
  const [listCategorysChange, setListCategorysChange] = useState([]);
  const [listCategory, setListCategory] = useState([]);
  const [loading, setLoading] = useState(false);
  const [listGroupProject, setListGroupProject] = useState([]);
  const [selectedGroupProject, setSelectedGroupProject] = useState(null);

  useEffect(() => {
    if (idProject !== null && idProject !== "" && visible === true) {
      setListCategorysChange([]);
      setSelectedGroupProject(null);
      setErrorCode("");
      setErrorName("");
      setErrorDescriptions("");
      setErrorTime("");
      setErrorCategorys("");
      featDataGroupProject();
      fetchDataCategory();
      featchProject();
    }
  }, [idProject, visible]);

  const handleChangeCategorys = (idCategory) => {
    setListCategorysChange(idCategory);
  };

  const fetchDataCategory = async () => {
    try {
      const responeGetAllCategory =
        await CategoryProjectManagementAPI.fetchAllCategory();
      setListCategory(responeGetAllCategory.data.data);
    } catch (error) {}
  };

  const featDataGroupProject = async () => {
    try {
      await AdGroupProjectAPI.getAllGroupToProjectManagement().then(
        (response) => {
          setListGroupProject(response.data.data);
        }
      );
    } catch (error) {}
  };

  const featchProject = async () => {
    try {
      setLoading(true);
      await ProjectManagementAPI.detailUpdate(idProject).then((response) => {
        let obj = response.data.data;
        setCode(obj.code);
        setName(obj.name);
        setStartTime(obj.startTime);
        setEndTime(obj.endTime);
        setDescriptions(obj.descriptions);
        setSelectedGroupProject(obj.groupProjectId);
        setListCategorysChange(
          obj.listCategory != null && obj.listCategory.map((i) => i.categoryId)
        );
        setLoading(false);
      });
    } catch (error) {}
  };

  const handleChangeGroupProject = (value) => {
    setSelectedGroupProject(value);
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
  const update = () => {
    let check = 0;
    dispatch(SetLoadingTrue());
    if (code.trim() === "") {
      setErrorCode("Mã không được để trống");
      check++;
    } else {
      setErrorCode("");
    }
    if (name.trim() === "") {
      setErrorName("Tên không được để trống");
      check++;
    } else {
      setErrorName("");
    }
    if (startTime == null && endTime == null) {
      setErrorTime("Thời gian không được để trống");
      check++;
    } else {
      setErrorTime("");
    }
    if (startTime === null && endTime !== null) {
      setErrorTime("Thời gian bắt đầu không được để trống");
      check++;
    }
    if (endTime === null && startTime !== null) {
      setErrorTime("Thời gian kết thúc không được để trống");
      check++;
    }
    if (listCategorysChange.length <= 0) {
      setErrorCategorys("Thể loại không được để trống");
      check++;
    } else {
      setErrorCategorys("");
    }
    if (descriptions.length > 1000) {
      setErrorDescriptions("Mô tả không vượt quá 1000 ký tự");
      check++;
    } else {
      setErrorDescriptions("");
    }
    if (check === 0) {
      let projectUp = {
        id: idProject,
        code: code.trim(),
        name: name.trim(),
        descriptions: descriptions.trim(),
        startTime: startTime,
        endTime: endTime,
        groupProjectId: selectedGroupProject,
        listCategorysId: listCategorysChange,
      };
      DetailProjectAPI.updateFiledProject(projectUp).then(
        (response) => {
          message.success("Cập nhật thành công !");
          dispatch(SetProject(response.data.data.project));
          dispatch(SetProjectCustom(response.data.data.projectCustom));
          dispatch(SetLoadingFalse());
          onCancel();
        },
        (error) => {
          dispatch(SetLoadingFalse());
        }
      );
    }
  };

  return (
    <>
      <Modal
        open={visible}
        onCancel={onCancel}
        width={1150}
        footer={null}
        style={{ top: "53px" }}
      >
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Cập nhật dự án</span>
        </div>
        {loading ? (
          <div
            className="loading-overlay"
            style={{
              display: "flex",
              flexDirection: "column",
              justifyContent: "center",
              alignItems: "center",
              minHeight: "540px",
            }}
          >
            <Spin size="large" style={{ paddingTop: "50px" }} />
          </div>
        ) : (
          <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
            <Row gutter={24} style={{ marginBottom: "10px" }}>
              <Col span={12}>
                <span>
                  <span className="notBlank">(*) </span>
                  Mã dự án:
                </span>{" "}
                <br />
                <Input
                  placeholder="Nhập mã"
                  value={code}
                  onChange={(e) => {
                    setCode(e.target.value);
                  }}
                  type="text"
                />
                <span className="error">{errorCode}</span>
              </Col>
              <Col span={12}>
                <span>
                  <span className="notBlank">(*) </span>Tên dự án:
                </span>{" "}
                <br />
                <Input
                  placeholder="Nhập tên"
                  value={name}
                  onChange={(e) => {
                    setName(e.target.value);
                  }}
                  type="text"
                />
                <span className="error">{errorName}</span>
              </Col>
            </Row>
            <Row gutter={24} style={{ marginBottom: "10px" }}>
              <Col span={12}>
                <span className="notBlank">(*) </span>
                <span>Thời gian:</span> <br />
                <RangePicker
                  locale={locale}
                  style={{ width: "100%" }}
                  format="YYYY-MM-DD"
                  value={[
                    startTime ? dayjs(startTime) : null,
                    endTime ? dayjs(endTime) : null,
                  ]}
                  onChange={(e) => {
                    handleDateChange(e);
                  }}
                />
                <span className="error">{errorTime}</span>
              </Col>
              <Col span={12}>
                <span>Nhóm dự án:</span>
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  placeholder="Chọn thuộc nhóm dự án"
                  optionFilterProp="children"
                  filterOption={(input, option) =>
                    (option?.label ?? "").includes(input)
                  }
                  filterSort={(optionA, optionB) =>
                    (optionA?.label ?? "")
                      .toLowerCase()
                      .localeCompare((optionB?.label ?? "").toLowerCase())
                  }
                  value={selectedGroupProject}
                  onChange={(e) => handleChangeGroupProject(e)}
                  defaultValue={selectedGroupProject}
                  options={[
                    { value: null, label: "Không trong nhóm dự án" },
                    ...listGroupProject.map((i) => {
                      return { value: i.id, label: i.name };
                    }),
                  ]}
                />
              </Col>
            </Row>
            <Row gutter={24} style={{ marginBottom: "10px" }}>
              <Col span={24}>
                <div style={{ width: "100%" }}>
                  {" "}
                  <span className="notBlank">(*) </span>
                  <span>Thể loại:</span>
                  <Select
                    mode="multiple"
                    placeholder="Thêm thể loại"
                    style={{
                      width: "100%",
                      height: "auto",
                    }}
                    value={listCategorysChange}
                    onChange={handleChangeCategorys}
                    optionLabelProp="label"
                    filterOption={(text, option) =>
                      option.label.toLowerCase().indexOf(text.toLowerCase()) !==
                      -1
                    }
                  >
                    {listCategory.map((record) => (
                      <Option
                        label={record.name}
                        value={record.id}
                        key={record.id}
                      >
                        <Tooltip title={record.name}>
                          <Space>{record.name}</Space>
                        </Tooltip>
                      </Option>
                    ))}
                  </Select>
                  <span
                    className="error"
                    style={{ display: "block", marginTop: "2px" }}
                  >
                    {errorCategorys}
                  </span>
                </div>
              </Col>
            </Row>
            <Row gutter={24} style={{ marginBottom: "10px" }}>
              {" "}
              <Col span={24}>
                <span className="notBlank">*</span>
                <span>Mô tả:</span> <br />
                <TextArea
                  placeholder="Nhập mô tả"
                  rows={2}
                  value={descriptions}
                  onChange={(e) => {
                    setDescriptions(e.target.value);
                  }}
                />
                <span className="error">{errorDescriptions}</span>
              </Col>
            </Row>
          </div>
        )}
        <div style={{ textAlign: "right" }}>
          <div style={{ paddingTop: "15px" }}>
            <Button
              className="btn_filter"
              style={{
                backgroundColor: "red",
                color: "white",
                width: "100px",
              }}
              onClick={onCancel}
            >
              Hủy
            </Button>{" "}
            <Button
              className="btn_clean"
              style={{ width: "100px", marginLeft: "10px" }}
              onClick={update}
            >
              Cập nhật
            </Button>
          </div>
        </div>
      </Modal>
    </>
  );
};

export default ModalUpdateFiledProject;
