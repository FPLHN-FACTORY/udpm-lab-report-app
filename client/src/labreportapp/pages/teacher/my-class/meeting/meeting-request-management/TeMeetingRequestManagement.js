import {
  Breadcrumb,
  Button,
  Checkbox,
  Empty,
  Input,
  Modal,
  Pagination,
  Select,
  Table,
  Tag,
  Tooltip,
  message,
} from "antd";
import {
  CheckCircleOutlined,
  CloseCircleOutlined,
  ControlOutlined,
  HomeOutlined,
  SearchOutlined,
  SyncOutlined,
} from "@ant-design/icons";
import { Link, useNavigate, useParams } from "react-router-dom";
import {
  convertDateLongToString,
  convertHourAndMinuteToString,
} from "../../../../../helper/util.helper";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faArrowUp19,
  faBook,
  faCalendarWeek,
  faCircleInfo,
  faEnvelopeOpenText,
  faHandDots,
  faMagnifyingGlass,
  faPaperPlane,
  faPenToSquare,
  faPeopleRoof,
  faTableList,
} from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { TeacherMeetingRequestAPI } from "../../../../../api/teacher/meeting-request/TeacherMeeting.api";
import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
import LoadingIndicator from "../../../../../helper/loading";
import { TeacherMyClassAPI } from "../../../../../api/teacher/my-class/TeacherMyClass.api";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../../app/common/Loading.reducer";
import ModalUpdateMeetingRequest from "./modal-update-meeting-request/ModalUpdateMeetingRequest";
import { AdMeetingPeriodAPI } from "../../../../../api/admin/AdMeetingPeriodAPI";
import { SetAdMeetingPeriod } from "../../../../../app/admin/AdMeetingPeriodSlice.reducer";
import { ClassAPI } from "../../../../../api/admin/class-manager/ClassAPI.api";
import { SetAdTeacher } from "../../../../../app/admin/AdTeacherSlice.reducer";
import {
  GetMeetingRequest,
  SetMeetingRequest,
} from "../../../../../app/teacher/meeting-request/teMeetingRequestSlice.reduce";

const { confirm } = Modal;

