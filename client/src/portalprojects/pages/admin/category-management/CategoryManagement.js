import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFolder,
  faFilter,
  faPencil,
  faPlus,
  faTrashCan,
} from "@fortawesome/free-solid-svg-icons";
import "./styleCategory.css";
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
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import { useAppSelector, useAppDispatch } from "../../../app/hook";
import {
  SetCategory,
  GetCategory,
  DeleteCategory,
} from "../../../app/reducer/admin/category-management/adCategorySlice.reducer";
import { AdCategoryAPI } from "../../../api/admin-category/adCategory.api";
import "react-toastify/dist/ReactToastify.css";
import ModalCreateCategory from "./modal-create/ModalCreateCategory";
import ModalUpdateCategory from "./modal-update/ModalUpdateCategory";
import LoadingIndicator from "../../../helper/loading";

const CategoryManagement = () => {
  const [category, setCategory] = useState(null);
  const [name, setName] = useState("");
  const { id } = useParams();
  const [current, setCurrent] = useState(1);
  const [total, setTotal] = useState(0);
  const dispatch = useAppDispatch();
  const [start, setStart] = useState(0);
  const startIndex = (current - 1) * 10;
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchData();
  }, [current]);

  const fetchData = () => {
    setLoading(true);
    AdCategoryAPI.fetchAllCategory(name, current).then((response) => {
      dispatch(SetCategory(response.data.data.data));
      setTotal(response.data.data.totalPages);
      setLoading(false);
    });
  };

  const handleDelete = async (id) => {
    try {
      setLoading(true);
      await AdCategoryAPI.deleteCategoryId(id).then((response) => {
        dispatch(DeleteCategory(response.data.data));
        message.success("Xóa thể loại thành công");
        setLoading(false);
      });
    } catch (error) {
      setLoading(false);
    }
  };

  const [modalUpdate, setModalUpdate] = useState(false);
  const [modalCreate, setModalCreate] = useState(false);

  const buttonCreate = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setModalCreate(true);
  };

  const buttonCreateCancel = () => {
    document.querySelector("body").style.overflowX = "auto";
    setModalCreate(false);
    setCategory(null);
  };

  const buttonUpdate = (record) => {
    document.querySelector("body").style.overflowX = "hidden";
    setModalUpdate(true);
    setCategory(record);
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

  const data = useAppSelector(GetCategory);

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => startIndex + index + 1,
      align: "center",
    },
    {
      title: "Tên thể loại",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div>
          <Tooltip title="Cập nhật">
            <FontAwesomeIcon
              className="icon"
              onClick={() => {
                buttonUpdate(record);
              }}
              style={{ marginRight: "15px", cursor: "pointer" }}
              icon={faPencil}
              size="1x"
            />
          </Tooltip>
          <Tooltip title="Xóa">
            <Popconfirm
              title="Bạn có chắc chắn xóa thể loại ?"
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
            <FontAwesomeIcon icon={faFolder} style={{ fontSize: 20 }} />
            <span
              style={{ marginLeft: "10px", fontSize: 20, fontWeight: "500" }}
            >
              Quản lý thể loại
            </span>
          </span>
        </div>
      </div>
      <div className="category" style={{ paddingTop: 10, marginTop: 0 }}>
        {loading && <LoadingIndicator />}
        <div className="filter_category">
          <FontAwesomeIcon icon={faFilter} style={{ fontSize: 20 }} />{" "}
          <span style={{ fontSize: "20px", fontWeight: "500" }}>Bộ lọc</span>
          <hr />
          <div className="title__search">
            Tên thể loại:{" "}
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
              style={{
                backgroundColor: "rgb(38, 144, 214)",
                marginLeft: "10px",
              }}
            >
              Làm mới bộ lọc
            </Button>
          </div>
        </div>

        <div className="table__category">
          <div className="tittle__category">
            <div>
              {" "}
              {<FontAwesomeIcon icon={faFolder} style={{ fontSize: 20 }} />}
              <span style={{ fontSize: "18px", fontWeight: "500" }}>
                {" "}
                Danh sách thể loại
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
                    paddingRight: "5px",
                  }}
                />{" "}
                Thêm thể loại
              </Button>
            </div>
          </div>
          <br />
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
                    onChange={(value) => {
                      setCurrent(value);
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
        <ModalCreateCategory
          visible={modalCreate}
          onCancel={buttonCreateCancel}
        />
        <ModalUpdateCategory
          visible={modalUpdate}
          onCancel={buttonUpdateCancel}
          category={category}
        />
      </div>
    </>
  );
};

export default CategoryManagement;
