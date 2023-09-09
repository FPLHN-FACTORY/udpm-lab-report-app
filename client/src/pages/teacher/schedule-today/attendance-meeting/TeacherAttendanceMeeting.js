import { useEffect } from "react";
import "./styleTeacherAttendance.css";
import { useParams } from "react-router";
import { TeacherStudentClassesAPI } from "../../../../api/teacher/student-class/TeacherStudentClasses.api";
import { TeacherAttendanceAPI } from "../../../../api/teacher/attendance/TeacherAttendance.api";
import { useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherMeetingAPI } from "../../../../api/teacher/meeting/TeacherMeeting.api";
import { Link } from "react-router-dom";
import CustomSwitch from "./CustomSwitch";
import { Table } from "antd";
import { toast } from "react-toastify";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import {
  SetAttendanceMeeting,
  GetAttendanceMeeting,
  UpdateAttendanceMeeting,
} from "../../../../app/teacher/attendance-meeting-today/teacherAttendanceMeetingSlice.reduce";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBook, faHome } from "@fortawesome/free-solid-svg-icons";

const TeacherAttendanceMeeting = () => {
  const dispatch = useAppDispatch();
  const { idMeeting } = useParams();
  const [meeting, setMeeting] = useState({});
  const [classFind, setClassFind] = useState({});
  const [listStudentClassAPI, setListStudentClassAPI] = useState([]);
  const [loadingData, setLoadingData] = useState(false);
  const [loading, setLoading] = useState(false);
  const [idClass, setIdClass] = useState("");
  const [checkAttendance, setCheckAttendance] = useState(false);
  const [listAttendance, setListAttendance] = useState([]);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - điểm danh";
    featchMeetingCheckDate(idMeeting);
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
      alert("Lỗi hệ thống, vui lòng F5 lại trang");
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
    } catch (error) {
      alert(error.message);
    }
  };

  const featInforStudent = async (idClass) => {
    try {
      if (checkAttendance) {
        const listShowTable = listStudentClassAPI.map((item1) => {
          const matchedObject = listAttendance.find(
            (item2) => item2.idStudent === item1.idStudent
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
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
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
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang");
    }
  };

  const featchMeetingCheckDate = async (id) => {
    setLoading(false);
    try {
      await TeacherMeetingAPI.getAndCheckMeetingById(id).then(
        (response) => {
          setMeeting(response.data.data);
          setIdClass(response.data.data.idClass);
          fetchData(response.data.data.idClass);
        },
        (error) => {
          setLoading(true);
          toast.error(error.response.data.message);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang");
    }
  };
  useEffect(() => {
    if (loadingData === true) {
      fetchData(idClass);
    }
  }, [loadingData]);

  const handleSave = async () => {
    try {
      let dataFind = { listAttendance: data };
      await TeacherAttendanceAPI.createOrUpdate(dataFind).then((respone) => {
        dispatch(UpdateAttendanceMeeting(respone.data.data));
        toast.success("Lưu điểm danh thành công !");
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
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
      title: "Mã sinh viên",
      dataIndex: "code",
      key: "code",
      render: (text, record, index) => {
        const countSpace = (record.name.match(/ /g) || []).length;
        const lastSpaceIndex = record.name.lastIndexOf(" ");
        const wordCount =
          lastSpaceIndex >= 0
            ? record.name.substring(lastSpaceIndex + 1).length
            : 0;
        const nameIndexCut = countSpace + wordCount;
        const codeShow = record.username.substring(nameIndexCut).toUpperCase();
        return <span style={{ color: "#007bff" }}>{codeShow}</span>;
      },
    },

    {
      title: "Tên sinh viên",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      sorter: (a, b) => a.email.localeCompare(b.email),
    },
    {
      title: "Trạng thái",
      dataIndex: "statusAttendance",
      key: "statusAttendance",
      width: "30%",
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
            </span>{" "}
          </Link>
          <span style={{ fontSize: "18px" }}> / </span>{" "}
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
          style={{ marginTop: "25px" }}
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
                  {" "}
                  <span style={{ fontSize: "14px", padding: "10px" }}>
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
                  fontWeight: 500,
                  color: "red",
                }}
              >
                Mặc định trạng thái điểm danh của sinh viên là "Có mặt". Giảng
                viên chuyển từ "Có mặt" thành "Vắng mặt" nếu sinh viên vi phạm
                một trong những nội quy như ra ngoài không lý do, mất trật tự,..
              </span>
            </div>
          </div>
          <div style={{ minHeight: "200px" }}>
            {data.length > 0 ? (
              <>
                <div className="table">
                  <Table
                    dataSource={data}
                    rowKey="id"
                    columns={columns}
                    pagination={false}
                  />
                </div>
              </>
            ) : (
              <>
                <p
                  style={{
                    textAlign: "center",
                    marginTop: "100px",
                    fontSize: "15px",
                    color: "red",
                  }}
                >
                  Không có thông tin sinh viên
                </p>
              </>
            )}
          </div>
          <div className="box-button-center">
            {data.length > 0 && (
              <div className="box-button" onClick={handleSave}>
                Lưu điểm danh
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
};
export default TeacherAttendanceMeeting;
