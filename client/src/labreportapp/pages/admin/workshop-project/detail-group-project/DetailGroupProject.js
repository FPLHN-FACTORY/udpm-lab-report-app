import {
  faListAlt,
  faObjectGroup,
  faPlus,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-detail-group-project.css";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import { AdGroupProjectAPI } from "../../../../api/admin/AdGroupProjectAPI";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import {
  GetAdDetailGroupProject,
  SetAdDetailGroupProject,
} from "../../../../app/admin/AdDetailGroupProjectSlice.reducer";
import { Link } from "react-router-dom";
import DeFault from "../../../../assets/img/FPT_Polytechnic_Hanoi-2.jpg";
import { Col, Input, Row, message } from "antd";
import LoadingIndicator from "../../../../helper/loading";
import ModalCreateProjectGroup from "./modal-create-project/ModalCreateProjectGroup";
import LoadingIndicatorNoOverlay from "../../../../helper/loadingNoOverlay";
import LoadingBalls from "../../../../helper/loadingBalls";

const DetailGroupProject = () => {
  const { id } = useParams();
  const [detailGroupProject, setDetailGroupProject] = useState(null);
  const [selectedImageUrl, setSelectedImageUrl] = useState("");
  const [showCreateModal, setShowCreateModal] = useState(false);
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);
  const [editName, setEditName] = useState(false);
  const [editDescription, setEditDescription] = useState(false);
  const [name, setName] = useState("");
  const [descriptions, setDescriptions] = useState("");
  const [loadingWait, setLoadingWait] = useState(false);

  useEffect(() => {
    setLoading(true);
    loadDataDetailGroupProject();
    loadDataAllProject();
    return () => {
      dispatch(SetAdDetailGroupProject([]));
    };
  }, []);

  const loadDataDetailGroupProject = () => {
    AdGroupProjectAPI.detailGroupProject(id).then((response) => {
      setDetailGroupProject(response.data.data);
      setName(response.data.data.name != null ? response.data.data.name : "");
      setDescriptions(
        response.data.data.descriptions ? response.data.data.descriptions : ""
      );
      if (response.data.data.backgroundImage) {
        setSelectedImageUrl(response.data.data.backgroundImage);
      }
      setLoading(false);
    });
  };

  const loadDataAllProject = () => {
    AdGroupProjectAPI.getAllProject(id).then((response) => {
      dispatch(SetAdDetailGroupProject(response.data.data));
    });
  };
  const handleCancelModalCreateSusscess = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreateModal(false);
  };
  const handleCancelModalCreateFaild = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreateModal(false);
  };
  const handleCancelCreate = {
    handleCancelModalCreateSusscess,
    handleCancelModalCreateFaild,
  };
  const data = useAppSelector(GetAdDetailGroupProject);

  const handleKeyDown = (event) => {
    const request = { id: id, name: name, descriptions: descriptions };
    if (event.key === "Enter") {
      setLoadingWait(true);
      if (name.length < 6 || name.length > 255) {
        setLoadingWait(false);
        message.warning("Tên nhóm phải lớn hơn 6 và nhỏ hơn 255 ký tự !");
      } else {
        setEditName(false);
        setEditDescription(false);
        AdGroupProjectAPI.updateTitleGroupProject(request)
          .then((response) => {
            setDetailGroupProject(response.data.data);
            setName(response.data.data.name);
            setDescriptions(
              response.data.data.descriptions
                ? response.data.data.descriptions
                : ""
            );
            if (response.data.data.backgroundImage) {
              setSelectedImageUrl(response.data.data.backgroundImage);
            }
            setLoadingWait(false);
            message.success("Cập nhật thành công !");
          })
          .catch((error) => {
            setLoadingWait(false);
            message.error(error.response.data.message);
          });
      }
    }
  };
  return (
    <>
      <ModalCreateProjectGroup
        visible={showCreateModal}
        onCancel={handleCancelCreate}
        nameGroup={detailGroupProject != null ? detailGroupProject.name : ""}
      />
      {loading && <LoadingIndicator />}
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon
              icon={faObjectGroup}
              style={{ fontSize: "20px" }}
            />
            <span style={{ marginLeft: "10px" }}>
              Quản lý nhóm dự án /{" "}
              {detailGroupProject != null ? detailGroupProject.name : ""}
            </span>
          </span>
        </div>
      </div>
      <div className="box-general" style={{ paddingTop: "5px" }}>
        <div
          className="box-son-general"
          style={{ minHeight: "620px", marginTop: "20px" }}
        >
          <div
            className="title__group__project"
            style={{
              marginBottom: "20px",
              marginLeft: 30,
              borderBottom: "1px solid gray",
              paddingBottom: 25,
            }}
          >
            <div
              style={{ width: "100%", display: "flex", alignItems: "center" }}
            >
              <img
                src={selectedImageUrl !== "" ? selectedImageUrl : DeFault}
                width="10%"
                height="10%"
                style={{ objectFit: "cover", borderRadius: 5 }}
              />{" "}
              {editName ? (
                <>
                  <Input
                    type="text"
                    value={name}
                    onChange={(e) => {
                      setName(e.target.value);
                      setEditName(true);
                    }}
                    onKeyDown={handleKeyDown}
                    style={{
                      marginLeft: "10px",
                      minWidth: "50%",
                      width: "auto",
                      backgroundColor: "transparent",
                      border: "none",
                      borderBottom: "2px solid black",
                      fontWeight: "bold",
                      fontSize: "22px",
                    }}
                    className="text-3xl font-black"
                  />
                </>
              ) : (
                <span
                  className="text-3xl font-black"
                  onClick={() => setEditName(true)}
                  style={{
                    fontSize: "22px",
                    fontWeight: "500",
                    marginLeft: "10px",
                  }}
                >
                  {name}
                </span>
              )}
            </div>
            <div style={{ marginTop: "6px" }}>
              {editDescription ? (
                <>
                  <Input
                    type="text"
                    value={descriptions}
                    onChange={(e) => {
                      setDescriptions(e.target.value);
                      setEditDescription(true);
                    }}
                    onKeyDown={handleKeyDown}
                    style={{
                      backgroundColor: "transparent",
                      border: "none",
                      minWidth: "50%",
                      width: "auto",
                      borderBottom: "2px solid black",
                      fontWeight: "500",
                    }}
                    className="text-3xl font-black"
                  />
                </>
              ) : (
                <span
                  className="text-3xl font-black"
                  onClick={() => setEditDescription(true)}
                >
                  {descriptions != null ? descriptions : ""}
                </span>
              )}
            </div>
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                textAlign: "center",
              }}
            >
              {loadingWait && <LoadingBalls />}
            </div>
          </div>
          <div style={{ marginTop: 25 }}>
            <span style={{ fontSize: 17, marginLeft: 29 }}>
              <FontAwesomeIcon icon={faListAlt} style={{ cursor: "pointer" }} />
              <span style={{ marginLeft: 10 }}>Danh sách dự án</span>
            </span>
          </div>
          <div style={{ marginTop: 25, marginLeft: 25 }}>
            <Row>
              {data.map((item) => {
                return (
                  <Col span={6} style={{ padding: 7 }}>
                    <div style={{ position: "relative" }}>
                      <Link to={`/detail-project/${item.id}`}>
                        {item.backGroundImage != null && (
                          <img
                            src={item.backGroundImage}
                            alt="Nhóm dự án"
                            style={{
                              objectFit: "cover",
                              height: 120,
                              width: "100%",
                              borderRadius: 5,
                              boxShadow: "4px 4px 4px rgb(223, 223, 223)",
                            }}
                          />
                        )}
                        {item.backGroundColor != null && (
                          <div
                            className="div__backGroundColor"
                            style={{
                              backgroundColor: item.backGroundColor,
                              height: 120,
                              width: "100%",
                              borderRadius: 5,
                              boxShadow: "4px 4px 4px rgb(223, 223, 223)",
                            }}
                          ></div>
                        )}
                      </Link>
                      <div
                        style={{
                          position: "absolute",
                          top: 0,
                          left: 0,
                          fontSize: 19,
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
                  </Col>
                );
              })}
              <Col span={6} style={{ padding: "5px" }}>
                <div
                  style={{
                    height: 120,
                    width: "100%",
                    display: "flex",
                    borderRadius: "5px",
                    alignItems: "center",
                    justifyContent: "center",
                    backgroundColor: "rgb(239, 239, 239)",
                    cursor: "pointer",
                  }}
                  onClick={() => {
                    setShowCreateModal(true);
                  }}
                >
                  <FontAwesomeIcon icon={faPlus} style={{ marginRight: 7 }} />
                  Thêm mới dự án
                </div>
              </Col>
            </Row>
          </div>
        </div>
      </div>
    </>
  );
};

export default DetailGroupProject;
