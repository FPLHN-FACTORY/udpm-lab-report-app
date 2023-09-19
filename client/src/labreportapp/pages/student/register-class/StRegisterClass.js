import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-register-class.css";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import LoadingIndicator from "../../../helper/loading";
import { faRegistered } from "@fortawesome/free-solid-svg-icons";
import {
  convertMeetingPeriod,
  convertMeetingPeriodToTime,
} from "../../../helper/util.helper";
import { faFilter, faRightToBracket } from "@fortawesome/free-solid-svg-icons";
import { toast } from "react-toastify";
import {
  Button,
  Col,
  Input,
  Row,
  Select,
  Table,
  Tooltip,
  Pagination,
  Popconfirm,
} from "antd";
import { QuestionCircleFilled, ProjectOutlined } from "@ant-design/icons";
import { StMyClassAPI } from "../../../api/student/StMyClassAPI";
import { sinhVienCurrent } from "../../../helper/inForUser";
import { StClassAPI } from "../../../api/student/StClassAPI";
import { StLevelAPI } from "../../../api/student/StLevelAPI";

const { Option } = Select;
const StRegisterClass = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [semester, setSemester] = useState("");
  const [activity, setActivity] = useState("");
  const [classCode, setClassCode] = useState("");
  const [classPeriod, setClassPeriod] = useState("");
  const [level, setLevel] = useState("");
  const [listLevel, setListLevel] = useState([]);
  const [listSemester, setListSemester] = useState([]);
  const [listActivity, setListActivity] = useState([]);
  const [listClass, setListClass] = useState([]);

  const loadDataLevel = () => {
    StLevelAPI.getLevelAll().then((response) => {
      setListLevel(response.data.data);
    });
  };

  const loadDataSemester = () => {
    StMyClassAPI.getAllSemesters().then((response) => {
      setListSemester(response.data.data);
      const getSemester = response.data.data && response.data.data[0].id;
      setSemester(getSemester);
      getClassByCriteriaIsAcive(getSemester);
    });
  };
  const loadDataActivity = () => {
    StMyClassAPI.getAllActivityByIdSemester(semester).then((response) => {
      setListActivity(response.data.data);
      setActivity("");
    });
  };
  useEffect(() => {
    loadDataLevel();
    loadDataSemester();
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
    getClassByCriteriaIsAcive(semester);
  };

  const filterOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };

  const handleJoinClass = async (record) => {
    try {
      setLoading(true);
      const filter = {
        idStudent: sinhVienCurrent.id,
        idClass: record.id,
      };
      await StClassAPI.studentJoinClass(filter).then((response) => {
        setLoading(false);
        toast.success("Tham gia lớp học thành công!");
        navigate(`/student/my-class/post/${record.id}`);
      });
    } catch (error) {
      setLoading(false);
      toast.error(error.response.data);
      getClassByCriteriaIsAcive(semester);
    }
  };

  const getClassByCriteriaIsAcive = (semester) => {
    setLoading(true);
    const filter = {
      semesterId: semester,
      activityId: activity,
      code: classCode,
      classPeriod: classPeriod === "" ? null : parseInt(classPeriod),
      level: level,
    };
    StClassAPI.getClassByCriteriaAndIsActive(filter).then((response) => {
      setListClass(response.data.data);
      setLoading(false);
    });
  };

  const clearData = () => {
    setSemester("");
    setActivity("Không có hoạt động");
    setClassCode("");
    setClassPeriod("");
    setLevel("");
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
      dataIndex: "classCode",
      sorter: (a, b) => a.classCode.localeCompare(b.classCode),
      key: "classCode",
    },
    {
      title: "Giảng viên",
      dataIndex: "teacherUsername",
      key: "teacherUsername",
      sorter: (a, b) => a.teacherUsername.localeCompare(b.teacherUsername),
      render: (text, record) => (
        <>
          <span>{record.teacherUsername}</span>
        </>
      ),
    },
    {
      title: "Số lượng thành viên",
      dataIndex: "classSize",
      key: "classSize",
      sorter: (a, b) => a.classSize - b.classSize,
      render: (text, record) => (
        <>
          <span>{record.classSize}</span>
        </>
      ),
    },
    {
      title: "Ca học",
      dataIndex: "classPeriod",
      key: "classPeriod",
      sorter: (a, b) => a.classPeriod - b.classPeriod,
      render: (text) => {
        return <span>{convertMeetingPeriod(text)}</span>;
      },
    },
    {
      title: "Thời gian",
      dataIndex: "timePeriod",
      key: "timePeriod",
      sorter: (a, b) => a.classPeriod - b.classPeriod,
      render: (text, record) => {
        return <span>{convertMeetingPeriodToTime(record.classPeriod)}</span>;
      },
    },
    {
      title: "Level",
      dataIndex: "level",
      key: "level",
    },
    {
      title: "Hành động",
      dataIndex: "action",
      key: "action",
      align: "center",
      render: (text, record) => (
        <>
          <Popconfirm
            title="Tham gia lớp học"
            description="Bạn có chắc chắn muốn tham gia lớp học này không?"
            okText="Có"
            cancelText="Không"
            onConfirm={() => {
              handleJoinClass(record);
            }}
          >
            <Tooltip title="Tham gia lớp học">
              <FontAwesomeIcon icon={faRightToBracket} className="icon" />
            </Tooltip>
          </Popconfirm>
        </>
      ),
    },
  ];

  return (
    <>
      {loading && <LoadingIndicator />}
      <div className="box-general">
        <div className="heading-box">
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon
              icon={faRegistered}
              style={{ marginRight: "8px" }}
            />{" "}
            Đăng ký lớp học
          </span>
        </div>
        <div className="filter">
          <FontAwesomeIcon icon={faFilter} style={{ fontSize: "18px" }} />{" "}
          <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
          <hr />
          <div className="content_filter">
            <Row
              gutter={16}
              style={{ marginBottom: "10px", paddingTop: "10px" }}
            >
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
                    listSemester.slice(0, 1).map((item) => (
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
            <Row
              gutter={16}
              style={{ marginBottom: "0px", paddingTop: "10px" }}
            >
              <Col span={6}>
                <span>Mã lớp:</span>{" "}
                <QuestionCircleFilled
                  style={{ paddingLeft: "12px", fontSize: "15px" }}
                />{" "}
                <br />
                <Input
                  onChange={(e) => {
                    setClassCode(e.target.value);
                  }}
                  value={classCode}
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
              Tìm kiếm
            </Button>
            <Button
              className="btn_clear"
              style={{ backgroundColor: "rgb(38, 144, 214)" }}
              onClick={clearData}
            >
              Làm mới bộ lọc
            </Button>
          </div>
        </div>
        <div className="wrapper-table">
          <div className="table-class">
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
            <div className="" style={{ marginTop: "20px" }}>
              <Table
                dataSource={listClass}
                rowKey="id"
                columns={columns}
                pagination={{ pageSize: 8 }}
              />
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default StRegisterClass;
