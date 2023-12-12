import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheck,
  faClose,
  faCodeCompare,
  faCommentDots,
  faDownload,
  faEye,
  faFilter,
  faGraduationCap,
  faHistory,
} from "@fortawesome/free-solid-svg-icons";
import {
  Button,
  Checkbox,
  Col,
  Empty,
  Input,
  Modal,
  Pagination,
  Row,
  Select,
  Table,
  Tag,
  Tooltip,
  message,
} from "antd";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import { ClassAPI } from "../../../api/admin/class-manager/ClassAPI.api";
import { SetAdTeacher } from "../../../app/admin/AdTeacherSlice.reducer";
import { AdMeetingPeriodAPI } from "../../../api/admin/AdMeetingPeriodAPI";
import {
  GetAdMeetingPeriod,
  SetAdMeetingPeriod,
} from "../../../app/admin/AdMeetingPeriodSlice.reducer";
import {
  convertDateLongToString,
  convertHourAndMinuteToString,
} from "../../../helper/util.helper";
import {
  GetAdMeetingRequest,
  SetAdMeetingRequest,
} from "../../../app/admin/AdMeetingRequestSlice.reducer";
import { AdMeetingRequestAPI } from "../../../api/admin/AdMeetingRequestAPI";
import { Link } from "react-router-dom";
import LoadingIndicator from "../../../helper/loading";
import ModalAllMeetingRequest from "./modal-all-meeting-request/ModalAllMeetingRequest";
import { SetAdCountApproveMeetingRequest } from "../../../app/admin/AdCountApproveMeetingRequest.reducer";
import ModalReasonsClass from "./modal-reasons-class/ModalReasonsClass";
import ModalShowHistory from "./modal-show-history/ModalShowHistory";
const { confirm } = Modal;
const { Option } = Select;

