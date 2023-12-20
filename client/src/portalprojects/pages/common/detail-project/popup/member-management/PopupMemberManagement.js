import "./stylePopupMemberManagement.css";
import React, { useEffect, useRef, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCogs,
  faFilter,
  faPencilAlt,
  faPencilRuler,
  faPencilSquare,
  faSave,
} from "@fortawesome/free-solid-svg-icons";
import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
import {
  GetMemberProject,
  SetMemberProject,
} from "../../../../../app/reducer/detail-project/DPMemberProject.reducer";
import { Button, Col, Input, Row, Select, Table, Tooltip } from "antd";
import Image from "../../../../../helper/img/Image";
import { SearchOutlined } from "@ant-design/icons";
import { CommonAPI } from "../../../../../api/commonAPI";
import { GetProject } from "../../../../../app/reducer/detail-project/DPProjectSlice.reducer";
import { getStompClient } from "../../stomp-client-config/StompClientManager";
import Cookies from "js-cookie";
import { DetailProjectAPI } from "../../../../../api/detail-project/detailProject.api";
import { GetMeRoleProject } from "../../../../../app/reducer/detail-project/DPRoleProjectSlice.reducer";
import { GetCheckRole } from "../../../../../app/reducer/detail-project/DPDetailProjectCheckRole.reducer";
const { Option } = Select;

