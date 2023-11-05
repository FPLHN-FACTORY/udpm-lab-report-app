import {
  faPlus,
  faFilterCircleDollar,
  faChainSlash,
  faTableList,
  faClock,
  faTrash,
  faPencil,
  faFilter,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  Button,
  Input,
  Pagination,
  Table,
  Tooltip,
  Popconfirm,
  message,
} from "antd";
import "./style-meeting-period-management.css";
import React from "react";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useAppSelector, useAppDispatch } from "../../../../app/hook";
import {
  GetMeetingPeriodConfiguration,
  SetMeetingPeriodConfiguration,
  DeleteMeetingPeriodConfiguration,
} from "../../../../app/admin/AdMeetingPeriodConfiguration.reducer";
import { AdMeetingPeriodConfigurationAPI } from "../../../../api/admin/AdMeetingPeriodConfigurationAPI";
import ModalCreateMeetingPeriod from "./modal-create/ModalCreateMeetingPeriod";
import ModalUpdateMeetingPeriod from "./modal-update/ModalUpdateMeetingPeriod";
import { toast } from "react-toastify";
import LoadingIndicator from "../../../../helper/loading";

const MeetingPeriodConfiguration = () => {
  const [meetingPeriod, setMeetingPeriod] = useState(null);
  const [name, setName] = useState("");
  const { id } = useParams();
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchData();
    window.scrollTo(0, 0);
    document.title = "Quản lý ca học | Lab-Report-App";
  }, [current]);

  const fetchData = () => {
    setLoading(true);
    let filter = {
      name: name,
      page: current,
      size: 10,
    };
    AdMeetingPeriodConfigurationAPI.fetchAllMeetingPeriodConfiguration(
      filter
    ).then((response) => {
      dispatch(SetMeetingPeriodConfiguration(response.data.data.data));
      setTotal(response.data.data.totalPages);
      setLoading(false);
    });
  };

  const data = useAppSelector(GetMeetingPeriodConfiguration);

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => (current - 1) * 10 + index + 1,
    },
    {
      title: "Tên ",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      width: "20%",
    },
    {
      title: "Thời gian bắt đầu",
      dataIndex: "startTime",
      key: "startTime",
      render: (text, record) => {
        const startHour = record.startHour;
        const startMinute = record.startMinute;
        return <span>{`${startHour}:${startMinute}`}</span>;
      },
      sorter: (a, b) => {
        return (
          a.startHour.localeCompare(b.startHour) ||
          a.startMinute.localeCompare(b.startMinute)
        );
      },
      width: "25%",
    },
    {
      title: "Thời gian kết thúc",
      dataIndex: "endTime",
      key: "endTime",
      render: (text, record) => {
        const endHour = record.endHour;
        const endMinute = record.endMinute;
        return <span>{`${endHour}:${endMinute}`}</span>;
      },
      sorter: (a, b) => {
        return (
          a.endHour.localeCompare(b.endHour) ||
          a.endMinute.localeCompare(b.endMinute)
        );
      },
      width: "25%",
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div>
          <Tooltip title="Cập nhật">
            <FontAwesomeIcon
              onClick={() => {
                buttonUpdate(record);
              }}
              style={{
                marginRight: "15px",
                cursor: "pointer",
                color: "rgb(38, 144, 214)",
              }}
              icon={faPencil}
              size="1x"
            />
          </Tooltip>
          <Popconfirm
            title="Xóa Ca"
            description="Bạn có chắc chắn muốn xóa Ca này không?"
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
    setMeetingPeriod(null);
  };

  const buttonUpdate = (record) => {
    document.querySelector("body").style.overflowX = "hidden";
    setModalUpdate(true);
    setMeetingPeriod(record);
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
    AdMeetingPeriodConfigurationAPI.deleteMeetingPeriod(id).then(
      (response) => {
        message.success("Xóa thành công!");
        dispatch(DeleteMeetingPeriodConfiguration(response.data.data));
        fetchData();
      },
      (error) => {}
    );
  };
  return (
    <>
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon icon={faClock} style={{ fontSize: "20px" }} />
            <span style={{ marginLeft: "10px" }}>Quản lý ca học</span>
          </span>
        </div>
      </div>
      <div className="box-general" style={{ paddingTop: 10, marginTop: 0 }}>
        {loading && <LoadingIndicator />}

        <div className="filter-level" style={{ marginBottom: "10px" }}>
          <FontAwesomeIcon icon={faFilter} style={{ fontSize: "20px" }} />{" "}
          <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
          <hr />
          <div className="title__search" style={{ marginRight: "60px" }}>
            Tên Ca:{" "}
            <Input
              type="text"
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
              <FontAwesomeIcon
                icon={faFilterCircleDollar}
                style={{ marginRight: 5 }}
              />
              Tìm kiếm
            </Button>
            <Button
              className="btn__clear"
              onClick={clearData}
              style={{ backgroundColor: "rgb(50, 144, 202)" }}
            >
              <FontAwesomeIcon icon={faChainSlash} style={{ marginRight: 5 }} />
              Làm mới bộ lọc
            </Button>
          </div>
        </div>
        <div
          className="box-son-general"
          style={{ minHeight: "400px", marginTop: "30px" }}
        >
          <div className="tittle__category" style={{ marginBottom: "15px" }}>
            <div>
              <FontAwesomeIcon
                icon={faTableList}
                style={{
                  marginRight: "10px",
                  fontSize: "20px",
                }}
              />
              <span style={{ fontSize: "18px", fontWeight: "500" }}>
                {" "}
                Danh sách ca học
              </span>
            </div>

            <div>
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
                />{" "}
                Thêm Ca
              </Button>
            </div>
          </div>
          <div>
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
        </div>
        <ModalCreateMeetingPeriod
          visible={modalCreate}
          onCancel={buttonCreateCancel}
        />

        <ModalUpdateMeetingPeriod
          visible={modalUpdate}
          onCancel={buttonUpdateCancel}
          meetingPeriod={meetingPeriod}
        />
      </div>
    </>
  );
};

export default MeetingPeriodConfiguration;
