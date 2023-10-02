import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-register-class.css";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import LoadingIndicator from "../../../helper/loading";
import {
  faChainSlash,
  faEye,
  faFilterCircleDollar,
  faRegistered,
} from "@fortawesome/free-solid-svg-icons";
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
  Modal,
  Empty,
} from "antd";
import { QuestionCircleFilled, ProjectOutlined } from "@ant-design/icons";
import { StMyClassAPI } from "../../../api/student/StMyClassAPI";
import { StClassAPI } from "../../../api/student/StClassAPI";
import { StLevelAPI } from "../../../api/student/StLevelAPI";
import { convertLongToDate } from "../../../helper/convertDate";
import { constant } from "lodash";

const { Option } = Select;
const { TextArea } = Input;
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
  const [showClassDescription, setShowClassDescription] = useState(false);
  const [idSelected, setIdSelected] = useState(null);
  const [startTimeStudent, setStartTimeStudent] = useState("");
  const [endTimeStudent, setEndTimeStudent] = useState("");
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

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
    document.title = "Đăng ký lớp học | Lab-Report-App";
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
      page: currentPage,
      size: 10,
    };
    StClassAPI.getClassByCriteriaAndIsActive(filter).then((response) => {
      const data = response.data.data.data;
      setListClass(data);
      setCurrentPage(response.data.data.currentPage);
      setTotalPages(response.data.data.totalPages);
      setStartTimeStudent(
        data && data[0] && convertLongToDate(data[0].startTimeStudent)
      );
      setEndTimeStudent(
        data && data[0] && convertLongToDate(data[0].endTimeStudent)
      );
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
      title: "Sĩ số",
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
        if (text != null) {
          return <span>{convertMeetingPeriod(text)}</span>;
        } else {
          return <span>Chưa có</span>;
        }
      },
    },
    {
      title: "Thời gian",
      dataIndex: "timePeriod",
      key: "timePeriod",
      sorter: (a, b) => a.classPeriod - b.classPeriod,
      render: (text, record) => {
        if (text != null) {
          return <span>{convertMeetingPeriodToTime(record.classPeriod)}</span>;
        } else {
          return <span>Chưa có</span>;
        }
      },
    },
    {
      title: "Level",
      dataIndex: "level",
      key: "level",
    },
    {
      title: "Hoạt động",
      dataIndex: "activityName",
      key: "activityName",
      sorter: (a, b) => a.activityName - b.activityName,
      render: (text, record) => {
        return (
          <span style={{ whiteSpace: "pre-line" }}>{record.activityName}</span>
        );
      },
    },
    {
      title: "Hành động",
      dataIndex: "action",
      key: "action",
      align: "",
      render: (text, record) => (
        <>
          <Popconfirm
            style={{}}
            placement={"topRight"}
            title="Tham gia lớp học"
            description={"Bạn có chắc chắn muốn tham gia lớp học này?"}
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
          <Tooltip title="Chi tiết">
            <FontAwesomeIcon
              icon={faEye}
              onClick={() => setOpen(true)}
              className="icon"
            />
            <Modal
              style={{ marginTop: "200px" }}
              open={open}
              onCancel={() => setOpen(false)}
              width={650}
              footer={null}
              className="modal_show_detail"
            >
              <div className="wrapper-modal">
                <div style={{ borderBottom: "1px solid black" }}>
                  <span style={{ fontSize: "18px" }}>Mô tả lớp học:</span>
                </div>
                <div
                  className="description-title"
                  style={{ marginTop: "20px" }}
                >
                  <TextArea
                    rows={4}
                    value={record.descriptions}
                    readOnly
                    style={{ cursor: "pointer" }}
                  />
                </div>
              </div>
            </Modal>
          </Tooltip>
        </>
      ),
    },
  ];

  const [open, setOpen] = useState(false);

  return (
    <>
      {loading && <LoadingIndicator />}
      <div className="box-general" style={{ paddingTop: 50 }}>
        <div className="heading-box">
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon
              icon={faRegistered}
              style={{ marginRight: "8px" }}
            />{" "}
            Đăng ký lớp học
          </span>
        </div>
        <div className="filter_register">
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
                <span>Mã lớp:</span> <br />
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
              <FontAwesomeIcon icon={faChainSlash} style={{ marginRight: 5 }} />{" "}
              Làm mới bộ lọc
            </Button>
          </div>
        </div>
        <div className="wrapper-table">
          <div className="table-class">
            <div className="title-table">
              <div
                style={{ display: "d-flex", justifyContent: "space-between" }}
              >
                {" "}
                <span style={{ fontSize: "17px", fontWeight: "500" }}>
                  {" "}
                  <ProjectOutlined
                    style={{ marginRight: "10px", fontSize: "24px" }}
                  />
                  Danh sách lớp học
                </span>
                {startTimeStudent && endTimeStudent && (
                  <span style={{ marginLeft: "10px", fontSize: "17px" }}>
                    (Sinh viên có thể đăng ký vào lớp từ ngày
                    <span style={{ color: "red" }}>
                      {" " + startTimeStudent + " "}
                    </span>
                    đến
                    <span style={{ color: "red" }}>{" " + endTimeStudent}</span>
                    )
                  </span>
                )}
              </div>
            </div>
            <div className="" style={{ marginTop: "20px" }}>
              <Table
                dataSource={listClass}
                rowKey="id"
                columns={columns}
                pagination={false}
                locale={{
                  emptyText: (
                    <Empty
                      imageStyle={{ height: 60 }}
                      style={{
                        padding: "20px 0px 20px 0",
                      }}
                      description={<span>Không có thông lớp học</span>}
                    />
                  ),
                }}
              />
              <div className="pagination_box">
                <Pagination
                  simple
                  current={currentPage + 1}
                  onChange={(page) => {
                    setCurrentPage(page - 1);
                  }}
                  total={totalPages * 10}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default StRegisterClass;
