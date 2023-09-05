import { useParams } from "react-router-dom";
import "./style-detail-my-class-team.css";
import { Link } from "react-router-dom";
import { ControlOutlined } from "@ant-design/icons";
import { useAppDispatch } from "../../../../app/hook";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import { StMyTeamClassAPI } from "../../../../api/student/StTeamClass";
import { faEye, faUserPlus } from "@fortawesome/free-solid-svg-icons";
import { Table, Button, Tooltip, Space } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { sinhVienCurrent } from "../../../../helper/inForUser";
import {
  convertMeetingPeriod,
  convertMeetingPeriodToNumber,
} from "../../../../helper/util.helper";
import { convertLongToDate } from "../../../../helper/convertDate";
import { EyeOutlined, UserAddOutlined } from "@ant-design/icons";

const DetailMyClassTeam = () => {
  const dispatch = useAppDispatch();
  const { id } = useParams();
  const [isLoading, setIsLoading] = useState(false);
  const [detailClass, setDetailClass] = useState(null);
  const [checkStatus, setCheckStatus] = useState(false);
  const [listStudentMyTeam, setStudentMyTeam] = useState([]);
  const [listTeam, setListTeam] = useState([]);
  const [detailTeam, setDetailTeam] = useState(null);

  useEffect(() => {
    setIsLoading(true);
    loadDataDetailClass();
    checkStatusStudentInClass();
  }, []);

  const loadDataDetailClass = () => {
    StMyTeamClassAPI.detailClass(id).then((response) => {
      setDetailClass(response.data.data);
      setIsLoading(false);
    });
  };

  const checkStatusStudentInClass = () => {
    StMyTeamClassAPI.checkStatusStudentInClass(id).then((response) => {
      setCheckStatus(response.data.data != null ? true : false);
      if (response.data.data != null) {
        setDetailTeam(response.data.data);
        StMyTeamClassAPI.getStudentInMyTeam(id, response.data.data.id).then(
          (response) => {
            setStudentMyTeam(response.data.data);
          }
        );
      } else {
        StMyTeamClassAPI.getTeamInClass(id).then((response) => {
          setListTeam(response.data.data);
        });
      }
    });
  };

  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => <>{index + 1}</>,
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
      title: "Mã nhóm",
      dataIndex: "code",
      key: "code",
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
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record, index) => (
        <Space>
          <FontAwesomeIcon icon={faEye} style={{ marginRight: "10px" }} />
          <FontAwesomeIcon icon={faUserPlus} />
        </Space>
      ),
    },
  ];

  return (
    <div style={{ paddingTop: "35px" }}>
      {isLoading && <LoadingIndicator />}
      <div className="title-student-my-class">
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{ fontSize: "18px", marginLeft: "10px", fontWeight: "500" }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - Lớp của tôi</span>
        </span>
      </div>
      <div className="box-students-detail-my-class" style={{ padding: "20px" }}>
        <div className="button-menu-student-detail-my-class">
          <div>
            <Link
              to={`/student/my-class/team/${id}`}
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
            <div className="info-team">
              <span className="info-heading">Thông tin lớp học:</span>
              <div className="group-info">
                <span
                  className="group-info-item"
                  style={{ marginTop: "10px", marginBottom: "15px" }}
                >
                  Mã lớp: {detailClass != null ? detailClass.code : ""}
                </span>
                <span className="group-info-item">
                  Tên lớp: {detailClass != null ? detailClass.name : ""}
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
                  Ca học:{" "}
                  {detailClass != null
                    ? "Ca " +
                      parseInt(
                        convertMeetingPeriodToNumber(detailClass.classPeriod) +
                          1
                      )
                    : ""}
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
                <div className="info-team">
                  <span className="info-heading">Thông tin nhóm:</span>
                  <div className="group-info">
                    <span
                      className="group-info-item"
                      style={{ marginTop: "10px", marginBottom: "15px" }}
                    >
                      Mã nhóm: {detailTeam != null ? detailTeam.code : ""}
                    </span>
                    <span className="group-info-item">
                      Tên nhóm: {detailTeam != null ? detailTeam.name : ""}{" "}
                    </span>
                    <span
                      className="group-info-item"
                      style={{ marginTop: "13px", marginBottom: "15px" }}
                    >
                      Tên đề tài:{" "}
                      {detailTeam != null ? detailTeam.subjectName : ""}
                    </span>
                  </div>
                </div>

                <>
                  <div
                    className="table-member-team"
                    style={{ marginBottom: "7px" }}
                  >
                    <span className="info-heading" style={{ fontSize: "16px" }}>
                      Danh sách thành viên trong nhóm:
                    </span>
                  </div>
                  <Table
                    columns={columns}
                    dataSource={listStudentMyTeam}
                    pagination={false}
                    rowKey="id"
                  />
                  <div style={{ marginTop: "8px", paddingBottom: "40px" }}>
                    <Button className="btnRoiNhom">Rời nhóm</Button>
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
                    <span className="info-heading" style={{ fontSize: "16px" }}>
                      Danh sách nhóm trong lớp:
                    </span>
                  </div>
                  <Table
                    columns={columnsTeam}
                    dataSource={listTeam}
                    pagination={false}
                    rowKey="id"
                  />
                </>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default DetailMyClassTeam;
