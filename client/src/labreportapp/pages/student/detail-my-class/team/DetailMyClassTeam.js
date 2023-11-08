import { useParams } from "react-router-dom";
import "./style-detail-my-class-team.css";
import { Link, useNavigate } from "react-router-dom";
import { ControlOutlined, ProjectOutlined } from "@ant-design/icons";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import { StMyTeamClassAPI } from "../../../../api/student/StTeamClass";
import {
  faCircleInfo,
  faEye,
  faFileDownload,
  faHistory,
  faRightFromBracket,
  faSignOut,
  faTableList,
  faUserPlus,
} from "@fortawesome/free-solid-svg-icons";
import {
  Table,
  Button,
  Tooltip,
  Space,
  Popconfirm,
  Tag,
  Empty,
  message,
} from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { convertMeetingPeriodToNumber } from "../../../../helper/util.helper";
import { convertLongToDate } from "../../../../helper/convertDate";
import ModalDetailTeam from "./modal-detail-team/ModalDetailTeam";
import LoadingIndicatorNoOverlay from "../../../../helper/loadingNoOverlay";
import { SetTTrueToggle } from "../../../../app/student/StCollapsedSlice.reducer";
import { StMyClassAPI } from "../../../../api/student/StMyClassAPI";
import { toast } from "react-toastify";
import { SetStStudentClasses } from "../../../../app/student/StStudentClasses.reducer";

