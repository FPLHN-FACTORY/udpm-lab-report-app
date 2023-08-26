import { useEffect } from "react";
import "./styleTeacherAttendance.css";
import { useParams } from "react-router";
import { TeacherStudentClassesAPI } from "../../../../api/teacher/student-class/TeacherStudentClasses.api";
import { TeacherAttendanceAPI } from "../../../../api/teacher/attendance/TeacherAttendance.api";
import { useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import { ControlOutlined } from "@ant-design/icons";
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
import { faHome } from "@fortawesome/free-solid-svg-icons";

const TeacherAttendanceMeeting = () => {
  const dispatch = useAppDispatch();
  const { idMeeting } = useParams();
  const [meeting, setMeeting] = useState({});
  const [classFind, setClassFind] = useState({});
  const [listStudentClassAPI, setListStudentClassAPI] = useState([]);
  const [dataTable, setDataTable] = useState([]);
  const [loadingData, setLoadingData] = useState(false);
  const [loading, setLoading] = useState(false);
  const [idClass, setIdClass] = useState("");
  useEffect(() => {
    featchMeetingCheckDate(idMeeting);
  }, []);

  const fetchData = async (idClass) => {
    await Promise.all([featchClass(idClass), featchAttendance(idClass)]);
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
            setListStudentClassAPI(response.data.data);
            featInforStudent(idClass);
          } else {
            featchStudentClass(idClass);
            featInforStudent(idClass);
          }
        }
      );
    } catch (error) {
      alert(error.message);
    }
  };

  const featchStudentClass = async (idClass) => {
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(idClass).then(
        (responese) => {
          const listAPI = responese.data.data.map((item) => {
            return { ...item, statusAttendance: "0" };
          });
          setListStudentClassAPI(listAPI);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang");
    }
  };

  const featInforStudent = async (idClass) => {
    try {
      let request = listStudentClassAPI.map((item) => item.idStudent).join("|");
      const listStudentAPI = await TeacherStudentClassesAPI.getAllInforStudent(
        `?id=` + request
      );
      const listShowTable = listStudentAPI.data
        .filter((item1) =>
          listStudentClassAPI.some((item2) => item1.id === item2.idStudent)
        )
        .map((item1) => {
          const matchedObject = listStudentClassAPI.find(
            (item2) => item2.idStudent === item1.id
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
      setDataTable(listShowTable);
      dispatch(SetAttendanceMeeting(listShowTable));
      if (loadingData === true) {
        setLoading(true);
      }
      setLoadingData(true);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
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
      <div className="title-teacher-my-class">
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
              - điểm danh
            </span>
          </span>
        </Link>
      </div>
      <div className="box-students-in-class">
        <div className="button-menu-teacher">
          <div>
            <span
              id="menu-checked"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
              }}
            >
              ĐIỂM DANH &nbsp;
            </span>
            <hr />
          </div>
        </div>
        <div
          className="box-center"
          style={{
            height: "30px",
            width: "200px",
            backgroundColor: "#007bff",
            color: "white",
            borderRadius: "5px",
            margin: "5px 0px 0px 83.5%",
          }}
        >
          {" "}
          <span style={{ fontSize: "14px" }}>Mã lớp: {classFind.code}</span>
        </div>

        <div>
          {dataTable.length > 0 ? (
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
                Không có thành viên
              </p>
            </>
          )}
        </div>
        <div className="box-button-center">
          <div className="box-button" onClick={handleSave}>
            Lưu điểm danh
          </div>
        </div>
      </div>
    </>
  );
};
export default TeacherAttendanceMeeting;
