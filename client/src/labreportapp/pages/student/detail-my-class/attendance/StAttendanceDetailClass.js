import { useParams } from "react-router-dom";
import "./style-attendance-detail-class.css";
import { Link } from "react-router-dom";
import { ControlOutlined, SearchOutlined } from "@ant-design/icons";
import { useAppDispatch } from "../../../../app/hook";
import { Button, Empty, Input, Pagination, Table } from "antd";
import { useEffect, useState } from "react";
import { StAttendanceAPI } from "../../../../api/student/StAttendanceAPI";
import {
  convertHourAndMinuteToString,
  convertMeetingPeriodToTime,
} from "../../../../helper/util.helper";
import LoadingIndicator from "../../../../helper/loading";
import { SetTTrueToggle } from "../../../../app/student/StCollapsedSlice.reducer";

const StAttendanceDetailClass = () => {
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const { id } = useParams();
  const [listAttendance, setListAttendance] = useState([]);

  const [currentDetail, setCurrentDetail] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  useEffect(() => {
    document.title = "Bảng điều khiển - Điểm danh";
    setIsLoading(true);
  }, [id]);

  useEffect(() => {
    fetchData(id);
  }, [currentDetail]);

  const fetchData = async (id) => {
    setIsLoading(true);
    try {
      let dataFind = {
        idClass: id,
        page: currentDetail,
        size: 8,
      };
      await StAttendanceAPI.getAllAttendanceById(dataFind).then((respone) => {
        setListAttendance(respone.data.data.data);
        setTotalPages(respone.data.data.totalPages);
        setIsLoading(false);
      });
    } catch (error) {
      setIsLoading(false);
      console.log(error);
    }
  };
  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    const format = `${day}/${month}/${year}`;
    return format;
  };

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      align: "center",
    },
    {
      title: "Buổi học",
      dataIndex: "lesson",
      key: "lesson",
      sorter: (a, b) => a.lesson.localeCompare(b.lesson),
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
        if (record.lesson === null) {
          return false;
        }
        return record.lesson.toLowerCase().includes(value.toLowerCase());
      },
    },
    {
      title: "Ngày học",
      dataIndex: "meetingDate",
      key: "meetingDate",
      render: (meetingDate) => <span>{convertLongToDate(meetingDate)}</span>,
      align: "center",
    },
    {
      title: "Ca",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",
      sorter: (a, b) => a.meetingPeriod.localeCompare(b.meetingPeriod),
      render: (text, record) => {
        if (text == null) {
          return <span>Chưa có</span>;
        } else {
          return <span>{text}</span>;
        }
      },
      align: "center",
    },
    {
      title: "Thời gian",
      dataIndex: "timePeriod",
      key: "timePeriod",
      render: (text, record) => {
        return (
          <span>
            {convertHourAndMinuteToString(
              record.startHour,
              record.startMinute,
              record.endHour,
              record.endMinute
            )}
          </span>
        );
      },
      align: "center",
    },
    {
      title: "Giảng viên",
      dataIndex: "userName",
      key: "userName",
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
        if (record.userName === null) {
          return "";
        }
        return record.userName.toLowerCase().includes(value.toLowerCase());
      },
    },
    {
      title: "Hình thức",
      dataIndex: "typeMeeting",
      key: "typeMeeting",
      render: (typeMeeting) =>
        typeMeeting === 0 ? <span>Online</span> : <span>Offline</span>,
    },
    {
      title: "Ghi chú",
      dataIndex: "notes",
      key: "notes",
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      render: (text, record) => (
        <span>
          {record.status === 1 ? (
            <span style={{ color: "red" }}>Vắng mặt</span>
          ) : record.status === 0 ? (
            <span style={{ color: "green" }}>Có mặt</span>
          ) : (
            <span style={{ color: "orange" }}>Not yet</span>
          )}
        </span>
      ),
    },
  ];
  return (
    <div style={{ paddingTop: "35px" }}>
      {isLoading && <LoadingIndicator />}
      <div className="title-student-my-class">
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{ fontSize: "18px", marginLeft: "10px", fontWeight: "500" }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - Điểm danh</span>
        </span>
      </div>
      <div className="box-students-detail-my-class" style={{ padding: "20px" }}>
        <div
          className="button-menu-student-detail-my-class"
          style={{ minHeight: "600px" }}
        >
          <div>
            <Link
              className="custom-link"
              to={`/student/my-class/post/${id}`}
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              BÀI ĐĂNG
            </Link>
            <Link
              to={`/student/my-class/team/${id}`}
              className="custom-link"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              THÔNG TIN LỚP HỌC
            </Link>
            <Link
              className="custom-link"
              to={`/student/my-class/meeting/${id}`}
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              DANH SÁCH BUỔI HỌC
            </Link>
            <Link
              id="menu-checked"
              to={`/student/my-class/attendance/${id}`}
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              ĐIỂM DANH
            </Link>
            <Link
              className="custom-link"
              to={`/student/my-class/point/${id}`}
              style={{
                fontSize: "16px",
                fontWeight: "bold",
                paddingLeft: "10px",
                paddingRight: "10px",
              }}
            >
              ĐIỂM
            </Link>
            <hr />
          </div>
          <div
            style={{ fontSize: "16px", marginTop: "15px", marginBottom: "8px" }}
          >
            Danh sách điểm danh:
          </div>
          {listAttendance.length > 0 ? (
            <div>
              <Table
                dataSource={listAttendance}
                columns={columns}
                key={"key"}
                pagination={false}
              />

              <div
                className="pagination-box"
                style={{ alignContent: "center" }}
              >
                <Pagination
                  simple
                  current={currentDetail}
                  onChange={(value) => {
                    setCurrentDetail(value);
                  }}
                  total={totalPages * 10}
                />
              </div>
            </div>
          ) : (
            <Empty
              imageStyle={{ height: 60 }}
              description={<span>Không có dữ liệu</span>}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default StAttendanceDetailClass;