const TeMeetingRequestManagement = () => {
  const { idClass } = useParams();
  const [type, setType] = useState(null);
  const [page, setPage] = useState(1);
  const [size, setSize] = useState(50);
  const [totals, setTotals] = useState(1);
  const [loading, setLoading] = useState(false);
  const [classDetail, setClassDetail] = useState({});
  const [lock, setLock] = useState(1);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const [checkedList, setCheckedList] = useState([]);
  const [checkBoxAll, setCheckBoxAll] = useState([]);
  // const [listMeetingRequest, setListMeetingRequest] = useState([]);
  useEffect(() => {
    window.scrollTo(0, 0);
    loadDataMeetingPeriod();
    fetchTeacherData();
    featchClass(idClass);
  }, []);

  useEffect(() => {
    featchMeeting();
  }, [page, size]);

  const featchMeeting = async () => {
    try {
      let data = {
        idClass: idClass,
        size: size,
        page: page,
        statusMeetingRequest: type,
      };
      setLoading(false);
      await TeacherMeetingRequestAPI.getAllMeetingRequest(data).then(
        (response) => {
          let filteredIds = response.data.data.content
            .filter(
              (meetingRequest) => meetingRequest.statusMeetingRequest === 2
            )
            .map((meetingRequest) => meetingRequest.id);
          setCheckBoxAll(filteredIds);
          setTotals(response.data.data.totalPages);
          dispatch(SetMeetingRequest(response.data.data.content));
          setLoading(true);
        }
      );
    } catch (error) {
      setLoading(true);
    }
  };

  const handleSearch = () => {
    setPage(1);
    featchMeeting();
  };

  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassDetail(responese.data.data);
        document.title = "Yêu cầu | " + responese.data.data.code;
        setLock(responese.data.data.statusClass);
      });
    } catch (error) {
      navigate("/teacher/my-class");
    }
  };
  const loadDataMeetingPeriod = () => {
    AdMeetingPeriodAPI.getAll().then((response) => {
      dispatch(SetAdMeetingPeriod(response.data.data));
    });
  };
  const fetchTeacherData = async () => {
    const responseTeacherData = await ClassAPI.fetchAllTeacher();
    const teacherData = responseTeacherData.data.data;
    dispatch(SetAdTeacher(teacherData));
  };
  const onChangeType = (value) => {
    setType(value);
  };

  const onChangeCheckBox = (id, checked) => {
    setCheckedList(
      checked ? [...checkedList, id] : checkedList.filter((item) => item !== id)
    );
  };

  const onCheckAllChange = (e) => {
    setCheckedList(e.target.checked ? checkBoxAll : []);
  };

  const handleSend = () => {
    if (checkedList.length === 0) {
      message.info("Vui lòng chọn buổi học để gửi yêu cầu !");
      return;
    }
    confirm({
      title: "Xác nhận gửi yêu cầu phê duyệt lại",
      content:
        "Bạn có chắc chắn muốn gửi yêu cầu phê duyệt lại lịch học không?",
      onOk() {
        dispatch(SetLoadingTrue());
        TeacherMeetingRequestAPI.sendMeetingRequestAgain({
          listMeetingRequestAgain: checkedList,
          idClass: idClass,
        }).then((response) => {
          if (response.data.data === true) {
            message.success("Gửi yêu cầu thành công !");
          } else {
            message.error("Gửi yêu cầu thất bại !");
          }
          setCheckedList([]);
          dispatch(SetLoadingFalse());
          featchMeeting();
        });
      },
      onCancel() {},
    });
  };

  const filterOption = (input, option) =>
    (option?.label ?? "").toLowerCase().includes(input.toLowerCase());

  const data = useAppSelector(GetMeetingRequest);
  const columns = [
    {
      title: (
        <Checkbox
          indeterminate={
            checkedList.length > 0 && checkedList.length < checkBoxAll.length
          }
          checked={checkBoxAll.length === checkedList.length}
          onChange={(e) => onCheckAllChange(e)}
        />
      ),
      dataIndex: "check",
      key: "check",
      align: "center",
      render: (text, record) => (
        <>
          {record.statusMeetingRequest === 2 && (
            <Checkbox
              checked={checkedList.includes(record.id)}
              onChange={(e) => onChangeCheckBox(record.id, e.target.checked)}
            />
          )}
        </>
      ),
      width: "2%",
    },
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (a, b, index) => index + 1,
      align: "center",
    },
    {
      title: "Buổi học",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      align: "center",
    },
    {
      title: "Ngày",
      dataIndex: "meetingDate",
      key: "meetingDate",
      render: (text, record) => convertDateLongToString(text),
      sorter: (a, b) => a.meetingDate - b.meetingDate,
      align: "center",
    },
    {
      title: "Ca học",
      dataIndex: "meetingPeriod",
      key: "meetingPeriod",
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Thời gian</div>,
      dataIndex: "timePeriod",
      key: "timePeriod",
      render: (text, record) => {
        return (
          <span>
            {convertHourAndMinuteToString(
              record.startHour,
              record.startMinute,
              record.endHour,
              record.endMinute
            )}
          </span>
        );
      },
      align: "center",
    },
    {
      title: "Giảng viên",
      dataIndex: "teacher",
      key: "teacher",
      filterDropdown: ({
        setSelectedKeys,
        selectedKeys,
        confirm,
        clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            placeholder="Tìm kiếm"
            value={selectedKeys[0]}
            onChange={(e) =>
              setSelectedKeys(e.target.value ? [e.target.value] : [])
            }
            onPressEnter={confirm}
            style={{ width: 188, marginBottom: 8, display: "block" }}
          />
          <Button
            type="primary"
            className="btn_search_member"
            onClick={confirm}
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Tìm
          </Button>
          <Button onClick={clearFilters} size="small" style={{ width: 90 }}>
            Đặt lại
          </Button>
        </div>
      ),
      filterIcon: (filtered) => (
        <SearchOutlined style={{ color: filtered ? "#1890ff" : undefined }} />
      ),
      onFilter: (value, record) =>
        record.teacher.toLowerCase().includes(value.toLowerCase()),
    },
    {
      title: "Hình thức",
      dataIndex: "typeMeeting",
      key: "typeMeeting",
      render: (text, record) => {
        return <span>{text === 0 ? "Online" : "Offline"}</span>;
      },
      align: "center",
    },
    {
      title: "Trạng thái",
      dataIndex: "statusMeetingRequest",
      key: "statusMeetingRequest",
      align: "center",
      render: (text, record) => {
        if (text === 0) {
          return (
            <Tag icon={<SyncOutlined spin />} color="processing">
              Chờ phê duyệt
            </Tag>
          );
        } else if (text === 1) {
          return (
            <Tag icon={<CheckCircleOutlined />} color="success">
              Đã phê duyệt
            </Tag>
          );
        } else {
          return (
            <Tag
              icon={<CloseCircleOutlined />}
              color="error"
              style={{ width: "117px", textAlign: "center" }}
            >
              Từ chối
            </Tag>
          );
        }
      },
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      aligh: "center",
      render: (text, record) => (
        <>
          <div>
            {lock === 0 && record.statusMeetingRequest !== 1 && (
              <Tooltip title="Cập nhật">
                <FontAwesomeIcon
                  className="icon"
                  icon={faPenToSquare}
                  onClick={() => {
                    openModalUpdateMeeting(record);
                  }}
                />
              </Tooltip>
            )}{" "}
          </div>
        </>
      ),
    },
  ];

  const [showModalUpdateMeeting, setShowModalUpdateMeeting] = useState(false);
  const [meetingSelected, setMeetingSelected] = useState(null);

  const handleCancelModalUpdateMeeting = () => {
    setShowModalUpdateMeeting(false);
    setMeetingSelected(null);
  };

  const openModalUpdateMeeting = (item) => {
    setMeetingSelected(item);
    setShowModalUpdateMeeting(true);
  };

  const [reasons, setReasons] = useState(null);
  const showReasons = () => {
    TeacherMeetingRequestAPI.showReasons(idClass).then((response) => {
      setReasons(response.data.data);
    });
  };

  useEffect(() => {
    showReasons();
  }, []);

  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="box-one">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <ControlOutlined style={{ fontSize: "23px" }} />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>{" "}
            <span style={{ color: "gray", fontSize: "14px" }}>
              {" "}
              - Chi tiết yêu cầu
            </span>
          </span>
        </Link>
      </div>
      <div className="box-two-student-in-my-class">
        <div
          className="box-two-student-in-my-class-son"
          style={{ minHeight: "505px", width: "100%" }}
        >
          <div
            style={{
              display: "flex",
              alignItems: "center",
            }}
          >
            <Breadcrumb
              style={{ flex: 1, marginLeft: "5px" }}
              items={[
                {
                  href: "/teacher/my-class",
                  title: <HomeOutlined />,
                },
                {
                  title: (
                    <Link to={`/teacher/my-class/meeting/${idClass}`}>
                      <FontAwesomeIcon
                        icon={faBook}
                        style={{ paddingRight: "6px" }}
                      />{" "}
                      <span style={{ fontSize: "15px" }}>Buổi học</span>
                    </Link>
                  ),
                  menu: {
                    items: [
                      {
                        key: "0",
                        label: (
                          <Link to={`/teacher/my-class/meeting/${idClass}`}>
                            <FontAwesomeIcon
                              icon={faBook}
                              style={{ paddingRight: "6px" }}
                            />{" "}
                            <span style={{ fontSize: "15px" }}>Buổi học</span>
                          </Link>
                        ),
                      },
                      {
                        key: "1",
                        label: (
                          <Link to={`/teacher/my-class/post/${idClass}`}>
                            <FontAwesomeIcon
                              icon={faEnvelopeOpenText}
                              style={{ paddingRight: "6px" }}
                            />{" "}
                            <span style={{ fontSize: "15px" }}>Bài đăng</span>
                          </Link>
                        ),
                      },
                      {
                        key: "2",
                        label: (
                          <Link to={`/teacher/my-class/students/${idClass}`}>
                            <FontAwesomeIcon
                              icon={faCircleInfo}
                              style={{ paddingRight: "6px" }}
                            />{" "}
                            <span style={{ fontSize: "15px" }}>
                              Thông tin lớp học
                            </span>
                          </Link>
                        ),
                      },
                      {
                        key: "3",
                        label: (
                          <Link to={`/teacher/my-class/teams/${idClass}`}>
                            <FontAwesomeIcon
                              icon={faPeopleRoof}
                              style={{ paddingRight: "6px" }}
                            />
                            <span style={{ fontSize: "15px" }}>
                              {" "}
                              Quản lý nhóm
                            </span>
                          </Link>
                        ),
                      },
                      {
                        key: "4",
                        label: (
                          <Link to={`/teacher/my-class/attendance/${idClass}`}>
                            <FontAwesomeIcon
                              icon={faHandDots}
                              style={{ paddingRight: "6px" }}
                            />{" "}
                            <span style={{ fontSize: "15px" }}>Điểm danh</span>
                          </Link>
                        ),
                      },
                      {
                        key: "5",
                        label: (
                          <Link to={`/teacher/my-class/point/${idClass}`}>
                            <FontAwesomeIcon
                              icon={faArrowUp19}
                              style={{ paddingRight: "6px" }}
                            />{" "}
                            <span style={{ fontSize: "15px" }}>Điểm</span>
                          </Link>
                        ),
                      },
                    ],
                  },
                },
                {
                  title: (
                    <>
                      {" "}
                      <FontAwesomeIcon
                        icon={faCalendarWeek}
                        style={{ marginRight: "7px" }}
                      />
                      <span style={{ fontSize: "15px" }}>
                        Buổi học yêu cầu{" "}
                        {classDetail != null && "Lớp " + classDetail.code}
                      </span>
                    </>
                  ),
                },
              ]}
            />
            <div>
              <Select
                style={{
                  minWidth: "150px",
                  width: "auto",
                  textAlign: "center",
                }}
                showSearch
                placeholder="Chọn 1 trạng thái"
                optionFilterProp="children"
                onChange={onChangeType}
                filterOption={filterOption}
                value={type}
                options={[
                  {
                    value: "null",
                    label: "Tất cả",
                  },
                  {
                    value: 0,
                    label: "Chờ phê duyệt",
                  },
                  {
                    value: 1,
                    label: "Đã phê duyệt",
                  },
                  {
                    value: 2,
                    label: "Từ chối",
                  },
                ]}
              />
              <Button className="btn_filter" onClick={() => handleSearch()}>
                <FontAwesomeIcon
                  icon={faMagnifyingGlass}
                  style={{ marginRight: "8px" }}
                />
                Tìm kiếm
              </Button>
            </div>
          </div>{" "}
          <hr />
          <div style={{ width: "100%" }}>
            <div style={{ margin: "20px 0px 10px 0px" }}>
              <span style={{ fontSize: "17px", fontWeight: "500px" }}>
                <FontAwesomeIcon
                  icon={faTableList}
                  style={{
                    marginRight: "10px",
                    fontSize: "20px",
                  }}
                />
                Danh sách buổi học yêu cầu
              </span>
              {lock === 0 && (
                <Button
                  className="btn_clear"
                  style={{
                    backgroundColor: "rgb(38, 144, 214)",
                    color: "white",
                    float: "right",
                  }}
                  onClick={() => handleSend()}
                >
                  <FontAwesomeIcon
                    icon={faPaperPlane}
                    style={{ marginRight: "7px" }}
                  />
                  <span>Gửi lại yêu cầu</span>
                </Button>
              )}
            </div>
            <div style={{ marginTop: 15 }}>
              Lí do từ chối gần nhất:
              <span style={{ color: "red" }}>
                {" "}
                {reasons != null ? reasons : "Không có"}
              </span>
            </div>
            <div style={{ width: "auto" }}>
              <div>
                {data.length > 0 ? (
                  <>
                    <div
                      style={{ paddingTop: "15px" }}
                      className="table-teacher"
                    >
                      <Table
                        dataSource={data}
                        rowKey="id"
                        columns={columns}
                        pagination={false}
                      />
                    </div>
                    <div
                      className="pagination-box"
                      style={{
                        display: "flex",
                        marginTop: 20,
                        marginBottom: 20,
                        alignItems: "center",
                        justifyContent: "center",
                      }}
                    >
                      <Pagination
                        simple
                        current={page}
                        onChange={(value) => {
                          setPage(value);
                        }}
                        total={totals * 10}
                      />

                      <Select
                        style={{ width: "100px", marginLeft: "10px" }}
                        value={size}
                        onChange={(e) => {
                          setPage(1);
                          setSize(e);
                        }}
                      >
                        <Select.Option value={10}>10</Select.Option>
                        <Select.Option value={50}>50</Select.Option>
                        <Select.Option value={100}>100</Select.Option>
                      </Select>
                    </div>
                  </>
                ) : (
                  <Empty
                    style={{ paddingTop: "120px" }}
                    imageStyle={{ height: "60px" }}
                    description={<span>Không có dữ liệu</span>}
                  />
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
      <ModalUpdateMeetingRequest
        item={meetingSelected}
        visible={showModalUpdateMeeting}
        onCancel={handleCancelModalUpdateMeeting}
        featchMeeting={featchMeeting}
      />
    </>
  );
};

export default TeMeetingRequestManagement;
