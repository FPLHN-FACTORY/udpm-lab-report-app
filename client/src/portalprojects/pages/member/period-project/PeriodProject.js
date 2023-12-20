import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./stylePeriodProject.css";
import {
  faCogs,
  faEye,
  faFilter,
  faHome,
  faRepeat,
  faPencil,
  faTrash,
  faPlus,
} from "@fortawesome/free-solid-svg-icons";
import {
  Button,
  Empty,
  Input,
  Pagination,
  Popconfirm,
  Select,
  Table,
  Tag,
  Tooltip,
  message,
} from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";
import { useAppSelector, useAppDispatch } from "../../../app/hook";
import { SetProject } from "../../../app/reducer/detail-project/DPProjectSlice.reducer";
import { DetailProjectAPI } from "../../../api/detail-project/detailProject.api";
import { PeriodProjectAPI } from "../../../api/period-project/periodProject.api";
import {
  GetPeriodProject,
  SetPeriodProject,
  DeletePeriodProject,
} from "../../../app/reducer/member/period-project/periodProjectSlice.reducer";
import "react-toastify/dist/ReactToastify.css";
import ModalDetailPeriod from "./modal-detail/ModalDetailPeriod";
import ModalUpdatePeriod from "./modal-update/ModalUpdatePeriod";
import ModalCreatePeriod from "./modal-create/ModalCreatePeriod";
import LoadingIndicator from "../../../helper/loading";
import HeaderDetailProject from "../../common/detail-project/HeaderDetailProject";
import logoUdpm2 from "../../../../labreportapp/assets/img/logo-udpm-2.png";
import logoUdpm3 from "../../../../labreportapp/assets/img/logo-udpm-3.png";
import {
  CheckCircleOutlined,
  CloseCircleOutlined,
  ExclamationCircleOutlined,
  SyncOutlined,
} from "@ant-design/icons";
import { convertDateLongToString } from "../../../../labreportapp/helper/util.helper";
import {
  GetProjectCustom,
  SetProjectCustom,
} from "../../../app/reducer/detail-project/DPDetailProjectCustom.reduce";
import ModalUpdateFiledProject from "./modal-update-filed-project/ModalUpdateFiledProject";
import {
  GetCheckRole,
  SetCheckRole,
} from "../../../app/reducer/detail-project/DPDetailProjectCheckRole.reducer";

const { Option } = Select;

