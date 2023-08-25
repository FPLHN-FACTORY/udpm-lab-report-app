import "./styleTeacherScheduleToday.css";
import { giangVienCurrent } from "../../../helper/inForUser";
import { useState } from "react";
import { useEffect } from "react";
import { ControlOutlined, ProjectOutlined } from "@ant-design/icons";
import { Link } from "react-router-dom";
import LoadingIndicator from "../../../helper/loading";
import { Input, Space, Switch, Table, Tooltip } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faL, faUpload } from "@fortawesome/free-solid-svg-icons";
import { TeacherScheduleTodayAPI } from "../../../api/teacher/meeting/schedule-today/TeacherScheduleToday.api";
import { each } from "lodash";
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

          toast.success("Lưu thành công");
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
    },
    {
      title: "Ngày",
      dataIndex: "meetingDate",
      key: "meetingDate",
      render: (text, record) => {
        return <span>{convertLongToDate(text)}</span>;
      },
    },

    {
      title: "Tên buổi học",
      dataIndex: "meetingName",
      key: "meetingName",
    },
    {
      title: "Phòng",
      dataIndex: "typeMeeting",
      key: "typeMeeting",
      render: (text, record) => {
        return <span>{text === 0 ? "Online" : "Offline"}</span>;
      },
    },

    {
      title: "Giảng đường",
      dataIndex: "meetingAddress",
      key: "meetingAddress",
    },
    {
      title: "Ca học",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",

      render: (text, record) => {
        return <span>{text + 1}</span>;
      },
      width: "7%",
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
      width: "20%",
    },

    {
      title: "Điểm danh",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <>
          <div className="box_icon">
            <Link
              to={`/teacher/schedule-today/attendance/${record.idMeeting}/${record.idClass}`}
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
      width: "105px",
    },
  ];
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="title-teacher-my-class">
        <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span style={{ marginLeft: "10px", fontWeight: "500" }}>
            Bảng điều khiển
          </span>
        </span>
      </div>
      <div className="filter-teacher-my-class">
        <div className="button-menu-teacher">
          <Link
            to="/teacher/schedule-today"
            style={{ fontSize: "17px" }}
            id="menu-checked"
          >
            &nbsp; Lịch dạy hôm nay &nbsp;
          </Link>
          <Link
            to="/teacher/my-class"
            className="custom-link"
            style={{ fontSize: "17px", paddingLeft: "10px" }}
          >
            Lớp của tôi &nbsp;
          </Link>
          <hr />
        </div>
        <div
          className="menu-teacher-search"
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
                Danh sách lịch dạy hôm nay
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
    </>
  );
};
export default TeacherScheduleToday;
