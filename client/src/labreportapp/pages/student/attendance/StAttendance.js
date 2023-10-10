import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-attendance.css";
import { Select, Table, Empty } from "antd";
import { faAtlas, faList, faSchool } from "@fortawesome/free-solid-svg-icons";
import { StMyClassAPI } from "../../../api/student/StMyClassAPI";
import { StAttendenceAPI } from "../../../api/student/StAttendenceAllAPI";
import { convertMeetingPeriodToTime } from "../../../helper/util.helper";
import LoadingIndicator from "../../../helper/loading";

const StAttendance = () => {
  const { Option } = Select;
  const [listSemester, setListSemester] = useState([]);
  const [semester, setSemester] = useState("");
  const [listClass, setListClass] = useState([]);
  const [listAttendence, setListAttendece] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  const getClassAttendenceListByStudentInClassAndSemester = (semester) => {
    const filter = {
      idSemester: semester,
    };
    StAttendenceAPI.getClassAttendenceListByStudentInClassAndSemester(
      filter
    ).then((response) => {
      setListClass(response.data.data);
      setListAttendece(response.data.data.attendences);
      setIsLoading(false);
    });
  };

  const loadDataSemester = () => {
    StMyClassAPI.getAllSemesters().then((response) => {
      setListSemester(response.data.data);
      setSemester(response.data.data[0].id);
    });
  };

  useEffect(() => {
    document.title = "Điểm danh | Lab-Report-App";
    loadDataSemester();
  }, []);

  useEffect(() => {
    setIsLoading(true);
    getClassAttendenceListByStudentInClassAndSemester(semester);
  }, [semester]);

  const handleChangeSemester = (e) => {
    setSemester(e);
  };

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      sorter: (a, b) => a.stt - b.stt,
      key: "stt",
    },
    {
      title: "Buổi học",
      dataIndex: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      key: "name",
    },
    {
      title: "Ngày",
      dataIndex: "meetingDate",
      key: "meetingDate",
      sorter: (a, b) => a.meetingDate - b.meetingDate,
      render: (text, record) => {
        const time = new Date(record.meetingDate);
        const formattedTime = `${time.getDate()}/${
          time.getMonth() + 1
        }/${time.getFullYear()}`;

        return <span>{formattedTime}</span>;
      },
    },
    {
      title: "Ca",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",
      sorter: (a, b) => a.meetingPeriod - b.meetingPeriod,
      render: (text, record) => {
        return <span>{parseInt(record.meetingPeriod) + 1}</span>;
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
      title: "Giảng viên",
      dataIndex: "teacherUsername",
      sorter: (a, b) => a.teacherUsername - b.teacherUsername,
      key: "teacherUsername",
    },
    {
      title: "Hình thức",
      dataIndex: "typeMeeting",
      key: "typeMeeting",
      sorter: (a, b) => a.typeMeeting - b.typeMeeting,
      render: (text, record) => (
        <span>{record.typeMeeting === 0 ? "Online" : "Offline"}</span>
      ),
    },
    {
      title: "Trạng thái đi học",
      dataIndex: "status",
      key: "status",
      sorter: (a, b) => a.status - b.status,
      render: (text, record) => (
        <span>
          {record.status === 1 ? (
            <span style={{ color: "red" }}>Vắng mặt</span>
          ) : record.status === 0 ? (
            <span style={{ color: "green" }}>Có mặt</span>
          ) : (
            <span style={{ color: "orange" }}>Not yet</span>
          )}
        </span>
      ),
    },
  ];
  return (
    <div className="box-general" style={{ paddingTop: 50 }}>
      {isLoading && <LoadingIndicator />}
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon icon={faAtlas} style={{ marginRight: "8px" }} /> Điểm
          danh
        </span>
      </div>
      <div className="filter-semester">
        <div className="wrapper-filter">
          <span style={{ marginLeft: "3px" }}>Học kỳ</span>
          <div className="select-semester" style={{ marginTop: "5px" }}>
            <Select
              showSearch
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
          </div>
          <span style={{ marginLeft: "3px", fontSize: "13.5px" }}>
            Lựa chọn học kỳ để hiện thị chi tiết điểm danh
          </span>
        </div>
      </div>
      {listClass.map((item, index) => {
        return (
          <>
            <div className="table-attendence">
              <div
                className="header"
                style={{
                  padding: "10px",
                  fontSize: "18px",
                  fontWeight: "500",
                  display: "flex",
                }}
              >
                <span className="header-icon">
                  <FontAwesomeIcon icon={faSchool} />
                </span>
                <span className="header-title" style={{ marginLeft: "12px" }}>
                  Lớp {item.classCode}
                </span>
                <span
                  className="header-absent"
                  style={{
                    marginLeft: "15px",
                    color: "red",
                    fontSize: "16.5px",
                    fontWeight: "500",
                  }}
                >
                  (Vắng: {item.attendences.filter((i) => i.status === 1).length}
                  /{item.attendences.length})
                </span>
              </div>
              <div
                style={{ borderBottom: "1px solid #ddd", marginTop: "10px" }}
              ></div>
              <div className="" style={{ marginTop: "30px" }}>
                <Table
                  dataSource={item.attendences}
                  columns={columns}
                  pagination={{ pageSize: 8 }}
                  locale={{
                    emptyText: (
                      <Empty
                        imageStyle={{ height: 60 }}
                        style={{
                          padding: "20px 0px 20px 0",
                        }}
                        description={<span>Không có dữ liệu</span>}
                      />
                    ),
                  }}
                />
              </div>
            </div>
          </>
        );
      })}
      {listClass.length === 0 && (
        <div
          style={{
            backgroundColor: "white",
            borderRadius: 5,
            paddingTop: 10,
            paddingBottom: 30,
            marginTop: 25,
            boxShadow: "0px 0px 20px 1px rgba(148, 148, 148, 0.3)",
          }}
        >
          <>
            <Empty
              imageStyle={{ height: 60 }}
              description={" Không có dữ liệu"}
            />
          </>
        </div>
      )}
    </div>
  );
};

export default StAttendance;
