import {
  Modal,
  Row,
  Col,
  Input,
  Button,
  Select,
  Tooltip,
  Space,
  Image,
  Table,
  Spin,
  message,
  DatePicker,
} from "antd";
import { useEffect, useState } from "react";
import "./styleModalUpdateProject.css";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import "react-toastify/dist/ReactToastify.css";
import { CategoryProjectManagementAPI } from "../../../../api/admin/project-management/categoryProjectManagement.api";
import { ProjectManagementAPI } from "../../../../api/admin/project-management/projectManagement.api";
import { UpdateProject } from "../../../../app/reducer/admin/project-management/projectManagementSlide.reducer";
import {
  SetCategoryProject,
  GetCategoryProject,
} from "../../../../app/reducer/admin/category-project-management/categoryProjectManagement.reduce";
import {
  SetMemberProject,
  GetMemberProject,
} from "../../../../app/reducer/admin/member-project-management/memberProjectManagement.reduce";
import { SearchOutlined } from "@ant-design/icons";
import { AdPotalsRoleProjectAPI } from "../../../../api/admin/role-project/AdPotalsRoleProject.api";
import dayjs from "dayjs";
import { AdPotalsMemberFactoryAPI } from "../../../../api/admin/member-factory/AdPotalsMemberFactory.api";
import { GetCategory } from "../../../../app/reducer/admin/category-management/adCategorySlice.reducer";
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
  const [errorMembers, setErrorMembers] = useState("");
  const [errorCategorys, setErrorCategorys] = useState("");
  const [listCategorys, setListCategorys] = useState([]); //getAll
  const [listCategorysChange, setListCategorysChange] = useState([]);
  const [listCategoryProjects, setListCategoryProjects] = useState([]); // add
  const [listCategoryNotJoin, setListCategoryNotJoin] = useState([]);
  const [listCategoryJoin, setListCategoryJoin] = useState([]);
  const [listMember, setListMember] = useState([]); //getAll
  const [listMemberChange, setListMemberChange] = useState([]); //change
  const [listMemberProjects, setListMemberProjects] = useState([]); //add
  const [listMemberNotJoin, setListMemberNotJoin] = useState([]);
  const [listMemberJoin, setListMemberJoin] = useState([]);
  const [loading, setLoading] = useState(false);
  const [listUpdateMember, setListUpdateMember] = useState([]); // listMemberAll update

  const [listRoleProject, setListRoleProject] = useState([]);
  const dataCategory = useAppSelector(GetCategory);
  useEffect(() => {
    if (idProject !== null && idProject !== "" && visible === true) {
      setListCategorysChange([]);
      setListMemberChange([]);
      setListUpdateMember([]);
      setListMemberProjects([]);
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
    setLoading(true);
    try {
      await AdPotalsMemberFactoryAPI.getAllMemberActive().then((respone) => {
        setListMember(respone.data.data);
      });
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
        setListCategorysChange(
          obj.listCategory != null && obj.listCategory.map((i) => i.categoryId)
        );
      });
    } catch (error) {
      console.log(error);
    }
  };

  const featMemberAndCaregoryAddProject = async () => {
    try {
      console.log("changer");
      console.log(listMember);
      const listMemberJoinCreate = listMember
        .filter((obj1) =>
          listMemberChange.some((obj2) => obj1.memberId === obj2)
        )
        .map((obj1) => {
          const matchedItem = listMemberChange.find(
            (obj2) => obj2 === obj1.memberId
          );
          return { ...obj1, ...matchedItem };
        });
      console.log("memebrer role");
      console.log(listMemberJoinCreate);
      // const roleDefault = listRoleProject.find(
      //   (item) => item.roleDefault === "DEFAULT"
      // );
      // const listMemberAdd = listMemberJoinCreate.map((member) => {
      //   return {
      //     memberId: member.memberId,
      //     email: member.email,
      //     role:
      //       listRoleProject.length > 0
      //         ? [roleDefault !== undefined && roleDefault.name]
      //         : ["Không có vai trò mặc định, vui lòng chọn vai trò khác"],
      //     statusWork: member.statusWork,
      //   };
      // });
      setListUpdateMember(listMemberJoinCreate);
      setListMemberProjects(listMemberJoinCreate);
      setListCategoryProjects(listCategorysChange);
    } catch (error) {
      alert("Hệ thống lỗi, vui lòng F5 lại trang");
    }
  };

  const handleRoleChange = (id, value) => {
    let listFindUpdateRole = [...listMemberJoin, ...listUpdateMember];
    let updatedListInfo = listFindUpdateRole.map((record) => {
      if (record.memberId === id) {
        let newData = {
          memberId: record.memberId,
          email: record.email,
          role: [...value],
        };
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
    if (descriptions.trim === "") {
      setErrorDescriptions("Mô tả không được để trống");
      check++;
    } else {
      if (descriptions.length > 1000) {
        setErrorDescriptions("Mô tả không vượt quá 1000 ký tự");
        check++;
      } else {
        setErrorDescriptions("");
      }
    }
    if (check === 0) {
      let projectNew = {
        code: code,
        name: name,
        descriptions: descriptions,
        startTime: startTime,
        endTime: endTime,
        listMembers: listUpdateMember,
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
          message.error(error.response.data.message);
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
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: "8px" }}>
          <Select
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(value) => setSelectedKeys(value ? [value] : [])}
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: "8px", display: "block" }}
          >
            {listRoleProject.length === 0
              ? "Không có vai trò để chọn"
              : listRoleProject.map((item) => {
                  <Option value={item.id}>{item.name}</Option>;
                })}
          </Select>
          <div style={{ display: "flex", justifyContent: "flex-end" }}>
            <Button
              type="primary"
              onClick={confirm}
              size="small"
              style={{ marginRight: "8px" }}
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
          mode="multiple"
          placeholder="Chọn vai trò"
          // value={record.listRole.map((item) => item.idRole)}
          style={{
            width: "100%",
          }}
          key={record.idRoleMemberProject}
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
        <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={12}>
              <span>
                <span className="notBlank">*</span>
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
                {" "}
                <span className="notBlank">*</span>Tên dự án:
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

          <Row style={{ marginBottom: "15px" }}>
            <span style={{ fontSize: "16px", fontWeight: 500 }}>
              Danh sách thành viên đang tham gia dự án:
            </span>
            <span
              className="error"
              style={{ display: "block", marginTop: "2px" }}
            >
              {errorMembers}
            </span>
          </Row>
          <Row style={{ marginBottom: "15px" }}>
            <div style={{ width: "100%" }}>
              <span>Thành viên:</span>
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
                  option.label.toLowerCase().indexOf(text.toLowerCase()) !== -1
                }
              >
                {listMember.map((member) => (
                  <Option
                    label={member.email}
                    value={member.memberId}
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
