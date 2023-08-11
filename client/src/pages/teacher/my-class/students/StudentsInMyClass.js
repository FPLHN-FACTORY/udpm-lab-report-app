import { useParams } from "react-router-dom";
import "./styleStudentsInMyClass.css";
import { Modal, Row, Col, Input, Table, Image, Pagination, Button } from "antd";
import { Link } from "react-router-dom";
import {
  ControlOutlined,
  QuestionCircleFilled,
  ProjectOutlined,
} from "@ant-design/icons";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherStudentClassesAPI } from "../../../../api/teacher/student-class/TeacherStudentClasses.api";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import moment from "moment";
const { TextArea } = Input;

const StudentsInMyClass = () => {
  const [classDetail, setClassDetail] = useState({});
  const [listStudentClass, setListStudentClass] = useState([]);
  const [loading, setLoading] = useState(false);
  const { id } = useParams();
  useEffect(() => {
    window.scrollTo(0, 0);
    featInforStudent();
    featchClass(id);
    featchStudentClass(id);
  }, []);

  const featchClass = async (id) => {
    setLoading(false);
    try {
      await TeacherMyClassAPI.detailMyClass(id).then((responese) => {
        setLoading(true);
        setClassDetail(responese.data.data);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang aaaaaaaaaa!");
    }
  };

  const featInforStudent = async () => {
    setLoading(false);

    // let list = [];
    // list = listStudentClass;
    try {
      console.log("Danh sachs infor");
      featchStudentClass(id);
      if (listStudentClass.length >= 1) {
        let request = `?id=`;
        if (listStudentClass.length >= 1) {
          for (let i = 1; i <= listStudentClass.length; i++) {
            if (i === listStudentClass.length) {
              request += listStudentClass[i].id + `|`;
            } else {
              request += listStudentClass[i].id;
            }
          }
        }
        console.log("requesnt");
        console.log(request);

        // const re = await TeacherStudentClassesAPI.getAllInforStudent();
      }
      // truy xuất nhiều bằng cách https://63ddb6cff1af41051b085b6d.mockapi.io/sinh-vien?id= jdajjsajdsaj | ádadad
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang do api sinh vien!");
    }
  };

  const featchStudentClass = async (id) => {
    setLoading(false);
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(id).then(
        (responese) => {
          setListStudentClass(responese.data.data);
          setLoading(true);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang aaaaaaaa!");
    }
  };
  // const data = useAppSelector(GetTeacherMyClass);
  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt - b.stt,
      width: "3%",
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
      sorter: (a, b) => a.descriptions.localeCompare(b.descriptions),
      width: "9%",
    },
    {
      title: "Mã nhóm",
      dataIndex: "level",
      key: "level",
      sorter: (a, b) => a.level - b.level,
      width: "7%",
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      sorter: (a, b) => a.status.localeCompare(b.status),
      width: "7%",
    },
  ];
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="title-teacher-my-class">
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{ fontSize: "18px", marginLeft: "10px", fontWeight: "500" }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - lớp của tôi</span>
        </span>
      </div>
      <div className="box-students-in-class">
        <div className="button-menu-teacher">
          <div
            className="box-center"
            style={{
              height: "30px",
              width: "180px",
              backgroundColor: "#007bff",
              color: "white",
              margin: "0px 0px 4px 10px",
            }}
          >
            {" "}
            <span>
              {classDetail.classSize} thành viên | Level:{" "}
              {classDetail.activityLevel}
            </span>
          </div>
          <div>
            <Link
              to={`/teacher/my-class/students-in-class/${id}`}
              id="menu-checked"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
              }}
            >
              THÀNH VIÊN TRONG LỚP &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/students-in-class/${id}`}
              className="custom-link"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              ĐIỂM DANH &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/students-in-class/${id}`}
              className="custom-link"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              QUẢN LÝ NHÓM &nbsp;
            </Link>
            <hr />
          </div>
        </div>
        <div className="content">
          <Row gutter={16} style={{ marginBottom: "15px", marginTop: "15px" }}>
            <Col span={8}>
              <span>Hoạt động: &nbsp; {classDetail.activityName}</span>
            </Col>
            <Col span={8}>
              <span>
                Thời gian bắt đầu:&nbsp;
                {moment(classDetail.startTime).format("DD-MM-YYYY")}
              </span>{" "}
            </Col>
            <Col span={8}>
              <span>Ca học: &nbsp;{classDetail.classPeriod}</span>
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={8}>
              <span>Mã lớp: &nbsp;{classDetail.code}</span>
            </Col>
            <Col span={8}>
              <span>Tên lớp: &nbsp;{classDetail.name}</span>
            </Col>
            <Col span={8}>
              <span>Mật khẩu: &nbsp;{classDetail.passWord}</span>
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>Mô tả: &nbsp;{classDetail.descriptions}</Col>
          </Row>
          <br />
        </div>
        {/* <div>
          {listMyClass.length > 0 ? (
            <>
              <div className="table">
                <Table
                  dataSource={data}
                  rowKey="id"
                  columns={columns}
                  pagination={false}
                />
              </div>
              <div className="pagination_box">
                <Pagination
                  simple
                  current={current}
                  onChange={(value) => {
                    setCurrent(value);
                  }}
                  total={totalPages * 10}
                />
              </div>
            </>
          ) : (
            <>
              <p style={{ textAlign: "center", marginTop: "100px" }}>
                Không có lớp học
              </p>
            </>
          )}
        </div> */}
      </div>
    </>
  );
};

export default StudentsInMyClass;
