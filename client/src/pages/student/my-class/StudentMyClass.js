import { faEye, faFilter, faHome } from "@fortawesome/free-solid-svg-icons";
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
} from "antd";
import {
  ControlOutlined,
  QuestionCircleFilled,
  ProjectOutlined,
} from "@ant-design/icons";
import { StMyClassAPI } from "../../../api/student/StMyClassAPI";
import { sinhVienCurrent } from "../../../helper/inForUser";
import { Link } from "react-router-dom";
import { convertMeetingPeriodToTime } from "../../../helper/util.helper";

const { Option } = Select;

const StudentMyClass = () => {
  const [loading, setLoading] = useState(false);
  const [semester, setSemester] = useState("");
  const [activity, setActivity] = useState("Không có hoạt động");
  const [code, setCode] = useState("");
  const [classPeriod, setClassPeriod] = useState("");
  const [level, setLevel] = useState("");
  const [listSemester, setListSemester] = useState([]);
  const [listActivity, setListActivity] = useState([]);
  const [listClass, setListClass] = useState([]);

  const loadDataSemester = () => {
    StMyClassAPI.getAllSemesters().then((response) => {
      setListSemester(response.data.data);
      setSemester(response.data.data[0].id);
      setLoading(false);
    });
  };

  useEffect(() => {
    setLoading(true);
    loadDataSemester();
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
      title: "Ca học",
      dataIndex: "classPeriod",
      key: "classPeriod",
      sorter: (a, b) => a.classPeriod - b.classPeriod,
      render: (text) => {
        let displayText = "";
        if (text === 0) {
          displayText = "Ca 1";
        } else if (text === 1) {
          displayText = "Ca 2";
        } else if (text === 2) {
          displayText = "Ca 3";
        } else if (text === 3) {
          displayText = "Ca 4";
        } else if (text === 4) {
          displayText = "Ca 5";
        } else if (text === 5) {
          displayText = "Ca 6";
        } else if (text === 6) {
          displayText = "Ca 7";
        } else if (text === 7) {
          displayText = "Ca 8";
        } else if (text === 8) {
          displayText = "Ca 9";
        } else if (text === 9) {
          displayText = "Ca 10";
        }

        return <span>{displayText}</span>;
      },
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
      title: "Cấp độ",
      dataIndex: "level",
      key: "level",
      sorter: (a, b) => a.level - b.level,
      render: (text) => {
        let displayText = "";
        if (text === 0) {
          displayText = "Level 1";
        } else if (text === 1) {
          displayText = "Level 2";
        } else if (text === 2) {
          displayText = "Level 3";
        } else {
          displayText = text;
        }

        return <span>{displayText}</span>;
      },
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

  const loadDataClass = () => {
    setLoading(true);
    let filter = {
      semesterId: semester,
      activityId: activity,
      code: code,
      classPeriod: classPeriod === "" ? null : parseInt(classPeriod),
      level: level === "" ? null : parseInt(level),
      studentId: sinhVienCurrent.id,
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
    <div className="my_project">
      {loading && <LoadingIndicator />}
      <div>
        <span style={{ fontSize: "20px" }}>
          <FontAwesomeIcon icon={faHome} /> Lớp của tôi
        </span>
      </div>
      <div className="filter">
        <FontAwesomeIcon icon={faFilter} style={{ fontSize: "18px" }} />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <div className="content_filter">
          <Row gutter={16} style={{ marginBottom: "10px", paddingTop: "10px" }}>
            <Col span={6}>
              <span>Học kỳ:</span>
              <QuestionCircleFilled
                style={{ paddingLeft: "12px", fontSize: "15px" }}
              />
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
              <QuestionCircleFilled
                style={{ paddingLeft: "12px", fontSize: "15px" }}
              />{" "}
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
              <span>Mã lớp:</span>{" "}
              <QuestionCircleFilled
                style={{ paddingLeft: "12px", fontSize: "15px" }}
              />{" "}
              <br />
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
              <QuestionCircleFilled
                style={{ paddingLeft: "12px", fontSize: "15px" }}
              />{" "}
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
              <span>Level:</span>{" "}
              <QuestionCircleFilled
                style={{ paddingLeft: "12px", fontSize: "15px" }}
              />{" "}
              <br />
              <Select
                style={{ width: "94%", marginTop: "6px" }}
                onChange={(e) => {
                  setLevel(e);
                }}
                showSearch
                filterOption={filterOptions}
                value={level}
              >
                <Option value="">Tất cả</Option>
                <Option value="0">Level 1</Option>
                <Option value="1">Level 2</Option>
                <Option value="2">Level 3</Option>
              </Select>
            </Col>
          </Row>
        </div>
        <div className="box_btn_filter">
          <Button className="btn_filter" onClick={handleClickFilter}>
            Tìm kiếm
          </Button>
          <Button className="btn_clear" onClick={clearData}>
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
        <div style={{ marginTop: "25px" }}>
          {" "}
          <Table dataSource={listClass} columns={columns} />
        </div>
      </div>
    </div>
  );
};

export default StudentMyClass;
