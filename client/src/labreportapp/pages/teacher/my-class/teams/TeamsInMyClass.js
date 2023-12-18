import { useParams, useNavigate } from "react-router-dom";
import "./styleTeamsInMyClass.css";
import {
  Row,
  Table,
  Button,
  Tooltip,
  Empty,
  Tag,
  message,
  Popconfirm,
} from "antd";
import { Link } from "react-router-dom";
import { ControlOutlined } from "@ant-design/icons";
import { TeacherStudentClassesAPI } from "../../../../api/teacher/student-class/TeacherStudentClasses.api";
import {
  SetStudentClasses,
  GetStudentClasses,
} from "../../../../app/teacher/student-class/studentClassesSlice.reduce";
import { useEffect, useState } from "react";
import { TeacherTeamsAPI } from "../../../../api/teacher/teams-class/TeacherTeams.api";
import {
  SetTeams,
  GetTeams,
  DeleteTeam,
  UpdateTeam,
} from "../../../../app/teacher/teams/teamsSlice.reduce";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import LoadingIndicator from "../../../../helper/loading";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCirclePlus,
  faEye,
  faFolderPlus,
  faMattressPillow,
  faPenToSquare,
  faTrashCan,
  faUpload,
  faUsers,
} from "@fortawesome/free-solid-svg-icons";
import ModalDetailTeam from "./modal-detail/ModalDetailTeam";
import ModalCreateTeam from "./modal-create/ModalCreateTeam";
import ModalUpdateTeam from "./modal-update/ModalUpdateTeam";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { SetTTrueToggle } from "../../../../app/teacher/TeCollapsedSlice.reducer";
import ButtonExportExcelTeam from "./export-excel/ButtonExportExcelTeam";
import ModalFileImportTeam from "./import-excel/ModalFileImportTeam";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../app/common/Loading.reducer";
import { convertDateLongToString } from "../../../../helper/util.helper";

