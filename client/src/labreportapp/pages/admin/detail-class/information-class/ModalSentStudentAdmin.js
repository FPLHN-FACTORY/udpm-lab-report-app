import {
  Modal,
  Table,
  Empty,
  Pagination,
  Spin,
  Tooltip,
  Input,
  Button,
} from "antd";
import { useEffect, useState } from "react";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faRightToBracket } from "@fortawesome/free-solid-svg-icons";
import { SearchOutlined } from "@ant-design/icons";
import { convertMeetingPeriodToTime } from "../../../../helper/util.helper";
import { toast } from "react-toastify";
import {
  SetStudentClasses,
  GetStudentClasses,
} from "../../../../app/teacher/student-class/studentClassesSlice.reduce";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";

const ModalSentStudent = ({
  visible,
  onCancel,
  listIdStudent,
  classDetail,
  getSelectedRowKeys,
  getSelectedRowKeysNotKickOrMove,
  id
}) => {
  const [currentModal, setCurrentModal] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [loadingNo, setLoadingNo] = useState(false);
  const [data, setData] = useState([]);
  const [studentInClass, setStudentInClass] = useState([]);
  const dispatch = useAppDispatch();
  const dataStudentClasses = useAppSelector(GetStudentClasses);
  useEffect(() => {
    if (visible === true) {
      featchClassSent();
    } else {
      setCurrentModal(1);
      setTotalPages(0);
      setLoadingNo(false);
      setData([]);
    }
  }, [visible, currentModal]);


  const featchClassSent = async () => {
    setLoadingNo(true);
    try {
      let filter = {
        idSemester: classDetail.semesterId,
        idActivity: classDetail.activityId,
        idLevel: classDetail.levelId,
        idClass: classDetail.id,
        countStudent: listIdStudent.length,
        page: currentModal,
        size: 6,
      };
      await ClassAPI.getClassSentStudent(filter).then((response) => {
        setData(response.data.data.data);
        setTotalPages(response.data.data.totalPages);
        setTimeout(() => {
          setLoadingNo(false);
        }, 120);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const setSelectedRowKeys = (selected, selectedRowKeys, isMoveStudent, isKickStudent) => {
    getSelectedRowKeys(selected, selectedRowKeys, isMoveStudent, isKickStudent);
  }

  const setSelectedRowKeysNotKickOrMove = (selected, selectedRowKeys) => {
    getSelectedRowKeysNotKickOrMove(selected, selectedRowKeys);
  }

  const handleSentStudent = async (idClassSent) => {
    try {
      let dataSent = {
        listIdStudent: listIdStudent,
        idClassSent: idClassSent,
        idClassOld: classDetail.id,
      };
      await ClassAPI.sentStudentClassesToClass(dataSent).then(
        (response) => {
          if (response.data.data) {
            toast.success("Trao đổi sinh viên thành công !");
            if (dataStudentClasses != null) {
              const objFilter = dataStudentClasses.filter(
                (item) => !listIdStudent.includes(item.idStudent)
              );
              try {
                ClassAPI.getAdStudentClassByIdClass(id).then((respone) => {
                  if (respone.data.data.length === 0) {
                    setSelectedRowKeys([], [], false, false);
                  }
                  else {
                    setSelectedRowKeysNotKickOrMove([], []);
                  }
                });
              } catch (error) {
                console.log(error);
              }

              dispatch(SetStudentClasses(objFilter));
            }
            onCancel();
          } else {
            onCancel();
            toast.error("Trao đổi sinh viên thất bại !");
          }
        }
      );
    } catch (error) {
      console.log(error);
    }
  };
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      width: "10px",
      sorter: (a, b) => a.stt - b.stt,
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Mã lớp</div>,
      dataIndex: "code",
      key: "code",
      sorter: (a, b) => a.code.localeCompare(b.code),
    },
    {
      title: <div style={{ textAlign: "center" }}>Sĩ số</div>,
      dataIndex: "classSize",
      key: "classSize",
      sorter: (a, b) => a.classSize - b.classSize,
      align: "center",
    },
    {
      title: <div style={{ textAlign: "center" }}>Ca</div>,
      dataIndex: "classPeriod",
      key: "classPeriod",
      render: (classPeriod) => <span>{classPeriod + 1}</span>,
      sorter: (a, b) => a.classPeriod - b.classPeriod,
    },
    {
      title: <div style={{ textAlign: "center" }}>Thời gian</div>,
      dataIndex: "timePeriod",
      key: "timePeriod",
      render: (text, record) => {
        return <span>{convertMeetingPeriodToTime(record.classPeriod)}</span>;
      },
    },
    {
      title: <div style={{ textAlign: "center" }}>Giảng viên</div>,
      dataIndex: "usernameTeacher",
      key: "usernameTeacher",
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
        if (record.usernameTeacher === null) {
          return "";
        }
        return record.usernameTeacher
          .toLowerCase()
          .includes(value.toLowerCase());
      },
    },
    {
      title: <div style={{ textAlign: "center" }}>Hành động</div>,
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <>
          <div
            className="box_icon"
            style={{ textAlign: "center" }}
            onClick={() => handleSentStudent(record.id)}
          >
            <Tooltip title="Di chuyển">
              <FontAwesomeIcon icon={faRightToBracket} className="icon" />
            </Tooltip>
          </div>
        </>
      ),
      align: "center",
    },
  ];
  return (
    <>
      <Modal
        onCancel={onCancel}
        open={visible}
        width={850}
        footer={null}
        style={{ top: "53px" }}
      >
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Danh sách lớp có thể chuyển</span>
        </div>
        <div style={{ marginTop: "15px" }}>
          <Spin spinning={loadingNo}>
            {data.length > 0 ? (
              <div
                style={{
                  height: data.length > 0 && "540px",
                }}
              >
                <Table
                  rowKey="id"
                  dataSource={data}
                  columns={columns}
                  style={{ paddingTop: "10px" }}
                  pagination={false}
                />
                <div
                  className="pagination-box"
                  style={{ alignContent: "center" }}
                >
                  <Pagination
                    simple
                    current={currentModal}
                    onChange={(value) => {
                      setCurrentModal(value);
                    }}
                    total={totalPages * 10}
                    style={{
                      position: "absolute",
                      bottom: "3px",
                      width: "100%",
                    }}
                  />{" "}
                  <Button
                    className="btn_clean"
                    style={{
                      color: "white",
                      position: "absolute",
                      bottom: "3px",
                      right: "0px",
                    }}
                    onClick={onCancel}
                  >
                    Hủy
                  </Button>
                </div>
              </div>
            ) : (
              <div
                className="pagination-box"
                style={{ alignContent: "center" }}
              >
                <Empty
                  imageStyle={{ height: 60 }}
                  description={<span>Không có dữ liệu</span>}
                />
              </div>
            )}
          </Spin>
        </div>
      </Modal>
    </>
  );
};

export default ModalSentStudent;
