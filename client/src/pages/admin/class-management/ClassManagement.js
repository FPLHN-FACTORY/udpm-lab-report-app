import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFilter,
  faCogs,
  faPencil,
  faPlus,
  faEye,
  faTags,
  faDownload,
  faUpload,
  faPencilAlt,
} from "@fortawesome/free-solid-svg-icons";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import {
  Button,
  Input,
  Pagination,
  Table,
  Tooltip,
  Select,
  Row,
  Col,
} from "antd";
import LoadingIndicator from "../../../helper/loading";

import { useEffect, useState } from "react";
import { ClassAPI } from "../../../api/admin/class-manager/ClassAPI.api";
import "./style-class-management.css";
import "react-toastify/dist/ReactToastify.css";
import ModalCreateProject from "../../admin/class-management/create-class/ModalCreateClass";
import { Link } from "react-router-dom";
import { SetAdTeacher } from "../../../app/admin/AdTeacherSlice.reducer";
import ModalUpdateClass from "./update-class/ModalUpdateClass";
import { convertMeetingPeriodToTime } from "../../../helper/util.helper";
import {
  GetAdClassManagement,
  SetMyClass,
} from "../../../app/admin/ClassManager.reducer";

const ClassManagement = () => {
  const { Option } = Select;
  const [listClassAll, setlistClassAll] = useState([]); //getAll
  const [teacherDataAll, setTeacherDataAll] = useState([]); // Dữ liệu teacherId và username
  const [semesterDataAll, setSemesterDataAll] = useState([]); // Dữ liệu semester
  const [activityDataAll, setActivityDataAll] = useState([]); // Dữ liệu activity
  const [idSemesterSeach, setIdSemesterSearch] = useState("");
  const [idActivitiSearch, setIdActivitiSearch] = useState("");

  const [selectedItems, setSelectedItems] = useState([]);
  const [code, setCode] = useState("");
  const [selectedItemsPerson, setSelectedItemsPerson] = useState("");
  const [loading, setLoading] = useState(true);
  const [current, setCurrent] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [clear, setClear] = useState(false);

  const dispatch = useAppDispatch();
  const listClassPeriod = [];

  for (let i = 0; i <= 9; i++) {
    listClassPeriod.push("" + i);
  }

  useEffect(() => {
    setIdSemesterSearch("");
    featchAllMyClass();
  }, []);

  useEffect(() => {
    featchAllMyClass();
  }, [current]);
  const featchAllMyClass = async () => {
    setLoading(false);
    let filter = {
      idTeacher: selectedItemsPerson,
      idActivity: idActivitiSearch,
      idSemester: idSemesterSeach,
      code: code,
      classPeriod: selectedItems,
      page: current,
      size: 10,
    };

    try {
      await ClassAPI.getAllMyClass(filter).then((respone) => {
        setTotalPages(parseInt(respone.data.data.totalPages));
        setlistClassAll(respone.data.data.data);
        dispatch(SetMyClass(respone.data.data.data));

        setLoading(true);
      });
    } catch (error) {
      alert("Vui lòng F5 lại trang !");
    }
  };
  useEffect(() => {
    featchAllMyClass();
  }, [clear]);

  useEffect(() => {
    const fetchTeacherData = async () => {
      const responseTeacherData = await ClassAPI.fetchAllTeacher();
      const teacherData = responseTeacherData.data.data;
      dispatch(SetAdTeacher(teacherData));
      setTeacherDataAll(teacherData);
    };
    fetchTeacherData();
  }, []);

  useEffect(() => {
    const featchDataActivity = async (idSemesterSeach) => {
      console.log(idSemesterSeach);
      await ClassAPI.getAllActivityByIdSemester(idSemesterSeach).then(
        (respone) => {
          setActivityDataAll(respone.data.data);
        }
      );
    };
    featchDataActivity(idSemesterSeach);
  }, [idSemesterSeach]);

  useEffect(() => {
    const featchDataSemester = async () => {
      try {
        const responseClassAll = await ClassAPI.fetchAllSemester();
        const listClassAll = responseClassAll.data;
        if (listClassAll.data.length > 0) {
          setIdSemesterSearch(listClassAll.data[0].id);
        } else {
          setIdSemesterSearch("null");
        }
        setSemesterDataAll(listClassAll.data);
      } catch (error) {
        alert("Vui lòng F5 lại trang !");
      }
    };
    featchDataSemester();
  }, []);

  useEffect(() => {
    document.title = "Quản lý lớp | Portal-Projects";
    setCode("");
    setSelectedItems("");
    setSelectedItemsPerson("");
  }, []);

  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt - b.stt,
      width: "3%",
    },
    {
      title: "Mã",
      dataIndex: "code",
      key: "code",
      sorter: (a, b) => a.code.localeCompare(b.code),
    },
    {
      title: "Thời gian bắt đầu",
      dataIndex: "startTime",
      key: "startTime",
      render: (text, record) => {
        const startTime = new Date(record.startTime);

        const formattedStartTime = `${startTime.getDate()}/${
          startTime.getMonth() + 1
        }/${startTime.getFullYear()}`;

        return <span>{formattedStartTime}</span>;
      },
    },
    {
      title: "Ca học",
      dataIndex: "classPeriod",
      key: "classPeriod",
      sorter: (a, b) => a.classPeriod - b.classPeriod,
      render: (text, record) => {
        return <span>{record.classPeriod + 1}</span>;
      },
    },
    {
      title: "Thời gian",
      dataIndex: "timePeriod",
      key: "timePeriod",
      render: (text, record) => {
        return <span>{convertMeetingPeriodToTime(record.classPeriod)}</span>;
      },
    },
    {
      title: "Sĩ số",
      dataIndex: "classSize",
      key: "classSize",
      sorter: (a, b) => a.classSize - b.classSize,
    },
    {
      title: "Giảng viên",
      dataIndex: "userNameTeacher",
      key: "userNameTeacher",
      sorter: (a, b) => a.userNameTeacher.localeCompare(b.userNameTeacher),
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div>
          <Tooltip title="Xem chi tiết">
            <Link to={`/admin/class-management/information-class/${record.id}`}>
              <FontAwesomeIcon
                style={{ marginRight: "15px", cursor: "pointer" }}
                icon={faEye}
                size="1x"
              />
            </Link>
          </Tooltip>
          <Tooltip
            title="Cập nhật"
            onClick={() => {
              setShowUpdateModal(true);
              setIDClassUpdate(record.id);
            }}
          >
            <FontAwesomeIcon
              icon={faPencilAlt}
              size="1x"
              style={{
                marginLeft: 7,
                color: "rgb(55, 137, 220)",
                cursor: "pointer",
              }}
            />
          </Tooltip>
        </div>
      ),
    },
  ];
  const dataClass = useAppSelector(GetAdClassManagement);

  const handleSearchAllByFilter = async () => {
    await featchAllMyClass();
  };
  const handleCodeChange = (e) => {
    setCode(e.target.value);
  };
  const handleSelectChange = (value) => {
    setSelectedItems(value);
  };

  const handleSelectPersonChange = (value) => {
    setSelectedItemsPerson(value);
  };

  const handleClear = () => {
    if (semesterDataAll.length > 0) {
      setIdSemesterSearch(semesterDataAll[0].id);
    } else {
      setIdSemesterSearch("null");
    }
    setSelectedItems("");
    setCode("");
    setIdActivitiSearch("");
    setSelectedItemsPerson("");
    setClear(true);
  };

  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [idClassUpdate, setIDClassUpdate] = useState("");

  const handleModalCreateCancel = async () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowCreateModal(false);
  };

  const handleModalUpdateCancel = async () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowUpdateModal(false);
  };

  const filterOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };

  return (
    <div className="class_management">
      {!loading && <LoadingIndicator />}

      <div className="title_activity_management">
        {" "}
        <FontAwesomeIcon icon={faTags} size="1x" />
        <span style={{ marginLeft: "10px" }}>Quản lý lớp học</span>
      </div>
      <div className="filter">
        <FontAwesomeIcon icon={faFilter} size="2x" />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <Row>
          <Col span={12} style={{ padding: "10px" }}>
            <div>
              <span>Học kỳ:</span>
              <br />
              <Select
                showSearch
                style={{ width: "100%" }}
                value={idSemesterSeach}
                onChange={(value) => {
                  setIdSemesterSearch(value);
                }}
                filterOption={filterOptions}
              >
                <Option value="">Tất cả</Option>

                {semesterDataAll.map((semester) => (
                  <Option key={semester.id} value={semester.id}>
                    {semester.name}
                  </Option>
                ))}
              </Select>
            </div>
          </Col>
          <Col span={12} style={{ padding: "10px" }}>
            <div className="selectSearch3">
              <span>Hoạt Động:</span>
              <br />
              <Select
                showSearch
                style={{ width: "100%" }}
                value={idActivitiSearch}
                onChange={(value) => {
                  setIdActivitiSearch(value);
                }}
                filterOption={filterOptions}
              >
                <Option value="">Tất cả</Option>
                {activityDataAll.map((activity) => (
                  <Option key={activity.id} value={activity.id}>
                    {activity.name}
                  </Option>
                ))}
              </Select>
            </div>
          </Col>
        </Row>
        <Row>
          <Col span={12} style={{ padding: "10px" }}>
            <div>
              <span>Ca học:</span>
              <br />
              <Select
                showSearch
                style={{ width: "100%" }}
                value={selectedItems}
                onChange={handleSelectChange}
              >
                <Option value="">Tất Cả</Option>
                {listClassPeriod.map((value) => {
                  return (
                    <Option value={value} key={value}>
                      {parseInt(value) + 1}
                    </Option>
                  );
                })}
              </Select>
            </div>
          </Col>
          <Col span={12} style={{ padding: "10px" }}>
            <div>
              <span>Giảng viên:</span>
              <br />
              <Select
                showSearch
                style={{ width: "100%" }}
                value={selectedItemsPerson}
                onChange={handleSelectPersonChange}
                filterOption={filterOptions}
              >
                <Option value="">Tất cả</Option>
                {teacherDataAll.map((teacher) => (
                  <Option key={teacher.id} value={teacher.id}>
                    {teacher.userName}
                  </Option>
                ))}
              </Select>
            </div>
          </Col>
        </Row>
        <Row>
          <Col span={12} style={{ padding: "10px" }}>
            <div className="inputCode">
              <span>Mã Lớp:</span>
              <br />
              <Input type="text" value={code} onChange={handleCodeChange} />
            </div>
          </Col>
        </Row>
        <div className="box_btn_filter">
          <Button className="btn_filter" onClick={handleSearchAllByFilter}>
            Tìm kiếm
          </Button>
          <Button className="btn_clear" onClick={handleClear}>
            Làm mới bộ lọc
          </Button>
        </div>
      </div>

      <div className="table-class-management">
        {" "}
        <div style={{ marginBottom: "8px" }}>
          <div className="table-class-management-info">
            {" "}
            <FontAwesomeIcon icon={faCogs} size="1x" />
            <span
              style={{
                fontSize: "18px",
                fontWeight: "500",
                marginBottom: "-50px",
              }}
            >
              {" "}
              Danh sách lớp học
            </span>{" "}
          </div>
          <div className="createButton">
            <Button
              style={{
                color: "white",
                backgroundColor: "rgb(55, 137, 220)",
                marginRight: "5px",
              }}
            >
              <FontAwesomeIcon
                icon={faDownload}
                size="1x"
                style={{
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: "7px",
                }}
              />{" "}
              Export
            </Button>
            <Button
              style={{
                color: "white",
                backgroundColor: "rgb(55, 137, 220)",
                marginRight: "5px",
              }}
            >
              <FontAwesomeIcon
                icon={faUpload}
                size="1x"
                style={{
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: "7px",
                }}
              />{" "}
              Import
            </Button>
            <Button
              style={{
                color: "white",
                backgroundColor: "rgb(55, 137, 220)",
                marginRight: "5px",
              }}
            >
              <FontAwesomeIcon
                icon={faDownload}
                size="1x"
                style={{
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: "7px",
                }}
              />{" "}
              Tải mẫu
            </Button>
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
                icon={faPlus}
                size="1x"
                style={{
                  backgroundColor: "rgb(55, 137, 220)",
                }}
              />{" "}
              Thêm Lớp
            </Button>
          </div>
        </div>
        <br />
        <div>
          <div>
            {listClassAll.length > 0 ? (
              <>
                <div className="table">
                  <Table
                    dataSource={dataClass}
                    rowKey="id"
                    columns={columns}
                    pagination={false}
                  />
                </div>
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
              <>
                <p
                  style={{
                    textAlign: "center",
                    marginTop: "100px",
                    fontSize: "15px",
                    color: "red",
                  }}
                >
                  Không có lớp học
                </p>
              </>
            )}
          </div>
        </div>
      </div>
      <ModalCreateProject
        visible={showCreateModal}
        onCancel={handleModalCreateCancel}
      />
      <ModalUpdateClass
        visible={showUpdateModal}
        onCancel={handleModalUpdateCancel}
        id={idClassUpdate}
      />
    </div>
  );
};

export default ClassManagement;
