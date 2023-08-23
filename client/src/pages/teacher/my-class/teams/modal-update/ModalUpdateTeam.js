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
import { UpdateTeam } from "../../../../../app/teacher/teams/teamsSlice.reduce";
import { TeacherTeamsAPI } from "../../../../../api/teacher/teams-class/TeacherTeams.api";
import {
  GetStudentClasses,
  SetStudentClasses,
} from "../../../../../app/teacher/student-class/studentClassesSlice.reduce";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrashCan } from "@fortawesome/free-solid-svg-icons";

const { Option } = Select;

const ModalUpdateTeam = ({ visible, onCancel, idClass, team }) => {
  const [code, setCode] = useState("");
  const [errorCode, setErrorCode] = useState("");
  const [name, setName] = useState("");
  const [errorName, setErrorName] = useState("");
  const [subjectName, setSubjectName] = useState("");
  const [errorStudent, setErrorStudent] = useState("");
  const [listStudentNotJoin, setListStudentNotJoin] = useState([]);
  const [listStudentsChange, setListStudentsChange] = useState([]);
  const [listStudentDetail, setListStudentDetail] = useState([]);
  const [listStudentMulty, setListStudentMulty] = useState([]);
  const [listShowTable, setListShowTable] = useState([]);
  const [checkDataStudent, setCheckDataStudent] = useState(false);
  const [listDetelteTemp, setListDeleteTemp] = useState([]);
  const dispatch = useAppDispatch();

  const cancelSuccess = () => {
    setCheckDataStudent(false);
    onCancel.handleCancelModalCreateSusscess();
  };

  const cancelFaild = () => {
    setErrorCode("");
    setErrorName("");
    setErrorStudent("");
    const objFilter = dataStudentClasses.map((item2) => {
      const matchedItem = listDetelteTemp.find(
        (item3) => item3.idStudentClass === item2.idStudentClass
      );
      return { ...item2, ...matchedItem };
    });
    dispatch(SetStudentClasses(objFilter));
    featchDataStudent();
    onCancel.handleCancelModalCreateFaild();
  };

  useEffect(() => {
    if (visible === true) {
      setSubjectName(team.subjectName);
      setCode(team.code);
      setName(team.name);
      setListStudentsChange([]);
      setListStudentMulty([]);
      setListStudentDetail([]);
      setListDeleteTemp([]);
      setListShowTable([]);
      featchDataStudent();
    }
  }, [visible]);

  useEffect(() => {
    if (visible === true) {
      featchDataStudentChange();
    }
  }, [listStudentsChange]);

  useEffect(() => {
    if (checkDataStudent === true) {
      featchDataStudent();
    }
  }, [checkDataStudent]);

  const featchDataStudentChange = () => {
    const listMulty = listStudentNotJoin
      .filter((item1) =>
        listStudentsChange.find((item2) => item2 === item1.idStudentClass)
      )
      .map((obj) => {
        return {
          ...obj,
          role: `1`,
          codeTeam: team.code,
          idTeam: team.id,
        };
      });
    setListStudentMulty(listMulty);
    setListShowTable([...listMulty, ...listStudentDetail]);
  };

  const featchDataStudent = async () => {
    const listFilter = dataStudentClasses.filter(
      (item) => item.idTeam === team.id
    );
    const listNotFilter = dataStudentClasses.filter(
      (item) => item.idTeam == null || item.idTeam === "null"
    );
    setListStudentNotJoin(listNotFilter);
    setListStudentDetail(listFilter);
    setCheckDataStudent(false);
  };

  const handleDeleteStudentInTeam = (id) => {
    const objCheckDetail = listStudentDetail.filter(
      (item) => item.idStudentClass !== id
    );
    const objCheckMulty = listStudentMulty.filter(
      (item) => item.idStudentClass !== id
    );
    if (objCheckDetail.length > 0) {
      setListStudentDetail(objCheckDetail);
    }
    if (objCheckMulty.length > 0) {
      setListStudentMulty(objCheckMulty);
    }
    const obj = dataStudentClasses.filter((item) => item.idStudentClass === id);
    setListDeleteTemp([...listDetelteTemp, ...obj]);
    const objFilter = dataStudentClasses.map((item) => {
      if (item.idStudentClass === id) {
        setCheckDataStudent(true);
        return { ...item, idTeam: null, codeTeam: null, role: `1` };
      }
      return item;
    });
    dispatch(SetStudentClasses(objFilter));
    const listNew = listShowTable.filter((item) => item.idStudentClass !== id);
    setListShowTable(listNew);
  };

  const handleChangeStudents = (idStudent) => {
    setListStudentsChange(idStudent);
  };

  const handleRoleChange = (idStudentClass, value) => {
    setListShowTable([...listStudentMulty, ...listStudentDetail]);
    let updatedListInfo = listShowTable.map((item) => {
      if (item.idStudentClass === idStudentClass) {
        return {
          ...item,
          role: value,
          codeTeam: team.code,
          idTeam: team.id,
        };
      }
      return item;
    });
    setListShowTable(updatedListInfo);
  };

  const update = async () => {
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
    if (listShowTable.length <= 0) {
      setErrorStudent("Nhóm phải có ít nhất 1 thành viên");
      check++;
    } else {
      setErrorStudent("");
    }

    if (check === 0) {
      let teamUpdate = {
        id: team.id,
        code: code,
        name: name,
        subjectName: subjectName,
        listStudentClasses: listShowTable,
        listStudentClassesDeleteIdTeam: listDetelteTemp,
      };
      await TeacherTeamsAPI.updateTeam(teamUpdate).then(
        (respone) => {
          toast.success("Sửa thông tin nhóm thành công !");
          let data = respone.data.data;
          let dataTableTeam = {
            ...data,
            stt: 1,
          };
          const mergedList = dataStudentClasses.map((item1) => {
            const matchedAddedItem = listShowTable.find(
              (item2) => item2.idStudentClass === item1.idStudentClass
            );
            return {
              ...item1,
              ...matchedAddedItem,
            };
          });
          dispatch(SetStudentClasses(mergedList));
          dispatch(UpdateTeam(dataTableTeam));
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
      width: "12px",
      render: (text, record, index) => index + 1,
    },
    {
      title: "Họ và tên",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
      sortDirections: ["ascend", "descend"],
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
          defaultValue={`${text}`}
          onChange={(value) => handleRoleChange(record.idStudentClass, value)}
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
                  handleDeleteStudentInTeam(record.idStudentClass);
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
          <span style={{ fontSize: "18px" }}>Sửa thông tin nhóm </span>
        </div>
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
              <span>
                Thành viên:{" "}
                {listShowTable.length <= 0 ? (
                  <span
                    style={{
                      color: "black",
                      fontSize: "14px",
                    }}
                  >
                    {" "}
                    Chưa có thành viên nào tham gia nhóm
                  </span>
                ) : (
                  ""
                )}
              </span>
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
                  option.label.toLowerCase().indexOf(text.toLowerCase()) !== -1
                }
              >
                {listStudentNotJoin.map((member) => (
                  <Option
                    label={member.email}
                    value={member.idStudentClass}
                    key={member.idStudentClass}
                  >
                    <Tooltip title={member.email}>
                      <Space>
                        {member.name + " " + " (" + member.email + ")"}
                      </Space>
                    </Tooltip>
                  </Option>
                ))}{" "}
              </Select>
              <span
                className="error"
                style={{ display: "block", marginTop: "2px" }}
              >
                {errorStudent}
              </span>
            </div>
          </Row>
          {listShowTable.length > 0 ? (
            <>
              <Row>
                {" "}
                <Col span={24}>
                  <Table
                    pagination={false}
                    columns={columns}
                    dataSource={listShowTable}
                    rowKey="id"
                  />
                </Col>{" "}
              </Row>
            </>
          ) : (
            <>
              <span></span>
            </>
          )}
        </div>
        <div
          style={{
            textAlign: "right",
            marginTop: "20px",
          }}
        >
          <>
            <div style={{ paddingTop: "15px" }}>
              <Button
                style={{
                  backgroundColor: "rgb(61, 139, 227)",
                  color: "white",
                }}
                onClick={update}
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
        </div>
      </Modal>
    </>
  );
};

export default ModalUpdateTeam;