const AdApproveMeeting = () => {
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
    document.title = "Phê duyệt lịch học | Lab-Report-App";
    setCode("");
    setSelectedItems("");
    loadDataLevel();
    setSelectedItemsPerson("");
    loadDataSemesterCurrent();
  }, []);
  const [idSemesterCurrent, setIdSemesterCurrent] = useState("");
  const loadDataSemesterCurrent = () => {
    ClassAPI.getSemesterCurrent().then((res) => {
      setIdSemesterCurrent(res.data.data);
    });
  };

  const dataMeetingPeriod = useAppSelector(GetAdMeetingPeriod);
  const filterOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };

  const handleSelectPersonChange = (value) => {
    setSelectedItemsPerson(value);
  };

  const handleSelectChange = (value) => {
    setSelectedItems(value);
  };

  const handleCodeChange = (e) => {
    setCode(e.target.value);
  };

  const featchAllClassHaveMeetingRequest = async () => {
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
    };

    AdMeetingRequestAPI.getAllClassHaveMeetingRequest(filter).then(
      (respone) => {
        if (respone.data.data != null) {
          setTotalPages(parseInt(respone.data.data.totalPages));
          setListClassAll(respone.data.data.data);
          dispatch(SetAdMeetingRequest(respone.data.data.data));
        } else {
          setTotalPages(0);
          setListClassAll([]);
          dispatch(SetAdMeetingRequest([]));
        }
        setLoading(false);
      }
    );
  };

  useEffect(() => {
    featchAllClassHaveMeetingRequest();

    return () => {
      dispatch(SetAdMeetingRequest([]));
    };
  }, [size, current]);

  const data = useAppSelector(GetAdMeetingRequest);

  const [selectedRowKeys, setSelectedRowKeys] = useState([]);

  const handleCheckboxChange = (e, record) => {
    const keys = [...selectedRowKeys];
    if (e.target.checked) {
      keys.push(record.id);
    } else {
      const index = keys.indexOf(record.id);
      if (index !== -1) {
        keys.splice(index, 1);
      }
    }
    setSelectedRowKeys(keys);
  };

  const handleSelectAll = (e) => {
    const keys = e.target.checked ? data.map((record) => record.id) : [];
    setSelectedRowKeys(keys);
  };

  const columns = [
    {
      title: (
        <Checkbox
          onChange={(e) => handleSelectAll(e)}
          indeterminate={
            selectedRowKeys.length > 0 && selectedRowKeys.length < data.length
          }
          checked={selectedRowKeys.length === data.length}
        />
      ),
      dataIndex: "checkbox",
      key: "checkbox",
      render: (text, record) => (
        <Checkbox
          onChange={(e) => handleCheckboxChange(e, record)}
          checked={selectedRowKeys.includes(record.id)}
        />
      ),
    },
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
      sorter: (a, b) => a.nameClassPeriod.localeCompare(b.nameClassPeriod),
      render: (text, record) => {
        if (record.nameClassPeriod == null) {
          return <span>Chưa có</span>;
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
          return <span>Chưa có</span>;
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
      title: "Số buổi chờ phê duyệt",
      dataIndex: "numberMeetingRequest",
      align: "center",
      key: "numberMeetingRequest",
      sorter: (a, b) => a.numberMeetingRequest - b.numberMeetingRequest,
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      align: "center",
      render: (text, record) => (
        <div>
          <Tooltip title="Xem danh sách buổi học chờ phê duyệt">
            <FontAwesomeIcon
              style={{
                cursor: "pointer",
                color: "rgb(38, 144, 214)",
              }}
              onClick={() => openModalAllMeetingRequest(record)}
              icon={faEye}
              size="1x"
            />
          </Tooltip>
        </div>
      ),
      width: "130px",
    },
  ];

  const [visibleModalAllMeetingRequest, setVisibleModalAllMeetingRequest] =
    useState(false);
  const [selectedClass, setSelectedClass] = useState(null);

  const openModalAllMeetingRequest = (item) => {
    setSelectedClass(item);
    setVisibleModalAllMeetingRequest(true);
  };

  const cancelModalAllMeetingRequest = () => {
    setVisibleModalAllMeetingRequest(false);
    setSelectedClass(null);
  };

  const approveAllClass = () => {
    if (selectedRowKeys.length === 0) {
      message.error("Mời chọn lớp cần phê duyệt");
      return;
    }
    confirm({
      title: "Xác nhận phê duyệt lịch học",
      content: "Bạn có chắc chắn muốn phê duyệt lịch học không?",
      onOk() {
        setLoading(true);
        AdMeetingRequestAPI.approveClass(selectedRowKeys).then(
          (response) => {
            message.success("Phê duyệt thành công !");
            featchAllClassHaveMeetingRequest();
            AdMeetingRequestAPI.countClassHaveMeetingRequest().then((res) => {
              dispatch(SetAdCountApproveMeetingRequest(res.data.data));
            });
          },
          (error) => {}
        );
      },
      onCancel() {},
    });
  };

  const noApproveAllClass = () => {
    confirm({
      title: "Xác nhận từ chối phê duyệt lịch học",
      content: "Bạn có chắc chắn muốn từ chối phê duyệt lịch học không?",
      onOk() {
        setLoading(true);
        AdMeetingRequestAPI.noApproveClass(selectedRowKeys).then(
          (response) => {
            message.success("Từ chối phê duyệt thành công !");
            featchAllClassHaveMeetingRequest();
            AdMeetingRequestAPI.countClassHaveMeetingRequest().then((res) => {
              dispatch(SetAdCountApproveMeetingRequest(res.data.data));
            });
          },
          (error) => {}
        );
      },
      onCancel() {},
    });
  };

  const [visibleModalReasonsClass, setVisibleModalReasonsClass] =
    useState(false);

  const openModalReasonsClass = () => {
    if (selectedRowKeys.length === 0) {
      message.error("Mời chọn lớp cần phê duyệt");
      return;
    }
    setVisibleModalReasonsClass(true);
  };

  const onCancelModalReasonsClass = () => {
    setVisibleModalReasonsClass(false);
  };
  const [selectedIdSemester, setSelectedIdSemester] = useState(null);
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

  const dowloadLog = () => {
    if (idSemesterCurrent === "" && idSemesterSeach === "") {
      message.error("Hãy chọn học kỳ để dowload file log");
      return;
    }
    let idSemester =
      idSemesterSeach !== "" ? idSemesterSeach : idSemesterCurrent;
    setLoading(true);
    AdMeetingRequestAPI.dowloadLog(idSemester).then(
      (response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const a = document.createElement("a");
        a.href = url;
        a.download = "phe_duyet_lich_hoc.csv";
        a.click();
        window.URL.revokeObjectURL(url);
        setLoading(false);
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return (
    <>
      {" "}
      {loading && <LoadingIndicator />}
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon icon={faCheck} size="20px" />
            <span style={{ marginLeft: "8px" }}>Phê duyệt lịch học</span>
          </span>
        </div>
      </div>
      <div className="box-three-dashboad" style={{ marginTop: 30 }}>
        <div className="box-three-son-dashboad" style={{ padding: 20 }}>
          <Row>
            <FontAwesomeIcon
              icon={faFilter}
              style={{ fontSize: "20px", paddingRight: "8px" }}
            />
            <span style={{ fontWeight: "400", fontSize: "18px" }}> Bộ lọc</span>
          </Row>{" "}
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

                  {semesterDataAll.map((semester) => (
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
                  {activityDataAll.map((activity) => (
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
                  {dataMeetingPeriod.map((item) => {
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
                  {teacherDataAll.map((teacher) => (
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
                  {listLevel.length > 0 &&
                    listLevel.map((item) => (
                      <Option value={item.id} key={item.id}>
                        {item.name}
                      </Option>
                    ))}
                </Select>
              </div>
            </Col>
          </Row>
          <br />
          <div className="title-box-two">
            <div style={{ textAlign: "center", paddingBottom: "10px" }}>
              <Button
                className={"btn_filter"}
                onClick={() => {}}
                style={{
                  marginRight: "20px",
                }}
              >
                <FontAwesomeIcon
                  icon={faFilter}
                  style={{ paddingRight: "5px" }}
                />{" "}
                <span>Tìm kiếm</span>
              </Button>
              <Button className="btn_clean">
                <FontAwesomeIcon
                  icon={faCodeCompare}
                  style={{ paddingRight: "5px" }}
                />{" "}
                <span>Làm mới bộ lọc</span>
              </Button>
            </div>
          </div>
        </div>
      </div>
      <div className="box-four-dashboad" style={{ marginTop: 30 }}>
        <div
          className="box-four-son-dashboad"
          style={{ minHeight: "355px", height: "auto" }}
        >
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
                Danh sách lớp học đã gửi yêu cầu phê duyệt lịch học
              </span>{" "}
            </div>
            <div className="createButton">
              {" "}
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: 5,
                }}
                onClick={dowloadLog}
              >
                <FontAwesomeIcon
                  icon={faDownload}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "7px",
                  }}
                />{" "}
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
                    marginRight: "7px",
                  }}
                />{" "}
                Lịch sử
              </Button>
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: 5,
                }}
                onClick={approveAllClass}
              >
                <FontAwesomeIcon
                  icon={faCheck}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "7px",
                  }}
                />{" "}
                Phê duyệt
              </Button>
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                }}
                onClick={openModalReasonsClass}
              >
                <FontAwesomeIcon
                  icon={faClose}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "7px",
                  }}
                />{" "}
                Từ chối
              </Button>
            </div>
          </div>
          <br />
          <div>
            <div>
              {data.length > 0 ? (
                <>
                  <div className="table_custom_class_management">
                    <Table
                      dataSource={data}
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
                      description={<span>Không có dữ liệu</span>}
                    />{" "}
                  </p>
                </>
              )}
            </div>
          </div>
        </div>
      </div>
      <ModalAllMeetingRequest
        visible={visibleModalAllMeetingRequest}
        onCancel={cancelModalAllMeetingRequest}
        item={selectedClass}
        fetchData={featchAllClassHaveMeetingRequest}
      />
      <ModalReasonsClass
        visible={visibleModalReasonsClass}
        listIdClass={selectedRowKeys}
        onCancel={onCancelModalReasonsClass}
        noApproveClass={noApproveAllClass}
      />
      <ModalShowHistory
        idSemester={selectedIdSemester}
        visible={visibleHistory}
        onCancel={cancelModalHistory}
      />
    </>
  );
};

export default AdApproveMeeting;
