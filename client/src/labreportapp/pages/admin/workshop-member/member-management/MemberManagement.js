import {
  faDownload,
  faEllipsisV,
  faPeopleGroup,
  faPlus,
  faUpload,
  faUser,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  Button,
  Checkbox,
  Col,
  Dropdown,
  Input,
  Menu,
  Pagination,
  Row,
  Select,
  Tag,
} from "antd";
import { useEffect, useState } from "react";
import "./style-member-management.css";
import React from "react";
import Image from "../../../../../portalprojects/helper/img/Image";
import LogoTeamFactory from "../../../../assets/img/logo_team_factory.png";
import { AdMemberFactoryAPI } from "../../../../api/admin/AdMemberFactoryAPI";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import {
  GetAdMemberFactory,
  SetAdMemberFactory,
} from "../../../../app/admin/AdMemberFactorySlice.reducer";
import LoadingIndicator from "../../../../helper/loading";
import useDebounce from "../../../../../portalprojects/custom-hook/useDebounce";
import ModalAddMemberFactory from "./modal-add-member-factory/ModalAddMemberFactory";
import ModalUpdateMemberFactory from "./modal-update-member-factory/ModalUpdateMemberFactory";

const MemberManagement = () => {
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Quản lý thành viên | Lab-Report-App";

    loadDataRoles();
    return () => {
      dispatch(SetAdMemberFactory([]));
    };
  }, []);

  const [value, setValue] = useState("");
  const [current, setCurrent] = useState(1);
  const [size, setSize] = useState("10");
  const [totalPages, setTotalPages] = useState(0);
  const [status, setStatus] = useState("");
  const [role, setRole] = useState("");
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);

  const [roles, setRoles] = useState([]);
  const [teams, setTeams] = useState([]);
  const [numberMemberFactory, setNumberMemberFactory] = useState(0);

  const debouncedNameValue = useDebounce(value, 650);

  useEffect(() => {
    loadData();
  }, [debouncedNameValue, current, status, role, size]);

  const loadData = () => {
    setLoading(true);
    let filter = {
      value: debouncedNameValue,
      page: current - 1,
      status: status === "" ? null : parseInt(status),
      roleFactoryId: role,
      size: size,
    };
    AdMemberFactoryAPI.getPage(filter).then((response) => {
      dispatch(SetAdMemberFactory(response.data.data.data));
      setTotalPages(response.data.data.totalPages);
      setLoading(false);
    });
  };

  const loadDataRoles = () => {
    AdMemberFactoryAPI.getRoles().then((response) => {
      setRoles(response.data.data);
    });
    AdMemberFactoryAPI.getTeams().then((response) => {
      setTeams(response.data.data);
    });
    AdMemberFactoryAPI.getNumberMemberFactory().then((response) => {
      setNumberMemberFactory(response.data.data);
    });
  };

  const data = useAppSelector(GetAdMemberFactory);

  const [dropdownStates, setDropdownStates] = useState({});

  const toggleDropdown = (itemId) => {
    setDropdownStates((prevState) => ({
      ...prevState,
      [itemId]: !prevState[itemId],
    }));
  };

  const handleMenuClick = (e, itemId) => {
    console.log("Chọn mục: ", e.key, "của phần tử:", itemId);
  };

  const [visibleAddMemberFactory, setVisibleAddMemberFactory] = useState(false);

  const openModalAddMemberFactory = () => {
    setVisibleAddMemberFactory(true);
  };

  const cancelModalAddMemberFactory = () => {
    setVisibleAddMemberFactory(false);
  };

  const [visibleUpdateMemberFactory, setVisibleUpdateMemberFactory] =
    useState(false);
  const [idMemberFactorySelected, setIdMemberFactorySelected] = useState(null);

  const openModalUpdateMemberFactory = (id) => {
    setVisibleUpdateMemberFactory(true);
    setIdMemberFactorySelected(id);
  };

  const cancelModalUpdateMemberFactory = () => {
    setVisibleUpdateMemberFactory(false);
    setIdMemberFactorySelected(null);
  };

  return (
    <div className="box-general" style={{ paddingTop: 50 }}>
      {loading && <LoadingIndicator />}
      <div className="title_activity_management" style={{ marginTop: 0 }}>
        {" "}
        <FontAwesomeIcon icon={faPeopleGroup} style={{ fontSize: "20px" }} />
        <span style={{ marginLeft: "10px" }}>Quản lý thành viên</span>
      </div>

      <div
        className="box-son-general"
        style={{ minHeight: "620px", marginTop: 20 }}
      >
        <div
          className="member-factory-managment"
          style={{ marginBottom: "20px" }}
        >
          <div style={{}}>Danh sách thành viên trong xưởng:</div>
          <div style={{ display: "flex", alignItems: "center", marginTop: 15 }}>
            <div style={{ flex: 1 }}>
              <Input
                type="text"
                placeholder="🔍 Nhập username"
                style={{ width: "55%" }}
                value={value}
                onChange={(e) => {
                  setValue(e.target.value);
                }}
              />
            </div>
            <div>
              <Button
                style={{
                  backgroundColor: "rgb(38, 144, 214)",
                  color: "white",
                  marginRight: 5,
                }}
              >
                <FontAwesomeIcon icon={faDownload} style={{ marginRight: 5 }} />
                Export
              </Button>
              <Button
                style={{
                  backgroundColor: "rgb(38, 144, 214)",
                  color: "white",
                  marginRight: 5,
                }}
              >
                <FontAwesomeIcon icon={faUpload} style={{ marginRight: 5 }} />
                Import
              </Button>
              <Button
                style={{
                  backgroundColor: "rgb(38, 144, 214)",
                  color: "white",
                  marginRight: 5,
                }}
              >
                <FontAwesomeIcon icon={faDownload} style={{ marginRight: 5 }} />
                Tải mẫu
              </Button>
              <Button
                style={{
                  backgroundColor: "rgb(38, 144, 214)",
                  color: "white",
                }}
                onClick={openModalAddMemberFactory}
              >
                <FontAwesomeIcon icon={faPlus} style={{ marginRight: 5 }} />
                Thêm thành viên
              </Button>
            </div>
          </div>
          <div className="" style={{ marginTop: 20 }}>
            <div className="header-list-member-factory">
              <span style={{ marginLeft: 5 }}>#</span>{" "}
              <div style={{ marginLeft: 30, width: "62%" }}>
                <FontAwesomeIcon icon={faUser} />{" "}
                <span style={{ marginLeft: 5 }}>
                  {numberMemberFactory} thành viên
                </span>
              </div>
              <div
                style={{
                  display: "flex",
                  alignItems: "center",
                  width: "38%",
                  justifyContent: "right",
                  marginRight: 20,
                }}
              >
                <div style={{ marginRight: 10 }}>
                  <Select
                    value={status}
                    onChange={(e) => {
                      setStatus(e);
                    }}
                    style={{ width: 150 }}
                  >
                    <Select.Option value="">Trạng thái</Select.Option>
                    <Select.Option value="0">Hoạt động</Select.Option>
                    <Select.Option value="1">Không hoạt động</Select.Option>
                  </Select>
                </div>
                <div>
                  <Select
                    value={role}
                    onChange={(e) => {
                      setRole(e);
                    }}
                    style={{ width: 150 }}
                  >
                    <Select.Option value="">Vai trò</Select.Option>
                    {roles.map((item) => {
                      return (
                        <Select.Option value={item.id} key={item.id}>
                          {item.name}
                        </Select.Option>
                      );
                    })}
                  </Select>
                </div>
              </div>
            </div>

            <div>
              {data.map((item) => {
                return (
                  <Row
                    style={{
                      display: "flex",
                      alignItems: "center",
                    }}
                    className="item-list-member-factory__"
                  >
                    {" "}
                    <span>{item.stt}</span>
                    <Col
                      span={16}
                      style={{
                        display: "flex",
                        alignItems: "center",
                        flex: 1,
                        marginLeft: 30,
                      }}
                    >
                      <Image
                        url={
                          item.picture !== "Images/Default.png"
                            ? item.picture
                            : LogoTeamFactory
                        }
                        picxel={45}
                      />{" "}
                      <span
                        style={{
                          marginLeft: 15,
                          color: "rgb(38, 144, 214)",
                          fontWeight: 500,
                        }}
                      >
                        {item.name}
                      </span>
                      <span
                        style={{
                          marginLeft: 10,
                          color: "gray",
                          fontSize: 14,
                        }}
                      >
                        {item.userName}
                      </span>
                      <div style={{ marginLeft: 20 }}>
                        {" "}
                        {item.statusMemberFactory === 0 && (
                          <Tag color="success">Hoạt động</Tag>
                        )}
                        {item.statusMemberFactory === 1 && (
                          <Tag color="error">Không hoạt động</Tag>
                        )}
                      </div>
                    </Col>
                    <Col
                      span={8}
                      style={{
                        display: "flex",
                        justifyContent: "right",
                        alignItems: "center",
                        paddingRight: 20,
                      }}
                    >
                      <div style={{ marginRight: 20 }}>
                        <span>
                          {item.roleMemberFactory == null ? (
                            <Tag color="error">Chưa có </Tag>
                          ) : (
                            <Tag color="success">{item.roleMemberFactory}</Tag>
                          )}
                        </span>{" "}
                        <span style={{ marginLeft: 8 }}>/</span>
                        <span style={{ marginLeft: 8 }}>
                          {item.numberTeam} Team
                        </span>
                      </div>
                      <div style={{ textAlign: "right" }}>
                        <Dropdown
                          overlay={
                            <Menu onClick={(e) => handleMenuClick(e, item.id)}>
                              <Menu.Item
                                key="item1"
                                onClick={() => {
                                  openModalUpdateMemberFactory(item.id);
                                }}
                              >
                                Cập nhật
                              </Menu.Item>
                            </Menu>
                          }
                          visible={dropdownStates[item.id]}
                          onVisibleChange={(visible) =>
                            setDropdownStates((prevState) => ({
                              ...prevState,
                              [item.id]: visible,
                            }))
                          }
                        >
                          <Button onClick={() => toggleDropdown(item.id)}>
                            <FontAwesomeIcon icon={faEllipsisV} />
                          </Button>
                        </Dropdown>
                      </div>
                    </Col>
                  </Row>
                );
              })}
            </div>
            <div>
              <div
                className="pagination_box"
                style={{ display: "flex", alignItems: "center" }}
              >
                <Pagination
                  style={{ marginRight: "10px" }}
                  simple
                  current={current}
                  onChange={(value) => {
                    setCurrent(value);
                  }}
                  total={totalPages * 10}
                />
                <Select
                  style={{ width: "100px", marginLeft: "10px" }}
                  value={size}
                  onChange={(e) => {
                    setSize(e);
                  }}
                >
                  <Select.Option value="10">10</Select.Option>
                  <Select.Option value="25">25</Select.Option>
                  <Select.Option value="50">50</Select.Option>
                  <Select.Option value="100">100</Select.Option>
                  <Select.Option value="250">250</Select.Option>
                  <Select.Option value="500">500</Select.Option>
                  <Select.Option value="1000">1000</Select.Option>
                </Select>
              </div>
            </div>
          </div>
        </div>
      </div>
      <ModalAddMemberFactory
        visible={visibleAddMemberFactory}
        onCancel={cancelModalAddMemberFactory}
        setNumberMemberFactory={setNumberMemberFactory}
        numberMemberFactory={numberMemberFactory}
      />
      <ModalUpdateMemberFactory
        visible={visibleUpdateMemberFactory}
        onCancel={cancelModalUpdateMemberFactory}
        id={idMemberFactorySelected}
        roles={roles}
        teams={teams}
      />
    </div>
  );
};

export default MemberManagement;
