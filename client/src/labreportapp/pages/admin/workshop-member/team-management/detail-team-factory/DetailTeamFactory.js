import {
  faCog,
  faPeopleGroup,
  faUser,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Checkbox, Col, Input, Row } from "antd";
import { useEffect, useState } from "react";
import "./style-detail-team-factory.css";
import React from "react";
import { useParams } from "react-router";
import LogoTeamFactory from "../../../../../assets/img/logo_team_factory.png";
import { AdTeamAPI } from "../../../../../api/admin/AdTeamAPI";
import Image from "../../../../../../portalprojects/helper/img/Image";

const DetailTeamFactory = () => {
  const { id } = useParams();
  const [detailTeam, setDetailTeam] = useState(null);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Chi tiết team | Lab-Report-App";

    loadData();
  }, []);

  const loadData = () => {
    AdTeamAPI.detailTeam(id).then((response) => {
      setDetailTeam(response.data.data);
    });
  };

  return (
    <div className="box-general" style={{ paddingTop: 50 }}>
      <div className="title_activity_management" style={{ marginTop: 0 }}>
        {" "}
        <FontAwesomeIcon icon={faPeopleGroup} style={{ fontSize: "20px" }} />
        <span style={{ marginLeft: "10px" }}>Quản lý team / Chi tiết team</span>
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
                  >
                    Thêm thành viên
                  </Button>
                </div>
              </div>
              <div style={{ marginTop: 20 }}>
                <div className="header-list-member-factory">
                  <Checkbox />{" "}
                  <div style={{ marginLeft: 30 }}>
                    <FontAwesomeIcon icon={faUser} />{" "}
                    <span style={{ marginLeft: 5 }}>20 thành viên</span>
                  </div>
                </div>
                <div className="">
                  <div className="item-list-member-factory">
                    <Checkbox />{" "}
                    <div
                      style={{
                        marginLeft: 30,
                        display: "flex",
                        alignItems: "center",
                      }}
                    >
                      <Image url={LogoTeamFactory} picxel={45} />{" "}
                      <span
                        style={{
                          marginLeft: 15,
                          color: "rgb(38, 144, 214)",
                          fontWeight: 500,
                        }}
                      >
                        Nguyễn Công Thắng
                      </span>
                      <span
                        style={{
                          marginLeft: 10,
                          color: "gray",
                          fontSize: 14,
                        }}
                      >
                        thangncph26123
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </Col>
          </Row>
        </div>
      </div>
    </div>
  );
};

export default DetailTeamFactory;
