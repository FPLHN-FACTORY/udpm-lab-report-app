import { useParams } from "react-router";
import { ControlOutlined, UnorderedListOutlined } from "@ant-design/icons";
import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { useAppDispatch } from "../../../../app/hook";
import { SetTTrueToggle } from "../../../../app/teacher/TeCollapsedSlice.reducer";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { toast } from "react-toastify";
import { TeacherMeetingAPI } from "../../../../api/teacher/meeting/TeacherMeeting.api";
import { TeacherAttendanceAPI } from "../../../../api/teacher/attendance/TeacherAttendance.api";
import LoadingIndicator from "../../../../helper/loading";
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
    const format = `${date.getDate()}/${
      date.getMonth() + 1
    }/${date.getFullYear()}`;
    return format;
  };
  return (
    <>
      {" "}
      {!loading && <LoadingIndicator />}
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
          style={{ height: "auto" }}
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
                Mã lớp: &nbsp;{classDetail.code}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Mô tả: &nbsp;{classDetail.descriptions}
              </span>
            </div>
          </div>
          <div className="">
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
              <div style={{ height: "auto" }}>
                <table className="ant-table-wrapper">
                  <thead className="ant-table-thead">
                    <tr>
                      <th>#</th>
                      <th>Tên sinh viên</th>
                      <th>Email</th>
                      {column.map((item, index) => (
                        <th
                          key={index}
                          style={{
                            maxWidth: "100px",
                            whiteSpace: "nowrap",
                            overflow: "hidden",
                            textOverflow: "ellipsis",
                          }}
                        >
                          {convertLongToDate(item.meetingDate)}
                          <br></br> {item.nameMeeting}
                        </th>
                      ))}
                      <th>Vắng</th>
                      <th>Tỷ lệ</th>
                    </tr>
                  </thead>
                  <tbody>
                    {data.map((item, rowIndex) => {
                      let countAbsent = 0;
                      let countMeeting = 0;
                      return (
                        <tr key={rowIndex}>
                          <td>{rowIndex + 1}</td>
                          <td>{item.name}</td>
                          <td>{item.email}</td>
                          {item.listAttendance.map((column, colIndex) => {
                            let text = "";
                            countMeeting++;
                            if (column.statusAttendance === "1") {
                              countAbsent++;
                              text = "A";
                            } else {
                              text = "P";
                            }
                            return (
                              <>
                                <td key={colIndex}>
                                  {column.statusAttendance === "0" ? (
                                    <span style={{ color: "green" }}>
                                      {text}
                                    </span>
                                  ) : column.statusAttendance === "1" ? (
                                    <span style={{ color: "red" }}>{text}</span>
                                  ) : (
                                    <span>-</span>
                                  )}
                                </td>
                              </>
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
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default TeacherAttendanceClass;
