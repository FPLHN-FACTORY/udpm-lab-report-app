import { useParams } from "react-router";
import { UnorderedListOutlined } from "@ant-design/icons";
import FloatingDiv from "../../floatingDiv/FloatingDiv";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome } from "@fortawesome/free-solid-svg-icons";
const TeacherAttendanceClass = () => {
  const { idClass } = useParams();
  return (
    <>
      <div className="box-one">
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
              - điểm danh
            </span>
          </span>
        </Link>
      </div>
      <div className="box-two" style={{ minHeight: "580px" }}>
        <div className="button-menu">
          <div>
            <Link
              to={`/teacher/my-class/students/${idClass}`}
              className="custom-link"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                fontWeight: "bold",
              }}
            >
              THÀNH VIÊN TRONG LỚP &nbsp;
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
              <span style={{ fontSize: "14px", padding: "10px" }}>aaaaaaa</span>
            </div>
            <hr />
          </div>
        </div>
        <div className="menu-teacher-search">
          <div>
            <div style={{ margin: "30px 0px 20px 0px" }}>
              {" "}
              <span style={{ fontSize: "17px", fontWeight: "500" }}>
                {" "}
                <UnorderedListOutlined
                  style={{ marginRight: "10px", fontSize: "20px" }}
                />
                Chi tiết điểm danh
              </span>
            </div>
            <div className="data-table"></div>
          </div>
        </div>
      </div>
    </>
  );
};
export default TeacherAttendanceClass;
