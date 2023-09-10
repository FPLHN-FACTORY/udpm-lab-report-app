import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-poin-my-class.css";
import {
  faDownload,
  faFloppyDisk,
  faMarker,
  faUpload,
} from "@fortawesome/free-solid-svg-icons";
import { ControlOutlined } from "@ant-design/icons";
import { Link, useParams } from "react-router-dom";
import { Button, Row } from "antd";
import { useEffect, useState } from "react";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherStudentClassesAPI } from "../../../../api/teacher/student-class/TeacherStudentClasses.api";
import { TeacherPointAPI } from "../../../../api/teacher/point/TeacherPoint.api";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { SetPoint } from "../../../../app/teacher/point/tePointSlice.reduce";
import TablePoint from "./table-point-student/TablePoint";
import LoadingIndicator from "../../../../helper/loading";

const PointManagement = () => {
  const { idClass } = useParams();
  const dispatch = useAppDispatch();
  const [classDetail, setClassDetail] = useState({});
  const [listStudentClassAPI, setListStudentClassAPI] = useState([]);
  const [listAll, setListAll] = useState([]);
  const [checkPoint, setCheckPoint] = useState(false);
  const [listPoint, setListPoint] = useState([]);
  const [loading, setLoading] = useState(false);
  const [loadingData, setLoadingData] = useState(false);

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - Bảng điểm";
    fetchData(idClass);
  }, []);

  const fetchData = async (idClass) => {
    await Promise.all([
      await featchStudentClass(idClass),
      await featchClass(idClass),
      await featchPoint(idClass),
    ]);
  };

  const featchPoint = async (idClass) => {
    try {
      await TeacherPointAPI.getPointByIdClass(idClass).then((response) => {
        console.log("list point api");
        console.log(response.data.data);
        if (response.data.data.length >= 1) {
          setCheckPoint(true);
          setListPoint(response.data.data);
          featchStudentPoint(idClass);
        } else {
          setCheckPoint(false);
          featchStudentClass(idClass);
          featchStudentPoint(idClass);
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

  const featchStudentPoint = async (idClass) => {
    try {
      if (checkPoint) {
        const listShowTable = listStudentClassAPI.map((item1) => {
          const matchedObject = listPoint.find(
            (item2) =>
              item2.idStudent === item1.idStudent && item2.idClass === idClass
          );
          return {
            ...item1,
            ...matchedObject,
          };
        });
        console.log("db exist");
        console.log(listShowTable);
        dispatch(SetPoint(listShowTable));
      } else {
        const listShowTable = listStudentClassAPI.map((item1) => {
          return {
            ...item1,
            checkPointPhase1: "0.0",
            checkPointPhase2: "0.0",
            finalPoint: "0.0",
          };
        });
        console.log("DB Null");
        console.log(listShowTable);
        dispatch(SetPoint(listShowTable));
      }
      if (loadingData === true) {
        setLoading(true);
      }
      setLoadingData(true);
    } catch (error) {
      alert(error.message);
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
  useEffect(() => {
    if (loadingData === true) {
      fetchData(idClass);
    }
  }, [loadingData]);
  return (
    <>
      {" "}
      {!loading && <LoadingIndicator />}
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
                  style={{
                    backgroundColor: "rgb(38, 144, 214)",
                    color: "white",
                  }}
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
                <TablePoint></TablePoint>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default PointManagement;
