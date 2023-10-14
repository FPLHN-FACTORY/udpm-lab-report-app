import { ControlOutlined, SearchOutlined } from "@ant-design/icons";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import { AdminFeedBackAPI } from "../../../../api/admin/AdFeedBackAPI";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { Button, Empty, Input, Select, Table } from "antd";
import LoadingIndicator from "../../../../helper/loading";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck } from "@fortawesome/free-solid-svg-icons";

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
    } catch (error) {}
  };

  useEffect(() => {
    setLoading(false);
    featchClass();
    featchFeedBack(id);
  }, []);

  const featchFeedBack = async (idClass) => {
    try {
      await AdminFeedBackAPI.getAllFeedBackByIdClass(idClass).then(
        (response) => {
          setFeedBack(response.data.data);
          setLoading(true);
        }
      );
    } catch (error) {
      console.log(error);
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
      title: "Họ và tên",
      dataIndex: "nameStudent",
      key: "nameStudent",
      sorter: (a, b) => a.nameStudent.localeCompare(b.nameStudent),
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            autoFocus={true}
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) => {
        if (record.nameStudent === null) {
          return false;
        }
        return record.nameStudent.toLowerCase().includes(value.toLowerCase());
      },
    },
    {
      title: "Email",
      dataIndex: "emailStudent",
      key: "emailStudent",
      sorter: (a, b) => a.nameStudent.localeCompare(b.nameStudent),
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            autoFocus={true}
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) => {
        if (record.emailStudent === null) {
          return false;
        }
        return record.emailStudent.toLowerCase().includes(value.toLowerCase());
      },
    },
    {
      title: "Nội dung",
      dataIndex: "description",
      key: "description",
      sorter: (a, b) => a.descriptions.localeCompare(b.descriptions),
    },
    {
      title: "Ngày làm feedback",
      dataIndex: "createdDate",
      key: "createdDate",
      sorter: (a, b) => a.createdDate - b.createdDate,
      render: (createdDate) => {
        const date = new Date(createdDate);
        const day = date.getDate();
        const month = date.getMonth() + 1;
        const year = date.getFullYear();
        const hours = date.getHours();
        const minutes = date.getMinutes();
        const formattedDate = `${day}/${month}/${year + "  -"}`;
        const formattedTime = `${hours + "h"}:${"" + minutes + "p"}`;
        return (
          <span>
            {formattedDate}{" "}
            <span style={{ color: "brown" }}>{formattedTime}</span>
          </span>
        );
      },
    },
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
          <span style={{ color: "gray" }}> - Feedback của sinh viên</span>
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
                <span
                  style={{
                    fontSize: "14px",
                    padding: "15px",
                    fontWeight: 500,
                  }}
                >
                  {classDetail != null ? classDetail.code : ""}
                </span>
              </div>
              <hr />
            </div>
          </div>
          <div style={{ marginTop: 15, marginBottom: 12 }}>
            <span style={{ fontSize: 16 }}>
              <FontAwesomeIcon
                icon={faCheck}
                style={{ marginRight: 7, fontSize: 18 }}
              />
              Danh sách feedback:
            </span>
          </div>
          {feedback.length > 0 && (
            <Table
              columns={columns}
              dataSource={feedback}
              key="id"
              pagination={false}
            />
          )}
          {feedback.length === 0 && (
            <>
              <p
                style={{
                  textAlign: "center",
                  marginTop: "100px",
                  fontSize: "15px",
                  color: "red",
                }}
              >
                <Empty
                  imageStyle={{ height: 60 }}
                  description={<span>Không có dữ liệu</span>}
                />{" "}
              </p>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default AdFeedbackDetailClass;
