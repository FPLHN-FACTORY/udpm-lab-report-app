import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-poin-my-class.css";
import {
  faFloppyDisk,
  faHouseChimney,
  faMarker,
  faUpload,
} from "@fortawesome/free-solid-svg-icons";
import { ControlOutlined } from "@ant-design/icons";
import { Link, useNavigate, useParams } from "react-router-dom";
import { Button, Row, message } from "antd";
import { useEffect, useState } from "react";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherPointAPI } from "../../../../api/teacher/point/TeacherPoint.api";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import {
  GetPoint,
  SetPoint,
  UpdatePoint,
} from "../../../../app/teacher/point/tePointSlice.reduce";
import TablePoint from "./table-point-student/TablePoint";
import LoadingIndicator from "../../../../helper/loading";
import ButtonExportExcel from "./export-excel/ButtonExportExcel";
import ModalFileImportPoint from "./import-excel/ModalFileImportPoint";
import { SetTTrueToggle } from "../../../../app/teacher/TeCollapsedSlice.reducer";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../app/common/Loading.reducer";
import ModalAddHoney from "./modal-add-honey/ModalAddHoney";

const TeacherPointMyClass = () => {
  const { idClass } = useParams();
  const dispatch = useAppDispatch();
  const [classDetail, setClassDetail] = useState({});
  const [checkPoint, setCheckPoint] = useState(false);
  const [listPoint, setListPoint] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showModalImport, setShowModalImport] = useState(false);
  const navigate = useNavigate();

  dispatch(SetTTrueToggle());
  useEffect(() => {
    window.scrollTo(0, 0);
    fetchData(idClass);
    getAllCategory();
    return () => {
      dispatch(SetPoint([]));
    };
  }, []);

  const fetchData = async (idClass) => {
    await Promise.all([await featchClass(idClass), await featchPoint(idClass)]);
  };

  const [listCategory, setListCategory] = useState([]);

  const getAllCategory = () => {
    TeacherPointAPI.getAllCategory().then((response) => {
      setListCategory(response.data.data);
    });
  };

  const featchPoint = async (idClass) => {
    try {
      await TeacherPointAPI.getPointByIdClass(idClass).then((response) => {
        setListPoint(response.data.data);
        dispatch(SetPoint(response.data.data));
        setLoading(true);
      });
    } catch (error) {}
  };

  const featchClass = async (idClass) => {
    try {
      setLoading(false);
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassDetail(responese.data.data);
        document.title = "Quản lý điểm | " + responese.data.data.code;
      });
    } catch (error) {
      setTimeout(() => {
        navigate("/teacher/my-class");
      }, [1000]);
    }
  };

  const handleSave = async () => {
    try {
      dispatch(SetLoadingTrue());
      const dataToSave = data;
      let dataFind = {
        listPoint: dataToSave,
        idClass: idClass,
      };
      await TeacherPointAPI.createOrUpdate(dataFind).then((respone) => {
        dispatch(SetLoadingFalse());
        dispatch(UpdatePoint(respone.data.data));
        message.success("Lưu bảng điểm thành công !");
      });
    } catch (error) {
      dispatch(SetLoadingFalse());
    }
  };

  const handleCancelImport = () => {
    setShowModalImport(false);
  };

  const [visibleAddHoney, setVisibleAddHoney] = useState(false);

  const openModalAddHoney = () => {
    setVisibleAddHoney(true);
  };

  const cancelAddHoney = () => {
    setVisibleAddHoney(false);
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
                    backgroundColor: "rgb(38, 144, 214)",
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
                    marginRight: "0px",
                  }}
                >
                  <FontAwesomeIcon icon={faMarker} /> Điểm sinh viên :
                </span>
              </Row>
              <Row style={{ marginTop: "10px" }}>
                <ButtonExportExcel idClass={idClass} />
                <Button
                  className="btn_clear"
                  style={{
                    backgroundColor: "rgb(38, 144, 214)",
                    color: "white",
                    marginLeft: "5px",
                  }}
                  onClick={() => setShowModalImport(true)}
                >
                  <FontAwesomeIcon
                    icon={faUpload}
                    style={{ marginRight: "7px" }}
                  />
                  Import bảng điểm
                </Button>
                <Button
                  style={{
                    backgroundColor: "rgb(38, 144, 214)",
                    color: "white",
                    marginLeft: "5px",
                  }}
                  onClick={openModalAddHoney}
                >
                  <FontAwesomeIcon
                    icon={faHouseChimney}
                    style={{ marginRight: "7px" }}
                  />
                  Quy đổi mật ong
                </Button>
                <ModalFileImportPoint
                  idClass={idClass}
                  visible={showModalImport}
                  fetchData={fetchData}
                  onCancel={handleCancelImport}
                />
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
      <ModalAddHoney
        visible={visibleAddHoney}
        onCancel={cancelAddHoney}
        listCategory={listCategory}
      />
    </>
  );
};

export default TeacherPointMyClass;
