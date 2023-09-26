import {
  faFilter,
  faLevelUp,
  faLevelUpAlt,
  faPlus,
  faTrash,
  faPencil,
  faFilterCircleDollar,
  faChainSlash,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Button, Input, Pagination, Table, Tooltip, Popconfirm } from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import "./style-level-management.css";
import { useAppSelector, useAppDispatch } from "../../../app/hook";
import { AdLevelAPI } from "../../../api/admin/AdLevelManagerAPI";
import React from "react";
import { toast } from "react-toastify";
import {
  SetLevel,
  GetLevel,
  DeleteLevel,
} from "../../../app/admin/AdLevelManager.reducer";
import ModalCreateLevel from "./modal-create/ModalCreateLevel";
import ModalUpdateLevel from "./modal-update/ModalUpdateLevel";
import LoadingIndicator from "../../../helper/loading";
const LevelManagement = () => {
  const [level, setLevel] = useState(null);
  const [name, setName] = useState("");
  const { id } = useParams();
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchData();
    document.title = "Quản lý level | Lab-Report-App";
  }, [current]);

  const fetchData = () => {
    setLoading(true);
    let filter = {
      name: name,
      page: current,
      size: 10,
    };
    AdLevelAPI.fetchAllLevel(filter).then((response) => {
      dispatch(SetLevel(response.data.data.data));
      setTotal(response.data.data.totalPages);
      setLoading(false);
      console.log(response.data.data.data);
    });
  };

  const data = useAppSelector(GetLevel);

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => (current - 1) * 10 + index + 1,
    },
    {
      title: "Tên Level",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      width: "30%",
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
            title="Xóa Level"
            description="Bạn có chắc chắn muốn xóa Level này không?"
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
    setLevel(null);
  };

  const buttonUpdate = (record) => {
    document.querySelector("body").style.overflowX = "hidden";
    setModalUpdate(true);
    setLevel(record);
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
    AdLevelAPI.deleteLevel(id).then(
      (response) => {
        toast.success("Xóa thành công!");
        dispatch(DeleteLevel(response.data.data));
        fetchData();
      },
      (error) => {}
    );
  };

  return (
    <div className="box-general">
      {loading && <LoadingIndicator />}

      <div className="heading-box" style={{ marginTop: "0" }}>
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon icon={faLevelUp} style={{ marginRight: "8px" }} />{" "}
          Quản lý level
        </span>
      </div>
      <div className="filter-level" style={{ marginBottom: "10px" }}>
        <FontAwesomeIcon icon={faFilter} style={{ fontSize: "20px" }} />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <div className="title__search" style={{ marginRight: "60px" }}>
          Tên level:{" "}
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
          <Button
            className="btn_filter"
            onClick={buttonSearch}
            style={{ marginRight: "20px" }}
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
        <div className="tittle__category" style={{ marginBottom: "20px" }}>
          <div>
            {" "}
            {
              <FontAwesomeIcon
                icon={faLevelUpAlt}
                style={{ fontSize: "20px" }}
              />
            }
            <span style={{ fontSize: "18px", fontWeight: "500" }}>
              {" "}
              Danh sách level
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
              Thêm level
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
      <ModalCreateLevel visible={modalCreate} onCancel={buttonCreateCancel} />

      <ModalUpdateLevel
        visible={modalUpdate}
        onCancel={buttonUpdateCancel}
        level={level}
      />
    </div>
  );
};

export default LevelManagement;
