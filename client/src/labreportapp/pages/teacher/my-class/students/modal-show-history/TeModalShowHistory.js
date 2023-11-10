import React, { useEffect, useState } from "react";
import { Modal, Button, Select, Table, Pagination, Empty } from "antd";
import { TeacherMyClassAPI } from "../../../../../api/teacher/my-class/TeacherMyClass.api";

const { Option } = Select;

const TeModalShowHistory = ({ visible, onCancel, classDetail }) => {
  const columns = [
    {
      title: "Email người thực hiện",
      dataIndex: "mail",
      key: "mail",
      sorter: (a, b) => a.mail.localeCompare(b.mail),
    },
    {
      title: "Thời gian thực hiện",
      dataIndex: "createDate",
      key: "createDate",
      sorter: (a, b) => a.createDate.localeCompare(b.createDate),
    },
    {
      title: "Vai trò",
      dataIndex: "author",
      key: "author",
      sorter: (a, b) => a.author.localeCompare(b.author),
    },
    {
      title: "Nội dung",
      dataIndex: "content",
      key: "content",
    },
  ];

  const [dataHistory, setDataHistory] = useState([]);
  const [current, setCurrent] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [size, setSize] = useState("50");

  const loadDataHistory = () => {
    TeacherMyClassAPI.showHistory({
      idClass: classDetail.id,
      page: current - 1,
      size: parseInt(size),
    }).then((response) => {
      setDataHistory(response.data.data);
      setTotalPages(response.data.totalPages);
    });
  };

  useEffect(() => {
    if (visible) {
      loadDataHistory();
    }
  }, [current, size, visible]);

  return (
    <>
      <Modal visible={visible} onCancel={onCancel} width={1300} footer={null}>
        <div>
          <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
            <span style={{ fontSize: "18px" }}>Lịch sử</span>
          </div>
          <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
            {dataHistory != null ? (
              <>
                {" "}
                <Table
                  dataSource={dataHistory}
                  rowKey="id"
                  columns={columns}
                  pagination={false}
                />
                <div
                  className="pagination_box"
                  style={{
                    display: "flex",
                    marginTop: 20,
                    marginBottom: 20,
                    alignItems: "center",
                    justifyContent: "center",
                  }}
                >
                  <Pagination
                    style={{ marginRight: "10px" }}
                    simple
                    current={current}
                    onChange={(value) => {
                      setCurrent(value);
                    }}
                    total={totalPages * 10}
                  />
                  <Select
                    style={{ width: "100px", marginLeft: "10px" }}
                    value={size}
                    onChange={(e) => {
                      setSize(e);
                    }}
                  >
                    <Option value="50">50</Option>
                    <Option value="100">100</Option>
                    <Option value="250">250</Option>
                    <Option value="500">500</Option>
                    <Option value="1000">1000</Option>
                    <Option value="1000">10000</Option>
                  </Select>
                </div>{" "}
              </>
            ) : (
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
                    imageStyle={{ height: 60 }}
                    description={<span>Không có dữ liệu</span>}
                  />{" "}
                </p>
              </>
            )}
          </div>
          <div style={{ textAlign: "right" }}>
            <div style={{ paddingTop: "15px" }}>
              <Button
                style={{
                  backgroundColor: "red",
                  color: "white",
                }}
                onClick={onCancel}
              >
                Hủy
              </Button>
            </div>
          </div>
        </div>
      </Modal>
    </>
  );
};
export default TeModalShowHistory;
