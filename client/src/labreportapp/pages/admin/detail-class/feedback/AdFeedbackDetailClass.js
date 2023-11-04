import { ControlOutlined, SearchOutlined } from "@ant-design/icons";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import { AdminFeedBackAPI } from "../../../../api/admin/AdFeedBackAPI";
import { Button, Col, Empty, Input, Row, Table } from "antd";
import LoadingIndicator from "../../../../helper/loading";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCommentDots, faTableList } from "@fortawesome/free-solid-svg-icons";
import {
  arrayQuestion,
  convertDateLongToString,
} from "../../../../helper/util.helper";

const AdFeedbackDetailClass = () => {
  const { id } = useParams();
  const [classDetail, setClassDetail] = useState(null);
  const [feedback, setFeedBack] = useState([]);
  const [loading, setLoading] = useState(false);

  const featchClass = async () => {
    try {
      await ClassAPI.getAdClassDetailById(id).then((responese) => {
        setClassDetail(responese.data.data);
        document.title = "Danh sách feedback - " + responese.data.data.code;
      });
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    window.scrollTo(0, 0);
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
      align: "center",
    },
    {
      title: "Họ và tên",
      dataIndex: "nameStudent",
      key: "nameStudent",
      align: "center",
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
      align: "center",
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
      title: "Q1",
      dataIndex: "rateQuestion1",
      key: "rateQuestion1",
      align: "center",
    },
    {
      title: "Q2",
      dataIndex: "rateQuestion2",
      key: "rateQuestion2",
      align: "center",
    },
    {
      title: "Q3",
      dataIndex: "rateQuestion3",
      key: "rateQuestion3",
      align: "center",
    },
    {
      title: "Q4",
      dataIndex: "rateQuestion4",
      key: "rateQuestion4",
      align: "center",
    },
    {
      title: "Q5",
      dataIndex: "rateQuestion5",
      key: "rateQuestion5",
      align: "center",
    },
    {
      title: "Trung bình",
      dataIndex: "averageRate",
      key: "averageRate",
      align: "center",
    },
    {
      title: "Nội dung",
      dataIndex: "description",
      key: "description",
    },
    {
      title: "Thời gian feedback",
      dataIndex: "createdDate",
      key: "createdDate",
      sorter: (a, b) => a.createdDate - b.createdDate,
      render: (createdDate) => {
        const date = new Date(createdDate);
        const hours = date.getHours();
        const minutes = date.getMinutes();
        const formattedDate = convertDateLongToString(createdDate);
        const formattedTime = `${hours + "h"}:${"" + minutes + "p"}`;
        return (
          <span>
            {formattedDate}
            {" - "}
            <span style={{ color: "red" }}>{formattedTime}</span>
          </span>
        );
      },
      align: "center",
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
          <div style={{ padding: "0 10px 10px 10px" }}>
            <Row style={{ paddingTop: "10px" }}>
              <Col span={24} style={{ fontSize: "17px" }}>
                <FontAwesomeIcon icon={faCommentDots} />{" "}
                <span>Câu hỏi feedback</span>
              </Col>
            </Row>
            <Row>
              <Col span={24}>
                <div className="group-info">
                  {arrayQuestion.length > 0 &&
                    arrayQuestion.map((i, index) => {
                      return (
                        <div style={{ padding: "15px 0 0px 15px" }} key={index}>
                          <span key={index}>
                            {i} <br />
                          </span>
                        </div>
                      );
                    })}
                  <br />
                </div>
              </Col>
            </Row>
            <div style={{ marginTop: 20, marginBottom: 12 }}>
              <span style={{ fontSize: 16 }}>
                <FontAwesomeIcon
                  icon={faTableList}
                  style={{
                    marginRight: "10px",
                    fontSize: "20px",
                  }}
                />
                Danh sách feedback
              </span>
            </div>
            {feedback.length > 0 && (
              <>
                <Table
                  columns={columns}
                  dataSource={feedback}
                  key="id"
                  pagination={false}
                />
                <div
                  style={{
                    padding: "15px 0 10px 10px",
                    fontSize: "17px",
                    fontWeight: "bold",
                  }}
                >
                  <span> Điểm trung bình: </span>
                  {(
                    feedback.reduce(
                      (total, feedback) => total + feedback.averageRate,
                      0
                    ) / feedback.length
                  ).toFixed(2)}
                </div>
              </>
            )}
            {feedback.length === 0 && (
              <>
                <p
                  style={{
                    textAlign: "center",
                    marginTop: "80px",
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
    </div>
  );
};

export default AdFeedbackDetailClass;
