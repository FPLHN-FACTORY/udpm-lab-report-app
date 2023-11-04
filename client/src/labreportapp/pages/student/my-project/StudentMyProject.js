import { useEffect, useState } from "react";
import { MyProjectAPI } from "../../../../portalprojects/api/my-project/myProject.api";
import {
  GetMyProject,
  SetMyProject,
} from "../../../../portalprojects/app/reducer/my-project/myProjectSlice.reducer";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import {
  Button,
  Col,
  Empty,
  Input,
  Pagination,
  Row,
  Select,
  Table,
  Tag,
  Tooltip,
} from "antd";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faChainSlash,
  faCodeCompare,
  faEye,
  faFilter,
  faFilterCircleDollar,
} from "@fortawesome/free-solid-svg-icons";
import LoadingIndicator from "../../../helper/loading";
import {
  CheckCircleOutlined,
  CloseCircleOutlined,
  ProjectOutlined,
  SyncOutlined,
} from "@ant-design/icons";
import { AdGroupProjectAPI } from "../../../api/admin/AdGroupProjectAPI";
import { convertDateLongToString } from "../../../helper/util.helper";

const { Option } = Select;

const StudentMyProject = () => {
  const dispatch = useAppDispatch();
  const [name, setName] = useState("");
  const [status, setStatus] = useState("");
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  const [listGroupProject, setListGroupProject] = useState([]);
  const [idGroupProjectSearch, setIdGroupProjectSearch] = useState("");

  useEffect(() => {
    document.title = "Dự án của tôi | Portal-Projects";
    fetchData();
    return () => {
      dispatch(SetMyProject([]));
    };
  }, [current]);

  useEffect(() => {
    featDataGroupProject();
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

  const fetchData = async () => {
    let filter = {
      nameProject: name,
      status: status === "" ? null : parseInt(status),
      groupProjectId: idGroupProjectSearch,
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
      console.log(error);
    }
  };

  const search = () => {
    fetchData();
  };

  const clear = () => {
    setName("");
    setStatus("");
    setIdGroupProjectSearch("");
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
      title: "Nhóm dự án",
      dataIndex: "nameGroupProject",
      key: "nameGroupProject",
      render: (text, record) => {
        return text ? text : <Tag color="geekblue">Không có nhóm</Tag>;
      },
    },
    {
      title: <div style={{ textAlign: "center" }}>Tiến độ</div>,
      dataIndex: "progress",
      key: "progress",
      sorter: (a, b) => a.progress - b.progress,
      render: (text, record) => {
        return <div style={{ textAlign: "center" }}>{record.progress}%</div>;
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
          <div style={{ textAlign: "center" }}>
            {startTime} - {endTime}
          </div>
        );
      },
    },
    {
      title: <div style={{ textAlign: "center" }}>Trạng thái</div>,
      dataIndex: "status",
      key: "status",
      sorter: (a, b) => a.status - b.status,
      render: (text) => {
        return (
          <div style={{ textAlign: "center" }}>
            {text === 0 ? (
              <Tag
                icon={<CheckCircleOutlined />}
                style={{ width: "120px", textAlign: "center" }}
                color="success"
              >
                Đã diễn ra
              </Tag>
            ) : text === 1 ? (
              <Tag
                icon={<SyncOutlined spin />}
                style={{ width: "120px", textAlign: "center" }}
                color="processing"
              >
                Đang diễn ra
              </Tag>
            ) : (
              <Tag
                icon={<CloseCircleOutlined />}
                style={{ width: "120px", textAlign: "center" }}
                color="error"
              >
                Chưa diễn ra
              </Tag>
            )}
          </div>
        );
      },
    },
    {
      title: <div style={{ textAlign: "center" }}>Loại dự án</div>,
      dataIndex: "typeProject",
      key: "typeProject",
      render: (text, record) => {
        return (
          <div style={{ textAlign: "center" }}>
            {text === 0 ? (
              <Tag color="#f50" style={{ width: "120px", textAlign: "center" }}>
                Xưởng dự án
              </Tag>
            ) : (
              <Tag
                color="#108ee9"
                style={{ width: "120px", textAlign: "center" }}
              >
                Xưởng thực hành
              </Tag>
            )}
          </div>
        );
      },
    },
    {
      title: <div style={{ textAlign: "center" }}>Hành động</div>,
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div style={{ textAlign: "center" }}>
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
    <>
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <ProjectOutlined style={{ fontSize: "26px" }} />
            <span style={{ marginLeft: "8px" }}>Dự án tại xưởng</span>
          </span>
        </div>
      </div>
      <div className="my_project_teacher">
        <div className="filter-my-project-teacher">
          <FontAwesomeIcon icon={faFilter} style={{ fontSize: 18 }} />{" "}
          <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
          <hr />
          <Row gutter={24} style={{ padding: "10px" }}>
            <Col span={8}>
              Tên dự án:{" "}
              <Input
                type="text"
                value={name}
                onChange={(e) => {
                  setName(e.target.value);
                }}
                style={{ width: "100%" }}
              />
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
            <Col span={8}>
              <span>Trạng thái:</span>
              {""}
              <Select
                value={status}
                onChange={(value) => {
                  setStatus(value);
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
          </Row>
          <div className="box-btn">
            <Button
              className="btn_filter"
              onClick={search}
              style={{ marginRight: "15px", backgroundColor: "#E2B357" }}
            >
              <FontAwesomeIcon
                icon={faFilter}
                style={{ paddingRight: "5px" }}
              />{" "}
              <span>Tìm kiếm</span>
            </Button>
            <Button className="btn_clean" onClick={clear}>
              <FontAwesomeIcon
                icon={faCodeCompare}
                style={{ paddingRight: "5px" }}
              />{" "}
              <span>Làm mới bộ lọc</span>
            </Button>
          </div>
        </div>
        <div className="table_project_teacher_my_project">
          <div className="title_my_project">
            <ProjectOutlined style={{ fontSize: "26px" }} />
            <span
              style={{
                fontSize: "18px",
                fontWeight: "500",
                paddingLeft: "8px",
              }}
            >
              Danh sách dự án
            </span>
          </div>
          <div
            style={{ marginTop: "25px", minHeight: "240px", height: "auto" }}
          >
            {data.length > 0 ? (
              <>
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
              </>
            ) : (
              <Empty
                imageStyle={{ height: "60px" }}
                description={<span>Không có dữ liệu</span>}
              />
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default StudentMyProject;
