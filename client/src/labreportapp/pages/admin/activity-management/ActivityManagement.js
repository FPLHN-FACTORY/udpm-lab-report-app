import "./style-activity-management.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFilter,
  faEdit,
  faTags,
  faPlus,
  faTrash,
  faAddressCard,
  faFilterCircleDollar,
  faChainSlash,
  faFileDownload,
  faHistory,
} from "@fortawesome/free-solid-svg-icons";
import {
  Button,
  Empty,
  Input,
  Pagination,
  Popconfirm,
  Select,
  Table,
  Tag,
  Tooltip,
  message,
} from "antd";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { ActivityManagementAPI } from "../../../api/admin/activity-management/activityManagement.api";
import { Option } from "antd/es/mentions";
import {
  DeleteActivityManagement,
  GetActivityManagement,
  SetActivityManagement,
} from "../../../app/admin/activity-management/activityManagementSlice.reducer";
import LoadingIndicator from "../../../helper/loading";
import ModalCreateActivity from "./modal-create/ModalCreateActivity";
import ModalUpdateActivity from "./modal-update/ModalUpdateActivity";
import { convertDateLongToString } from "../../../helper/util.helper";
import ModalShowHistoryActivity from "./modal-show-history-activity/ModalShowHistoryActivity";

const ActivityManagement = () => {
  const { id } = useParams();
  const dispatch = useAppDispatch();
  const [name, setName] = useState("");
  const [clear, setClear] = useState(false);
  const [searchName, setSearchName] = useState("");
  const [levelSearch, setLevelSearch] = useState("");
  const [semesterSearch, setSemesterSearch] = useState("");
  const [status, setStatus] = useState("");
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const [loading, setLoading] = useState(true);
  const [activity, setActivity] = useState({});
  const [listSemester, setListSemester] = useState([]);
  const [listLevel, setListLevel] = useState([]);

  useEffect(() => {
    fetchSemesterData();
    fetchLevelData();
    window.scrollTo(0, 0);
    document.title = "Quản lý hoạt động | Lab-Report-App";
  }, []);

  useEffect(() => {
    fetchData();
    return () => {
      dispatch(SetActivityManagement([]));
    };
  }, [current]);

  useEffect(() => {
    if (clear) {
      fetchData();
      return () => {
        setClear(false);
        dispatch(SetActivityManagement([]));
      };
    }
  }, [clear]);

  const fetchData = async () => {
    let filter = {
      name: searchName,
      level: levelSearch,
      semesterId: semesterSearch,
      page: current - 1,
    };
    setLoading(true);
    try {
      const response = await ActivityManagementAPI.fetchAll(filter);
      let listActivityManagement = response.data.data.data;
      dispatch(SetActivityManagement(listActivityManagement));
      setTotal(response.data.data.totalPages);
      setLoading(false);
    } catch (error) {}
  };

  const fetchSemesterData = async () => {
    try {
      const response = await ActivityManagementAPI.semester();
      const semesterData = response.data.data;
      setListSemester(semesterData);
    } catch (error) {
      console.error("Error fetching semester data:", error);
    }
  };

  const fetchLevelData = () => {
    ActivityManagementAPI.level().then((response) => {
      setListLevel(response.data.data);
    });
  };

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
    setActivity({});
  };

  const handleUpdateActivity = (item) => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowUpdateModal(true);
    setActivity(item);
  };

  const handleModalUpdateCancel = () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowUpdateModal(false);
  };
  const handleSearch = async () => {
    await fetchData();
  };
  const handleClear = () => {
    setName("");
    setStatus("");
    setSearchName("");
    setLevelSearch("");
    setSemesterSearch("");
    // setClear(true);
  };

  const handleSemesterSearch = (value) => {
    setSemesterSearch(value);
  };

  const handleLevelSearch = (value) => {
    setLevelSearch(value);
  };
  const searchActivities = async () => {
    await fetchData();
  };

  const handleDeleteActivity = async (id) => {
    try {
      await ActivityManagementAPI.delete(id).then((response) => {
        message.success("Xóa thành công !");
        dispatch(DeleteActivityManagement(id));
      });
    } catch (error) {}
  };

  const data = useAppSelector(GetActivityManagement);

  const columns = [
    {
      title: "#",
      dataIndex: "index",
      key: "index",
      render: (text, record, index) => index + 1,
    },
    {
      title: "Mã hoạt động",
      dataIndex: "code",
      key: "code",
    },
    {
      title: "Tên hoạt động",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: <div style={{ textAlign: "center" }}>Thời gian</div>,
      dataIndex: "startTimeAndEndTime",
      key: "startTimeAndEndTime",
      render: (text, record) => {
        const startTime = convertDateLongToString(record.startTime);
        const endTime = convertDateLongToString(record.endTime);
        return (
          <span>
            {startTime} - {endTime}
          </span>
        );
      },
    },
    {
      title: "Cấp độ",
      dataIndex: "level",
      key: "level",
    },
    {
      title: "Mô tả",
      dataIndex: "descriptions",
      key: "descriptions",
      render: (text) => (
        <Tooltip title={text}>
          {text.length > 50 ? `${text.substring(0, 50)}...` : text}
        </Tooltip>
      ),
    },
    {
      title: "Tạo không gian quản lý dự án",
      dataIndex: "allowUseTrello",
      key: "allowUseTrello",
      render: (text, record) => {
        if (record.allowUseTrello === 0) {
          return <Tag color="success">Cho phép</Tag>;
        } else {
          return <Tag color="error">Không cho phép</Tag>;
        }
      },
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      render: (text, record) => {
        let currentTime = new Date().getTime();
        if (record.startTime <= currentTime && currentTime <= record.endTime) {
          return <Tag color="processing">Đang diễn ra</Tag>;
        } else if (currentTime < record.startTime) {
          return <Tag color="success">Chưa diễn ra</Tag>;
        } else {
          return <Tag color="error">Đã diễn ra</Tag>;
        }
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
          <Link style={{ marginRight: "9px" }}>
            <Tooltip title="Chỉnh sửa chi tiết">
              <FontAwesomeIcon
                icon={faEdit}
                style={{ marginRight: "9px", color: "rgb(38, 144, 214)" }}
                size="1x"
                onClick={() => {
                  handleUpdateActivity(record);
                }}
              />
            </Tooltip>
          </Link>
          <Popconfirm
            placement="topRight"
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
                style={{ color: "rgb(38, 144, 214)" }}
                icon={faTrash}
                size="1x"
              />
            </Tooltip>
          </Popconfirm>
        </div>
      ),
    },
  ];

  const [visibleHistory, setVisibleHistory] = useState(false);
  const openModalShowHistory = () => {
    setVisibleHistory(true);
  };
  const cancelModalHistory = () => {
    setVisibleHistory(false);
  };

  const changeTotalsPage = (newTotalPages) => {
    setTotal(newTotalPages);
  };
  const dowloadLog = () => {
    setLoading(true);
    ActivityManagementAPI.dowloadLog().then(
      (response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const a = document.createElement("a");
        a.href = url;
        a.download = "hoat_dong.csv";
        a.click();
        window.URL.revokeObjectURL(url);
        setLoading(false);
      },
      (error) => {
        console.log(error);
      }
    );
  };
  return (
    <>
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon icon={faTags} style={{ fontSize: "20px" }} />
            <span style={{ marginLeft: "10px" }}>Quản lý hoạt động</span>
          </span>
        </div>
      </div>
      <div
        className="activity_management"
        style={{ paddingTop: 10, marginTop: 0 }}
      >
        {loading && <LoadingIndicator />}
        <div className="filter_my_class" style={{ padding: 15 }}>
          <FontAwesomeIcon
            icon={faFilter}
            style={{ fontSize: "20px", marginRight: "7px" }}
          />{" "}
          <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
          <hr />
          <div className="content">
            <div className="content-wrapper">
              <div className="content-center">
                Hoạt động{" "}
                <Input
                  type="text"
                  placeholder="Nhập mã, tên hoạt động"
                  value={searchName}
                  onChange={handleChangeSearch}
                  style={{ width: "75%", marginLeft: "10px" }}
                />
              </div>
            </div>
            <div className="content-wrapper">
              <div className="content-center">
                Cấp độ{" "}
                <Select
                  value={levelSearch}
                  style={{ width: "75%", marginLeft: "10px" }}
                  onChange={handleLevelSearch}
                >
                  <Option value={""}>Tất cả</Option>
                  {listLevel.map((level) => (
                    <Option key={level.id} value={level.id}>
                      {level.name}
                    </Option>
                  ))}
                </Select>
              </div>
            </div>
            <div className="content-wrapper">
              <div className="content-center">
                Học kỳ{" "}
                <Select
                  style={{ width: "75%", marginLeft: "10px" }}
                  value={semesterSearch}
                  onChange={handleSemesterSearch}
                >
                  <Option value={""}>Chọn học kỳ</Option>
                  {listSemester.map((semester) => (
                    <Option key={semester.id} value={semester.id}>
                      {semester.name +
                        " (" +
                        convertDateLongToString(semester.startTime) +
                        " - " +
                        convertDateLongToString(semester.endTime) +
                        ")"}
                    </Option>
                  ))}
                </Select>
              </div>
            </div>
          </div>
          <div className="box_btn_filter" style={{ paddingBottom: 10 }}>
            <Button
              className="btn_filter"
              onClick={handleSearch}
              style={{ marginRight: "15px" }}
            >
              {" "}
              <FontAwesomeIcon
                icon={faFilterCircleDollar}
                style={{ marginRight: 5 }}
              />
              Tìm kiếm
            </Button>
            <Button
              className="btn_clear"
              style={{ backgroundColor: "rgb(38, 144, 214)", color: "white" }}
              onClick={handleClear}
            >
              <FontAwesomeIcon icon={faChainSlash} style={{ marginRight: 5 }} />
              Làm mới bộ lọc
            </Button>
          </div>
        </div>
        <div className="table_activity_management">
          <div className="title_activity_management_table">
            <div>
              {
                <FontAwesomeIcon
                  icon={faAddressCard}
                  style={{ fontSize: "20px", marginRight: "7px" }}
                />
              }
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
                  marginRight: 5,
                }}
                onClick={dowloadLog}
              >
                <FontAwesomeIcon
                  icon={faFileDownload}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "5px",
                  }}
                />
                Dowload log
              </Button>
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: 5,
                }}
                onClick={openModalShowHistory}
              >
                <FontAwesomeIcon
                  icon={faHistory}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "5px",
                  }}
                />
                Lịch sử
              </Button>
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
                    marginRight: "5px",
                  }}
                />{" "}
                Thêm hoạt động
              </Button>
            </div>
          </div>
          {data.length > 0 ? (
            <div style={{ marginTop: "15px" }}>
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
          ) : (
            <Empty
              style={{ paddingTop: "80px" }}
              imageStyle={{ height: "60px" }}
              description={<span>Không có dữ liệu</span>}
            />
          )}
        </div>
        <ModalCreateActivity
          visible={showCreateModal}
          onCancel={handleModalCreateCancel}
          listSemester={listSemester}
          listLevel={listLevel}
          changeTotalsPage={changeTotalsPage}
          totalPages={total}
          size={10}
        />
        <ModalUpdateActivity
          visible={showUpdateModal}
          onCancel={handleModalUpdateCancel}
          listSemester={listSemester}
          activity={activity}
          listLevel={listLevel}
          fetchData={fetchData}
        />
        <ModalShowHistoryActivity
          visible={visibleHistory}
          onCancel={cancelModalHistory}
        />
      </div>
    </>
  );
};

export default ActivityManagement;
