import React, { useEffect, useState } from "react";
import { Modal, Button, Select, Row, Upload, message } from "antd";
import "./style-modal-import-class.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUpload } from "@fortawesome/free-solid-svg-icons";
import { UploadOutlined } from "@ant-design/icons";
import { ClassAPI } from "../../../../api/admin/class-manager/ClassAPI.api";
import LoadingIndicatorNoOverlay from "../../../../helper/loadingNoOverlay";

const { Option } = Select;

const ModalImportClass = ({ visible, onCancel, fetchData }) => {
  const [selectedExcel, setSelectedExcel] = useState(null);
  const [errorSelectedExcel, setErrorSelectedExcel] = useState("");
  const [loading, setLoading] = useState(false);

  const handleExcelUpload = (info) => {
    setSelectedExcel(info.file.originFileObj);
  };

  useEffect(() => {
    setSelectedExcel(null);
    setErrorSelectedExcel("");
    setErrorSemester("");
  }, [visible]);

  const [idSemesterSeach, setIdSemesterSearch] = useState("");
  const [errorSemester, setErrorSemester] = useState("");
  const [semesterDataAll, setSemesterDataAll] = useState([]);

  useEffect(() => {
    const featchDataSemester = async () => {
      try {
        const responseClassAll = await ClassAPI.fetchAllSemester();
        const listSemester = responseClassAll.data;
        if (listSemester.data.length > 0) {
          listSemester.data.forEach((item) => {
            if (
              item.startTime <= new Date().getTime() &&
              new Date().getTime() <= item.endTime
            ) {
              setIdSemesterSearch(item.id);
            }
          });
        } else {
          setIdSemesterSearch(null);
        }
        setSemesterDataAll(listSemester.data);
      } catch (error) {}
    };
    featchDataSemester();
  }, []);

  const filterOptions = (input, option) => {
    return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0;
  };

  const uploadFileExcel = () => {
    let check = 0;
    if (selectedExcel == null) {
      setErrorSelectedExcel("Hãy chọn file excel");
      ++check;
    } else {
      setErrorSelectedExcel("");
    }
    if (idSemesterSeach === "") {
      setErrorSemester("Hãy chọn học kỳ");
      ++check;
    } else {
      setErrorSemester("");
    }
    if (check === 0) {
      setLoading(true);
      const formData = new FormData();
      formData.append("multipartFile", selectedExcel);
      ClassAPI.importExcel(formData, idSemesterSeach).then(
        (response) => {
          if (response.data.data.status === false) {
            message.success(response.data.data.message);
          } else {
            message.success("Import thành công !");
            onCancel();
            fetchData();
          }
          setLoading(false);
        },
        (error) => {}
      );
    }
  };

  return (
    <>
      {loading && <LoadingIndicatorNoOverlay />}
      <Modal
        open={visible}
        onCancel={onCancel}
        width={700}
        footer={null}
        className="modal_show_detail_project"
      >
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Import excel lớp học</span>
        </div>
        <div
          style={{
            marginTop: "15px",
            borderBottom: "1px solid gray",
            marginBottom: "2px",
          }}
        >
          <Row style={{ display: "flex", alignItems: "center" }}>
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
          <Row style={{ marginTop: "15px", marginBottom: "15px" }}>
            Chọn học kỳ:
            <Select
              showSearch
              style={{ width: "100%" }}
              value={idSemesterSeach}
              onChange={(value) => {
                setIdSemesterSearch(value);
              }}
              filterOption={filterOptions}
            >
              <Option value="">Chọn 1 học kỳ</Option>

              {semesterDataAll != null && semesterDataAll.map((semester) => (
                <Option key={semester.id} value={semester.id}>
                  {semester.name}
                </Option>
              ))}
            </Select>{" "}
            <span style={{ color: "red" }}>{errorSemester}</span>
          </Row>
        </div>

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
export default ModalImportClass;
