import {
  faListAlt,
  faObjectGroup,
  faPencilAlt,
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
import { Col, Row } from "antd";
import LoadingIndicator from "../../../../helper/loading";

const DetailGroupProject = () => {
  const { id } = useParams();
  const [detailGroupProject, setDetailGroupProject] = useState(null);
  const [selectedImageUrl, setSelectedImageUrl] = useState("");
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);

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
      if (response.data.data.backgroundImage) {
        setSelectedImageUrl(response.data.data.backgroundImage);
      }
      setLoading(false);
    });
  };

  const loadDataAllProject = () => {
    AdGroupProjectAPI.getAllProject(id).then((response) => {
      console.log(response.data.data);
      dispatch(SetAdDetailGroupProject(response.data.data));
    });
  };

  const data = useAppSelector(GetAdDetailGroupProject);

  return (
    <div className="box-general" style={{ paddingTop: 50 }}>
      {loading && <LoadingIndicator />}
      <div className="title_activity_management" style={{ marginTop: 0 }}>
        {" "}
        <FontAwesomeIcon icon={faObjectGroup} style={{ fontSize: "20px" }} />
        <span style={{ marginLeft: "10px" }}>
          Quản lý nhóm dự án /{" "}
          {detailGroupProject != null ? detailGroupProject.name : ""}
        </span>
      </div>
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
          <div style={{ width: "100%", display: "flex", alignItems: "center" }}>
            <img
              src={selectedImageUrl !== "" ? selectedImageUrl : DeFault}
              width="10%"
              height="10%"
              style={{ objectFit: "cover", borderRadius: 5 }}
            />{" "}
            <span style={{ fontSize: 22, fontWeight: 500, marginLeft: 10 }}>
              {detailGroupProject != null && detailGroupProject.name}
            </span>{" "}
            <span>
              <FontAwesomeIcon
                icon={faPencilAlt}
                style={{ marginLeft: 10, cursor: "pointer" }}
              />
            </span>
          </div>{" "}
          <div style={{ marginTop: 6 }}>
            {detailGroupProject != null ? detailGroupProject.descriptions : ""}
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
                <Col span={5} style={{ padding: 7 }}>
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
            <Col span={5} style={{ padding: 5 }}>
              <div
                style={{
                  height: 120,
                  width: "100%",
                  display: "flex",
                  borderRadius: 5,
                  alignItems: "center",
                  justifyContent: "center",
                  backgroundColor: "rgb(239, 239, 239)",
                  cursor: "pointer",
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
  );
};

export default DetailGroupProject;