const PopupMemberManagement = ({ position, onClose }) => {
  const popupRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (
        !popupRef.current.contains(event.target) &&
        !event.target.className.includes("btn_search_member") &&
        !event.target.className.includes("ant") &&
        !event.target.tagName.includes("SPAN") &&
        !event.target.className.includes("image_common") &&
        !event.target.className.includes("div_option_member_management")
      ) {
        onClose();
      }
    };

    const handleEscapeKey = (event) => {
      if (event.key === "Escape") {
        onClose();
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    document.addEventListener("keydown", handleEscapeKey);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
      document.removeEventListener("keydown", handleEscapeKey);
    };
  }, [onClose]);

  const roleProjects = useAppSelector(GetMeRoleProject);

  const popupStyle = {
    position: "fixed",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    zIndex: 9999999999,
    paddingBottom: "7px",
    width: "80%",
    backgroundColor: "white",
    borderRadius: "10px",
    boxShadow: "0 2px 5px rgba(0, 0, 0, 0.3)",
  };

  const overlayStyle = {
    position: "fixed",
    top: 0,
    left: 0,
    width: "100%",
    height: "100%",
    backgroundColor: "rgba(0, 0, 0, 0.5)",
    zIndex: 9999999998,
  };

  const checkRole = useAppSelector(GetCheckRole);

  const columns = [
    {
      title: "#",
      dataIndex: "index",
      key: "index",
      render: (text, record, index) => {
        return <div style={{ textAlign: "center" }}>{index + 1}</div>;
      },
    },
    {
      title: "Họ và tên",
      dataIndex: "name",
      key: "name",
      width: "23%",
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
        record.name.toLowerCase().includes(value.toLowerCase()),
      sorter: (a, b) => a.name.localeCompare(b.name),
      sortDirections: ["ascend", "descend"],
      render: (text, record) => (
        <div style={{ display: "flex", alignItems: "center" }}>
          <Image
            url={record.picture}
            picxel={30}
            marginRight={8}
            name={record.name + " " + record.userName}
          />
          <span>{record.name}</span>
        </div>
      ),
    },
    {
      title: "Tài khoản",
      dataIndex: "userName",
      key: "userName",
      width: "10%",
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
        record.userName.toLowerCase().includes(value.toLowerCase()),
      sorter: (a, b) => a.userName.localeCompare(b.userName),
      sortDirections: ["ascend", "descend"],
      render: (text) => text,
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
      dataIndex: "roles",
      key: "roles",
      render: (text, record) => {
        return (
          <>
            {checkRole ? (
              <Select
                mode="multiple"
                maxTagCount={2}
                style={{ width: "100%" }}
                value={record.roles}
                placeholder="Chưa có vai trò"
                onChange={(value) => handleRoleChange(record.id, value)}
              >
                {roleProjects.map((item) => (
                  <Option value={item.id} key={item.id}>
                    {item.name}
                  </Option>
                ))}
              </Select>
            ) : (
              <div>
                {roleProjects.map((item) => {
                  return record.roles.map((role) => {
                    if (item.id === role) {
                      return <span key={item.id}>{item.name + ", "}</span>;
                    }
                    return null;
                  });
                })}
              </div>
            )}
          </>
        );
      },
    },
    {
      title: "Trạng thái",
      dataIndex: "statusWork",
      key: "statusWork",
      width: "12%",
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
            <Option value="0">Đang làm</Option>
            <Option value="1">Nghỉ làm</Option>
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
      onFilter: (value, record) => record.statusWork === value,
      sorter: (a, b) => a.statusWork.localeCompare(b.statusWork),
      sortDirections: ["ascend", "descend"],
      render: (text, record) => {
        return (
          <>
            {checkRole && (
              <Select
                style={{ width: "100%" }}
                value={record.statusWork}
                onChange={(value) => handleStatusChange(record.id, value)}
              >
                <Option value="0">Đang làm</Option>
                <Option value="1">Nghỉ làm</Option>
              </Select>
            )}
            {!checkRole && record.statusWork === "0" ? "Đang làm" : "Nghỉ làm"}
          </>
        );
      },
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div style={{ textAlign: "center" }}>
          {checkRole && (
            <Tooltip title="Lưu thay đổi">
              <FontAwesomeIcon
                style={{
                  marginRight: "15px",
                  cursor: "pointer",
                  color: "rgb(38, 144, 214)",
                }}
                onClick={() => handleSaveChanges(record)}
                icon={faSave}
                size="1x"
              />
            </Tooltip>
          )}
        </div>
      ),
      width: "5%",
    },
  ];

  const handleSaveChanges = (item) => {
    let obj = {
      idMember: item.memberId,
      idProject: detailProject.id,
      statusWork: parseInt(item.statusWork),
      role: item.roles,
    };

    const bearerToken = Cookies.get("token");
    const headers = {
      Authorization: "Bearer " + bearerToken,
    };
    stompClient.send(
      "/action/update-member-project/" + detailProject.id,
      headers,
      JSON.stringify(obj)
    );
  };

  const [valueMultiMember, setValueMultiMember] = useState([]);
  const [memberAll, setMemberAll] = useState([]);
  const memberProject = useAppSelector(GetMemberProject);
  console.log(memberProject);
  const [listMemberNoJoinProject, setListMemberNoJoinProject] = useState([]);
  const [listMemberAdd, setListMemberAdd] = useState([]);

  const dispatch = useAppDispatch();

  const handleRoleChange = (recordId, value) => {
    const updatedData = memberProject.map((record) =>
      record.id === recordId ? { ...record, roles: value } : record
    );
    dispatch(SetMemberProject(updatedData));
  };

  const handleStatusChange = (recordId, value) => {
    const updatedData = memberProject.map((record) =>
      record.id === recordId ? { ...record, statusWork: value } : record
    );
    dispatch(SetMemberProject(updatedData));
  };

  const handleChangeValueMultiMember = (e) => {
    setValueMultiMember(e);
  };

  const loadDataMemberAll = () => {
    DetailProjectAPI.getAllMemberTeam(detailProject.id).then((response) => {
      setMemberAll(response.data.data);
      fetchListMemberNoJoinProject(response.data.data);
    });
  };

  useEffect(() => {
    loadDataMemberAll();
  }, []);

  const fetchListMemberNoJoinProject = (allMember) => {
    const memberProjectIds = memberProject.map((member) => member.memberId);

    const membersNotInProject = allMember.filter(
      (member) => !memberProjectIds.includes(member.id)
    );
    setListMemberNoJoinProject(membersNotInProject);
  };

  const detailProject = useAppSelector(GetProject);

  const fetchLoadListMemberAdd = () => {
    const listData = memberAll
      .filter((member) => valueMultiMember.includes(member.id))
      .map((member) => ({
        memberId: member.id,
        email: member.email,
        projectId: detailProject.id,
        role: [],
      }));
    setListMemberAdd(listData);
  };

  useEffect(() => {
    fetchLoadListMemberAdd();
  }, [valueMultiMember]);

  const columnsTableAddMember = [
    {
      title: "#",
      dataIndex: "#",
      key: "#",
      render: (text, record, index) => index + 1,
      width: "5%",
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
      width: "12%",
      render: (text, record) => (
        <Select
          mode="multiple"
          maxTagCount={2}
          style={{ width: "100%" }}
          value={record.role}
          placeholder="Chưa có vai trò"
          onChange={(value) => handleRoleChangeAdd(record.memberId, value)}
        >
          {roleProjects.map((item) => (
            <Option value={item.id} key={item.id}>
              {item.name}
            </Option>
          ))}
        </Select>
      ),
    },
  ];

  const handleRoleChangeAdd = (id, value) => {
    let updatedListInfo = listMemberAdd.map((record) => {
      if (record.memberId === id) {
        let newData = {
          memberId: record.memberId,
          email: record.email,
          projectId: detailProject.id,
          role: value,
        };
        return { ...record, ...newData };
      }
      return record;
    });

    setListMemberAdd(updatedListInfo);
  };

  const stompClient = getStompClient();

  const handleClickAddMemberProject = () => {
    let obj = {
      listMemberProject: listMemberAdd,
    };
    const bearerToken = Cookies.get("token");
    const headers = {
      Authorization: "Bearer " + bearerToken,
    };
    stompClient.send(
      "/action/create-member-project/" + detailProject.id,
      headers,
      JSON.stringify(obj)
    );
    onClose();
  };

  return (
    <div>
      <div style={overlayStyle} className="overlay"></div>
      <div
        ref={popupRef}
        style={popupStyle}
        className="popup-member-management"
      >
        {" "}
        <div className="header-member-management">
          {" "}
          <FontAwesomeIcon icon={faCogs} style={{ marginRight: "7px" }} />
          Quản lý thành viên trong dự án
        </div>
        <div
          style={{ padding: "10px", overflowY: "auto" }}
          className="box-member-management"
        >
          {checkRole && (
            <div className="box_add_member">
              <span style={{ fontSize: "15px" }}>
                Thêm thành viên (danh sách thành viên trong nhóm):{" "}
              </span>{" "}
              <br />
              <div style={{ display: "flex", alignItems: "center" }}>
                <Select
                  value={valueMultiMember}
                  onChange={handleChangeValueMultiMember}
                  mode="multiple"
                  placeholder="Chọn thành viên"
                  style={{ width: "85%" }}
                  filterOption={(input, option) => {
                    const name =
                      option.children.props.children[1].toLowerCase();
                    const email =
                      option.children.props.children[3].toLowerCase();
                    const inputValue = input.toLowerCase();

                    return (
                      name.includes(inputValue) || email.includes(inputValue)
                    );
                  }}
                >
                  {listMemberNoJoinProject.map((item) => (
                    <Option value={item.id} key={item.id}>
                      <div
                        className="div_option_member_management"
                        style={{ display: "flex", alignItems: "center" }}
                      >
                        <Image
                          picxel={28}
                          url={item.picture}
                          marginRight={5}
                          key={item.id}
                        />
                        {item.name} ({item.email})
                      </div>
                    </Option>
                  ))}
                </Select>
                <Button
                  style={{
                    marginLeft: "15px",
                    backgroundColor: "rgb(38, 144, 214)",
                    color: "white",
                  }}
                  onClick={handleClickAddMemberProject}
                >
                  Thêm thành viên
                </Button>
              </div>
            </div>
          )}
          {listMemberAdd.length > 0 && (
            <Row>
              {" "}
              <Col span={24}>
                <Table
                  className="table-member-management"
                  columns={columnsTableAddMember}
                  dataSource={listMemberAdd}
                  rowKey="memberId"
                  pagination={false}
                />
              </Col>{" "}
            </Row>
          )}
          <div className="box-table">
            {" "}
            <div style={{ fontSize: "15px" }}>
              Danh sách thành viên đang tham gia dự án:{" "}
            </div>{" "}
            <br />
            <Table
              className="table-member-management"
              columns={columns}
              dataSource={memberProject}
              rowKey="id"
              pagination={false}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default PopupMemberManagement;
