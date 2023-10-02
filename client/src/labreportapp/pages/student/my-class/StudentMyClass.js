import {
  faChainSlash,
  faEye,
  faFilter,
  faFilterCircleDollar,
  faHome,
} from "@fortawesome/free-solid-svg-icons";
import "./style-student-my-class.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../helper/loading";
import {
  Button,
  Col,
  Input,
  Row,
  Select,
  Table,
  Tooltip,
  Pagination,
  Empty,
} from "antd";
import {
  ControlOutlined,
  QuestionCircleFilled,
  ProjectOutlined,
} from "@ant-design/icons";
import { StMyClassAPI } from "../../../api/student/StMyClassAPI";
import { Link } from "react-router-dom";
import { convertMeetingPeriodToTime } from "../../../helper/util.helper";
import { StLevelAPI } from "../../../api/student/StLevelAPI";

const { Option } = Select;

const StudentMyClass = () => {
  const [loading, setLoading] = useState(false);
  const [semester, setSemester] = useState("");
  const [activity, setActivity] = useState("Không có hoạt động");
  const [code, setCode] = useState("");
  const [classPeriod, setClassPeriod] = useState("");
  const [level, setLevel] = useState("");
  const [listSemester, setListSemester] = useState([]);
  const [listLevel, setListLevel] = useState([]);
  const [listActivity, setListActivity] = useState([]);
  const [listClass, setListClass] = useState([]);

  const loadDataSemester = () => {
    StMyClassAPI.getAllSemesters().then((response) => {
      setListSemester(response.data.data);
      setSemester(response.data.data[0].id);
      setLoading(false);
    });
  };
  const loadDataLevel = () => {
    StLevelAPI.getLevelAll().then((response) => {
      setListLevel(response.data.data);
    });
  };
  useEffect(() => {
    loadDataLevel();
    loadDataSemester();
    document.title = "Lớp của tôi | Lab-Report-App";
  }, []);

  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      sorter: (a, b) => a.stt - b.stt,
      key: "stt",
    },
    {
      title: "Mã lớp",
      dataIndex: "code",
      sorter: (a, b) => a.code.localeCompare(b.code),
      key: "code",
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
      dataIndex: "classPeriod",
      key: "classPeriod",
      sorter: (a, b) => a.classPeriod - b.classPeriod,
      render: (text, record) => {
        if (record.classPeriod == null) {
          return <span>Chưa có</span>;
        } else {
          return <span>{record.classPeriod + 1}</span>;
        }
      },
    },
    {
      title: "Thời gian",
      dataIndex: "timePeriod",
      key: "timePeriod",
      render: (text, record) => {
        if (record.classPeriod == null) {
          return <span>Chưa có</span>;
        } else {
          return <span>{convertMeetingPeriodToTime(record.classPeriod)}</span>;
        }
      },
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
          return (
            <span>{record.userNameTeacher + " - " + record.nameTeacher}</span>
          );
        }
      },
    },
    {
      title: "Cấp độ",
      dataIndex: "level",
      key: "level",
    },
    {
      title: "Hoạt động",
      dataIndex: "nameActivity",
      key: "nameActivity",
    },
    {
      title: "Hành động",
      dataIndex: "action",
      key: "action",
      render: (text, record) => (
        <>
          <Link to={`/student/my-class/post/${record.id}`}>
            <Tooltip title="Xem chi tiết lớp học">
              <FontAwesomeIcon icon={faEye} className="icon" />
            </Tooltip>
          </Link>
        </>
      ),
    },
  ];

  const loadDataActivity = () => {
    StMyClassAPI.getAllActivityByIdSemester(semester).then((response) => {
      setListActivity(response.data.data);
      setActivity("");
      loadDataClass("");
    });
  };

  useEffect(() => {
    if (semester === "") {
      setListActivity([]);
      setActivity("Không có hoạt động");
    } else {
      loadDataActivity();
    }
  }, [semester]);

  const handleChangeSemester = (e) => {
    setSemester(e);
  };

  const handleClickFilter = () => {
    loadDataClass();
  };

  const filterOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };

  const loadDataClass = (activityParams) => {
    setLoading(true);
    let filter = {
      semesterId: semester,
      activityId: activityParams !== "" ? activity : activityParams,
      code: code,
      classPeriod: classPeriod === "" ? null : parseInt(classPeriod),
      level: level,
    };
    StMyClassAPI.getAllClass(filter).then((response) => {
      setListClass(response.data.data);
      setLoading(false);
    });
  };

  const clearData = () => {
    setSemester("");
    setActivity("Không có hoạt động");
    setCode("");
    setClassPeriod("");
    setLevel("");
  };

  return (
    <div className="my_class_my">
      {loading && <LoadingIndicator />}
      <div>
        <span style={{ fontSize: "20px" }}>
          <FontAwesomeIcon icon={faHome} /> Lớp của tôi
        </span>
      </div>
      <div className="filter_my_class">
        <FontAwesomeIcon icon={faFilter} style={{ fontSize: "18px" }} />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <div className="content_filter">
          <Row gutter={16} style={{ marginBottom: "10px", paddingTop: "10px" }}>
            <Col span={6}>
              <span>Học kỳ:</span>

              <br />
              <Select
                showSearch
                filterOption={filterOptions}
                style={{
                  width: "263px",
                  minWidth: "120px",
                  maxWidth: "263px",
                  margin: "6px 0 10px 0",
                }}
                onChange={(e) => {
                  handleChangeSemester(e);
                }}
                value={semester}
              >
                <Option value="">Chọn học kỳ</Option>
                {listSemester.length > 0 &&
                  listSemester.map((item) => (
                    <Option value={item.id} key={item.id}>
                      {item.name}
                    </Option>
                  ))}
              </Select>
            </Col>
            <Col span={14}>
              <span>Hoạt động:</span>

              <br />
              <Select
                showSearch
                filterOption={filterOptions}
                style={{
                  width: "868px",
                  margin: "6px 0 10px 0",
                }}
                value={activity}
                onChange={(e) => {
                  setActivity(e);
                }}
              >
                <Option value="">Tất cả</Option>
                {listActivity.length > 0 &&
                  listActivity.map((item) => (
                    <Option value={item.id} key={item.id}>
                      {item.name}
                    </Option>
                  ))}
              </Select>
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "0px", paddingTop: "10px" }}>
            <Col span={6}>
              <span>Mã lớp:</span> <br />
              <Input
                onChange={(e) => {
                  setCode(e.target.value);
                }}
                value={code}
                style={{ width: "94%", marginTop: "6px" }}
                type="text"
                placeholder="Nhập mã lớp"
              />
            </Col>

            <Col span={6}>
              <span>Ca học:</span>

              <br />
              <Select
                style={{ width: "94%", marginTop: "6px" }}
                onChange={(e) => {
                  setClassPeriod(e);
                }}
                showSearch
                filterOption={filterOptions}
                value={classPeriod}
              >
                <Option value="">Tất cả</Option>
                <Option value="0">Ca 1</Option>
                <Option value="1">Ca 2</Option>
                <Option value="2">Ca 3</Option>
                <Option value="3">Ca 4</Option>
                <Option value="4">Ca 5</Option>
                <Option value="5">Ca 6</Option>
                <Option value="6">Ca 7</Option>
                <Option value="7">Ca 8</Option>
                <Option value="8">Ca 9</Option>
                <Option value="9">Ca 10</Option>
              </Select>
            </Col>
            <Col span={6}>
              <span>Level:</span> <br />
              <Select
                style={{ width: "94%", marginTop: "6px" }}
                onChange={(e) => {
                  setLevel(e);
                }}
                showSearch
                filterOption={filterOptions}
                value={level}
              >
                {" "}
                <Option value={""}>Tất cả</Option>
                {listLevel.length > 0 &&
                  listLevel.map((item) => (
                    <Option value={item.id} key={item.id}>
                      {item.name}
                    </Option>
                  ))}
              </Select>
            </Col>
          </Row>
        </div>
        <div className="box_btn_filter">
          <Button className="btn_filter" onClick={handleClickFilter}>
            <FontAwesomeIcon
              icon={faFilterCircleDollar}
              style={{ marginRight: 5 }}
            />{" "}
            Tìm kiếm
          </Button>
          <Button
            className="btn_clear"
            style={{ backgroundColor: "rgb(38, 144, 214)" }}
            onClick={clearData}
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
      <div className="table_myclass">
        <div className="title_table_myclass">
          <div style={{ float: "left" }}>
            {" "}
            <FontAwesomeIcon icon={faHome} style={{ fontSize: "18px" }} />
            <span style={{ fontSize: "18px", fontWeight: "500" }}>
              {" "}
              Danh sách lớp học
            </span>
          </div>
        </div>
        <br />
        {listClass.length > 0 && (
          <div style={{ marginTop: "25px" }}>
            {" "}
            <Table dataSource={listClass} columns={columns} />
          </div>
        )}
        {listClass.length === 0 && (
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
                description={
                  <span style={{ color: "#007bff" }}>
                    Không tìm thấy lớp học nào !
                  </span>
                }
              />{" "}
            </p>
          </>
        )}
      </div>
    </div>
  );
};

export default StudentMyClass;
