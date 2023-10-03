import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-information-class.css";
import {
  faDownload,
  faLineChart,
  faUpload,
} from "@fortawesome/free-solid-svg-icons";
import { ControlOutlined, SearchOutlined } from "@ant-design/icons";
import { Link, useParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { SetTTrueToggle } from "../../../../app/admin/AdCollapsedSlice.reducer";
import { useEffect, useState } from "react";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import moment from "moment";
import { Button, Empty, Input, Spin, Table } from "antd";
import { GetStudentClasses } from "../../../../app/teacher/student-class/studentClassesSlice.reduce";
import LoadingIndicator from "../../../../helper/loading";
import LoadingIndicatorNoOverlay from "../../../../helper/loadingNoOverlay";
import { toast } from "react-toastify";

const InformationClass = () => {
  const { id } = useParams();
  const dispatch = useAppDispatch();
  const [classDetail, setClassDetail] = useState({});
  const [students, setStudents] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const [loadingOverLay, setLoadingOverLay] = useState(false);
  const [downloading, setDownloading] = useState(false);
  const [inputFile, setInputFile] = useState("");
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
      console.log(error);
    }
  };

  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const format = `${date.getHours()}_${date.getMinutes()}_${date.getSeconds()}`;
    return format;
  };

  const downloadSample = () => {
    setLoadingOverLay(true);
    ClassAPI.exportExcelStudentsInClass(id, true).then((response) => {
      setLoadingOverLay(false);
      const blob = new Blob([response.data], {
        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
      });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download =
        "Danh sách sinh viên trong lớp " +
        classDetail.code +
        "_" +
        convertLongToDate(new Date().getTime()) +
        ".xlsx";
      link.click();
      window.URL.revokeObjectURL(url);
    });
  };

  const exportExcel = () => {
    setLoadingOverLay(true);
    ClassAPI.exportExcelStudentsInClass(id, false).then((response) => {
      setLoadingOverLay(false);
      const blob = new Blob([response.data], {
        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
      });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download =
        "Danh sách sinh viên trong lớp " +
        classDetail.code +
        "_" +
        convertLongToDate(new Date().getTime()) +
        ".xlsx";
      link.click();
      window.URL.revokeObjectURL(url);
    });
  };

  const importExcel = async (e) => {
    try {
      const selectedFile = e.target.files[0];
      if (selectedFile) {
        const formData = new FormData();
        formData.append("multipartFile", selectedFile);
        await ClassAPI.importExcelStudentsInClass(formData, id)
          .then((response) => {
            setTimeout(() => {
              if (response.data.data.status === true) {
                setInputFile("");
                toast.success("Đang import, vui lòng chờ giây lát !", {
                  position: toast.POSITION.TOP_CENTER,
                });
              } else {
                toast.error(
                  "Import thất bại, " +
                    response.data.data.message +
                    ", vui lòng chờ !",
                  {
                    position: toast.POSITION.TOP_CENTER,
                  }
                );
              }
            }, 200);
            setDownloading(true);
            setTimeout(() => {
              window.open(
                `http://localhost:3000/admin/class-management/information-class/` +
                  id,
                "_self"
              );
              setDownloading(false);
            }, 2000);
          })
          .catch((error) => {
            console.log(error);
          });
      }
    } catch (error) {
      console.log(error);
    }
  };

  const fetchStudent = async (idClass) => {
    try {
      await ClassAPI.getAdStudentClassByIdClass(idClass).then((respone) => {
        setStudents(respone.data.data);
        setIsLoading(false);
      });
    } catch (error) {
      console.log(error);
    }
  };
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
      sorter: (a, b) => a.stt - b.stt,
      width: "12px",
    },
    {
      title: "Tên tài khoản",
      dataIndex: "username",
      key: "username",
      sorter: (a, b) => a.username.localeCompare(b.username),
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            autoFocus={true}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) => {
        if (record.username === null) {
          return false;
        }
        return record.username.toLowerCase().includes(value.toLowerCase());
      },
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
      sorter: (a, b) => {
        if (a.nameTeam == null && b.nameTeam == null) {
          return 0;
        }
        if (a.nameTeam == null) {
          return -1;
        }
        if (b.nameTeam == null) {
          return 1;
        }
        return a.nameTeam.localeCompare(b.nameTeam);
      },
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            autoFocus={true}
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) => {
        if (record.nameTeam === null) {
          return false;
        }
        return record.nameTeam.toLowerCase().includes(value.toLowerCase());
      },
    },
    {
      title: "Họ và tên",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            autoFocus={true}
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) => {
        if (record.name === null) {
          return false;
        }
        return record.name.toLowerCase().includes(value.toLowerCase());
      },
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      sorter: (a, b) => a.email.localeCompare(b.email),
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            autoFocus={true}
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) => {
        if (record.email === null) {
          return false;
        }
        return record.email.toLowerCase().includes(value.toLowerCase());
      },
    },

    {
      title: "Trạng thái",
      dataIndex: "statusStudent",
      key: "statusStudent",
      render: (text) => {
        if (text === "0") {
          return <span style={{ color: "green" }}>Đạt</span>;
        } else {
          return <span style={{ color: "red" }}>Trượt</span>;
        }
      },
      sorter: (a, b) => a.statusStudent.localeCompare(b.statusStudent),
      width: "120px",
    },
  ];

  return (
    <div className="box-general-custom">
      {isLoading && <LoadingIndicator />}
      {loadingOverLay && <LoadingIndicatorNoOverlay />}
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
                <div style={{ float: "right", display: "flex" }}>
                  <Button
                    onClick={exportExcel}
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
                  <Spin spinning={downloading}>
                    <Button
                      className="btn_clear"
                      style={{
                        backgroundColor: "rgb(38, 144, 214)",
                        color: "white",
                      }}
                      onClick={() => {
                        document.getElementById("fileInput").click();
                      }}
                    >
                      <FontAwesomeIcon
                        icon={faUpload}
                        style={{ marginRight: "7px" }}
                      />
                      {downloading ? (
                        "Đang tải lên..."
                      ) : (
                        <>
                          Import
                          <input
                            id="fileInput"
                            type="file"
                            accept=".xlsx"
                            onChange={importExcel}
                            style={{
                              display: "none",
                            }}
                          />
                        </>
                      )}
                    </Button>
                  </Spin>
                  <Button
                    onClick={downloadSample}
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
