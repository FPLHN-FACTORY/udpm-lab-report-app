import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-teacher-dashboard.css";
import {
  faCodeCompare,
  faDownload,
  faFilter,
  faLineChart,
  faTableList,
} from "@fortawesome/free-solid-svg-icons";
import {
  Button,
  Col,
  Empty,
  Pagination,
  Row,
  Select,
  Table,
  Tooltip,
  message,
} from "antd";
import { useState } from "react";
import { useAppDispatch } from "../../../app/hook";
import { TeacherSemesterAPI } from "../../../api/teacher/semester/TeacherSemester.api";
import { SetTeacherSemester } from "../../../app/teacher/semester/teacherSemesterSlice.reduce";
import { TeacherActivityAPI } from "../../../api/teacher/activity/TeacherActivity.api";
import { useEffect } from "react";
import { convertHourAndMinuteToString } from "../../../helper/util.helper";
import { TeacherStatisticalAPI } from "../../../api/teacher/statistical/TeacherStatistical.api";
import LoadingIndicator from "../../../helper/loading";
import LoadingIndicatorNoOverlay from "../../../helper/loadingNoOverlay";
const { Option } = Select;

const TeacherDashboard = () => {
  const dispatch = useAppDispatch();
  const [idSemesterSearch, setIdSemesterSearch] = useState("");
  const [idActivitiSearch, setIdActivitiSearch] = useState("");
  const [listSemester, setListSemester] = useState([]);
  const [listActivity, setListActivity] = useState([]);
  const [clear, setClear] = useState(false);
  const [current, setCurrent] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);
  const [listClass, setListClass] = useState([]);
  const [loadOne, setLoadOne] = useState(false);
  const [countClass, setCountClass] = useState({
    classLesson: 0,
    classNumber: 0,
  });
  const [loadingExport, setLoadingExport] = useState(false);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Thống kê";
  }, []);

  useEffect(() => {
    featchDataSemester();
    featchAllMyClass();
  }, [current]);

  useEffect(() => {
    if (idSemesterSearch !== "") {
      featchDataActivity(idSemesterSearch);
    } else {
      setListActivity([]);
      setIdActivitiSearch("");
    }
  }, [idSemesterSearch]);

  useEffect(() => {
    if (clear) {
      featchAllMyClass();
    }
    setClear(false);
  }, [clear]);

  const featchAllMyClass = async () => {
    setLoading(false);
    let filter = {
      idActivity: idActivitiSearch,
      idSemester: idSemesterSearch,
      page: current,
      size: 10,
    };
    try {
      await TeacherStatisticalAPI.getCountClass(filter).then((response) => {
        setCountClass(response.data.data);
      });
      await TeacherStatisticalAPI.getStatistical(filter).then((response) => {
        setTotalPages(parseInt(response.data.data.totalPages));
        setListClass(response.data.data.data);
        setLoadOne(true);
        setLoading(true);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const featchDataSemester = async () => {
    try {
      setLoading(false);
      await TeacherSemesterAPI.getAllSemesters().then((respone) => {
        dispatch(SetTeacherSemester(respone.data.data));
        setListSemester(respone.data.data);
      });
    } catch (error) {
      console.log(error);
    }
  };
  const featchDataActivity = async (idSemesterSeach) => {
    try {
      await TeacherActivityAPI.getAllActivityByIdSemester(idSemesterSeach).then(
        (respone) => {
          setListActivity(respone.data.data);
        }
      );
    } catch (error) {
      console.log(error);
    }
  };

  const handleSearch = async () => {
    await featchAllMyClass();
  };

  const handleClear = () => {
    setIdSemesterSearch("");
    setIdActivitiSearch("");
    setListActivity([]);
    setClear(true);
  };
  const convertLongToDateTime = (dateLong) => {
    const date = new Date(dateLong);
    const format = `${date.getFullYear()}-${
      date.getMonth() + 1
    }-${date.getDay()}_${date.getHours()}_${date.getMinutes()}_${date.getSeconds()}`;
    return format;
  };

  const handleExport = async (idClass) => {
    setLoadingExport(true);
    try {
      const response = await TeacherStatisticalAPI.export(idClass);
      const blob = new Blob([response.data], {
        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
      });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download =
        "ThongKe_" + convertLongToDateTime(new Date().getTime()) + ".xlsx";
      link.click();
      window.URL.revokeObjectURL(url);
      setLoadingExport(false);
      message.success("Export thành công !");
    } catch (error) {
      console.log(error);
    }
  };

  const dataTable = listClass;
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt - b.stt,
    },
    {
      title: <div style={{ textAlign: "center" }}>Mã lớp</div>,
      dataIndex: "code",
      key: "code",
      sorter: (a, b) => a.code.localeCompare(b.code),
    },
    {
      title: <div style={{ textAlign: "center" }}>Thời gian bắt đầu</div>,
      dataIndex: "startTime",
      key: "startTime",
      sorter: (a, b) => a.startTime - b.startTime,
      render: (text, record) => {
        return <span>{convertLongToDate(text)}</span>;
      },
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Ca học</div>,
      dataIndex: "classPeriod",
      key: "classPeriod",
      sorter: (a, b) => a.classPeriod.localeCompare(b.classPeriod),
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Thời gian</div>,
      dataIndex: "timePeriod",
      key: "timePeriod",
      render: (text, record) => {
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
      },
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Sĩ số</div>,
      dataIndex: "classSize",
      key: "classSize",
      sorter: (a, b) => a.classSize - b.classSize,
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Hoạt động</div>,
      dataIndex: "activity",
      key: "activity",
      sorter: (a, b) => a.activity.localeCompare(b.activity),
    },
    {
      title: <div style={{ textAlign: "center" }}>Cấp độ</div>,
      dataIndex: "level",
      key: "level",
      sorter: (a, b) => a.level.localeCompare(b.level),
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Số nhóm</div>,
      dataIndex: "countTeam",
      key: "countTeam",
      sorter: (a, b) => a.countTeam - b.countTeam,
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Tổng buổi học</div>,
      dataIndex: "countLesson",
      key: "countLesson",
      sorter: (a, b) => a.countLesson - b.countLesson,
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Số buổi nghỉ</div>,
      dataIndex: "countLessonOff",
      key: "countLessonOff",
      sorter: (a, b) => a.countLessonOff - b.countLessonOff,
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Số bài viết</div>,
      dataIndex: "countPost",
      key: "countPost",
      sorter: (a, b) => a.countPost - b.countPost,
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Hành động</div>,
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <>
          <div className="box_icon" style={{ textAlign: "center" }}>
            <Tooltip title="Export chi tiết">
              <FontAwesomeIcon
                icon={faDownload}
                style={{ marginRight: "7px" }}
                className="icon"
                onClick={() => handleExport(record.id)}
              />
            </Tooltip>
          </div>
        </>
      ),
      align: "center",
    },
  ];
  const filterOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };
  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    const format = `${day}/${month}/${year}`;
    return format;
  };
  return (
    <>
      {!loading && <LoadingIndicator />}
      {loadingExport && <LoadingIndicatorNoOverlay />}
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon
              icon={faLineChart}
              style={{ marginRight: "8px" }}
            />{" "}
            Thống kê
          </span>
        </div>
      </div>
      <div className="box-three-dashboad">
        <div className="box-three-son-dashboad">
          <Row>
            <FontAwesomeIcon
              icon={faFilter}
              style={{ fontSize: "20px", paddingRight: "8px" }}
            />
            <span style={{ fontWeight: "400", fontSize: "18px" }}> Bộ lọc</span>
          </Row>
          <hr />
          <div className="title-box-two">
            <Row gutter={24} style={{ margin: "5px 10px 5px 10px" }}>
              <Col span={8}>
                <span>Học kỳ</span>
                <br />
                {listSemester.length > 0 ? (
                  <Select
                    value={idSemesterSearch}
                    onChange={(value) => {
                      setIdSemesterSearch(value);
                    }}
                    showSearch
                    filterOption={filterOptions}
                    style={{
                      width: "100%",
                      margin: "6px 0 10px 0",
                    }}
                  >
                    <Option value="">Chọn 1 học kỳ</Option>
                    {listSemester.map((item) => {
                      return (
                        <Option
                          value={item.id}
                          key={item.id}
                          style={{ width: "auto" }}
                        >
                          {item.name}
                        </Option>
                      );
                    })}
                  </Select>
                ) : (
                  <p>Không có học kỳ</p>
                )}
              </Col>
              <Col span={16}>
                <span>Hoạt động</span>
                <br />
                {listActivity.length > 0 ? (
                  <Select
                    showSearch
                    filterOption={filterOptions}
                    value={idActivitiSearch}
                    onChange={(value) => {
                      setIdActivitiSearch(value);
                    }}
                    style={{
                      width: "100%",
                      margin: "6px 0 10px 0",
                    }}
                  >
                    <Option value="">Tất cả</Option>
                    {listActivity.map((item) => {
                      return (
                        <Option
                          value={item.id}
                          key={item.id}
                          style={{ width: "auto" }}
                        >
                          {item.name} ({convertLongToDate(item.startTime)}
                          {" - "}
                          {convertLongToDate(item.endTime)})
                        </Option>
                      );
                    })}
                  </Select>
                ) : (
                  <p>Không có hoạt động</p>
                )}
              </Col>
            </Row>
            <div style={{ textAlign: "center", paddingBottom: "10px" }}>
              <Button
                className="btn_filter"
                onClick={handleSearch}
                style={{ marginRight: "20px", backgroundColor: "#E2B357" }}
              >
                <FontAwesomeIcon
                  icon={faFilter}
                  style={{ paddingRight: "5px" }}
                />{" "}
                <span>Tìm kiếm</span>
              </Button>
              <Button className="btn_clean" onClick={handleClear}>
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
      <div className="box-four-dashboad">
        <div
          className="box-four-son-dashboad"
          style={{ minHeight: "355px", height: "auto" }}
        >
          <Row gutter={50} style={{ paddingTop: "14px" }}>
            <Col span={18}>
              {" "}
              <div>
                <span style={{ fontSize: "17px", fontWeight: "500" }}>
                  <FontAwesomeIcon
                    icon={faTableList}
                    style={{
                      marginRight: "10px",
                      fontSize: "20px",
                    }}
                  />
                  Danh sách lớp học
                </span>
              </div>
            </Col>
            <Col
              span={6}
              style={{
                fontWeight: "500",
                fontSize: "16px",
                color: "red",
                display: "flex",
                justifyContent: "flex-end",
              }}
            >
              <span style={{ marginRight: "5px" }}>Số lớp dạy:</span>
              <span>
                {" "}
                {countClass.classLesson !== null ? countClass.classLesson : "0"}
                /
                {countClass.classNumber !== null ? countClass.classNumber : "0"}
              </span>
            </Col>
          </Row>
          <div>
            {listClass.length > 0 ? (
              <>
                <div style={{ paddingTop: "15px" }} className="table-teacher">
                  <Table
                    dataSource={dataTable}
                    rowKey="id"
                    columns={columns}
                    pagination={false}
                  />
                </div>
                <div className="pagination-box">
                  <Pagination
                    simple
                    current={current}
                    onChange={(value) => {
                      setCurrent(value);
                    }}
                    total={totalPages * 10}
                  />
                </div>
              </>
            ) : (
              <Empty
                imageStyle={{ height: 60 }}
                description={<span>Không có dữ liệu</span>}
              />
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default TeacherDashboard;
