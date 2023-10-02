import { Modal, Row, Col, Input, Table, Select, Empty } from "antd";
import "./styleModalDetailTeam.css";
import { useEffect, useState } from "react";
import { useAppSelector } from "../../../../../app/hook";
import moment from "moment";
import { GetStudentClasses } from "../../../../../app/teacher/student-class/studentClassesSlice.reduce";

const { Option } = Select;
const ModalDetailTeam = ({ visible, onCancel, team, idClass }) => {
  const [teamDetail, setTeamDetail] = useState({});
  const [listShowTable, setListShowTable] = useState([]);
  const dataStudentClasses = useAppSelector(GetStudentClasses);

  const featchDataStudent = () => {
    const listFilter = dataStudentClasses
      .filter((item) => item.idTeam === team.id)
      .sort((a, b) => {
        if (a.role === b.role) {
          return "0";
        } else if (a.role === "0") {
          return "-1";
        } else {
          return "1";
        }
      });
    setListShowTable(listFilter);
  };

  useEffect(() => {
    if (visible === true && idClass !== "") {
      setTeamDetail(team);
      featchDataStudent();
    } else {
      setTeamDetail({});
      setListShowTable([]);
    }
  }, [visible]);

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
      width: "10px",
    },
    {
      title: "Họ và tên",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      render: (text, record) => {
        return (
          <div>
            <span style={{ paddingLeft: "8px" }}>{record.name}</span>
          </div>
        );
      },
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      sorter: (a, b) => a.email.localeCompare(b.email),
      sortDirections: ["ascend", "descend"],
    },
    {
      title: "Vai trò",
      dataIndex: "role",
      key: "role",
      width: "100px",
      sorter: (a, b) => a.role.localeCompare(b.role),
      sortDirections: ["ascend", "descend"],
      render: (text, record) => (
        <Select style={{ width: "100%" }} value={text}>
          <Option value="0">Trưởng nhóm</Option>
          <Option value="1">Thành viên</Option>
        </Select>
      ),
    },
  ];
  return (
    <>
      <Modal
        onCancel={onCancel}
        open={visible}
        width={850}
        footer={null}
        style={{ top: "53px" }}
      >
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Chi tiết nhóm</span>
        </div>
        <div style={{ marginTop: "15px" }}>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={12}>
              <span>Tên nhóm:</span> <br />
              <Input type="text" value={teamDetail.name} readOnly />
            </Col>
            <Col span={12}>
              <span>Ngày tạo:</span> <br />
              <Input
                value={moment(teamDetail.createdDate).format("YYYY-MM-DD")}
                type="date"
                readOnly
              />
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Chủ đề:</span> <br />
              {teamDetail.subjectName !== null &&
              teamDetail.subjectName !== "" ? (
                <Input value={teamDetail.subjectName} type="text" readOnly />
              ) : (
                <Input value="Chưa có chủ đề" type="text" readOnly />
              )}
            </Col>
          </Row>
          <Row gutter={16}>
            <Col span={24}>
              <span>Thành viên:</span>
            </Col>
          </Row>
          {listShowTable.length > 0 ? (
            <div className="table-teacher">
              <Table
                dataSource={listShowTable}
                rowKey="id"
                columns={columns}
                pagination={false}
              />
            </div>
          ) : (
            <Empty
              imageStyle={{ height: 60 }}
              description={<span>Không có sinh viên nào trong nhóm</span>}
            />
          )}
        </div>
      </Modal>
    </>
  );
};

export default ModalDetailTeam;
