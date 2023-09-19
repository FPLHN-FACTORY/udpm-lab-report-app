import "./style-semester-management.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFilter,
  faHome,
  faLayerGroup,
  faPencil,
  faTrash,
  faPlus,
} from "@fortawesome/free-solid-svg-icons";
import { Button, Input, Pagination, Table, Tooltip, Popconfirm } from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useAppSelector, useAppDispatch } from "../../../app/hook";
import {
  SetSemester,
  GetSemester,
  DeleteSemester,
} from "../../../app/admin/AdSemester.reducer";
import { toast } from "react-toastify";
import { AdSemesterAPI } from "../../../api/admin/AdSemesterAPI";
import moment from "moment";
import React, { useCallback } from "react";
import ModalCreateSemester from "./modal-create/ModalCreateSemester";
import ModalUpdateSemester from "./modal-update/ModalUpdateSemester";
import LoadingIndicator from "../../../helper/loading";

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
      console.log(response.data.data.data);
    });
  };

  const data = useAppSelector(GetSemester);

  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => (current - 1) * 10 + index + 1,
    },
    {
      title: "Tên học kỳ",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      width: "30%",
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
              style={{ marginRight: "15px", cursor: "pointer" }}
              icon={faPencil}
              size="1x"
            />
          </Tooltip>
          <Popconfirm
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
        toast.success("Xóa thành công!");
        dispatch(DeleteSemester(response.data.data));
        fetchData();
      },
      (error) => {}
    );
  };
  
  return (
    <div className="semester">
      {loading && <LoadingIndicator />}
      <div className="title_activity_management">
        {" "}
        <FontAwesomeIcon icon={faLayerGroup} size="1x" />
        <span style={{ marginLeft: "10px" }}>Quản lý học kỳ</span>
      </div>
      <div className="filter-semester" style={{ marginBottom: "10px" }}>
        <FontAwesomeIcon icon={faFilter} size="2x" />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <div className="title__search">
          Tên học kỳ:{" "}
          <Input
            type="text"
            value={name}
            onChange={(e) => {
              setName(e.target.value);
            }}
            style={{ width: "300px", marginLeft: "5px" }}
          />
        </div>
        <div className="box_btn_filter">
          <Button className="btn_filter" onClick={buttonSearch}>
            Tìm kiếm
          </Button>
          <Button
            className="btn__clear"
            onClick={clearData}
            style={{ backgroundColor: "rgb(50, 144, 202)" }}
          >
            Làm mới bộ lọc
          </Button>
        </div>
      </div>

      <div
        className="table__category"
        style={{ marginTop: "25px", padding: "20px" }}
      >
        <div className="tittle__category">
          <div>
            {" "}
            {<FontAwesomeIcon icon={faLayerGroup} size="1x" />}
            <span style={{ fontSize: "18px", fontWeight: "500" }}>
              {" "}
              Danh sách học kỳ
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
                }}
              />{" "}
              Thêm học kỳ
            </Button>
          </div>
        </div>
        <br />
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

      <ModalCreateSemester
        visible={modalCreate}
        onCancel={buttonCreateCancel}
      />

      <ModalUpdateSemester
        visible={modalUpdate}
        onCancel={buttonUpdateCancel}
        semester={semester}
      />
    </div>
  );
};

export default SemesterManagement;