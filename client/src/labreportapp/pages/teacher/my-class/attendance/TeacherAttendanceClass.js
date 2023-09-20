import "./styleTeacherAttendance.css";
import { useParams } from "react-router";
import { ControlOutlined } from "@ant-design/icons";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { useAppDispatch } from "../../../../app/hook";
import { SetTTrueToggle } from "../../../../app/teacher/TeCollapsedSlice.reducer";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { toast } from "react-toastify";
import { TeacherMeetingAPI } from "../../../../api/teacher/meeting/TeacherMeeting.api";
import { TeacherAttendanceAPI } from "../../../../api/teacher/attendance/TeacherAttendance.api";
import LoadingIndicator from "../../../../helper/loading";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTableList } from "@fortawesome/free-solid-svg-icons";
import { Empty } from "antd";
const TeacherAttendanceClass = () => {
  const { idClass } = useParams();
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const [data, setData] = useState([]);
  const [column, setColumn] = useState([]);
  const [classDetail, setClassDetail] = useState({});
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    featchClass(idClass);
    featchColumn(idClass);
    featchTable(idClass);
  }, []);
  const featchColumn = async (idClass) => {
    try {
      await TeacherMeetingAPI.getColumnMeetingByIdClass(idClass).then(
        (response) => {
          setColumn(response.data.data);
          console.log("locoasmaa");
          console.log(response.data.data);
        }
      );
    } catch (error) {
      toast.error(error.data.message);
    }
  };
  const featchTable = async (idClass) => {
    try {
      await TeacherAttendanceAPI.getAllAttendanceByIdClass(idClass)
        .then((responese) => {
          setData(responese.data.data);
          console.log("dâttattattattattattattattattatt");
          console.log(responese.data.data);
          setLoading(true);
        })
        .catch((err) => {
          toast.error(err.data.message);
        });
    } catch (error) {
      alert(error.message);
    }
  };
  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassDetail(responese.data.data);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const format = `${date.getDate()}/${date.getMonth() + 1}`;
    return format;
  };
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="attendance-all">
        <div className="box-one">
          <Link to="/teacher/my-class" style={{ color: "black" }}>
            <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
              <ControlOutlined style={{ fontSize: "22px" }} />
              <span style={{ marginLeft: "10px", fontWeight: "500" }}>
                Bảng điều khiển
              </span>
              <span style={{ color: "gray", fontSize: "14px" }}>
                - Điểm danh
              </span>
            </span>
          </Link>
        </div>
        <div className="box-two-student-in-my-class">
          <div
            className="box-two-student-in-my-class-son"
            style={{ height: "auto", minHeight: "570px" }}
          >
            <div className="button-menu">
              <div>
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
                  <span
                    style={{
                      fontSize: "14px",
                      padding: "15px",
                      fontWeight: 500,
                    }}
                  >
                    {classDetail.code}
                  </span>
                </div>
                <hr />
              </div>
            </div>
            <div className="info-team">
              <div className="group-info">
                <span
                  className="group-info-item"
                  style={{ marginTop: "13px", marginBottom: "15px" }}
                >
                  Tên lớp: &nbsp;{classDetail.code}
                </span>
                <span
                  className="group-info-item"
                  style={{ marginTop: "13px", marginBottom: "15px" }}
                >
                  Mô tả: &nbsp;{classDetail.descriptions}
                </span>
              </div>
            </div>
            <div style={{ overflowX: "auto" }}>
              <div style={{ margin: "15px 0px 15px 15px" }}>
                <span style={{ fontSize: "17px", fontWeight: 500 }}>
                  <FontAwesomeIcon
                    icon={faTableList}
                    style={{
                      marginRight: "10px",
                      fontSize: "20px",
                    }}
                  />
                  Chi tiết điểm danh :
                </span>
              </div>
              {data !== null ? (
                <table
                  className="custom-table-teacher"
                  style={{
                    width: "100%",
                    borderCollapse: "collapse",
                    border: "none",
                  }}
                >
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>Họ và tên</th>
                      <th>Email</th>
                      {column.length > 0 &&
                        column.map((item, index) => (
                          <th className="column-AP-teacher" key={index}>
                            {convertLongToDate(item.meetingDate)}
                            <br />
                            <span>{item.nameMeeting}</span>
                          </th>
                        ))}
                      <th>Vắng</th>
                      <th>Tỷ lệ</th>
                    </tr>
                  </thead>
                  <tbody>
                    {data != null &&
                      data.map((item, rowIndex) => {
                        let countAbsent = 0;
                        let countMeeting = 0;
                        return (
                          <tr key={rowIndex}>
                            <td style={{ padding: "4px" }}>{rowIndex + 1}</td>
                            <td style={{ textAlign: "left" }}>{item.name}</td>
                            <td style={{ textAlign: "left" }}>{item.email}</td>
                            {item.listAttendance != null &&
                              item.listAttendance.length > 0 &&
                              item.listAttendance.map((column, colIndex) => {
                                countMeeting++;
                                if (column.statusAttendance === "1") {
                                  countAbsent++;
                                }
                                return (
                                  <td key={colIndex}>
                                    {column.statusAttendance === "0" ? (
                                      <span style={{ color: "green" }}>A</span>
                                    ) : column.statusAttendance === "1" ? (
                                      <span style={{ color: "red" }}>P</span>
                                    ) : (
                                      <span>-</span>
                                    )}
                                  </td>
                                );
                              })}
                            <td>
                              {parseFloat(countAbsent / countMeeting) * 100}%
                            </td>
                            <td>{countAbsent + `/` + countMeeting}</td>
                          </tr>
                        );
                      })}
                  </tbody>
                </table>
              ) : (
                <Empty
                  imageStyle={{ height: 60 }}
                  style={{
                    padding: "20px 0px 20px 0",
                  }}
                  description={<span>Không có thông tin buổi học</span>}
                />
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default TeacherAttendanceClass;
