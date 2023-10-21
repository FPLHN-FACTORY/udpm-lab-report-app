import React from "react";
import {
  Modal,
  Row,
  Col,
  Input,
  Button,
  Select,
  Space,
  Image,
  Tooltip,
  Table,
  message,
  DatePicker,
} from "antd";
import "./styleModalCreateProject.css";
import { useEffect, useState } from "react";
import moment from "moment";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch } from "../../../../app/hook";
import { ProjectManagementAPI } from "../../../../api/admin/project-management/projectManagement.api";
import { CreateProject } from "../../../../app/reducer/admin/project-management/projectManagementSlide.reducer";
import { CommonAPI } from "../../../../api/commonAPI";
import LoadingIndicator from "../../../../helper/loading";
import { CategoryProjectManagementAPI } from "../../../../api/admin/project-management/categoryProjectManagement.api";
import { SearchOutlined } from "@ant-design/icons";
import dayjs from "dayjs";
import { AdRoleConfigAPI } from "../../../../api/admin/role-config/AdPotalsRoleConfig.api";
import { AdPotalsMemberFactoryAPI } from "../../../../api/admin/member-factory/AdPotalsMemberFactory.api";
const { RangePicker } = DatePicker;
const { Option } = Select;
const { TextArea } = Input;

const ModalCreateProject = ({ visible, onCancel }) => {
  const [code, setCode] = useState("");
  const [errorCode, setErrorCode] = useState("");
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("");
  const [startTime, setStartTime] = useState(null);
  const [errorTime, setErrorTime] = useState("");
  const [endTime, setEndTime] = useState(null);
  const [descriptions, setDescriptions] = useState("");
  const [errorDescriptions, setErrorDescriptions] = useState("");
  const [errorMembers, setErrorMembers] = useState("");
  const [errorCategorys, setErrorCategorys] = useState("");
  const [listCategorys, setListCategorys] = useState([]);
  const [listCategorysChange, setListCategorysChange] = useState([]);
  const [listCategoryName, setListCategoryName] = useState("");
  const [listCategoryProjects, setListCategoryProjects] = useState([]);
  const [listMembers, setListMembers] = useState([]);
  const [listMembersChange, setListMembersChange] = useState([]);
  const [listMemberProjects, setListMemberProjects] = useState([]);

  const [listRoleConfig, setListRoleConfig] = useState([]);
  const [loading, setLoading] = useState(false);
  const dispatch = useAppDispatch();

  const cancelSuccess = () => {
    onCancel.handleCancelModalCreateSusscess();
  };
  const cancelFaild = () => {
    onCancel.handleCancelModalCreateFaild();
  };

  useEffect(() => {
    featChMultiSelect();
  }, []);

  useEffect(() => {
    if (visible) {
      featchRoleConfig();
      return () => {
        setCode("");
        setName("");
        setStartTime(null);
        setEndTime(null);
        setErrorCode("");
        setDescriptions("");
        setErrorCategorys("");
        setErrorDescriptions("");
        setErrorTime("");
        setErrorName();
        setListCategorysChange([]);
        setListMembersChange([]);
        setListCategoryProjects([]);
        setListMemberProjects([]);
        setListCategoryName("");
      };
    }
  }, [visible]);

  useEffect(() => {
    if (visible) {
      setListMembersChange(listMembersChange);
      setListCategorysChange(listCategorysChange);
      featMemberAndCaregoryAddProject();
    }
  }, [listCategorysChange, listMembersChange]);

  const featchRoleConfig = async () => {
    try {
      await AdRoleConfigAPI.getAllRoleConfig().then((response) => {
        setListRoleConfig(response.data.data);
        console.log("//////////////////");
        console.log(response);
      });
    } catch (error) {}
  };

  const featChMultiSelect = async () => {
    setLoading(true);
    try {
      await AdPotalsMemberFactoryAPI.getAllMemberActive().then((respone) => {
        setListMembers(respone.data.data);
        console.log(respone.data.data);
      });
      await CategoryProjectManagementAPI.fetchAllCategory().then((respone) => {
        setListCategorys(respone.data.data);
        setLoading(false);
      });
    } catch (error) {
      alert("Vui lòng load lại trang");
    }
  };

  const fetchCategoryData = async (id) => {
    const response =
      await CategoryProjectManagementAPI.fetchCategoryWithIdProject(id);
    return response.data.data;
  };

  const handleChangeMembers = (idMember) => {
    setListMembersChange(idMember);
  };

  const handleChangeCategorys = (idCategory) => {
    setListCategorysChange(idCategory);
  };

  const handleRoleChange = (id, value) => {
    let updatedListInfo = listMemberProjects.map((record) => {
      if (record.memberId === id) {
        let newData = {
          memberId: record.memberId,
          email: record.email,
          role: value + "",
          statusWork: record.statusWork,
        };
        return { ...record, ...newData };
      }
      return record;
    });
    setListMemberProjects(updatedListInfo);
  };

  const featMemberAndCaregoryAddProject = async () => {
    try {
      const listMemberJoinCreate = await listMembers
        .filter((obj1) => listMembersChange.some((obj2) => obj1.id === obj2))
        .map((obj1) => {
          const matchedItem = listMembersChange.find(
            (obj2) => obj2.id === obj1.id
          );
          return { ...obj1, ...matchedItem };
        });

      const listMemberAdd = listMemberJoinCreate.map((member) => {
        return {
          memberId: member.id,
          email: member.email,
          role: `Dev`,
          statusWork: `0`,
          name: member.name,
          userName: member.userName,
          picture: member.picture,
        };
      });
      setListMemberProjects(listMemberAdd);
      setListCategoryProjects(listCategorysChange);
    } catch (error) {
      alert("Hệ thống lỗi, vui lòng F5 lại trang");
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
  const create = () => {
    let check = 0;
    if (code.trim() === "") {
      setErrorCode("Mã dự án không được để trống");
      check++;
    } else {
      setErrorCode("");
    }
    if (name.trim() === "") {
      setErrorName("Tên dự án không được để trống");
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
    if (check === 0) {
      featMemberAndCaregoryAddProject();
      let projectNew = {
        code: code,
        name: name,
        descriptions: descriptions,
        startTime: startTime,
        endTime: endTime,
        listMembers: listMemberProjects,
        listCategorysId: listCategorysChange,
      };
      ProjectManagementAPI.createProject(projectNew).then(
        (respone) => {
          message.success("Thêm thành công !");
          let data = respone.data.data;
          let dataAddTable = {
            ...data,
            stt: 1,
          };
          dispatch(CreateProject(dataAddTable));
          cancelSuccess();
        },
        (error) => {
          console.log(error);
        }
      );
    }
  };
  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
      width: "7%",
    },
    {
      title: "Mã",
      dataIndex: "userName",
      key: "userName",
      width: 50,
      sorter: (a, b) => a.userName.localeCompare(b.userName),
    },
    {
      title: "Thành viên",
      dataIndex: "name",
      key: "name",
      width: 250,
      sorter: (a, b) => a.name.localeCompare(b.name),
      render: (text, record) => {
        return (
          <div>
            <Image.PreviewGroup>
              <Image
                src={record.picture}
                alt="Avatar"
                width={25}
                height={25}
                marginRight={-5}
                className="avatarMember"
              />
            </Image.PreviewGroup>
            <span style={{ paddingLeft: "8px" }}>{record.name}</span>
          </div>
        );
      },
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      width: "25%",
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) =>
        record.email.toLowerCase().includes(value.toLowerCase()),
      sorter: (a, b) => a.email.localeCompare(b.email),
      sortDirections: ["ascend", "descend"],
    },
    {
      title: "Vai trò",
      dataIndex: "role",
      key: "role",
      width: "15%",
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Select
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(value) => setSelectedKeys(value ? [value] : [])}
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          >
            {listRoleConfig.length === 0
              ? "Không có vai trò để chọn"
              : listRoleConfig.map((item) => {
                  <Option value={item.name}>{item.name}</Option>;
                })}
          </Select>
          <div style={{ display: "flex", justifyContent: "flex-end" }}>
            <Button
              type="primary"
              onClick={confirm}
              size="small"
              style={{ marginRight: 8 }}
            >
              Tìm
            </Button>
            <Button onClick={clearFilters} size="small">
              Đặt lại
            </Button>
          </div>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) => record.role === value,
      sorter: (a, b) => a.role.localeCompare(b.role),
      render: (text, record) => (
        <Select
          style={{ width: "100%" }}
          value={record.role}
          onChange={(value) => handleRoleChange(record.memberId, value)}
        >
          {listRoleConfig.length > 0
            ? listRoleConfig.map((item) => (
                <Option value={item.name}>{item.name}</Option>
              ))
            : "Không có vai trò để chọn"}
        </Select>
      ),
    },
  ];

  return (
    <>
      {loading && <LoadingIndicator />}
      <Modal
        visible={visible}
        onCancel={cancelFaild}
        width={1000}
        footer={null}
        bodyStyle={{ overflow: "hidden" }}
        style={{ top: "53px" }}
      >
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Thêm dự án</span>
        </div>
        <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
          <Row gutter={24} style={{ marginBottom: "15px" }}>
            <Col span={12}>
              {" "}
              <span className="notBlank">(*) </span>
              <span>Mã dự án:</span> <br />
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
              {" "}
              <span className="notBlank">(*) </span>
              <span>Tên dự án:</span> <br />
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
          <Row gutter={24} style={{ marginBottom: "15px" }}>
            <Col span={12}>
              <span className="notBlank">(*) </span>
              <span>Thời gian:</span> <br />
              <RangePicker
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
              <div style={{ width: "100%" }}>
                {" "}
                <span className="notBlank">(*) </span>
                <span>Thể loại:</span>
                <Select
                  mode="multiple"
                  placeholder="Thêm thể loại"
                  dropdownMatchSelectWidth={false}
                  style={{
                    width: "100%",
                    height: "auto",
                  }}
                  value={listCategorysChange}
                  onChange={handleChangeCategorys}
                  optionLabelProp="label"
                  defaultValue={[]}
                  filterOption={(text, option) =>
                    option.label.toLowerCase().indexOf(text.toLowerCase()) !==
                    -1
                  }
                >
                  {listCategorys.map((record) => (
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
          <Row style={{ marginBottom: "15px" }}>
            <div style={{ width: "100%" }}>
              {" "}
              <span>Thành viên:</span>
              <Select
                mode="multiple"
                placeholder="Thêm thành viên"
                dropdownMatchSelectWidth={false}
                style={{
                  width: "100%",
                  height: "auto",
                }}
                value={listMembersChange}
                onChange={handleChangeMembers}
                optionLabelProp="label"
                defaultValue={[]}
                filterOption={(text, option) =>
                  option.label.toLowerCase().indexOf(text.toLowerCase()) !== -1
                }
              >
                {listMembers.map((member) => (
                  <Option
                    label={member.email}
                    value={member.id}
                    key={member.id}
                  >
                    <Tooltip title={member.email}>
                      <Space>
                        <Image.PreviewGroup>
                          <Image
                            src={
                              member.picture ||
                              "https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png"
                            }
                            alt="Avatar"
                            width={25}
                            height={25}
                            className="avatarMember"
                          />
                        </Image.PreviewGroup>
                        {member.name +
                          " " +
                          member.userName +
                          " (" +
                          member.email +
                          ")"}
                        {}
                      </Space>
                    </Tooltip>
                  </Option>
                ))}{" "}
              </Select>
              <span
                className="error"
                style={{ display: "block", marginTop: "2px" }}
              >
                {errorMembers}
              </span>
            </div>
          </Row>
          {listMemberProjects.length > 0 && (
            <Row>
              {" "}
              <Col span={24}>
                <Table
                  className="table-member-management"
                  columns={columns}
                  dataSource={listMemberProjects}
                  rowKey="id"
                />
              </Col>{" "}
            </Row>
          )}
          <Row gutter={16} style={{ marginBottom: "20px" }}>
            {" "}
            <Col span={24}>
              {" "}
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
        <div
          style={{
            textAlign: "right",
            marginTop: "20px",
          }}
        >
          <div style={{ paddingTop: "15px" }}>
            <Button
              style={{
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
              onClick={cancelFaild}
            >
              Hủy
            </Button>
          </div>
        </div>
      </Modal>
    </>
  );
};

export default ModalCreateProject;
