import "./style-semester-management.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFilter,
  faLayerGroup,
  faPencil,
  faTrash,
  faPlus,
  faFileEdit,
  faFilterCircleDollar,
  faChainSlash,
  faHistory,
  faFileDownload,
} from "@fortawesome/free-solid-svg-icons";
import {
  Button,
  Input,
  Pagination,
  Table,
  Tooltip,
  Popconfirm,
  Tag,
  message,
  Empty,
} from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useAppSelector, useAppDispatch } from "../../../app/hook";
import {
  SetSemester,
  GetSemester,
  DeleteSemester,
  UpdateStatusFeedback,
  UpdateCloseStatusFeedback,
} from "../../../app/admin/AdSemester.reducer";
import { AdSemesterAPI } from "../../../api/admin/AdSemesterAPI";
import React from "react";
import ModalCreateSemester from "./modal-create/ModalCreateSemester";
import ModalUpdateSemester from "./modal-update/ModalUpdateSemester";
import LoadingIndicator from "../../../helper/loading";
import { convertDateLongToString } from "../../../helper/util.helper";
import ModalShowHistory from "./modal-show-history/ModalShowHistory";

const SemesterManagement = () => {
  const [semester, setSemester] = useState(null);
  const [name, setName] = useState("");
  const { id } = useParams();
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchData();
    document.title = "Quản lý học kỳ | Lab-Report-App";
  }, [current]);

  const fetchData = () => {
    setLoading(true);
    let filter = {
      name: name,
      page: current,
      size: 10,
    };
    AdSemesterAPI.fetchAllSemester(filter).then((response) => {
      dispatch(SetSemester(response.data.data.data));
      setTotal(response.data.data.totalPages);
      setLoading(false);
    });
  };

  const updateStatusFeedback = (id) => {
    AdSemesterAPI.updateStatusFeedback(id).then((response) => {
      dispatch(UpdateStatusFeedback(id));
      message.success("Bật feedback thành công !");
    });
  };

  const updateCloseStatusFeedback = (id) => {
    AdSemesterAPI.updateCloseStatusFeedback(id).then((response) => {
      dispatch(UpdateCloseStatusFeedback(id));
      message.success("Tắt feedback thành công !");
    });
  };

  const changeTotalsPage = (newTotalPages) => {
    setTotal(newTotalPages);
  };

  const data = useAppSelector(GetSemester);
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => (current - 1) * 10 + index + 1,
    },
    {
      title: "Tên học kỳ",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Thời gian bắt đầu / kết thúc",
      dataIndex: "startTimeAndEndTime",
      key: "startTimeAndEndTime",
      render: (text, record) => {
        return (
          <span>
            {convertDateLongToString(record.startTime)} -{" "}
            {convertDateLongToString(record.endTime)}
          </span>
        );
      },
    },
    {
      title: "Thời gian của sinh viên",
      dataIndex: "startTimeStudentAndEndTimeStudent",
      key: "startTimeStudentAndEndTimeStudent",
      render: (text, record) => {
        return (
          <span>
            {convertDateLongToString(record.startTimeStudent)} -{" "}
            {convertDateLongToString(record.endTimeStudent)}
          </span>
        );
      },
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      render: (text, record) => {
        let currentTime = new Date().getTime();
        if (record.startTime <= currentTime && currentTime <= record.endTime) {
          return <Tag color="processing">Đang diễn ra</Tag>;
        } else if (currentTime < record.startTime) {
          return <Tag color="success">Chưa diễn ra</Tag>;
        } else {
          return <Tag color="error">Đã diễn ra</Tag>;
        }
      },
    },
    {
      title: "Trạng thái feedback",
      dataIndex: "statusFeedback",
      key: "statusFeedback",
      render: (text, record) => {
        if (record.statusFeedback === 0) {
          return <Tag color="processing">Đang tắt feedback</Tag>;
        } else {
          return <Tag color="success">Đang bật feedback</Tag>;
        }
      },
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div>
          {record.statusFeedback === 0 && (
            <Popconfirm
              placement="topLeft"
              title="Bật feedback"
              description="Bạn có chắc chắn muốn bật feedback không?"
              onConfirm={() => {
                updateStatusFeedback(record.id);
              }}
              okText="Có"
              cancelText="Không"
            >
              <Tooltip title="Bật feedback">
                <FontAwesomeIcon
                  className="icon"
                  onClick={() => {}}
                  style={{ marginRight: "19px", cursor: "pointer" }}
                  icon={faFileEdit}
                  size="1x"
                />
              </Tooltip>
            </Popconfirm>
          )}
          {record.statusFeedback === 1 && (
            <Popconfirm
              placement="topLeft"
              title="Bật feedback"
              description="Bạn có chắc chắn muốn tắt feedback không?"
              onConfirm={() => {
                updateCloseStatusFeedback(record.id);
              }}
              okText="Có"
              cancelText="Không"
            >
              <Tooltip title="Tắt feedback">
                <FontAwesomeIcon
                  className="icon"
                  onClick={() => {}}
                  style={{ marginRight: "19px", cursor: "pointer" }}
                  icon={faFileEdit}
                  size="1x"
                />
              </Tooltip>
            </Popconfirm>
          )}
          <Tooltip title="Cập nhật">
            <FontAwesomeIcon
              onClick={() => {
                buttonUpdate(record);
              }}
              style={{
                marginRight: "15px",
                cursor: "pointer",
                marginLeft: "3px",
                color: "rgb(38, 144, 214)",
              }}
              icon={faPencil}
              size="1x"
            />
          </Tooltip>
          <Popconfirm
            placement="topLeft"
            title="Xóa học kỳ"
            description="Bạn có chắc chắn muốn xóa học kỳ này không?"
            onConfirm={() => {
              buttonDelete(record.id);
            }}
            okText="Có"
            cancelText="Không"
          >
            <Tooltip title="Xóa">
              <FontAwesomeIcon
                style={{
                  cursor: "pointer",
                  marginLeft: "10px",
                  color: "rgb(38, 144, 214)",
                }}
                icon={faTrash}
                size="1x"
              />
            </Tooltip>
          </Popconfirm>
        </div>
      ),
      align: "center",
      width: "170px",
    },
  ];
  const [modalUpdate, setModalUpdate] = useState(false);
  const [modalCreate, setModalCreate] = useState(false);
  const buttonCreate = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setModalCreate(true);
  };
  const buttonCreateCancel = () => {
    document.querySelector("body").style.overflowX = "auto";
    setModalCreate(false);
    setSemester(null);
  };
  const buttonUpdate = (record) => {
    document.querySelector("body").style.overflowX = "hidden";
    setModalUpdate(true);
    setSemester(record);
  };
  const buttonUpdateCancel = () => {
    document.querySelector("body").style.overflowX = "auto";
    setModalUpdate(false);
  };
  const buttonSearch = () => {
    fetchData();
    setCurrent(1);
  };
  const clearData = () => {
    setName("");
  };
  const buttonDelete = (id) => {
    AdSemesterAPI.deleteSemester(id).then(
      (response) => {
        message.success("Xóa thành công !");
        dispatch(DeleteSemester(response.data.data));
        setCurrent(1);
        fetchData();
      },
      (error) => {}
    );
  };

  const dowloadLog = () => {
    setLoading(true);
    AdSemesterAPI.dowloadLog().then(
      (response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const a = document.createElement("a");
        a.href = url;
        a.download = "hoc_ky.csv";
        a.click();
        window.URL.revokeObjectURL(url);
        setLoading(false);
      },
      (error) => {
        console.log(error);
      }
    );
  };

  const [visibleHistory, setVisibleHistory] = useState(false);

  const openModalShowHistory = () => {
    setVisibleHistory(true);
  };

  const cancelModalHistory = () => {
    setVisibleHistory(false);
  };

  return (
    <>
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon icon={faLayerGroup} size="1x" />
            <span style={{ marginLeft: "10px" }}>Quản lý học kỳ</span>
          </span>
        </div>
      </div>
      <div className="semester" style={{ paddingTop: 30 }}>
        {loading && <LoadingIndicator />}
        <div
          className="filter-semester"
          style={{ marginBottom: "10px", marginTop: 0, padding: 15 }}
        >
          <FontAwesomeIcon
            icon={faFilter}
            style={{ fontSize: "20px", marginRight: "7px" }}
          />
          <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
          <hr />
          <div className="title__search">
            Tên học kỳ:
            <Input
              type="text"
              placeholder="Nhập tên học kỳ"
              value={name}
              onChange={(e) => {
                setName(e.target.value);
              }}
              style={{ width: "300px", marginLeft: "5px" }}
            />
          </div>
          <div className="box_btn_filter" style={{ paddingBottom: 10 }}>
            <Button
              className="btn_filter"
              onClick={buttonSearch}
              style={{ marginRight: "15px" }}
            >
              {" "}
              <FontAwesomeIcon
                icon={faFilterCircleDollar}
                style={{ marginRight: "5px" }}
              />
              Tìm kiếm
            </Button>
            <Button
              className="btn__clear"
              onClick={clearData}
              style={{ backgroundColor: "rgb(50, 144, 202)" }}
            >
              <FontAwesomeIcon
                icon={faChainSlash}
                style={{ marginRight: "5px" }}
              />
              Làm mới bộ lọc
            </Button>
          </div>
        </div>
        <div
          className="table__category_custom"
          style={{ marginTop: "30px", padding: "20px" }}
        >
          <div className="tittle__category">
            <div>
              {
                <FontAwesomeIcon
                  icon={faLayerGroup}
                  style={{ marginRight: "7px", fontSize: "20px" }}
                />
              }
              <span style={{ fontSize: "18px", fontWeight: "500" }}>
                Danh sách học kỳ
              </span>
            </div>
            <div>
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: 5,
                }}
                onClick={dowloadLog}
              >
                <FontAwesomeIcon
                  icon={faFileDownload}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "5px",
                  }}
                />
                Dowload log
              </Button>
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: 5,
                }}
                onClick={openModalShowHistory}
              >
                <FontAwesomeIcon
                  icon={faHistory}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "5px",
                  }}
                />
                Lịch sử
              </Button>
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                }}
                onClick={buttonCreate}
              >
                <FontAwesomeIcon
                  icon={faPlus}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "5px",
                  }}
                />
                Thêm học kỳ
              </Button>
            </div>
          </div>
          <br />
          {data.length > 0 && (
            <div>
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
                  onChange={(page) => {
                    setCurrent(page);
                  }}
                  total={total * 10}
                />
              </div>
            </div>
          )}
          {data.length === 0 && (
            <>
              <p
                style={{
                  textAlign: "center",
                  marginTop: "100px",
                  fontSize: "15px",
                  color: "red",
                }}
              >
                <Empty
                  style={{ paddingTop: "100px" }}
                  imageStyle={{ height: 60 }}
                  description={<span>Không có dữ liệu</span>}
                />{" "}
              </p>
            </>
          )}
        </div>
        <ModalCreateSemester
          visible={modalCreate}
          onCancel={buttonCreateCancel}
          changeTotalsPage={changeTotalsPage}
          totalPages={total}
          size={10}
        />
        <ModalUpdateSemester
          visible={modalUpdate}
          onCancel={buttonUpdateCancel}
          semester={semester}
        />
        <ModalShowHistory
          visible={visibleHistory}
          onCancel={cancelModalHistory}
        />
      </div>
    </>
  );
};

export default SemesterManagement;
