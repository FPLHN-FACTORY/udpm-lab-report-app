import "./styleTeacherScheduleToday.css";
import { giangVienCurrent } from "../../../helper/inForUser";
import { useState } from "react";
import { useEffect } from "react";
import { ProjectOutlined } from "@ant-design/icons";
import { Link } from "react-router-dom";
import LoadingIndicator from "../../../helper/loading";
import { Input, Table, Tooltip } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome, faUpload } from "@fortawesome/free-solid-svg-icons";
import { TeacherScheduleTodayAPI } from "../../../api/teacher/meeting/schedule-today/TeacherScheduleToday.api";
import { toast } from "react-toastify";
const TeacherScheduleToday = () => {
  const [loading, setLoading] = useState(false);
  const [dataToday, setDataToday] = useState([]);

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - lịch dạy hôm nay";
    featchData(giangVienCurrent.id);
  }, []);

  const featchData = async (idTeacher) => {
    setLoading(false);
    try {
      await TeacherScheduleTodayAPI.getAllByIdTe(idTeacher).then((response) => {
        setDataToday(response.data.data);
      });
      setLoading(true);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  const handleDescriptionChange = (idMeeting, value) => {
    const listNew = dataToday.map((item) => {
      if (item.idMeeting === idMeeting) {
        return {
          ...item,
          descriptionsMeeting: value,
        };
      }
      return item;
    });
    setDataToday(listNew);
  };

  const updateDescription = async () => {
    try {
      let dataUp = {
        idTeacher: giangVienCurrent.id,
        listMeeting: dataToday,
      };
      await TeacherScheduleTodayAPI.updateDescriptionMeeting(dataUp).then(
        (response) => {
          setDataToday(response.data.data);
          toast.success("Lưu link học thành công");
        }
      );
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

  const data = dataToday;
  const columns = [
    {
      title: "Mã lớp",
      dataIndex: "codeClass",
      key: "codeClass",
      width: "15%",
    },
    {
      title: "Ngày",
      dataIndex: "meetingDate",
      key: "meetingDate",
      render: (text, record) => {
        return <span>{convertLongToDate(text)}</span>;
      },
      width: "5%",
    },
    {
      title: "Tên buổi học",
      dataIndex: "meetingName",
      key: "meetingName",
      width: "25%",
    },
    {
      title: "Phòng",
      dataIndex: "typeMeeting",
      key: "typeMeeting",
      render: (text, record) => {
        return <span>{text === 0 ? "Online" : "Offline"}</span>;
      },
      width: "5%",
    },

    {
      title: "Địa điểm",
      dataIndex: "meetingAddress",
      key: "meetingAddress",
      width: "13%",
    },
    {
      title: "Ca học",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",

      render: (text, record) => {
        return <span>{text + 1}</span>;
      },
      width: "8%",
    },
    {
      title: "Link học trực tuyến",
      dataIndex: "descriptionsMeeting",
      key: "descriptionsMeeting",
      render: (text, record) => {
        return (
          <div key={record.idMeeting}>
            {" "}
            <Input
              style={{ width: "80%" }}
              type="text"
              value={text}
              readOnly
              onDoubleClick={(e) => {
                e.target.readOnly = false;
                e.target.select();
              }}
              onBlur={(e) => {
                e.target.readOnly = true;
              }}
              onChange={(e) => {
                handleDescriptionChange(record.idMeeting, e.target.value);
              }}
            />
            <span className="box-icon-square">
              <Tooltip title="Upload">
                <FontAwesomeIcon
                  icon={faUpload}
                  style={{ color: "white", fontSize: 21 }}
                  onClick={updateDescription}
                />
              </Tooltip>
            </span>
          </div>
        );
      },
      width: "28%",
    },

    {
      title: "Điểm danh",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <>
          <div className="box_icon">
            <Link
              to={`/teacher/schedule-today/attendance/${record.idMeeting}`}
              className="btn btn-success ml-4"
            >
              <Tooltip title="Xem chi tiết">
                <div
                  className="box-center"
                  style={{
                    height: "30px",
                    width: "120px",
                    backgroundColor: "#007bff",
                    color: "white",
                    borderRadius: "5px",
                  }}
                >
                  {" "}
                  <span style={{ fontSize: "14px" }}> Xem - chỉnh sửa </span>
                </div>
              </Tooltip>
            </Link>
          </div>
        </>
      ),
      width: "10%",
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
          </span>
        </Link>
      </div>
      <div className="box-two-student-in-my-class">
        <div className="box-two-student-in-my-class-son">
          <div className="button-menu">
            <Link
              to="/teacher/schedule-today"
              style={{ fontSize: "17px", fontWeight: "blod" }}
              id="menu-checked"
            >
              &nbsp; LỊCH DẠY HÔM NAY &nbsp;
            </Link>
            <Link
              to="/teacher/my-class"
              className="custom-link"
              style={{
                fontSize: "17px",
                paddingLeft: "10px",
                fontWeight: "bold",
              }}
            >
              LỚP CỦA TÔI &nbsp;
            </Link>
            <hr />
          </div>
          <div
            className="title-box-two"
            style={{ paddingTop: "20px", minHeight: "500px", height: "auto" }}
          >
            <div className="title-table">
              <div>
                {" "}
                <span style={{ fontSize: "17px", fontWeight: "500" }}>
                  {" "}
                  <ProjectOutlined
                    style={{ marginRight: "10px", fontSize: "24px" }}
                  />
                  Danh sách ca dạy
                </span>
              </div>
            </div>
            <div className="table" style={{ marginTop: "30px" }}>
              {dataToday.length > 0 ? (
                <Table
                  dataSource={data}
                  rowKey="idMeeting"
                  columns={columns}
                  pagination={false}
                />
              ) : (
                <p
                  style={{
                    textAlign: "center",
                    marginTop: "100px",
                    fontSize: "15px",
                    color: "red",
                  }}
                >
                  Không có ca dạy
                </p>
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default TeacherScheduleToday;
