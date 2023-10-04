import "./styleTeacherScheduleToday.css";
import { useState } from "react";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import LoadingIndicator from "../../../helper/loading";
import {
  Button,
  Col,
  Empty,
  Input,
  Pagination,
  Row,
  Select,
  Table,
  Tooltip,
  message,
} from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCalendarWeek,
  faChalkboard,
  faHome,
  faUpload,
} from "@fortawesome/free-solid-svg-icons";
import { TeacherScheduleTodayAPI } from "../../../api/teacher/meeting/schedule-today/TeacherScheduleToday.api";
import { toast } from "react-toastify";
import {
  convertCheckAttended,
  convertMeetingPeriodToTime,
  convertStatusMeetingByDateAndPeriod,
} from "../../../helper/util.helper";
import { SearchOutlined } from "@ant-design/icons";
import { useNavigate } from "react-router-dom";
import { TeacherAttendanceAPI } from "../../../api/teacher/attendance/TeacherAttendance.api";
import { async } from "q";
const { Option } = Select;

const TeacherScheduleToday = () => {
  const [loading, setLoading] = useState(false);
  const [dataToday, setDataToday] = useState([]);
  const [time, setTime] = useState("7");
  const [dataTime, setDataTime] = useState([]);
  const [current, setCurrent] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [actionData, setActionData] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - Lịch dạy hôm nay";
    featchData();
    setTime("7");
    featchDataTime();
  }, []);

  useEffect(() => {
    featchDataTime();
  }, [time, current]);
  // const updateActionData = () => {
  //   const updatedActionData = dataToday.map((item) => {
  //     return {
  //       idMeeting: item.idMeeting,
  //       showButton: convertCheckAttended(item.meetingPeriod),
  //     };
  //   });
  //   setActionData(updatedActionData);
  // };
  const featchData = async () => {
    setLoading(false);
    try {
      await TeacherScheduleTodayAPI.getAllByIdTe().then((response) => {
        setDataToday(response.data.data);
      });
      setLoading(true);
    } catch (error) {}
  };

  const featchDataTime = async () => {
    try {
      let dataFind = {
        time: time,
        page: current,
        size: 6,
      };
      await TeacherScheduleTodayAPI.getAllNowToTimeByIdTeacher(dataFind).then(
        (response) => {
          setTotalPages(parseInt(response.data.data.totalPages));
          setDataTime(response.data.data.data);
        }
      );
      setLoading(true);
    } catch (error) {}
  };
  const handleAddressChange = (idMeeting, value) => {
    const listNew = dataToday.map((item) => {
      if (item.idMeeting === idMeeting) {
        return {
          ...item,
          meetingAddress: value,
        };
      }
      return item;
    });
    setDataToday(listNew);
  };
  const updateAddress = async () => {
    try {
      let dataUp = {
        listMeeting: dataToday,
      };
      await TeacherScheduleTodayAPI.updateDescriptionMeeting(dataUp).then(
        (response) => {
          setDataToday(response.data.data);
          toast.success("Lưu link học thành công");
        }
      );
    } catch (error) {}
  };
  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    const format = `${day}/${month}/${year}`;
    return format;
  };

  const handleDetailAttend = (idMeeting, meetingPeriod) => {
    try {
      let check = convertCheckAttended(meetingPeriod);
      check
        ? navigate(`/teacher/schedule-today/attendance/` + idMeeting)
        : toast.warning(
            "Không còn trong ca dạy, không thể xem hoặc sửa điểm danh !"
          );
    } catch (error) {}
  };

  const data = dataToday;
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      width: "12px",
    },
    {
      title: "Mã lớp",
      dataIndex: "codeClass",
      key: "codeClass",
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) =>
        record.codeClass.toLowerCase().includes(value.toLowerCase()),
    },
    {
      title: "Ngày",
      dataIndex: "meetingDate",
      key: "meetingDate",
      render: (text, record) => {
        return (
          <div style={{ textAlign: "center" }}>{convertLongToDate(text)}</div>
        );
      },
    },
    {
      title: "Tên buổi học",
      dataIndex: "meetingName",
      key: "meetingName",
    },
    {
      title: "Phòng",
      dataIndex: "typeMeeting",
      key: "typeMeeting",
      render: (text, record) => {
        return <span>{text === 0 ? "Online" : "Offline"}</span>;
      },
    },

    {
      title: "Địa điểm",
      dataIndex: "addressCustom",
      key: "addressCustom",
      render: (text, record) => {
        return (
          <span>{record.typeMeeting === 0 ? "Google Meet" : "Xưởng"}</span>
        );
      },
    },
    {
      title: "Thời gian",
      dataIndex: "timePeriod",
      key: "timePeriod",
      render: (text, record) => {
        return (
          <div style={{ textAlign: "center" }}>
            {convertMeetingPeriodToTime(record.meetingPeriod)}
          </div>
        );
      },
    },
    {
      title: "Ca học",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",
      render: (text, record) => {
        return <div style={{ textAlign: "center" }}>{text + 1}</div>;
      },
      sorter: (a, b) => a.meetingPeriod - b.meetingPeriod,
    },
    {
      title: "Level",
      dataIndex: "level",
      key: "level",
      sorter: (a, b) => a.level.localeCompare(b.level),
    },
    {
      title: "Link học trực tuyến",
      dataIndex: "meetingAddress",
      key: "meetingAddress",
      render: (text, record) => {
        return (
          <div key={record.idMeeting}>
            {" "}
            <Input
              style={{ width: "80%" }}
              type="text"
              value={text}
              readOnly
              onDoubleClick={(e) => {
                e.target.readOnly = false;
                e.target.select();
              }}
              onBlur={(e) => {
                e.target.readOnly = true;
              }}
              onChange={(e) => {
                handleAddressChange(record.idMeeting, e.target.value);
              }}
            />
            <span className="box-icon-square">
              <Tooltip title="Upload">
                <FontAwesomeIcon
                  icon={faUpload}
                  style={{ color: "white", fontSize: 21 }}
                  onClick={updateAddress}
                />
              </Tooltip>
            </span>
          </div>
        );
      },
    },
    {
      title: "Điểm danh",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => {
        if (convertCheckAttended(record.meetingPeriod)) {
          return (
            <div className="box_icon" style={{ textAlign: "center" }}>
              <Tooltip title="Xem chi tiết">
                <div
                  onClick={() =>
                    handleDetailAttend(record.idMeeting, record.meetingPeriod)
                  }
                  className="box-center"
                  style={{
                    height: "30px",
                    width: "120px",
                    backgroundColor: "#007bff",
                    color: "white",
                    borderRadius: "5px",
                  }}
                >
                  <span style={{ fontSize: "14px" }}>Xem - chỉnh sửa</span>
                </div>
              </Tooltip>
            </div>
          );
        } else {
          return null;
        }
      },
      width: "10%",
    },
  ];
  const columnsTime = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
    },
    {
      title: "Mã lớp",
      dataIndex: "codeClass",
      key: "codeClass",
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) =>
        record.codeClass.toLowerCase().includes(value.toLowerCase()),
    },
    {
      title: "Ngày",
      dataIndex: "meetingDate",
      key: "meetingDate",
      render: (text, record) => {
        return (
          <div style={{ textAlign: "center" }}>{convertLongToDate(text)}</div>
        );
      },
      sorter: (a, b) => a.meetingDate - b.meetingDate,
    },
    {
      title: "Tên buổi học",
      dataIndex: "meetingName",
      key: "meetingName",
    },
    {
      title: "Phòng",
      dataIndex: "typeMeeting",
      key: "typeMeeting",
      render: (text, record) => {
        return <span>{text === 0 ? "Online" : "Offline"}</span>;
      },
    },

    {
      title: "Địa điểm",
      dataIndex: "addressCustom",
      key: "addressCustom",
      render: (text, record) => {
        return (
          <span>{record.typeMeeting === 0 ? "Google Meet" : "Xưởng"}</span>
        );
      },
    },
    {
      title: "Thời gian",
      dataIndex: "timePeriod",
      key: "timePeriod",
      render: (text, record) => {
        return (
          <div style={{ textAlign: "center" }}>
            {convertMeetingPeriodToTime(record.meetingPeriod)}
          </div>
        );
      },
    },
    {
      title: "Ca học",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",
      render: (text, record) => {
        return <div style={{ textAlign: "center" }}>{text + 1}</div>;
      },
      sorter: (a, b) => a.meetingPeriod - b.meetingPeriod,
    },

    {
      title: "Level",
      dataIndex: "level",
      key: "level",
      sorter: (a, b) => a.level - b.level,
    },
    {
      title: "Link học trực tuyến",
      dataIndex: "meetingAddress",
      key: "meetingAddress",
    },
  ];
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="box-one">
        <Link to="/teacher/schedule-today" style={{ color: "black" }}>
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
      <div className="box-two-student-in-my-class">
        <div className="box-two-student-in-my-class-son">
          <div className="button-menu">
            <Link
              to="/teacher/schedule-today"
              style={{
                fontSize: "17px",
                fontWeight: "bold",
              }}
              id="menu-checked"
            >
              &nbsp; LỊCH DẠY HÔM NAY &nbsp;
            </Link>
            <Link
              to="/teacher/my-class"
              className="custom-link"
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
          <div
            className="title-box-two"
            style={{ paddingTop: "20px", minHeight: "500px", height: "auto" }}
          >
            <div className="title-table">
              <div>
                <span style={{ fontSize: "17px", fontWeight: "500" }}>
                  <FontAwesomeIcon
                    icon={faChalkboard}
                    style={{ marginRight: "10px", fontSize: "24px" }}
                  />
                  Lịch dạy hôm nay
                </span>
              </div>
            </div>
            <div className="table-teacher" style={{ marginTop: "20px" }}>
              {dataToday != null && dataToday.length > 0 ? (
                <Table
                  size="middle"
                  dataSource={data}
                  rowKey="idMeeting"
                  columns={columns}
                  pagination={false}
                />
              ) : (
                <Empty
                  imageStyle={{ height: 60 }}
                  description={<span>Không có ca dạy</span>}
                />
              )}
            </div>
            <div className="list-lesson-custom">
              <div className="title-table">
                <div>
                  <Row gutter={25}>
                    <Col span={3}>
                      <div
                        style={{
                          fontSize: "17px",
                          fontWeight: "500",
                          paddingTop: "8px",
                        }}
                      >
                        <FontAwesomeIcon
                          icon={faCalendarWeek}
                          style={{ marginRight: "10px", fontSize: "24px" }}
                        />
                        Lịch dạy
                      </div>
                    </Col>
                    <Col span={21}>
                      <Select
                        showSearch
                        value={time}
                        onChange={(value) => {
                          setTime(value);
                        }}
                        style={{
                          width: "100%",
                          margin: "6px 0 10px 0",
                        }}
                      >
                        <Option value="7">7 ngày tới</Option>
                        <Option value="14">14 ngày tới</Option>
                        <Option value="30">30 ngày tới</Option>
                        <Option value="-7">7 ngày trước</Option>
                        <Option value="-14">14 ngày trước</Option>
                        <Option value="-30">30 ngày trước</Option>
                      </Select>
                    </Col>
                  </Row>
                </div>
              </div>
              <div className="table-teacher" style={{ marginTop: "20px" }}>
                {dataTime.length > 0 ? (
                  <>
                    <div>
                      <Table
                        dataSource={dataTime}
                        rowKey="idMeeting"
                        columns={columnsTime}
                        pagination={false}
                      />
                      <div
                        className="pagination_box"
                        style={{ display: "flex", alignItems: "center" }}
                      >
                        <Pagination
                          style={{ marginRight: "10px" }}
                          simple
                          current={current}
                          onChange={(value) => {
                            setCurrent(value);
                          }}
                          total={totalPages * 10}
                        />
                      </div>
                    </div>
                  </>
                ) : (
                  <Empty
                    imageStyle={{ height: 60 }}
                    description={<span>Không có ca dạy</span>}
                  />
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default TeacherScheduleToday;
