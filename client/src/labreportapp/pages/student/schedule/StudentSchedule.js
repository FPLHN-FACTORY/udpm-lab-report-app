import "./style-student-schedule.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFilter,
  faCalendarAlt,
  faChainSlash,
  faFilterCircleDollar,
} from "@fortawesome/free-solid-svg-icons";
import { Button, Input, Pagination, Table, Select } from "antd";
import { Option } from "antd/es/mentions";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useAppSelector, useAppDispatch } from "../../../app/hook";
import {
  SetSchedule,
  GetSchedule,
} from "../../../app/student/StSchedule.reduce";
import { StScheduleAPI } from "../../../api/student/StScheduleAPI";
import React, { useCallback } from "react";
import LoadingIndicator from "../../../helper/loading";
import { convertMeetingPeriodToTime } from "../../../helper/util.helper";
import { SearchOutlined } from "@ant-design/icons";

const StudentSchedule = () => {
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);
  const [searchTime, setSearchTime] = useState("7");

  useEffect(() => {
    fetchData();
    document.title = "Lịch học | Lab-Report-App";
  }, [current]);

  const fetchData = () => {
    const searchTimeAsNumber = parseInt(searchTime);
    console.log(searchTimeAsNumber);
    setLoading(true);
    let filter = {
      searchTime: searchTimeAsNumber,
      page: current,
      size: 10,
    };

    StScheduleAPI.fetchAllSchedule(filter).then((response) => {
      dispatch(SetSchedule(response.data.data.data));
      setTotal(response.data.data.totalPages);
      setLoading(false);
      console.log(response.data.data.data);
    });
  };

  const data = useAppSelector(GetSchedule);

  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => (current - 1) * 10 + index + 1,
    },
    {
      title: "Lớp",
      dataIndex: "classCode",
      key: "classCode",
      sorter: (a, b) => a.classCode.localeCompare(b.classCode),
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
      onFilter: (value, record) =>
        record.classCode.toLowerCase().includes(value.toLowerCase()),
    },
    {
      title: "Buổi học",
      dataIndex: "meetingName",
      key: "meetingName",
      sorter: (a, b) => a.meetingName.localeCompare(b.meetingName),
    },
    {
      title: "Ngày",
      dataIndex: "meetingDate",
      key: "meetingDate",
      sorter: (a, b) => a.meetingDate - b.meetingDate,
      render: (text, record) => {
        const time = new Date(record.meetingDate);
        const formattedTime = `${time.getDate()}/${
          time.getMonth() + 1
        }/${time.getFullYear()}`;

        return <span>{formattedTime}</span>;
      },
    },
    {
      title: "Ca học",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",
      sorter: (a, b) => a.meetingPeriod.localeCompare(b.meetingPeriod),
      render: (text, record) => {
        return <span>{parseInt(record.meetingPeriod) + 1}</span>;
      },
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
      title: "Địa điểm học",
      dataIndex: "address",
      key: "address",
      sorter: (a, b) => a.address.localeCompare(b.address),
    },
    {
      title: "Hình thức học",
      dataIndex: "typeMeeting",
      key: "typeMeeting",
      sorter: (a, b) => a.typeMeeting.localeCompare(b.typeMeeting),
      render: (text, record) => (
        <span>{record.typeMeeting === 0 ? "Online" : "Offline"}</span>
      ),
    },
  ];
  const buttonSearch = () => {
    fetchData();
    setCurrent(1);
  };

  const clearData = () => {
    setSearchTime("7");
  };

  const handleOptionChange = (value) => {
    setSearchTime(value);
  };

  return (
    <div className="shedule" style={{ paddingTop: "50px" }}>
      {loading && <LoadingIndicator />}
      <div className="title_activity_management">
        {" "}
        <FontAwesomeIcon icon={faCalendarAlt} size="1x" />
        <span style={{ marginLeft: "10px" }}>Lịch học</span>
      </div>
      <div className="filter-semester">
        <FontAwesomeIcon icon={faFilter} style={{ fontSize: "20px" }} />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <div className="title__search">
          Thời gian:{" "}
          <Select
            style={{ width: "300px", marginLeft: "5px", textAlign: "left" }}
            value={searchTime}
            onChange={handleOptionChange}
          >
            <Option value="7">7 ngày tới</Option>
            <Option value="14">14 ngày tới</Option>
            <Option value="30">30 ngày tới</Option>
            <Option value="60">60 ngày tới</Option>
            <Option value="90">90 ngày tới</Option>
            <Option value="-7">7 ngày trước</Option>
            <Option value="-14">14 ngày trước</Option>
            <Option value="-30">30 ngày trước</Option>
            <Option value="-60">60 ngày trước</Option>
            <Option value="-90">90 ngày trước</Option>
          </Select>
        </div>
        <div className="box_btn_filter_st">
          <Button
            className="btn_filter"
            onClick={buttonSearch}
            style={{ marginRight: "15px" }}
          >
            <FontAwesomeIcon
              icon={faFilterCircleDollar}
              style={{ marginRight: "5px" }}
            />{" "}
            Tìm kiếm
          </Button>
          <Button
            className="btn_clear"
            style={{ backgroundColor: "rgb(38, 144, 214)" }}
            onClick={clearData}
          >
            <FontAwesomeIcon
              icon={faChainSlash}
              style={{ marginRight: "5px" }}
            />{" "}
            Làm mới bộ lọc
          </Button>
        </div>
      </div>
      <div
        className="table__category_custom"
        style={{ marginTop: "30px", padding: "20px" }}
      >
        <div className="tittle__category">
          <div>
            {" "}
            {<FontAwesomeIcon icon={faCalendarAlt} style={{ fontSize: 20 }} />}
            <span style={{ fontSize: "18px", fontWeight: "500" }}>
              {" "}
              Lịch học
            </span>
          </div>
        </div>
        <br />
        <div>
          <Table
            dataSource={data}
            rowKey="id"
            columns={columns}
            pagination={false}
          />
          <div className="pagination_box">
            <Pagination
              simple
              current={current}
              onChange={(page) => {
                setCurrent(page);
              }}
              total={total * 10}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default StudentSchedule;
