import { useParams } from "react-router-dom";
import "./styleTeamsInMyClass.css";
import { Row, Table, Button, Tooltip, Col } from "antd";
import { Link } from "react-router-dom";
import {
  ControlOutlined,
  ProjectOutlined,
  UnorderedListOutlined,
} from "@ant-design/icons";
import { TeacherTeamsAPI } from "../../../../api/teacher/teams-class/TeacherTeams.api";
import { useEffect, useState } from "react";
import {
  SetTeams,
  GetTeams,
} from "../../../../app/teacher/teams/teamsSlice.reduce";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import LoadingIndicator from "../../../../helper/loading";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import ModalDetailTeam from "./modal-detail/ModalDetailTeam";
import ModalCreateTeam from "./modal-create/ModalCreateTeam";
import ModalUpdateTeam from "./modal-update/ModalUpdateTeam";

const TeamsInMyClass = () => {
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showDetailModal, setShowDetailModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [loading, setLoading] = useState(false);
  const dispatch = useAppDispatch();
  const { id } = useParams();

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển";
    featchTeams(id);
  }, []);

  const featchTeams = async (id) => {
    setLoading(false);
    try {
      await TeacherTeamsAPI.getTeamsByIdClass(id).then((responese) => {
        dispatch(SetTeams(responese.data.data));
        setLoading(true);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
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

  const handleUpdateTeam = (id) => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowUpdateModal(true);
  };

  const handleDetailTeam = async (id) => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowDetailModal(true);
  };

  const handleModalDetailCancel = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowDetailModal(false);
  };
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
      title: "Mã",
      dataIndex: "code",
      key: "code",
      sorter: (a, b) => a.code.localeCompare(b.code),
      render: (text, record, index) => {
        return <span style={{ color: "#007bff" }}>{text}</span>;
      },
      width: "130px",
    },

    {
      title: "Tên",
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
          <div>
            <Tooltip title="Chi tiết">
              <FontAwesomeIcon
                icon={faEye}
                className="icon"
                style={{ paddingRight: 8 }}
                onClick={() => {
                  handleDetailTeam(record.id);
                }}
              />
            </Tooltip>
            <Tooltip title="Cập nhật">
              <FontAwesomeIcon
                className="icon"
                icon={faPenToSquare}
                onClick={() => {
                  handleUpdateTeam(record.id);
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
              to={`/teacher/my-class/students/${id}`}
              className="custom-link"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
              }}
            >
              THÀNH VIÊN TRONG LỚP &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/students-in-class/${id}`}
              className="custom-link"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              ĐIỂM DANH &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/teams/${id}`}
              id="menu-checked"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              QUẢN LÝ NHÓM &nbsp;
            </Link>
            <hr />
          </div>
        </div>
        <div className="content">
          <Row>
            <Col span={22}>
              <div>
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
        </div>
        <div>
          {data.length > 0 ? (
            <>
              <div className="table">
                <Table
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
        />
        <ModalCreateTeam
          visible={showCreateModal}
          onCancel={handleCancelCreate}
          idClass={id}
        />
        <ModalUpdateTeam
          visible={showUpdateModal}
          onCancel={handleCancelCreate}
          idClass={id}
        />
      </div>
    </>
  );
};

export default TeamsInMyClass;
