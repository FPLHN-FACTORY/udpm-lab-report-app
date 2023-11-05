import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-admin-dashboard.css";
import {
  faChalkboardTeacher,
  faChartGantt,
  faCircleInfo,
  faEdit,
  faFilter,
  faGraduationCap,
  faLevelUp,
  faLineChart,
  faUserAltSlash,
} from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { Col, Row, Select } from "antd";
import { ClassAPI } from "../../../api/admin/class-manager/ClassAPI.api";
const { Option } = Select;

const AdFactoryDeploymentStatistics = () => {
  useEffect(() => {
    document.title = "Thống kê triển khai xưởng | Lab-Report-App";
    featchDataSemester();
  }, []);

  const [listSemester, setListSemester] = useState([]);
  const [semester, setSemester] = useState("");

  const featchDataSemester = async () => {
    try {
      const responseClassAll = await ClassAPI.fetchAllSemester();
      const listSemester = responseClassAll.data;
      setListSemester(listSemester.data);
    } catch (error) {}
  };

  const [idActivitiSearch, setIdActivitiSearch] = useState("none");
  const [activityDataAll, setActivityDataAll] = useState([]);

  useEffect(() => {
    if (semester === "") {
      setIdActivitiSearch("none");
      setActivityDataAll([]);
    } else {
      const featchDataActivity = async (idSemesterSeach) => {
        await ClassAPI.getAllActivityByIdSemester(idSemesterSeach).then(
          (respone) => {
            if (respone.data.data.length === 0) {
              setIdActivitiSearch("none");
              setActivityDataAll([]);
            } else {
              setIdActivitiSearch("");
              setActivityDataAll(respone.data.data);
            }
          }
        );
      };
      featchDataActivity(semester);
    }
  }, [semester]);

  return (
    <>
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon
              icon={faChartGantt}
              style={{ marginRight: "8px" }}
            />{" "}
            Thống kê triển khai xưởng
          </span>
        </div>
      </div>
      <div className="box-general" style={{ paddingTop: 10, marginTop: 0 }}>
        <div className="box-son-general">
          <Row
            style={{
              padding: "0 0 5px 10px",
              display: "flex",
              alignItems: "center",
            }}
          >
            <span
              style={{
                fontSize: "20px",
                fontWeight: "500",
                paddingRight: "10px",
              }}
            >
              <FontAwesomeIcon icon={faFilter} style={{ marginRight: "7px" }} />
              Lọc thống kê:
            </span>
            <Col span={6}>
              Học kỳ:
              <Select
                style={{ width: "75%", marginLeft: 7 }}
                value={semester}
                onChange={(value) => {
                  setSemester(value);
                }}
              >
                <Select.Option value="">Chọn 1 học kỳ</Select.Option>
                {listSemester.map((semester) => (
                  <Select.Option key={semester.id} value={semester.id}>
                    {semester.name}
                  </Select.Option>
                ))}
              </Select>
            </Col>
            <Col span={14}>
              Hoạt động:
              <Select
                style={{ width: "80%", marginLeft: 7 }}
                value={idActivitiSearch}
                onChange={(value) => {
                  setIdActivitiSearch(value);
                }}
              >
                {activityDataAll.length > 0 && <Option value="">Tất cả</Option>}
                {activityDataAll.length === 0 && (
                  <Option value="none">Không có hoạt động</Option>
                )}
                {activityDataAll.map((activity) => (
                  <Option key={activity.id} value={activity.id}>
                    {activity.name}
                  </Option>
                ))}
              </Select>
            </Col>
          </Row>{" "}
          <hr />{" "}
          <div
            style={{
              fontSize: 18,
              fontWeight: 500,
              marginTop: 15,
              marginBottom: 20,
              marginLeft: 10,
            }}
          >
            <FontAwesomeIcon icon={faLineChart} style={{ marginRight: 5 }} />{" "}
            Thống kê triển khai xưởng kỳ FALL 2023
          </div>
          <div style={{ marginTop: 20, marginLeft: 10, marginRight: 10 }}>
            <div
              style={{
                width: "100%",
                height: 115,
                border: "none",
                borderTopLeftRadius: 5,
                borderTopRightRadius: 5,
                boxShadow: "0px 0px 1px 1px rgba(148, 148, 148, 0.3)",
                padding: 20,
              }}
            >
              <Row>
                <Col
                  style={{
                    borderRight: "1px solid gray",
                    height: "100%",
                    paddingLeft: 10,
                  }}
                  span={6}
                >
                  <span
                    style={{ fontSize: 18, fontWeight: 500, color: "gray" }}
                  >
                    <FontAwesomeIcon
                      icon={faGraduationCap}
                      style={{ marginRight: "5px" }}
                    />
                    Tổng số lớp học
                  </span>{" "}
                  <br /> <br />
                  <span style={{ fontSize: 24, fontWeight: 700, color: "red" }}>
                    20
                  </span>
                </Col>
                <Col
                  style={{
                    borderRight: "1px solid gray",
                    height: "100%",
                    paddingLeft: 30,
                  }}
                  span={6}
                >
                  <span
                    style={{ fontSize: 18, fontWeight: 500, color: "gray" }}
                  >
                    <FontAwesomeIcon
                      icon={faChalkboardTeacher}
                      style={{ marginRight: "5px" }}
                    />
                    Tổng số giảng viên
                  </span>{" "}
                  <br /> <br />
                  <span style={{ fontSize: 24, fontWeight: 700, color: "red" }}>
                    20
                  </span>
                </Col>
                <Col
                  style={{
                    borderRight: "1px solid gray",
                    height: "100%",
                    paddingLeft: 30,
                  }}
                  span={6}
                >
                  <span
                    style={{ fontSize: 18, fontWeight: 500, color: "gray" }}
                  >
                    <FontAwesomeIcon
                      icon={faUserAltSlash}
                      style={{ marginRight: "5px" }}
                    />{" "}
                    Tổng số sinh viên
                  </span>{" "}
                  <br /> <br />
                  <span style={{ fontSize: 24, fontWeight: 700, color: "red" }}>
                    20
                  </span>
                </Col>
                <Col
                  style={{
                    height: "100%",
                    paddingLeft: 30,
                  }}
                  span={6}
                >
                  <span
                    style={{ fontSize: 18, fontWeight: 500, color: "gray" }}
                  >
                    <FontAwesomeIcon
                      icon={faLevelUp}
                      style={{ marginRight: "5px" }}
                    />{" "}
                    Tổng số lớp chưa có GV
                  </span>{" "}
                  <br /> <br />
                  <span style={{ fontSize: 24, fontWeight: 700, color: "red" }}>
                    0
                  </span>
                </Col>
              </Row>
            </div>
            <div
              style={{
                width: "100%",
                marginTop: 1,
                height: 115,
                border: "none",
                borderBottomLeftRadius: 5,
                borderBottomRightRadius: 5,
                boxShadow: "0px 0px 1px 1px rgba(148, 148, 148, 0.3)",
                padding: 20,
              }}
            >
              <Row>
                <Col
                  style={{
                    borderRight: "1px solid gray",
                    height: "100%",
                    paddingLeft: 10,
                  }}
                  span={6}
                >
                  <span
                    style={{ fontSize: 18, fontWeight: 500, color: "gray" }}
                  >
                    <FontAwesomeIcon
                      icon={faGraduationCap}
                      style={{ marginRight: "5px" }}
                    />
                    Tổng lớp học đủ ĐK
                  </span>{" "}
                  <br /> <br />
                  <span style={{ fontSize: 24, fontWeight: 700, color: "red" }}>
                    20
                  </span>
                </Col>
                <Col
                  style={{
                    borderRight: "1px solid gray",
                    height: "100%",
                    paddingLeft: 30,
                  }}
                  span={6}
                >
                  <span
                    style={{ fontSize: 18, fontWeight: 500, color: "gray" }}
                  >
                    <FontAwesomeIcon
                      icon={faGraduationCap}
                      style={{ marginRight: "5px" }}
                    />
                    Tổng lớp học chưa đủ ĐK
                  </span>{" "}
                  <br /> <br />
                  <span style={{ fontSize: 24, fontWeight: 700, color: "red" }}>
                    20
                  </span>
                </Col>
                <Col
                  style={{
                    borderRight: "1px solid gray",
                    height: "100%",
                    paddingLeft: 30,
                  }}
                  span={6}
                >
                  <span
                    style={{ fontSize: 18, fontWeight: 500, color: "gray" }}
                  >
                    <FontAwesomeIcon
                      icon={faEdit}
                      style={{ marginRight: "5px" }}
                    />{" "}
                    Tổng lớp GV chỉnh sửa
                  </span>{" "}
                  <br /> <br />
                  <span style={{ fontSize: 24, fontWeight: 700, color: "red" }}>
                    20
                  </span>
                </Col>
                <Col
                  style={{
                    height: "100%",
                    paddingLeft: 30,
                  }}
                  span={6}
                >
                  <span
                    style={{ fontSize: 18, fontWeight: 500, color: "gray" }}
                  >
                    <FontAwesomeIcon
                      icon={faLevelUp}
                      style={{ marginRight: "5px" }}
                    />{" "}
                    Tổng số level
                  </span>{" "}
                  <br /> <br />
                  <span style={{ fontSize: 24, fontWeight: 700, color: "red" }}>
                    20
                  </span>
                </Col>
              </Row>
            </div>
          </div>
          <div style={{ marginTop: 20, marginLeft: 10, marginRight: 10 }}>
            <span style={{ fontSize: 18, fontWeight: 500 }}>
              <FontAwesomeIcon
                icon={faChalkboardTeacher}
                style={{ marginRight: "5px" }}
              />
              Thống kê giảng viên
            </span>
          </div>
        </div>
      </div>
    </>
  );
};

export default AdFactoryDeploymentStatistics;
