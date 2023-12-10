import { useEffect } from "react";
import "./styleTeacherAttendance.css";
import { useParams, useNavigate } from "react-router-dom";
import { TeacherStudentClassesAPI } from "../../../../api/teacher/student-class/TeacherStudentClasses.api";
import { TeacherAttendanceAPI } from "../../../../api/teacher/attendance/TeacherAttendance.api";
import { useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherMeetingAPI } from "../../../../api/teacher/meeting/TeacherMeeting.api";
import { Link } from "react-router-dom";
import CustomSwitch from "./CustomSwitch";
import { Button, Empty, Input, Row, Table, message } from "antd";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import {
  SetAttendanceMeeting,
  GetAttendanceMeeting,
  UpdateAttendanceMeeting,
} from "../../../../app/teacher/attendance-meeting-today/teacherAttendanceMeetingSlice.reduce";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBook, faHome } from "@fortawesome/free-solid-svg-icons";
import { SearchOutlined } from "@ant-design/icons";
import { checkInToAttend } from "../../../../helper/util.helper";

const TeacherAttendanceMeeting = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { idMeeting } = useParams();
  const [meeting, setMeeting] = useState({});
  const [classFind, setClassFind] = useState({});
  const [listStudentClassAPI, setListStudentClassAPI] = useState([]);
  const [loadingData, setLoadingData] = useState(false);
  const [loading, setLoading] = useState(false);
  const [idClass, setIdClass] = useState("");
  const [checkAttendance, setCheckAttendance] = useState(false);
  const [listAttendance, setListAttendance] = useState([]);
  const [notes, setNotes] = useState("");
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - điểm danh";
    featchMeetingCheckDate(idMeeting);
    setNotes("");
  }, []);
  const fetchData = async (idClass) => {
    await Promise.all([
      await featchStudentClass(idClass),
      await featchClass(idClass),
      await featchAttendance(idClass),
    ]);
  };
  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassFind(responese.data.data);
      });
    } catch (error) {
      console.log(error);
    }
  };
  const featchAttendance = async (idClass) => {
    try {
      await TeacherAttendanceAPI.getAttendanceByIdMeeting(idMeeting).then(
        (response) => {
          if (response.data.data.length >= 1) {
            setCheckAttendance(true);
            setListAttendance(response.data.data);
            featInforStudent(idClass);
          } else {
            setCheckAttendance(false);
            featchStudentClass(idClass);
            featInforStudent(idClass);
          }
        }
      );
    } catch (error) {}
  };
  const featInforStudent = async (idClass) => {
    try {
      if (checkAttendance) {
        const listShowTable = listStudentClassAPI.map((item1) => {
          const matchedObject = listAttendance.find(
            (item2) =>
              item2.idStudent === item1.idStudent &&
              item2.idMeeting === idMeeting
          );
          return {
            ...item1,
            ...matchedObject,
            idMeeting: idMeeting,
            nameMeeting: meeting.name,
            statusAttendance:
              matchedObject.statusAttendance === "0" ? "YES" : "NO",
          };
        });
        dispatch(SetAttendanceMeeting(listShowTable));
      } else {
        const listShowTable = listStudentClassAPI.map((item1) => {
          return {
            ...item1,
            idMeeting: idMeeting,
            nameMeeting: meeting.name,
            statusAttendance: "YES",
          };
        });
        dispatch(SetAttendanceMeeting(listShowTable));
      }
      if (loadingData === true) {
        setLoading(true);
      }
      setLoadingData(true);
    } catch (error) {}
  };
  const featchStudentClass = async (idClass) => {
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(idClass).then(
        (responese) => {
          const listAPI = responese.data.data.map((item) => {
            return { ...item, statusAttendance: "1" };
          });
          setListStudentClassAPI(listAPI);
        }
      );
    } catch (error) {}
  };

  const featchMeetingCheckDate = async (id) => {
    setLoading(false);
    try {
      await TeacherMeetingAPI.getAndCheckMeetingById(id).then(
        (response) => {
          setMeeting(response.data.data);
          setNotes(response.data.data.notes);
          setIdClass(response.data.data.idClass);
          fetchData(response.data.data.idClass);
        },
        (error) => {
          setLoading(true);
          navigate("/teacher/schedule-today");
        }
      );
    } catch (error) {}
  };

  useEffect(() => {
    if (loadingData === true) {
      fetchData(idClass);
    }
  }, [loadingData]);

  const handleSave = async () => {
    try {
      let dataFind = {
        listAttendance: data,
        idMeeting: idMeeting,
        idClass: classFind.id,
        codeClass: classFind.code,
        notes: notes,
      };
      let checkTime = false;
      if (meeting !== null) {
        checkTime = checkInToAttend(
          meeting.meetingDate,
          meeting.startHour,
          meeting.startMinute,
          meeting.endHour,
          meeting.endMinute
        );
      }
      if (!checkTime) {
        message.error(
          "Không còn trong ca dạy, không thể xem hoặc sửa điểm danh !"
        );
        navigate("/teacher/schedule-today");
        return;
      }
      await TeacherAttendanceAPI.createOrUpdate(dataFind).then((respone) => {
        dispatch(UpdateAttendanceMeeting(respone.data.data.listAttendance));
        message.success(respone.data.data.message);
        navigate("/teacher/schedule-today");
      });
    } catch (error) {
      console.log(error);
      navigate("/teacher/schedule-today");
    }
  };
  const handleChangeNotes = (id, value) => {
    let dataUpdate = data.map((item) => {
      if (item.idStudent === id) {
        return { ...item, notes: value };
      }
      return item;
    });
    dispatch(SetAttendanceMeeting(dataUpdate));
  };
  const data = useAppSelector(GetAttendanceMeeting);
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
    },
    {
      title: "Tên tài khoản",
      dataIndex: "username",
      key: "username",
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
        record.username.toLowerCase().includes(value.toLowerCase()),
    },

    {
      title: "Tên sinh viên",
      dataIndex: "name",
      key: "name",
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
        record.name.toLowerCase().includes(value.toLowerCase()),
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      sorter: (a, b) => a.email.localeCompare(b.email),
    },
    {
      title: "Ghi chú",
      dataIndex: "notes",
      key: "notes",
      render: (text, record) => {
        return (
          <Input
            type="text"
            style={{ minWidth: "100px" }}
            value={text}
            onChange={(e) =>
              handleChangeNotes(record.idStudent, e.target.value)
            }
          />
        );
      },
    },
    {
      title: "Trạng thái",
      dataIndex: "statusAttendance",
      key: "statusAttendance",
      width: "30%",
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
      onFilter: (value, record) => {
        return (
          (value.toLowerCase() === "có mặt" &&
            record.statusAttendance === "YES") ||
          (value.toLowerCase() === "vắng mặt" &&
            record.statusAttendance === "NO")
        );
      },
      render: (text, record) => {
        return (
          <>
            <CustomSwitch
              leftLabel="Có mặt"
              rightLabel="Vắng mặt"
              status={text}
              items={record}
            />
          </>
        );
      },
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
            <span style={{ color: "gray", fontSize: "14px" }}>
              {" "}
              - Điểm danh
            </span>
          </span>
        </Link>
      </div>
      <div className="box-two-student-in-my-class">
        <div>
          <Link to={`/teacher/schedule-today`} style={{ color: "black" }}>
            <span style={{ fontSize: "18px" }}>
              <FontAwesomeIcon
                icon={faHome}
                style={{ fontSize: 19, marginRight: 6 }}
              />
              Lịch dạy
            </span>
          </Link>
          <span style={{ fontSize: "18px" }}> / </span>
          <span style={{ fontSize: "18px" }}>
            <FontAwesomeIcon
              icon={faBook}
              style={{ fontSize: 19, marginRight: 6 }}
            />
            Điểm danh
          </span>{" "}
        </div>
        <div
          className="box-two-student-in-my-class-son"
          style={{ marginTop: "25px", minHeight: "500px" }}
        >
          <div className="button-menu">
            <div>
              <span
                id="menu-checked"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM DANH &nbsp;
              </span>
              {data.length > 0 && (
                <div
                  className="box-center"
                  style={{
                    height: "28.5px",
                    width: "auto",
                    backgroundColor: "#007bff",
                    color: "white",
                    borderRadius: "5px",
                    float: "right",
                  }}
                >
                  <span
                    style={{
                      fontSize: "14px",
                      padding: "15px",
                      fontWeight: 500,
                    }}
                  >
                    {classFind.code}
                  </span>
                </div>
              )}
              <hr />
            </div>
          </div>
          <div className="content-class">
            <div
              style={{
                height: "auto",
                margin: "20px 10px 20px 10px",
              }}
            >
              <span
                style={{
                  paddingTop: "15px",

                  color: "red",
                }}
              >
                Mặc định trạng thái điểm danh của sinh viên là <b>"Có mặt"</b>.
                Giảng viên chuyển từ <b>"Có mặt"</b> thành <b>"Vắng mặt"</b> nếu
                sinh viên vi phạm một trong những nội quy như ra ngoài không lý
                do, mất trật tự,...
              </span>
              <br />
              <span
                style={{
                  paddingTop: "15px",

                  color: "red",
                }}
              >
                Lưu ý: Nếu trưởng nhóm <b>"Vắng mặt"</b>, quyền trưởng nhóm của
                sinh viên sẽ được thay đổi ngẫu nhiên cho thành viên trong nhóm.
              </span>
            </div>
          </div>
          <div>
            {data.length > 0 ? (
              <div className="table-teacher">
                <Table
                  dataSource={data}
                  rowKey="id"
                  columns={columns}
                  pagination={false}
                />
                <div className="notes_attendance">
                  <Row
                    style={{
                      padding: "5px 0px 7px 10%",
                      paddingLeft: "10%",
                      fontWeight: "bold",
                      color: "red",
                      fontSize: "16px",
                    }}
                  >
                    <span
                      style={{
                        fontWeight: 500,
                        color: "red",
                      }}
                    >
                      Đánh giá buổi học :
                    </span>
                  </Row>
                  <Row
                    style={{
                      display: "flex",
                      flexDirection: "column",
                      alignItems: "center",
                    }}
                  >
                    <div />
                    <Input.TextArea
                      value={notes}
                      onChange={(e) => setNotes(e.target.value)}
                      placeholder="Nhập đánh giá"
                      style={{ width: "80%" }}
                      autoSize={{
                        minRows: 5,
                      }}
                    />
                  </Row>
                </div>
              </div>
            ) : (
              <Empty
                imageStyle={{ height: 60 }}
                description={<span>Không có dữ liệu</span>}
              />
            )}
          </div>
          <div className="box-button-center">
            {data.length > 0 && (
              <>
                <div className="box-button" onClick={handleSave}>
                  Lưu điểm danh
                </div>
              </>
            )}
          </div>
        </div>
      </div>
    </>
  );
};
export default TeacherAttendanceMeeting;
