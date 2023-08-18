import { useParams } from "react-router-dom";
import "./styleMeetingInMyClass.css";
import { Row, Col, Table, Tooltip } from "antd";
import { Link } from "react-router-dom";
import { ControlOutlined, UnorderedListOutlined } from "@ant-design/icons";
import { TeacherMeetingAPI } from "../../../../api/teacher/meeting/TeacherMeeting.api";
import {
  SetMeeting,
  GetMeeting,
} from "../../../../app/teacher/meeting/teacherMeetingSlice.reduce";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import { faEye } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const MeetingInMyClass = () => {
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);
  const { idClass } = useParams();
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - buổi họp";
    featchMeeting(idClass);
  }, []);

  const featchMeeting = async (idClass) => {
    setLoading(false);
    try {
      await TeacherMeetingAPI.getAllMeetingByClass(idClass).then(
        (responese) => {
          dispatch(SetMeeting(responese.data.data));
          setLoading(true);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  const data = useAppSelector(GetMeeting);
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
      width: "12px",
    },
    {
      title: "Tên buổi",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <>
          <div className="box_icon">
            <Link
              to={`/teacher/meeting/detail/${record.id}`}
              className="btn btn-success ml-4"
            >
              <Tooltip title="Xem chi tiết buổi học">
                <FontAwesomeIcon icon={faEye} className="icon" />
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
          <div>
            <Link
              to={`/teacher/my-class/students/${idClass}`}
              className="custom-link"
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
              id="menu-checked"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              BUỔI HỌP &nbsp;
            </Link>
            <hr />
          </div>
        </div>

        <div className="content-class">
          <div
            className="box-center"
            style={{
              height: "28px",
              width: "100px",
              backgroundColor: "#007bff",
              color: "white",
              margin: "0px 0px 0px 92%",
            }}
          >
            {" "}
            <span style={{ fontSize: "14px" }}>{data.length} buổi học </span>
          </div>
          <Row gutter={16} style={{ margin: "3px 10px 0px 0px" }}>
            <Col span={24}>
              <div style={{ marginLeft: "0px" }}>
                {" "}
                <span style={{ fontSize: "17px", fontWeight: "500" }}>
                  {" "}
                  <UnorderedListOutlined
                    style={{ marginRight: "10px", fontSize: "20px" }}
                  />
                  Danh sách buổi họp
                </span>
              </div>
            </Col>
          </Row>
          <br />
        </div>
        <div>
          {data.length > 0 ? (
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
                Chưa có buổi họp
              </p>
            </>
          )}
        </div>
      </div>
    </>
  );
};

export default MeetingInMyClass;
