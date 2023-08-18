import "./styleTeacherMyClass.css";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye } from "@fortawesome/free-solid-svg-icons";
import { giangVienCurrent } from "../../../helper/inForUser";
import LoadingIndicator from "../../../helper/loading";
import {
  ControlOutlined,
  QuestionCircleFilled,
  ProjectOutlined,
} from "@ant-design/icons";
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
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import { TeacherMyClassAPI } from "../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherSemesterAPI } from "../../../api/teacher/semester/TeacherSemester.api";
import { TeacherActivityAPI } from "../../../api/teacher/activity/TeacherActivity.api";
import { SetTeacherSemester } from "../../../app/teacher/semester/teacherSemesterSlice.reduce";
import {
  GetTeacherMyClass,
  SetTeacherMyClass,
} from "../../../app/teacher/my-class/teacherMyClassSlice.reduce";

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
    document.title = "Bảng điều khiển";
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
      name: nameSearch,
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
      alert("Vui lòng F5 lại trang do featchData activiti!");
    }
  };

  const handleSearch = async () => {
    await featchAllMyClass(giangVienCurrent);
    toast.success("Tìm kiếm thành công !");
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
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt - b.stt,
      width: "3%",
    },

    {
      title: "Mã lớp",
      dataIndex: "code",
      key: "code",
      sorter: (a, b) => a.code.localeCompare(b.code),
      width: "7%",
    },
    {
      title: "Tên lớp",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Thời gian",
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
      width: "100px",
    },
    {
      title: "Ca học",
      dataIndex: "classPeriod",
      key: "classPeriod",
      sorter: (a, b) => a.descriptions.localeCompare(b.descriptions),
      width: "9%",
    },
    {
      title: "Level",
      dataIndex: "level",
      key: "level",
      sorter: (a, b) => a.level - b.level,
      width: "7%",
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <>
          <div className="box_icon">
            <Link
              to={`/teacher/my-class/students/${record.id}`}
              className="btn btn-success ml-4"
            >
              <Tooltip title="Xem chi tiết lớp học">
                <FontAwesomeIcon icon={faEye} className="icon" />
              </Tooltip>
            </Link>
          </div>
        </>
      ),
      width: "105px",
    },
  ];

  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="title-teacher-my-class">
        <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span style={{ marginLeft: "10px", fontWeight: "500" }}>
            Bảng điều khiển
          </span>
        </span>
      </div>
      <div className="filter-teacher-my-class">
        <div className="button-menu-teacher">
          <Link style={{ fontSize: "17px" }} className="custom-link">
            Lịch dạy hôm nay &nbsp;
          </Link>
          <Link
            to="/"
            id="menu-checked"
            style={{ fontSize: "17px", paddingLeft: "10px" }}
          >
            Lớp của tôi &nbsp;
          </Link>

          <hr />
        </div>
        <div className="menu-teacher-search">
          <Row gutter={16} style={{ marginBottom: "15px", paddingTop: "20px" }}>
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
          <Row gutter={16} style={{ marginBottom: "15px", paddingTop: "20px" }}>
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
              <span>Tên lớp</span>{" "}
              <QuestionCircleFilled
                style={{ paddingLeft: "12px", fontSize: "15px" }}
              />
              {""} <br />
              <Input
                style={{ width: "92%", marginTop: "6px" }}
                placeholder="Nhập tên lớp"
                type="text"
                value={nameSearch}
                onChange={(e) => {
                  setNameSearch(e.target.value);
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
                value={levelSearch}
                onChange={(value) => {
                  setLevelSearch(value);
                }}
                style={{ width: "92%", marginTop: "6px" }}
              >
                <Option value="">Tất cả</Option>
                <Option value="1">1</Option>
                <Option value="2">2</Option>
                <Option value="3">3</Option>
              </Select>
            </Col>
          </Row>
          <div className="box_btn_filter">
            <Button className="btn_filter" onClick={handleSearch}>
              Tìm kiếm
            </Button>
            <Button className="btn_clear" onClick={handleClear}>
              Làm mới
            </Button>
          </div>
        </div>
      </div>
      <div className="table-teacher-my-class">
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
              <div className="pagination_box">
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
    </>
  );
};

export default TeacherMyClass;
