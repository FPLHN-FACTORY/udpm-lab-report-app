import React from "react";
import {
  Modal,
  Row,
  Col,
  Input,
  Button,
  Select,
  Table,
  Spin,
  Empty,
  Tag,
} from "antd";
import "react-toastify/dist/ReactToastify.css";
import { StMyTeamClassAPI } from "../../../../../api/student/StTeamClass";
import { useState } from "react";
import { useEffect } from "react";
import { GetStStudentClasses } from "../../../../../app/student/StStudentClasses.reducer";
import { useAppSelector } from "../../../../../app/hook";

const { Option } = Select;

const ModalDetailTeam = ({ id, visible, onCancel, idClass }) => {
  const [detailTeam, setDetailTeam] = useState(null);
  const [listStudentMyTeam, setStudentMyTeam] = useState([]);
  const [loadingNo, setLoadingNo] = useState(false);
  useEffect(() => {
    if (id != null) {
      loadDataDetailTeam();
      loadDataMemberInTeam();
    }
    return () => {
      setDetailTeam(null);
      setStudentMyTeam([]);
    };
  }, [id]);

  const loadDataDetailTeam = async () => {
    try {
      await StMyTeamClassAPI.detailTeam(id).then((respone) => {
        setDetailTeam(respone.data.data);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const dataStudentClasses = useAppSelector(GetStStudentClasses);

  const loadDataMemberInTeam = async () => {
    setLoadingNo(true);
    try {
      StMyTeamClassAPI.getStudentInMyTeam(idClass, id).then((response) => {
        let res = response.data.data;
        res.forEach((item) => {
          dataStudentClasses.forEach((st) => {
            if (item.studentId === st.id) {
              item.name = st.name;
            }
          });
        });
        setStudentMyTeam(res);
        setLoadingNo(false);
      });
    } catch (error) {
      console.log(error);
      setLoadingNo(false);
    }
  };

  const columns = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => <>{index + 1}</>,
    },
    {
      title: "Họ và tên",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Vai trò",
      dataIndex: "role",
      key: "role",
      render: (text, record, index) => (
        <>{record.role === 0 ? "Trưởng nhóm" : "Thành viên"}</>
      ),
    },
  ];

  return (
    <>
      <Modal open={visible} onCancel={onCancel} width={750} footer={null}>
        <Spin spinning={loadingNo}>
          <div>
            <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
              <span style={{ fontSize: "18px" }}>Chi tiết nhóm</span>
            </div>
            <div
              style={{ borderBottom: "1px solid black", paddingBottom: "10px" }}
            >
              <div className="info-team">
                <span className="info-heading">Thông tin nhóm:</span>
                <div className="group-info">
                  <span className="group-info-item">
                    Tên nhóm: {detailTeam != null ? detailTeam.name : ""}{" "}
                  </span>
                  <span
                    className="group-info-item"
                    style={{ marginTop: "13px", marginBottom: "15px" }}
                  >
                    Tên đề tài:{" "}
                    {detailTeam != null
                      ? detailTeam.subjectName
                        ? detailTeam.subjectName
                        : "Chưa có chủ đề"
                      : "Chưa có chủ đề"}
                  </span>
                </div>
              </div>
              <>
                <div
                  className="table-member-team"
                  style={{ marginBottom: "7px" }}
                >
                  <span className="info-heading" style={{ fontSize: "16px" }}>
                    Danh sách thành viên trong nhóm:
                  </span>
                </div>
                {listStudentMyTeam.length > 0 ? (
                  <Table
                    columns={columns}
                    dataSource={listStudentMyTeam}
                    pagination={false}
                    rowKey="id"
                  />
                ) : (
                  <Empty
                    imageStyle={{ height: 60 }}
                    description={<span>Không có dữ liệu</span>}
                  />
                )}
              </>
            </div>
            <div style={{ textAlign: "right" }}>
              <div style={{ paddingTop: "15px" }}>
                <Button
                  className="btn_clean"
                  style={{
                    width: "80px",
                  }}
                  onClick={onCancel}
                >
                  Hủy
                </Button>
              </div>
            </div>
          </div>
        </Spin>
      </Modal>
    </>
  );
};
export default ModalDetailTeam;
