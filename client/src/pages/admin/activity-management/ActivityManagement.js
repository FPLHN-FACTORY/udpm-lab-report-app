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
  faTrash,
} from "@fortawesome/free-solid-svg-icons";
import { Button, Input, Pagination, Popconfirm, Select, Table, Tooltip } from "antd";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { ActivityManagementAPI } from "../../../api/admin/activity-management/activityManagement.api";
import { Option } from "antd/es/mentions";
import { DeleteActivityManagement, GetActivityManagement, SetActivityManagement } from "../../../app/admin/activity-management/activityManagementSlice.reducer";
import LoadingIndicator from "../../../helper/loading";
import ModalCreateActivity from "./modal-create/ModalCreateActivity";
import ModalUpdateActivity from "./modal-update/ModalUpdateActivity";
import { toast } from "react-toastify";
import axios from "axios";
import { Center } from "@chakra-ui/react";



const ActivityManagement = () => {
  const dispatch = useAppDispatch();
  const [name, setName] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [level, setLevel] = useState("");
  const [semesterId, setSemesterId] = useState("");

  const [searchName, setSearchName] = useState("");
  const [levelSearch, setLevelSearch] = useState("");
  const [semesterSearch, setSemesterSearch] = useState("");

  const [status, setStatus] = useState("");
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(true);

  const { id } = useParams();
  const [activity, setActivity] = useState(null);
  const [idActivity, setIdActivity] = useState("");
  const [listSemester, setListSemester] = useState([]);


  useEffect(() => {
    fetchData();

    return () => {
      dispatch(SetActivityManagement([]));
    };
  }, [current]);

  useEffect(() => {
    // Gọi hàm để tải dữ liệu học kỳ từ API
    fetchSemesterData();
  }, []);

  const fetchData = async () => {
    let filter = {
      nameActivity: name,
      name: searchName,
      level: levelSearch,
      status: status === "" ? null : parseInt(status),
      page: current - 1,
    };
    setLoading(true);
    try {
      const response = await ActivityManagementAPI.fetchAll(filter);
      let listAcivityManagement = response.data.data.data;
      dispatch(SetActivityManagement(listAcivityManagement));
      setTotal(response.data.data.totalPages);
      setLoading(false);
      console.log(response.data.data.data);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng ấn F5 để tải lại trang");
    }
  };

  const fetchSemesterData = async () => {
    try {
      const response = await ActivityManagementAPI.semester();
      const semesterData = response.data.data; // Dữ liệu học kỳ từ API
      // Cập nhật state hoặc Redux store với dữ liệu học kỳ
      setListSemester(semesterData);
    } catch (error) {
      console.error('Error fetching semester data:', error);
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
      title: "Thời gian",
      dataIndex: "startTimeAndEndTime",
      key: "startTimeAndEndTime",
      render: (text, record) => {
        const startTime = new Date(record.startTime);
        const endTime = new Date(record.endTime);

        const formattedStartTime = `${startTime.getDate()}/${startTime.getMonth() + 1
          }/${startTime.getFullYear()}`;
        const formattedEndTime = `${endTime.getDate()}/${endTime.getMonth() + 1
          }/${endTime.getFullYear()}`;

        return (
          <span>
            {formattedStartTime} - {formattedEndTime}
          </span>
        );
      },
      width: "175px",
    },
    {
      title: 'Cấp độ',
      dataIndex: 'level',
      key: 'level',
      sorter: (a, b) => a.level - b.level,
      render: (text) => {
        let displayText = '';
        if (text === 0) {
          displayText = 'Level 1';
        } else if (text === 1) {
          displayText = 'Level 2';
        } else if (text === 2) {
          displayText = 'Level 3';
        } else {
          displayText = text;
        }

        return <span>{displayText}</span>;
      },
    },
    {
      title: "Học kỳ",
      dataIndex: "nameSemester",
      key: "nameSemester",
      sorter: (a, b) => a.nameSemester.localeCompare(b.nameSemester),
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div style={{ textAlign: "center" }}>
          <Link style={{ marginRight: "30px" }}>
            <Tooltip title="Chỉnh sửa chi tiết">
              <FontAwesomeIcon
                icon={faEdit}
                size="1x"
                onClick={() => {
                  handleUpdateActivity(record.id, record);
                }}
              />
            </Tooltip>
          </Link>
          <Popconfirm
            title="Xóa hoạt động"
            description="Bạn có chắc chắn muốn xóa hoạt động này không?"
            onConfirm={() => {
              handleDeleteActivity(record.id);
            }}
            okText="Có"
            cancelText="Không"
          >
            <Tooltip title="Xóa">
              <FontAwesomeIcon
                style={{ cursor: "pointer" }}
                icon={faTrash}
                size="1x"
              />
            </Tooltip>
          </Popconfirm>
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
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreateModal(true);
  };

  const handleModalCreateCancel = () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowCreateModal(false);
  };

  const handleUpdateActivity = (id, activity) => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowUpdateModal(true);
    setIdActivity(id);
    setActivity(activity);
  };

  const handleModalUpdateCancel = () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowUpdateModal(false);

  };
  const handleSearch = async () => {
    await fetchData();
    toast.success("Tìm kiếm thành công!");
  };

  const handleClear = () => {
    setName("");
    setStatus("");
    setSearchName("");
    setLevelSearch("");
    setSemesterSearch("");
  };

  const handleSemesterSearch = (value) => {
    setSemesterSearch(value);
    searchActivitiesBySemester(value);
  };

  const handleLevelSearch = (value) => {
    setLevelSearch(value);
    searchActivitiesByLevel(value);
  };

  const searchActivitiesByLevel = (level) => {
    const filteredActivities = data.filter(activity => activity.level === level);
    dispatch(SetActivityManagement(filteredActivities));
    setCurrent(1);
  };

  const handleDeleteActivity = async (id) => {
    try {
      await ActivityManagementAPI.deleteActivity(id);
      toast.success("Xóa thành công!");
      fetchData(); // Gọi lại hàm fetchData để cập nhật danh sách hoạt động sau khi xóa
    } catch (error) {
      toast.error("Xóa không thành công. Vui lòng thử lại!");
    }
  };

  const searchActivitiesBySemester = (semesterId) => {
    // Thực hiện tìm kiếm hoạt động dựa trên học kỳ ở đây
    const filteredActivities = data.filter(activity => activity.semesterId === semesterId);

    // Cập nhật dữ liệu hiển thị hoạt động theo kết quả tìm kiếm
    dispatch(SetActivityManagement(filteredActivities));

    // Cập nhật giá trị trang hiện tại nếu cần
    setCurrent(1);
  };

  return (
    <div className="activity_management">
      {/* {loading && <LoadingIndicator />} */}

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
                onChange={handleChangeSearch}
                style={{ width: "50%", marginLeft: "10px" }}
              />
            </div>
          </div>
          <div className="content-wrapper">
            <div className="content-center">
              Cấp độ:{" "}
              <Select
                style={{ width: "50%", marginLeft: "10px" }}
                value={levelSearch}
                onChange={handleLevelSearch}
              >
                <Option value=" ">Tất cả</Option>
                <Option value="1">Level 1</Option>
                <Option value="2">Level 2</Option>
                <Option value="3">Level 3</Option>
              </Select>
            </div>
          </div>
          <div className="content-wrapper">
            <div className="content-center">
              Học kỳ:{" "}
              <Select
                style={{ width: "50%", marginLeft: "10px" }}
                value={semesterSearch}
                onChange={handleSemesterSearch} // Gọi hàm khi có thay đổi học kỳ
              >
                {/* Render danh sách học kỳ */}
                {listSemester.map((semester) => (
                  <Option key={semester.id} value={semester.id}>
                    {semester.name}
                  </Option>
                ))}
              </Select>
            </div>
          </div>
        </div>
        <div className="box_btn_filter">
          <Button className="btn_filter" onClick={handleSearch}>
            Tìm kiếm
          </Button>
          <Button className="btn_clear" onClick={handleClear}>
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
        listSemester={listSemester}
      />
      <ModalUpdateActivity
        visible={showUpdateModal}
        onCancel={handleModalUpdateCancel}
        listSemester={listSemester}
      />
    </div>
  );
};

export default ActivityManagement;
