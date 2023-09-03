import { useEffect } from "react";
import { useState } from "react";
import { useParams } from "react-router";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import LoadingIndicator from "../../../../helper/loading";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome } from "@fortawesome/free-solid-svg-icons";

const TeacherPostMyClass = () => {
  const { idClass } = useParams();
  const [classDetail, setClassDetail] = useState({});
  const [loading, setLoading] = useState(false);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - bài viết";
    featchClass(idClass);
  }, []);

  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassDetail(responese.data.data);
        setLoading(true);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="box-one">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <FontAwesomeIcon
              icon={faHome}
              style={{ color: "#00000", fontSize: "23px" }}
            />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>{" "}
            <span style={{ color: "gray", fontSize: "14px" }}>
              {" "}
              - Thành viên
            </span>
          </span>
        </Link>
      </div>
      <div className="box-two-student-in-my-class">
        <div className="box-two-student-in-my-class-son">
          <div className="button-menu">
            <div>
              <Link
                to={`/teacher/my-class/post/${idClass}`}
                id="menu-checked"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                BÀI VIẾT &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/students/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                THÀNH VIÊN TRONG LỚP &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/teams/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                QUẢN LÝ NHÓM &nbsp;
              </Link>{" "}
              <Link
                to={`/teacher/my-class/meeting/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                BUỔI HỌC &nbsp;
              </Link>{" "}
              <Link
                to={`/teacher/my-class/attendance/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM DANH &nbsp;
              </Link>
              <Link
                to=""
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM &nbsp;
              </Link>
              <div
                className="box-center"
                style={{
                  height: "28.5px",
                  width: "auto",
                  backgroundColor: "#007bff",
                  color: "white",
                  borderRadius: "5px",
                  float: "right",
                }}
              >
                {" "}
                <span style={{ fontSize: "14px", padding: "10px" }}>
                  {classDetail.code}
                </span>
              </div>
              <hr />
            </div>
          </div>
          <div
            className="content-class"
            style={{ margin: "25px 0px 15px 15px" }}
          >
            {/* <Row style={{ marginBottom: "4px", marginTop: "6px" }}>
                  <Col span={24}>
                    <span>Hoạt động: &nbsp; {classDetail.activityName}</span>
                  </Col>
                </Row>
                <Row style={{ marginBottom: "4px" }}>
                  <Col>
                    <span>Level: &nbsp; {classDetail.activityLevel + 1}</span>
                  </Col>
                </Row>
                <Row gutter={16} style={{ marginBottom: "4px" }}>
                  {" "}
                  <Col span={24}>
                    <span>
                      Thời gian bắt đầu:&nbsp;
                      {moment(classDetail.startTime).format("DD-MM-YYYY")}
                    </span>{" "}
                  </Col>
                </Row>
                <Row gutter={16} style={{ marginBottom: "4px" }}>
                  <Col>
                    <span>Mã lớp: &nbsp;{classDetail.code}</span>
                  </Col>
                </Row>
                <Row style={{ marginBottom: "4px" }}>
                  <Col>
                    <span>Tên lớp: &nbsp;{classDetail.name}</span>
                  </Col>
                </Row>
                <Row style={{ marginBottom: "4px" }}>
                  <Col>
                    <span>Ca học: &nbsp; {classDetail.classPeriod + 1} </span>
                  </Col>
                </Row>
                <Row style={{ marginBottom: "4px" }}>
                  <Col>
                    <span>Số thành viên: &nbsp;{classDetail.classSize}</span>
                  </Col>
                </Row>
                <Row gutter={16} style={{ marginBottom: "4px" }}>
                  <Col span={24}>
                    <span>Mô tả: &nbsp;{classDetail.descriptions}</span>
                  </Col>
                </Row>
                <Row gutter={16}>
                  <Col span={24}>
                    <span>Mật khẩu: &nbsp;{classDetail.passWord}</span>
                  </Col>
                </Row> */}
            <br />
          </div>
          <div style={{ minHeight: "140px" }}>
            {/* {data.length > 0 ? (
                  <>
                    <div className="table">
                      <Table
                        dataSource={data}
                        rowKey="id"
                        columns={columns}
                        pagination={false}
                      />
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
                      Không có thành viên
                    </p>
                  </>
                )} */}
          </div>
        </div>
      </div>
    </>
  );
};
export default TeacherPostMyClass;
