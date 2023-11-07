import "./styleTeacherMyClass.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCodeCompare,
  faEye,
  faFilter,
  faHome,
  faTableList,
} from "@fortawesome/free-solid-svg-icons";
import LoadingIndicator from "../../helper/loading";
import {
  Row,
  Col,
  Select,
  Input,
  Button,
  Table,
  Pagination,
  Tooltip,
  Empty,
} from "antd";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { useAppDispatch, useAppSelector } from "../../app/hook";
import { TeacherMyClassAPI } from "../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherSemesterAPI } from "../../api/teacher/semester/TeacherSemester.api";
import { TeacherActivityAPI } from "../../api/teacher/activity/TeacherActivity.api";
import { TeacherLevelAPI } from "../../api/teacher/level/TeacherLevel.api";
import { SetTeacherSemester } from "../../app/teacher/semester/teacherSemesterSlice.reduce";
import {
  GetTeacherMyClass,
  SetTeacherMyClass,
} from "../../app/teacher/my-class/teacherMyClassSlice.reduce";
import { convertHourAndMinuteToString } from "../../helper/util.helper";
import { TeacherMeetingPeriodAPI } from "../../api/teacher/meeting-period/TeacherMeetingPeriod.api";
const { Option } = Select;

const TeacherMyClass = () => {
  const dispatch = useAppDispatch();
  const [listSemester, setListSemester] = useState([]);
  const [listActivity, setListActivity] = useState([]);
  const [listLevel, setListLevel] = useState([]);
  const [listMeetingPeriod, setListMeetingPeriod] = useState([]);
  const [listMyClass, setListMyClass] = useState([]);
  const [idSemesterSearch, setIdSemesterSearch] = useState("");
  const [idActivitiSearch, setIdActivitiSearch] = useState("");
  const [idLevelSearch, setIdLevelSearch] = useState("");
  const [codeSearch, setCodeSearch] = useState("");
  const [classPeriodSearch, setClassPeriodSearch] = useState("");
  const [clear, setClear] = useState(false);
  const [current, setCurrent] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - Lớp của tôi";
    setIdSemesterSearch("");
    setIdActivitiSearch("");
    setCodeSearch("");
    setClassPeriodSearch("");
    setIdLevelSearch("");
    dispatch(SetTeacherMyClass([]));
    featchDataLevel();
    featchDataSemester();
    featchDataMeetingPeriod();
  }, []);

  useEffect(() => {
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
      code: codeSearch,
      classPeriod: classPeriodSearch,
      level: idLevelSearch,
      page: current,
      size: 10,
    };

    try {
      await TeacherMyClassAPI.getAllMyClass(filter).then((respone) => {
        dispatch(SetTeacherMyClass(respone.data.data));
        setTotalPages(parseInt(respone.data.data.totalPages));
        setListMyClass(respone.data.data.data);
        setLoading(true);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const featchDataSemester = async () => {
    try {
      await TeacherSemesterAPI.getAllSemesters().then((respone) => {
        dispatch(SetTeacherSemester(respone.data.data));
        setListSemester(respone.data.data);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const featchDataLevel = async () => {
    try {
      await TeacherLevelAPI.getAllLevel().then((respone) => {
        setListLevel(respone.data.data);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const featchDataActivity = async (idSemesterSearch) => {
    try {
      await TeacherActivityAPI.getAllActivityByIdSemester(
        idSemesterSearch
      ).then((respone) => {
        setListActivity(respone.data.data);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const featchDataMeetingPeriod = async () => {
    try {
      await TeacherMeetingPeriodAPI.getPeriod().then((respone) => {
        setListMeetingPeriod(respone.data.data);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const handleSearch = async () => {
    await featchAllMyClass();
  };

  const handleClear = () => {
    setIdActivitiSearch("");
    setIdSemesterSearch("");
    setCodeSearch("");
    setClassPeriodSearch("");
    setIdLevelSearch("");
    setListActivity([]);
    setClear(true);
  };
  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    const format = `${day}/${month}/${year}`;
    return format;
  };

  const data = useAppSelector(GetTeacherMyClass);
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt - b.stt,
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Lớp</div>,
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
      title: <div style={{ textAlign: "center" }}>Hành động</div>,
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <>
          <div className="box_icon" style={{ textAlign: "center" }}>
            <Link
              to={`/teacher/my-class/post/${record.id}`}
              className="btn btn-success ml-4"
            >
              <Tooltip title="Xem chi tiết lớp học">
                <FontAwesomeIcon icon={faEye} className="icon" />
              </Tooltip>
            </Link>
          </div>
        </>
      ),
      align: "center",
    },
  ];
  const filterOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };
  return (
    <div>
      {!loading && <LoadingIndicator />}
      <div className="box-one">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <FontAwesomeIcon
              icon={faHome}
              style={{ color: "#00000", fontSize: "23px" }}
            />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>
          </span>
        </Link>
      </div>
      <div className="box-three">
        <div className="box-three-son">
          <div className="button-menu">
            <Link
              to="/teacher/schedule-today"
              style={{ fontSize: "17px", fontWeight: "bold" }}
              className="custom-link"
            >
              &nbsp; LỊCH DẠY HÔM NAY &nbsp;
            </Link>
            <Link
              to="/teacher/my-class"
              id="menu-checked"
              style={{
                fontSize: "17px",
                paddingLeft: "10px",
                fontWeight: "bold",
              }}
            >
              LỚP CỦA TÔI &nbsp;
            </Link>
            <hr />
          </div>
          <div className="title-box-two">
            <Row gutter={24} style={{ padding: "5px 2% 0" }}>
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
                        <Option value={item.id} key={item.id} title={item.name}>
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
            <Row gutter={24} style={{ padding: "5px 2% 0" }}>
              <Col span={8}>
                <span>Mã lớp</span>

                <br />
                <Input
                  style={{ width: "100%", marginTop: "6px" }}
                  type="text"
                  placeholder="Nhập mã lớp"
                  value={codeSearch}
                  onChange={(e) => {
                    setCodeSearch(e.target.value);
                  }}
                />
              </Col>
              <Col span={8}>
                <span>Ca học</span>
                <br />
                <Select
                  showSearch
                  filterOption={filterOptions}
                  value={classPeriodSearch}
                  onChange={(value) => {
                    setClassPeriodSearch(value);
                  }}
                  style={{ width: "100%", marginTop: "6px" }}
                >
                  <Option value="">Tất cả</Option>
                  {listMeetingPeriod.map((item) => {
                    return (
                      <Option value={item.id} key={item.id}>
                        {item.name} (
                        {convertHourAndMinuteToString(
                          item.startHour,
                          item.startMinute,
                          item.endHour,
                          item.endMinute
                        )}
                        )
                      </Option>
                    );
                  })}
                </Select>
              </Col>
              <Col span={8}>
                <span>Cấp độ</span>
                <br />
                {listLevel.length > 0 ? (
                  <Select
                    showSearch
                    filterOption={filterOptions}
                    value={idLevelSearch}
                    onChange={(value) => {
                      setIdLevelSearch(value);
                    }}
                    style={{ width: "100%", marginTop: "6px" }}
                  >
                    <Option value="">Tất cả</Option>
                    {listLevel.map((item) => {
                      return (
                        <Option value={item.id} key={item.id} title={item.name}>
                          {item.name}
                        </Option>
                      );
                    })}
                  </Select>
                ) : (
                  <p>Không có cấp độ</p>
                )}
              </Col>
            </Row>
            <div className="box-btn">
              <Button
                className="btn_filter"
                onClick={handleSearch}
                style={{ marginRight: "15px", backgroundColor: "#E2B357" }}
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
      <div className="box-four">
        <div className="box-four-son" style={{ minHeight: "262px" }}>
          <div className="title-table">
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
          </div>
          <div>
            {listMyClass.length > 0 ? (
              <>
                <div style={{ paddingTop: "15px" }} className="table-teacher">
                  <Table
                    dataSource={data}
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
                imageStyle={{ height: "60px" }}
                description={<span>Không có dữ liệu</span>}
              />
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default TeacherMyClass;
