import "./style-student-schedule.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFilter,
  faCalendar,
  faCalendarAlt  ,
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
import { toast } from "react-toastify";
import { StScheduleAPI } from "../../../api/student/StScheduleAPI";
import { sinhVienCurrent } from "../../../helper/inForUser";
import moment from "moment";
import React, { useCallback } from "react";
import LoadingIndicator from "../../../helper/loading";

const StudentSchedule = () => {
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);
  const [searchTime, setSearchTime] = useState('7');

  useEffect(() => {
    fetchData(sinhVienCurrent);
  }, [current]);

  const fetchData = (sinhVienCurrent) => {
    const searchTimeAsNumber = parseInt(searchTime);
    console.log(searchTimeAsNumber);
    setLoading(false);
    let filter = {
      idStudent: sinhVienCurrent.id,
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
        <span>{record.typeMeeting === 0 ? "online" : "offline"}</span>
      ),
    },
    {
      title: "Lớp",
      dataIndex: "classCode",
      key: "classCode",
      sorter: (a, b) => a.classCode.localeCompare(b.classCode),
    },
    {
      title: "Thời gian",
      dataIndex: "startTime",
      key: "startTime",
      render: (text, record) => {
        const startTime = new Date(record.startTime);
        const formattedTime = `${startTime.getDate()}/${
          startTime.getMonth() + 1
        }/${startTime.getFullYear()}`;

        return <span>{formattedTime}</span>;
      },
    },
  ];
  const buttonSearch = () => {
    fetchData(sinhVienCurrent);
    setCurrent(1);
  };

  const clearData = () => {
    setSearchTime("");
  };

  const handleOptionChange = (value) => {
      setSearchTime(value);
  };

  return (
    <div className="shedule">
      {loading && <LoadingIndicator />}
      <div className="title_activity_management">
        {" "}
        <FontAwesomeIcon icon={faCalendarAlt  } size="1x" />
        <span style={{ marginLeft: "10px" }}>Lịch học</span>
      </div>
      <div className="filter-semester">
        <FontAwesomeIcon icon={faFilter} size="2x" />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <div className="title__search">
          Thời gian:{" "}
          <Select
            style={{ width: "300px", marginLeft: "5px", textAlign: "left"}}
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
        <div className="box_btn_filter">
          <Button className="btn_filter" onClick={buttonSearch}>
            Tìm kiếm
          </Button>
          <Button className="btn_clear" onClick={clearData}>
            Làm mới bộ lọc
          </Button>
        </div>
      </div>

      <div
        className="table__category"
        style={{ marginTop: "25px", padding: "20px" }}
      >
        <div className="tittle__category">
          <div>
            {" "}
            {<FontAwesomeIcon icon={faCalendarAlt} size="1x" />}
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
