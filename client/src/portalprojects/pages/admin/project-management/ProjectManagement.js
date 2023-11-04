import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState, useEffect } from "react";
import {
  faFilter,
  faEye,
  faPenToSquare,
  faCodeCompare,
  faCirclePlus,
  faMattressPillow,
} from "@fortawesome/free-solid-svg-icons";
import "./styleProjectManagement.css";
import {
  CheckCircleOutlined,
  CloseCircleOutlined,
  ProjectOutlined,
  SyncOutlined,
} from "@ant-design/icons";
import {
  Button,
  Pagination,
  Table,
  Tooltip,
  Input,
  Select,
  Row,
  Col,
  Tag,
  DatePicker,
  Empty,
} from "antd";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import {
  GetProjectManagement,
  SetProjectManagement,
} from "../../../app/reducer/admin/project-management/projectManagementSlide.reducer";
import { ProjectManagementAPI } from "../../../api/admin/project-management/projectManagement.api";
import { CategoryProjectManagementAPI } from "../../../api/admin/project-management/categoryProjectManagement.api";
import ModalDetailProject from "../project-management/modal-detail/ModalDetailProject";
import ModalCreateProject from "../project-management/modal-create/ModalCreateProject";
import ModalUpdateProject from "../project-management/modal-update/ModalUpdateProject";
import LoadingIndicator from "../../../helper/loading";
import { convertDateLongToString } from "../../../../labreportapp/helper/util.helper";
import { Link } from "react-router-dom";
import { AdGroupProjectAPI } from "../../../../../src/labreportapp/api/admin/AdGroupProjectAPI";
import { SetCategory } from "../../../app/reducer/admin/category-management/adCategorySlice.reducer";
import dayjs from "dayjs";
import viVN from "antd/lib/locale/vi_VN";
const { RangePicker } = DatePicker;
const { Option } = Select;

