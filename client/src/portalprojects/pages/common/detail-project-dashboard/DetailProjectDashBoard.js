import { useState } from "react";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import { GetPeriodCurrent } from "../../../app/reducer/detail-project/DPPeriodSlice.reducer";
import {
  GetProject,
  SetProject,
} from "../../../app/reducer/detail-project/DPProjectSlice.reducer";
import "./styleDetailProjectDashBoard.css";
import LoadingIndicator from "../../../helper/loading";
import { Link, useParams } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faChartColumn,
  faCogs,
  faHome,
  faLineChart,
} from "@fortawesome/free-solid-svg-icons";
import { DetailProjectAPI } from "../../../api/detail-project/detailProject.api";
import { useEffect } from "react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from "recharts";
import { Col, Progress, Row, Select } from "antd";
import { DashboardApi } from "../../../api/dashboard/Dashboard.api";
import HeaderDetailProject from "../detail-project/HeaderDetailProject";
import BurndownChart from "./BurndownChart";
import CanvasJSReact from "@canvasjs/react-charts";

var CanvasJSChart = CanvasJSReact.CanvasJSChart;

const { Option } = Select;

const DetailProjectDashBoard = () => {
  const { id } = useParams();
  const dispatch = useAppDispatch();
  const [detailProject, setDetailProject] = useState(null);
  const [loading, setLoading] = useState(false);
  const [listPeriod, setListPeriod] = useState([]);
  const [progress, setProgress] = useState(0);
  const [idPeriodChange, setIdPeriodChange] = useState("none");

  useEffect(() => {
    fetchData();
    loadDataPeriod();
    document.querySelector(".logo_project").src =
      "https://raw.githubusercontent.com/FPLHN-FACTORY/udpm-common-resources/main/fpoly-udpm/logo-udpm-2.png";

    return () => {
      if (document.querySelector(".logo_project") != null) {
        document.querySelector(".logo_project").src =
          "https://raw.githubusercontent.com/FPLHN-FACTORY/udpm-common-resources/main/fpoly-udpm/logo-udpm-3.png";
      }
    };
  }, []);

  const fetchData = () => {
    DetailProjectAPI.findProjectById(id).then((response) => {
      setDetailProject(response.data.data);
      dispatch(SetProject(response.data.data));
      setProgress(response.data.data.progress);
      document.title = "Thống kê | " + response.data.data.name;
    });
  };

  const loadDataPeriod = () => {
    DetailProjectAPI.findAllPeriodProject(id).then((response) => {
      setListPeriod(response.data.data);
    });
  };

  const loadDashboardAll = () => {
    setLoading(true);
    DashboardApi.dashboardAll(detailProject.id, idPeriodChange).then(
      (response) => {
        setDashboardAll(response.data.data);
        setLoading(false);
      }
    );
  };

  useEffect(() => {
    if (detailProject != null) {
      loadDashboardAll();
    }
  }, [detailProject, idPeriodChange]);

  const handleChangeIdPeriod = (e) => {
    if (e !== "none") {
      DashboardApi.detailPeriod(e).then((response) => {
        setProgress(response.data.data.progress);
      });
    } else {
      fetchData();
    }
    setIdPeriodChange(e);
  };

  const [dashboardAll, setDashboardAll] = useState([]);

  const [typeChartTodoList, setTypeChartTodoList] = useState("column");
  const [typeChartDueDate, setTypeChartDueDate] = useState("column");
  const [typeChartMember, setTypeChartMember] = useState("column");
  const [typeChartLabel, setTypeChartLabel] = useState("column");

  const listType = [
    { id: 1, key: "bar", name: "Biểu đồ cột ngang" },
    { id: 2, key: "column", name: "Biểu đồ cột dọc" },
    { id: 3, key: "pie", name: "Biểu đồ tròn" },
    { id: 4, key: "line", name: "Biểu đồ đường" },
    { id: 5, key: "area", name: "Biểu đồ vùng" },
  ];

  const optionsTodoList = {
    animationEnabled: true,
    theme: "light2",
    title: {
      text: "Thống kê theo danh sách",
    },
    axisX: {
      title: "Danh sách",
      reversed: true,
    },
    axisY: {
      title: "Số thẻ trong danh sách",
      includeZero: true,
    },
    data: [
      {
        type: typeChartTodoList,
        dataPoints: dashboardAll.listDashboardTodoList,
      },
    ],
  };

  const optionsDueDate = {
    animationEnabled: true,
    theme: "light2",
    title: {
      text: "Thống kê theo ngày hạn",
    },
    axisX: {
      title: "Ngày hạn",
      reversed: true,
    },
    axisY: {
      title: "Số thẻ",
      includeZero: true,
    },
    data: [
      {
        type: typeChartDueDate,
        dataPoints: dashboardAll.listDashboardDueDate,
      },
    ],
  };

  const optionsMember = {
    animationEnabled: true,
    theme: "light2",
    title: {
      text: "Thống kê theo thành viên",
    },
    axisX: {
      title: "Thành viên",
      reversed: true,
    },
    axisY: {
      title: "Số thẻ",
      includeZero: true,
    },
    data: [
      {
        type: typeChartMember,
        dataPoints: dashboardAll.listDashboardMember,
      },
    ],
  };

  const optionsLabel = {
    animationEnabled: true,
    theme: "light2",
    title: {
      text: "Thống kê theo nhãn",
    },
    axisX: {
      title: "Nhãn",
      reversed: true,
    },
    axisY: {
      title: "Số thẻ",
      includeZero: true,
    },
    data: [
      {
        type: typeChartLabel,
        dataPoints: dashboardAll.listDashboardLabel,
      },
    ],
  };

  return (
    <div className="detail-project">
      {loading && <LoadingIndicator />}
      <div>
        {" "}
        <HeaderDetailProject />
      </div>
      <div className="dashboard-project-box">
        <div className="dashboard-project-content">
          <div className="dashboard-custom-v2">
            {" "}
            <div className="custom-dashboard-content">
              <div
                style={{
                  borderBottom: "1px solid gray",
                  display: "flex",
                  alignItems: "center",
                  paddingBottom: "10px",
                }}
              >
                <span style={{ fontSize: "18px", fontWeight: "700" }}>
                  <FontAwesomeIcon
                    icon={faLineChart}
                    style={{ marginRight: "7px" }}
                  />
                  Thống kê
                </span>
              </div>
              <div style={{ marginTop: "30px", marginBottom: "25px" }}>
                <Row>
                  <Col
                    span={12}
                    style={{
                      textAlign: "center",
                      justifyContent: "center",
                      display: "flex",
                      alignItems: "center",
                    }}
                  >
                    <span>Bộ lọc:</span>
                    <Select
                      value={idPeriodChange}
                      onChange={handleChangeIdPeriod}
                      style={{ marginLeft: "10px", width: "250px" }}
                    >
                      <Option value="none">Tất cả dự án</Option>
                      {listPeriod != null &&
                        listPeriod.map((item) => (
                          <Option value={item.id} key={item.id}>
                            {item.name}
                          </Option>
                        ))}
                    </Select>
                  </Col>
                  <Col
                    span={12}
                    style={{ textAlign: "center", justifyContent: "center" }}
                  >
                    {" "}
                    <span>Tiến độ chung:</span>
                    <Progress
                      type="circle"
                      percent={progress}
                      strokeColor={{
                        "0%": "#108ee9",
                        "100%": "#87d068",
                      }}
                      strokeWidth={6}
                      width={100}
                      trailColor="#f0f0f0"
                      strokeLinecap="round"
                      style={{
                        boxShadow: "0 0 10px rgba(82, 196, 26, 0.6)",
                        borderRadius: "50%",
                        marginLeft: "10px",
                      }}
                    />
                  </Col>
                </Row>
              </div>
              <div
                style={{ marginLeft: 150, marginRight: 150, marginBottom: 30 }}
              >
                <BurndownChart />
              </div>
              <div
                style={{ marginLeft: 150, marginRight: 150, marginBottom: 30 }}
              >
                <div style={{ fontSize: 20, fontWeight: 500 }}>
                  <FontAwesomeIcon
                    icon={faChartColumn}
                    style={{ marginRight: 5 }}
                  />
                  Các thống kê khác
                </div>

                <div style={{ marginTop: 35 }}>
                  <span style={{ fontSize: 15 }}>Dạng biểu đồ:</span>{" "}
                  <Select
                    style={{ width: 200 }}
                    value={typeChartTodoList}
                    onChange={(e) => {
                      setTypeChartTodoList(e);
                    }}
                  >
                    {listType.map((item) => {
                      return (
                        <Select.Option value={item.key}>
                          {item.name}
                        </Select.Option>
                      );
                    })}
                  </Select>
                  <CanvasJSChart options={optionsTodoList} />
                </div>
                <div style={{ marginTop: 40 }}>
                  <span style={{ fontSize: 15 }}>Dạng biểu đồ:</span>{" "}
                  <Select
                    style={{ width: 200 }}
                    value={typeChartDueDate}
                    onChange={(e) => {
                      setTypeChartDueDate(e);
                    }}
                  >
                    {listType.map((item) => {
                      return (
                        <Select.Option value={item.key}>
                          {item.name}
                        </Select.Option>
                      );
                    })}
                  </Select>
                  <CanvasJSChart options={optionsDueDate} />
                </div>

                <div style={{ marginTop: 40 }}>
                  <span style={{ fontSize: 15 }}>Dạng biểu đồ:</span>{" "}
                  <Select
                    style={{ width: 200 }}
                    value={typeChartMember}
                    onChange={(e) => {
                      setTypeChartMember(e);
                    }}
                  >
                    {listType.map((item) => {
                      return (
                        <Select.Option value={item.key}>
                          {item.name}
                        </Select.Option>
                      );
                    })}
                  </Select>
                  <CanvasJSChart options={optionsMember} />
                </div>

                <div style={{ marginTop: 40 }}>
                  <span style={{ fontSize: 15 }}>Dạng biểu đồ:</span>{" "}
                  <Select
                    style={{ width: 200 }}
                    value={typeChartLabel}
                    onChange={(e) => {
                      setTypeChartLabel(e);
                    }}
                  >
                    {listType.map((item) => {
                      return (
                        <Select.Option value={item.key}>
                          {item.name}
                        </Select.Option>
                      );
                    })}
                  </Select>
                  <CanvasJSChart options={optionsLabel} />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DetailProjectDashBoard;
