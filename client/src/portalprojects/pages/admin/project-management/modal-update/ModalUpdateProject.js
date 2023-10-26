import {
  Modal,
  Row,
  Col,
  Input,
  Button,
  Select,
  Tooltip,
  Space,
  Table,
  Spin,
  message,
  DatePicker,
} from "antd";
import { useEffect, useState } from "react";
import "./styleModalUpdateProject.css";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import "react-toastify/dist/ReactToastify.css";
import { ProjectManagementAPI } from "../../../../api/admin/project-management/projectManagement.api";
import { UpdateProject } from "../../../../app/reducer/admin/project-management/projectManagementSlide.reducer";
import { SearchOutlined } from "@ant-design/icons";
import { AdPotalsRoleProjectAPI } from "../../../../api/admin/role-project/AdPotalsRoleProject.api";
import dayjs from "dayjs";
import { AdPotalsMemberFactoryAPI } from "../../../../api/admin/member-factory/AdPotalsMemberFactory.api";
import { GetCategory } from "../../../../app/reducer/admin/category-management/adCategorySlice.reducer";
import { AdGroupProjectAPI } from "../../../../../labreportapp/api/admin/AdGroupProjectAPI";
import Image from "../../../../helper/img/Image";
const { RangePicker } = DatePicker;
const { TextArea } = Input;
const { Option } = Select;

