import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-point-management.css";
import {
  faDownload,
  faLineChart,
  faMarker,
  faUpload,
} from "@fortawesome/free-solid-svg-icons";
import { ControlOutlined } from "@ant-design/icons";
import { Link, useParams } from "react-router-dom";
import { Button, Row, Table } from "antd";
import { Divider } from "@chakra-ui/react";

const PointManagement = () => {
  const { idClass } = useParams();
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => <span>{index + 1}</span>,
    },
    {
      title: "Tên sinh viên",
      dataIndex: "age",
      key: "age",
    },
    {
      title: "Email",
      dataIndex: "age",
      key: "age",
    },
    {
      title: "Điểm giai đoạn 1",
      dataIndex: "age",
      key: "age",
    },
    {
      title: "Điểm giai đoạn 2",
      dataIndex: "address",
      key: "address",
    },
    {
      title: "Điểm giai Final",
      dataIndex: "address",
      key: "address",
    },
    // Thêm cột khác nếu cần
  ];

  const dataSource = [
    {
      key: "1",
      name: "John Doe",
      age: 30,
      address: "123 Main St",
    },
    {
      key: "2",
      name: "Jane Smith",
      age: 25,
      address: "456 Elm St",
    },
    // Thêm dữ liệu khác nếu cần
  ];

  return (
    <div className="box-general-custom">
      <div className="title-meeting-managemnt-my-class">
        <Link
          to="/teacher/my-class"
          style={{ color: "black", marginLeft: "8px" }}
        >
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <ControlOutlined style={{ fontSize: "22px" }} />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>{" "}
            <span style={{ color: "gray", fontSize: "14px" }}>
              {" "}
              - Điểm danh
            </span>
          </span>
        </Link>
      </div>
      <div className="box-general" style={{ padding: 25, marginTop: 0 }}>
        <div
          className="box-son-general"
          style={{ marginTop: 0, padding: "20px" }}
        >
          <div className="button-menu">
            <div>
              {" "}
              <Link
                to={`/teacher/my-class/post/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                BÀI ĐĂNG &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/students/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                THÔNG TIN LỚP HỌC &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/teams/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                QUẢN LÝ NHÓM &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/meeting/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                BUỔI HỌC &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/attendance/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM DANH &nbsp;
              </Link>
              <Link
                id="menu-checked"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM &nbsp;
              </Link>
              <div
                className="box-center"
                style={{
                  height: "28.5px",
                  width: "auto",
                  backgroundColor: "#007bff",
                  color: "white",
                  borderRadius: "5px",
                  float: "right",
                }}
              >
                {" "}
                <span style={{ fontSize: "14px", padding: "10px" }}>
                  aaaaaaa
                </span>
              </div>
              <hr />
            </div>
          </div>
          <div style={{ marginTop: "10px" }}>
            <Row>
              <span style={{ fontSize: "17px", fontWeight: 500 }}>
                <FontAwesomeIcon icon={faMarker} /> Điểm sinh viên:
              </span>
            </Row>
            <Row style={{ marginTop: "10px" }}>
              <Button
                style={{
                  backgroundColor: "rgb(38, 144, 214)",
                  color: "white",
                  marginRight: "5px",
                }}
              >
                <FontAwesomeIcon
                  icon={faDownload}
                  style={{ marginRight: "7px" }}
                />
                Export mẫu điểm
              </Button>
              <Button
                style={{ backgroundColor: "rgb(38, 144, 214)", color: "white" }}
              >
                <FontAwesomeIcon
                  icon={faUpload}
                  style={{ marginRight: "7px" }}
                />
                Import điểm
              </Button>
            </Row>
            <div style={{ marginTop: 10 }}>
              <Table
                columns={columns}
                dataSource={dataSource}
                pagination={false}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PointManagement;
