import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-information-class.css";
import {
  faDownload,
  faLineChart,
  faUpload,
} from "@fortawesome/free-solid-svg-icons";
import { ControlOutlined } from "@ant-design/icons";
import { Link, useParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { SetTTrueToggle } from "../../../../app/admin/AdCollapsedSlice.reducer";
import { useEffect, useState } from "react";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import moment from "moment";
import { Button, Empty, Table } from "antd";
import { GetStudentClasses } from "../../../../app/teacher/student-class/studentClassesSlice.reduce";
import LoadingIndicator from "../../../../helper/loading";

const InformationClass = () => {
  const { id } = useParams();
  const dispatch = useAppDispatch();
  const [classDetail, setClassDetail] = useState({});
  const [students, setStudents] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  dispatch(SetTTrueToggle());

  useEffect(() => {
    setIsLoading(true);
    featchClass(id);
    fetchStudent(id);
  }, [id]);

  const featchClass = async (idClass) => {
    try {
      await ClassAPI.getAdClassDetailById(idClass).then((responese) => {
        setClassDetail(responese.data.data);
        document.title = "Thông tin lớp học - " + responese.data.data.code;
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  const fetchStudent = async (idClass) => {
    try {
      await ClassAPI.getAdStudentClassByIdClass(idClass).then((respone) => {
        setStudents(respone.data.data);
        setIsLoading(false);
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
      render: (text, record, index) => index + 1,
      width: "12px",
    },
    {
      title: "Tên tài khoản",
      dataIndex: "username",
      key: "username",
      width: "130px",
    },
    {
      title: "Nhóm",
      dataIndex: "nameTeam",
      key: "nameTeam",
      render: (text, record) => {
        if (text === null) {
          return <span style={{ color: "blue" }}>Chưa vào nhóm</span>;
        } else {
          return <span>{text}</span>;
        }
      },
      width: "150px",
    },
    {
      title: "Họ và tên",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      sorter: (a, b) => a.email.localeCompare(b.email),
    },

    {
      title: "Trạng thái",
      dataIndex: "statusStudent",
      key: "statusStudent",
      render: (text) => {
        if (text === "0") {
          return <span style={{ color: "green" }}>HD</span>;
        } else {
          return <span style={{ color: "red" }}>HL</span>;
        }
      },
      width: "120px",
    },
  ];

  return (
    <div className="box-general-custom">
      {isLoading && <LoadingIndicator />}
      <div className="title-meeting-managemnt-my-class">
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{
              fontSize: "18px",
              marginLeft: "10px",
              fontWeight: "500",
            }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - Thông tin lớp học</span>
        </span>
      </div>
      <div className="box-general" style={{ padding: 25, marginTop: 0 }}>
        <div className="box-son-general" style={{ marginTop: 0 }}>
          <div>
            <Link
              id="menu-checked"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                fontWeight: "bold",
              }}
            >
              THÔNG TIN LỚP HỌC &nbsp;
            </Link>
            <Link
              className="custom-link"
              to={`/admin/class-management/meeting-management/${id}`}
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                fontWeight: "bold",
              }}
            >
              QUẢN LÝ LỊCH HỌC &nbsp;
            </Link>
            <Link
              className="custom-link"
              to={`/admin/class-management/feedback/${id}`}
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                fontWeight: "bold",
              }}
            >
              FEEDBACK CỦA SINH VIÊN &nbsp;
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
              <span style={{ fontSize: "14px", padding: "10px" }}>
                {classDetail.code}
              </span>
            </div>
            <hr />
          </div>
          <div className="info-team">
            <span className="info-heading">Thông tin lớp học:</span>
            <div className="group-info">
              <span
                className="group-info-item"
                style={{ marginTop: "10px", marginBottom: "15px" }}
              >
                Hoạt động: &nbsp;{classDetail.activityName}
              </span>
              <span className="group-info-item">
                Level: &nbsp; {classDetail.activityLevel}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Thời gian bắt đầu: &nbsp;
                {moment(classDetail.startTime).format("DD-MM-YYYY")}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Mã lớp: &nbsp;{classDetail.code}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Ca học: &nbsp; {classDetail.classPeriod + 1}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Số thành viên: &nbsp;{classDetail.classSize}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Mô tả: &nbsp;{classDetail.descriptions}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Mã tham gia: &nbsp;{classDetail.passWord}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Trạng thái: &nbsp;
                {classDetail.statusClass === 0 ? "Mở" : "Đóng"}
              </span>
            </div>
            <div style={{ marginTop: "10px" }}>
              <br />
              <div style={{ marginBottom: "10px" }}>
                <div style={{ float: "left" }}>
                  <span style={{ fontSize: "16px" }}>
                    Danh sách sinh viên trong lớp:
                  </span>
                </div>
                <div style={{ float: "right" }}>
                  <Button
                    style={{
                      color: "white",
                      backgroundColor: "rgb(55, 137, 220)",
                      marginRight: "5px",
                    }}
                  >
                    <FontAwesomeIcon
                      icon={faDownload}
                      size="1x"
                      style={{
                        backgroundColor: "rgb(55, 137, 220)",
                        marginRight: "7px",
                      }}
                    />{" "}
                    Export
                  </Button>
                  <Button
                    style={{
                      color: "white",
                      backgroundColor: "rgb(55, 137, 220)",
                      marginRight: "5px",
                    }}
                  >
                    <FontAwesomeIcon
                      icon={faUpload}
                      size="1x"
                      style={{
                        backgroundColor: "rgb(55, 137, 220)",
                        marginRight: "7px",
                      }}
                    />{" "}
                    Import
                  </Button>
                  <Button
                    style={{
                      color: "white",
                      backgroundColor: "rgb(55, 137, 220)",
                      marginRight: "5px",
                    }}
                  >
                    <FontAwesomeIcon
                      icon={faDownload}
                      size="1x"
                      style={{
                        backgroundColor: "rgb(55, 137, 220)",
                        marginRight: "7px",
                      }}
                    />{" "}
                    Tải mẫu
                  </Button>
                </div>
              </div>
              <br />
              <div style={{ minHeight: "140px", marginTop: "15px" }}>
                {students.length > 0 ? (
                  <>
                    <div className="table">
                      <Table
                        dataSource={students}
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
                      <Empty
                        imageStyle={{ height: 60 }}
                        style={{
                          padding: "20px 0px 20px 0",
                        }}
                        description={<span>Không có sinh viên nào !</span>}
                      />
                    </p>
                  </>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default InformationClass;
