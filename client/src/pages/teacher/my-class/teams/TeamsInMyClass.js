import { useParams } from "react-router-dom";
import "./styleTeamsInMyClass.css";
import { Row, Table, Button, Tooltip, Col, Modal } from "antd";
import { Link } from "react-router-dom";
import { ControlOutlined, UnorderedListOutlined } from "@ant-design/icons";
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
  faPenToSquare,
  faTrashCan,
} from "@fortawesome/free-solid-svg-icons";
import ModalDetailTeam from "./modal-detail/ModalDetailTeam";
import ModalCreateTeam from "./modal-create/ModalCreateTeam";
import ModalUpdateTeam from "./modal-update/ModalUpdateTeam";
import { toast } from "react-toastify";

const TeamsInMyClass = () => {
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showDetailModal, setShowDetailModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [loading, setLoading] = useState(false);
  const [objeactTeam, setObjeactTeam] = useState({});
  const [listStudentClass, setListStudentClass] = useState([]);
  const [loadingStudentClass, setLoadingStudentClass] = useState(false);
  const dispatch = useAppDispatch();
  const { idClass } = useParams();
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - nhóm";
    featchTeams(idClass);
  }, []);
  const fetchData = async (idClass) => {
    await featchStudentClass(idClass);
    featInforStudent();
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

  const featchStudentClass = async (id) => {
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(id).then(
        (responese) => {
          setListStudentClass(responese.data.data);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const featInforStudent = async () => {
    try {
      let request = listStudentClass.map((item) => item.idStudent).join("|");
      const listStudentAPI = await TeacherStudentClassesAPI.getAllInforStudent(
        `?id=` + request
      );
      const listShowTable = listStudentAPI.data
        .filter((item1) =>
          listStudentClass.some((item2) => item1.id === item2.idStudent)
        )
        .map((item1) => {
          const matchedObject = listStudentClass.find(
            (item2) => item2.idStudent === item1.id
          );
          return {
            ...item1,
            ...matchedObject,
          };
        });
      dispatch(SetStudentClasses(listShowTable));
      setLoading(true);
      setLoadingStudentClass(true);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  useEffect(() => {
    if (loadingStudentClass === true) {
      fetchData(idClass);
    }
  }, [loadingStudentClass]);

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
      width: "12px",
    },
    {
      title: "Tên nhóm",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Chủ đề",
      dataIndex: "subjectName",
      key: "subjectName",
      sorter: (a, b) => a.subjectName.localeCompare(b.subjectName),
    },

    {
      title: "Ngày tạo",
      dataIndex: "createdDate",
      key: "createdDate",
      sorter: (a, b) => a.createdDate.localeCompare(b.createdDate),
      render: (text, record) => {
        const startTime = new Date(record.createdDate);
        const formattedStartTime = `${startTime.getDate()}/${
          startTime.getMonth() + 1
        }/${startTime.getFullYear()}`;
        return <span>{formattedStartTime}</span>;
      },
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
      width: "105px",
    },
  ];
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="title-teacher-my-class">
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{ fontSize: "18px", marginLeft: "10px", fontWeight: "500" }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - lớp của tôi</span>
        </span>
      </div>
      <div className="box-students-in-class">
        <div className="button-menu-teacher">
          <div>
            <Link
              to={`/teacher/my-class/students/${idClass}`}
              className="custom-link"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
              }}
            >
              THÀNH VIÊN TRONG LỚP &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/students-in-class/${idClass}`}
              className="custom-link"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              ĐIỂM DANH &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/teams/${idClass}`}
              id="menu-checked"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              QUẢN LÝ NHÓM &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/meeting/${idClass}`}
              className="custom-link"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              BUỔI HỌC &nbsp;
            </Link>
            <hr />
          </div>
        </div>
        <Row gutter={16} style={{ margin: "40px 10px 30px 10px" }}>
          <Col span={22}>
            <div style={{ marginLeft: "0px" }}>
              {" "}
              <span style={{ fontSize: "17px", fontWeight: "500" }}>
                {" "}
                <UnorderedListOutlined
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
    </>
  );
};

export default TeamsInMyClass;
