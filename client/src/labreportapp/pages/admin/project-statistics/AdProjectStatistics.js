import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-ad-project-statistics.css";
import {
  faCircleInfo,
  faCodeCompare,
  faFilter,
  faLineChart,
  faObjectGroup,
} from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { Button, Card, Col, DatePicker, Row, Select, Tag } from "antd";
import {
  CheckCircleOutlined,
  CloseCircleOutlined,
  SyncOutlined,
} from "@ant-design/icons";
import CanvasJSReact from "@canvasjs/react-charts";
import { AdProjectStatisticsAPI } from "../../../api/admin/AdProjectStatisticsAPI";
import dayjs from "dayjs";
import viVN from "antd/lib/locale/vi_VN";
const { RangePicker } = DatePicker;
var CanvasJSChart = CanvasJSReact.CanvasJSChart;

const AdProjectStatistics = () => {
  const [typeChartList, setTypeChartList] = useState("column");
  const [listProjectProgress, setListProjectProgress] = useState([]);
  const [listProjectTask, setListProjectTask] = useState([]);
  const [startTimeSearch, setStartTimeSearch] = useState("");
  const [endTimeSearch, setEndTimeSearch] = useState("");
  const [countGroupProject, setCountGroupProject] = useState(0);
  const [countProjectNotStart, setCountProjectNotStart] = useState(0);
  const [countProjectStarting, setCountProjectStarting] = useState(0);
  const [countProjectEnding, setCountProjectEnding] = useState(0);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Thống kê dự án xưởng | Lab-Report-App";
  }, []);

  useEffect(() => {
    featchProject();
  }, [startTimeSearch, endTimeSearch]);

  const featchProject = async () => {
    try {
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
      labels: listProjectTask.map((item) => item.name),
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
            x: listProjectTask.length > 0 && listProjectTask.length - index,
            y: item.progress,
            label: item.name,
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
      labelAngle: -45,
    },
    data: [
      {
        type: "rangeColumn",
        name: "Tổng số task",
        color: "#3498db",
        showInLegend: true,
        dataPoints: listProjectTask.map((item) => ({
          label: item.name,
          y: [0, item.totalTasks],
          color: "#3498db",
        })),
      },
      {
        type: "rangeColumn",
        name: "Số task hoàn thành",
        showInLegend: true,
        dataPoints: listProjectTask.map((item) => ({
          label: item.name,
          y: [0, item.completedTasks],
          color: "rgb(66, 186, 66)",
        })),
      },
    ],
  };
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
            style={{ display: "flex", textAlign: "center", paddingTop: "5px" }}
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
                <span>Chưa diễn ra</span>
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
                <span>Đang diễn ra</span>
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
                <span>Đã diễn ra</span>
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
                <span>Số nhóm dự án</span>
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
        </div>
      </div>
    </>
  );
};

export default AdProjectStatistics;
