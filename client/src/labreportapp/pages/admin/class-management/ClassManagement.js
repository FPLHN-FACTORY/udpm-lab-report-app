import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFilter,
  faPlus,
  faEye,
  faTags,
  faDownload,
  faUpload,
  faPencilAlt,
  faRandom,
  faChainSlash,
  faFilterCircleDollar,
  faHistory,
  faGraduationCap,
  faFileDownload,
  faMailBulk,
} from "@fortawesome/free-solid-svg-icons";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import {
  Button,
  Input,
  Pagination,
  Table,
  Tooltip,
  Select,
  Row,
  Col,
  Empty,
  Tag,
  message,
} from "antd";
import LoadingIndicator from "../../../helper/loading";

import { useEffect, useState } from "react";
import { ClassAPI } from "../../../api/admin/class-manager/ClassAPI.api";
import "./style-class-management.css";
import "react-toastify/dist/ReactToastify.css";
import ModalCreateProject from "../../admin/class-management/create-class/ModalCreateClass";
import { Link } from "react-router-dom";
import { SetAdTeacher } from "../../../app/admin/AdTeacherSlice.reducer";
import ModalUpdateClass from "./update-class/ModalUpdateClass";
import {
  convertDateLongToString,
  convertHourAndMinuteToString,
} from "../../../helper/util.helper";
import {
  GetAdClassManagement,
  SetMyClass,
} from "../../../app/admin/ClassManager.reducer";
import LoadingIndicatorNoOverlay from "../../../helper/loadingNoOverlay";
import ModalRandomClass from "./random-class/ModalRandomClass";
import ModalImportClass from "./import-class/ModalImportClass";
import { AdMeetingPeriodAPI } from "../../../api/admin/AdMeetingPeriodAPI";
import {
  GetAdMeetingPeriod,
  SetAdMeetingPeriod,
} from "../../../app/admin/AdMeetingPeriodSlice.reducer";
import ModalShowHistory from "../detail-class/information-class/ModalShowHistory";
import ModalShowHistoryDetail from "./modal-show-history-detail/ModalShowHistoryDetail";
import ModalShowHistoryLogLuong from "./modal-show-history-log-luong/ModalShowHistoryLogLuong";
import ModalShowHistoryClassManager from "./modal-show-history-class-managerment/ModalShowHistoryClassManager";

