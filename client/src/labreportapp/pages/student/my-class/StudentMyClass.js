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
import { Button, Col, Input, Row, Select, Table, Tooltip, Empty } from "antd";
import { StMyClassAPI } from "../../../api/student/StMyClassAPI";
import { Link } from "react-router-dom";
import { convertHourAndMinuteToString } from "../../../helper/util.helper";
import { StLevelAPI } from "../../../api/student/StLevelAPI";
import { StMeetingPeriodAPI } from "../../../api/student/StMeetingPeriodAPI";

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
  const [listMeetingPeriod, setListMeetingPeriod] = useState([]);
  const [listClass, setListClass] = useState([]);

  const loadDataSemester = () => {
    StMyClassAPI.getAllSemesters().then((response) => {
      setListSemester(response.data.data);
    });
  };

  const loadDataLevel = () => {
    StLevelAPI.getLevelAll().then((response) => {
      setListLevel(response.data.data);
    });
  };
  const loadMeetingPeriod = async () => {
    try {
      await StMeetingPeriodAPI.getPeriod().then((respone) => {
        setListMeetingPeriod(respone.data.data);
      });
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    loadDataLevel();
    loadDataSemester();
    loadMeetingPeriod();
    document.title = "Lớp của tôi | Lab-Report-App";
  }, []);

  const loadDataActivity = () => {
    StMyClassAPI.getAllActivityByIdSemester(semester).then((response) => {
      setListActivity(response.data.data);
      setActivity("");
    });
  };

  useEffect(() => {
    loadDataClass("Không có hoạt động");
  }, []);

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

  const loadDataClass = (activityParams, classPeriodParams) => {
    setLoading(true);
    let filter = {
      semesterId: semester,
      activityId: activityParams !== "" ? activity : activityParams,
      code: code,
      classPeriod: classPeriod,
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
  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    const format = `${day}/${month}/${year}`;
    return format;
  };
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
      render: (text, record) => convertLongToDate(record.startTime),
    },
    {
      title: "Ca",
      dataIndex: "classPeriod",
      key: "classPeriod",
      sorter: (a, b) => a.classPeriod.localeCompare(b.classPeriod),
      render: (text, record) => {
        if (text == null) {
          return <span>Chưa có</span>;
        } else {
          return <span>{text}</span>;
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
          <Row gutter={24} style={{ padding: "5px 2% 0" }}>
            <Col span={8}>
              <span>Học kỳ</span>
              <br />
              <Select
                showSearch
                filterOption={filterOptions}
                style={{
                  width: "100%",
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
            <Col span={16}>
              <span>Hoạt động</span>
              <br />
              <Select
                showSearch
                filterOption={filterOptions}
                style={{
                  width: "100%",
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
          <Row gutter={24} style={{ padding: "5px 2% 0" }}>
            <Col span={8}>
              <span>Mã lớp</span> <br />
              <Input
                onChange={(e) => {
                  setCode(e.target.value);
                }}
                value={code}
                style={{ marginTop: "6px" }}
                type="text"
                placeholder="Nhập mã lớp"
              />
            </Col>
            <Col span={8}>
              <span>Ca học</span>
              <br />

              <Select
                showSearch
                filterOption={filterOptions}
                style={{
                  width: "100%",
                  margin: "6px 0 10px 0",
                }}
                onChange={(e) => {
                  setClassPeriod(e);
                }}
                value={classPeriod}
              >
                <Option value="">Tất cả</Option>
                {listMeetingPeriod.length > 0 &&
                  listMeetingPeriod.map((item) => (
                    <Option value={item.id} key={item.id}>
                      {item.name}
                      {" - "}(
                      {convertHourAndMinuteToString(
                        item.startHour,
                        item.startMinute,
                        item.endHour,
                        item.endMinute
                      )}
                      )
                    </Option>
                  ))}
              </Select>
            </Col>
            <Col span={8}>
              <span>Cấp độ</span> <br />
              <Select
                style={{ width: "100%", marginTop: "6px" }}
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
        <div className="box_btn_filter_st">
          <Button
            className="btn_filter"
            onClick={handleClickFilter}
            style={{ marginRight: "15px" }}
          >
            <FontAwesomeIcon
              icon={faFilterCircleDollar}
              style={{ marginRight: "8px" }}
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
              style={{ marginRight: "8px" }}
            />{" "}
            Làm mới bộ lọc
          </Button>
        </div>
      </div>
      <div className="table_myclass" style={{ minHeight: "273px" }}>
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
            <Table dataSource={listClass} rowKey="id" columns={columns} />
          </div>
        )}
        {listClass.length === 0 && (
          <>
            <Empty
              imageStyle={{ height: 60 }}
              description={<span>Không tìm thấy lớp học nào !</span>}
            />{" "}
          </>
        )}
      </div>
    </div>
  );
};

export default StudentMyClass;
