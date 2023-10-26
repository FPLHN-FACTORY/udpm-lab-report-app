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
import "./style-detail-team-factory.css";
import React from "react";
import { useParams } from "react-router";
import LogoTeamFactory from "../../../../../assets/img/logo_team_factory.png";
import { AdTeamAPI } from "../../../../../api/admin/AdTeamAPI";
import Image from "../../../../../../portalprojects/helper/img/Image";
import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
import {
  DeleteListMemberTeamFactory,
  DeleteMemberTeamFactory,
  GetAdDetailTeam,
  SearchAdDetailTeam,
  SetAdDetailTeam,
} from "../../../../../app/admin/AdDetailTeamSlice.reducer";
import { Link } from "react-router-dom";
import LoadingIndicator from "../../../../../helper/loading";
import ModalAddMemberTeam from "./modal-add-member-team/ModalAddMemberTeam";
import LoadingIndicatorNoOverlay from "../../../../../helper/loadingNoOverlay";

const DetailTeamFactory = () => {
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
      dispatch(SetAdDetailTeam([]));
    };
  }, []);

  const loadData = () => {
    AdTeamAPI.detailTeam(id).then((response) => {
      setDetailTeam(response.data.data);
    });
  };

  const loadDataMemberTeamFactory = () => {
    AdTeamAPI.detailMemberTeamFactory(id).then((response) => {
      dispatch(SetAdDetailTeam(response.data.data));
      setLoading(false);
    });
  };

  const data = useAppSelector(GetAdDetailTeam);

  const handleChangeValue = (e) => {
    setValue(e.target.value);
  };

  const filteredData = data.filter((item) => {
    const name = item.name.toLowerCase();
    const username = item.userName.toLowerCase();
    const search = value.toLowerCase();
    return name.includes(search) || username.includes(search);
  });

  const [isCheckedAll, setIsCheckedAll] = useState(false);
  const [checkedItems, setCheckedItems] = useState([]);

  const handleCheckAll = () => {
    setIsCheckedAll(!isCheckedAll);

    if (!isCheckedAll) {
      setCheckedItems(data.map((item) => item.idMemberTeamFactory));
    } else {
      setCheckedItems([]);
    }
  };

  const handleCheckItem = (id) => {
    const index = checkedItems.indexOf(id);
    const newCheckedItems = [...checkedItems];

    if (index !== -1) {
      newCheckedItems.splice(index, 1);
    } else {
      newCheckedItems.push(id);
    }

    setCheckedItems(newCheckedItems);

    setIsCheckedAll(newCheckedItems.length === data.length);
  };

  const isItemChecked = (id) => checkedItems.includes(id);

  const [allMemberFactory, setAllMemberFactory] = useState([]);

  const loadDataAllMemberFactory = () => {
    AdTeamAPI.getAllMemberFactory().then((response) => {
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

  const [showModalAddMemberTeam, setShowModalAddMemberTeam] = useState(false);

  const openModalAddMemberTeam = () => {
    setShowModalAddMemberTeam(true);
  };

  const cancelShowModalAddMemberTeam = () => {
    setShowModalAddMemberTeam(false);
  };

  const [loadingNoOverlay, setLoadingNoOverlay] = useState(false);

  const deleteMemberTeamFactory = (id) => {
    setLoadingNoOverlay(true);
    AdTeamAPI.deleteMemberTeamFactory(id).then(
      (response) => {
        dispatch(DeleteMemberTeamFactory(response.data.data));
        message.success("Xóa thành công");
        setLoadingNoOverlay(false);
      },
      (error) => {}
    );
  };

  const deleteListMemberTeamFactory = () => {
    setLoadingNoOverlay(true);
    AdTeamAPI.deleteListMemberTeamFactory({
      listIdMemberFactory: checkedItems,
    }).then(
      (response) => {
        dispatch(DeleteListMemberTeamFactory(response.data.data));
        message.success("Xóa thành công");
        setLoadingNoOverlay(false);
        setIsCheckedAll(false);
      },
      (error) => {}
    );
  };

  return (
    <>
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
            <span style={{ marginLeft: "10px" }}>
              <Link to="/admin/team-management" style={{ color: "black" }}>
                {" "}
                Quản lý team
              </Link>{" "}
              / {detailTeam != null && detailTeam.name}
            </span>
          </span>
        </div>
      </div>
      <div className="box-general" style={{ paddingTop: 10, marginTop: 0 }}>
        {loadingNoOverlay && <LoadingIndicatorNoOverlay />}
        {loading && <LoadingIndicator />}
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
                  <span
                    style={{ fontSize: 20, fontWeight: 500, marginTop: 10 }}
                  >
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
                      style={{ width: "55%" }}
                    />
                  </div>
                  <div>
                    <Button
                      style={{
                        backgroundColor: "rgb(38, 144, 214)",
                        color: "white",
                      }}
                      onClick={openModalAddMemberTeam}
                    >
                      <FontAwesomeIcon
                        icon={faPlus}
                        style={{ marginRight: 5 }}
                      />{" "}
                      Thêm thành viên
                    </Button>
                  </div>
                </div>
                <div style={{ marginTop: 20 }}>
                  <div className="header-list-member-factory">
                    <Checkbox
                      checked={isCheckedAll}
                      onChange={handleCheckAll}
                    />{" "}
                    <div style={{ marginLeft: 30 }}>
                      <FontAwesomeIcon icon={faUser} />{" "}
                      <span style={{ marginLeft: 5 }}>
                        {data != null && data.length} thành viên
                      </span>
                      <Popconfirm
                        placement="left"
                        title="Xóa nhiều thành viên"
                        description="Bạn có chắc chắn muốn xóa những thành viên đã chọn ra khỏi nhóm không ?"
                        onConfirm={() => {
                          deleteListMemberTeamFactory();
                        }}
                        okText="Có"
                        cancelText="Không"
                      >
                        {" "}
                        <Button
                          style={{
                            marginLeft: 25,
                            color: "white",
                            backgroundColor: "rgb(244, 65, 65)",
                          }}
                        >
                          <FontAwesomeIcon
                            icon={faTrashCan}
                            style={{ marginRight: 9 }}
                          />
                          Xóa
                        </Button>
                      </Popconfirm>
                    </div>
                  </div>
                  <div className="">
                    {filteredData.map((item) => {
                      return (
                        <div className="item-list-member-factory">
                          <Checkbox
                            checked={isItemChecked(item.idMemberTeamFactory)}
                            onChange={() =>
                              handleCheckItem(item.idMemberTeamFactory)
                            }
                          />{" "}
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
                          <div
                            className="right-div"
                            style={{ paddingRight: 30 }}
                          >
                            <Popconfirm
                              placement="topLeft"
                              title="Xóa thành viên"
                              description="Bạn có chắc chắn muốn xóa thành viên này ra khỏi nhóm không ?"
                              onConfirm={() => {
                                deleteMemberTeamFactory(
                                  item.idMemberTeamFactory
                                );
                              }}
                              okText="Có"
                              cancelText="Không"
                            >
                              {" "}
                              <FontAwesomeIcon
                                icon={faTrashCan}
                                style={{ cursor: "pointer" }}
                              />{" "}
                            </Popconfirm>
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
        <ModalAddMemberTeam
          visible={showModalAddMemberTeam}
          allMemberFactory={differentItems}
          onCancel={cancelShowModalAddMemberTeam}
          fetchAll={loadData}
          fetchAllMemberFactory={loadDataMemberTeamFactory}
        />
      </div>
    </>
  );
};

export default DetailTeamFactory;
