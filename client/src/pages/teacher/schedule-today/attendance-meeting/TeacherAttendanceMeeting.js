import { useEffect } from "react";
import "./styleTeacherAttendance.css";
import { useParams } from "react-router";
import { TeacherStudentClassesAPI } from "../../../../api/teacher/student-class/TeacherStudentClasses.api";
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
} from "../../../../app/teacher/attendance-meeting-today/teacherAttendanceMeetingSlice.reduce";
const TeacherAttendanceMeeting = () => {
  const dispatch = useAppDispatch();
  const { idMeeting } = useParams();
  const [classFind, setClassFind] = useState({});
  const [listStudentClassAPI, setListStudentClassAPI] = useState([]);
  const [dataTable, setDataTable] = useState([]);
  const [loadingData, setLoadingData] = useState(false);
  const [loading, setLoading] = useState(false);
  const [idClass, setIdClass] = useState("");
  useEffect(() => {
    featchMeeting(idMeeting);
  }, []);

  const fetchData = async (idClass) => {
    await Promise.all([
      featchClass(idClass),
      featchStudentClass(idClass),
      featInforStudent(idClass),
    ]);
  };

  const featchMeeting = async (id) => {
    try {
      await TeacherMeetingAPI.getAndCheckMeetingById(id).then(
        (response) => {
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

  const featchClass = async (idClass) => {
    setLoading(false);
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassFind(responese.data.data);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang");
    }
  };

  const featchStudentClass = async (id) => {
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(id).then(
        (responese) => {
          setListStudentClassAPI(responese.data.data);
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
          return {
            ...item1,
            // ...matchedObject,
            idMeeting: idMeeting,
            idStudent: item1.id,
          };
        });
      console.log("----------------------------------------------------");
      console.log(listShowTable);
      setDataTable(listShowTable);
      //   dispatch(SetStudentClasses(listShowTable));
      setLoading(true);
      setLoadingData(true);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  const featchStudentAttendance = async () => {
    try {
    } catch (error) {
      alert(error.message);
    }
  };
  useEffect(() => {
    if (loadingData === true) {
      fetchData(idClass);
    }
  }, [loadingData]);

  const data = dataTable;
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
      dataIndex: "status",
      key: "status",
      width: "30%",
      render: (text) => {
        return (
          <>
            <CustomSwitch
              leftLabel="Có mặt"
              rightLabel="Vắng mặt"
              status={text}
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
            <ControlOutlined style={{ fontSize: "22px" }} />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
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
      </div>
    </>
  );
};
export default TeacherAttendanceMeeting;
