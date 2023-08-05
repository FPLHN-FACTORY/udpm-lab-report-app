import "./styleTeacherMyClass.css";
import { giangVienCurrent } from "../../../helper/inForUser";
import LoadingIndicator from "../../../helper/loading";
import {
  ControlOutlined,
  QuestionCircleFilled,
  ProjectOutlined,
} from "@ant-design/icons";
import { Row, Col, Select, Input, Button, Table, Pagination } from "antd";
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

  const [idSemesterSeach, setIdSemesterSeach] = useState("");
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
    setIdSemesterSeach("");
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
        console.log(respone.data.data.data);
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

  const handleSearch = () => {
    featchAllMyClass(giangVienCurrent);
  };
  const handleClear = () => {
    setIdSemesterSeach("");
    setIdActivitiSearch("");
    setCodeSearch("");
    setNameSearch("");
    setClassPeriodSearch("");
    setLevelSearch("");
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
          <Button style={{ fontSize: "16px" }}>Lịch dạy hôm nay</Button>
          <Link to="/" className="buttonChange" style={{ fontSize: "16px" }}>
            Lớp của tôi
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
                  setIdSemesterSeach(value);
                }}
                style={{
                  width: "263px",
                  minWidth: "120px",
                  maxWidth: "263px",
                  margin: "6px 0 10px 0",
                }}
              >
                <Option value="">Tất cả</Option>
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
            <span style={{ fontSize: "18px", fontWeight: "500" }}>
              {" "}
              <ProjectOutlined
                style={{ marginRight: "10px", fontSize: "26px" }}
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
                />{" "}
              </div>
            </>
          ) : (
            <>
              <p style={{ textAlign: "center", marginTop: "100px" }}>
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
