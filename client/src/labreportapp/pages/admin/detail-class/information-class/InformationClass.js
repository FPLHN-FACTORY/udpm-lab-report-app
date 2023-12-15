import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-information-class.css";
import {
  faCircleInfo,
  faDownload,
  faEye,
  faFileDownload,
  faHistory,
  faRightFromBracket,
  faTableList,
  faUpload,
} from "@fortawesome/free-solid-svg-icons";
import { ControlOutlined, SearchOutlined } from "@ant-design/icons";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useAppDispatch } from "../../../../app/hook";
import { SetTTrueToggle } from "../../../../app/admin/AdCollapsedSlice.reducer";
import { useEffect, useState } from "react";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import moment from "moment";
import {
  Button,
  Empty,
  Input,
  Popconfirm,
  Spin,
  Table,
  Tag,
  message,
} from "antd";
import LoadingIndicator from "../../../../helper/loading";
import LoadingIndicatorNoOverlay from "../../../../helper/loadingNoOverlay";
import ModalSentStudentAdmin from "./ModalSentStudentAdmin";
import { convertHourAndMinuteToString } from "../../../../helper/util.helper";
import ModalShowHistory from "./ModalShowHistory";

const InformationClass = () => {
  const { id } = useParams();
  const dispatch = useAppDispatch();
  const [classDetail, setClassDetail] = useState({});
  const [students, setStudents] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const [loadingOverLay, setLoadingOverLay] = useState(false);
  const [downloading, setDownloading] = useState(false);
  const [inputFile, setInputFile] = useState("");
  const [selectedRowKeys, setSelectedRowKeys] = useState([]);
  const [selected, setSelected] = useState([]);
  const [modalClass, setModalClass] = useState(false);
  const [isMoveStudent, setIsMoveStudent] = useState(false);
  const [isKickStudent, setIsKickStudent] = useState(false);
  dispatch(SetTTrueToggle());
  const navigate = useNavigate();
  useEffect(() => {
    setIsLoading(true);
    featchClass(id);
  }, [id]);

  useEffect(() => {
    fetchStudent(id);
    featchClass(id);
  }, [selected]);

  const onSelectChange = (newSelectedRowKeys) => {
    setSelectedRowKeys(newSelectedRowKeys);
  };

  const handleShowStudentsCouldKick = () => {
    if (students.length === 0) {
      message.warning("Hiện tại lớp học chưa có sinh viên !");
    } else if (!isKickStudent) {
      setIsKickStudent(true);
      setIsMoveStudent(false);
      message.success(
        "Đã chuyển sang chế độ xóa sinh viên khỏi lớp, hãy chọn sinh viên cần xóa !"
      );
    }
  };
  const handleOpenModalClass = () => {
    if (students.length === 0) {
      message.warning("Hiện tại lớp học chưa có sinh viên !");
    } else if (!isMoveStudent) {
      setIsMoveStudent(true);
      setIsKickStudent(false);
      message.success(
        "Đã chuyển sang chế độ di chuyển sinh viên, hãy chọn sinh viên cần chuyển lớp !"
      );
    } else if (isMoveStudent) {
      if (selectedRowKeys.length === 0) {
        message.warning("Vui lòng chọn sinh viên cần xóa khỏi lớp !");
      } else {
        setModalClass(true);
      }
    }
  };

  useEffect(() => {
    setSelectedRowKeys([]);
  }, [isMoveStudent, isKickStudent]);

  let rowSelection = null;
  if (students.length > 0) {
    if (isKickStudent) {
      rowSelection = {
        renderCell: (checked, record, index, originNode) => {
          if (record.idFeedBack != null || record.idAttendance != null) {
            return null;
          }
          return originNode;
        },
        selectedRowKeys,
        columnTitle: null,
        onChange: onSelectChange,
        getCheckboxProps: (record) => ({
          disabled: record.idFeedBack != null || record.idAttendance != null,
        }),
      };
    } else if (isMoveStudent) {
      rowSelection = {
        renderCell: (checked, record, index, originNode) => {
          if (record.nameTeam != null) {
            return null;
          }
          return originNode;
        },
        selectedRowKeys,
        columnTitle: null,
        onChange: onSelectChange,
        getCheckboxProps: (record) => ({
          disabled: record.nameTeam != null,
        }),
      };
    } else {
      rowSelection = null;
    }
  }

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

  const importExcel = (e) => {
    setIsLoading(true);
    try {
      const selectedFile = e.target.files[0];
      if (selectedFile) {
        const formData = new FormData();
        formData.append("multipartFile", selectedFile);
        ClassAPI.importExcelStudentsInClass(formData, id).then(
          (response) => {
            if (response.data.data.status === true) {
              setInputFile("");
              message.success("Đang import, vui lòng chờ giây lát !");
            } else {
              message.error("Import thất bại, " + response.data.data.message);
            }
            setDownloading(true);
            fetchStudent(id);
            setDownloading(false);
            setIsLoading(false);
          },
          (error) => {}
        );
      }
    } catch (error) {
      setDownloading(false);
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
      title: "Nhóm",
      dataIndex: "nameTeam",
      key: "nameTeam",
      render: (text, record) => {
        if (text === null || text === "") {
          return <Tag color="processing">Chưa vào nhóm</Tag>;
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

  const getSelectedRowKeysNotKickOrMove = (selected, selectedRowKeys) => {
    setSelected(selected);
    setSelectedRowKeys(selectedRowKeys);
  };

  const getSelectedRowKeys = (
    selected,
    selectedRowKeys,
    isMoveStudent,
    isKickStudent
  ) => {
    setSelected(selected);
    setSelectedRowKeys(selectedRowKeys);
    setIsKickStudent(isKickStudent);
    setIsMoveStudent(isMoveStudent);
  };

  const handleKickStudent = async () => {
    if (selectedRowKeys.length === 0) {
      message.warning("Vui lòng chọn sinh viên cần chuyển lớp !");
    } else {
      try {
        let data = {
          listIdStudent: selectedRowKeys,
          idClassOld: classDetail.id,
        };
        await ClassAPI.kickStudentClassesToClass(data).then((response) => {
          if (response.data.data) {
            message.success("Xóa sinh viên thành công !");
            setSelected([]);
          } else {
            message.error("Xóa sinh viên thất bại !");
          }
        });
      } catch (error) {
        console.log(error);
      }
    }
  };

  const [visibleHistory, setVisibleHistory] = useState(false);

  const openModalShowHistory = () => {
    setVisibleHistory(true);
  };

  const cancelModalHistory = () => {
    setVisibleHistory(false);
  };

  const dowloadLogAll = () => {
    setIsLoading(true);
    ClassAPI.dowloadLogAll(id).then(
      (response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const a = document.createElement("a");
        a.href = url;
        a.download = "luong_lop_hoc.csv";
        a.click();
        window.URL.revokeObjectURL(url);
        setIsLoading(false);
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return (
    <div className="box-general-custom">
      {isLoading && <LoadingIndicator />}
      {loadingOverLay && <LoadingIndicatorNoOverlay />}
      <ModalSentStudentAdmin
        visible={modalClass}
        onCancel={() => setModalClass(false)}
        listIdStudent={selectedRowKeys}
        classDetail={classDetail}
        getSelectedRowKeys={getSelectedRowKeys}
        getSelectedRowKeysNotKickOrMove={getSelectedRowKeysNotKickOrMove}
        id={id}
      />
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
            <Link
              to={`/admin/class-management/point/${id}`}
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
                  marginRight: 5,
                }}
                onClick={dowloadLogAll}
              >
                <FontAwesomeIcon
                  icon={faFileDownload}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "5px",
                  }}
                />
                Dowload log
              </Button>
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                }}
                onClick={openModalShowHistory}
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
                Tên giảng viên: &nbsp;
                {classDetail.teacherName != null
                  ? classDetail.teacherName
                  : "Chưa có"}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Tài khoản giảng viên: &nbsp;
                {classDetail.teacherUserName != null
                  ? classDetail.teacherUserName
                  : "Chưa có"}
              </span>
              {classDetail.classPeriod == null && (
                <span
                  className="group-info-item"
                  style={{ marginTop: "13px", marginBottom: "15px" }}
                >
                  Ca học: &nbsp; Chưa có
                </span>
              )}
              {classDetail.classPeriod != null && (
                <span
                  className="group-info-item"
                  style={{ marginTop: "13px", marginBottom: "15px" }}
                >
                  Ca học: &nbsp; {classDetail.classPeriod} (
                  {convertHourAndMinuteToString(
                    classDetail.startHour,
                    classDetail.startMinute,
                    classDetail.endHour,
                    classDetail.endMinute
                  )}
                  )
                </span>
              )}
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
                Mô tả: &nbsp;
                {classDetail.descriptions != null
                  ? classDetail.descriptions
                  : "Chưa có"}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Mã tham gia: &nbsp;
                {classDetail.passWord != null
                  ? classDetail.passWord
                  : "Không có mật khẩu"}
              </span>
              <span
                className="group-info-item"
                style={{
                  marginTop: "13px",
                  marginBottom: "15px",
                  color: "red",
                }}
              >
                Trạng thái lớp: &nbsp;
                {classDetail.statusClass === 0 ? "Đã mở" : "Đã khóa"}
              </span>
              <span
                className="group-info-item"
                style={{
                  marginTop: "13px",
                  marginBottom: "15px",
                  color: "red",
                }}
              >
                Trello: &nbsp;
                {classDetail.allowUseTrello === 0
                  ? "Cho phép"
                  : "Không cho phép"}
              </span>
              <span
                className="group-info-item"
                style={{
                  marginTop: "13px",
                  marginBottom: "15px",
                  color: "red",
                }}
              >
                Trao đổi sinh viên: &nbsp;
                {classDetail.statusTeacherEdit === 0
                  ? "Cho phép"
                  : "Không cho phép"}
              </span>
            </div>
            <div style={{ marginTop: "10px" }}>
              <br />
              <div style={{ marginBottom: "10px" }}>
                <div style={{ float: "left" }}>
                  <span style={{ fontSize: "17px", fontWeight: 500 }}>
                    <FontAwesomeIcon
                      icon={faTableList}
                      style={{
                        marginRight: "10px",
                        fontSize: "20px",
                      }}
                    />
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
                        marginRight: "5px",
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
                        style={{ marginRight: "5px" }}
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
                        marginRight: "5px",
                      }}
                    />{" "}
                    Tải mẫu
                  </Button>
                  <Button
                    onClick={() => {
                      handleOpenModalClass();
                    }}
                    style={{
                      backgroundColor: "rgb(38, 144, 214)",
                      color: "white",
                      marginRight: "5px",
                    }}
                  >
                    <FontAwesomeIcon
                      icon={faRightFromBracket}
                      color="white"
                      style={{ paddingRight: "5px" }}
                    />
                    {isMoveStudent === false
                      ? "Trao đổi sinh viên"
                      : "Chọn lớp cần chuyển"}
                  </Button>
                  <Popconfirm
                    disabled={!isKickStudent}
                    placement={"topRight"}
                    description={
                      "Bạn có chắc chắn muốn xóa những sinh viên này khỏi lớp không?"
                    }
                    okText="Có"
                    cancelText="Không"
                    onConfirm={() => {
                      handleKickStudent();
                    }}
                  >
                    <Button
                      onClick={() => handleShowStudentsCouldKick()}
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
                      {isKickStudent === false
                        ? "Xóa sinh viên khỏi lớp"
                        : "Xác nhận xóa"}
                    </Button>
                  </Popconfirm>
                  <Button
                    onClick={() => {
                      if (!isKickStudent && !isMoveStudent) {
                        message.success(
                          "Đang ở chế độ chỉ xem danh sách sinh viên !"
                        );
                      } else {
                        setIsMoveStudent(false);
                        setIsKickStudent(false);
                        message.success(
                          "Đã chuyển sang chế độ chỉ xem danh sách sinh viên! "
                        );
                      }
                    }}
                    style={{
                      backgroundColor: "rgb(38, 144, 214)",
                      color: "white",
                      marginRight: "5px",
                    }}
                  >
                    <FontAwesomeIcon
                      icon={faEye}
                      color="white"
                      style={{ paddingRight: "5px" }}
                    />{" "}
                    Chế độ xem
                  </Button>
                </div>
              </div>
              <br />
              <div style={{ minHeight: "140px", marginTop: "15px" }}>
                {students.length > 0 ? (
                  <>
                    <div className="table">
                      <Table
                        rowSelection={rowSelection}
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
                        imageStyle={{ height: "60px" }}
                        style={{
                          padding: "20px 0px 20px 0",
                        }}
                        description={<span>Không có dữ liệu</span>}
                      />
                    </p>
                  </>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
      <ModalShowHistory
        visible={visibleHistory}
        onCancel={cancelModalHistory}
      />
    </div>
  );
};

export default InformationClass;
