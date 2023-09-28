import { useParams } from "react-router-dom";
import { Button, Empty, Input, Modal, Pagination, Spin, Table } from "antd";
import { useEffect, useState } from "react";
import { convertMeetingPeriodToTime } from "../../../../../helper/util.helper";
import { TeacherAttendanceAPI } from "../../../../../api/teacher/attendance/TeacherAttendance.api";
import { SearchOutlined } from "@ant-design/icons";

const TeModalDetailOneStudent = ({ onCancel, visible, objStudent }) => {
  const { idClass } = useParams();
  const [data, setData] = useState([]);
  const [currentDetail, setCurrentDetail] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [loadingNo, setLoadingNo] = useState(true);
  useEffect(() => {
    if (visible && objStudent != {}) {
      featchDataAttenStudent(objStudent.idStudent);
    } else {
      setData([]);
    }
    if (visible === false) {
      setCurrentDetail(1);
      setTotalPages(0);
      setData([]);
    }
  }, [visible, currentDetail]);
  const featchDataAttenStudent = async (idStudent) => {
    setLoadingNo(true);
    try {
      let dataFind = {
        idStudent: idStudent,
        idClass: idClass,
        page: currentDetail,
        size: 8,
      };
      await TeacherAttendanceAPI.getAllAttendanceStudentByIdStuIdClass(
        dataFind
      ).then((response) => {
        setData(response.data.data.data);
        setTotalPages(response.data.data.totalPages);
        setTimeout(() => {
          setLoadingNo(false);
        }, 120);
      });
    } catch {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
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
    },
    {
      title: "Buổi học",
      dataIndex: "nameMeeting",
      key: "nameMeeting",
      sorter: (a, b) => a.nameMeeting.localeCompare(b.nameMeeting),
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
        if (record.nameMeeting === null) {
          return false;
        }
        return record.nameMeeting.toLowerCase().includes(value.toLowerCase());
      },
    },
    {
      title: "Ngày học",
      dataIndex: "meetingDate",
      key: "meetingDate",
      render: (meetingDate) => <span>{convertLongToDate(meetingDate)}</span>,
    },
    {
      title: "Ca",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",
      render: (meetingPeriod) => <span>{meetingPeriod + 1}</span>,
      sorter: (a, b) => a.meetingPeriod - b.meetingPeriod,
    },
    {
      title: "Thời gian",
      dataIndex: "timePeriod",
      key: "timePeriod",
      render: (text, record) => {
        return <span>{convertMeetingPeriodToTime(record.meetingPeriod)}</span>;
      },
    },
    {
      title: "Giảng viên",
      dataIndex: "usernameTeacher",
      key: "usernameTeacher",
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
        if (record.usernameTeacher === null) {
          return record.usernameTeacher;
        }
        return record.usernameTeacher
          .toLowerCase()
          .includes(value.toLowerCase());
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
    <>
      <Modal
        onCancel={onCancel}
        open={visible}
        width={850}
        footer={null}
        style={{ top: "53px" }}
        bodyStyle={{ overflow: "hidden" }}
      >
        <div>
          <div style={{ paddingTop: "0" }}>
            <span style={{ fontSize: "18px" }}>
              Chi tiết điểm danh : <span>{objStudent.name}</span>
            </span>
          </div>
          <Spin spinning={loadingNo}>
            {data.length > 0 ? (
              <div
                style={{
                  height: data.length > 0 && "540px",
                }}
              >
                <Table
                  rowKey="id"
                  dataSource={data}
                  columns={columns}
                  style={{ paddingTop: "10px" }}
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
                    style={{
                      position: "absolute",
                      bottom: "3px",
                      width: "95%",
                    }}
                  />
                </div>
              </div>
            ) : (
              <div
                className="pagination-box"
                style={{ alignContent: "center" }}
              >
                <Empty
                  imageStyle={{ height: 60 }}
                  description={<span>Không có dữ liệu</span>}
                />
              </div>
            )}
          </Spin>
        </div>
      </Modal>
    </>
  );
};

export default TeModalDetailOneStudent;
