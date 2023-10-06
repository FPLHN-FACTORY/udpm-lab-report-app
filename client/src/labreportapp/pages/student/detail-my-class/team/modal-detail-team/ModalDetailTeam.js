import React from "react";
import { Modal, Row, Col, Input, Button, Select, Table } from "antd";
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

  const loadDataDetailTeam = () => {
    StMyTeamClassAPI.detailTeam(id).then((respone) => {
      setDetailTeam(respone.data.data);
    });
  };

  const dataStudentClasses = useAppSelector(GetStStudentClasses);

  const loadDataMemberInTeam = () => {
    StMyTeamClassAPI.getStudentInMyTeam(idClass, id).then((response) => {
      let res = response.data.data;
      res.forEach((item) => {
        dataStudentClasses.forEach((st) => {
          if (item.studentId === st.id) {
            item.name = st.name;
          }
        });
      });
      console.log(res);
      setStudentMyTeam(res);
    });
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
      <Modal visible={visible} onCancel={onCancel} width={750} footer={null}>
        <div>
          <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
            <span style={{ fontSize: "18px" }}>Chi tiết nhóm</span>
          </div>
          <div style={{ borderBottom: "1px solid black" }}>
            <div className="info-team">
              <span className="info-heading">Thông tin nhóm:</span>
              <div className="group-info">
                <span
                  className="group-info-item"
                  style={{ marginTop: "10px", marginBottom: "15px" }}
                >
                  Mã nhóm: {detailTeam != null ? detailTeam.code : ""}
                </span>
                <span className="group-info-item">
                  Tên nhóm: {detailTeam != null ? detailTeam.name : ""}{" "}
                </span>
                <span
                  className="group-info-item"
                  style={{ marginTop: "13px", marginBottom: "15px" }}
                >
                  Tên đề tài: {detailTeam != null ? detailTeam.subjectName : ""}
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
              <Table
                columns={columns}
                dataSource={listStudentMyTeam}
                pagination={false}
                rowKey="id"
              />
            </>
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
export default ModalDetailTeam;
