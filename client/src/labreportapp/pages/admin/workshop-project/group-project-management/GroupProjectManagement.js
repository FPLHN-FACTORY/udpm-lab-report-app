import {
  faPlus,
  faFilterCircleDollar,
  faChainSlash,
  faTableList,
  faGroupArrowsRotate,
  faObjectGroup,
  faFilter,
  faEllipsisV,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  Button,
  Card,
  Col,
  Dropdown,
  Input,
  Menu,
  Pagination,
  Row,
  Select,
  Tooltip,
} from "antd";
import { useEffect, useState } from "react";
import "./style-group-project-management.css";
import React from "react";
import { AdGroupProjectAPI } from "../../../../api/admin/AdGroupProjectAPI";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import {
  GetAdGroupProject,
  SetAdGroupProject,
} from "../../../../app/admin/AdGroupProjectSlice.reducer";
import LoadingIndicator from "../../../../helper/loading";
import { Link } from "react-router-dom";
import DeFault from "../../../../assets/img/FPT_Polytechnic_Hanoi-2.jpg";
import { EllipsisOutlined } from "@ant-design/icons";
import ModalUpdateGroupProject from "../modal-update-group-project/ModalUpdateGroupProject";
import ModalCreateGroupProject from "../modal-add-group-project/ModalAddGroupProject";

