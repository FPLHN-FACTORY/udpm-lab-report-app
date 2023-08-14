import "./style-semester-management.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCogs,
  faEye,
  faFilter,
  faHome,
  faRepeat,
  faPencil,
  faTrash,
  faPlus,
} from "@fortawesome/free-solid-svg-icons";
import { Button, Input, Pagination, Table, Tooltip } from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useAppSelector, useAppDispatch } from "../../../app/hook";
import {
  SetSemester,
  GetSemester,
} from "../../../app/admin/AdSemester.reducer";
import { AdSemesterAPI } from "../../../api/admin/AdSemesterAPI";
import React, { useCallback } from 'react';
import LoadingIndicator from "../../../helper/loading";
import { toast } from "react-toastify";

const SemesterManagement = () => {
  const [semester, setSemester] = useState(null);
  const [name, setName] = useState("");
  const { id } = useParams();
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const dispatch = useAppDispatch();
  const [start, setStart] = useState(0);
  const startIndex = (current - 1) * 10;
  const [loading, setLoading] = useState(false);

  const fetchData = useCallback(() => {
    setLoading(true);
    AdSemesterAPI.fetchAllSemester(name, current).then((response) => {
      dispatch(SetSemester(response.data.data.data));
      setTotal(response.data.data.totalPages);
      setLoading(false);
      console.log(response.data.data.data)
    });
  }, [name, current, dispatch]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const data = useAppSelector(GetSemester);

  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      //sorter: (a, b) => a.stt.localeCompare(b.stt),
      render: (text, record, index) => startIndex + index + 1,
    },
    {
      title: "Tên học kỳ",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Thời gian",
      dataIndex: "startTimeAndEndTime",
      key: "startTimeAndEndTime",
      render: (text, record) => {
        const startTime = new Date(record.startTime);
        const endTime = new Date(record.endTime);

        const formattedStartTime = `${startTime.getDate()}/${
          startTime.getMonth() + 1
        }/${startTime.getFullYear()}`;
        const formattedEndTime = `${endTime.getDate()}/${
          endTime.getMonth() + 1
        }/${endTime.getFullYear()}`;

        return (
          <span>
            {formattedStartTime} - {formattedEndTime}
          </span>
        );
      },
      width: "15%",
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div>
          <Tooltip title="Cập nhật">
            <FontAwesomeIcon
              onClick={() => {
                // buttonUpdate(record);
              }}
              style={{ marginRight: "15px", cursor: "pointer" }}
              icon={faPencil}
              size="1x"
            />
          </Tooltip>
        </div>
      ),
    },
  ];

  return (
    <div className="semester">
      {loading && <LoadingIndicator />}
      <div className="title_my_project">
        {" "}
        <FontAwesomeIcon icon={faHome} size="1x" />
        <span style={{ marginLeft: "10px" }}>Danh sách học kỳ</span>
      </div>
      <div className="filter">
        <FontAwesomeIcon icon={faFilter} size="2x" />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <div className="title__search">
          Tên kỳ học:{" "}
          <Input
            type="text"
            value={name}
            onChange={(e) => {
              setName(e.target.value);
            }}
            style={{ width: "300px", marginLeft: "5px" }}
          />
        </div>
        <div className="box_btn_filter">
          <Button
            className="btn_filter"
            // onClick={buttonSearch}
          >
            Tìm kiếm
          </Button>
          <Button
            className="btn__clear"
            // onClick={clearData}
          >
            Làm mới bộ lọc
          </Button>
        </div>
      </div>

      <div className="table__category">
        <div className="tittle__category">
          <div>
            {" "}
            {<FontAwesomeIcon icon={faHome} size="2x" />}
            <span style={{ fontSize: "18px", fontWeight: "500" }}>
              {" "}
              Danh sách học kỳ
            </span>
          </div>
          <div>
            <Button
              style={{
                color: "white",
                backgroundColor: "rgb(55, 137, 220)",
              }}
              // onClick={buttonCreate}
            >
              <FontAwesomeIcon
                icon={faPlus}
                size="1x"
                style={{
                  backgroundColor: "rgb(55, 137, 220)",
                }}
              />{" "}
              Thêm học kỳ
            </Button>
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
              onChange={(value) => {
                setCurrent(value);
              }}
              total={total * 10}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default SemesterManagement;
