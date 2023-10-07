import { useEffect, useState } from "react";
import { MyProjectAPI } from "../../../../portalprojects/api/my-project/myProject.api";
import {
  GetMyProject,
  SetMyProject,
} from "../../../../portalprojects/app/reducer/my-project/myProjectSlice.reducer";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import { Button, Input, Pagination, Select, Table, Tooltip } from "antd";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faFilter } from "@fortawesome/free-solid-svg-icons";
import LoadingIndicator from "../../../helper/loading";
import { ProjectOutlined } from "@ant-design/icons";
import "./style-teacher-my-project.css";

const { Option } = Select;

const TeacherMyProject = () => {
  const dispatch = useAppDispatch();
  const [name, setName] = useState("");
  const [status, setStatus] = useState("");
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    document.title = "Dự án của tôi | Portal-Projects";
    fetchData();

    return () => {
      dispatch(SetMyProject([]));
    };
  }, [current]);

  const fetchData = async () => {
    let filter = {
      nameProject: name,
      status: status === "" ? null : parseInt(status),
      page: current - 1,
    };
    setIsLoading(true);
    try {
      const response = await MyProjectAPI.fetchAll(filter);
      let listProject = response.data.data.data;

      dispatch(SetMyProject(listProject));
      setTotal(response.data.data.totalPages);
      setIsLoading(false);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng ấn F5 để tải lại trang");
    }
  };

  const search = () => {
    fetchData();
  };

  const clear = () => {
    setName("");
    setStatus("");
  };

  const data = useAppSelector(GetMyProject);

  const columns = [
    {
      title: "Tên dự án",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      render: (text, record) => {
        if (record.backgroundImage) {
          return (
            <div style={{ display: "flex", alignItems: "center" }}>
              <img
                src={record.backgroundImage}
                alt="Background"
                className="backgroundImageTable"
              />
              <span>{text}</span>
            </div>
          );
        } else {
          return (
            <div style={{ display: "flex", alignItems: "center" }}>
              <div
                style={{ backgroundColor: record.backgroundColor }}
                className="box_backgroundColor"
              ></div>
              <span>{text}</span>
            </div>
          );
        }
      },
    },
    {
      title: "Tiến độ",
      dataIndex: "progress",
      key: "progress",
      sorter: (a, b) => a.progress - b.progress,
      render: (text, record) => {
        return <span>{record.progress}%</span>;
      },
      width: "10%",
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
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      sorter: (a, b) => a.status - b.status,
      render: (text) => {
        let statusText = "";
        if (text === 0) {
          statusText = "Đã diễn ra";
          return (
            <span
              className="box_span_status"
              style={{ backgroundColor: "rgb(45, 211, 86)", fontSize: "13px" }}
            >
              {statusText}
            </span>
          );
        } else if (text === 1) {
          statusText = "Đang diễn ra";
          return (
            <span
              className="box_span_status"
              style={{ backgroundColor: "rgb(41, 157, 224)", fontSize: "13px" }}
            >
              {statusText}
            </span>
          );
        } else if (text === 2) {
          statusText = "Chưa diễn ra";
          return (
            <span
              className="box_span_status"
              style={{ backgroundColor: "rgb(238, 162, 48)", fontSize: "13px" }}
            >
              {statusText}
            </span>
          );
        }
      },
      width: "15%",
    },

    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div>
          <Link to={`/detail-project/${record.id}`}>
            <Tooltip title="Xem chi tiết dự án">
              <FontAwesomeIcon icon={faEye} size="1x" />
            </Tooltip>
          </Link>
        </div>
      ),
    },
  ];

  return (
    <div className="my_project_teacher">
      {isLoading && <LoadingIndicator />}

      <div className="title_my_project">
        {" "}
        <ProjectOutlined style={{ fontSize: "26px" }} />
        <span style={{ marginLeft: "10px" }}>Dự án của tôi</span>
      </div>
      <div className="filter-my-project-teacher">
        <FontAwesomeIcon icon={faFilter} style={{ fontSize: 18 }} />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <div className="content">
          <div className="content-wrapper">
            <div className="content-left">
              Tên dự án:{" "}
              <Input
                type="text"
                value={name}
                onChange={(e) => {
                  setName(e.target.value);
                }}
                style={{ width: "50%", marginLeft: "10px" }}
              />
            </div>
            <div className="content-right">
              Trạng thái:{" "}
              <Select
                value={status}
                onChange={(value) => {
                  setStatus(value);
                }}
                style={{ width: "50%", marginLeft: "10px" }}
              >
                <Option value="">Tất cả</Option>
                <Option value="0">Đã diễn ra</Option>
                <Option value="1">Đang diễn ra</Option>
                <Option value="2">Chưa diễn ra</Option>
              </Select>
            </div>
          </div>
        </div>
        <div className="box_btn_filter">
          <Button className="btn_filter" onClick={search}>
            Tìm kiếm
          </Button>
          <Button
            className="btn_clear"
            onClick={clear}
            style={{ backgroundColor: "rgb(38, 144, 214)" }}
          >
            Làm mới bộ lọc
          </Button>
        </div>
      </div>
      <div className="table_project_teacher_my_project">
        <div className="title_my_project">
          {" "}
          <ProjectOutlined style={{ fontSize: "26px" }} />
          <span style={{ fontSize: "18px", fontWeight: "500" }}>
            {" "}
            Danh sách dự án
          </span>
        </div>
        <div style={{ marginTop: "25px" }}>
          <Table
            dataSource={data}
            rowKey="id"
            columns={columns}
            pagination={false}
            className="table_my_project"
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

export default TeacherMyProject;
