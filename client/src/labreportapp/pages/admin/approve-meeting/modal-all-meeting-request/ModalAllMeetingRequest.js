import React, { useEffect, useState } from "react";
import {
  Button,
  Checkbox,
  Empty,
  Modal,
  Select,
  Table,
  Tabs,
  message,
} from "antd";
import "react-toastify/dist/ReactToastify.css";
import TabPane from "antd/es/tabs/TabPane";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck, faClose } from "@fortawesome/free-solid-svg-icons";
import { AdMeetingRequestAPI } from "../../../../api/admin/AdMeetingRequestAPI";
import LoadingIndicator from "../../../../helper/loading";
import { convertLongToDate } from "../../../../helper/convertDate";
import { convertHourAndMinuteToString } from "../../../../helper/util.helper";
import { MeetingManagementAPI } from "../../../../api/admin/meeting-management/MeetingManagementAPI";

const { Option } = Select;

const ModalAllMeetingRequest = ({ visible, onCancel, item, fetchData }) => {
  const handleTabChange = (key) => {
    if (key === "1") {
      setListMeeting([]);
      getAllMeetingRequest();
    }
    if (key === "2") {
      setListMeetingRequest([]);
      loadDataMeeting();
    }
  };

  const [loading, setLoading] = useState(false);

  const [listMeetingRequest, setListMeetingRequest] = useState([]);
  const [listMeeting, setListMeeting] = useState([]);

  const getAllMeetingRequest = () => {
    setLoading(true);
    AdMeetingRequestAPI.getAllMeetingRequestByIdClass(item.id).then(
      (response) => {
        setLoading(false);
        console.log(response.data.data);
        setListMeetingRequest(response.data.data);
      }
    );
  };

  const loadDataMeeting = () => {
    setLoading(true);
    MeetingManagementAPI.getAllMeetingByIdClass(item.id).then(
      (response) => {
        setListMeeting(response.data.data);
        setLoading(false);
      },
      (error) => {
        console.log(error);
      }
    );
  };

  useEffect(() => {
    if (visible) {
      getAllMeetingRequest();
    }

    setListMeetingRequest([]);
  }, [visible]);

  const [selectedRowKeys, setSelectedRowKeys] = useState([]);

  const handleCheckboxChange = (e, record) => {
    const keys = [...selectedRowKeys];
    if (e.target.checked) {
      keys.push(record.id);
    } else {
      const index = keys.indexOf(record.id);
      if (index !== -1) {
        keys.splice(index, 1);
      }
    }
    setSelectedRowKeys(keys);
  };

  const handleSelectAll = (e) => {
    const keys = e.target.checked
      ? listMeetingRequest.map((record) => record.id)
      : [];
    setSelectedRowKeys(keys);
  };

  const columnsMeetingRequest = [
    {
      title: (
        <Checkbox
          onChange={(e) => handleSelectAll(e)}
          indeterminate={
            selectedRowKeys.length > 0 &&
            selectedRowKeys.length < listMeetingRequest.length
          }
          checked={selectedRowKeys.length === listMeetingRequest.length}
        />
      ),
      dataIndex: "checkbox",
      key: "checkbox",
      render: (text, record) => (
        <Checkbox
          onChange={(e) => handleCheckboxChange(e, record)}
          checked={selectedRowKeys.includes(record.id)}
        />
      ),
    },
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt - b.stt,
      render: (text, record, index) => index + 1,
    },
    {
      title: "Tên buổi",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.code.localeCompare(b.code),
    },
    {
      title: "Ngày dạy",
      dataIndex: "meetingDate",
      key: "meetingDate",
      render: (text, record) => {
        return <span>{convertLongToDate(record.meetingDate)}</span>;
      },
    },
    {
      title: "Ca học",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",
      render: (text, record) => {
        return (
          <span>
            {" "}
            {record.nameMeetingPeriod} (
            {convertHourAndMinuteToString(
              record.startHour,
              record.startMinute,
              record.endHour,
              record.endMinute
            )}
            )
          </span>
        );
      },
    },
    {
      title: "Hình thức",
      dataIndex: "type",
      key: "type",
      sorter: (a, b) => a.typeMeeting - b.typeMeeting,
      render: (text, record) => {
        if (record.typeMeeting === 0) {
          return <span>Online</span>;
        } else {
          return <span>Offline</span>;
        }
      },
    },
    {
      title: "Giảng viên",
      dataIndex: "userNameTeacher",
      key: "userNameTeacher",
      sorter: (a, b) => a.userNameTeacher.localeCompare(b.userNameTeacher),
    },
  ];

  const columnsMeeting = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt - b.stt,
      render: (text, record, index) => index + 1,
    },
    {
      title: "Tên buổi",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.code.localeCompare(b.code),
    },
    {
      title: "Ngày dạy",
      dataIndex: "meetingDate",
      key: "meetingDate",
      render: (text, record) => {
        return <span>{convertLongToDate(record.meetingDate)}</span>;
      },
    },
    {
      title: "Ca học",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",
      render: (text, record) => {
        return (
          <span>
            {" "}
            {record.nameMeetingPeriod} (
            {convertHourAndMinuteToString(
              record.startHour,
              record.startMinute,
              record.endHour,
              record.endMinute
            )}
            )
          </span>
        );
      },
    },
    {
      title: "Hình thức",
      dataIndex: "type",
      key: "type",
      sorter: (a, b) => a.typeMeeting - b.typeMeeting,
      render: (text, record) => {
        if (record.typeMeeting === 0) {
          return <span>Online</span>;
        } else {
          return <span>Offline</span>;
        }
      },
    },
    {
      title: "Giảng viên",
      dataIndex: "userNameTeacher",
      key: "userNameTeacher",
      sorter: (a, b) => a.userNameTeacher.localeCompare(b.userNameTeacher),
    },
  ];

  const approveMeetingRequest = () => {
    setLoading(true);
    AdMeetingRequestAPI.approveMeetingRequest(selectedRowKeys).then(
      (response) => {
        message.success("Phê duyệt lịch học thành công !");
        setLoading(false);
        onCancel();
        fetchData();
      },
      (error) => {}
    );
  };

  return (
    <>
      {loading && <LoadingIndicator />}
      <Modal visible={visible} onCancel={onCancel} width={1000} footer={null}>
        <div>
          <div style={{ paddingTop: "0" }}>
            <span style={{ fontSize: "18px" }}>
              <FontAwesomeIcon icon={faCheck} />
              <span style={{ marginLeft: 5 }}>Phê duyệt lịch học</span>
            </span>
          </div>
          <div style={{ marginTop: "15px" }}>
            <span style={{ fontSize: 16, fontWeight: 500 }}>
              Thông tin lớp học:
            </span>{" "}
            <span style={{ color: "red", fontWeight: 500 }}>
              <span>{item != null && item.code + " - "}</span>
              <span> {item != null && item.userNameTeacher}</span>{" "}
            </span>
          </div>
        </div>
        <div>
          {" "}
          <Tabs defaultActiveKey="1" onChange={handleTabChange}>
            <TabPane tab="Danh sách buổi học đang chờ phê duyệt" key="1">
              <div>
                <Button
                  style={{
                    color: "white",
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: 5,
                  }}
                  onClick={approveMeetingRequest}
                >
                  <FontAwesomeIcon
                    icon={faCheck}
                    size="1x"
                    style={{
                      backgroundColor: "rgb(55, 137, 220)",
                      marginRight: "7px",
                    }}
                  />{" "}
                  Phê duyệt
                </Button>
                <Button
                  style={{
                    color: "white",
                    backgroundColor: "rgb(55, 137, 220)",
                  }}
                >
                  <FontAwesomeIcon
                    icon={faClose}
                    size="1x"
                    style={{
                      backgroundColor: "rgb(55, 137, 220)",
                      marginRight: "7px",
                    }}
                  />{" "}
                  Từ chối
                </Button>
              </div>
              <div style={{ marginTop: 15 }}>
                <div className="table_custom_class_management">
                  {listMeetingRequest.length > 0 && (
                    <Table
                      dataSource={listMeetingRequest}
                      rowKey="id"
                      columns={columnsMeetingRequest}
                      pagination={false}
                    />
                  )}
                  {listMeetingRequest.length === 0 && (
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
            </TabPane>
            <TabPane tab="Danh sách buổi học của lớp học" key="2">
              <div>
                {listMeeting.length > 0 && (
                  <Table
                    dataSource={listMeeting}
                    rowKey="id"
                    columns={columnsMeeting}
                    pagination={false}
                  />
                )}
                {listMeeting.length === 0 && (
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
            </TabPane>
          </Tabs>
        </div>
      </Modal>
    </>
  );
};
export default ModalAllMeetingRequest;
