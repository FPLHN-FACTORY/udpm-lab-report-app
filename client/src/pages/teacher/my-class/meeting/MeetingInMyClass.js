import { useParams } from "react-router-dom";
import "./styleMeetingInMyClass.css";
import { Row, Col, Table, Tooltip, Select } from "antd";
import { Link } from "react-router-dom";
import {
  ControlOutlined,
  QuestionCircleFilled,
  UnorderedListOutlined,
} from "@ant-design/icons";
import { TeacherMeetingAPI } from "../../../../api/teacher/meeting/TeacherMeeting.api";
import { TeacherTeamsAPI } from "../../../../api/teacher/teams-class/TeacherTeams.api";
import {
  SetTeams,
  GetTeams,
} from "../../../../app/teacher/teams/teamsSlice.reduce";
import {
  SetMeeting,
  GetMeeting,
} from "../../../../app/teacher/meeting/teacherMeetingSlice.reduce";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import { faEye } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const { Option } = Select;
const MeetingInMyClass = () => {
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);
  const { idClass } = useParams();
  const [idTeamSearch, setIdTeamSearch] = useState("");
  const [listdataMeeting, setListDataMeeting] = useState([]);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - buổi họp";
    //featchData(idClass);
    // featchMeeting(idClass);
    setListDataMeeting([]);
    setIdTeamSearch("");
    featchTeams(idClass);
  }, []);

  // const featchData = async (idClass, idTeam) => {
  //   await featchTeams(idClass);
  //   await featchMeeting(idClass);
  // };
  useEffect(() => {
    featchMeeting(idClass, idTeamSearch);
  }, [idTeamSearch]);
  const featchTeams = async (idClass) => {
    setLoading(false);
    try {
      await TeacherTeamsAPI.getTeamsByIdClass(idClass).then((responese) => {
        dispatch(SetTeams(responese.data.data));
        console.log("----------------------------");
        console.log(responese.data.data);
        // if (responese.data.data.length >= 0) {
        setIdTeamSearch(responese.data.data[0].id);
        // featchMeeting(idClass, responese.data.data[0].id);
        // } else {
        //   setIdTeamSearch("null");
        // }
        setLoading(true);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const featchMeeting = async (idClass, idTeam) => {
    setLoading(false);
    try {
      let dataSearch = {
        idClass: idClass,
        idTeam: idTeam,
      };
      await TeacherMeetingAPI.getAllMeetingByIdClassAndIdTeam(dataSearch).then(
        (responese) => {
          // dispatch(SetMeeting([]));
          // dispatch(SetMeeting(responese.data.data));
          setListDataMeeting(responese.data.data);
          console.log("+++++++++++++++++++++++++++++");
          console.log(responese.data.data);
          setLoading(true);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  useEffect(() => {
    featchMeeting(idClass, idTeamSearch);
  }, [idTeamSearch]);

  const dataTeams = useAppSelector(GetTeams);
  //const dataMeeting = useAppSelector(GetMeeting);
  const dataMeeting = listdataMeeting;
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
      width: "12px",
    },
    {
      title: "Tên buổi",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Mô tả",
      dataIndex: "descriptions",
      key: "descriptions",
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <>
          <div className="box_icon">
            <Link
              to={`/teacher/meeting/detail/${record.id}`}
              className="btn btn-success ml-4"
            >
              <Tooltip title="Xem chi tiết buổi học">
                <FontAwesomeIcon icon={faEye} className="icon" />
              </Tooltip>
            </Link>
          </div>
        </>
      ),
      width: "105px",
    },
  ];
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="title-teacher-my-class">
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{ fontSize: "18px", marginLeft: "10px", fontWeight: "500" }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - lớp của tôi</span>
        </span>
      </div>
      <div className="box-filter">
        <div className="button-menu-teacher">
          <div>
            <Link
              to={`/teacher/my-class/students/${idClass}`}
              className="custom-link"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
              }}
            >
              THÀNH VIÊN TRONG LỚP &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/students-in-class/${idClass}`}
              className="custom-link"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              ĐIỂM DANH &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/teams/${idClass}`}
              className="custom-link"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              QUẢN LÝ NHÓM &nbsp;
            </Link>
            <Link
              to={`/teacher/my-class/meeting/${idClass}`}
              id="menu-checked"
              style={{ fontSize: "16px", paddingLeft: "10px" }}
            >
              BUỔI HỌP &nbsp;
            </Link>
            <hr />
          </div>
        </div>
        <div className="menu-teacher-search">
          <Row
            gutter={16}
            style={{
              margin: "0px 0px 15px 10px",
              paddingTop: "20px",
              fontSize: "14px",
            }}
          >
            <Col span={24}>
              <span>Chọn nhóm</span>
              <QuestionCircleFilled
                style={{ paddingLeft: "12px", fontSize: "15px" }}
              />
              <br />
              {idTeamSearch !== "" ? (
                <>
                  <Select
                    value={idTeamSearch}
                    onChange={(value) => {
                      setIdTeamSearch(value);
                    }}
                    style={{
                      width: "auto",
                      minWidth: "120px",
                      maxWidth: "263px",
                      margin: "6px 0 10px 0",
                    }}
                  >
                    {dataTeams.map((item) => {
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
                </>
              ) : (
                <>
                  <span
                    style={{
                      textAlign: "center",
                      marginTop: "100px",
                      fontSize: "15px",
                      color: "red",
                    }}
                  >
                    Lớp học hiện chưa có nhóm nào, vui lòng tạo nhóm !
                  </span>
                </>
              )}
            </Col>
          </Row>
        </div>
      </div>
      <div className="box-students-in-class">
        <div className="content-class">
          <div
            className="box-center"
            style={{
              height: "28px",
              width: "100px",
              backgroundColor: "#007bff",
              color: "white",
              margin: "0px 0px 0px 92%",
            }}
          >
            {" "}
            <span style={{ fontSize: "14px" }}>
              {dataMeeting.length} buổi học{" "}
            </span>
          </div>
          <Row gutter={16} style={{ margin: "3px 10px 0px 0px" }}>
            <Col span={24}>
              <div style={{ marginLeft: "0px" }}>
                {" "}
                <span style={{ fontSize: "17px", fontWeight: "500" }}>
                  {" "}
                  <UnorderedListOutlined
                    style={{ marginRight: "10px", fontSize: "20px" }}
                  />
                  Danh sách buổi họp
                </span>
              </div>
            </Col>
          </Row>
          <br />
        </div>
        <div>
          {dataMeeting.length > 0 ? (
            <>
              <div className="table">
                <Table
                  dataSource={dataMeeting}
                  rowKey="id"
                  columns={columns}
                  pagination={false}
                />
              </div>
            </>
          ) : (
            <>
              <p
                style={{
                  textAlign: "center",
                  marginTop: "100px",
                  fontSize: "15px",
                  color: "red",
                }}
              >
                Chưa có buổi họp
              </p>
            </>
          )}
        </div>
      </div>
    </>
  );
};

export default MeetingInMyClass;
