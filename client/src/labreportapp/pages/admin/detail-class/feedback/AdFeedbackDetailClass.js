import { ControlOutlined } from "@ant-design/icons";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import { AdminFeedBackAPI } from "../../../../api/admin/AdFeedBackAPI";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { Select, Table } from "antd";
const AdFeedbackDetailClass = () => {
  const { id } = useParams();
  const [classDetail, setClassDetail] = useState(null);
  const dispatch = useAppDispatch();
  const [feedback, setFeedBack] = useState({});

  const featchClass = async () => {
    try {
      await ClassAPI.getAdClassDetailById(id).then((responese) => {
        setClassDetail(responese.data.data);
        document.title = "Danh sách feedback - " + responese.data.data.code;
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  useEffect(() => {
    featchClass();
    // featchMeeting(id);
  }, []);
  const featchMeeting = async (idClass) => {
    // setLoading(false);
    // try {
      await AdminFeedBackAPI.getAllFeedBackByIdClass(idClass).then(
        (responese) => {
          dispatch(setFeedBack(responese.data.data));
          // setLoading(true);
        }
      );
    // } catch (error) {
    //   alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    // }
  };
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt - b.stt,
    },
    {
      title: "Feedback",
      dataIndex: "descriptions",
      key: "descriptions",
      sorter: (a, b) => a.descriptions.localeCompare(b.descriptions),
      width: "30%",
    },
    {
      title: "Sinh Viên",
      dataIndex: "student_id",
      key: "student_id",
      sorter: (a, b) => a.student_id.localeCompare(b.student_id),
      width: "30%",
    }
  ];


  return (
    <div style={{ paddingTop: "35px" }}>
      <div className="title-meeting-managemnt-my-class">
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{
              fontSize: "18px",
              marginLeft: "10px",
              fontWeight: "500",
            }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - Quản lý lịch học</span>
        </span>
      </div>
      <div className="box-filter-meeting-management">
        <div
          className="box-filter-meeting-management-son"
          style={{ minHeight: "600px" }}
        >
          <div className="button-menu-teacher">
            <div>
              <Link
                to={`/admin/class-management/information-class/${id}`}
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
                to={`/admin/class-management/meeting-management/${id}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                QUẢN LÝ LỊCH HỌC &nbsp;
              </Link>
              <Link
                id="menu-checked"
                to={`/admin/class-management/feedback/${id}`}
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                FEEDBACK CỦA SINH VIÊN &nbsp;
              </Link>
              <div
                className="box-center"
                style={{
                  height: "28.5px",
                  width: "auto",
                  backgroundColor: "rgb(38, 144, 214)",
                  color: "white",
                  borderRadius: "5px",
                  float: "right",
                }}
              >
                <span style={{ fontSize: "14px", padding: "10px" }}>
                  {classDetail != null ? classDetail.code : ""}
                </span>


              </div>
              <hr />
            </div>
          </div>
        </div>
        <Table
                columns={columns}
                // dataSource={feedback}
                key="stt"
                pagination={false}
              />

      </div>
    </div>
  );
};

export default AdFeedbackDetailClass;