const TeamsInMyClass = () => {
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showDetailModal, setShowDetailModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [loading, setLoading] = useState(false);
  const [objeactTeam, setObjeactTeam] = useState({});
  const [classDetail, setClassDetail] = useState({});
  const [showModalImport, setShowModalImport] = useState(false);
  const [teamDelete, setTeamDelete] = useState({});
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  dispatch(SetTTrueToggle());
  const { idClass } = useParams();
  const [lock, setLock] = useState(1);
  useEffect(() => {
    window.scrollTo(0, 0);
    fetchData(idClass);
    return () => {
      dispatch(SetTeams([]));
    };
  }, []);

  const fetchData = async (idClass) => {
    await featchClass(idClass);
    await featchTeams(idClass);
    await featchStudentClass(idClass);
  };

  const featchTeams = async (id) => {
    setLoading(false);
    try {
      await TeacherTeamsAPI.getTeamsByIdClass(id).then((responese) => {
        dispatch(SetTeams(responese.data.data));
      });
    } catch (error) {}
  };

  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassDetail(responese.data.data);
        document.title = "Quản lý nhóm | " + responese.data.data.code;
        setLock(responese.data.data.statusClass);
      });
    } catch (error) {
      navigate("/teacher/my-class");
    }
  };

  const featchStudentClass = async (id) => {
    setLoading(false);
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(id).then(
        (responese) => {
          if (responese.data.data != null) {
            dispatch(SetStudentClasses(responese.data.data));
          }
          setLoading(true);
        }
      );
    } catch (error) {}
  };

  const handleShowDeleteTeam = (team) => {
    setTeamDelete(team);
  };

  const handleDeleteTeam = async () => {
    try {
      dispatch(SetLoadingTrue());
      await TeacherTeamsAPI.deleteById(teamDelete.id).then((respone) => {
        message.success(respone.data.data);
        dispatch(DeleteTeam(teamDelete));
        if (dataStudentClasses != null) {
          const objFilter = dataStudentClasses.map((item) => {
            if (item.idTeam === teamDelete.id) {
              return { ...item, idTeam: null, role: `1` };
            }
            return item;
          });
          dispatch(SetStudentClasses(objFilter));
        }
        setTeamDelete({});
        dispatch(SetLoadingFalse());
        fetchData(idClass);
      });
    } catch (error) {
      dispatch(SetLoadingFalse());
      message.error("Xóa thất bại !");
    }
  };
  const handleCreateproject = async (idTeam) => {
    try {
      dispatch(SetLoadingTrue());
      let dataUp = {
        idClass: idClass,
        idTeam: idTeam,
      };
      await TeacherTeamsAPI.createProjectToTeam(dataUp).then((response) => {
        dispatch(UpdateTeam(response.data.data));
        dispatch(SetLoadingFalse());
        message.success("Tạo không gian quản lý dự án thành công !");
      });
    } catch (error) {
      dispatch(SetLoadingFalse());
    }
  };
  const handleCancelModalCreateSusscess = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreateModal(false);
    setShowUpdateModal(false);
    setLoading(true);
  };
  const handleCancelModalCreateFaild = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreateModal(false);
    setShowUpdateModal(false);
    setLoading(true);
  };
  const handleCancelCreate = {
    handleCancelModalCreateSusscess,
    handleCancelModalCreateFaild,
  };
  const handleUpdateTeam = (objectTeam) => {
    document.querySelector("body").style.overflowX = "hidden";
    setObjeactTeam(objectTeam);
    setShowUpdateModal(true);
  };
  const handleDetailTeam = (objectTeam) => {
    document.querySelector("body").style.overflowX = "hidden";
    setObjeactTeam(objectTeam);
    setShowDetailModal(true);
  };
  const handleModalDetailCancel = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setObjeactTeam({});
    setShowDetailModal(false);
  };

  const dataStudentClasses = useAppSelector(GetStudentClasses);
  const data = useAppSelector(GetTeams);
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
      align: "center",
    },
    {
      title: "Tên nhóm",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      align: "center",
    },
    {
      title: "Chủ đề",
      dataIndex: "subjectName",
      key: "subjectName",
      sorter: (a, b) => a.subjectName.localeCompare(b.subjectName),
      render: (text, record) => {
        return (
          <>
            {text === "" ? (
              <Tag color="processing">Chưa có chủ đề</Tag>
            ) : (
              <span>{text}</span>
            )}
          </>
        );
      },
    },
    {
      title: "Ngày tạo",
      dataIndex: "createdDate",
      key: "createdDate",
      sorter: (a, b) => a.createdDate - b.createdDate,
      render: (text, record) => {
        return <span>{convertDateLongToString(text)}</span>;
      },
      align: "center",
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      aligh: "center",
      render: (text, record) => (
        <>
          <div>
            {record.idProject != null && (
              <Tooltip title="Xem không gian quản lý dự án">
                <Link
                  to={`/detail-project/${record.idProject}`}
                  style={{ color: "black" }}
                >
                  <FontAwesomeIcon
                    icon={faMattressPillow}
                    className="icon"
                    style={{ width: "19px" }}
                  />
                </Link>
              </Tooltip>
            )}
            {record.idProject == null && lock === 0 && (
              <Tooltip title="Thêm không gian quản lý dự án">
                <span>
                  <FontAwesomeIcon
                    icon={faFolderPlus}
                    className="icon"
                    style={{ width: "19px" }}
                    onClick={() => {
                      handleCreateproject(record.id);
                    }}
                  />
                </span>
              </Tooltip>
            )}
            <Tooltip title="Chi tiết">
              <FontAwesomeIcon
                icon={faEye}
                className="icon"
                onClick={() => {
                  setShowDetailModal(true);
                  handleDetailTeam(record);
                }}
              />
            </Tooltip>
            {lock === 0 && (
              <Tooltip title="Cập nhật">
                <FontAwesomeIcon
                  className="icon"
                  icon={faPenToSquare}
                  onClick={() => {
                    handleUpdateTeam(record);
                  }}
                />
              </Tooltip>
            )}{" "}
            {lock === 0 && (
              <Popconfirm
                placement={"topRight"}
                title="Xóa nhóm"
                description={"Bạn có chắc chắn muốn xóa nhóm này?"}
                okText="Xác nhận"
                cancelText="Hủy"
                onClick={() => {
                  handleShowDeleteTeam(record);
                }}
                onConfirm={() => {
                  handleDeleteTeam();
                }}
                onCancel={() => {
                  setTeamDelete({});
                }}
              >
                <Tooltip title="Xóa nhóm">
                  <FontAwesomeIcon
                    className="icon"
                    icon={faTrashCan}
                    onClick={() => {
                      handleShowDeleteTeam(record);
                    }}
                  />
                </Tooltip>
              </Popconfirm>
            )}
          </div>
        </>
      ),
    },
  ];
  const handleCancelImport = () => {
    setShowModalImport(false);
  };

  return (
    <div className="teacher-team">
      {!loading && <LoadingIndicator />}
      <div className="box-one">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <ControlOutlined style={{ fontSize: "22px" }} />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>{" "}
            <span style={{ color: "gray", fontSize: "14px" }}>
              {" "}
              - Quản lý nhóm
            </span>
          </span>
        </Link>
      </div>
      <div className="box-two-student-in-my-class">
        <div
          className="box-two-student-in-my-class-son"
          style={{ minHeight: "555px" }}
        >
          <div className="button-menu-teacher">
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
                id="menu-checked"
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
                className="custom-link"
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
              <span style={{ fontSize: "17px", fontWeight: 500 }}>
                <FontAwesomeIcon
                  icon={faUsers}
                  style={{ marginRight: "10px", fontSize: "20px" }}
                />
                Danh sách nhóm :
              </span>
            </Row>
            {lock === 0 && (
              <Row style={{ marginTop: "10px" }}>
                <ButtonExportExcelTeam idClass={idClass} />
                <Button
                  className="btn_clear"
                  style={{
                    backgroundColor: "rgb(38, 144, 214)",
                    color: "white",
                    marginLeft: "10px",
                  }}
                  onClick={() => setShowModalImport(true)}
                >
                  <FontAwesomeIcon
                    icon={faUpload}
                    style={{ marginRight: "7px" }}
                  />
                  Import nhóm
                </Button>
                <ModalFileImportTeam
                  idClass={idClass}
                  visible={showModalImport}
                  fetchData={fetchData}
                  onCancel={handleCancelImport}
                />
                <Button
                  className="btn_clear"
                  style={{
                    backgroundColor: "rgb(38, 144, 214)",
                    color: "white",
                    marginRight: "0px",
                    marginLeft: "auto",
                  }}
                  onClick={() => {
                    setShowCreateModal(true);
                  }}
                >
                  <FontAwesomeIcon
                    icon={faCirclePlus}
                    style={{ fontSize: "16px", paddingRight: "7px" }}
                  />
                  Thêm nhóm
                </Button>
              </Row>
            )}
          </div>
          <div style={{ marginTop: "20px" }}>
            {data.length > 0 ? (
              <div className="table-teacher">
                <Table
                  dataSource={data}
                  columns={columns}
                  rowKey="id"
                  pagination={false}
                />
              </div>
            ) : (
              <Empty
                imageStyle={{ height: 60 }}
                description={<span>Không có dữ liệu</span>}
              />
            )}
          </div>
          <ModalDetailTeam
            visible={showDetailModal}
            onCancel={handleModalDetailCancel}
            idClass={idClass}
            team={objeactTeam}
          />
          <ModalCreateTeam
            visible={showCreateModal}
            onCancel={handleCancelCreate}
            idClass={idClass}
          />
          <ModalUpdateTeam
            visible={showUpdateModal}
            onCancel={handleCancelCreate}
            idClass={idClass}
            team={objeactTeam}
          />
        </div>
      </div>
    </div>
  );
};

export default TeamsInMyClass;
