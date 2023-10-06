import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-teacher-dashboard.css";
import {
  faCodeCompare,
  faDownload,
  faFilter,
  faLineChart,
} from "@fortawesome/free-solid-svg-icons";
import {
  Button,
  Col,
  Empty,
  Pagination,
  Row,
  Select,
  Statistic,
  Table,
  Tooltip,
} from "antd";
import { useState } from "react";
import { useAppDispatch } from "../../../app/hook";
import { TeacherSemesterAPI } from "../../../api/teacher/semester/TeacherSemester.api";
import { SetTeacherSemester } from "../../../app/teacher/semester/teacherSemesterSlice.reduce";
import { TeacherActivityAPI } from "../../../api/teacher/activity/TeacherActivity.api";
import { useEffect } from "react";
import { convertMeetingPeriodToTime } from "../../../helper/util.helper";
import { TeacherStatisticalAPI } from "../../../api/teacher/statistical/TeacherStatistical.api";
import LoadingIndicator from "../../../helper/loading";
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
  const [semesterOne, setSemesterOne] = useState(null);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Thống kê";
    featchDataSemester();
  }, []);

  useEffect(() => {
    featchAllMyClass();
  }, [current]);

  useEffect(() => {
    if (loadOne) {
      const currentTime = new Date();
      const selectedObject = listSemester.find((item) => {
        const startTime = new Date(item.startTime).getTime();
        const endTime = new Date(item.endTime).getTime();
        return currentTime >= startTime && currentTime <= endTime;
      });
      if (selectedObject !== undefined) {
        setSemesterOne(selectedObject);
        setIdSemesterSearch(selectedObject.id);
        setIdActivitiSearch("");
      } else {
        setIdSemesterSearch("");
        setIdActivitiSearch("null");
      }
      setLoadOne(true);
    }
  }, [loadOne]);
  useEffect(() => {
    featchDataActivity(idSemesterSearch);
    setIdActivitiSearch("");
  }, [idSemesterSearch]);
  useEffect(() => {
    featchDataActivity(idSemesterSearch);
  }, [idSemesterSearch]);

  useEffect(() => {
    if (clear) {
      featchAllMyClass();
    }
    setClear(false);
  }, [clear]);
  const featchAllMyClass = async () => {
    setLoading(false);
    const currentTime = new Date();
    const selectedObject = listSemester.find((item) => {
      const startTime = new Date(item.startTime).getTime();
      const endTime = new Date(item.endTime).getTime();
      return currentTime >= startTime && currentTime <= endTime;
    });
    if (selectedObject !== undefined && idSemesterSearch === "") {
      setIdSemesterSearch(selectedObject.id);
      setIdActivitiSearch("");
    }
    let filter = {
      idActivity: idActivitiSearch,
      idSemester: idSemesterSearch,
      page: current,
      size: 10,
    };
    if (selectedObject === undefined && idSemesterSearch === "") {
      filter = {
        idActivity: "null",
        idSemester: idSemesterSearch,
        page: current,
        size: 10,
      };
    }
    try {
      await TeacherStatisticalAPI.getStatistical(filter).then((respone) => {
        setTotalPages(parseInt(respone.data.data.totalPages));
        setListClass(respone.data.data.data);
        setLoading(true);
        setLoadOne(true);
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
        if (respone.data.data.length > 0) {
          setIdSemesterSearch(respone.data.data[0].id);
        } else {
          setIdSemesterSearch("");
        }
        setListSemester(respone.data.data);
        setLoading(true);
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
          setLoading(true);
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
    const currentTime = new Date();
    const selectedObject = listSemester.find((item) => {
      const startTime = new Date(item.startTime).getTime();
      const endTime = new Date(item.endTime).getTime();
      return currentTime >= startTime && currentTime <= endTime;
    });
    if (selectedObject !== undefined) {
      setIdSemesterSearch(selectedObject.id);
      setIdActivitiSearch("");
    } else {
      setIdSemesterSearch("");
      setIdActivitiSearch("null");
      setListActivity([]);
    }
    setClear(true);
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
      render: (text) => <span>{text + 1}</span>,
      sorter: (a, b) => a.classPeriod - b.classPeriod,
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
      title: <div style={{ textAlign: "center" }}>Thời gian</div>,
      dataIndex: "timePeriod",
      key: "timePeriod",
      render: (text, record) => {
        return <span>{convertMeetingPeriodToTime(record.classPeriod)}</span>;
      },
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
            <Tooltip title="Export chi tiết lớp học">
              <FontAwesomeIcon
                icon={faDownload}
                style={{ marginRight: "7px" }}
                className="icon"
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
      {/* <div className="box-general">
        <div className="heading-box">
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon
              icon={faLineChart}
              style={{ marginRight: "8px" }}
            />{" "}
            Thống kê
          </span>
        </div>
        <div className="box-son-general"></div>
      </div> */}
      <div className="box-dash-board">
        <Row>
          {" "}
          <FontAwesomeIcon
            icon={faFilter}
            style={{ fontSize: "20px", paddingRight: "8px" }}
          />
          <span style={{ fontWeight: "400", fontSize: "18px" }}> Bộ lọc</span>
        </Row>
        <div className="title-box-two" style={{ paddingRight: "30px" }}>
          <Row gutter={24} style={{ marginBottom: "5px", paddingTop: "5px" }}>
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
                      <Option value={item.id} key={item.id} title={item.name}>
                        {item.name}
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
      <div
        className="box-dash-board"
        style={{ marginTop: "10px", minHeight: "56%" }}
      >
        <Row>
          <Col span={22}></Col>
          <Col span={2}>
            <Statistic title="Số lớp dạy" value={93} suffix="/ 100" />
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
    </>
  );
};

export default TeacherDashboard;
