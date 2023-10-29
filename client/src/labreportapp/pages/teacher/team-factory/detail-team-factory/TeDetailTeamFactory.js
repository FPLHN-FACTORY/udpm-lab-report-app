import {
    faCog,
    faDeleteLeft,
    faPeopleGroup,
    faPlus,
    faTrashCan,
    faUser,
    faX,
  } from "@fortawesome/free-solid-svg-icons";
  import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
  import {
    Button,
    Checkbox,
    Col,
    Empty,
    Input,
    Popconfirm,
    Row,
    message,
  } from "antd";
  import { useEffect, useState } from "react";
//   import "./style-detail-team-factory.css";
  import React from "react";
  import { useParams } from "react-router";
  import LogoTeamFactory from "../../../../assets/img/logo_team_factory.png";
  import { TeTeamFactoryAPI } from "../../../../api/teacher/team-factory/TeTeamFactoryAPI";
  import Image from "../../../../../portalprojects/helper/img/Image";
  import { useAppDispatch, useAppSelector } from "../../../../app/hook";
  import {
    GetTeDetailTeam,
    SearchTeDetailTeam,
    SetTeDetailTeam,
  } from "../../../../app/teacher/team-factory/TeDetailTeamFactorySlice.reducer";
  import { Link } from "react-router-dom";
  import LoadingIndicator from "../../../../helper/loading";
  import LoadingIndicatorNoOverlay from "../../../../helper/loadingNoOverlay";
  
  const TeDetailTeamFactory = () => {
    const { id } = useParams();
    const [detailTeam, setDetailTeam] = useState(null);
    const dispatch = useAppDispatch();
    const [loading, setLoading] = useState(false);
    const [value, setValue] = useState("");
  
    useEffect(() => {
      window.scrollTo(0, 0);
      document.title = "Chi tiết team | Lab-Report-App";
      setLoading(true);
      loadData();
      loadDataMemberTeamFactory();
  
      return () => {
        dispatch(SetTeDetailTeam([]));
      };
    }, []);
  
    const loadData = () => {
        TeTeamFactoryAPI.detailTeam(id).then((response) => {
        setDetailTeam(response.data.data);
      });
    };
  
    const loadDataMemberTeamFactory = () => {
        TeTeamFactoryAPI.detailMemberTeamFactory(id).then((response) => {
        dispatch(SetTeDetailTeam(response.data.data));
        setLoading(false);
      });
    };
  
    const data = useAppSelector(GetTeDetailTeam);
  
    const handleChangeValue = (e) => {
      setValue(e.target.value);
    };
  
    const filteredData = data.filter((item) => {
      const name = item.name.toLowerCase();
      const username = item.userName.toLowerCase();
      const search = value.toLowerCase();
      return name.includes(search) || username.includes(search);
    });

    const [allMemberFactory, setAllMemberFactory] = useState([]);
  
    const loadDataAllMemberFactory = () => {
        TeTeamFactoryAPI.getAllMemberFactory().then((response) => {
        setAllMemberFactory(response.data.data);
      });
    };
  
    useEffect(() => {
      loadDataAllMemberFactory();
    }, []);
  
    const [differentItems, setDifferentItems] = useState([]);
  
    useEffect(() => {
      const dataIds = data.map((item) => item.memberId);
      const allMemberFactoryIds = allMemberFactory.map((item) => item.memberId);
  
      const differentIds = allMemberFactoryIds.filter(
        (id) => !dataIds.includes(id)
      );
      const differentItems = allMemberFactory.filter((item) =>
        differentIds.includes(item.memberId)
      );
      setDifferentItems(differentItems);
    }, [data, allMemberFactory]);
  
    const [loadingNoOverlay, setLoadingNoOverlay] = useState(false);
  
  
    return (
      <div className="box-general" style={{ paddingTop: 50 }}>
        {loadingNoOverlay && <LoadingIndicatorNoOverlay />}
        {loading && <LoadingIndicator />}
        <div className="title_activity_management" style={{ marginTop: 0 }}>
          {" "}
          <FontAwesomeIcon icon={faPeopleGroup} style={{ fontSize: "20px" }} />
          <span style={{ marginLeft: "10px" }}>
            <Link to="/teacher/team-factory" style={{ color: "black" }}>
              {" "}
              Danh sách team
            </Link>{" "}
            / {detailTeam != null && detailTeam.name}
          </span>
        </div>
  
        <div
          className="box-son-general"
          style={{ minHeight: "620px", marginTop: 20 }}
        >
          <div
            className="title_detail_team_factory"
            style={{ marginBottom: "20px" }}
          >
            <Row>
              <Col span={8} style={{ padding: 20 }}>
                <img
                  src={LogoTeamFactory}
                  width="35%"
                  style={{
                    borderRadius: 5,
                    border: "1px solid rgb(216, 216, 216)",
                  }}
                />{" "}
                <br />
                <div
                  style={{
                    marginTop: 15,
                    borderBottom: "1px solid gray",
                    paddingBottom: 15,
                  }}
                >
                  <span style={{ fontSize: 20, fontWeight: 500, marginTop: 10 }}>
                    {detailTeam != null && detailTeam.name}
                  </span>
                </div>
                <div
                  style={{
                    marginTop: 15,
                    display: "flex",
                    justifyContent: "space-between",
                  }}
                >
                  <div style={{ fontSize: 16, fontWeight: 500 }}>Mô tả:</div>{" "}
                  <div>
                    <FontAwesomeIcon
                      icon={faCog}
                      style={{
                        fontSize: 18,
                        color: "rgb(38, 144, 214)",
                        cursor: "pointer",
                      }}
                    />
                  </div>
                </div>
                <div style={{ marginTop: 10 }}>
                  {" "}
                  <div>
                    {detailTeam != null
                      ? detailTeam.descriptions
                      : "Chưa có mô tả"}
                  </div>
                </div>
              </Col>
              <Col span={16} style={{ padding: 20 }}>
                <div style={{ display: "flex", alignItems: "center" }}>
                  <div style={{ flex: 1 }}>
                    <Input
                      value={value}
                      onChange={handleChangeValue}
                      type="text"
                      placeholder="🔍 Nhập username, tên thành viên"
                      style={{ width: "100%" }}
                    />
                  </div>
                </div>
                <div style={{ marginTop: 20 }}>
                  
                  <div className="">
                    {filteredData.map((item) => {
                      return (
                        <div className="item-list-member-factory">
                          {" "}
                          <div
                            style={{
                              marginLeft: 30,
                              display: "flex",
                              alignItems: "center",
                              flex: 1,
                            }}
                            className="left-div"
                          >
                            <Image
                              url={item.picture}
                              name={item.name + " " + item.userName}
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
                          </div>
                         
                        </div>
                      );
                    })}
                    {filteredData.length === 0 && (
                      <p style={{ marginTop: 30 }}>
                        {" "}
                        <Empty
                          imageStyle={{ height: 60 }}
                          description={<span>Không có thành viên</span>}
                        />
                      </p>
                    )}
                  </div>
                </div>
              </Col>
            </Row>
          </div>
        </div>
      </div>
    );
  };
  
  export default TeDetailTeamFactory;
  