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
import "./styleModalCreateTeam.css";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
import LoadingIndicator from "../../../../../helper/loading";
import { CreateTeam } from "../../../../../app/teacher/teams/teamsSlice.reduce";
import { TeacherStudentClassesAPI } from "../../../../../api/teacher/student-class/TeacherStudentClasses.api";
import { TeacherTeamsAPI } from "../../../../../api/teacher/teams-class/TeacherTeams.api";
import {
  GetStudentClasses,
  SetStudentClasses,
} from "../../../../../app/teacher/student-class/studentClassesSlice.reduce";

const { Option } = Select;

const ModalCreateTeam = ({ visible, onCancel, idClass }) => {
  const [code, setCode] = useState("");
  const [errorCode, setErrorCode] = useState("");
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("");
  const [subjectName, setSubjectName] = useState("");
  const [errorMembers, setErrorMembers] = useState("");
  const [listStudentMulty, setListStudentMulty] = useState([]);
  const [listStudentClass, setListStudentClass] = useState([]);
  const [dataTable, setDataTable] = useState([]);
  const [loading, setLoading] = useState(false);
  const [listStudentsChange, setListStudentsChange] = useState([]);
  const [checkDataStudent, setCheckDataStudent] = useState(false);
  const [visitedCreate, setVisitedCreate] = useState(false);
  const dispatch = useAppDispatch();
  const cancelSuccess = () => {
    onCancel.handleCancelModalCreateSusscess();
  };
  const cancelFaild = () => {
    onCancel.handleCancelModalCreateFaild();
  };

  useEffect(() => {
    if (visible === true) {
      setVisitedCreate(true);
      setSubjectName("");
      setCode("");
      setName("");
      setListStudentMulty([]);
      setListStudentClass([]);
      setListStudentsChange([]);
      setDataTable([]);
      fetchData(idClass);
    }
  }, [visible]);

  useEffect(() => {
    if (visitedCreate === false) {
      fetchData(idClass);
    }
  }, [visitedCreate]);

  useEffect(() => {
    featchDataTable();
  }, [listStudentsChange]);

  useEffect(() => {
    if (checkDataStudent === true) {
      featchDataStudent();
    }
  }, [checkDataStudent]);

  const featchDataStudent = () => {
    const listNotFilter = dataStudentClasses.filter(
      (item) => item.idTeam == null || item.idTeam === "null"
    );
    setListStudentMulty(listNotFilter);
    setCheckDataStudent(false);
  };
  const fetchData = async (idClass) => {
    await featchStudentClass(idClass);
    await featInforStudent();
  };
  const featchStudentClass = async (id) => {
    setLoading(false);
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(id).then(
        (responese) => {
          const listNotTeams = responese.data.data.filter(
            (item) =>
              item.idTeam == null ||
              item.idTeam === "null" ||
              item.idTeam === "NULL"
          );
          setListStudentClass(listNotTeams);
          setVisitedCreate(false);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const featInforStudent = async () => {
    setLoading(false);
    try {
      const listNotFilter = dataStudentClasses.filter(
        (item) => item.idTeam == null || item.idTeam === "null"
      );
      setListStudentMulty(listNotFilter);
      setLoading(false);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  const featchDataTable = () => {
    const list = dataStudentClasses
      .filter((item1) => {
        return listStudentsChange.find((item2) => item1.idStudent === item2);
      })
      .map((item) => {
        return { ...item, role: `1` };
      });
    setDataTable(list);
  };

  const handleChangeStudents = (idStudent) => {
    setListStudentsChange(idStudent);
  };

  const handleRoleChange = (id, value) => {
    let updatedListInfo = dataTable.map((record) => {
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
    setDataTable(updatedListInfo);
  };

  const create = () => {
    let check = 0;
    // if (code.trim() === "") {
    //   setErrorCode("Mã nhóm không được để trống");
    //   check++;
    // } else {
    //   setErrorCode("");
    // }
    if (name.trim() === "") {
      setErrorName("Tên nhóm không được để trống");
      check++;
    } else {
      setErrorName("");
    }
    if (listStudentsChange.length <= 0) {
      setErrorMembers("Nhóm phải có ít nhất 1 thành viên");
      check++;
    } else {
      setErrorMembers("");
    }

    if (check === 0) {
      featchDataTable();
      let teamNew = {
        code: code,
        name: name,
        classId: idClass + ``,
        subjectName: subjectName,
        listStudentClasses: dataTable,
      };
      TeacherTeamsAPI.createTeam(teamNew).then(
        (respone) => {
          toast.success("Thêm thành công !");
          let data = respone.data.data;
          let dataAddTable = {
            ...data,
            stt: 1,
          };
          const mergedList = dataStudentClasses.map((item1) => {
            const matchedAddedItem = dataTable.find(
              (item2) => item2.idStudentClass === item1.idStudentClass
            );
            if (matchedAddedItem != null) {
              return {
                ...matchedAddedItem,
                codeTeam: teamNew.code,
                idTeam: data.id,
              };
            }
            return item1;
          });
          dispatch(SetStudentClasses(mergedList));
          dispatch(CreateTeam(dataAddTable));
          setCheckDataStudent(true);
          cancelSuccess();
        },
        (error) => {
          toast.error(error.response.data.message);
        }
      );
    }
  };
  const dataStudentClasses = useAppSelector(GetStudentClasses);
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
      width: "7%",
    },
    {
      title: "Họ và tên",
      dataIndex: "name",
      key: "name",
      width: 250,
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
      width: "25%",
      sorter: (a, b) => a.email.localeCompare(b.email),
      sortDirections: ["ascend", "descend"],
    },
    {
      title: "Vai trò",
      dataIndex: "role",
      key: "role",
      width: "23%",
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
  ];

  return (
    <>
      {loading && <LoadingIndicator />}
      <Modal
        open={visible}
        onCancel={cancelFaild}
        width={850}
        footer={null}
        bodyStyle={{ overflow: "hidden" }}
        style={{ top: "8px" }}
      >
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Thêm nhóm </span>
        </div>
        {listStudentClass.length <= 0 ? (
          <p style={{ color: "red", fontSize: "15px", marginLeft: "10px" }}>
            {" "}
            Không thể tạo nhóm vì tất cả thành viên trong lớp đã có nhóm
          </p>
        ) : (
          <>
            <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
              <Row gutter={16} style={{ marginBottom: "15px" }}>
                {/* <Col span={12}>
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
                </Col> */}
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
                <Col span={12}>
                  {" "}
                  <span>Chủ đề:</span> <br />
                  <Input
                    placeholder="Nhập chủ đề"
                    value={subjectName}
                    onChange={(e) => {
                      setSubjectName(e.target.value);
                    }}
                    type="text"
                  />
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
                    {listStudentMulty.map((member) => (
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
              {dataTable.length > 0 && (
                <>
                  <span>Thành viên:</span>
                  <Row>
                    {" "}
                    <Col span={24}>
                      <Table
                        pagination={false}
                        className="table-member-management"
                        columns={columns}
                        dataSource={dataTable}
                        rowKey="id"
                      />
                    </Col>{" "}
                  </Row>
                </>
              )}
            </div>
          </>
        )}
        <div
          style={{
            textAlign: "right",
            marginTop: "20px",
          }}
        >
          {listStudentClass.length >= 1 ? (
            <>
              <div style={{ paddingTop: "15px" }}>
                <Button
                  style={{
                    backgroundColor: "rgb(61, 139, 227)",
                    color: "white",
                  }}
                  onClick={create}
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
          ) : (
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
          )}
        </div>
      </Modal>
    </>
  );
};

export default ModalCreateTeam;
