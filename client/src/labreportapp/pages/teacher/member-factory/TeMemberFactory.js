import { faPeopleGroup, faUser } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Col, Input, Pagination, Row, Select, Tag } from "antd";
import { useEffect, useState } from "react";
import React from "react";
import Image from "../../../../portalprojects/helper/img/Image";
import LogoTeamFactory from "../../../assets/img/logo_team_factory.png";
import { TeMemberFactoryAPI } from "../../../api/teacher/member-factory/TeMemberFactoryAPI";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import {
  GetTeMemberFactory,
  SetTeMemberFactory,
} from "../../../app/teacher/member-factory/TeMemberFactorySlice.reducer";
import LoadingIndicator from "../../../helper/loading";
import useDebounce from "../../../../portalprojects/custom-hook/useDebounce";

const TeMemberFactory = () => {
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Quản lý thành viên | Lab-Report-App";

    loadDataRoles();
    return () => {
      dispatch(SetTeMemberFactory([]));
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
      value: debouncedNameValue.trim(),
      page: current - 1,
      status: status === "" ? null : parseInt(status),
      roleFactoryId: role,
      size: size,
    };
    TeMemberFactoryAPI.getPage(filter).then((response) => {
      dispatch(SetTeMemberFactory(response.data.data.data));
      setTotalPages(response.data.data.totalPages);
      setLoading(false);
    });
  };

  const loadDataRoles = () => {
    TeMemberFactoryAPI.getRoles().then((response) => {
      setRoles(response.data.data);
    });
    TeMemberFactoryAPI.getTeams().then((response) => {
      setTeams(response.data.data);
    });
    TeMemberFactoryAPI.getNumberMemberFactory().then((response) => {
      setNumberMemberFactory(response.data.data);
    });
  };

  const data = useAppSelector(GetTeMemberFactory);

  return (
    <div className="box-general" style={{ paddingTop: 50 }}>
      {loading && <LoadingIndicator />}
      <div className="title_activity_management" style={{ marginTop: 0 }}>
        {" "}
        <FontAwesomeIcon icon={faPeopleGroup} style={{ fontSize: "20px" }} />
        <span style={{ marginLeft: "10px" }}>Thành viên trong xưởng</span>
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
                style={{ width: "100%" }}
                value={value}
                onChange={(e) => {
                  setValue(e.target.value);
                }}
              />
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
                          item.picture !== "/image/Default.png"
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
    </div>
  );
};

export default TeMemberFactory;
