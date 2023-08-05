import "./style-teacher-my-class.css";
import { giangVienCurrent } from "../../../helper/inForUser";
import LoadingIndicator from "../../../helper/loading";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  ControlOutlined,
  QuestionCircleFilled,
  ProjectOutlined,
} from "@ant-design/icons";
import { Row, Col, Select, Input, Button, Table, Pagination } from "antd";
import {
  faFilter,
  faEye,
  faPenToSquare,
  faPlus,
} from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import { TeacherMyClassAPI } from "../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherSemesterAPI } from "../../../api/teacher/semester/TeacherSemester.api";
import { TeacherActivityAPI } from "../../../api/teacher/activity/TeacherActivity.api";
import {
  GetTeacherSemester,
  SetTeacherSemester,
} from "../../../app/teacher/semester/teacherSemesterSlice.reduce";
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
  const [current, setCurrent] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    document.title = "Bảng điều khiển";
    dispatch(SetTeacherMyClass([]));
    featchDataSemester();
    featchAllMyClass(giangVienCurrent);
  }, []);

  useEffect(() => {
    featchDataActivity(idSemesterSeach);
    setIdActivitiSearch("");
  }, [idSemesterSeach]);

  useEffect(() => {
    featchDataActivity(idSemesterSeach);
  }, [idActivitiSearch]);

  useEffect(() => {
    featchAllMyClass(giangVienCurrent);
  }, [idSemesterSeach, idActivitiSearch, current]);

  const featchAllMyClass = async (giangVienCurrent) => {
    setLoading(false);
    let filter = {
      idTeacher: giangVienCurrent.id,
      idActivity: idActivitiSearch,
      idSemester: idSemesterSeach,
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
        setListSemester(respone.data.data);
        setLoading(true);
      });
    } catch (error) {
      alert("Vui lòng F5 lại trang !");
    }
  };

  const featchDataActivity = async (idSemesterSeach) => {
    try {
      setLoading(false);
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
            <Col span={8}>
              <span>Chọn học kỳ</span>
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
                  width: "auto",
                  minWidth: "130px",
                  margin: "10px 0 10px 0",
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
            <Col span={8}>
              <span>Chọn hoạt động:</span>
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
                  width: "auto",
                  minWidth: "100px",
                  maxWidth: "200%",
                  margin: "10px 0 10px 0",
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
        </div>
      </div>
      <div className="table-teacher-my-class">
        <div className="title-table">
          <div>
            {" "}
            <span style={{ fontSize: "18px", fontWeight: "500" }}>
              {" "}
              <ProjectOutlined
                style={{ marginRight: "7px", fontSize: "26px" }}
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
