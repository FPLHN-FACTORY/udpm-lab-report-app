import React from "react";
import {
  Modal,
  Row,
  Col,
  Input,
  Button,
  Select,
  Space,
  Tooltip,
  Table,
} from "antd";
import "./styleModalUpdateTeam.css";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
import LoadingIndicator from "../../../../../helper/loading";
import { CreateTeam } from "../../../../../app/teacher/teams/teamsSlice.reduce";
import { TeacherStudentClassesAPI } from "../../../../../api/teacher/student-class/TeacherStudentClasses.api";
import { TeacherTeamsAPI } from "../../../../../api/teacher/teams-class/TeacherTeams.api";
import {
  SetTeams,
  GetTeams,
} from "../../../../../app/teacher/teams/teamsSlice.reduce";
import { GetStudentClasses } from "../../../../../app/teacher/student-class/studentClassesSlice.reduce";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrashCan } from "@fortawesome/free-solid-svg-icons";

const { Option } = Select;

const ModalUpdateTeam = ({ visible, onCancel, idClass, team }) => {
  const [code, setCode] = useState("");
  const [errorCode, setErrorCode] = useState("");
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("");
  const [subjectName, setSubjectName] = useState("");
  const [errorSubjectName, setErrorSubjectName] = useState("");
  const [errorMembers, setErrorMembers] = useState("");
  const [listStudentDetail, setListStudentDetail] = useState([]);
  const [listStudentNotJoin, setListStudentNotJoin] = useState([]);
  const [loading, setLoading] = useState(false);
  const [listStudentsChange, setListStudentsChange] = useState([]);

  const [listFilterAdd, setListFilterAdd] = useState([]);
  const dispatch = useAppDispatch();

  const cancelSuccess = () => {
    onCancel.handleCancelModalCreateSusscess();
  };
  const cancelFaild = () => {
    onCancel.handleCancelModalCreateFaild();
  };

  useEffect(() => {
    if (visible === true) {
      setSubjectName(team.subjectName);
      setCode(team.code);
      setName(team.name);
      setListStudentsChange([]);
      setListFilterAdd([]);
      featchDataStudent();
      // fetchData(idClass);
    }
  }, [visible]);
  const dataStudentClasses = useAppSelector(GetStudentClasses);

  useEffect(() => {
    if (visible === true) {
      featchDataStudentChange();
    }
  }, [listStudentsChange]);

  const featchDataStudentChange = () => {
    let listFilter = listStudentNotJoin.filter((item1) =>
      listStudentsChange.some((item2) => item1.id === item2)
    );

    let listFilterDuplicate = listStudentDetail.filter((item1) =>
      listFilter.map((item2) => item1.id === item2.id)
    );
    console.log("dup");
    console.log(listFilterDuplicate);
    if (listFilterDuplicate.length >= 0) {
      let a = listFilter.filter((item1) =>
        listFilterDuplicate.find((item2) => item1.id !== item2.id)
      );
      setListFilterAdd(a);
    } else {
      setListFilterAdd(listFilter);
    }
    console.log("ấksakasks");
    console.log(listFilterAdd);
    // if (listFilterDuplicate.length > 0) {
    //   listFilter = listFilter.filter((item1) =>
    //     listFilterDuplicate.some((item2) => item1.id !== item2)
    //   );
    //  }
    const listJoin = [...listFilterAdd, ...listStudentDetail];
    // console.log(
    //   "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
    // );
    console.log(listJoin);
    // // console.log(listJoin);
    setListStudentDetail(listJoin);
  };

  const featchDataStudent = async () => {
    const listFilter = dataStudentClasses.filter(
      (item) => item.idTeam === team.id
    );
    const listNotFilter = dataStudentClasses.filter(
      (item) => item.idTeam !== team.id
    );
    setListStudentNotJoin(listNotFilter);
    setListStudentDetail(listFilter);
  };

  const handleChangeStudents = (idStudent) => {
    setListStudentsChange(idStudent);
    // const uniqueSelectedItems = listStudentsChange.filter(
    //   (id, index, self) => index === self.indexOf(id)
    // );
    // setListStudentsChange(uniqueSelectedItems);
  };

  const handleRoleChange = (id, value) => {
    let updatedListInfo = listStudentDetail.map((record) => {
      if (record.idStudent === id) {
        let newData = {
          idStudentClass: record.idStudentClass,
          classId: idClass,
          role: value,
        };
        return { ...record, ...newData };
      }
      return record;
    });
    setListStudentDetail(updatedListInfo);
  };

  const handleDeleteStudentInTeam = (id) => {
    const listNew = listStudentDetail.filter((item) => item.idStudent !== id);
    setListStudentDetail(listNew);
  };

  const create = () => {
    let check = 0;
    if (code.trim() === "") {
      setErrorCode("Mã nhóm không được để trống");
      check++;
    } else {
      setErrorCode("");
    }
    if (name.trim() === "") {
      setErrorName("Tên nhóm không được để trống");
      check++;
    } else {
      setErrorName("");
    }
    if (subjectName.trim() === "") {
      setErrorSubjectName("Chủ đề không được để trống");
      check++;
    } else {
      setErrorSubjectName("");
    }

    if (listStudentsChange.length <= 0) {
      setErrorMembers("Nhóm phải có ít nhất 1 thành viên");
      check++;
    } else {
      setErrorMembers("");
    }

    if (check === 0) {
      // featchDataTable();
      let teamNew = {
        code: code,
        name: name,
        classId: idClass + ``,
        subjectName: subjectName,
        listStudentClasses: listStudentDetail,
      };
      TeacherTeamsAPI.createTeam(teamNew).then(
        (respone) => {
          toast.success("Thêm thành công !");
          let data = respone.data.data;
          let dataAddTable = {
            ...data,
            stt: 1,
          };
          dispatch(CreateTeam(dataAddTable));
          cancelSuccess();
        },
        (error) => {
          toast.error(error.response.data.message);
        }
      );
    }
  };

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      width: "12px",
      render: (text, record, index) => index + 1,
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

      sorter: (a, b) => a.role.localeCompare(b.role),
      sortDirections: ["ascend", "descend"],
      render: (text, record) => (
        <Select
          style={{ width: "100%" }}
          value={record.role}
          onChange={(value) => handleRoleChange(record.id, value)}
        >
          <Option value="0">Trưởng nhóm</Option>
          <Option value="1">Thành viên</Option>
        </Select>
      ),
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <>
          <div style={{ textAlign: "center" }}>
            <Tooltip title="Xóa thành viên">
              <FontAwesomeIcon
                className="icon"
                icon={faTrashCan}
                onClick={() => {
                  handleDeleteStudentInTeam(record.id);
                }}
              />
            </Tooltip>
          </div>
        </>
      ),
      width: "105px",
    },
  ];

  return (
    <>
      {loading && <LoadingIndicator />}
      <Modal
        open={visible}
        onCancel={cancelFaild}
        width={750}
        footer={null}
        bodyStyle={{ overflow: "hidden" }}
        style={{ top: "8px" }}
      >
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Sửa thông tin nhóm </span>
        </div>
        {/* {listStudent.length <= 0 ? (
          <p style={{ color: "red", fontSize: "15px", marginLeft: "10px" }}>
            {" "}
            Không thể tạo nhóm vì tất cả thành viên trong lớp đã có nhóm
          </p>
        ) : ( */}
        <>
          <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
            <Row gutter={16} style={{ marginBottom: "15px" }}>
              <Col span={12}>
                {" "}
                <span className="notBlank">*</span>
                <span>Mã nhóm:</span> <br />
                <Input
                  placeholder="Nhập mã"
                  value={code}
                  onChange={(e) => {
                    setCode(e.target.value);
                  }}
                  type="text"
                />
                <span className="error">{errorCode}</span>
              </Col>
              <Col span={12}>
                {" "}
                <span className="notBlank">*</span>
                <span>Tên nhóm:</span> <br />
                <Input
                  placeholder="Nhập tên"
                  value={name}
                  onChange={(e) => {
                    setName(e.target.value);
                  }}
                  type="text"
                />
                <span className="error">{errorName}</span>
              </Col>
            </Row>
            <Row gutter={16} style={{ marginBottom: "15px" }}>
              <Col span={24}>
                {" "}
                <span className="notBlank">*</span>
                <span>Chủ đề:</span> <br />
                <Input
                  placeholder="Nhập chủ đề"
                  value={subjectName}
                  onChange={(e) => {
                    setSubjectName(e.target.value);
                  }}
                  type="text"
                />
                <span className="error">{errorSubjectName}</span>
              </Col>
            </Row>
            <Row style={{ marginBottom: "15px" }}>
              <div style={{ width: "100%" }}>
                {" "}
                <span className="notBlank">*</span>
                <span>Thành viên:</span>
                <Select
                  mode="multiple"
                  placeholder="Thêm thành viên"
                  style={{
                    width: "100%",
                    height: "auto",
                  }}
                  value={listStudentsChange}
                  onChange={handleChangeStudents}
                  optionLabelProp="label"
                  defaultValue={[]}
                  filterOption={(text, option) =>
                    option.label.toLowerCase().indexOf(text.toLowerCase()) !==
                    -1
                  }
                >
                  {listStudentNotJoin.map((member) => (
                    <Option
                      label={member.email}
                      value={member.id}
                      key={member.idStudent}
                    >
                      <Tooltip title={member.email}>
                        <Space>
                          {member.name + " " + " (" + member.email + ")"}
                          {}
                        </Space>
                      </Tooltip>
                    </Option>
                  ))}{" "}
                </Select>
                <span
                  className="error"
                  style={{ display: "block", marginTop: "2px" }}
                >
                  {errorMembers}
                </span>
              </div>
            </Row>
            {/* {dataTable.length > 0 && ( */}
            <Row>
              {" "}
              <Col span={24}>
                <Table
                  pagination={false}
                  className="table-member-management"
                  columns={columns}
                  dataSource={listStudentDetail}
                  rowKey="id"
                />
              </Col>{" "}
            </Row>
            {/* )} */}
          </div>
        </>
        {/* )} */}
        <div
          style={{
            textAlign: "right",
            marginTop: "20px",
          }}
        >
          {/* {listStudent.length >= 1 ? ( */}
          <>
            <div style={{ paddingTop: "15px" }}>
              <Button
                style={{
                  backgroundColor: "rgb(61, 139, 227)",
                  color: "white",
                }}
                onClick={create}
              >
                Sửa
              </Button>
              <Button
                style={{
                  backgroundColor: "red",
                  color: "white",
                }}
                onClick={cancelFaild}
              >
                Hủy
              </Button>
            </div>
          </>
          {/* ) : (
            <>
              <div style={{ paddingTop: "15px" }}>
                <Button
                  style={{
                    backgroundColor: "gray",
                    color: "white",
                  }}
                >
                  Thêm
                </Button>
                <Button
                  style={{
                    backgroundColor: "red",
                    color: "white",
                  }}
                  onClick={cancelFaild}
                >
                  Hủy
                </Button>
              </div>
            </>
          )} */}
        </div>
      </Modal>
    </>
  );
};

export default ModalUpdateTeam;
