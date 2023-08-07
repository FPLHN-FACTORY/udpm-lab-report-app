import { Modal, Row, Col, Input, Table, Image, Pagination } from "antd";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { useEffect, useState } from "react";
import LoadingIndicator from "../../../../helper/loading";
import moment from "moment";
const { TextArea } = Input;

const ModalTeacherDetailMyClass = ({ visible, onCancel, idDetail }) => {
  const [classDetail, setClassDetail] = useState({});
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (idDetail !== null && idDetail !== "" && visible === true) {
      featchClass(idDetail);
    }
  }, [visible, idDetail, onCancel]);

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
      {" "}
      {!loading && <LoadingIndicator />}
      <Modal
        onCancel={onCancel}
        visible={visible}
        width={750}
        footer={null}
        className="modal_project_show_detail"
      >
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
      </Modal>
    </>
  );
};

export default ModalTeacherDetailMyClass;
