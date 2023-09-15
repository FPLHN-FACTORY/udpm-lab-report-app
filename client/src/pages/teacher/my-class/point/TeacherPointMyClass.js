import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-poin-my-class.css";
import { faFloppyDisk, faMarker } from "@fortawesome/free-solid-svg-icons";
import { ControlOutlined } from "@ant-design/icons";
import { Link, useParams } from "react-router-dom";
import { Button, Row } from "antd";
import { useEffect, useState } from "react";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherStudentClassesAPI } from "../../../../api/teacher/student-class/TeacherStudentClasses.api";
import { TeacherPointAPI } from "../../../../api/teacher/point/TeacherPoint.api";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import {
  GetPoint,
  SetPoint,
  UpdatePoint,
} from "../../../../app/teacher/point/tePointSlice.reduce";
import TablePoint from "./table-point-student/TablePoint";
import LoadingIndicator from "../../../../helper/loading";
import { toast } from "react-toastify";
import ButtonExportExcel from "./export-excel/ButtonExportExcel";
import ButtonImportExcel from "./import-excel/ButtonImportExcel";
import { SetTTrueToggle } from "../../../../app/teacher/TeCollapsedSlice.reducer";
const TeacherPointMyClass = () => {
  const { idClass } = useParams();
  const dispatch = useAppDispatch();
  const [classDetail, setClassDetail] = useState({});
  const [listStudentClassAPI, setListStudentClassAPI] = useState([]);
  const [checkPoint, setCheckPoint] = useState(false);
  const [listPoint, setListPoint] = useState([]);
  const [loading, setLoading] = useState(false);
  const [loadingData, setLoadingData] = useState(false);
  dispatch(SetTTrueToggle());
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
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
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
            finalPoint:
              parseFloat(
                parseFloat(matchedObject.checkPointPhase1) +
                  parseFloat(matchedObject.checkPointPhase2)
              ) / 2,
          };
        });
        dispatch(SetPoint(listShowTable));
      } else {
        const listShowTable = listStudentClassAPI.map((item1) => {
          return {
            ...item1,
            idClass: idClass,
            checkPointPhase1: parseFloat("0"),
            checkPointPhase2: parseFloat("0"),
            finalPoint: parseFloat("0"),
          };
        });
        dispatch(SetPoint(listShowTable));
      }
      if (loadingData === true) {
        setLoading(true);
      }
      setLoadingData(true);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
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
  const handleSave = async () => {
    try {
      let dataFind = {
        listPoint: data,
      };
      await TeacherPointAPI.createOrUpdate(dataFind).then((respone) => {
        dispatch(UpdatePoint(respone.data.data));
        toast.success("Lưu bảng điểm thành công !");
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const data = useAppSelector(GetPoint);
  return (
    <>
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
            style={{ marginTop: 0, padding: "20px", minHeight: "555px" }}
          >
            <div className="button-menu">
              <div>
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
                  to={`/teacher/my-class/point/${idClass}`}
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
                  <span
                    style={{
                      fontSize: "14px",
                      padding: "15px",
                      fontWeight: 500,
                    }}
                  >
                    {classDetail.code}
                  </span>
                </div>
                <hr />
              </div>
            </div>
            <div style={{ marginTop: "15px" }}>
              <Row style={{ margin: "15px 0px 15px 15px" }}>
                <span
                  style={{
                    fontSize: "17px",
                    fontWeight: 500,
                    marginRight: "10px",
                  }}
                >
                  <FontAwesomeIcon icon={faMarker} /> Điểm sinh viên :
                </span>
              </Row>
              <Row style={{ marginTop: "10px" }}>
                <ButtonExportExcel idClass={idClass} />
                <ButtonImportExcel idClass={idClass} />
                <Button
                  style={{
                    backgroundColor: "rgb(38, 144, 214)",
                    color: "white",
                    marginRight: "0px",
                    marginLeft: "auto",
                  }}
                  onClick={handleSave}
                >
                  <FontAwesomeIcon
                    icon={faFloppyDisk}
                    style={{ marginRight: "7px" }}
                  />
                  Lưu bảng điểm
                </Button>
              </Row>
              <div style={{ margin: "20px 0px 10px 0px" }}>
                <TablePoint />
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default TeacherPointMyClass;
