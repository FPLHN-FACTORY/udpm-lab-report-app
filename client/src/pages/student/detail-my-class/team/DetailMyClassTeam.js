import { useParams } from "react-router-dom";
import "./style-detail-my-class-team.css";
import { Row, Col, Table, Button } from "antd";
import { Link } from "react-router-dom";
import { ControlOutlined } from "@ant-design/icons";
import { useAppDispatch } from "../../../../app/hook";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import moment from "moment";

const DetailMyClassTeam = () => {
  const dispatch = useAppDispatch();

  const columns = [
    {
      title: "Name",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Age",
      dataIndex: "age",
      key: "age",
    },
    {
      title: "Address",
      dataIndex: "address",
      key: "address",
    },
  ];

  return (
    <>
      <div className="title-teacher-my-class">
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{ fontSize: "18px", marginLeft: "10px", fontWeight: "500" }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - Lớp của tôi</span>
        </span>
      </div>
      <div className="box-students-in-class">
        <div className="button-menu-teacher">
          <div>
            <Link
              id="menu-checked"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
              }}
            >
              DANH SÁCH NHÓM
            </Link>
            <Link
              className="custom-link"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
              }}
            >
              DANH SÁCH BUỔI HỌC
            </Link>
            <hr />
            <div className="info-team">
              <span className="info-heading">Thông tin nhóm:</span>
              <div className="group-info">
                <span
                  className="group-info-item"
                  style={{ marginTop: "10px", marginBottom: "15px" }}
                >
                  Tên nhóm: Nhóm 1
                </span>
                <span className="group-info-item">Tên đề tài: Bán hàng</span>
              </div>
            </div>
            <div className="table-member-team">
              <span className="info-heading" style={{ fontSize: "16px" }}>
                Danh sách thành viên trong nhóm:
              </span>
            </div>
            <div style={{ marginTop: "8px" }}>
              <Table columns={columns} />
              <Button className="btnRoiNhom">Rời nhóm</Button>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default DetailMyClassTeam;
