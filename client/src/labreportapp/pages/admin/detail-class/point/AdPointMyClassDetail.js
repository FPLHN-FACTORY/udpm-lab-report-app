import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMarker } from "@fortawesome/free-solid-svg-icons";
import { ControlOutlined, SearchOutlined } from "@ant-design/icons";
import { Link, useParams } from "react-router-dom";
import { Button, Empty, Input, Row, Tag, Table } from "antd";
import { useEffect, useState } from "react";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { useAppDispatch } from "../../../../app/hook";
import LoadingIndicator from "../../../../helper/loading";
import { SetTTrueToggle } from "../../../../app/teacher/TeCollapsedSlice.reducer";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";

const AdPointMyClassDetail = () => {
  const { idClass } = useParams();
  const dispatch = useAppDispatch();
  const [classDetail, setClassDetail] = useState({});
  const [listPointAdmin, setListPointAdmin] = useState([]);
  const [loading, setLoading] = useState(false);

  dispatch(SetTTrueToggle());
  useEffect(() => {
    window.scrollTo(0, 0);
    fetchData(idClass);
  }, []);

  const fetchData = async (idClass) => {
    await Promise.all([await featchClass(idClass), await featchPoint(idClass)]);
  };

  const featchClass = async (idClass) => {
    try {
      await ClassAPI.detailClassById(idClass).then((responese) => {
        setClassDetail(responese.data.data);
        document.title = "Quản lý điểm | " + responese.data.data.code;
      });
    } catch (error) {}
  };

  const featchPoint = async (idClass) => {
    try {
      setLoading(false);
      await ClassAPI.getPointByIdClass(idClass).then((response) => {
        setListPointAdmin(response.data.data);
        setLoading(true);
      });
    } catch (error) {}
  };

  const columnsPoint = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt - b.stt,
    },
    {
      title: "Nhóm",
      dataIndex: "nameTeam",
      key: "nameTeam",
      render: (text, record) => {
        if (text === "") {
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
      onFilter: (value, record) =>
        record.nameTeam.toLowerCase().includes(value.toLowerCase()),
    },
    {
      title: "Tên sinh viên",
      dataIndex: "nameStudent",
      key: "nameStudent",
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
        record.nameStudent.toLowerCase().includes(value.toLowerCase()),
      sorter: (a, b) => a.nameStudent.localeCompare(b.nameStudent),
    },
    {
      title: "Email",
      dataIndex: "emailStudent",
      key: "emailStudent",
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
        record.emailStudent.toLowerCase().includes(value.toLowerCase()),
    },
    {
      title: "Điểm giai đoạn 1",
      dataIndex: "checkPointPhase1",
      key: "checkPointPhase1",
      render: (text, record) => {
        if (text == null || text === "") {
          text = 0;
        }
        return <span>{text}</span>;
      },
      align: "center",
      sorter: (a, b) => a.checkPointPhase1 - b.checkPointPhase1,
    },
    {
      title: "Điểm giai đoạn 2",
      dataIndex: "checkPointPhase2",
      key: "checkPointPhase2",
      render: (text, record) => {
        if (text == null || text === "") {
          text = 0;
        }
        return <span>{text}</span>;
      },
      align: "center",
      sorter: (a, b) => a.checkPointPhase2 - b.checkPointPhase2,
    },
    {
      title: "Điểm final",
      dataIndex: "finalPoint",
      key: "finalPoint",
      align: "center",
      render: (text, record) => {
        if (
          (record.checkPointPhase1 !== "" && record.checkPointPhase2 !== "") ||
          (record.checkPointPhase1 !== null && record.checkPointPhase2 !== null)
        ) {
          return (
            <div style={{ textAlign: "center" }}>
              {parseFloat(
                (parseFloat(record.checkPointPhase1) +
                  parseFloat(record.checkPointPhase2)) /
                  2
              ).toFixed(2)}
            </div>
          );
        } else {
          return <div tyle={{ textAlign: "center" }}>0</div>;
        }
      },
    },
    {
      title: "Trạng thái điểm",
      dataIndex: "statusPointCustome",
      key: "statusPointCustome",
      render: (text, record) => {
        let ratePoint = parseFloat(
          (parseFloat(record.checkPointPhase1) +
            parseFloat(record.checkPointPhase2)) /
            2
        );
        return (
          <div style={{ textAlign: "center" }}>
            {ratePoint >= record.pointMin ? (
              <Tag
                color="success"
                style={{ width: "60px", textAlign: "center" }}
              >
                Đạt
              </Tag>
            ) : (
              <Tag color="error" style={{ width: "60px", textAlign: "center" }}>
                Trượt
              </Tag>
            )}
          </div>
        );
      },
    },
    {
      title: "Trạng thái điểm danh",
      dataIndex: "statusAttendedCustome",
      key: "statusAttendedCustome",
      render: (text, record) => {
        let rateAttended = parseFloat(
          (parseFloat(record.numberOfSessionAttended) /
            parseFloat(record.numberOfSession)) *
            100
        );
        return (
          <div style={{ textAlign: "center" }}>
            {rateAttended >= record.maximumNumberOfBreaks ? (
              <Tag
                color="success"
                style={{ width: "60px", textAlign: "center" }}
              >
                Đạt
              </Tag>
            ) : (
              <Tag color="error" style={{ width: "60px", textAlign: "center" }}>
                Trượt
              </Tag>
            )}
          </div>
        );
      },
    },
    {
      title: "Tình trạng",
      dataIndex: "statusAll",
      key: "statusAll",
      render: (text, record) => {
        let rateAttended = parseFloat(
          (parseFloat(record.numberOfSessionAttended) /
            parseFloat(record.numberOfSession)) *
            100
        );
        let ratePoint = parseFloat(
          (parseFloat(record.checkPointPhase1) +
            parseFloat(record.checkPointPhase2)) /
            2
        );
        return (
          <div style={{ textAlign: "center" }}>
            {rateAttended >= record.maximumNumberOfBreaks &&
            ratePoint >= record.pointMin ? (
              <Tag
                color="success"
                style={{ width: "60px", textAlign: "center" }}
              >
                Đạt
              </Tag>
            ) : (
              <Tag color="error" style={{ width: "60px", textAlign: "center" }}>
                Trượt
              </Tag>
            )}
          </div>
        );
      },
    },
  ];
  return (
    <div style={{ paddingTop: "35px" }}>
      {!loading && <LoadingIndicator />}
      <div className="title-meeting-managemnt-my-class">
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{
              fontSize: "18px",
              marginLeft: "10px",
              fontWeight: "500",
            }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - Điểm sinh viên</span>
        </span>
      </div>
      <div className="box-filter-meeting-management">
        <div
          className="box-filter-meeting-management-son"
          style={{ minHeight: "600px" }}
        >
          <div className="button-menu-teacher">
            <div>
              <Link
                to={`/admin/class-management/information-class/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                THÔNG TIN LỚP HỌC &nbsp;
              </Link>
              <Link
                to={`/admin/class-management/meeting-management/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                QUẢN LÝ LỊCH HỌC &nbsp;
              </Link>
              <Link
                className="custom-link"
                to={`/admin/class-management/feedback/${idClass}`}
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                FEEDBACK CỦA SINH VIÊN &nbsp;
              </Link>
              <Link
                to={`/admin/class-management/point/${idClass}`}
                id="menu-checked"
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
                ></div>
                <span
                  style={{
                    fontSize: "14px",
                    padding: "15px",
                    fontWeight: 500,
                  }}
                >
                  {classDetail != null ? classDetail.code : ""}
                </span>
              </div>
              <hr />
            </div>
          </div>
          <div style={{ marginTop: "15px" }}>
            <Row style={{ margin: "15px 0px 15px 15px" }}>
              <span
                style={{
                  fontSize: "17px",
                  fontWeight: 500,
                  marginRight: "10px",
                }}
              >
                <FontAwesomeIcon icon={faMarker} /> Điểm sinh viên :
              </span>
            </Row>

            <div style={{ margin: "20px 0px 10px 0px" }}>
              {listPointAdmin.length > 0 ? (
                <div className="table-teacher">
                  <Table
                    columns={columnsPoint}
                    dataSource={listPointAdmin}
                    rowKey="idStudent"
                    pagination={false}
                  />
                </div>
              ) : (
                <Empty
                  imageStyle={{ height: 60 }}
                  description={<span>Không có dữ liệu</span>}
                />
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AdPointMyClassDetail;
