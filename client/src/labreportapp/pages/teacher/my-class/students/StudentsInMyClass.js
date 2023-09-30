import { useParams } from "react-router-dom";
import "./styleStudentsInMyClass.css";
import { Button, Checkbox, Empty, Input, Table, Tag } from "antd";
import { Link } from "react-router-dom";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherStudentClassesAPI } from "../../../../api/teacher/student-class/TeacherStudentClasses.api";
import {
  GetStudentClasses,
  SetStudentClasses,
} from "../../../../app/teacher/student-class/studentClassesSlice.reduce";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import moment from "moment";
import { ControlOutlined, SearchOutlined } from "@ant-design/icons";
import { SetTTrueToggle } from "../../../../app/teacher/TeCollapsedSlice.reducer";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCircleInfo,
  faRightFromBracket,
  faTableList,
} from "@fortawesome/free-solid-svg-icons";
import { toast } from "react-toastify";
import ModalSentStudent from "./modal-sent-student/ModalSentStudent";

const StudentsInMyClass = () => {
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const [classDetail, setClassDetail] = useState({});
  const [loading, setLoading] = useState(false);
  const { idClass } = useParams();
  const [listIdStudent, setListIdStudent] = useState([]);
  const [showModalSent, setShowModalSent] = useState(false);
  useEffect(() => {
    window.scrollTo(0, 0);
    dispatch(SetStudentClasses([]));
    featchClass(idClass);
  }, []);
  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassDetail(responese.data.data);
        document.title = "Thông tin lớp học | " + responese.data.data.code;
        featchStudentClass(idClass);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const featchStudentClass = async (id) => {
    setLoading(false);
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(id).then(
        (responese) => {
          if (responese.data.data != null) {
            let studentIds = responese.data.data
              .filter((item) => item.idTeam === null)
              .map((item) => item.idStudent);
            setListIdStudent(studentIds);
            dispatch(SetStudentClasses(responese.data.data));
          }
          setLoading(true);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  const data = useAppSelector(GetStudentClasses);
  let columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
      width: "12px",
    },
    {
      title: "Nhóm",
      dataIndex: "nameTeam",
      key: "nameTeam",
      render: (text, record) => {
        if (text === null || text === "") {
          return <Tag color="processing">Chưa vào nhóm</Tag>;
        } else {
          return <span>{text}</span>;
        }
      },
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
      onFilter: (value, record) => {
        if (record.nameTeam === null) {
          return false;
        }
        return record.nameTeam.toLowerCase().includes(value.toLowerCase());
      },
      sorter: (a, b) => {
        if (a.nameTeam === null && b.nameTeam === null) {
          return 0;
        }
        if (a.nameTeam === null) {
          return -1;
        }
        if (b.nameTeam === null) {
          return 1;
        }
        return a.nameTeam.localeCompare(b.nameTeam);
      },
    },
    {
      title: "Tên tài khoản",
      dataIndex: "username",
      key: "username",
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
        record.username.toLowerCase().includes(value.toLowerCase()),
      sorter: (a, b) => a.username.localeCompare(b.username),
    },
    {
      title: "Tên sinh viên",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
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
      onFilter: (value, record) => {
        if (record.name === null) {
          return false;
        }
        return record.name.toLowerCase().includes(value.toLowerCase());
      },
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      sorter: (a, b) => a.email.localeCompare(b.email),
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
      onFilter: (value, record) => {
        if (record.email === null) {
          return false;
        }
        return record.email.toLowerCase().includes(value.toLowerCase());
      },
    },
    {
      title: "Trạng thái",
      dataIndex: "statusStudent",
      key: "statusStudent",
      render: (text) => {
        return (
          <div style={{ textAlign: "center" }}>
            {text === "0" ? (
              <Tag
                color="success"
                style={{ width: "70px", textAlign: "center" }}
              >
                Đạt
              </Tag>
            ) : (
              <Tag color="error" style={{ width: "70px", textAlign: "center" }}>
                Trượt
              </Tag>
            )}
          </div>
        );
      },
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
      onFilter: (value, record) => {
        return (
          (value === "Đạt" && record.statusStudent === "0") ||
          (value === "Trượt" && record.statusStudent === "1")
        );
      },
    },
  ];

  const [checkedList, setCheckedList] = useState([]);
  const handleSentStudent = () => {
    if (checkedList.length === 0) {
      toast.warning("Vui lòng chọn sinh viên cần chuyển lớp !");
      return;
    }
    setShowModalSent(true);
  };
  if (classDetail.statusTeacherEdit === 0 && data != null && data.length > 0) {
    const checkAll = listIdStudent.length === checkedList.length;
    const indeterminate =
      checkedList.length > 0 && checkedList.length < listIdStudent.length;
    const onChangeCheckBox = (idStudent, checked) => {
      setCheckedList(
        checked
          ? [...checkedList, idStudent]
          : checkedList.filter((item) => item !== idStudent)
      );
    };
    const onCheckAllChange = (e) => {
      setCheckedList(e.target.checked ? listIdStudent : []);
    };
    columns = [
      {
        title: classDetail.statusTeacherEdit === 0 && (
          <Checkbox
            indeterminate={indeterminate}
            onChange={onCheckAllChange}
            checked={checkAll}
          />
        ),
        dataIndex: "check",
        key: "check",
        render: (text, record) => (
          <>
            {classDetail.statusTeacherEdit === 0 &&
              (record.nameTeam !== null ||
                (record.nameTeam !== "" && (
                  <Checkbox
                    checked={checkedList.includes(record.idStudent)}
                    onChange={(e) =>
                      onChangeCheckBox(record.idStudent, e.target.checked)
                    }
                  />
                )))}
          </>
        ),
      },
      ...columns,
    ];
  }
  return (
    <>
      {!loading && <LoadingIndicator />}
      <ModalSentStudent
        visible={showModalSent}
        onCancel={() => setShowModalSent(!showModalSent)}
        listIdStudent={checkedList}
        classDetail={classDetail}
      />
      <div className="box-one">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <ControlOutlined style={{ fontSize: "22px" }} />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>{" "}
            <span style={{ color: "gray", fontSize: "14px" }}>
              {" "}
              - Thông tin lớp học
            </span>
          </span>
        </Link>
      </div>
      <div className="box-two-student-in-my-class">
        <div className="box-two-student-in-my-class-son">
          <div className="button-menu">
            <div>
              <Link
                to={`/teacher/my-class/post/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                BÀI ĐĂNG &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/students/${idClass}`}
                id="menu-checked"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                THÔNG TIN LỚP HỌC &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/teams/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                QUẢN LÝ NHÓM &nbsp;
              </Link>{" "}
              <Link
                to={`/teacher/my-class/meeting/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                BUỔI HỌC &nbsp;
              </Link>{" "}
              <Link
                to={`/teacher/my-class/attendance/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM DANH &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/point/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM &nbsp;
              </Link>
              <div
                className="box-center"
                style={{
                  height: "28.5px",
                  width: "auto",
                  backgroundColor: "rgb(38, 144, 214)",
                  color: "white",
                  borderRadius: "5px",
                  float: "right",
                }}
              >
                <span
                  style={{
                    fontSize: "14px",
                    padding: "15px",
                    fontWeight: 500,
                  }}
                >
                  {classDetail.code}
                </span>
              </div>
              <hr />
            </div>
          </div>
          <div className="info-team">
            <div style={{ margin: "15px 0px 15px 15px" }}>
              <span style={{ fontSize: "17px", fontWeight: 500 }}>
                <FontAwesomeIcon
                  icon={faCircleInfo}
                  style={{
                    marginRight: "10px",
                    fontSize: "20px",
                  }}
                />
                Thông tin lớp học :
              </span>
            </div>
            <div className="group-info">
              <span
                className="group-info-item"
                style={{ marginTop: "10px", marginBottom: "15px" }}
              >
                Hoạt động: &nbsp;{classDetail.activityName}
              </span>
              <span className="group-info-item">
                Level: &nbsp; {classDetail.activityLevel}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Thời gian bắt đầu: &nbsp;
                {moment(classDetail.startTime).format("DD-MM-YYYY")}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Tên lớp: &nbsp;{classDetail.code}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Ca học: &nbsp; {classDetail.classPeriod + 1}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Số thành viên: &nbsp;
                {data != null ? data.length : classDetail.classSize}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Mô tả: &nbsp;{classDetail.descriptions}
              </span>
              <span
                className="group-info-item"
                style={{ marginTop: "13px", marginBottom: "15px" }}
              >
                Mã tham gia: &nbsp;{classDetail.passWord}
              </span>
              <span
                className="group-info-item"
                style={{
                  marginTop: "13px",
                  marginBottom: "15px",
                  color: "red",
                }}
              >
                Trạng thái lớp: &nbsp;
                {classDetail.statusClass === 0 ? "Đã mở" : "Đã khóa"}
              </span>
              <span
                className="group-info-item"
                style={{
                  marginTop: "13px",
                  marginBottom: "15px",
                  color: "red",
                }}
              >
                Trello: &nbsp;
                {classDetail.allowUseTrello === 0
                  ? "Cho phép"
                  : "Không cho phép"}
              </span>
              <span
                className="group-info-item"
                style={{
                  marginTop: "13px",
                  marginBottom: "15px",
                  color: "red",
                }}
              >
                Trao đổi sinh viên: &nbsp;
                {classDetail.statusTeacherEdit === 0
                  ? "Cho phép"
                  : "Không cho phép"}
              </span>
            </div>
          </div>
          <span style={{ fontSize: "17px", fontWeight: 500 }}>
            <div style={{ margin: "18px 0px 15px 15px" }}>
              <FontAwesomeIcon
                icon={faTableList}
                style={{
                  marginRight: "10px",
                  fontSize: "20px",
                }}
              />
              Danh sách sinh viên :
              {classDetail.statusTeacherEdit === 0 &&
                data != null &&
                data.length > 0 && (
                  <Button
                    onClick={handleSentStudent}
                    style={{
                      backgroundColor: "rgb(38, 144, 214)",
                      color: "white",
                      float: "right",
                    }}
                  >
                    <FontAwesomeIcon
                      icon={faRightFromBracket}
                      color="white"
                      style={{ paddingRight: "5px" }}
                    />
                    Trao đổi sinh viên
                  </Button>
                )}
            </div>
          </span>

          <div
            style={{ minHeight: "140px", marginTop: "20px" }}
            className="table-teacher"
          >
            {data != null && data.length > 0 ? (
              <>
                <div className="table">
                  <Table
                    dataSource={data}
                    rowKey="id"
                    columns={columns}
                    pagination={false}
                  />
                </div>
              </>
            ) : (
              <>
                <Empty
                  imageStyle={{ height: 60 }}
                  description={<span>Không có dữ liệu</span>}
                />
              </>
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default StudentsInMyClass;