const GroupProjectManagement = () => {
  const [name, setName] = useState("");
  const dispatch = useAppDispatch();
  const [current, setCurrent] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [size, setSize] = useState("8");
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Quản lý nhóm dự án | Lab-Report-App";
  }, []);

  useEffect(() => {
    loadDataGroupProject();
    return () => {
      dispatch(SetAdGroupProject([]));
    };
  }, [current, size]);

  const clearFilter = () => {
    setName("");
  };

  const loadDataGroupProject = () => {
    let filter = {
      name: name,
      page: current - 1,
      size: parseInt(size),
    };
    setLoading(true);
    AdGroupProjectAPI.getAllGroupProject(filter).then((response) => {
      console.log(response.data.data.data);
      dispatch(SetAdGroupProject(response.data.data.data));
      setTotalPages(response.data.data.totalPages);
      setLoading(false);
    });
  };

  const data = useAppSelector(GetAdGroupProject);

  const [dropdownStates, setDropdownStates] = useState({}); // Lưu trạng thái của menu dropdown

  const toggleDropdown = (itemId) => {
    setDropdownStates((prevState) => ({
      ...prevState,
      [itemId]: !prevState[itemId],
    }));
  };

  const handleMenuClick = (e, itemId) => {
    // Xử lý khi chọn một mục trong menu dropdown của phần tử cụ thể (itemId)
    console.log("Chọn mục: ", e.key, "của phần tử:", itemId);
  };

  const [showUpdateGroupProject, setShowUpdateGroupProject] = useState(false);
  const [itemUpdateGroupProject, setItemUpdateGroupProject] = useState(null);

  const openModalUpdateGroupProject = (item) => {
    setShowUpdateGroupProject(true);
    setItemUpdateGroupProject(item);
  };

  const handleShowUpdateGroupProject = () => {
    setShowUpdateGroupProject(false);
    setItemUpdateGroupProject(null);
  };

  const [showCreateGroupProject, setShowCreateGroupProject] = useState(false);

  const openModalCreateGroupProject = (item) => {
    setShowCreateGroupProject(true);
  };

  const handleShowCreateGroupProject = () => {
    setShowCreateGroupProject(false);
  };

  return (
    <div className="box-general" style={{ paddingTop: 50 }}>
      {loading && <LoadingIndicator />}
      <div className="title_activity_management" style={{ marginTop: 0 }}>
        {" "}
        <FontAwesomeIcon icon={faObjectGroup} style={{ fontSize: "20px" }} />
        <span style={{ marginLeft: "10px" }}>Quản lý nhóm dự án</span>
      </div>
      <div className="filter-level" style={{ marginBottom: "10px" }}>
        <FontAwesomeIcon icon={faFilter} style={{ fontSize: "19px" }} />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <Row style={{ marginTop: 15, marginBottom: 15 }}>
          Tên nhóm dự án:
          <Input
            type="text"
            value={name}
            onChange={(e) => {
              setName(e.target.value);
            }}
            placeholder="Nhập tên nhóm dự án"
          />
        </Row>
        <div className="box_btn_filter" style={{ paddingBottom: 10 }}>
          <Button
            className="btn_filter"
            style={{ marginRight: "15px" }}
            onClick={loadDataGroupProject}
          >
            <FontAwesomeIcon
              icon={faFilterCircleDollar}
              style={{ marginRight: 5 }}
            />
            Tìm kiếm
          </Button>
          <Button
            className="btn__clear"
            style={{ backgroundColor: "rgb(50, 144, 202)" }}
            onClick={clearFilter}
          >
            <FontAwesomeIcon icon={faChainSlash} style={{ marginRight: 5 }} />
            Làm mới bộ lọc
          </Button>
        </div>
      </div>
      <div
        className="box-son-general"
        style={{ minHeight: "400px", marginTop: "30px" }}
      >
        <div className="tittle__category" style={{ marginBottom: "20px" }}>
          <div>
            <FontAwesomeIcon
              icon={faTableList}
              style={{
                marginRight: "10px",
                fontSize: "20px",
              }}
            />
            <span style={{ fontSize: "18px", fontWeight: "500" }}>
              {" "}
              Danh sách nhóm dự án
            </span>
          </div>

          <div>
            <Button
              style={{
                color: "white",
                backgroundColor: "rgb(55, 137, 220)",
              }}
              onClick={openModalCreateGroupProject}
            >
              <FontAwesomeIcon
                icon={faPlus}
                size="1x"
                style={{
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: "5px",
                }}
              />{" "}
              Thêm mới
            </Button>
          </div>
        </div>
        <div>
          <Row>
            {data.length > 0 &&
              data.map((item) => {
                return (
                  <Col span={6} style={{ padding: 10 }}>
                    <Tooltip title={item.name}>
                      <Card
                        hoverable
                        cover={
                          <div style={{ position: "relative" }}>
                            <Link
                              to={`/admin/group-project-management/${item.id}`}
                            >
                              <img
                                src={
                                  item.backgroundImage == null
                                    ? DeFault
                                    : item.backgroundImage
                                }
                                alt="Nhóm dự án"
                                style={{
                                  objectFit: "cover",
                                  height: 150,
                                  width: "100%",
                                }}
                              />
                            </Link>
                            <div
                              style={{
                                position: "absolute",
                                top: 0,
                                left: 0,
                                fontSize: 20,
                                fontWeight: "bold",
                                color: "white",
                                paddingTop: 5,
                                textShadow: "2px 2px 4px rgba(0, 0, 0, 0.5)",
                                paddingLeft: 5,
                              }}
                            >
                              {item.name}
                            </div>
                          </div>
                        }
                        className="card-group-project"
                        style={{ textAlign: "center", minHeight: 230 }}
                      >
                        {item.descriptions}
                        <div style={{ textAlign: "right" }}>
                          <Dropdown
                            overlay={
                              <Menu
                                onClick={(e) => handleMenuClick(e, item.id)}
                              >
                                <Menu.Item
                                  key="item1"
                                  onClick={() => {
                                    openModalUpdateGroupProject(item);
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
                      </Card>
                    </Tooltip>
                  </Col>
                );
              })}
          </Row>
          <Row
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
            }}
          >
            <div>
              <div
                className="pagination_box"
                style={{
                  display: "flex",
                  alignItems: "center",
                  justifyContent: "center",
                }}
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
                  <Select.Option value="8">8</Select.Option>
                  <Select.Option value="25">25</Select.Option>
                  <Select.Option value="50">50</Select.Option>
                  <Select.Option value="100">100</Select.Option>
                  <Select.Option value="250">250</Select.Option>
                  <Select.Option value="500">500</Select.Option>
                  <vOption value="1000">1000</vOption>
                </Select>
              </div>
            </div>
          </Row>
        </div>
        <ModalUpdateGroupProject
          visible={showUpdateGroupProject}
          onCancel={handleShowUpdateGroupProject}
          item={itemUpdateGroupProject}
        />
        <ModalCreateGroupProject
          visible={showCreateGroupProject}
          onCancel={handleShowCreateGroupProject}
        />
      </div>
    </div>
  );
};

export default GroupProjectManagement;
