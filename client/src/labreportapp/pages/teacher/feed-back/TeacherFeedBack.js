import { useEffect, useState } from "react";
import LoadingIndicator from "../../../helper/loading";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheck,
  faCodeCompare,
  faCommentDots,
  faFilter,
} from "@fortawesome/free-solid-svg-icons";
import { TeacherActivityAPI } from "../../../api/teacher/activity/TeacherActivity.api";
import { TeacherSemesterAPI } from "../../../api/teacher/semester/TeacherSemester.api";
import { Button, Col, Empty, Row, Select, Table, message } from "antd";
import { TeacherMyClassAPI } from "../../../api/teacher/my-class/TeacherMyClass.api";
import { arrayQuestion } from "../../../helper/util.helper";
import { useAppSelector } from "../../../app/hook";
import { GetUserCurrent } from "../../../app/common/UserCurrent.reducer";
import { TeacherFeedbackAPI } from "../../../api/teacher/feedback/TeacherFeedback.api";
import { convertDateLongToString } from "../../../helper/util.helper";
const { Option } = Select;

const TeacherFeedBack = () => {
  const [listFeedBack, setListFeedBack] = useState([]);
  const [loading, setLoading] = useState(false);
  const [listSemester, setListSemester] = useState([]);
  const [listActivity, setListActivity] = useState([]);
  const [listClass, setListClass] = useState([]);
  const [idSemester, setIdSemester] = useState("");
  const [idActivity, setIdActivity] = useState("");
  const [idClass, setIdClass] = useState("");
  const [nameClass, setNameClass] = useState("");
  const userRedux = useAppSelector(GetUserCurrent);

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Feedback của tôi | Lab-report-app";
    featchDataSemester();
  }, []);

  useEffect(() => {
    if (idSemester === "") {
      setIdActivity("");
      setListActivity([]);
      setIdClass("");
      setListClass([]);
    } else {
      featchDataActivity(idSemester);
    }
    if (idActivity === "") {
      setIdClass("");
      setListClass([]);
    } else {
      if (idSemester !== "") {
        featchDataClass();
      }
    }
  }, [idSemester, idActivity]);

  const featchDataSemester = async () => {
    try {
      await TeacherSemesterAPI.getAllSemesters().then((respone) => {
        setListSemester(respone.data.data);
      });
    } catch (error) {
      console.log(error);
    }
  };
  const featchDataActivity = async (idSemesterSeach) => {
    try {
      await TeacherActivityAPI.getAllActivityByIdSemester(idSemesterSeach).then(
        (respone) => {
          setListActivity(respone.data.data);
        }
      );
    } catch (error) {
      console.log(error);
    }
  };
  const featchDataClass = async () => {
    try {
      let data = {
        idSemester: idSemester,
        idActivity: idActivity,
      };
      await TeacherMyClassAPI.filterClass(data).then((respone) => {
        setListClass(respone.data.data);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const featchDataFeedback = async (idClass) => {
    try {
      setLoading(true);
      await TeacherFeedbackAPI.getAllFeedbackByIdClass(idClass).then(
        (respone) => {
          setListFeedBack(respone.data.data.listFeedback);
          setNameClass(respone.data.data.codeClass);
          setLoading(false);
        }
      );
    } catch (error) {
      console.log(error);
    }
  };

  const handleSearch = () => {
    if (idClass !== "") {
      featchDataFeedback(idClass);
    } else {
      message.error("Vui lòng chọn lớp học để tìm kiếm !");
    }
  };
  const handleClear = () => {
    setNameClass("");
    setIdSemester("");
    setIdActivity("");
    setIdClass("");
    setListActivity([]);
    setListClass([]);
    setListFeedBack([]);
  };

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      align: "center",
    },
    {
      title: "Q1",
      dataIndex: "rateQuestion1",
      key: "rateQuestion1",
      align: "center",
    },
    {
      title: "Q2",
      dataIndex: "rateQuestion2",
      key: "rateQuestion2",
      align: "center",
    },
    {
      title: "Q3",
      dataIndex: "rateQuestion3",
      key: "rateQuestion3",
      align: "center",
    },
    {
      title: "Q4",
      dataIndex: "rateQuestion4",
      key: "rateQuestion4",
      align: "center",
    },
    {
      title: "Q5",
      dataIndex: "rateQuestion5",
      key: "rateQuestion5",
      align: "center",
    },
    {
      title: "Trung bình",
      dataIndex: "averageRate",
      key: "averageRate",
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Nhận xét</div>,
      dataIndex: "descriptions",
      key: "descriptions",
    },
    {
      title: "Sinh viên",
      dataIndex: "studentUserName",
      key: "studentUserName",
      render: (text, record) => {
        return <span>{text}</span>;
      },
      align: "center",
    },
  ];

  const filterOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };
  return (
    <>
      {" "}
      {loading && <LoadingIndicator />}
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon icon={faCheck} size="20px" />
            <span style={{ marginLeft: "8px" }}>Feedback</span>
          </span>
        </div>
      </div>
      <div className="box-three-dashboad">
        <div className="box-three-son-dashboad">
          <Row>
            <FontAwesomeIcon
              icon={faFilter}
              style={{ fontSize: "20px", paddingRight: "8px" }}
            />
            <span style={{ fontWeight: "400", fontSize: "18px" }}> Bộ lọc</span>
          </Row>
          <hr />
          <div className="title-box-two">
            <Row gutter={24} style={{ margin: "5px 10px 5px 10px" }}>
              <Col span={6}>
                <span>Học kỳ</span>
                <br />
                {listSemester.length > 0 ? (
                  <Select
                    value={idSemester}
                    onChange={(value) => {
                      setIdSemester(value);
                    }}
                    showSearch
                    filterOption={filterOptions}
                    style={{
                      width: "100%",
                      margin: "6px 0 10px 0",
                    }}
                  >
                    {" "}
                    <Option value="">Chọn 1 học kỳ</Option>
                    {listSemester.map((item) => {
                      return (
                        <Option
                          value={item.id}
                          key={item.id}
                          style={{ width: "auto" }}
                        >
                          {item.name}
                        </Option>
                      );
                    })}
                  </Select>
                ) : (
                  <p>Không có học kỳ</p>
                )}
              </Col>
              <Col span={10}>
                <span>Hoạt động</span>
                <br />
                {listActivity.length > 0 ? (
                  <Select
                    showSearch
                    filterOption={filterOptions}
                    value={idActivity}
                    onChange={(value) => {
                      setIdActivity(value);
                    }}
                    style={{
                      width: "100%",
                      margin: "6px 0 10px 0",
                    }}
                  >
                    <Option value="">Chọn 1 hoạt động</Option>
                    {listActivity.map((item) => {
                      return (
                        <Option
                          value={item.id}
                          key={item.id}
                          style={{ width: "auto" }}
                        >
                          {item.name} ({convertDateLongToString(item.startTime)}
                          {" - "}
                          {convertDateLongToString(item.endTime)})
                        </Option>
                      );
                    })}
                  </Select>
                ) : (
                  <p>Không có hoạt động</p>
                )}
              </Col>
              <Col span={8}>
                <span>Lớp học</span>
                <br />
                {listClass.length > 0 ? (
                  <Select
                    showSearch
                    filterOption={filterOptions}
                    value={idClass}
                    onChange={(value) => {
                      setIdClass(value);
                    }}
                    style={{
                      width: "100%",
                      margin: "6px 0 10px 0",
                    }}
                  >
                    <Option value="">Chọn 1 lớp học</Option>
                    {listClass.map((item) => {
                      return (
                        <Option value={item.id} key={item.id} title={item.code}>
                          {item.code}
                        </Option>
                      );
                    })}
                  </Select>
                ) : (
                  <p>Không có lớp học</p>
                )}
              </Col>
            </Row>{" "}
            <div style={{ textAlign: "center", paddingBottom: "10px" }}>
              <Button
                className={"btn_filter"}
                onClick={() => {
                  handleSearch();
                }}
                style={{
                  marginRight: "20px",
                }}
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
            <div style={{ paddingBottom: "10px" }}>
              <i style={{ color: "red", float: "right" }}>
                <div>
                  {" "}
                  <span>(*)Vui lòng chọn lớp để lọc</span>
                </div>
              </i>
            </div>
          </div>
        </div>
      </div>
      <div className="box-four-dashboad">
        <div
          className="box-four-son-dashboad"
          style={{ minHeight: "355px", height: "auto" }}
        >
          <Row>
            <Col span={24} style={{ fontSize: "17px" }}>
              <FontAwesomeIcon icon={faCommentDots} />{" "}
              <span>Feedback của giảng viên</span>
              {userRedux && (
                <span style={{ fontWeight: 500 }}>
                  {" "}
                  {userRedux.name} - {userRedux.userName}
                </span>
              )}
              {nameClass !== "" && (
                <>
                  {" "}
                  <span>dạy lớp </span>
                  <span style={{ fontWeight: 500 }}>{nameClass}</span>
                </>
              )}
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <div className="group-info">
                {arrayQuestion.length > 0 &&
                  arrayQuestion.map((i, index) => {
                    return (
                      <div style={{ padding: "15px 0 0px 15px" }} key={index}>
                        <span key={index}>
                          {i} <br />
                        </span>
                      </div>
                    );
                  })}
                <br />
              </div>
            </Col>
          </Row>
          <div style={{ paddingTop: "10px" }}>
            {listFeedBack.length > 0 ? (
              <>
                <div style={{ paddingTop: "15px" }} className="table-teacher">
                  <Table
                    dataSource={listFeedBack}
                    rowKey="id"
                    columns={columns}
                    pagination={false}
                  />
                  <div
                    style={{
                      padding: "15px 0 10px 10px",
                      fontSize: "17px",
                      fontWeight: "bold",
                    }}
                  >
                    <span> Điểm trung bình: </span>
                    {(
                      listFeedBack.reduce(
                        (total, feedback) => total + feedback.averageRate,
                        0
                      ) / listFeedBack.length
                    ).toFixed(2)}
                  </div>
                </div>
              </>
            ) : (
              <Empty
                imageStyle={{ height: 60 }}
                description={<span>Không có dữ liệu</span>}
              />
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default TeacherFeedBack;