const DetailMyClassTeam = () => {
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const { id } = useParams();
  const [isLoading, setIsLoading] = useState(false);
  const [isLoadingOverlay, setIsLoadingOverlay] = useState(false);
  const [detailClass, setDetailClass] = useState(null);
  const [checkStatus, setCheckStatus] = useState(false);
  const [listStudentMyTeam, setStudentMyTeam] = useState([]);
  const [listTeam, setListTeam] = useState([]);
  const [detailTeam, setDetailTeam] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    setIsLoading(true);
    loadDataDetailClass();
    checkStatusStudentInClass();
    document.title = "Bảng điều khiển - Thông tin lớp học";
  }, []);

  const loadDataDetailClass = () => {
    StMyTeamClassAPI.detailClass(id).then((response) => {
      setDetailClass(response.data.data);
    });
  };

  const checkStatusStudentInClass = async () => {
    await StMyTeamClassAPI.checkStatusStudentInClass(id).then((response) => {
      setCheckStatus(response.data.data != null ? true : false);
      if (response.data.data != null) {
        setDetailTeam(response.data.data);
        StMyTeamClassAPI.getStudentInMyTeam(id, response.data.data.id).then(
          (response) => {
            setStudentMyTeam(response.data.data);
            setIsLoading(false);
          }
        );
      } else {
        StMyTeamClassAPI.getTeamInClass(id).then((response) => {
          setListTeam(response.data.data);
          setIsLoading(false);
        });
      }
    });
  };

  const handleLeaveClass = () => {
    const obj = {
      idClass: id,
    };
    StMyClassAPI.leaveClass(obj)
      .then((response) => {
        message.success("Rời lớp học thành công!");
        navigate(`/student/my-class`);
      })
      .catch((error) => {});
  };
  const [showDetailTeam, setShowDetailTeam] = useState(false);
  const [idSelected, setIdSelected] = useState(null);

  const openModalDetailTeam = (id) => {
    setShowDetailTeam(true);
    setIdSelected(id);
  };

  const handleDetailTeamCancel = () => {
    setShowDetailTeam(false);
    setIdSelected(null);
  };

  const joinTeam = (idTeam) => {
    setIsLoadingOverlay(true);
    let obj = {
      idClass: id,
      idTeam: idTeam,
    };

    StMyTeamClassAPI.joinTeam(obj).then((response) => {
      checkStatusStudentInClass();
      setIsLoadingOverlay(false);
    });
  };

  const outTeam = () => {
    setIsLoadingOverlay(true);

    let obj = {
      idClass: id,
    };

    StMyTeamClassAPI.outTeam(obj).then((response) => {
      checkStatusStudentInClass();
      setIsLoadingOverlay(false);
    });
  };

  const loadDataStudentClasses = () => {
    StMyClassAPI.getAllStudentClasses(id).then((response) => {
      dispatch(SetStStudentClasses(response.data.data));
    });
  };

  useEffect(() => {
    loadDataStudentClasses();
  }, []);
  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => <>{index + 1}</>,
    },
    {
      title: "Họ và tên",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Vai trò",
      dataIndex: "role",
      key: "role",
      render: (text, record, index) => (
        <>{record.role === 0 ? "Trưởng nhóm" : "Thành viên"}</>
      ),
    },
  ];

  const columnsTeam = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => <>{index + 1}</>,
    },
    {
      title: "Tên nhóm",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Tên đề tài",
      dataIndex: "subjectName",
      key: "subjectName",
      render: (text, record, index) => {
        if (record.subjectName === "" || record.subjectName == null) {
          return <Tag color="processing">Chưa có chủ đề</Tag>;
        }
        return <span>{record.subjectName}</span>;
      },
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record, index) => (
        <Space>
          <Tooltip title="Xem chi tiết nhóm">
            <FontAwesomeIcon
              className="icon"
              icon={faEye}
              style={{ marginRight: "10px", cursor: "pointer" }}
              onClick={() => {
                openModalDetailTeam(record.id);
              }}
            />
          </Tooltip>
          <Popconfirm
            title="Tham gia nhóm"
            description="Bạn có chắc chắn muốn tham gia nhóm này?"
            onConfirm={() => {
              joinTeam(record.id);
            }}
            okText="Có"
            cancelText="Không"
          >
            <Tooltip title="Tham gia nhóm">
              <FontAwesomeIcon
                className="icon"
                style={{ marginRight: "10px", cursor: "pointer" }}
                icon={faUserPlus}
              />{" "}
            </Tooltip>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <div style={{ paddingTop: "35px" }}>
      {isLoading && <LoadingIndicator />}
      {isLoadingOverlay && <LoadingIndicatorNoOverlay />}
      <div className="title-student-my-class">
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{ fontSize: "18px", marginLeft: "10px", fontWeight: "500" }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - Thông tin lớp học</span>
        </span>
      </div>
      <div className="box-students-detail-my-class" style={{ padding: "20px" }}>
        <div className="button-menu-student-detail-my-class">
          <div>
            <Link
              to={`/student/my-class/post/${id}`}
              className="custom-link"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              BÀI ĐĂNG
            </Link>
            <Link
              id="menu-checked"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              THÔNG TIN LỚP HỌC
            </Link>
            <Link
              className="custom-link"
              to={`/student/my-class/meeting/${id}`}
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              DANH SÁCH BUỔI HỌC
            </Link>
            <Link
              className="custom-link"
              to={`/student/my-class/attendance/${id}`}
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              ĐIỂM DANH
            </Link>
            <Link
              className="custom-link"
              to={`/student/my-class/point/${id}`}
              style={{
                fontSize: "16px",
                fontWeight: "bold",
                paddingLeft: "10px",
                paddingRight: "10px",
              }}
            >
              ĐIỂM
            </Link>
            <hr />
            <div
              style={{
                marginTop: 17,
                display: "flex",
                alignItems: "center",
                justifyContent: "space-between",
              }}
            >
              {" "}
              <div style={{ float: "left" }}>
                <span style={{ fontSize: "17px", fontWeight: 500 }}>
                  <FontAwesomeIcon
                    icon={faCircleInfo}
                    style={{
                      marginRight: "10px",
                      fontSize: "20px",
                    }}
                  />
                  Thông tin lớp học :
                </span>
              </div>
              <div style={{ float: "right" }}>
                <Button
                  style={{
                    color: "white",
                    backgroundColor: "rgb(55, 137, 220)",
                  }}
                >
                  <FontAwesomeIcon
                    icon={faHistory}
                    size="1x"
                    style={{
                      backgroundColor: "rgb(55, 137, 220)",
                      marginRight: "5px",
                    }}
                  />
                  Lịch sử
                </Button>
              </div>
            </div>
            <div className="info-team">
              <div className="group-info">
                <span
                  className="group-info-item"
                  style={{ marginTop: "10px", marginBottom: "15px" }}
                >
                  Mã lớp: {detailClass != null ? detailClass.code : ""}
                </span>

                <span
                  className="group-info-item"
                  style={{ marginTop: "13px", marginBottom: "15px" }}
                >
                  Thời gian bắt đầu:{" "}
                  {detailClass != null
                    ? convertLongToDate(detailClass.startTime)
                    : ""}
                </span>
                <span
                  className="group-info-item"
                  style={{ marginTop: "13px", marginBottom: "15px" }}
                >
                  Ca học: {detailClass != null ? detailClass.classPeriod : ""}
                </span>
                <span
                  className="group-info-item"
                  style={{ marginTop: "13px", marginBottom: "15px" }}
                >
                  Mô tả:{" "}
                  {detailClass != null ? detailClass.descriptions : "Không có"}
                </span>
                <span
                  className="group-info-item"
                  style={{ marginTop: "13px", marginBottom: "15px" }}
                >
                  Giảng viên:{" "}
                  {detailClass != null
                    ? detailClass.nameTeacher +
                      " - " +
                      detailClass.usernameTeacher
                    : ""}
                </span>
              </div>
            </div>
            {checkStatus && (
              <div>
                <span style={{ fontSize: "17px", fontWeight: 500 }}>
                  <FontAwesomeIcon
                    icon={faCircleInfo}
                    style={{
                      marginRight: "10px",
                      fontSize: "20px",
                    }}
                  />
                  Thông tin nhóm:
                </span>
                <div className="info-team">
                  <div className="group-info">
                    <span className="group-info-item">
                      Tên nhóm: {detailTeam != null ? detailTeam.name : ""}{" "}
                    </span>
                    <span className="group-info-item">
                      Tên đề tài:{" "}
                      {detailTeam != null
                        ? detailTeam.subjectName === ""
                          ? "Chưa có chủ đề"
                          : detailTeam.subjectName
                        : "Chưa có chủ đề"}
                    </span>
                  </div>
                </div>
                <>
                  <div
                    style={{
                      display: "flex",
                      alignItems: "center",
                      justifyContent: "space-between",
                      marginBottom: 5,
                    }}
                  >
                    <div
                      className="table-member-team"
                      style={{ marginBottom: "15px" }}
                    >
                      <span style={{ fontSize: "17px", fontWeight: 500 }}>
                        <FontAwesomeIcon
                          icon={faTableList}
                          style={{
                            marginRight: "10px",
                            fontSize: "20px",
                          }}
                        />
                        Danh sách thành viên trong nhóm:
                      </span>
                    </div>
                    <div>
                      {detailTeam != null && detailTeam.projectId != null && (
                        <Link to={`/detail-project/${detailTeam.projectId}`}>
                          <Button
                            style={{
                              backgroundColor: "rgb(38, 144, 214)",
                              color: "white",
                            }}
                          >
                            <ProjectOutlined />
                            Chi tiết dự án của nhóm
                          </Button>
                        </Link>
                      )}
                    </div>
                  </div>
                  {listStudentMyTeam.length > 0 ? (
                    <Table
                      columns={columns}
                      dataSource={listStudentMyTeam}
                      pagination={false}
                      rowKey="id"
                    />
                  ) : (
                    <Empty
                      imageStyle={{ height: 60 }}
                      description={<span>Không có dữ liệu</span>}
                    />
                  )}

                  <div style={{ marginTop: "8px", paddingBottom: "40px" }}>
                    <Popconfirm
                      title="Rời nhóm"
                      description="Bạn có chắc chắn muốn rời nhóm này?"
                      onConfirm={() => {
                        outTeam();
                      }}
                      okText="Có"
                      cancelText="Không"
                      placement="topLeft"
                    >
                      <Button
                        style={{ backgroundColor: "#E2B357" }}
                        className="btnRoiNhom"
                      >
                        <FontAwesomeIcon
                          icon={faRightFromBracket}
                          style={{ paddingRight: "8px" }}
                        />
                        Rời nhóm
                      </Button>
                    </Popconfirm>
                  </div>
                </>
              </div>
            )}
            {!checkStatus && (
              <div>
                <>
                  <div
                    className="table-member-team"
                    style={{ marginBottom: "7px" }}
                  >
                    <span style={{ fontSize: "17px", fontWeight: 500 }}>
                      <FontAwesomeIcon
                        icon={faTableList}
                        style={{
                          marginRight: "10px",
                          fontSize: "20px",
                        }}
                      />
                      Danh sách nhóm:
                    </span>
                  </div>
                  {listTeam.length > 0 ? (
                    <Table
                      columns={columnsTeam}
                      dataSource={listTeam}
                      pagination={false}
                      rowKey="id"
                    />
                  ) : (
                    <Empty
                      imageStyle={{ height: 60 }}
                      description={<span>Không có dữ liệu</span>}
                    />
                  )}

                  <div
                    className="button-leave"
                    style={{ marginTop: "11px", textAlign: "right" }}
                  >
                    <Popconfirm
                      placement="topRight"
                      description="Bạn có chắc chắn muốn rời lớp học này chứ?"
                      okText="Có"
                      cancelText="Không"
                      onConfirm={() => handleLeaveClass()}
                    >
                      <Button
                        style={{
                          backgroundColor: "rgb(231, 68, 68)",
                          color: "white",
                        }}
                      >
                        <FontAwesomeIcon
                          icon={faSignOut}
                          style={{ marginRight: "7px" }}
                        />{" "}
                        Rời khỏi lớp học
                      </Button>
                    </Popconfirm>
                  </div>
                </>
              </div>
            )}
          </div>
        </div>
      </div>
      <ModalDetailTeam
        id={idSelected}
        visible={showDetailTeam}
        onCancel={handleDetailTeamCancel}
        idClass={id}
      />
    </div>
  );
};

export default DetailMyClassTeam;
