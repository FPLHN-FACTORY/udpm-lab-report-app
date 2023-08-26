import { useParams } from "react-router-dom";
import "./styleStudentsInMyClass.css";
import { Row, Col, Table } from "antd";
import { Link } from "react-router-dom";
import { ControlOutlined } from "@ant-design/icons";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherStudentClassesAPI } from "../../../../api/teacher/student-class/TeacherStudentClasses.api";
import {
  GetStudentClasses,
  SetStudentClasses,
} from "../../../../app/teacher/student-class/studentClassesSlice.reduce";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import moment from "moment";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome } from "@fortawesome/free-solid-svg-icons";

const StudentsInMyClass = () => {
  const dispatch = useAppDispatch();
  const [classDetail, setClassDetail] = useState({});
  const [listStudentClass, setListStudentClass] = useState([]);
  const [dataTable, setDataTable] = useState([]);
  const [loading, setLoading] = useState(false);
  const [loadingStudentClass, setLoadingStudentClass] = useState(false);
  const { idClass } = useParams();

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - thành viên";
    featchClass(idClass);
  }, []);

  const fetchData = async (idClass) => {
    await Promise.all([featchStudentClass(idClass), featInforStudent(idClass)]);
  };

  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassDetail(responese.data.data);
        fetchData(idClass);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const featchStudentClass = async (id) => {
    setLoading(false);
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(id).then(
        (responese) => {
          setListStudentClass(responese.data.data);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const featInforStudent = async (idClass) => {
    try {
      let request = listStudentClass.map((item) => item.idStudent).join("|");
      const listStudentAPI = await TeacherStudentClassesAPI.getAllInforStudent(
        `?id=` + request
      );
      const listShowTable = listStudentAPI.data
        .filter((item1) =>
          listStudentClass.some((item2) => item1.id === item2.idStudent)
        )
        .map((item1) => {
          const matchedObject = listStudentClass.find(
            (item2) => item2.idStudent === item1.id
          );
          return {
            ...item1,
            ...matchedObject,
          };
        });
      setDataTable(listShowTable);
      dispatch(SetStudentClasses(listShowTable));
      setLoading(true);
      setLoadingStudentClass(true);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  useEffect(() => {
    if (loadingStudentClass === true) {
      fetchData(idClass);
    }
  }, [loadingStudentClass]);

  const data = useAppSelector(GetStudentClasses);
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
      width: "12px",
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
      width: "130px",
    },
    {
      title: "Nhóm",
      dataIndex: "nameTeam",
      key: "nameTeam",
      render: (text, record) => {
        if (text === null) {
          return <span style={{ color: "blue" }}>Chưa vào nhóm</span>;
        } else {
          return <span>{text}</span>;
        }
      },
      width: "150px",
    },
    {
      title: "Họ và tên",
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
      dataIndex: "statusStudent",
      key: "statusStudent",
      render: (text) => {
        if (text === "0") {
          return <span style={{ color: "green" }}>HD</span>;
        } else {
          return <span style={{ color: "red" }}>HL</span>;
        }
      },
      width: "120px",
    },
  ];
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="title-teacher-my-class">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <FontAwesomeIcon
              icon={faHome}
              style={{ color: "#00000", fontSize: "23px" }}
            />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>{" "}
            <span style={{ color: "gray", fontSize: "14px" }}>
              {" "}
              - thành viên
            </span>
          </span>
        </Link>
      </div>
      <div className="box-students-in-class">
        <div className="button-menu-teacher">
          <div>
            <Link
              to={`/teacher/my-class/students/${idClass}`}
              id="menu-checked"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
              }}
            >
              THÀNH VIÊN TRONG LỚP &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/students-in-class/${idClass}`}
              className="custom-link"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              ĐIỂM DANH &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/teams/${idClass}`}
              className="custom-link"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              QUẢN LÝ NHÓM &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/meeting/${idClass}`}
              className="custom-link"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              BUỔI HỌC &nbsp;
            </Link>
            <hr />
          </div>
        </div>

        <div className="content-class">
          <div
            className="box-center"
            style={{
              height: "30px",
              width: "200px",
              backgroundColor: "#007bff",
              color: "white",
              borderRadius: "5px",
              margin: "5px 0px 0px 84.4%",
            }}
          >
            {" "}
            <span style={{ fontSize: "14px" }}>
              {classDetail.classSize} thành viên{" "}
              <span style={{ color: "yellow" }}>| </span> Level{"  "}
              {classDetail.activityLevel + 1}
              <span style={{ color: "yellow" }}>| </span> Ca{"  "}
              {classDetail.classPeriod + 1}
            </span>
          </div>
          <Row gutter={16} style={{ marginBottom: "4px" }}>
            <Col span={24}>
              <span>Hoạt động: &nbsp; {classDetail.activityName}</span>
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "4px" }}>
            {" "}
            <Col span={24}>
              <span>
                Thời gian bắt đầu:&nbsp;
                {moment(classDetail.startTime).format("DD-MM-YYYY")}
              </span>{" "}
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "4px" }}>
            <Col>
              <span>Mã lớp: &nbsp;{classDetail.code}</span>
            </Col>
          </Row>
          <Row style={{ marginBottom: "4px" }}>
            <Col>
              <span>Tên lớp: &nbsp;{classDetail.name}</span>
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "4px" }}>
            <Col span={24}>
              <span>Mô tả: &nbsp;{classDetail.descriptions}</span>
            </Col>
          </Row>
          <Row>
            <Col>
              <span>Mật khẩu: &nbsp;{classDetail.passWord}</span>
            </Col>
          </Row>
          <br />
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

export default StudentsInMyClass;
