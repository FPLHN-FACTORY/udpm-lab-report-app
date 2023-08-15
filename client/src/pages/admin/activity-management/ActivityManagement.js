import "./style-activity-management.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFilter,
  faCogs,
  faList,
  faEdit,
  faEye,
  faTrashCan,
  faTags,
  faPlus,
} from "@fortawesome/free-solid-svg-icons";
import { Button, Input, Pagination, Select, Table, Tooltip } from "antd";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { ActivityManagementAPI } from "../../../api/admin/activity-management/activityManagement.api";
import { Option } from "antd/es/mentions";
import { GetActivityManagement, SetActivityManagement } from "../../../app/admin/activity-management/activityManagementSlice.reducer";
import LoadingIndicator from "../../../helper/loading";
import ModalCreateActivity from "./modal-create/ModalCreateActivity";



const ActivityManagement = () => {
  const dispatch = useAppDispatch();
  const [name, setName] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [level, setLevel] = useState("");
  const [semesterId, setSemesterId] = useState("");
  const [status, setStatus] = useState("");
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  const [searchName, setSearchName] = useState("");
  const { id } = useParams();

  useEffect(() => {
    fetchData();

    return () => {
      dispatch(SetActivityManagement([]));
    };
  }, [current]);

  const fetchData = async () => {
    let filter = {
      nameActivity: name,
      name: searchName,
      status: status === "" ? null : parseInt(status),
      page: current - 1,
    };
    setIsLoading(true);
    try {
      const response = await ActivityManagementAPI.fetchAll(filter);
      let listAcivityManagement = response.data.data.data;
      dispatch(SetActivityManagement(listAcivityManagement));
      setTotal(response.data.data.totalPages);
      setIsLoading(false);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng ấn F5 để tải lại trang");
    }
  };

  const data = useAppSelector(GetActivityManagement);

  const columns = [
    {
      title: "STT",
      dataIndex: "index",
      key: "index",
      render: (text, record, index) => index + 1,
    },
    {
      title: "Tên hoạt động",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Thời gian bắt đầu",
      dataIndex: "startTime",
      key: "startTime",
      sorter: (a, b) => new Date(a.startTime) - new Date(b.startTime),
    },
    {
      title: "Thời gian kết thúc",
      dataIndex: "endTime",
      key: "endTime",
      sorter: (a, b) => new Date(a.endTime) - new Date(b.endTime),
    },
    {
      title: "Cấp độ",
      dataIndex: "level",
      key: "level",
      sorter: (a, b) => a.level - b.level,
    },
    {
      title: "kỳ",
      dataIndex: "semesterId",
      key: "semesterId",
      sorter: (a, b) => a.semesterId.localeCompare(b.semesterId),
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div>
          <Link>
            <Tooltip title="Xem chi tiết">
              <FontAwesomeIcon
                icon={faEye}
                size="1x"
                onClick={() => {

                }}
              />
            </Tooltip>
          </Link>
          <Link>
            <Tooltip title="Chỉnh sửa chi tiết">
              <FontAwesomeIcon
                icon={faEdit}
                size="1x"
                onClick={() => {

                }}
              />
            </Tooltip>
          </Link>
          <Link>
            <Tooltip title="Xóa">
              <FontAwesomeIcon
                icon={faTrashCan}
                size="1x"
                onClick={() => {

                }}
              />
            </Tooltip>
          </Link>
        </div>
      ),
    },

  ];

  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);

  const handleChangeSearch = (e) => {
    setSearchName(e.target.value);
  };

  const handleActivityCreate = () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowCreateModal(true);

  };

  const handleModalCreateCancel = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreateModal(false);
  };

  const handleUpdateActivity = (id) => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowUpdateModal(true);

  };

  const handleModalUpdateCancel = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowUpdateModal(false);

  };
  const handleSearch = () => {
    setCurrent(1);
    fetchData();
  };

  const handleClear = () => {
    setName("");
    setStatus("");
    setSearchName("");
  };
  
  return (
    <div className="activity_management">


      <div className="title_activity_management">
        {" "}
        <FontAwesomeIcon icon={faTags} size="1x" />
        <span style={{ marginLeft: "10px" }}>Quản lý hoạt động</span>
      </div>
      <div className="filter">
        <FontAwesomeIcon icon={faFilter} size="2x" />{" "}
        <span style={{ fontSize: "25px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <div className="content">
          <div className="content-wrapper">
            <div className="content-center">
              Tên hoạt động:{" "}
              <Input
                type="text"
                value={searchName}

                style={{ width: "50%", marginLeft: "10px" }}
              />
            </div>
          </div>
          <div className="content-wrapper">
            <div className="content-center">
              Cấp độ:{" "}
              <Select
                style={{ width: "50%", marginLeft: "10px" }}
              // value={searchSemester}
              // onChange={value => setSearchSemester(value)}
              >
                <Option value="level1">1</Option>
                <Option value="level2">2</Option>
                <Option value="level3">3</Option>
              </Select>
            </div>
          </div>
          <div className="content-wrapper">
            <div className="content-center">
              Kỳ:{" "}
              <Select
                style={{ width: "50%", marginLeft: "10px" }}
              // value={searchSemester}
              // onChange={value => setSearchSemester(value)}
              >
                <Option value="semester1">Kỳ 1</Option>
                <Option value="semester2">Kỳ 2</Option>
                <Option value="semester3">Kỳ 3</Option>
              </Select>
            </div>
          </div>
        </div>
        <div className="box_btn_filter">
          <Button className="btn_filter">
            Tìm kiếm
          </Button>
          <Button className="btn_clear">
            Làm mới bộ lọc
          </Button>
        </div>
      </div>
      <div className="table_activity_management">
        <div className="title_activity_management_table">
          <div>
            {" "}
            {<FontAwesomeIcon icon={faList} size="2x" />}
            <span style={{ fontSize: "18px", fontWeight: "500" }}>
              {" "}
              Danh sách hoạt động
            </span>
          </div>
          <div>
            <Button
              style={{
                color: "white",
                backgroundColor: "rgb(55, 137, 220)",
              }}
              onClick={handleActivityCreate}
            >
              <FontAwesomeIcon
                icon={faPlus}
                size="1x"
                style={{
                  backgroundColor: "rgb(55, 137, 220)",
                }}
              />{" "}
              Thêm hoạt động
            </Button>
          </div>
        </div>
        <div style={{ marginTop: "10px" }}>
          <Table
            className="table_content"
            pagination={false}
            columns={columns}
            rowKey="id"
            dataSource={data}
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
      <ModalCreateActivity
        visible={showCreateModal}
        onCancel={handleModalCreateCancel}
      />
    </div>
  );
};

export default ActivityManagement;
