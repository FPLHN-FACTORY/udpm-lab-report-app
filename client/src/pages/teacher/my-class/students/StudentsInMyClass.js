import { useParams } from "react-router-dom";
import "./styleStudentsInMyClass.css";
import { Modal, Row, Col, Input, Table, Image, Pagination, Button } from "antd";
import { Link } from "react-router-dom";
import {
  ControlOutlined,
  QuestionCircleFilled,
  ProjectOutlined,
} from "@ant-design/icons";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import moment from "moment";
const { TextArea } = Input;

const StudentsInMyClass = () => {
  const [classDetail, setClassDetail] = useState({});
  const [loading, setLoading] = useState(false);
  const { id } = useParams();
  useEffect(() => {
    window.scrollTo(0, 0);
    featchClass(id);
  }, []);

  const featchClass = async (id) => {
    setLoading(false);
    try {
      await TeacherMyClassAPI.detailMyClass(id).then((responese) => {
        setLoading(true);
        setClassDetail(responese.data.data);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="title-teacher-my-class">
        <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
          <ControlOutlined style={{ fontSize: "22px" }} />
          <span style={{ marginLeft: "10px", fontWeight: "500" }}>
            Bảng điều khiển
          </span>
        </span>
      </div>
      <div className="box-students-in-class">
        <div className="button-menu-teacher">
          <div
            className="box-center"
            style={{
              height: "30px",
              width: "110px",
              backgroundColor: "#007bff",
              color: "white",
              marginBottom: "4px",
              marginLeft: "10px",
            }}
          >
            {" "}
            <span>{classDetail.classSize} thành viên</span>
          </div>
          <Link
            to={`/teacher/my-class/students-in-class/${id}`}
            className="link-change"
            style={{
              fontSize: "16px",
              marginLeft: "10px",
            }}
          >
            THÀNH VIÊN TRONG LỚP
          </Link>
          <Link
            to={`/teacher/my-class/students-in-class/${id}`}
            className="link-change"
            style={{ fontSize: "16px", marginLeft: "20px" }}
          >
            ĐIỂM DANH
          </Link>
          <Link
            to={`/teacher/my-class/students-in-class/${id}`}
            className="link-change"
            style={{ fontSize: "16px", marginLeft: "20px" }}
          >
            QUẢN LÝ NHÓM
          </Link>
          <hr />
        </div>
        <div>
          <p>Tên lớp: {classDetail.name}</p>
          <p>Mã lớp: {classDetail.code}</p>
          <p>Mật khẩu:{classDetail.passWord}</p>
        </div>

        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Chi tiết lớp học</span>
        </div>
        <div style={{ marginTop: "15px" }}>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={8}>
              <span>Học kỳ:</span> <br />
              <Input value={classDetail.semesterName} type="text" readOnly />
            </Col>
            <Col span={8}>
              <span>Level:</span> <br />
              <Input value={classDetail.activityLevel} type="text" readOnly />
            </Col>
            <Col span={8}>
              <span>Sĩ số:</span> <br />
              <Input value={classDetail.classSize} type="text" readOnly />
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Hoạt động:</span> <br />
              <Input value={classDetail.activityName} type="text" readOnly />
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={12}>
              <span>Mã lớp:</span> <br />
              <Input value={classDetail.code} type="text" readOnly />
            </Col>
            <Col span={12}>
              <span>Tên lớp:</span> <br />
              <Input type="text" value={classDetail.name} readOnly />
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={8}>
              <span>Thời gian bắt đầu:</span> <br />
              <Input
                value={moment(classDetail.startTime).format("YYYY-MM-DD")}
                type="date"
                readOnly
              />
            </Col>
            <Col span={8}>
              <span>Ca học:</span> <br />
              <Input value={classDetail.classPeriod} type="text" readOnly />
            </Col>
            <Col span={8}>
              <span>Mật khẩu lớp:</span> <br />
              <Input value={classDetail.passWord} type="text" readOnly />
            </Col>
          </Row>
          <Row gutter={16} style={{ marginBottom: "15px" }}>
            <Col span={24}>
              <span>Mô tả:</span> <br />
              <TextArea rows={2} value={classDetail.descriptions} readOnly />
            </Col>
          </Row>
        </div>
      </div>

      {/* </Modal> */}
    </>
  );
};

export default StudentsInMyClass;
