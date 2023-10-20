import {
  faPlus,
  faFilterCircleDollar,
  faChainSlash,
  faTableList,
  faClock,
  faTrash,
  faPencil,
  faFilter,
  faTeletype,
  faPersonMilitaryPointing,
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
  Tag,
} from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useAppSelector, useAppDispatch } from "../../../app/hook";
import {
  SetRoleProject,
  DeleteRoleProject,
  GetRoleProject,
} from "../../../app/admin/AdRoleProjectSlice.reducer";
import { AdRoleProjectAPI } from "../../../api/admin/AdRoleProjectAPI";
import ModalCreateRoleProject from "./modal-create/ModalCreateRoleProject";
import ModalUpdateRoleProject from "./modal-update/ModalUpdateRoleProject";
import { toast } from "react-toastify";
import LoadingIndicator from "../../../helper/loading";
import moment from "moment";
import "./style-role-management.css";
import React from "react";

const RoleManagement = () => {
  const [roleProject, setRoleProject] = useState(null);
  const [name, setName] = useState("");
  const { id } = useParams();
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchData();
    window.scrollTo(0, 0);
    document.title = "Quản lý vai trò | Lab-Report-App";
  }, [current]);

  const fetchData = () => {
    setLoading(true);
    let filter = {
      name: name,
      page: current,
      size: 10,
    };
    AdRoleProjectAPI.fetchAllRoleProject(filter).then((response) => {
      dispatch(SetRoleProject(response.data.data.data));
      setTotal(response.data.data.totalPages);
      setLoading(false);
    });
  };

  const data = useAppSelector(GetRoleProject);

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
      title: "Mô tả",
      dataIndex: "description",
      key: "description",
      sorter: (a, b) => a.description.localeCompare(b.description),
      width: "40%",
      render: (text, record) => {
        if (record.description === "" || record.description == null) {
          return <span>Chưa có</span>;
        } else {
          return <span>{record.description}</span>;
        }
      },
    },
    {
      title: "Trạng thái",
      dataIndex: "status",
      key: "status",
      render: (text, record) => {
        if (record.roleDefault === 0) {
          return <Tag color="success">Mặc định</Tag>;
        } else {
          return <Tag color="error">Không mặc định</Tag>;
        }
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
            placement="topLeft"
            title="Xóa vai trò"
            description="Bạn có chắc chắn muốn xóa vai trò này không?"
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
    setRoleProject(null);
  };

  const buttonUpdate = (record) => {
    document.querySelector("body").style.overflowX = "hidden";
    setModalUpdate(true);
    setRoleProject(record);
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
    AdRoleProjectAPI.deleteRoleProject(id).then(
      (response) => {
        message.success("Xóa thành công!");
        dispatch(DeleteRoleProject(response.data.data));
        fetchData();
      },
      (error) => {}
    );
  };

  return (
    <div className="box-general" style={{ paddingTop: 50 }}>
      {loading && <LoadingIndicator />}
      <div className="title_activity_management" style={{ marginTop: 0 }}>
        {" "}
        <FontAwesomeIcon
          icon={faPersonMilitaryPointing}
          style={{ fontSize: "20px" }}
        />
        <span style={{ marginLeft: "10px" }}>Quản lý vai trò trong dự án</span>
      </div>
      <div className="filter-level">
        <FontAwesomeIcon icon={faFilter} style={{ fontSize: "20px" }} />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <div className="title__search" style={{ marginRight: "60px" }}>
          Tên vai trò:{" "}
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
        style={{ minHeight: "400px", marginTop: "30px", padding: 20 }}
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
              Danh sách vai trò
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
              Thêm vai trò
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
      <ModalCreateRoleProject
        visible={modalCreate}
        onCancel={buttonCreateCancel}
      />
      <ModalUpdateRoleProject
        visible={modalUpdate}
        onCancel={buttonUpdateCancel}
        roleProject={roleProject}
      />
    </div>
  );
};

export default RoleManagement;
