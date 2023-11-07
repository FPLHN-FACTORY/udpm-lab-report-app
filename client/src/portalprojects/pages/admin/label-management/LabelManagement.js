import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./styleLabelManagement.css";
import {
  faFilter,
  faList,
  faEdit,
  faPlus,
  faTags,
  faTrashCan,
} from "@fortawesome/free-solid-svg-icons";
import {
  Button,
  Empty,
  Input,
  Pagination,
  Popconfirm,
  Table,
  Tooltip,
  message,
} from "antd";
import { useAppDispatch } from "../../../app/hook";
import LoadingIndicator from "../../../helper/loading";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useAppSelector } from "../../../app/hook";
import {
  DeleteLabel,
  GetLabelManagement,
  SetLabelManagement,
} from "../../../app/reducer/admin/label-management/labelManagementSlice.reducer";
import { LabelManagementAPI } from "../../../api/label-management/labelManagement.api";
import ModalCreateLabel from "./modal-create/ModalCreateLabel";
import ModalUpdateLabel from "./modal-update/ModalUpdateLabel";

const LabelManagement = () => {
  const dispatch = useAppDispatch();
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  const [idLabel, setIdLabel] = useState("");
  const { id } = useParams();
  const [label, setLabel] = useState(null);
  const [searchName, setSearchName] = useState("");
  const [showCreateModal, setShowCreateModal] = useState(false);
  const [showUpdateModal, setShowUpdateModal] = useState(false);

  useEffect(() => {
    fetchData();
    return () => {
      dispatch(SetLabelManagement([]));
    };
  }, [current]);

  const fetchData = async () => {
    let filter = {
      name: searchName,
      page: current - 1,
    };
    setIsLoading(true);
    try {
      const response = await LabelManagementAPI.fetchAll(filter);
      let listLabelManagement = response.data.data.data;
      dispatch(SetLabelManagement(listLabelManagement));
      setTotal(response.data.data.totalPages);
      setIsLoading(false);
    } catch (error) {
      console.log(error);
    }
  };
  const handleDelete = async (id) => {
    try {
      setIsLoading(true);
      await LabelManagementAPI.deleteLabelById(id).then((response) => {
        dispatch(DeleteLabel(response.data.data));
        message.success("Xóa nhãn thành công");
        setIsLoading(false);
      });
    } catch (error) {
      setIsLoading(false);
    }
  };

  const handleChangeSearch = (e) => {
    setSearchName(e.target.value);
  };

  const handleLabelCreate = () => {
    document.querySelector("body").style.overflowX = "auto";
    setShowCreateModal(true);
    setIdLabel(id);
  };

  const handleModalCreateCancel = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreateModal(false);
  };

  const handleUpdateLabel = (id, label) => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowUpdateModal(true);
    setIdLabel(id);
    setLabel(label);
  };

  const handleModalUpdateCancel = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowUpdateModal(false);
  };

  const handleSearch = () => {
    setCurrent(1);
    fetchData();
  };

  const handleClear = () => {
    setSearchName("");
  };

  const data = useAppSelector(GetLabelManagement);

  const columns = [
    {
      title: "#",
      dataIndex: "index",
      key: "index",
      render: (text, record, index) => index + 1,
      align: "center",
    },
    {
      title: "Tên nhãn",
      dataIndex: "name",
      align: "center",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Màu sắc nhãn",
      dataIndex: "colorLabel",
      align: "center",
      key: "colorLabel",
      render: (text, record) => {
        if (record.colorLabel) {
          return (
            <div style={{ display: "flex", justifyContent: "center" }}>
              <div
                style={{
                  backgroundColor: record.colorLabel,
                  width: 200,
                  height: 30,
                  borderRadius: 8,
                }}
              ></div>
            </div>
          );
        }
      },
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      align: "center",
      key: "actions",
      render: (text, record) => (
        <div>
          <Tooltip title="Chỉnh sửa chi tiết">
            <FontAwesomeIcon
              className="icon"
              icon={faEdit}
              size="1x"
              onClick={() => {
                handleUpdateLabel(record.id, record);
              }}
            />
          </Tooltip>
          <Tooltip title="Xóa">
            <Popconfirm
              title="Bạn có chắc chắn xóa nhãn ?"
              description={<div>{record.name}</div>}
              onConfirm={(e) => handleDelete(record.id)}
              okText="Yes"
              cancelText="No"
            >
              {" "}
              <FontAwesomeIcon className="icon" icon={faTrashCan} />
            </Popconfirm>
          </Tooltip>
        </div>
      ),
    },
  ];

  return (
    <>
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon icon={faTags} style={{ fontSize: 20 }} />
            <span style={{ marginLeft: "10px" }}>Quản lý nhãn</span>
          </span>
        </div>
      </div>
      <div
        className="label_management"
        style={{ paddingTop: 10, marginTop: 0 }}
      >
        {isLoading && <LoadingIndicator />}
        <div className="filter-label">
          <FontAwesomeIcon icon={faFilter} style={{ fontSize: 20 }} />{" "}
          <span style={{ fontSize: "20px", fontWeight: "500" }}>Bộ lọc</span>
          <hr />
          <div className="content">
            <div className="content-wrapper">
              <div className="content-center">
                Tên nhãn:{" "}
                <Input
                  type="text"
                  value={searchName}
                  onChange={handleChangeSearch}
                  style={{ width: "50%", marginLeft: "10px" }}
                />
              </div>
            </div>
          </div>
          <div className="box_btn_filter">
            <Button className="btn_filter" onClick={handleSearch}>
              Tìm kiếm
            </Button>
            <Button
              className="btn_clear"
              onClick={handleClear}
              style={{
                backgroundColor: "rgb(38, 144, 214)",
                marginLeft: "10px",
              }}
            >
              Làm mới bộ lọc
            </Button>
          </div>
        </div>
        <div className="table_label" style={{ minHeight: "250px" }}>
          <div className="title_label_management_table">
            <div>
              {" "}
              {<FontAwesomeIcon icon={faList} style={{ fontSize: 20 }} />}
              <span style={{ fontSize: "18px", fontWeight: "500" }}>
                {" "}
                Danh sách nhãn
              </span>
            </div>
            <div>
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                }}
                onClick={handleLabelCreate}
              >
                <FontAwesomeIcon
                  icon={faPlus}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    paddingRight: "5px",
                  }}
                />{" "}
                Thêm nhãn
              </Button>
            </div>
          </div>
          {data.length > 0 ? (
            <div style={{ marginTop: "10px" }}>
              <Table
                className="table_content"
                pagination={false}
                columns={columns}
                rowKey="id"
                dataSource={data}
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
          ) : (
            <Empty
              imageStyle={{ height: "60px" }}
              description={<span>Không có dữ liệu</span>}
            />
          )}
        </div>
        <ModalCreateLabel
          visible={showCreateModal}
          onCancel={handleModalCreateCancel}
        />
        <ModalUpdateLabel
          visible={showUpdateModal}
          onCancel={handleModalUpdateCancel}
          idLabel={idLabel}
          label={label}
        />
      </div>
    </>
  );
};

export default LabelManagement;
