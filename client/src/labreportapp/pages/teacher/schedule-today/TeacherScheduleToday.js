import "./styleTeacherScheduleToday.css";
import { giangVienCurrent } from "../../../helper/inForUser";
import { useState } from "react";
import { useEffect } from "react";
import { Link } from "react-router-dom";
import LoadingIndicator from "../../../helper/loading";
import { Col, Empty, Input, Row, Select, Table, Tooltip } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCalendarWeek,
  faChalkboard,
  faHome,
  faUpload,
} from "@fortawesome/free-solid-svg-icons";
import { TeacherScheduleTodayAPI } from "../../../api/teacher/meeting/schedule-today/TeacherScheduleToday.api";
import { toast } from "react-toastify";
import { convertMeetingPeriodToTime } from "../../../helper/util.helper";

const { Option } = Select;

const TeacherScheduleToday = () => {
  const [loading, setLoading] = useState(false);
  const [dataToday, setDataToday] = useState([]);
  const [time, setTime] = useState("7");
  const [dataTime, setDataTime] = useState([]);

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - lịch dạy hôm nay";
    featchData(giangVienCurrent.id);
    setTime("7");
    featchDataTime();
  }, []);
  useEffect(() => {
    featchDataTime();
  }, [time]);

  const featchData = async (idTeacher) => {
    setLoading(false);
    try {
      await TeacherScheduleTodayAPI.getAllByIdTe(idTeacher).then((response) => {
        setDataToday(response.data.data);
      });
      setLoading(true);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const featchDataTime = async () => {
    try {
      let dataFind = {
        idTeacher: giangVienCurrent.id,
        time: time,
      };
      await TeacherScheduleTodayAPI.getAllNowToTimeByIdTeacher(dataFind).then(
        (response) => {
          setDataTime(response.data.data);
        }
      );
      setLoading(true);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
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
        idTeacher: giangVienCurrent.id,
        listMeeting: dataToday,
      };
      await TeacherScheduleTodayAPI.updateDescriptionMeeting(dataUp).then(
        (response) => {
          setDataToday(response.data.data);
          toast.success("Lưu link học thành công");
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const format = `${date.getDate()}/${
      date.getMonth() + 1
    }/${date.getFullYear()}`;
    return format;
  };
  const data = dataToday;
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
      width: "12px",
    },
    {
      title: "Mã lớp",
      dataIndex: "codeClass",
      key: "codeClass",
    },
    {
      title: "Ngày",
      dataIndex: "meetingDate",
      key: "meetingDate",
      render: (text, record) => {
        return <span>{convertLongToDate(text)}</span>;
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
        return <span>{convertMeetingPeriodToTime(record.meetingPeriod)}</span>;
      },
    },
    {
      title: "Ca học",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",
      render: (text, record) => {
        return <span>{text + 1}</span>;
      },
    },
    {
      title: "Level",
      dataIndex: "level",
      key: "level",
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
      render: (text, record) => (
        <>
          <div className="box_icon">
            <Link
              to={`/teacher/schedule-today/attendance/${record.idMeeting}`}
              className="btn btn-success ml-4"
            >
              <Tooltip title="Xem chi tiết">
                <div
                  className="box-center"
                  style={{
                    height: "30px",
                    width: "120px",
                    backgroundColor: "#007bff",
                    color: "white",
                    borderRadius: "5px",
                  }}
                >
                  <span style={{ fontSize: "14px" }}> Xem - chỉnh sửa </span>
                </div>
              </Tooltip>
            </Link>
          </div>
        </>
      ),
      width: "10%",
    },
  ];
  const columnsTime = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
      width: "12px",
    },
    {
      title: "Mã lớp",
      dataIndex: "codeClass",
      key: "codeClass",
    },
    {
      title: "Ngày",
      dataIndex: "meetingDate",
      key: "meetingDate",
      render: (text, record) => {
        return <span>{convertLongToDate(text)}</span>;
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
        return <span>{convertMeetingPeriodToTime(record.meetingPeriod)}</span>;
      },
    },
    {
      title: "Ca học",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",
      render: (text, record) => {
        return <span>{text + 1}</span>;
      },
    },

    {
      title: "Level",
      dataIndex: "level",
      key: "level",
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
              {dataToday.length > 0 ? (
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
                  description={
                    <span style={{ color: "#007bff" }}>Không có ca dạy</span>
                  }
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
                  <Table
                    dataSource={dataTime}
                    rowKey="idMeeting"
                    columns={columnsTime}
                    size="large"
                    pagination={{
                      defaultPageSize: 6,
                    }}
                  />
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