const ClassManagement = () => {
  const { Option } = Select;
  const [listClassAll, setListClassAll] = useState([]); //getAll
  const [teacherDataAll, setTeacherDataAll] = useState([]); // Dữ liệu teacherId và username
  const [semesterDataAll, setSemesterDataAll] = useState([]); // Dữ liệu semester
  const [activityDataAll, setActivityDataAll] = useState([]); // Dữ liệu activity
  const [idSemesterSeach, setIdSemesterSearch] = useState("");
  const [idActivitiSearch, setIdActivitiSearch] = useState("none");

  const [selectedItems, setSelectedItems] = useState([]);
  const [code, setCode] = useState("");
  const [selectedItemsPerson, setSelectedItemsPerson] = useState("");
  const [loading, setLoading] = useState(false);
  const [loadingOverLay, setLoadingOverLay] = useState(false);
  const [current, setCurrent] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [clear, setClear] = useState(false);
  const [level, setLevel] = useState("");
  const [listLevel, setListLevel] = useState([]);
  const [size, setSize] = useState("10");
  const [statusClass, setStatusClass] = useState("");
  const [classSize, setClassSize] = useState("");
  const [statusTeacherEdit, setStatusTeacherEdit] = useState("");
  const dispatch = useAppDispatch();
  const listClassPeriod = [];

  for (let i = 0; i <= 9; i++) {
    listClassPeriod.push("" + i);
  }

  useEffect(() => {
    setIdSemesterSearch("");
  }, []);

  useEffect(() => {
    featchAllMyClass();
  }, [current, size]);

  const featchAllMyClass = async () => {
    setLoading(true);
    let filter = {
      idTeacher: selectedItemsPerson,
      idActivity: idActivitiSearch,
      idSemester: idSemesterSeach,
      code: code,
      classPeriod: selectedItems,
      page: current,
      size: size,
      levelId: level,
      classSize: classSize,
      statusClass: statusClass,
      statusTeacherEdit: statusTeacherEdit,
    };

    ClassAPI.getAllMyClass(filter).then((respone) => {
      if (respone.data.data != null) {
        setTotalPages(parseInt(respone.data.data.totalPages));
        setListClassAll(respone.data.data.data);
        dispatch(SetMyClass(respone.data.data.data));
      } else {
        setTotalPages(0);
        setListClassAll([]);
        dispatch(SetMyClass([]));
      }
      setLoading(false);
    });
  };

  useEffect(() => {
    const fetchTeacherData = async () => {
      const responseTeacherData = await ClassAPI.fetchAllTeacher();
      const teacherData = responseTeacherData.data.data;
      dispatch(SetAdTeacher(teacherData));
      setTeacherDataAll(teacherData);
    };
    fetchTeacherData();
  }, []);

  useEffect(() => {
    if (idSemesterSeach === "") {
      setIdActivitiSearch("none");
      setActivityDataAll([]);
    } else {
      const featchDataActivity = async (idSemesterSeach) => {
        await ClassAPI.getAllActivityByIdSemester(idSemesterSeach).then(
          (respone) => {
            if (respone.data.data.length === 0) {
              setIdActivitiSearch("none");
              setActivityDataAll([]);
            } else {
              setIdActivitiSearch("");
              setActivityDataAll(respone.data.data);
            }
          }
        );
      };
      featchDataActivity(idSemesterSeach);
    }
  }, [idSemesterSeach]);

  const featchDataSemester = async () => {
    try {
      const responseClassAll = await ClassAPI.fetchAllSemester();
      const listSemester = responseClassAll.data;
      setSemesterDataAll(listSemester.data);
    } catch (error) {}
  };

  useEffect(() => {
    featchDataSemester();
    loadDataMeetingPeriod();
  }, []);

  const loadDataLevel = () => {
    ClassAPI.getAllLevel().then((response) => {
      setListLevel(response.data.data);
    });
  };

  const loadDataMeetingPeriod = () => {
    AdMeetingPeriodAPI.getAll().then((response) => {
      dispatch(SetAdMeetingPeriod(response.data.data));
    });
  };

  useEffect(() => {
    document.title = "Quản lý lớp học | Lab-Report-App";
    setCode("");
    setSelectedItems("");
    loadDataLevel();
    setSelectedItemsPerson("");
    loadDataSemesterCurrent();
  }, []);

  const dataMeetingPeriod = useAppSelector(GetAdMeetingPeriod);

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt - b.stt,
      width: "3%",
    },
    {
      title: "Mã",
      dataIndex: "code",
      key: "code",
      sorter: (a, b) => a.code.localeCompare(b.code),
    },
    {
      title: "Thời gian bắt đầu",
      dataIndex: "startTime",
      key: "startTime",
      sorter: (a, b) => a.startTime - b.startTime,
      render: (text, record) => {
        const startTime = new Date(record.startTime);

        const formattedStartTime = `${startTime.getDate()}/${
          startTime.getMonth() + 1
        }/${startTime.getFullYear()}`;

        return <span>{formattedStartTime}</span>;
      },
    },
    {
      title: "Ca",
      dataIndex: "nameClassPeriod",
      key: "nameClassPeriod",
      render: (text, record) => {
        if (record.nameClassPeriod == null) {
          return <span>-</span>;
        } else {
          return <span>{record.nameClassPeriod}</span>;
        }
      },
    },
    {
      title: "Thời gian",
      dataIndex: "timePeriod",
      key: "timePeriod",
      render: (text, record) => {
        if (record.startHour == null) {
          return <span>-</span>;
        } else {
          return (
            <span>
              {convertHourAndMinuteToString(
                record.startHour,
                record.startMinute,
                record.endHour,
                record.endMinute
              )}
            </span>
          );
        }
      },
    },
    {
      title: "Sĩ số",
      dataIndex: "classSize",
      key: "classSize",
      sorter: (a, b) => a.classSize - b.classSize,
    },
    {
      title: "Giảng viên",
      dataIndex: "userNameTeacher",
      key: "userNameTeacher",
      sorter: (a, b) => {
        if (a.userNameTeacher == null && b.userNameTeacher == null) {
          return 0;
        }
        if (a.userNameTeacher == null) {
          return -1;
        }
        if (b.userNameTeacher == null) {
          return 1;
        }
        return a.userNameTeacher.localeCompare(b.userNameTeacher);
      },
      render: (text, record) => {
        if (record.userNameTeacher == null) {
          return <span>Chưa có</span>;
        } else {
          return <span>{record.userNameTeacher}</span>;
        }
      },
    },
    {
      title: "Level",
      dataIndex: "nameLevel",
      key: "nameLevel",
      sorter: (a, b) => a.nameLevel.localeCompare(b.nameLevel),
    },
    {
      title: "Hoạt động",
      dataIndex: "activityName",
      key: "activityName",
      sorter: (a, b) => a.activityName.localeCompare(b.activityName),
    },
    {
      title: "Trạng thái",
      dataIndex: "statusClass",
      key: "statusClass",
      render: (text, record) => {
        if (record.statusClass === "Đủ điều kiện") {
          return <Tag color="success">{record.statusClass}</Tag>;
        } else {
          return <Tag color="error">{record.statusClass}</Tag>;
        }
      },
    },
    {
      title: "Quyền GV",
      dataIndex: "statusTeacherEdit",
      key: "statusTeacherEdit",
      render: (text, record) => {
        if (record.statusTeacherEdit === 0) {
          return <Tag color="success">Cho phép</Tag>;
        } else {
          return <Tag color="error">Không cho phép</Tag>;
        }
      },
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div>
          <Tooltip title="Xem chi tiết">
            <Link to={`/admin/class-management/information-class/${record.id}`}>
              <FontAwesomeIcon
                style={{
                  marginRight: "15px",
                  cursor: "pointer",
                  color: "rgb(38, 144, 214)",
                }}
                icon={faEye}
                size="1x"
              />
            </Link>
          </Tooltip>
          <Tooltip
            title="Cập nhật"
            onClick={() => {
              setShowUpdateModal(true);
              setIDClassUpdate(record.id);
            }}
          >
            <FontAwesomeIcon
              icon={faPencilAlt}
              size="1x"
              style={{
                marginLeft: 7,
                marginRight: "15px",
                color: "rgb(38, 144, 214)",
                cursor: "pointer",
              }}
            />
          </Tooltip>
          <Tooltip title="Lịch sử lớp">
            <FontAwesomeIcon
              icon={faHistory}
              onClick={() => {
                openHistoryDetail(record.id);
              }}
              size="1x"
              style={{
                marginLeft: 7,
                color: "rgb(38, 144, 214)",
                cursor: "pointer",
              }}
            />
          </Tooltip>
        </div>
      ),
      width: "170px",
    },
  ];

  const dataClass = useAppSelector(GetAdClassManagement);

  const handleSearchAllByFilter = async () => {
    await featchAllMyClass();
  };
  const handleCodeChange = (e) => {
    setCode(e.target.value);
  };
  const handleSelectChange = (value) => {
    setSelectedItems(value);
  };

  const handleSelectPersonChange = (value) => {
    setSelectedItemsPerson(value);
  };

  const handleClear = () => {
    setIdSemesterSearch("");
    setSelectedItems("");
    setCode("");
    setClassSize("");
    setLevel("");
    setStatusClass("");
    setStatusTeacherEdit("");
    setIdActivitiSearch("none");
    setSelectedItemsPerson("");
    setClear(true);
  };

  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [idClassUpdate, setIDClassUpdate] = useState("");

  const handleModalCreateCancel = async () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowCreateModal(false);
  };

  const handleModalUpdateCancel = async () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowUpdateModal(false);
  };

  const filterOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };

  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const format = `${date.getHours()}_${date.getMinutes()}_${date.getSeconds()}`;
    return format;
  };

  const exportExcel = () => {
    setLoadingOverLay(true);
    let filter = {
      idTeacher: selectedItemsPerson,
      idActivity: idActivitiSearch,
      idSemester: idSemesterSeach,
      code: code,
      classPeriod: selectedItems,
      page: current,
      size: size,
      levelId: level,
      classSize: classSize,
      statusClass: statusClass,
      statusTeacherEdit: statusTeacherEdit,
    };

    ClassAPI.exportExcel(filter).then((response) => {
      setLoadingOverLay(false);
      const blob = new Blob([response.data], {
        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
      });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download =
        "Danh sách lớp học_" +
        convertLongToDate(new Date().getTime()) +
        ".xlsx";
      link.click();
      window.URL.revokeObjectURL(url);
    });
  };

  const [showRandomModal, setShowRandomModal] = useState(false);

  const handleClickModalRandom = () => {
    setShowRandomModal(true);
  };

  const handleCancelModalRandom = () => {
    setShowRandomModal(false);
  };

  const [showImportModal, setShowImportModal] = useState(false);

  const handleClickModalImportClass = () => {
    setShowImportModal(true);
  };

  const handleCancelModalImportClass = () => {
    setShowImportModal(false);
  };

  const [showHistoryDetail, setShowHistoryDetail] = useState(false);
  const [selectedIdClass, setSelectedIdClass] = useState(null);

  const openHistoryDetail = (id) => {
    setSelectedIdClass(id);
    setShowHistoryDetail(true);
  };

  const cancelModalHistoryDetail = () => {
    setShowHistoryDetail(false);
    setSelectedIdClass(null);
  };

  const [idSemesterCurrent, setIdSemesterCurrent] = useState("");

  const loadDataSemesterCurrent = () => {
    ClassAPI.getSemesterCurrent().then((res) => {
      setIdSemesterCurrent(res.data.data);
    });
  };

  const dowloadLogLuong = () => {
    if (idSemesterCurrent === "" && idSemesterSeach === "") {
      message.error("Hãy chọn học kỳ để dowload file log");
      return;
    }
    let idSemester =
      idSemesterSeach !== "" ? idSemesterSeach : idSemesterCurrent;
    setLoading(true);
    ClassAPI.dowloadLogLuong(idSemester).then(
      (response) => {
        if (response.data.size === 0) {
          message.error("File log không tồn tại");
        } else {
          const url = window.URL.createObjectURL(new Blob([response.data]));
          const a = document.createElement("a");
          a.href = url;
          a.download = "luong_lop_hoc.csv";
          a.click();
          window.URL.revokeObjectURL(url);
          setLoading(false);
        }
      },
      (error) => {
        console.log(error);
      }
    );
  };
  const dowloadLog = () => {
    if (idSemesterCurrent === "" && idSemesterSeach === "") {
      message.error("Hãy chọn học kỳ để dowload file log");
      return;
    }
    let idSemester =
      idSemesterSeach !== "" ? idSemesterSeach : idSemesterCurrent;
    setLoading(true);
    ClassAPI.dowloadLog(idSemester).then(
      (response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const a = document.createElement("a");
        a.href = url;
        a.download = "lop_hoc.csv";
        a.click();
        window.URL.revokeObjectURL(url);
        setLoading(false);
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const [visibleHistory, setVisibleHistory] = useState(false);
  const openModalShowHistory = () => {
    if (idSemesterCurrent === "" && idSemesterSeach === "") {
      message.error("Hãy chọn học kỳ để xem lịch sử luồng log lớp học");
      return;
    }
    let idSemester =
      idSemesterSeach !== "" ? idSemesterSeach : idSemesterCurrent;
    setSelectedIdSemester(idSemester);
    setVisibleHistory(true);
  };
  const cancelModalHistory = () => {
    setVisibleHistory(false);
  };

  const [selectedIdSemester, setSelectedIdSemester] = useState(null);
  const [showHistoryLogLuong, setShowHistoryLogLuong] = useState(false);

  const openModalShowHistoryLogLuong = () => {
    if (idSemesterCurrent === "" && idSemesterSeach === "") {
      message.error("Hãy chọn học kỳ để xem lịch sử luồng log lớp học");
      return;
    }
    let idSemester =
      idSemesterSeach !== "" ? idSemesterSeach : idSemesterCurrent;
    setSelectedIdSemester(idSemester);
    setShowHistoryLogLuong(true);
  };

  const cancelModalHistoryLogLuong = () => {
    setSelectedIdSemester(null);
    setShowHistoryLogLuong(false);
  };

  const sendMail = () => {
    setLoading(true);
    ClassAPI.sendMailToStudent().then(
      (response) => {
        message.success("Gửi mail thông báo đến sinh viên thành công");
        setLoading(false);
      },
      (error) => {}
    );
  };

  return (
    <>
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon icon={faGraduationCap} size="1x" />
            <span style={{ marginLeft: "10px" }}>Quản lý lớp học</span>
          </span>
        </div>
        <div style={{ marginRight: 14 }}>
          <Button
            style={{
              color: "white",
              backgroundColor: "rgb(55, 137, 220)",
              marginRight: "5px",
            }}
            onClick={dowloadLogLuong}
          >
            <FontAwesomeIcon
              icon={faDownload}
              size="1x"
              style={{
                backgroundColor: "rgb(55, 137, 220)",
                marginRight: "7px",
              }}
            />{" "}
            Dowload luồng log
          </Button>
          <Button
            style={{
              color: "white",
              backgroundColor: "rgb(55, 137, 220)",
              marginRight: "5px",
            }}
            onClick={openModalShowHistoryLogLuong}
          >
            <FontAwesomeIcon
              icon={faHistory}
              size="1x"
              style={{
                backgroundColor: "rgb(55, 137, 220)",
                marginRight: "7px",
              }}
            />{" "}
            Lịch sử luồng
          </Button>
        </div>
      </div>
      <div
        className="class_management"
        style={{ paddingTop: 10, marginTop: 0 }}
      >
        {loading && <LoadingIndicator />}
        {loadingOverLay && <LoadingIndicatorNoOverlay />}
        <div className="filter_class_management">
          <FontAwesomeIcon icon={faFilter} style={{ fontSize: "19px" }} />{" "}
          <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
          <hr />
          <Row>
            <Col span={12} style={{ padding: "10px" }}>
              <div>
                <span>Học kỳ</span>
                <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  value={idSemesterSeach}
                  onChange={(value) => {
                    setIdSemesterSearch(value);
                  }}
                  filterOption={filterOptions}
                >
                  <Option value="">Chọn 1 học kỳ</Option>

                  {semesterDataAll != null &&
                    semesterDataAll.map((semester) => (
                      <Option key={semester.id} value={semester.id}>
                        {semester.name +
                          " (" +
                          convertDateLongToString(semester.startTime) +
                          " - " +
                          convertDateLongToString(semester.endTime) +
                          ")"}
                      </Option>
                    ))}
                </Select>
              </div>
            </Col>
            <Col span={12} style={{ padding: "10px" }}>
              <div className="selectSearch3">
                <span>Hoạt động</span>
                <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  value={idActivitiSearch}
                  onChange={(value) => {
                    setIdActivitiSearch(value);
                  }}
                  filterOption={filterOptions}
                >
                  {activityDataAll.length > 0 && (
                    <Option value="">Tất cả</Option>
                  )}
                  {activityDataAll.length === 0 && (
                    <Option value="none">Không có hoạt động</Option>
                  )}
                  {activityDataAll != null &&
                    activityDataAll.map((activity) => (
                      <Option key={activity.id} value={activity.id}>
                        {activity.name}
                      </Option>
                    ))}
                </Select>
              </div>
            </Col>
          </Row>
          <Row>
            <Col span={12} style={{ padding: "10px" }}>
              <div>
                <span>Ca học dự kiến</span>
                <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  value={selectedItems}
                  onChange={handleSelectChange}
                >
                  <Option value="">Tất Cả</Option>
                  <Option value="none">Chưa có ca học</Option>
                  {dataMeetingPeriod != null &&
                    dataMeetingPeriod.map((item) => {
                      return (
                        <Option value={item.id} key={item.id}>
                          {item.name} -{" "}
                          {convertHourAndMinuteToString(
                            item.startHour,
                            item.startMinute,
                            item.endHour,
                            item.endMinute
                          )}
                        </Option>
                      );
                    })}
                </Select>
              </div>
            </Col>
            <Col span={12} style={{ padding: "10px" }}>
              <div>
                <span>Giảng viên</span>
                <br />
                <Select
                  showSearch
                  style={{ width: "100%" }}
                  value={selectedItemsPerson}
                  onChange={handleSelectPersonChange}
                  filterOption={filterOptions}
                >
                  <Option value="">Tất cả</Option>
                  <Option value="none">Chưa có giảng viên</Option>
                  {teacherDataAll != null &&
                    teacherDataAll.map((teacher) => (
                      <Option key={teacher.id} value={teacher.id}>
                        {teacher.userName + " - " + teacher.name}
                      </Option>
                    ))}
                </Select>
              </div>
            </Col>
          </Row>
          <Row>
            <Col span={12} style={{ padding: "10px" }}>
              <div className="inputCode">
                <span>Mã lớp</span>
                <br />
                <Input
                  placeholder="Nhập mã lớp"
                  type="text"
                  value={code}
                  onChange={handleCodeChange}
                />
              </div>
            </Col>
            <Col span={12} style={{ padding: "10px" }}>
              <div className="inputCode">
                <span>Cấp độ</span>
                <br />
                <Select
                  onChange={(e) => {
                    setLevel(e);
                  }}
                  style={{ width: "100%" }}
                  showSearch
                  filterOption={filterOptions}
                  value={level}
                >
                  <Option value={""}>Tất cả</Option>
                  {listLevel != null &&
                    listLevel.length > 0 &&
                    listLevel.map((item) => (
                      <Option value={item.id} key={item.id}>
                        {item.name}
                      </Option>
                    ))}
                </Select>
              </div>
            </Col>
            <Col span={12} style={{ padding: "10px" }}>
              <div>
                <span>Sĩ số lớp</span>
                <br />
                <Input
                  type="number"
                  placeholder="Nhập sĩ số lớp"
                  value={classSize}
                  onChange={(e) => {
                    setClassSize(e.target.value);
                  }}
                />
              </div>
            </Col>
            <Col span={12} style={{ padding: "10px" }}>
              <div>
                <span>Quyền giảng viên chỉnh sửa</span>
                <br />
                <Select
                  value={statusTeacherEdit}
                  onChange={(e) => {
                    setStatusTeacherEdit(e);
                  }}
                  style={{ width: "100%" }}
                >
                  <Option value="">Tất cả</Option>
                  <Option value="0">Cho phép chỉnh sửa</Option>
                  <Option value="1">Không cho phép chỉnh sửa</Option>
                </Select>
              </div>
            </Col>
            <Col span={24} style={{ padding: "10px" }}>
              <div>
                <span>Trạng thái</span>
                <br />
                <Select
                  value={statusClass}
                  onChange={(e) => {
                    setStatusClass(e);
                  }}
                  style={{ width: "100%" }}
                >
                  <Option value="">Tất cả</Option>
                  <Option value="yes">Đủ điều kiện</Option>
                  <Option value="no">Chưa đủ điều kiện</Option>
                </Select>
              </div>
            </Col>
          </Row>
          <div
            className="box_btn_filter"
            style={{ paddingBottom: 15, marginTop: 15 }}
          >
            <Button
              className="btn_filter"
              onClick={handleSearchAllByFilter}
              style={{ marginRight: 15 }}
            >
              <FontAwesomeIcon
                icon={faFilterCircleDollar}
                style={{ marginRight: 5 }}
              />{" "}
              Tìm kiếm
            </Button>
            <Button
              className="btn_clear"
              style={{ backgroundColor: "rgb(38, 144, 214)" }}
              onClick={handleClear}
            >
              {" "}
              <FontAwesomeIcon
                icon={faChainSlash}
                style={{ marginRight: 5 }}
              />{" "}
              Làm mới bộ lọc
            </Button>
          </div>
        </div>

        <div className="table-class-management">
          {" "}
          <div style={{ marginBottom: "8px" }}>
            <div className="table-class-management-info">
              {" "}
              <FontAwesomeIcon
                icon={faGraduationCap}
                style={{ fontSize: "18px" }}
              />
              <span
                style={{
                  fontSize: "18px",
                  fontWeight: "500",
                  marginBottom: "-50px",
                }}
              >
                {" "}
                Danh sách lớp học
              </span>{" "}
            </div>
            <div className="createButton">
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: 5,
                }}
                onClick={sendMail}
              >
                <FontAwesomeIcon
                  icon={faMailBulk}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "5px",
                  }}
                />
                Gửi mail thông báo
              </Button>{" "}
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: 5,
                }}
                onClick={dowloadLog}
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
                  marginRight: 5,
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
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: "5px",
                }}
                onClick={exportExcel}
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
                onClick={handleClickModalImportClass}
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
                onClick={handleClickModalRandom}
              >
                <FontAwesomeIcon
                  icon={faRandom}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "7px",
                  }}
                />{" "}
                Tạo nhiều lớp học
              </Button>
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                }}
                onClick={() => {
                  setShowCreateModal(true);
                }}
              >
                <FontAwesomeIcon
                  icon={faPlus}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "7px",
                  }}
                />{" "}
                Thêm Lớp
              </Button>
            </div>
          </div>
          <br />
          <div>
            <div>
              {listClassAll.length > 0 ? (
                <>
                  <div className="table_custom_class_management">
                    <Table
                      dataSource={dataClass}
                      rowKey="id"
                      columns={columns}
                      pagination={false}
                    />
                  </div>
                  <div>
                    <div
                      className="pagination_box"
                      style={{ display: "flex", alignItems: "center" }}
                    >
                      <Pagination
                        style={{ marginRight: "10px" }}
                        simple
                        current={current}
                        onChange={(value) => {
                          setCurrent(value);
                        }}
                        total={totalPages * 10}
                      />
                      <Select
                        style={{ width: "100px", marginLeft: "10px" }}
                        value={size}
                        onChange={(e) => {
                          setSize(e);
                        }}
                      >
                        <Option value="10">10</Option>
                        <Option value="25">25</Option>
                        <Option value="50">50</Option>
                        <Option value="100">100</Option>
                        <Option value="250">250</Option>
                        <Option value="500">500</Option>
                        <Option value="1000">1000</Option>
                      </Select>
                    </div>
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
                      description={<span>Không tìm thấy lớp học</span>}
                    />{" "}
                  </p>
                </>
              )}
            </div>
            <div>
              <span style={{ color: "red" }}>
                (*) Lưu ý: Lớp có trạng thái chưa đủ điều kiện là lớp có sĩ số
                nhỏ hơn (sĩ số nhỏ nhất) đã cấu hình ở màn cấu hình lớp học.
              </span>
            </div>
          </div>
        </div>
        <ModalCreateProject
          visible={showCreateModal}
          onCancel={handleModalCreateCancel}
        />
        <ModalUpdateClass
          visible={showUpdateModal}
          onCancel={handleModalUpdateCancel}
          id={idClassUpdate}
        />
        <ModalRandomClass
          visible={showRandomModal}
          onCancel={handleCancelModalRandom}
          fetchData={featchAllMyClass}
        />
        <ModalImportClass
          visible={showImportModal}
          onCancel={handleCancelModalImportClass}
          fetchData={featchAllMyClass}
        />
        <ModalShowHistoryDetail
          id={selectedIdClass}
          visible={showHistoryDetail}
          onCancel={cancelModalHistoryDetail}
        />
        <ModalShowHistoryLogLuong
          idSemester={selectedIdSemester}
          visible={showHistoryLogLuong}
          onCancel={cancelModalHistoryLogLuong}
        />
        <ModalShowHistoryClassManager
          idSemester={selectedIdSemester}
          visible={visibleHistory}
          onCancel={cancelModalHistory}
        />
      </div>
    </>
  );
};

export default ClassManagement;
