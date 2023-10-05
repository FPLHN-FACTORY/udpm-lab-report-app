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
  Input,
  Pagination,
  Popconfirm,
  Select,
  Table,
  Tooltip,
} from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";
import { useAppSelector, useAppDispatch } from "../../../app/hook";
import {
  GetProject,
  SetProject,
} from "../../../app/reducer/detail-project/DPProjectSlice.reducer";
import { DetailProjectAPI } from "../../../api/detail-project/detailProject.api";
import { PeriodProjectAPI } from "../../../api/period-project/periodProject.api";
import {
  GetPeriodProject,
  SetPeriodProject,
  DeletePeriodProject,
} from "../../../app/reducer/member/period-project/periodProjectSlice.reducer";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import ModalDetailPeriod from "./modal-detail/ModalDetailPeriod";
import ModalUpdatePeriod from "./modal-update/ModalUpdatePeriod";
import ModalCreatePeriod from "./modal-create/ModalCreatePeriod";
import LoadingIndicator from "../../../helper/loading";
import HeaderDetailProject from "../../common/detail-project/HeaderDetailProject";

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
    fetchData();

    return () => {
      dispatch(SetPeriodProject([]));
      dispatch(SetProject([]));
    };
  }, []);

  const fetchData = () => {
    DetailProjectAPI.findProjectById(id).then((response) => {
      setDetailProject(response.data.data);
      dispatch(SetProject(response.data.data));
      document.title = "Quản lý giai đoạn | " + response.data.data.name;
    });
  };

  useEffect(() => {
    fetchDataPeriod();
    document.querySelector(".logo_project").src =
      "https://raw.githubusercontent.com/FPLHN-FACTORY/udpm-common-resources/main/fpoly-udpm/logo-udpm-2.png";

    return () => {
      document.querySelector(".logo_project").src =
        "https://raw.githubusercontent.com/FPLHN-FACTORY/udpm-common-resources/main/fpoly-udpm/logo-udpm-3.png";
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

  const data = useAppSelector(GetPeriodProject);

  const columns = [
    {
      title: "STT",
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
        const startTime = new Date(record.startTime);
        const endTime = new Date(record.endTime);

        const formattedStartTime = `${startTime.getDate()}/${
          startTime.getMonth() + 1
        }/${startTime.getFullYear()}`;
        const formattedEndTime = `${endTime.getDate()}/${
          endTime.getMonth() + 1
        }/${endTime.getFullYear()}`;

        return (
          <span>
            {formattedStartTime} - {formattedEndTime}
          </span>
        );
      },
      width: "15%",
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      sorter: (a, b) => a.status - b.status,
      render: (text) => {
        let statusText = "";
        if (text === 0) {
          statusText = "Đã diễn ra";
          return (
            <span
              className="box_span_status"
              style={{ backgroundColor: "rgb(45, 211, 86)" }}
            >
              {statusText}
            </span>
          );
        } else if (text === 1) {
          statusText = "Đang diễn ra";
          return (
            <span
              className="box_span_status"
              style={{ backgroundColor: "rgb(41, 157, 224)" }}
            >
              {statusText}
            </span>
          );
        } else if (text === 2) {
          statusText = "Chưa diễn ra";
          return (
            <span
              className="box_span_status"
              style={{ backgroundColor: "rgb(238, 162, 48)" }}
            >
              {statusText}
            </span>
          );
        }
      },
      width: "15%",
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
              size="1x"
            />
          </Tooltip>
          <Tooltip title="Cập nhật">
            <FontAwesomeIcon
              onClick={() => {
                handlePeriodUpdate(record.id);
              }}
              style={{ marginRight: "15px", cursor: "pointer" }}
              icon={faPencil}
              size="1x"
            />
          </Tooltip>
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
                style={{ cursor: "pointer" }}
                icon={faTrash}
                size="1x"
              />
            </Tooltip>
          </Popconfirm>
        </div>
      ),
    },
  ];

  const [showDetailModal, setShowDetailModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [showCreateModal, setShowCreateModal] = useState(false);

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
        toast.success("Xóa thành công!");
        dispatch(DeletePeriodProject(response.data.data));
      },
      (error) => {
        toast.error(error.response.data.message);
      }
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
                        marginRight: "8x",
                        backgroundColor: "rgb(55, 137, 220)",
                      }}
                    />{" "}
                    Thêm giai đoạn
                  </Button>
                </div>
              </div>
              <br />
              <div style={{ marginTop: "25px" }}>
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
    </div>
  );
};

export default PeriodProject;
