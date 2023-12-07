import {
  faPlus,
  faFilterCircleDollar,
  faChainSlash,
  faTableList,
  faTrash,
  faPencil,
  faFilter,
  faPeopleGroup,
  faFileDownload,
  faHistory,
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
  Empty,
} from "antd";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useAppSelector, useAppDispatch } from "../../../../app/hook";
import {
  SetRoleFactory,
  DeleteRoleFactory,
  GetRoleFactory,
} from "../../../../app/admin/AdRoleFactorySlice.reducer";
import { AdRoleFactoryAPI } from "../../../../api/admin/AdRoleFactoryAPI";
import ModalCreateRoleFactory from "./modal-create/ModalCreateRoleFactory";
import ModalUpdateRoleFactory from "./modal-update/ModalUpdateRoleFactory";
import LoadingIndicator from "../../../../helper/loading";
import "./style-role-factory-management.css";
import React from "react";
import ModalHistoryRoleFactory from "./modal-history-role-factory/ModalHistoryRoleFactory";

const RoleFactoryManagement = () => {
  const [roleFactory, setRoleFactory] = useState(null);
  const [name, setName] = useState("");
  const { id } = useParams();
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const dispatch = useAppDispatch();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchData();
    window.scrollTo(0, 0);
    document.title = "Quản lý vài trò trong xưởng | Lab-Report-App";
  }, [current]);

  const fetchData = () => {
    setLoading(true);
    let filter = {
      name: name,
      page: current,
      size: 10,
    };
    AdRoleFactoryAPI.fetchAllRoleFactory(filter).then((response) => {
      dispatch(SetRoleFactory(response.data.data.data));
      setTotal(response.data.data.totalPages);
      setLoading(false);
    });
  };

  const data = useAppSelector(GetRoleFactory);

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => (current - 1) * 10 + index + 1,
    },
    {
      title: "Tên",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      width: "20%",
    },
    {
      title: "Mô tả",
      dataIndex: "description",
      key: "description",
      sorter: (a, b) => a.descriptions.localeCompare(b.descriptions),
      width: "40%",
      render: (text, record) => {
        if (record.descriptions === "" || record.descriptions == null) {
          return <span>Chưa có</span>;
        } else {
          return <span>{record.descriptions}</span>;
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
    setRoleFactory(null);
  };

  const buttonUpdate = (record) => {
    document.querySelector("body").style.overflowX = "hidden";
    setModalUpdate(true);
    setRoleFactory(record);
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
    AdRoleFactoryAPI.deleteRoleFactory(id).then(
      (response) => {
        message.success("Xóa thành công !");
        dispatch(DeleteRoleFactory(response.data.data));
        fetchData();
      },
      (error) => {}
    );
  };
  const dowloadLog = () => {
    AdRoleFactoryAPI.dowloadLog().then(
      (response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const a = document.createElement("a");
        a.href = url;
        a.download = "vai_tro_trong_xuong.csv";
        a.click();
        window.URL.revokeObjectURL(url);
      },
      (error) => {}
    );
  };
  const [visibleHistory, setVisibleHistory] = useState(false);
  const openModalShowHistory = () => {
    setVisibleHistory(true);
  };

  const cancelModalHistory = () => {
    setVisibleHistory(false);
  };

  const changeTotalsPage = (newTotalPages) => {
    setTotal(newTotalPages);
  };
  return (
    <>
      {" "}
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            {" "}
            <FontAwesomeIcon
              icon={faPeopleGroup}
              style={{ fontSize: "20px" }}
            />
            <span style={{ marginLeft: "10px" }}>
              Quản lý vai trò trong xưởng
            </span>
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
            Tên Vai trò:{" "}
            <Input
              type="text"
              placeholder="Nhập tên vai trò"
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
                />{" "}
                Thêm
              </Button>
            </div>
          </div>
          <div>
            {data.length > 0 && (
              <>
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
              </>
            )}
            {data.length === 0 && (
              <>
                <p
                  style={{
                    textAlign: "center",
                    marginTop: "20px",
                    fontSize: "15px",
                    color: "red",
                  }}
                >
                  <Empty
                    imageStyle={{ height: 60 }}
                    description={<span>Không có dữ liệu</span>}
                  />{" "}
                </p>
              </>
            )}
          </div>
        </div>
        <ModalCreateRoleFactory
          visible={modalCreate}
          onCancel={buttonCreateCancel}
          changeTotalsPage={changeTotalsPage}
          totalPages={total}
          size={10}
        />
        <ModalUpdateRoleFactory
          visible={modalUpdate}
          onCancel={buttonUpdateCancel}
          roleFactory={roleFactory}
        />{" "}
        <ModalHistoryRoleFactory
          visible={visibleHistory}
          onCancel={cancelModalHistory}
        />
      </div>
    </>
  );
};

export default RoleFactoryManagement;