const PeriodProject = () => {
  const [name, setName] = useState("");
  const [status, setStatus] = useState("");
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const { id } = useParams();
  const dispatch = useAppDispatch();
  const [detailProject, setDetailProject] = useState({});
  const [idPeriod, setIdPeriod] = useState("");
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    DetailProjectAPI.checkMemberProject(id).then(
      (response) => {},
      (error) => {}
    );
  }, []);
  useEffect(() => {
    fetchDataProjectCustom();
    return () => {
      dispatch(SetPeriodProject([]));
      dispatch(SetProject([]));
    };
  }, []);

  useEffect(() => {
    DetailProjectAPI.checkRole(id).then(
      (response) => {
        console.log(response);
        dispatch(SetCheckRole(response.data));
      },
      (error) => {}
    );
  }, []);

  const checkRole = useAppSelector(GetCheckRole);

  const fetchDataProjectCustom = () => {
    DetailProjectAPI.findProjectCustomById(id).then((response) => {
      setDetailProject(response.data.data.project);
      dispatch(SetProject(response.data.data.project));
      dispatch(SetProjectCustom(response.data.data.projectCustom));
      document.title = "Quản lý giai đoạn | " + response.data.data.project.name;
    });
  };

  useEffect(() => {
    fetchDataPeriod();
    document.querySelector(".logo_project").src = logoUdpm2;

    return () => {
      if (document.querySelector(".logo_project") != null) {
        document.querySelector(".logo_project").src = logoUdpm3;
      }
    };
  }, [current]);

  const fetchDataPeriod = () => {
    setLoading(true);
    PeriodProjectAPI.fetchAllPeriodByIdProject(
      id,
      name,
      status === "" ? null : status
    ).then((response) => {
      dispatch(SetPeriodProject(response.data.data.data));
      setTotal(response.data.data.totalPages);
      setLoading(false);
    });
  };
  const dataProject = useAppSelector(GetProjectCustom);
  const data = useAppSelector(GetPeriodProject);

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      sorter: (a, b) => a.stt.localeCompare(b.stt),
    },
    {
      title: "Tên giai đoạn",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Tiến độ",
      dataIndex: "progress",
      key: "progress",
      sorter: (a, b) => a.progress - b.progress,
      render: (text) => {
        return <span>{text} %</span>;
      },
    },
    {
      title: "Thời gian",
      dataIndex: "startTimeAndEndTime",
      key: "startTimeAndEndTime",
      render: (text, record) => {
        return (
          <span>
            {convertDateLongToString(record.startTime)}
            {" - "}
            {convertDateLongToString(record.endTime)}
          </span>
        );
      },
      align: "center",
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      sorter: (a, b) => a.status - b.status,
      render: (text) => {
        if (text === 0) {
          return (
            <Tag
              icon={<CheckCircleOutlined />}
              style={{ width: "120px", textAlign: "center" }}
              color="success"
            >
              Đã diễn ra
            </Tag>
          );
        } else if (text === 1) {
          return (
            <Tag
              icon={<SyncOutlined spin />}
              style={{ width: "120px", textAlign: "center" }}
              color="processing"
            >
              Đang diễn ra
            </Tag>
          );
        } else {
          return (
            <Tag
              icon={<CloseCircleOutlined />}
              style={{ width: "120px", textAlign: "center" }}
              color="error"
            >
              Chưa diễn ra
            </Tag>
          );
        }
      },
      align: "center",
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div>
          <Tooltip title="Xem chi tiết">
            <FontAwesomeIcon
              style={{ marginRight: "15px", cursor: "pointer" }}
              icon={faEye}
              onClick={() => {
                handleTaskClick(record.id);
              }}
              className="icon"
              size="1x"
            />
          </Tooltip>
          {checkRole && (
            <Tooltip title="Cập nhật">
              <FontAwesomeIcon
                className="icon"
                onClick={() => {
                  handlePeriodUpdate(record.id);
                }}
                style={{ marginRight: "15px", cursor: "pointer" }}
                icon={faPencil}
                size="1x"
              />
            </Tooltip>
          )}
          {checkRole && (
            <Popconfirm
              title="Xóa giai đoạn"
              description="Bạn có chắc chắn muốn xóa giai đoạn này không?"
              onConfirm={() => {
                deletePeriod(record.id);
              }}
              okText="Có"
              cancelText="Không"
            >
              <Tooltip title="Xóa">
                <FontAwesomeIcon
                  className="icon"
                  style={{ cursor: "pointer" }}
                  icon={faTrash}
                  size="1x"
                />
              </Tooltip>
            </Popconfirm>
          )}
        </div>
      ),
    },
  ];

  const [showDetailModal, setShowDetailModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showUpdateFiledProject, setShowUpdateFiledProject] = useState(false);

  const handleTaskClick = (id) => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowDetailModal(true);
    setIdPeriod(id);
  };

  const handleModalCancel = () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowDetailModal(false);
  };

  const handlePeriodUpdate = (id) => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowUpdateModal(true);
    setIdPeriod(id);
  };

  const handleModalUpdateCancel = () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowUpdateModal(false);
  };

  const handlePeriodCreate = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreateModal(true);
    setIdPeriod(id);
  };

  const handleModalCreateCancel = () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowCreateModal(false);
  };

  const handleModalUpdateFiledProjectCancel = () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowUpdateFiledProject(false);
  };

  const handleSearch = () => {
    fetchDataPeriod();
  };

  const clear = () => {
    setName("");
    setStatus("");
  };

  const deletePeriod = (id) => {
    PeriodProjectAPI.delete(id, detailProject.id).then(
      (response) => {
        message.success("Xóa thành công !");
        dispatch(DeletePeriodProject(response.data.data));
      },
      (error) => {}
    );
  };

  return (
    <div className="detail-project">
      {loading && <LoadingIndicator />}
      <div>
        {" "}
        <HeaderDetailProject />
      </div>
      <div className="period-project-box">
        <div className="period-project-content">
          <div style={{ padding: 15, paddingTop: 0 }}>
            <div
              style={{
                justifyContent: "center",
                borderBottom: "1px solid gray",
                display: "flex",
                alignItems: "center",
                height: "50px",
              }}
            >
              <span style={{ fontSize: 18 }}>
                {" "}
                <FontAwesomeIcon icon={faRepeat} style={{ marginRight: 7 }} />
                Quản lý giai đoạn
              </span>
            </div>
            <div className="table_project" style={{ marginTop: 5 }}>
              <div style={{ marginBottom: 20 }}>
                {" "}
                <span style={{ fontSize: "18px", fontWeight: "500" }}>
                  {" "}
                  <FontAwesomeIcon icon={faCogs} size="1x" /> Thông tin dự án
                </span>
                {checkRole && (
                  <span>
                    <Tooltip title="Cập nhật thông tin dự án">
                      <FontAwesomeIcon
                        className="icon"
                        onClick={() => {
                          setShowUpdateFiledProject(true);
                        }}
                        style={{ marginRight: "15px", cursor: "pointer" }}
                        icon={faPencil}
                        size="1x"
                      />
                    </Tooltip>
                  </span>
                )}
                <div className="group-info">
                  <span
                    className="group-info-item"
                    style={{ marginTop: "10px", marginBottom: "15px" }}
                  >
                    Mã dự án: {dataProject != null ? dataProject.code : ""}
                  </span>
                  <span
                    className="group-info-item"
                    style={{ marginTop: "10px", marginBottom: "15px" }}
                  >
                    Tên dư án: {dataProject != null ? dataProject.name : ""}
                  </span>
                  <span
                    className="group-info-item"
                    style={{ marginTop: "10px", marginBottom: "15px" }}
                  >
                    Ngày bắt đầu/kết thúc:{" "}
                    {dataProject != null ? (
                      <span>
                        {convertDateLongToString(dataProject.startTime)} {" - "}
                        {convertDateLongToString(dataProject.endTime)}
                      </span>
                    ) : (
                      ""
                    )}
                  </span>
                  <span
                    className="group-info-item"
                    style={{ marginTop: "10px", marginBottom: "15px" }}
                  >
                    Thể loại:{" "}
                    {dataProject != null &&
                    dataProject.nameCategorys != null ? (
                      dataProject.nameCategorys
                    ) : (
                      <Tag icon={<ExclamationCircleOutlined />} color="warning">
                        Không có thể loại
                      </Tag>
                    )}
                  </span>
                  <span
                    className="group-info-item"
                    style={{ marginTop: "10px", marginBottom: "15px" }}
                  >
                    Nhóm dự án:{" "}
                    {dataProject != null &&
                    dataProject.nameGroupProject != null ? (
                      dataProject.nameGroupProject
                    ) : (
                      <Tag icon={<ExclamationCircleOutlined />} color="warning">
                        Không có nhóm dự án
                      </Tag>
                    )}
                  </span>
                </div>
              </div>
              <div className="title_my_project">
                <div style={{ float: "left" }}>
                  {" "}
                  <FontAwesomeIcon icon={faCogs} size="1x" />
                  <span style={{ fontSize: "18px", fontWeight: "500" }}>
                    {" "}
                    Danh sách giai đoạn
                  </span>
                </div>
                <div style={{ float: "right" }}>
                  <Button
                    style={{
                      color: "white",
                      backgroundColor: "rgb(55, 137, 220)",
                    }}
                    onClick={handlePeriodCreate}
                  >
                    <FontAwesomeIcon
                      icon={faPlus}
                      size="1x"
                      style={{
                        marginRight: "8px",
                        backgroundColor: "rgb(55, 137, 220)",
                      }}
                    />
                    Thêm giai đoạn
                  </Button>
                </div>
              </div>
              <br />
              <div
                style={{
                  marginTop: "15px",
                  minHeight: "170px",
                  height: "auto",
                }}
              >
                {data.length > 0 ? (
                  <>
                    {" "}
                    <Table
                      dataSource={data}
                      rowKey="id"
                      columns={columns}
                      pagination={false}
                    />
                    <div className="pagination_box">
                      <Pagination
                        simple
                        current={current}
                        onChange={(value) => {
                          setCurrent(value);
                        }}
                        total={total * 10}
                      />
                    </div>
                  </>
                ) : (
                  <Empty
                    imageStyle={{ height: "60px" }}
                    description={<span>Không có dữ liệu</span>}
                  />
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
      <ModalDetailPeriod
        visible={showDetailModal}
        onCancel={handleModalCancel}
        idPeriod={idPeriod}
      />
      <ModalCreatePeriod
        visible={showCreateModal}
        onCancel={handleModalCreateCancel}
      />
      <ModalUpdatePeriod
        visible={showUpdateModal}
        onCancel={handleModalUpdateCancel}
        idPeriod={idPeriod}
      />
      <ModalUpdateFiledProject
        visible={showUpdateFiledProject}
        onCancel={handleModalUpdateFiledProjectCancel}
        idProject={id}
      />
    </div>
  );
};

export default PeriodProject;
