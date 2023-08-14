import { Modal, Row, Col, Input, Table, Select } from "antd";
import "./styleModalDetailProject.css";
import { useEffect, useState } from "react";
import { TeacherStudentClassesAPI } from "../../../../../api/teacher/student-class/TeacherStudentClasses.api";
import moment from "moment";
import LoadingIndicator from "../../../../../helper/loading";

const { Option } = Select;
const ModalDetailTeam = ({ visible, onCancel, team, idClass, click }) => {
  const [teamDetail, setTeamDetail] = useState({});
  const [listStudentClasses, setListStudentClasses] = useState([]);
  const [listShowTable, setListShowTable] = useState([]);
  const [loadingModalDetail, setLoadingModalDetail] = useState(true);
  useEffect(() => {
    if (visible === false && team === {} && idClass !== "") {
      return () => {
        setTeamDetail({});
        setListStudentClasses([]);
        setListShowTable([]);
      };
    }
  }, [visible]);

  useEffect(() => {
    if (visible === true && team !== {} && idClass !== "") {
      setTeamDetail(team);
      fetchDataDetail();
    } else {
      setTeamDetail({});
      setListStudentClasses([]);
      setListShowTable([]);
    }
  }, [visible]);

  useEffect(() => {
    if (listStudentClasses.length >= 0) {
      featInforStudentTable();
      setLoadingModalDetail(false);
    }
  }, [listStudentClasses]);

  const featchDataStudent = async () => {
    setLoadingModalDetail(true);
    let data = {
      idClass: idClass,
      idTeam: team.id,
    };
    try {
      await TeacherStudentClassesAPI.getStudentByIdClassAndIdTeam(data).then(
        (respone) => {
          setListStudentClasses(respone.data.data);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  const featInforStudentTable = async () => {
    try {
      let request = listStudentClasses.map((item) => item.idStudent).join("|");
      const listStudentAPI = await TeacherStudentClassesAPI.getAllInforStudent(
        `?id=` + request
      );
      const tempDataTable = listStudentAPI.data
        .filter((item1) =>
          listStudentClasses.some((item2) => item1.id === item2.idStudent)
        )
        .map((item1) => {
          const matchedObject = listStudentClasses.find(
            (item2) => item2.idStudent === item1.id
          );
          return {
            ...item1,
            ...matchedObject,
          };
        });
      setListShowTable(tempDataTable);
      setLoadingModalDetail(false);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const fetchDataDetail = async () => {
    await featchDataStudent();
    await featInforStudentTable();
    setLoadingModalDetail(false);
  };

  const columns = [
    {
      title: "STT",
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
  const rowTable = "row_table";
  return (
    <>
      {/* {!loadingModalDetail && <LoadingIndicator />} */}
      <Modal onCancel={onCancel} open={visible} width={750} footer={null}>
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Chi tiết nhóm</span>
        </div>
        <div style={{ marginTop: "15px" }}>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={8}>
              <span>Mã nhóm:</span> <br />
              <Input value={teamDetail.code} type="text" readOnly />
            </Col>
            <Col span={8}>
              <span>Tên nhóm:</span> <br />
              <Input type="text" value={teamDetail.name} readOnly />
            </Col>
            <Col span={8}>
              <span>Ngày tạo:</span> <br />
              <Input
                value={moment(teamDetail.createdDate).format("YYYY-MM-DD")}
                type="date"
                readOnly
              />
            </Col>
          </Row>

          <Row gutter={16}>
            <Col span={24}>
              <span>Thành viên:</span>
            </Col>
          </Row>
          <Table
            dataSource={listShowTable}
            rowKey="id"
            columns={columns}
            pagination={false}
            rowClassName={rowTable}
          />
        </div>
      </Modal>
    </>
  );
};

export default ModalDetailTeam;
