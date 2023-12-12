import React, { useEffect, useState } from "react";
import { Modal, Button, Select, Row, Col, Input, message } from "antd";
import "./style-modal-random-class.css";
import "react-toastify/dist/ReactToastify.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faRandom } from "@fortawesome/free-solid-svg-icons";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import moment from "moment";

import LoadingIndicatorNoOverlay from "../../../../helper/loadingNoOverlay";
import { useAppDispatch } from "../../../../app/hook";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../app/common/Loading.reducer";

const { Option } = Select;

const ModalRandomClass = ({ visible, onCancel, fetchData }) => {
  const [idSemesterSeach, setIdSemesterSearch] = useState("");
  const [semesterDataAll, setSemesterDataAll] = useState([]);
  const [idActivitiSearch, setIdActivitiSearch] = useState("");
  const [activityDataAll, setActivityDataAll] = useState([]);
  const [errorActivity, setErrorActivity] = useState("");
  const [soLopRandom, setSoLopRandom] = useState(0);
  const [errorSoLopRandom, setErrorSoLopRandom] = useState("");
  const [startTime, setStartTime] = useState("");
  const [errorStartTime, setErrorStartTime] = useState("");
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    return () => {
      setIdActivitiSearch("");
      setSoLopRandom(0);
      setErrorActivity("");
      setErrorSoLopRandom("");
      setStartTime("");
      setErrorStartTime("");
    };
  }, [visible]);

  useEffect(() => {
    const featchDataSemester = async () => {
      try {
        const responseClassAll = await ClassAPI.fetchAllSemester();
        const listSemester = responseClassAll.data;
        if (listSemester.data.length > 0) {
          listSemester.data.forEach((item) => {
            if (
              item.startTime <= new Date().getTime() &&
              new Date().getTime() <= item.endTime
            ) {
              setIdSemesterSearch(item.id);
            }
          });
        } else {
          setIdSemesterSearch(null);
        }
        setSemesterDataAll(listSemester.data);
      } catch (error) {}
    };
    featchDataSemester();
  }, []);

  useEffect(() => {
    if (idSemesterSeach === "") {
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
      featchDataActivity(idSemesterSeach);
    }
  }, [idSemesterSeach]);

  const dispatch = useAppDispatch();

  const randomClass = () => {
    let check = 0;
    if (idActivitiSearch === "" || idActivitiSearch === "none") {
      setErrorActivity("Chọn hoạt động cần random lớp");
      ++check;
    } else {
      setErrorActivity("");
    }
    if (startTime === "") {
      setErrorStartTime("Thời gian bắt đầu không được để trống");
      ++check;
    } else {
      setErrorStartTime("");
    }
    if (soLopRandom === "") {
      setErrorSoLopRandom("Số lớp random không được để trống");
      ++check;
    } else {
      setErrorSoLopRandom("");
    }
    if (check === 0) {
      let obj = {
        activityId: idActivitiSearch,
        numberRandon: soLopRandom,
        startTime: moment(startTime, "YYYY-MM-DD").valueOf(),
      };
      dispatch(SetLoadingTrue());
      ClassAPI.randomClass(obj).then((response) => {
        message.success("Random tạo lớp thành công !");
        dispatch(SetLoadingFalse());
        fetchData();
        onCancel();
      });
    }
  };

  return (
    <>
      {loading && <LoadingIndicatorNoOverlay />}
      <Modal
        visible={visible}
        onCancel={onCancel}
        width={700}
        footer={null}
        className="modal_show_detail_project"
      >
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Random lớp học</span>
        </div>
        <div
          style={{
            marginTop: "10px",
            borderBottom: "1px solid gray",
            marginBottom: "2px",
          }}
        >
          <Row>
            <Col span={12} style={{ padding: "5px" }}>
              <span style={{ color: "red" }}>(*) </span> Học kỳ: <br />
              <Select
                showSearch
                style={{ width: "100%" }}
                value={idSemesterSeach}
                onChange={(value) => {
                  setIdSemesterSearch(value);
                }}
              >
                <Option value="">Chọn 1 học kì</Option>

                {semesterDataAll.map((semester) => (
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
            </Col>
            <Col span={12} style={{ padding: "5px" }}>
              <span style={{ color: "red" }}>(*) </span> Hoạt Động: <br />
              <Select
                showSearch
                style={{ width: "100%" }}
                value={idActivitiSearch}
                onChange={(value) => {
                  setIdActivitiSearch(value);
                }}
              >
                {activityDataAll.length > 0 && (
                  <Option value="">Chọn hoạt động cần radom lớp học</Option>
                )}
                {activityDataAll.length === 0 && (
                  <Option value="none">Không có hoạt động</Option>
                )}
                {activityDataAll.map((activity) => (
                  <Option key={activity.id} value={activity.id}>
                    {activity.name}
                  </Option>
                ))}
              </Select>
              <span style={{ color: "red" }}>{errorActivity}</span>
            </Col>
            <Col span={12} style={{ padding: "5px" }}>
              <span style={{ color: "red" }}>(*) </span> Số lớp muốn random:{" "}
              <br />
              <Input
                type="number"
                min={0}
                max={100}
                value={soLopRandom}
                onChange={(e) => {
                  setSoLopRandom(e.target.value);
                }}
              />
              <span style={{ color: "red" }}>{errorSoLopRandom}</span>
            </Col>
            <Col span={12} style={{ padding: "5px" }}>
              <span style={{ color: "red" }}>(*) </span> Thời gian bắt đầu dự
              kiến: <br />
              <Input
                type="date"
                value={startTime}
                onChange={(e) => {
                  setStartTime(e.target.value);
                }}
              />
              <span style={{ color: "red" }}>{errorStartTime}</span>
            </Col>
          </Row>{" "}
          <br />
        </div>

        <div>
          <div style={{ textAlign: "right" }}>
            <div style={{ paddingTop: "15px" }}>
              <Button
                style={{
                  marginRight: "5px",
                  backgroundColor: "rgb(61, 139, 227)",
                  color: "white",
                }}
                onClick={randomClass}
              >
                <FontAwesomeIcon
                  icon={faRandom}
                  style={{ marginRight: "5px" }}
                />
                Random
              </Button>
              <Button
                style={{
                  backgroundColor: "red",
                  color: "white",
                }}
                onClick={onCancel}
              >
                Hủy
              </Button>
            </div>
          </div>
        </div>
      </Modal>
    </>
  );
};
export default ModalRandomClass;
