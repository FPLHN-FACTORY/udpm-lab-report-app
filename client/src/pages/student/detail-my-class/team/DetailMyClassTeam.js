import { useParams } from "react-router-dom";
import "./style-detail-my-class-team.css";
import { Link } from "react-router-dom";
import { ControlOutlined } from "@ant-design/icons";
import { useAppDispatch } from "../../../../app/hook";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import { StMyTeamClassAPI } from "../../../../api/student/StTeamClass";
import { faUserPlus } from "@fortawesome/free-solid-svg-icons";
import { Table, Button, Tooltip } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { sinhVienCurrent } from "../../../../helper/inForUser";

const DetailMyClassTeam = () => {
  const dispatch = useAppDispatch();
  const { id } = useParams();
  const [listTeamClassAll, setlistTeamClassAll] = useState([]); //getAll
  const [listTeamAll, setlistTeamAll] = useState([]); //getAllPerson
  const [studentDataAll, setStudentDataAll] = useState([]); // student data
  const [studentNotJoinDataAll, setStudentNotJoinDataAll] = useState([]); // student not join data
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    featchAllMyTeamClass();
  }, []);

  const featchAllMyTeamClass = async () => {
    setLoading(false);
    let filter = {
      idClass: id,
      idStudent: sinhVienCurrent.id,
    };

    try {
      StMyTeamClassAPI.getAllTeamMyClass(filter).then((respone) => {
        setlistTeamClassAll(respone.data.data);
        setLoading(true);
      });
    } catch (error) {
      alert("Vui lòng F5 lại trang !");
    }
  };

  useEffect(() => {
    featchAllTeamStNotJoin();
  }, []);
  const featchAllTeamStNotJoin = async () => {
    setLoading(false);
    let filter = {
      idClass: id,
      idStudent: sinhVienCurrent.id,
    };

    try {
      StMyTeamClassAPI.getAllTeamByStNotJoin(filter).then((respone) => {
        setStudentNotJoinDataAll(respone.data.data);
        console.log(respone.data.data);
        setLoading(true);
      });
    } catch (error) {
      alert("Vui lòng F5 lại trang !");
    }
  };

  useEffect(() => {
    setLoading(false);

    if (
      listTeamAll.length === 0 &&
      listTeamClassAll.length > 0 &&
      listTeamClassAll.length < 2
    ) {
      let filter = {
        idClass: id,
        idTeam: listTeamClassAll[0].id,
      };

      try {
        StMyTeamClassAPI.getAllMyTeam(filter).then((respone) => {
          setlistTeamAll(respone.data.data);
        });
        setLoading(true);
      } catch (error) {
        alert("Vui lòng F5 lại trang !");
      }
    }
  }, [listTeamAll, listTeamClassAll]);

  useEffect(() => {
    const fetchStudentData = async () => {
      const responseStudentData = await StMyTeamClassAPI.fetchAllStudent();
      const studentData = responseStudentData.data;
      setStudentDataAll(studentData);
    };
    fetchStudentData();
  }, []);
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
      width: "12px",
    },
    {
      title: "Mã",
      dataIndex: "studentId",
      key: "studentId",
      sorter: (a, b) => a.studentId.localeCompare(b.studentId),
      render: (text, record) => {
        const student = studentDataAll.find(
          (item) => item.id === record.studentId
        );
        return student ? student.username : "";
      },
    },

    {
      title: "Tên",
      dataIndex: "studentId",
      key: "studentId",
      sorter: (a, b) => a.studentId.localeCompare(b.studentId),
      render: (text, record) => {
        const student = studentDataAll.find(
          (item) => item.id === record.studentId
        );
        return student ? student.name : "";
      },
    },

    {
      title: "Vai trò",
      dataIndex: "role",
      key: "role",
      sorter: (a, b) => a.role.localeCompare(b.role),
      render: (text, record, index) => (
        <span style={{ color: "black" }}>
          {text == 1 ? "Thành viên" : "Trưởng nhóm"}
        </span>
      ),
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
      sorter: (a, b) => a.email.localeCompare(b.email),
    },
  ];

  const columnsTeamStNotJoin = [
    {
      title: "STT",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => index + 1,
      width: "12px",
    },
    {
      title: "Mã Nhóm",
      dataIndex: "code",
      key: "code",
      sorter: (a, b) => a.code.localeCompare(b.code),
    },
    {
      title: "Tên Nhóm",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
      title: "Đề tài",
      dataIndex: "subjectName",
      key: "subjectName",
      sorter: (a, b) => a.subjectName.localeCompare(b.subjectName),
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <>
          <div style={{ width: "105px" }}>
            <Tooltip title="Tham gia nhóm">
              <FontAwesomeIcon
                icon={faUserPlus}
                className="icon"
                onClick={() => {
                  // setShowDetailModal(true);
                  // handleDetailTeam(record);
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
    <div style={{ paddingTop: "35px" }}>
      <div className="title-student-my-class">
        {loading && <LoadingIndicator />}
        <span style={{ paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span
            style={{ fontSize: "18px", marginLeft: "10px", fontWeight: "500" }}
          >
            Bảng điều khiển
          </span>
          <span style={{ color: "gray" }}> - Lớp của tôi</span>
        </span>
      </div>
      <div className="box-students-detail-my-class" style={{ padding: "20px" }}>
        <div className="button-menu-student-detail-my-class">
          <div>
            <Link
              to={`/student/my-class/team/${id}`}
              id="menu-checked"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              DANH SÁCH NHÓM
            </Link>
            <Link
              className="custom-link"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              DANH SÁCH BUỔI HỌC
            </Link>
            <Link
              className="custom-link"
              style={{
                fontSize: "16px",
                paddingLeft: "10px",
                paddingRight: "10px",
                fontWeight: "bold",
              }}
            >
              ĐIỂM DANH
            </Link>
            <Link
              className="custom-link"
              style={{
                fontSize: "16px",
                fontWeight: "bold",
                paddingLeft: "10px",
                paddingRight: "10px",
              }}
            >
              ĐIỂM
            </Link>
            <hr />
            {listTeamClassAll.length > 0 && listTeamClassAll.length < 2 ? (
              <div className="info-team">
                <span className="info-heading">Thông tin nhóm:</span>
                <div className="group-info">
                  <span
                    className="group-info-item"
                    style={{ marginTop: "10px", marginBottom: "15px" }}
                  >
                    Mã nhóm:{" "}
                    {listTeamClassAll.length > 0 &&
                      listTeamClassAll.length < 2 &&
                      listTeamClassAll[0].code}
                  </span>
                  <span className="group-info-item">
                    Tên nhóm:{" "}
                    {listTeamClassAll.length > 0 &&
                      listTeamClassAll.length < 2 &&
                      listTeamClassAll[0].name}
                  </span>
                  <span
                    className="group-info-item"
                    style={{ marginTop: "13px", marginBottom: "15px" }}
                  >
                    Tên đề tài:{" "}
                    {listTeamClassAll.length > 0 &&
                      listTeamClassAll.length < 2 &&
                      listTeamClassAll[0].subjectName}
                  </span>
                </div>
              </div>
            ) : (
              ""
            )}
            {listTeamClassAll.length > 0 && listTeamClassAll.length < 2 ? (
              <>
                <div className="table-member-team">
                  <span className="info-heading" style={{ fontSize: "16px" }}>
                    Danh sách thành viên trong nhóm:
                  </span>
                </div>
                <div style={{ marginTop: "8px", paddingBottom: "40px" }}>
                  <div>
                    {listTeamAll.length > 0 ? (
                      <>
                        <div className="table">
                          <Table
                            style={{ marginTop: "150px" }}
                            dataSource={listTeamAll}
                            rowKey="id"
                            columns={columns}
                            pagination={false}
                          />{" "}
                        </div>
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
                          Không có nhóm nào trong lớp
                        </p>
                      </>
                    )}
                  </div>
                  <Button className="btnRoiNhom">Rời nhóm</Button>
                </div>
              </>
            ) : (
              <>
                <div className="table-member-team">
                  {/* <span className="info-team-not-join" style={{ fontSize: "16px" }}>
                Danh sách nhóm:
              </span> */}
                </div>

                <div className="table-not-join">
                  <Table
                    // style={{ marginTop: '250px' }}
                    dataSource={studentNotJoinDataAll}
                    rowKey="stt"
                    columns={columnsTeamStNotJoin}
                    pagination={{
                      pageSize: 5,
                      showSizeChanger: false,
                      size: "small",
                    }}
                  />
                </div>
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default DetailMyClassTeam;