const ProjectManagement = () => {
  const dispatch = useAppDispatch();
  const [current, setCurrent] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [idProject, setIdProject] = useState("");
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showDetailModal, setShowDetailModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [codeSearch, setCodeSearch] = useState("");
  const [nameSearch, setNameSearch] = useState("");
  const [startTimeSearch, setStartTimeSearch] = useState("");
  const [endTimeSearch, setEndTimeSearch] = useState("");
  const [statusProjectSearch, setStatusProjectSearch] = useState("");
  const [idCategorySearch, setIdCategorySearch] = useState("");
  const [listCategorySearch, setListCategorySearch] = useState([]);
  const [loading, setLoading] = useState(true);
  const [clear, setClear] = useState(false);
  const [listGroupProject, setListGroupProject] = useState([]);
  const [idGroupProjectSearch, setIdGroupProjectSearch] = useState("");

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Quản lý dự án | Portal-Projects";
    fetchDataCategory();
    featDataGroupProject();
    handleSearch(current);
    return () => {
      dispatch(SetProjectManagement([]));
    };
  }, [current]);

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Quản lý dự án | Portal-Projects";
    if (clear) {
      fetchDataCategory();
      featDataGroupProject();
      handleSearch(1);
      setClear(false);
      return () => {
        dispatch(SetProjectManagement([]));
      };
    }
  }, [clear]);

  useEffect(() => {
    document.title = "Quản lý dự án | Portal-Projects";
    setCodeSearch("");
    setNameSearch("");
    setStartTimeSearch("");
    setEndTimeSearch("");
    setStatusProjectSearch("");
    setIdCategorySearch("");
    setIdGroupProjectSearch("");
  }, []);

  const featDataGroupProject = async () => {
    try {
      await AdGroupProjectAPI.getAllGroupToProjectManagement().then(
        (response) => {
          setListGroupProject(response.data.data);
        }
      );
    } catch (error) {
      console.log(error);
    }
  };

  const fetchDataCategory = async () => {
    const responeGetAllCategory =
      await CategoryProjectManagementAPI.fetchAllCategory();
    setListCategorySearch(responeGetAllCategory.data.data);
    dispatch(SetCategory(responeGetAllCategory.data.data));
  };

  const handleSearch = async (current) => {
    try {
      setLoading(true);
      let filter = {
        code: codeSearch,
        name: nameSearch,
        startTime: startTimeSearch,
        endTime: endTimeSearch,
        statusProject: statusProjectSearch,
        idCategory: idCategorySearch,
        groupProjectId: idGroupProjectSearch,
        page: current,
        size: 5,
      };
      const response = await ProjectManagementAPI.featchAll(filter);
      const responseData = response.data.data;
      setTotalPages(parseInt(responseData.totalPages));
      dispatch(SetProjectManagement(responseData.data));
      if (responseData.totalPages === 1) {
        setCurrent(1);
      }
      setLoading(false);
    } catch (error) {
      setLoading(false);
      console.log(error);
    }
  };

  const handleFind = async () => {
    setCurrent(1);
    await handleSearch(1);
  };
  const handleClear = async () => {
    setCodeSearch("");
    setNameSearch("");
    setStartTimeSearch("");
    setEndTimeSearch("");
    setStatusProjectSearch("");
    setIdCategorySearch("");
    setIdGroupProjectSearch("");
    setCurrent(1);
    setClear(true);
  };

  const handleCancelModalCreateSusscess = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreateModal(false);
    setShowUpdateModal(false);
  };
  const handleCancelModalCreateFaild = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreateModal(false);
    setShowUpdateModal(false);
  };
  const handleCancelCreate = {
    handleCancelModalCreateSusscess,
    handleCancelModalCreateFaild,
  };

  const handleUpdateProject = (id) => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowUpdateModal(true);
    setIdProject(id);
  };

  const handleDetailProject = async (id) => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowDetailModal(true);
    setIdProject(id);
  };

  const handleModalDetailCancel = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setIdProject("");
    setShowDetailModal(false);
  };

  const data = useAppSelector(GetProjectManagement);

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt - b.stt,
    },

    {
      title: "Mã",
      dataIndex: "code",
      key: "code",
      sorter: (a, b) => a.code.localeCompare(b.code),
    },
    {
      title: "Tên",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      render: (text, record) => {
        if (record.backGroundImage) {
          return (
            <div style={{ display: "flex", alignItems: "center" }}>
              <img
                src={record.backGroundImage}
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
                style={{ backgroundColor: record.backGroundColor }}
                className="box_backgroundColor"
              ></div>
              <span>{text}</span>
            </div>
          );
        }
      },
    },
    {
      title: "Thể loại",
      dataIndex: "nameCategorys",
      key: "nameCategorys",
    },
    {
      title: "Nhóm dự án",
      dataIndex: "nameGroupProject",
      key: "nameGroupProject",
      render: (text, record) => {
        return text ? text : <Tag color="geekblue">Không có nhóm</Tag>;
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
      title: <div style={{ textAlign: "center" }}>Trạng thái</div>,
      dataIndex: "statusProject",
      key: "statusProject",
      sorter: (a, b) => a.statusProject - b.statusProject,
      render: (text) => {
        if (text === "0") {
          return (
            <Tag
              icon={<CheckCircleOutlined />}
              style={{ width: "120px", textAlign: "center" }}
              color="success"
            >
              Đã diễn ra
            </Tag>
          );
        } else if (text === "1") {
          return (
            <Tag
              icon={<SyncOutlined spin />}
              style={{ width: "120px", textAlign: "center" }}
              color="processing"
            >
              Đang diễn ra
            </Tag>
          );
        } else {
          return (
            <Tag
              icon={<CloseCircleOutlined />}
              style={{ width: "120px", textAlign: "center" }}
              color="error"
            >
              Chưa diễn ra
            </Tag>
          );
        }
      },
    },
    {
      title: <div style={{ textAlign: "center" }}>Hành động</div>,
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <>
          <div style={{ textAlign: "center" }}>
            <Tooltip title="Xem trello dự án">
              <Link
                to={`/detail-project/${record.id}`}
                style={{ color: "black" }}
              >
                <FontAwesomeIcon
                  icon={faMattressPillow}
                  className="icon"
                  style={{ paddingRight: "5px" }}
                />
              </Link>
            </Tooltip>
            <Tooltip title="Xem chi tiết dự án">
              <FontAwesomeIcon
                icon={faEye}
                className="icon"
                style={{ paddingRight: "5px" }}
                onClick={() => {
                  handleDetailProject(record.id);
                }}
              />
            </Tooltip>
            <Tooltip title="Cập nhật">
              <FontAwesomeIcon
                className="icon"
                icon={faPenToSquare}
                onClick={() => {
                  handleUpdateProject(record.id);
                }}
              />
            </Tooltip>
          </div>
        </>
      ),
    },
  ];

  const handleDateChange = (e) => {
    if (e != null) {
      setStartTimeSearch(
        dayjs(e[0]).set({ hour: 0, minute: 0, second: 0 }).valueOf()
      );
      setEndTimeSearch(
        dayjs(e[1]).set({ hour: 0, minute: 0, second: 0 }).valueOf()
      );
    } else {
      setStartTimeSearch("");
      setEndTimeSearch("");
    }
  };
  return (
    <>
      {loading && <LoadingIndicator />}
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <ProjectOutlined style={{ fontSize: "26px" }} />
            <span style={{ marginLeft: "10px" }}>Quản lý dự án</span>
          </span>
        </div>
      </div>
      <div
        className="project_management"
        style={{ paddingTop: 10, marginTop: 0 }}
      >
        <div className="filter_project_management">
          <FontAwesomeIcon icon={faFilter} style={{ fontSize: "26px" }} />{" "}
          <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
          <hr />
          <Row gutter={24} style={{ marginBottom: "10px", paddingTop: "15px" }}>
            <Col span={8}>
              <span>Mã:</span>{" "}
              <Input
                type="text"
                value={codeSearch}
                onChange={(e) => {
                  setCodeSearch(e.target.value);
                }}
              />
            </Col>
            <Col span={8}>
              <span>Tên:</span>
              <Input
                type="text"
                value={nameSearch}
                onChange={(e) => {
                  setNameSearch(e.target.value);
                }}
              />
            </Col>
            <Col span={8}>
              <span>Thể loại:</span>
              {""}
              <Select
                value={idCategorySearch}
                onChange={(value) => {
                  setIdCategorySearch(value);
                }}
                style={{ width: "100%", marginRight: "10px" }}
              >
                <Option value="">Tất cả</Option>
                {listCategorySearch.map((item) => {
                  return (
                    <Option value={item.id} key={item.id}>
                      {item.name}
                    </Option>
                  );
                })}
              </Select>
            </Col>
          </Row>
          <Row gutter={24} style={{ marginBottom: "10px" }}>
            <Col span={8}>
              <span>Thời gian:</span> <br />
              <RangePicker
                style={{ width: "100%" }}
                format="YYYY-MM-DD"
                value={[
                  startTimeSearch ? dayjs(startTimeSearch) : null,
                  endTimeSearch ? dayjs(endTimeSearch) : null,
                ]}
                onChange={(e) => {
                  handleDateChange(e);
                }}
                locale={viVN}
              />
            </Col>
            <Col span={8}>
              <span>Trạng thái:</span>
              {""}
              <Select
                value={statusProjectSearch}
                onChange={(value) => {
                  setStatusProjectSearch(value);
                }}
                style={{
                  width: "100%",
                }}
              >
                <Option value="">Tất cả</Option>
                <Option value="0">Đã diễn ra</Option>
                <Option value="1">Đang diễn ra</Option>
                <Option value="2">Chưa diễn ra</Option>
              </Select>
            </Col>
            <Col span={8}>
              <span>Nhóm dự án:</span>
              <Select
                showSearch
                style={{ width: "100%" }}
                placeholder="Chọn thuộc nhóm dự án"
                optionFilterProp="children"
                filterOption={(input, option) =>
                  (option?.label ?? "").includes(input)
                }
                filterSort={(optionA, optionB) =>
                  (optionA?.label ?? "")
                    .toLowerCase()
                    .localeCompare((optionB?.label ?? "").toLowerCase())
                }
                value={idGroupProjectSearch}
                onChange={(e) => setIdGroupProjectSearch(e)}
                defaultValue={""}
                options={[
                  { value: "", label: "Tất cả" },
                  { value: "0", label: "Không có nhóm" },
                  ...listGroupProject.map((i) => {
                    return { value: i.id, label: i.name };
                  }),
                ]}
              />
            </Col>
          </Row>
          <div className="box-btn">
            <Button
              className="btn_filter"
              onClick={handleFind}
              style={{ marginRight: "15px", backgroundColor: "#E2B357" }}
            >
              <FontAwesomeIcon
                icon={faFilter}
                style={{ paddingRight: "5px" }}
              />{" "}
              <span>Tìm kiếm</span>
            </Button>
            <Button className="btn_clean" onClick={handleClear}>
              <FontAwesomeIcon
                icon={faCodeCompare}
                style={{ paddingRight: "5px" }}
              />{" "}
              <span>Làm mới bộ lọc</span>
            </Button>
          </div>
        </div>
        <div className="table_project_management">
          <div className="title_project_management">
            <div>
              {" "}
              <span style={{ fontSize: "18px", fontWeight: "500" }}>
                {" "}
                <ProjectOutlined
                  style={{ marginRight: "7px", fontSize: "26px" }}
                />
                Danh sách dự án
              </span>
            </div>
            <div>
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                }}
                onClick={() => {
                  setShowCreateModal(true);
                }}
              >
                <FontAwesomeIcon
                  icon={faCirclePlus}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    paddingRight: "7px",
                  }}
                />{" "}
                Thêm dự án
              </Button>
            </div>
          </div>
          <div
            style={{ marginTop: "15px", minHeight: "170px", height: "auto" }}
          >
            {data.length > 0 ? (
              <>
                {" "}
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
                    total={totalPages * 10}
                  />
                </div>
              </>
            ) : (
              <Empty
                imageStyle={{ height: "60px" }}
                description={<span>Không có dữ liệu</span>}
              />
            )}
          </div>
          <ModalDetailProject
            visible={showDetailModal}
            onCancel={handleModalDetailCancel}
            idProject={idProject}
          />
          <ModalCreateProject
            visible={showCreateModal}
            onCancel={handleCancelCreate}
          />
          <ModalUpdateProject
            visible={showUpdateModal}
            onCancel={handleCancelCreate}
            idProject={idProject}
          />
        </div>
      </div>
    </>
  );
};

export default ProjectManagement;
