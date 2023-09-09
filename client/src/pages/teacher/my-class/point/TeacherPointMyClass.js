import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-poin-my-class.css";
import {
  faDownload,
  faFloppyDisk,
  faLineChart,
  faMarker,
  faUpload,
} from "@fortawesome/free-solid-svg-icons";
import { ControlOutlined } from "@ant-design/icons";
import { Link, useParams } from "react-router-dom";
import { Button, Row, Table } from "antd";
import { Divider } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherStudentClassesAPI } from "../../../../api/teacher/student-class/TeacherStudentClasses.api";
import { TeacherPointAPI } from "../../../../api/teacher/point/TeacherPoint.api";

const PointManagement = () => {
  const { idClass } = useParams();
  const [classDetail, setClassDetail] = useState({});
  const [listStudentClassAPI, setListStudentClassAPI] = useState([]);
  const [checkPoint, setCheckPoint] = useState(false);
  const [listPoint, setListPoint] = useState([]);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - Bảng điểm";
    featchClass(idClass);
    featchStudentClass(idClass);
    featchPoint(idClass);
  }, []);

  const featchPoint = async (idClass) => {
    try {
      await TeacherPointAPI.getPointByIdClass(idClass).then((response) => {
        console.log(response.data.data);
        if (response.data.data.length >= 1) {
          setCheckPoint(true);
          setListPoint(response.data.data);
          //featInforStudent(idClass);
        } else {
          setCheckPoint(false);
          featchStudentClass(idClass);
          //featInforStudent(idClass);
        }
      });
    } catch (error) {
      alert(error.message);
    }
  };

  const featchStudentClass = async (idClass) => {
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(idClass).then(
        (responese) => {
          const listAPI = responese.data.data.map((item) => {
            return { ...item };
          });
          setListStudentClassAPI(listAPI);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang");
    }
  };

  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassDetail(responese.data.data);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => <span>{index + 1}</span>,
    },
    {
      title: "Tên sinh viên",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Điểm giai đoạn 1",
      dataIndex: "checkPointPhase1",
      key: "checkPointPhase1",
    },
    {
      title: "Điểm giai đoạn 2",
      dataIndex: "checkPointPhase2",
      key: "checkPointPhase2",
    },
    {
      title: "Điểm giai Final",
      dataIndex: "finalPoint",
      key: "finalPoint",
    },
  ];

  const dataSource = listStudentClassAPI;

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
                  {classDetail.code}
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
              <Button
                style={{
                  backgroundColor: "rgb(38, 144, 214)",
                  color: "white",
                  marginRight: "0px",
                  marginLeft: "auto",
                }}
              >
                <FontAwesomeIcon
                  icon={faFloppyDisk}
                  style={{ marginRight: "7px" }}
                />
                Lưu bảng điểm
              </Button>
            </Row>
            <div style={{ marginTop: 20 }}>
              <Table
                rowKey="id"
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
