import { ControlOutlined } from "@ant-design/icons";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import { AdminFeedBackAPI } from "../../../../api/admin/AdFeedBackAPI";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { Select, Table } from "antd";
import LoadingIndicator from "../../../../helper/loading";

const AdFeedbackDetailClass = () => {
  const { id } = useParams();
  const [classDetail, setClassDetail] = useState(null);
  const dispatch = useAppDispatch();
  const [feedback, setFeedBack] = useState([]);
  const [listStudent, setListStudent] = useState([]);
  const [loading, setLoading] = useState(false);


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
  const [pagination, setPagination] = useState({
    current: 1,
    pageSize: 10 
  });
  const handleTableChange = (pagination, filters, sorter) => {
    setPagination(pagination);
  };

  useEffect(() => {
    featchClass();
    featchFeedBack(id);
    featchStudentClass(id);
  }, []);
  const featchFeedBack = async (idClass) => {
    try {
      await AdminFeedBackAPI.getAllFeedBackByIdClass(idClass).then((response) => {
          setFeedBack(response.data.data);
      });
    } catch (error) {
      console.log(error);
    }
  };
  const featchStudentClass = async (id) => {
    setLoading(false);
    try {
      await AdminFeedBackAPI.getStudentInClasses(id).then(
        (responese) => {
         setListStudent(responese.data.data);
         console.log(responese.data.data);
          setLoading(true);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
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
      dataIndex: "description",
      key: "description",
      sorter: (a, b) => a.descriptions.localeCompare(b.descriptions),
      width: "50%",
    },
    {
      title: "Ngày feedback",
      dataIndex: "createdDate",
      key: "createdDate",
      sorter: (a, b) => a.createdDate.localeCompare(b.createdDate),
      width: "40%",
      render: (createdDate) => {
        const date = new Date(createdDate);
        const day = date.getDate();
        const month = date.getMonth() + 1;
        const year = date.getFullYear();
        const hours = date.getHours();
        const minutes = date.getMinutes();
        const formattedDate = `${day}/${month}/${year+"  --"}`;
        const formattedTime = `${hours+" giờ "}:${" "+minutes+" phút"}`;
        return (
          <span>
            {formattedDate} <span style={{ color: "brown" }}>{formattedTime}</span>
          </span>
        );
      },
    },
    {
      title: "Sinh Viên",
      dataIndex: "idStudent",
      key: "idStudent",
      width: "30%",
      render: (idStudent) => {
        const student = listStudent.find((student) => student.idStudent === idStudent);
        return student ? student.username : "";
      }
    }
  ];


  return (
    <div style={{ paddingTop: "35px" }}>
      {!loading && <LoadingIndicator />}
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
          <Table
                columns={columns}
                dataSource={feedback}
                key="stt"
                pagination={pagination}
                onChange={handleTableChange}
              />
        </div>
        

      </div>
    </div>
  );
};

export default AdFeedbackDetailClass;