const ModalUpdateProject = ({ visible, onCancel, idProject }) => {
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
  const [listMember, setListMember] = useState([]);
  const [listMemberChange, setListMemberChange] = useState([]);
  const [loading, setLoading] = useState(false);
  const [listUpdateMember, setListUpdateMember] = useState([]);
  const [listRoleProject, setListRoleProject] = useState([]);
  const [listMemberRole, setListMemberRole] = useState([]);
  const [listGroupProject, setListGroupProject] = useState([]);
  const [selectedGroupProject, setSelectedGroupProject] = useState(null);
  const dataCategory = useAppSelector(GetCategory);

  useEffect(() => {
    if (idProject !== null && idProject !== "" && visible === true) {
      setListCategorysChange([]);
      setListMemberChange([]);
      setListUpdateMember([]);
      setSelectedGroupProject(null);
      setErrorCode("");
      setErrorName("");
      setErrorDescriptions("");
      setErrorTime("");
      setErrorCategorys("");
      featDataGroupProject();
      featchRoleProject(idProject);
      featChMultiSelect();
      featchProject();
    }
  }, [idProject, visible]);

  useEffect(() => {
    if (visible) {
      featMemberAndCaregoryAddProject();
    }
  }, [listCategorysChange, listMemberChange]);

  const cancelUpdateSuccess = () => {
    onCancel.handleCancelModalCreateSusscess();
  };
  const cancelUpdateFaild = () => {
    onCancel.handleCancelModalCreateFaild();
  };

  const handleChangeMembers = (idMember) => {
    setListMemberChange(idMember);
  };
  const handleChangeCategorys = (idCategory) => {
    setListCategorysChange(idCategory);
  };

  const featChMultiSelect = async () => {
    try {
      await AdPotalsMemberFactoryAPI.getAllMemberActive().then((respone) => {
        setListMember(respone.data.data);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const featDataGroupProject = async () => {
    try {
      await AdGroupProjectAPI.getAllGroupToProjectManagement().then(
        (response) => {
          setListGroupProject(response.data.data);
        }
      );
    } catch (error) {
      console.log(error);
    }
  };

  const featchRoleProject = async (idProject) => {
    try {
      await AdPotalsRoleProjectAPI.getAllRoleProjectByProjId(idProject).then(
        (response) => {
          setListRoleProject(response.data.data);
        }
      );
    } catch (error) {
      console.log(error);
    }
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
        setListMemberChange(
          obj.listMemberRole != null &&
            obj.listMemberRole.map((i) => i.memberId)
        );
        setSelectedGroupProject(obj.groupProjectId);
        setListMemberRole(obj.listMemberRole);
        setListCategorysChange(
          obj.listCategory != null && obj.listCategory.map((i) => i.categoryId)
        );
        setTimeout(() => {
          setLoading(false);
        }, 200);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const featMemberAndCaregoryAddProject = async () => {
    try {
      const memberRoles = {};
      listMemberRole.forEach((memberRole) => {
        memberRoles[memberRole.memberId] = memberRole.listRole;
      });

      const roleDefault = listRoleProject.find(
        (item) => item.roleDefault === "DEFAULT"
      );

      const membersInChange = listMember.filter((member) =>
        listMemberChange.includes(member.memberId)
      );

      const updatedListMemberJoinCreate = membersInChange.map((obj1) => {
        let updatedRoles = memberRoles[obj1.memberId] || [];
        if (updatedRoles.length === 0) {
          if (roleDefault) {
            updatedRoles.push({
              idRole: roleDefault.id,
              nameRole: roleDefault.name,
            });
          }
        }
        return {
          ...obj1,
          listRole: updatedRoles,
        };
      });
      setListUpdateMember(updatedListMemberJoinCreate);
    } catch (error) {
      console.log(error);
    }
  };

  const handleChangeGroupProject = (value) => {
    setSelectedGroupProject(value);
  };

  const handleRoleChange = (id, value) => {
    const selectedRoles = Array.isArray(value) ? value : value.split(",");

    let updatedListInfo = listUpdateMember.map((record) => {
      if (record.memberId === id) {
        let newData = {
          memberId: record.memberId,
          email: record.email,
          listRole: [],
        };

        selectedRoles.forEach((roleId) => {
          let selectedRole = listRoleProject.find((role) => role.id === roleId);
          if (selectedRole) {
            newData.listRole.push({
              idRole: selectedRole.id,
              nameRole: selectedRole.name,
            });
          }
        });
        return { ...record, ...newData };
      }
      return record;
    });
    setListUpdateMember(updatedListInfo);
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
    if (code === "") {
      setErrorCode("Mã không được để trống");
      check++;
    } else {
      setErrorCode("");
    }
    if (name === "") {
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
    let checkRole = 0;
    listUpdateMember.map((item) => {
      if (item.listRole.length === 0) {
        checkRole++;
        check++;
      }
    });
    if (checkRole > 0) {
      message.error("Vai trò của thành viên không được trống");
    }
    if (check === 0) {
      let projectNew = {
        code: code.trim(),
        name: name.trim(),
        descriptions: descriptions.trim(),
        startTime: startTime,
        endTime: endTime,
        groupProjectId: selectedGroupProject,
        listMember: listUpdateMember,
        listCategorysId: listCategorysChange,
      };
      ProjectManagementAPI.updateProject(projectNew, idProject).then(
        (respone) => {
          message.success("Cập nhật thành công !");
          let data = respone.data.data;
          dispatch(UpdateProject(data));
          cancelUpdateSuccess();
        },
        (error) => {
          console.log(error);
        }
      );
    }
  };

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
    },
    {
      title: "Mã",
      dataIndex: "userName",
      key: "userName",
      sorter: (a, b) => a.userName.localeCompare(b.userName),
    },
    {
      title: "Thành viên",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      render: (text, record) => {
        return (
          <div style={{ display: "flex", alignItems: "center" }}>
            <Image
              url={record.picture}
              picxel={25}
              marginRight={8}
              name={record.name}
            />
            <span style={{ paddingLeft: "8px" }}>{record.name}</span>
          </div>
        );
      },
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: "8px" }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: "8px", display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: "90px", marginRight: "8px" }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: "90px" }}>
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
      dataIndex: "roleCustome",
      key: "roleCustome",

      render: (text, record) => (
        <Select
          mode="multiple"
          placeholder="Chọn vai trò"
          value={record.listRole.map((item) => item.idRole)}
          style={{
            width: "100%",
          }}
          onChange={(e) => handleRoleChange(record.memberId, e)}
          key={record.idRole}
          options={listRoleProject.map((item) => ({
            value: item.id,
            label: item.name,
          }))}
        />
      ),
    },
  ];

  return (
    <>
      <Modal
        visible={visible}
        onCancel={cancelUpdateFaild}
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
                    {dataCategory.map((record) => (
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
              <Col span={24}>
                <span>Thành viên:</span>
                {listMemberChange.length > 0 && (
                  <span style={{ color: "red" }}>
                    {" "}
                    (*) Vui lòng chọn vai trò khi đã thêm tất cả thành viên
                  </span>
                )}
                <Select
                  mode="multiple"
                  placeholder="Thêm thành viên"
                  style={{
                    width: "100%",
                    height: "auto",
                  }}
                  value={listMemberChange}
                  onChange={handleChangeMembers}
                  optionLabelProp="label"
                  filterOption={(text, option) =>
                    option.label.toLowerCase().indexOf(text.toLowerCase()) !==
                    -1
                  }
                >
                  {listMember.map((member) => (
                    <Option
                      label={member.email}
                      value={member.memberId}
                      key={member.id}
                    >
                      <Tooltip title={member.email}>
                        <div style={{ display: "flex", alignItems: "center" }}>
                          <Image
                            url={member.picture}
                            picxel={25}
                            marginRight={8}
                            name={member.name}
                          />
                          <span>
                            {member.name +
                              " " +
                              member.userName +
                              " (" +
                              member.email +
                              ")"}
                          </span>
                        </div>
                      </Tooltip>
                    </Option>
                  ))}{" "}
                </Select>
              </Col>
            </Row>
            {listUpdateMember.length > 0 && (
              <Row gutter={24} style={{ marginBottom: "10px" }}>
                {" "}
                <Col span={24}>
                  <Table
                    className="table-member-management"
                    columns={columns}
                    dataSource={listUpdateMember}
                    rowKey="id"
                  />
                </Col>{" "}
              </Row>
            )}
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
              onClick={cancelUpdateFaild}
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

export default ModalUpdateProject;
