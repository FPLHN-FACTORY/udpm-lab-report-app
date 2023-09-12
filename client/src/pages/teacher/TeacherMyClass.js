import { toast } from "react-toastify";
import "./styleTeacherMyClass.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faHome } from "@fortawesome/free-solid-svg-icons";
import { giangVienCurrent } from "../../helper/inForUser";
import LoadingIndicator from "../../helper/loading";
import { QuestionCircleFilled, ProjectOutlined } from "@ant-design/icons";
import {
  Row,
  Col,
  Select,
  Input,
  Button,
  Table,
  Pagination,
  Tooltip,
} from "antd";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { useAppDispatch, useAppSelector } from "../../app/hook";
import { TeacherMyClassAPI } from "../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherSemesterAPI } from "../../api/teacher/semester/TeacherSemester.api";
import { TeacherActivityAPI } from "../../api/teacher/activity/TeacherActivity.api";
import { SetTeacherSemester } from "../../app/teacher/semester/teacherSemesterSlice.reduce";
import {
  GetTeacherMyClass,
  SetTeacherMyClass,
} from "../../app/teacher/my-class/teacherMyClassSlice.reduce";
import { convertMeetingPeriodToTime } from "../../helper/util.helper";

const { Option } = Select;

const TeacherMyClass = () => {
  const dispatch = useAppDispatch();
  const [listSemester, setListSemester] = useState([]);
  const [listActivity, setListActivity] = useState([]);
  const [listMyClass, setListMyClass] = useState([]);
  const [idSemesterSeach, setIdSemesterSearch] = useState("");
  const [idActivitiSearch, setIdActivitiSearch] = useState("");
  const [codeSearch, setCodeSearch] = useState("");
  const [nameSearch, setNameSearch] = useState("");
  const [classPeriodSearch, setClassPeriodSearch] = useState("");
  const [levelSearch, setLevelSearch] = useState("");
  const [clear, setClear] = useState(false);
  const listClassPeriod = [];

  for (let i = 1; i <= 10; i++) {
    listClassPeriod.push("" + i);
  }
  const [current, setCurrent] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - Lớp của tôi";
    setIdSemesterSearch("");
    setIdActivitiSearch("");
    setCodeSearch("");
    setNameSearch("");
    setClassPeriodSearch("");
    setLevelSearch("");
    dispatch(SetTeacherMyClass([]));
    featchDataSemester();
    featchAllMyClass(giangVienCurrent);
  }, []);

  useEffect(() => {
    featchDataActivity(idSemesterSeach);
    setIdActivitiSearch("");
  }, [idSemesterSeach]);

  useEffect(() => {
    featchAllMyClass(giangVienCurrent);
  }, [current]);

  useEffect(() => {
    featchAllMyClass(giangVienCurrent);
    setClear(false);
  }, [clear]);

  const featchAllMyClass = async (giangVienCurrent) => {
    setLoading(false);
    let filter = {
      idTeacher: giangVienCurrent.id,
      idActivity: idActivitiSearch,
      idSemester: idSemesterSeach,
      code: codeSearch,
      classPeriod: classPeriodSearch,
      level: levelSearch,
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
      alert("Vui lòng F5 lại trang !");
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
          setIdSemesterSearch("null");
        }
        setListSemester(respone.data.data);
        setLoading(true);
      });
    } catch (error) {
      alert("Vui lòng F5 lại trang !");
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
      alert("Vui lòng F5 lại trang !");
    }
  };

  const handleSearch = async () => {
    await featchAllMyClass(giangVienCurrent);
  };

  const handleClear = () => {
    if (listSemester.length > 0) {
      setIdSemesterSearch(listSemester[0].id);
    } else {
      setIdSemesterSearch("null");
    }
    setIdActivitiSearch("");
    setCodeSearch("");
    setNameSearch("");
    setClassPeriodSearch("");
    setLevelSearch("");
    toast.success("Hủy bộ lọc thành công !");
    setClear(true);
  };
  const data = useAppSelector(GetTeacherMyClass);
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt - b.stt,
    },

    {
      title: "Mã lớp",
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
      title: "Ca học",
      dataIndex: "classPeriod",
      key: "classPeriod",
      render: (text) => <span>{text + 1}</span>,
      sorter: (a, b) => a.classPeriod - b.classPeriod,
    },
    {
      title: "Thời gian",
      dataIndex: "timePeriod",
      key: "timePeriod",
      render: (text, record) => {
        return <span>{convertMeetingPeriodToTime(record.classPeriod)}</span>;
      },
    },
    {
      title: "Level",
      dataIndex: "level",
      key: "level",
      render: (text) => <span>{text + 1}</span>,
      sorter: (a, b) => a.level - b.level,
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <>
          <div className="box_icon">
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
            <Row
              gutter={16}
              style={{ marginBottom: "15px", paddingTop: "20px" }}
            >
              <Col span={6}>
                <span>Học kỳ</span>
                <QuestionCircleFilled
                  style={{ paddingLeft: "12px", fontSize: "15px" }}
                />
                <br />
                <Select
                  value={idSemesterSeach}
                  onChange={(value) => {
                    setIdSemesterSearch(value);
                  }}
                  showSearch
                  filterOption={filterOptions}
                  style={{
                    width: "263px",
                    minWidth: "120px",
                    maxWidth: "263px",
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
              </Col>
              <Col span={14}>
                <span>Hoạt động</span>
                <QuestionCircleFilled
                  style={{ paddingLeft: "12px", fontSize: "15px" }}
                />{" "}
                <br />
                <Select
                  showSearch
                  filterOption={filterOptions}
                  value={idActivitiSearch}
                  onChange={(value) => {
                    setIdActivitiSearch(value);
                  }}
                  style={{
                    width: "868px",
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
              </Col>
            </Row>
            <Row
              gutter={16}
              style={{ marginBottom: "15px", paddingTop: "20px" }}
            >
              <Col span={6}>
                <span>Mã lớp</span>{" "}
                <QuestionCircleFilled
                  style={{ paddingLeft: "12px", fontSize: "15px" }}
                />{" "}
                <br />
                <Input
                  style={{ width: "92%", marginTop: "6px" }}
                  type="text"
                  placeholder="Nhập mã lớp"
                  value={codeSearch}
                  onChange={(e) => {
                    setCodeSearch(e.target.value);
                  }}
                />
              </Col>

              <Col span={6}>
                <span>Ca học</span>
                <QuestionCircleFilled
                  style={{ paddingLeft: "12px", fontSize: "15px" }}
                />{" "}
                <br />
                <Select
                  showSearch
                  filterOption={filterOptions}
                  value={classPeriodSearch}
                  onChange={(value) => {
                    setClassPeriodSearch(value);
                  }}
                  style={{ width: "92%", marginTop: "6px" }}
                >
                  <Option value="">Tất cả</Option>
                  {listClassPeriod.map((value) => {
                    return (
                      <Option value={value} key={value}>
                        {value}
                      </Option>
                    );
                  })}
                </Select>
              </Col>
              <Col span={6}>
                <span>Level</span>{" "}
                <QuestionCircleFilled
                  style={{ paddingLeft: "12px", fontSize: "15px" }}
                />{" "}
                <br />
                <Select
                  showSearch
                  filterOption={filterOptions}
                  value={levelSearch}
                  onChange={(value) => {
                    setLevelSearch(value);
                  }}
                  style={{ width: "92%", marginTop: "6px" }}
                >
                  <Option value="">Tất cả</Option>
                  <Option value="0">1</Option>
                  <Option value="1">2</Option>
                  <Option value="2">3</Option>
                </Select>
              </Col>
            </Row>
            <div className="box_btn_filter">
              <Button className="btn_filter" onClick={handleSearch}>
                Tìm kiếm
              </Button>
              <Button className="btn_clear" onClick={handleClear}>
                Làm mới bộ lọc
              </Button>
            </div>
          </div>
        </div>
      </div>
      <div className="box-four">
        <div className="box-four-son">
          <div className="title-table">
            <div>
              {" "}
              <span style={{ fontSize: "17px", fontWeight: "500" }}>
                {" "}
                <ProjectOutlined
                  style={{ marginRight: "10px", fontSize: "24px" }}
                />
                Danh sách lớp học
              </span>
            </div>
          </div>
          <div>
            {listMyClass.length > 0 ? (
              <>
                <div className="table">
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
              <>
                <p
                  style={{
                    textAlign: "center",
                    marginTop: "100px",
                    fontSize: "15px",
                  }}
                >
                  Không có lớp học
                </p>
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default TeacherMyClass;
