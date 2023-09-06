import { useParams } from "react-router-dom";
import "./styleTeamsInMyClass.css";
import { Row, Table, Button, Tooltip, Col, Modal } from "antd";
import { Link } from "react-router-dom";
import { UnorderedListOutlined } from "@ant-design/icons";
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
} from "../../../../app/teacher/teams/teamsSlice.reduce";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import LoadingIndicator from "../../../../helper/loading";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faEye,
  faHome,
  faPenToSquare,
  faTrashCan,
  faUsers,
} from "@fortawesome/free-solid-svg-icons";
import ModalDetailTeam from "./modal-detail/ModalDetailTeam";
import ModalCreateTeam from "./modal-create/ModalCreateTeam";
import ModalUpdateTeam from "./modal-update/ModalUpdateTeam";
import { toast } from "react-toastify";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";

const TeamsInMyClass = () => {
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showDetailModal, setShowDetailModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [loading, setLoading] = useState(false);
  const [objeactTeam, setObjeactTeam] = useState({});
  const [classDetail, setClassDetail] = useState({});
  const dispatch = useAppDispatch();
  const { idClass } = useParams();
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - nhóm";
    featchTeams(idClass);
    featchClass(idClass);
  }, []);

  const fetchData = async (idClass) => {
    await featchStudentClass(idClass);
  };
  const featchTeams = async (id) => {
    setLoading(false);
    try {
      await TeacherTeamsAPI.getTeamsByIdClass(id).then((responese) => {
        dispatch(SetTeams(responese.data.data));
        fetchData(idClass);
      });
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
  const featchStudentClass = async (id) => {
    setLoading(false);
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(id).then(
        (responese) => {
          dispatch(SetStudentClasses(responese.data.data));
          setLoading(true);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  const [teamDelete, setTeamDelete] = useState({});

  const handleShowDeleteTeam = (team) => {
    setShowDeleteModal(true);
    setTeamDelete(team);
  };
  const handleDeleteTeam = async () => {
    try {
      await TeacherTeamsAPI.deleteById(teamDelete.id).then((respone) => {
        toast.success(respone.data.data);
        dispatch(DeleteTeam(teamDelete));
        const objFilter = dataStudentClasses.map((item) => {
          if (item.idTeam === teamDelete.id) {
            return { ...item, idTeam: null, codeTeam: null, role: `1` };
          }
          return item;
        });
        dispatch(SetStudentClasses(objFilter));
        setTeamDelete({});
        handleCancelModalCreateSusscess();
      });
    } catch (error) {
      toast.warning("Xóa thất bại !");
      alert("Lỗi hệ thống, vui lòng F5 lại trang  deleete!");
    }
  };
  const handleCancelModalCreateSusscess = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreateModal(false);
    setShowDeleteModal(false);
    setShowUpdateModal(false);
    setLoading(true);
  };
  const handleCancelModalCreateFaild = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreateModal(false);
    setShowUpdateModal(false);
    setShowDeleteModal(false);
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
      width: "5%",
    },
    {
      title: "Tên nhóm",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      width: "30%",
    },
    {
      title: "Chủ đề",
      dataIndex: "subjectName",
      key: "subjectName",
      sorter: (a, b) => a.subjectName.localeCompare(b.subjectName),
      width: "40%",
    },

    {
      title: "Ngày tạo",
      dataIndex: "createdDate",
      key: "createdDate",
      sorter: (a, b) => a.createdDate - b.createdDate,
      render: (text, record) => {
        const startTime = new Date(record.createdDate);
        const formattedStartTime = `${startTime.getDate()}/${
          startTime.getMonth() + 1
        }/${startTime.getFullYear()}`;
        return <span>{formattedStartTime}</span>;
      },
      width: "15%",
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <>
          <div style={{ width: "105px" }}>
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
            <Tooltip title="Cập nhật">
              <FontAwesomeIcon
                className="icon"
                icon={faPenToSquare}
                onClick={() => {
                  handleUpdateTeam(record);
                }}
              />
            </Tooltip>
            <Tooltip title="Xóa nhóm">
              <FontAwesomeIcon
                className="icon"
                icon={faTrashCan}
                onClick={() => {
                  handleShowDeleteTeam(record);
                }}
              />
            </Tooltip>
          </div>
        </>
      ),
      width: "10%",
    },
  ];
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="box-one">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <FontAwesomeIcon
              icon={faHome}
              style={{ color: "#00000", fontSize: "23px" }}
            />
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
          style={{ height: "580px" }}
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
              </Link>{" "}
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
                to=""
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
          <Row style={{ margin: "25px 0px 20px 10px" }}>
            <Col span={22}>
              <div style={{ marginLeft: "0px" }}>
                {" "}
                <span style={{ fontSize: "17px", fontWeight: "500" }}>
                  {" "}
                  <FontAwesomeIcon
                    icon={faUsers}
                    style={{ marginRight: "10px", fontSize: "20px" }}
                  />
                  Danh sách nhóm
                </span>
              </div>
            </Col>
            <Col span={2}>
              <Button
                className="btn_clear"
                style={{
                  color: "white",
                  backgroundColor: "#007bff",
                }}
                onClick={() => {
                  setShowCreateModal(true);
                }}
              >
                Tạo nhóm
              </Button>
            </Col>
          </Row>
          <div>
            {data.length > 0 ? (
              <>
                <div className="table">
                  <Table
                    style={{ marginTop: "150px" }}
                    dataSource={data}
                    rowKey="id"
                    columns={columns}
                    pagination={false}
                  />
                </div>
              </>
            ) : (
              <>
                <p
                  style={{
                    textAlign: "center",
                    marginTop: "100px",
                    fontSize: "15px",
                    color: "red",
                  }}
                >
                  Không có nhóm nào trong lớp
                </p>
              </>
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
          <Modal
            onCancel={handleCancelModalCreateFaild}
            open={showDeleteModal}
            width={750}
            footer={null}
          >
            <>
              <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
                <span style={{ fontSize: "18px" }}>
                  Bạn có muốn xóa nhóm {teamDelete.name} không ?{" "}
                </span>
              </div>
              <div
                style={{
                  textAlign: "right",
                  marginTop: "20px",
                }}
              >
                <Button
                  style={{
                    backgroundColor: "red",
                    color: "white",
                  }}
                  onClick={handleCancelModalCreateFaild}
                >
                  Hủy
                </Button>{" "}
                <Button
                  style={{
                    backgroundColor: "rgb(61, 139, 227)",
                    color: "white",
                  }}
                  onClick={handleDeleteTeam}
                >
                  Xóa
                </Button>
              </div>
            </>
          </Modal>
        </div>
      </div>
    </>
  );
};

export default TeamsInMyClass;
