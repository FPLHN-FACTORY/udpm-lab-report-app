import {
  faPlus,
  faFilterCircleDollar,
  faPeopleGroup,
  faEllipsisV,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  Button,
  Col,
  Dropdown,
  Input,
  Menu,
  Pagination,
  Popconfirm,
  Row,
  Tag,
  message,
} from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useAppSelector, useAppDispatch } from "../../../../app/hook";
import {
  SetTeam,
  DeleteTeam,
  GetTeam,
} from "../../../../app/admin/AdTeamSlice.reducer";
import { AdTeamAPI } from "../../../../api/admin/AdTeamAPI";
import ModalCreateTeam from "./modal-create/ModalCreateTeam";
import ModalUpdateTeam from "./modal-update/ModalUpdateTeam";
import { toast } from "react-toastify";
import LoadingIndicator from "../../../../helper/loading";
import moment from "moment";
import "./style-team-management.css";
import React from "react";
import { Link } from "react-router-dom";
import Image from "../../../../../portalprojects/helper/img/Image";

const TeamManagement = () => {
  const [team, setTeam] = useState(null);
  const [name, setName] = useState("");
  const { id } = useParams();
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchData();
    window.scrollTo(0, 0);
    document.title = "Quản lý team | Lab-Report-App";

    return () => {
      dispatch(SetTeam([]));
    };
  }, [current]);

  const fetchData = () => {
    setLoading(true);
    let filter = {
      name: name,
      page: current,
      size: 10,
    };
    AdTeamAPI.fetchAllTeam(filter).then((response) => {
      dispatch(SetTeam(response.data.data.data));
      setTotal(response.data.data.totalPages);
      setLoading(false);
    });
  };

  const data = useAppSelector(GetTeam);

  const [modalUpdate, setModalUpdate] = useState(false);
  const [modalCreate, setModalCreate] = useState(false);

  const buttonCreate = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setModalCreate(true);
  };

  const buttonCreateCancel = () => {
    document.querySelector("body").style.overflowX = "auto";
    setModalCreate(false);
    setTeam(null);
  };

  const buttonUpdate = (record) => {
    document.querySelector("body").style.overflowX = "hidden";
    setModalUpdate(true);
    setTeam(record);
  };

  const buttonUpdateCancel = () => {
    document.querySelector("body").style.overflowX = "auto";
    setModalUpdate(false);
  };

  const buttonSearch = () => {
    fetchData();
    setCurrent(1);
  };

  const clearData = () => {
    setName("");
  };

  const buttonDelete = (id) => {
    AdTeamAPI.deleteTeam(id).then(
      (response) => {
        message.success("Xóa thành công!");
        dispatch(DeleteTeam(response.data.data));
      },
      (error) => {}
    );
  };

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

  return (
    <>
      {" "}
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon
              icon={faPeopleGroup}
              style={{ fontSize: "20px" }}
            />
            <span style={{ marginLeft: "10px" }}>Quản lý team</span>
          </span>
        </div>
      </div>
      <div className="box-general" style={{ paddingTop: 10, marginTop: 0 }}>
        {loading && <LoadingIndicator />}
        <div
          className="box-son-general"
          style={{ minHeight: "620px", marginTop: 20 }}
        >
          <div
            className="member-factory-managment"
            style={{ marginBottom: "20px" }}
          >
            <div style={{}}>Danh sách team trong xưởng:</div>
            <div
              style={{ display: "flex", alignItems: "center", marginTop: 15 }}
            >
              <div style={{ flex: 1 }}>
                <Input
                  type="text"
                  placeholder="🔍 Nhập tên nhóm"
                  style={{ width: "55%" }}
                  value={name}
                  onChange={(e) => {
                    setName(e.target.value);
                  }}
                />
                <Button
                  className="btn_filter"
                  onClick={buttonSearch}
                  style={{ marginRight: "15px" }}
                >
                  <FontAwesomeIcon
                    icon={faFilterCircleDollar}
                    style={{ marginRight: 5 }}
                  />
                  Tìm kiếm
                </Button>
              </div>
              <div>
                <Button
                  style={{
                    backgroundColor: "rgb(38, 144, 214)",
                    color: "white",
                  }}
                  onClick={buttonCreate}
                >
                  <FontAwesomeIcon icon={faPlus} style={{ marginRight: 5 }} />
                  Thêm nhóm
                </Button>
              </div>
            </div>
            <div className="" style={{ marginTop: 20 }}>
              <div className="header-list-member-factory">
                <span style={{ marginLeft: 5 }}>#</span>{" "}
                <span style={{ marginLeft: 20 }}>Tên nhóm</span>{" "}
              </div>
              <div>
                {data.map((item) => {
                  return (
                    <Row
                      style={{
                        display: "flex",
                        alignItems: "center",
                        minHeight: 60,
                      }}
                      className="item-list-member-factory__"
                    >
                      {" "}
                      <span>{item.stt}</span>
                      <Link
                        to={`/admin/team-management/${item.id}`}
                        style={{ marginLeft: 20 }}
                      >
                        <span style={{ fontSize: 16 }}>{item.name}</span>
                      </Link>
                      <Col
                        span={8}
                        style={{
                          display: "flex",
                          alignItems: "center",
                          flex: 1,
                          marginLeft: 30,
                        }}
                      ></Col>
                      <Col
                        span={16}
                        style={{
                          display: "flex",
                          justifyContent: "right",
                          alignItems: "center",
                          paddingRight: 20,
                        }}
                      >
                        <div
                          style={{
                            marginRight: 20,
                            display: "flex",
                            alignItems: "center",
                          }}
                        >
                          {item.listMember.length > 0 &&
                            item.listMember.map((mb) => {
                              return (
                                <Image
                                  url={mb.picture}
                                  picxel={28}
                                  marginRight={-2}
                                  name={mb.name + " " + mb.userName}
                                />
                              );
                            })}
                          {item.listMember.length === 0 && (
                            <Tag color="error">Chưa có thành viên</Tag>
                          )}
                          <span style={{ marginLeft: 8 }}>/</span>
                          <span style={{ marginLeft: 8 }}>
                            {item.numberMember} Thành viên
                          </span>
                        </div>
                        <div style={{ textAlign: "right" }}>
                          <Dropdown
                            overlay={
                              <Menu
                                onClick={(e) => handleMenuClick(e, item.id)}
                              >
                                <Menu.Item
                                  key="item1"
                                  onClick={() => {
                                    buttonUpdate(item);
                                  }}
                                >
                                  Cập nhật
                                </Menu.Item>
                                <Popconfirm
                                  placement="topLeft"
                                  title="Xóa nhóm"
                                  description="Bạn có chắc chắn muốn xóa nhóm này không ?"
                                  onConfirm={() => {
                                    buttonDelete(item.id);
                                  }}
                                  okText="Có"
                                  cancelText="Không"
                                >
                                  <Menu.Item key="item2">Xóa</Menu.Item>
                                </Popconfirm>
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
                <div className="pagination_box">
                  <Pagination
                    simple
                    current={current}
                    onChange={(page) => {
                      setCurrent(page);
                    }}
                    total={total * 10}
                  />
                </div>
              </div>
            </div>
            <ModalCreateTeam
              visible={modalCreate}
              onCancel={buttonCreateCancel}
            />
            <ModalUpdateTeam
              visible={modalUpdate}
              onCancel={buttonUpdateCancel}
              team={team}
            />
          </div>
        </div>
      </div>
    </>
  );
};

export default TeamManagement;
