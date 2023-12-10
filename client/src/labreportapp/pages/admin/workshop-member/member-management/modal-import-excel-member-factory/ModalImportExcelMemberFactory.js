import React, { useEffect, useState } from "react";
import { Modal, Button, Select, Row, Upload, message } from "antd";
import "react-toastify/dist/ReactToastify.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUpload } from "@fortawesome/free-solid-svg-icons";
import { UploadOutlined } from "@ant-design/icons";
import { AdMemberFactoryAPI } from "../../../../../api/admin/AdMemberFactoryAPI";
import LoadingIndicatorNoOverlay from "../../../../../helper/loadingNoOverlay";

const ModalImportExcelMemberFactory = ({ visible, onCancel, fetchData }) => {
  const [selectedExcel, setSelectedExcel] = useState(null);
  const [errorSelectedExcel, setErrorSelectedExcel] = useState("");
  const [loading, setLoading] = useState(false);

  const handleExcelUpload = (info) => {
    setSelectedExcel(info.file.originFileObj);
  };

  useEffect(() => {
    setSelectedExcel(null);
    setErrorSelectedExcel("");
  }, [visible]);

  const uploadFileExcel = () => {
    let check = 0;
    if (selectedExcel == null) {
      setErrorSelectedExcel("Hãy chọn file excel");
      ++check;
    } else {
      setErrorSelectedExcel("");
    }

    if (check === 0) {
      setLoading(true);
      const formData = new FormData();
      formData.append("multipartFile", selectedExcel);
      AdMemberFactoryAPI.importExcelMemberFactory(formData).then(
        (response) => {
          if (response.data.data.status === false) {
            message.success(response.data.data.message);
          } else {
            message.success("Import excel thành công !");
            setLoading(false);
            fetchData();
          }
          setLoading(false);
          onCancel();
        },
        (error) => {}
      );
    }
  };

  return (
    <>
      {loading && <LoadingIndicatorNoOverlay />}
      <Modal
        visible={visible}
        onCancel={onCancel}
        width={700}
        footer={null}
        className="modal_show_detail_project"
      >
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>
            Import excel danh sách thành viên xưởng
          </span>
        </div>
        <Row style={{ display: "flex", alignItems: "center", marginTop: 20 }}>
          <span style={{ marginRight: "15px" }}>Chọn file excel:</span>
          <Upload
            listType="picture"
            showUploadList={false}
            onChange={handleExcelUpload}
            style={{ width: "100%", marginLeft: "15px" }}
            accept=".xls, .xlsx"
          >
            <Button style={{ width: "100%" }} icon={<UploadOutlined />}>
              Chọn file excel
            </Button>
          </Upload>{" "}
        </Row>
        <Row>{selectedExcel && <p>Tệp đã chọn: {selectedExcel.name}</p>}</Row>
        <Row>
          <span style={{ color: "red" }}>{errorSelectedExcel}</span>
        </Row>
        <div>
          <div style={{ textAlign: "right" }}>
            <div style={{ paddingTop: "15px" }}>
              <Button
                style={{
                  marginRight: "5px",
                  backgroundColor: "rgb(61, 139, 227)",
                  color: "white",
                }}
                onClick={uploadFileExcel}
              >
                <FontAwesomeIcon
                  icon={faUpload}
                  style={{ marginRight: "5px" }}
                />
                Upload
              </Button>
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
export default ModalImportExcelMemberFactory;
