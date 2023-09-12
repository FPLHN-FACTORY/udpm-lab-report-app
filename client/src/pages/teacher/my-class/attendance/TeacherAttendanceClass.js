import { useParams } from "react-router";
import { ControlOutlined, UnorderedListOutlined } from "@ant-design/icons";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome } from "@fortawesome/free-solid-svg-icons";
import { TeacherStudentClassesAPI } from "../../../../api/teacher/student-class/TeacherStudentClasses.api";
import { useEffect, useState } from "react";
import { Table } from "antd";
import { useAppDispatch } from "../../../../app/hook";
import { SetTTrueToggle } from "../../../../app/teacher/TeCollapsedSlice.reducer";
const TeacherAttendanceClass = () => {
  const { idClass } = useParams();
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const [listStudentClassAPI, setListStudentClassAPI] = useState([]);

  useEffect(() => {
    featchStudentClass(idClass);
  }, []);

  const featchStudentClass = async (idClass) => {
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(idClass).then(
        (responese) => {
          const listAPI = responese.data.data.map((item) => {
            return { ...item };
          });
          setListStudentClassAPI(listAPI);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang");
    }
  };

  return (
    <>
      <div className="box-one">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <ControlOutlined style={{ fontSize: "22px" }} />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>{" "}
            <span style={{ color: "gray", fontSize: "14px" }}>
              {" "}
              - Điểm danh
            </span>
          </span>
        </Link>
      </div>

      <div className="box-two-student-in-my-class">
        <div
          className="box-two-student-in-my-class-son"
          style={{ height: "580px" }}
        >
          <div className="button-menu">
            <div>
              {" "}
              <Link
                to={`/teacher/my-class/post/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                BÀI ĐĂNG &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/students/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                THÔNG TIN LỚP HỌC &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/teams/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                QUẢN LÝ NHÓM &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/meeting/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                BUỔI HỌC &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/attendance/${idClass}`}
                id="menu-checked"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM DANH &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/point/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM &nbsp;
              </Link>
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
                  aaaaaaa
                </span>
              </div>
              <hr />
            </div>
          </div>
          <div className="menu-teacher-search">
            <div>
              <div style={{ margin: "15px 0px 15px 0px" }}>
                {" "}
                <span style={{ fontSize: "17px", fontWeight: "500" }}>
                  {" "}
                  <UnorderedListOutlined
                    style={{ marginRight: "10px", fontSize: "20px" }}
                  />
                  Chi tiết điểm danh
                </span>
              </div>
              <div className="data-table">
                <Table
                  rowKey="id"
                  // columns={columns}
                  // dataSource={dataSource}
                  pagination={false}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default TeacherAttendanceClass;
