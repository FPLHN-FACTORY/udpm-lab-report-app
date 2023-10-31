import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-ad-project-statistics.css";
import {
  faCircleInfo,
  faLineChart,
  faMattressPillow,
  faObjectGroup,
} from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import {
  Card,
  Col,
  DatePicker,
  Empty,
  Row,
  Select,
  Table,
  Tag,
  Tooltip,
} from "antd";
import {
  CheckCircleOutlined,
  CloseCircleOutlined,
  SyncOutlined,
} from "@ant-design/icons";
import CanvasJSReact from "@canvasjs/react-charts";
import { AdProjectStatisticsAPI } from "../../../api/admin/AdProjectStatisticsAPI";
import dayjs from "dayjs";
import viVN from "antd/lib/locale/vi_VN";
import { convertDateLongToString } from "../../../helper/util.helper";
import LoadingIndicator from "../../../helper/loading";
import { Link } from "react-router-dom";
const { RangePicker } = DatePicker;
var CanvasJSChart = CanvasJSReact.CanvasJSChart;

const AdProjectStatistics = () => {
  const [typeChartList, setTypeChartList] = useState("column");
  const [typeTable, setTypeTable] = useState(1);
  const [listProjectProgress, setListProjectProgress] = useState([]);
  const [listProjectTask, setListProjectTask] = useState([]);
  const [startTimeSearch, setStartTimeSearch] = useState("");
  const [endTimeSearch, setEndTimeSearch] = useState("");
  const [countGroupProject, setCountGroupProject] = useState(0);
  const [countProjectNotStart, setCountProjectNotStart] = useState(0);
  const [countProjectStarting, setCountProjectStarting] = useState(0);
  const [countProjectEnding, setCountProjectEnding] = useState(0);
  const [listProjectTop, setListProjectTop] = useState([]);
  const [loading, setLoading] = useState(false);
  const MAX_NAME_LENGTH = 10;
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Thống kê dự án xưởng | Lab-Report-App";
    setLoading(true);
  }, []);

  useEffect(() => {
    featchProject();
  }, [startTimeSearch, endTimeSearch]);

  useEffect(() => {
    featchProjectTop();
  }, [startTimeSearch, endTimeSearch, typeTable]);

  const featchProjectTop = async () => {
    try {
      setLoading(true);
      let data = {
        startTime: startTimeSearch,
        endTime: endTimeSearch,
        typeProject: typeTable,
      };
      await AdProjectStatisticsAPI.getProjectAllTop(data).then((response) => {
        setListProjectTop(response.data.data);
        setLoading(false);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const featchProject = async () => {
    try {
      setLoading(true);
      let data = {
        startTime: startTimeSearch,
        endTime: endTimeSearch,
      };
      await AdProjectStatisticsAPI.getProjectAllDuAn(data).then((response) => {
        setListProjectProgress(response.data.data.listProgress);
        setListProjectTask(response.data.data.listTasks);
        setCountProjectEnding(response.data.data.countProjectEnding);
        setCountProjectStarting(response.data.data.countProjectStarting);
        setCountProjectNotStart(response.data.data.countProjectNotStart);
        setCountGroupProject(response.data.data.countGroupProject);
        setLoading(false);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const listType = [
    { id: 1, key: "bar", name: "Biểu đồ cột ngang" },
    { id: 2, key: "column", name: "Biểu đồ cột dọc" },
    { id: 3, key: "pie", name: "Biểu đồ tròn" },
    { id: 4, key: "line", name: "Biểu đồ đường" },
    { id: 5, key: "area", name: "Biểu đồ vùng" },
  ];

  const dataProgress = {
    animationEnabled: true,
    theme: "light2",
    title: {
      text: "Thống kê tiến độ",
    },
    axisX: {
      title: "Danh sách dự án",
      reversed: true,
      interval: 1,
      labels: listProjectProgress.map((item) => item.name),
      labelAngle: -45,
    },
    axisY: {
      title: "Phần trăm",
      includeZero: true,
      maximum: 100,
    },
    data: [
      {
        type: typeChartList,
        dataPoints: listProjectProgress.map((item, index) => {
          return {
            x:
              listProjectProgress.length > 0 &&
              listProjectProgress.length - index,
            y: item.progress,
            label:
              item.name.length > 15
                ? item.name.substring(0, 15) + "..."
                : item.name,
          };
        }),
      },
    ],
  };

  const dataTasks = {
    animationEnabled: true,
    theme: "light2",
    title: {
      text: "Thống kê công việc",
    },
    axisX: {
      title: "Danh sách dự án",
      interval: 1,
      labels: listProjectTask.map((item) => item.name),
      labelAngle: -45,
    },
    axisY: {
      title: "Số task",
      includeZero: true,
    },
    data: [
      {
        type: "rangeColumn",
        name: "Tổng số task",
        color: "#3498db",
        showInLegend: true,
        dataPoints: listProjectTask.map((item) => ({
          label:
            item.name.length > 15
              ? item.name.substring(0, 15) + "..."
              : item.name,
          y: [0, item.totalTasks],
          color: "#3498db",
          name: item.name + " : " + item.completedTasks,
        })),
      },
      {
        type: "rangeColumn",
        name: "Số task hoàn thành",
        showInLegend: true,
        dataPoints: listProjectTask.map((item) => ({
          label:
            item.name.length > 15
              ? item.name.substring(0, 15) + "..."
              : item.name,
          y: [0, item.completedTasks],
          color: "rgb(66, 186, 66)",
          name: item.name + " : " + item.completedTasks,
        })),
      },
    ],
    toolTip: {
      contentFormatter: function (e) {
        return e.entries[0].dataPoint.name;
      },
    },
  };

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
    },
    {
      title: "Tên",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Tiến độ",
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
          <span>
            {startTime} - {endTime}
          </span>
        );
      },
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Trạng thái</div>,
      dataIndex: "statusProject",
      key: "statusProject",
      render: (text) => {
        if (text === 0) {
          return (
            <Tag
              icon={<CheckCircleOutlined />}
              style={{ width: "120px", textAlign: "center" }}
              color="success"
            >
              Đã diễn ra
            </Tag>
          );
        } else if (text === 1) {
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
      align: "center",
    },
    {
      title: "Số thành viên",
      dataIndex: "memberCount",
      key: "memberCount",
      sorter: (a, b) => a.memberCount - b.memberCount,
      align: "center",
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
            <FontAwesomeIcon
              icon={faLineChart}
              style={{ marginRight: "8px" }}
            />{" "}
            Thống kê dự án xưởng
          </span>
        </div>
      </div>
      <div className="box-general" style={{ paddingTop: "0px" }}>
        <div className="box-son-general">
          <Row gutter={24} style={{ padding: "0 0 5px 10px" }}>
            <Col span={18}>
              <span
                style={{
                  fontSize: "18px",
                  fontWeight: "500",
                  paddingRight: "10px",
                }}
              >
                <FontAwesomeIcon
                  icon={faCircleInfo}
                  style={{ marginRight: "7px" }}
                />
                Lọc thời gian:
              </span>
              <RangePicker
                format="YYYY-MM-DD"
                style={{}}
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
          </Row>{" "}
          <hr />
          <Row
            gutter={26}
            style={{
              paddingTop: "5px",
              display: "flex",
              textAlign: "center",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Col span={6}>
              <Card
                bordered={true}
                style={{
                  color: "white",
                  fontWeight: "500",
                  fontSize: "18px",
                  backgroundColor: "#e7615e",
                }}
              >
                <Tag
                  style={{
                    border: "none",
                    backgroundColor: "#e7615e",
                    fontSize: "18px",
                  }}
                  icon={<CloseCircleOutlined style={{ color: "white" }} />}
                />
                <span>Chưa diễn ra : </span>
                <span
                  style={{
                    paddingLeft: "6px",
                  }}
                >
                  {countProjectNotStart}
                </span>
              </Card>
            </Col>
            <Col span={6}>
              <Card
                bordered={true}
                style={{
                  color: "white",
                  fontWeight: "500",
                  fontSize: "18px",
                  backgroundColor: "#3b95d0",
                }}
              >
                <Tag
                  style={{
                    border: "none",
                    fontSize: "18px",
                    backgroundColor: "#3b95d0",
                  }}
                  icon={<SyncOutlined spin style={{ color: "white" }} />}
                />
                <span>Đang diễn ra : </span>
                <span
                  style={{
                    paddingLeft: "6px",
                  }}
                >
                  {countProjectStarting}
                </span>
              </Card>
            </Col>
            <Col span={6}>
              <Card
                bordered={true}
                style={{
                  color: "white",
                  fontWeight: "500",
                  fontSize: "18px",
                  backgroundColor: "#42ba42",
                }}
              >
                <Tag
                  style={{
                    border: "none",
                    fontSize: "18px",
                    backgroundColor: "#42ba42",
                  }}
                  icon={<CheckCircleOutlined style={{ color: "white" }} />}
                />
                <span>Đã diễn ra : </span>
                <span
                  style={{
                    paddingLeft: "6px",
                  }}
                >
                  {countProjectEnding}
                </span>
              </Card>
            </Col>
            <Col span={6}>
              <Card
                bordered={true}
                style={{
                  color: "white",
                  fontWeight: "500",
                  fontSize: "18px",
                  backgroundColor: "#e2aa45",
                }}
              >
                <Tag
                  style={{
                    border: "none",
                    fontSize: "18px",
                    backgroundColor: "#e2aa45",
                  }}
                  icon={
                    <FontAwesomeIcon
                      icon={faObjectGroup}
                      style={{ color: "white" }}
                    />
                  }
                />
                <span>Số nhóm dự án : </span>
                <span
                  style={{
                    paddingLeft: "6px",
                  }}
                >
                  {countGroupProject}
                </span>
              </Card>
            </Col>
          </Row>
          <div style={{ marginTop: "35px", padding: "0px 30px 0px 30px" }}>
            <span style={{ fontSize: 15 }}>Dạng biểu đồ:</span>{" "}
            <Select
              style={{ width: 200 }}
              value={typeChartList}
              onChange={(e) => {
                setTypeChartList(e);
              }}
            >
              {listType.map((item) => {
                return (
                  <Select.Option value={item.key}>{item.name}</Select.Option>
                );
              })}
            </Select>
            <CanvasJSChart options={dataProgress} />
          </div>
          <div style={{ marginTop: "35px", padding: "0px 30px 0px 30px" }}>
            <CanvasJSChart options={dataTasks} />
          </div>
          <div style={{ marginTop: "35px", padding: "0px 30px 0px 30px" }}>
            <span style={{ fontSize: 15 }}>Danh sách:</span>{" "}
            <Select
              style={{ width: "auto", minWidth: "300px" }}
              value={typeTable}
              onChange={(e) => {
                setTypeTable(e);
              }}
            >
              <Select.Option value={1}>
                Top 5 dự án có tiến độ cao nhất
              </Select.Option>
              <Select.Option value={2}>
                Top 5 dự án có tiến độ thấp nhất
              </Select.Option>
              <Select.Option value={3}>
                Top 5 dự án có số thành viên nhiều nhất
              </Select.Option>
              <Select.Option value={4}>
                Top 5 dự án có số thành viên ít nhất
              </Select.Option>
            </Select>
            <div
              style={{ marginTop: "15px", minHeight: "170px", height: "auto" }}
            >
              {listProjectTop.length > 0 ? (
                <>
                  {" "}
                  <Table
                    dataSource={listProjectTop}
                    rowKey="id"
                    columns={columns}
                    pagination={false}
                  />
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
      </div>
    </>
  );
};

export default AdProjectStatistics;
