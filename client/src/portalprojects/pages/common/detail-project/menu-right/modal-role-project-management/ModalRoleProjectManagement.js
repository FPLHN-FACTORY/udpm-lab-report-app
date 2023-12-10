import {
  Modal,
  Input,
  Row,
  Table,
  Tag,
  Tooltip,
  Popconfirm,
  Button,
  Empty,
  message,
} from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faImage,
  faPencil,
  faPlus,
  faTrash,
  faUserTag,
} from "@fortawesome/free-solid-svg-icons";
import { DetailProjectAPI } from "../../../../../api/detail-project/detailProject.api";
import { GetProject } from "../../../../../app/reducer/detail-project/DPProjectSlice.reducer";
import { useAppSelector } from "../../../../../app/hook";
import {
  DeleteMeRoleProject,
  GetMeRoleProject,
  SetMeRoleProject,
} from "../../../../../app/reducer/detail-project/DPRoleProjectSlice.reducer";
import { useEffect, useState } from "react";
import ModalCreateRoleProject from "./modal-create-role-project/ModalCreateRoleProject";
import { useAppDispatch } from "../../../../../../labreportapp/app/hook";
import ModalUpdateRoleProject from "./modal-update-role-project/ModalUpdateRoleProject";

const ModalRoleProjectManagement = ({ visible, onCancel }) => {
  const dispatch = useAppDispatch();

  const data = useAppSelector(GetMeRoleProject);

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
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
      dataIndex: "roleDefault",
      key: "roleDefault",
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
              style={{
                marginRight: "15px",
                cursor: "pointer",
                color: "rgb(38, 144, 214)",
              }}
              onClick={() => {
                openModalUpdate(record);
              }}
              icon={faPencil}
              size="1x"
            />
          </Tooltip>
          <Popconfirm
            placement="topLeft"
            title="Xóa vai trò"
            description="Bạn có chắc chắn muốn xóa vai trò này không?"
            okText="Có"
            onConfirm={() => {
              deleteRoleProject(record.id);
            }}
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

  const deleteRoleProject = (id) => {
    DetailProjectAPI.deleteRoleProject(id).then(
      (response) => {
        dispatch(DeleteMeRoleProject(id));
        message.success("Xóa thành công !");
      },
      (error) => {}
    );
  };

  const [visibleModalCreate, setVisibleModalCreate] = useState(false);

  const openModalCreate = () => {
    setVisibleModalCreate(true);
  };

  const onCancelModalCreate = () => {
    setVisibleModalCreate(false);
  };

  const [visibleModalUpdate, setVisibleModalUpdate] = useState(false);
  const [itemSelected, setItemSelected] = useState(null);

  const openModalUpdate = (item) => {
    setItemSelected(item);
    setVisibleModalUpdate(true);
  };

  const onCancelModalUpdate = () => {
    setItemSelected(null);
    setVisibleModalUpdate(false);
  };
  return (
    <>
      <Modal visible={visible} onCancel={onCancel} width={1000} footer={null}>
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>
            <FontAwesomeIcon icon={faUserTag} style={{ marginRight: "7px" }} />
            Quản lý vai trò trong dự án
          </span>
        </div>
        <div style={{ marginTop: "15px" }}>
          <div style={{ display: "flex", justifyContent: "flex-end" }}>
            <Button
              style={{
                backgroundColor: "rgb(55, 137, 220)",
                color: "white",
              }}
              onClick={openModalCreate}
            >
              <FontAwesomeIcon icon={faPlus} style={{ marginRight: 5 }} />
              Thêm vai trò
            </Button>
          </div>

          <div style={{ width: "100%", marginTop: 15 }}>
            {data.length > 0 && (
              <Table
                dataSource={data}
                rowKey="id"
                columns={columns}
                pagination={true}
              />
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
                    imageStyle={{ height: 60 }}
                    description={<span>Không có dữ liệu</span>}
                  />{" "}
                </p>
              </>
            )}
          </div>
        </div>
        <ModalCreateRoleProject
          visible={visibleModalCreate}
          onCancel={onCancelModalCreate}
        />
        <ModalUpdateRoleProject
          visible={visibleModalUpdate}
          onCancel={onCancelModalUpdate}
          item={itemSelected}
        />
      </Modal>
    </>
  );
};

export default ModalRoleProjectManagement;
