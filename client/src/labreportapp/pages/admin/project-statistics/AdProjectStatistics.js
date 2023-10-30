import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-ad-project-statistics.css";
import { faCircleInfo, faLineChart } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { Card, Col, Row, Tag } from "antd";
import {
  CheckCircleOutlined,
  CloseCircleOutlined,
  SyncOutlined,
} from "@ant-design/icons";

const AdProjectStatistics = () => {
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Thống kê dự án xưởng | Lab-Report-App";
  }, []);

  return (
    <>
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon
              icon={faLineChart}
              style={{ marginRight: "8px" }}
            />{" "}
            Thống kê dự án xưởng
          </span>
        </div>
      </div>
      <div className="box-general" style={{ paddingTop: "0px" }}>
        <div className="box-son-general">
          <Row>
            <span style={{ fontSize: "18px", fontWeight: "500" }}>
              <FontAwesomeIcon
                icon={faCircleInfo}
                style={{ marginRight: "7px" }}
              />
              Chi tiết
            </span>
          </Row>
          <Row gutter={26} style={{ display: "flex", textAlign: "center" }}>
            <Col span={6}>
              <Card
                bordered={true}
                style={{ color: "red", fontWeight: "500", fontSize: "18px" }}
              >
                <Tag
                  style={{
                    border: "none",
                    backgroundColor: "white",
                    color: "red",
                    fontSize: "18px",
                  }}
                  icon={<CloseCircleOutlined />}
                />
                <span>Chưa diễn ra</span>
                <span
                  style={{
                    paddingLeft: "6px",
                  }}
                >
                  10
                </span>
              </Card>
            </Col>
            <Col span={6}>
              <Card
                bordered={true}
                style={{
                  color: "#2690D6",
                  fontWeight: "500",
                  fontSize: "18px",
                }}
              >
                <Tag
                  style={{
                    border: "none",
                    backgroundColor: "white",
                    color: "#2690D6",
                    fontSize: "18px",
                  }}
                  icon={<SyncOutlined spin />}
                />
                <span>Đang diễn ra</span>
                <span
                  style={{
                    paddingLeft: "6px",
                  }}
                >
                  10
                </span>
              </Card>
            </Col>
            <Col span={6}>
              <Card
                bordered={true}
                style={{ color: "green", fontWeight: "500", fontSize: "18px" }}
              >
                <Tag
                  style={{
                    border: "none",
                    backgroundColor: "white",
                    color: "green",
                    fontSize: "18px",
                  }}
                  icon={<CheckCircleOutlined />}
                />
                <span>Đã diễn ra</span>
                <span
                  style={{
                    paddingLeft: "6px",
                  }}
                >
                  10
                </span>
              </Card>
            </Col>
          </Row>
        </div>
      </div>
    </>
  );
};

export default AdProjectStatistics;
