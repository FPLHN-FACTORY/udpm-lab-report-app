import {
  faFilterCircleDollar,
  faPeopleGroup,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Col, Input, Pagination, Row, Tag } from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useAppSelector, useAppDispatch } from "../../../app/hook";
import {
  SetTeam,
  GetTeam,
} from "../../../app/teacher/team-factory/TeTeamFactorySlice.reducer";
import { TeTeamFactoryAPI } from "../../../api/teacher/team-factory/TeTeamFactoryAPI";
import LoadingIndicator from "../../../helper/loading";
import React from "react";
import { Link } from "react-router-dom";
import Image from "../../../../portalprojects/helper/img/Image";

const TeTeamFactory = () => {
  const { id } = useParams();
  const [name, setName] = useState("");
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchData();
    window.scrollTo(0, 0);
    document.title = "Danh sách team | Lab-Report-App";
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
    TeTeamFactoryAPI.fetchAllTeam(filter).then((response) => {
      dispatch(SetTeam(response.data.data.data));
      setTotal(response.data.data.totalPages);
      setLoading(false);
    });
  };

  const data = useAppSelector(GetTeam);

  const buttonSearch = () => {
    fetchData();
    setCurrent(1);
  };

  return (
    <div className="box-general" style={{ paddingTop: 50 }}>
      {loading && <LoadingIndicator />}
      <div className="title_activity_management" style={{ marginTop: 0 }}>
        {" "}
        <FontAwesomeIcon icon={faPeopleGroup} style={{ fontSize: "20px" }} />
        <span style={{ marginLeft: "10px" }}>Danh sách team trong xưởng</span>
      </div>

      <div
        className="box-son-general"
        style={{ minHeight: "620px", marginTop: 20 }}
      >
        <div
          className="member-factory-managment"
          style={{ marginBottom: "20px" }}
        >
          <div style={{}}>Danh sách team trong xưởng:</div>
          <div style={{ display: "flex", alignItems: "center", marginTop: 15 }}>
            <div style={{ flex: 1 }}>
              <Input
                type="text"
                placeholder="🔍 Nhập tên nhóm"
                style={{ width: "85%" }}
                value={name}
                onChange={(e) => {
                  setName(e.target.value);
                }}
              />
              <Button
                className="btn_filter"
                onClick={buttonSearch}
                style={{ marginLeft: "10px" }}
              >
                <FontAwesomeIcon
                  icon={faFilterCircleDollar}
                  style={{ marginRight: 5 }}
                />
                Tìm kiếm
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
                      to={`/teacher/member-factory/${item.id}`}
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
        </div>
      </div>
    </div>
  );
};

export default TeTeamFactory;
