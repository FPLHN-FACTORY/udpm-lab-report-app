import { Button, Input, Modal, message } from "antd";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import { TeacherExcelPointAPI } from "../../../../../api/teacher/point/excel/TeacherExcelPoint.api";

const ModalFileImportPoint = ({ visible, onCancel, idClass, fetchData }) => {
  const [inputFileKey, setInputFileKey] = useState(0);
  const [selectedFile, setSelectedFile] = useState(null);
  useEffect(() => {
    if (visible) {
      setSelectedFile(null);
      setInputFileKey((prevKey) => prevKey + 1);
    }
  }, [visible]);
  const handleCancelImport = () => {
    setSelectedFile(null);
    setInputFileKey((prevKey) => prevKey + 1);
    onCancel();
  };
  const handleFileChange = async (e) => {
    const file = e.target.files[0];
    if (file) {
      setSelectedFile(file);
    }
  };
  const handleImportPoint = async () => {
    if (!selectedFile) {
      toast.warning("Vui lòng chọn file excel để import !");
      return;
    }
    const formData = new FormData();
    formData.append("multipartFile", selectedFile);
    await TeacherExcelPointAPI.import(formData, idClass).then((response) => {
      onCancel();
      if (response.data.data.status === true) {
        fetchData(idClass);
        message.success(response.data.data.message);
      } else {
        message.error(response.data.data.message);
      }
      setSelectedFile(null);
      setInputFileKey((prevKey) => prevKey + 1);
    });
  };
  return (
    <>
      <Modal
        open={visible}
        onCancel={() => {
          handleCancelImport();
        }}
        bodyStyle={{ overflow: "hidden" }}
        footer={null}
      >
        <div
          style={{
            paddingTop: "0",
            borderBottom: "1px solid black",
          }}
        >
          <span style={{ fontSize: "18px" }}>
            Chọn file excel để import điểm:
          </span>
        </div>
        <div style={{ paddingTop: "20px" }}>
          <Input
            key={inputFileKey}
            type="file"
            accept=".xlsx"
            onChange={(e) => {
              handleFileChange(e);
            }}
            placeholder="Chọn file"
          />
          <div style={{ paddingTop: "15px", float: "right", right: 0 }}>
            <Button
              className="btn_filter"
              style={{
                width: "66px",
              }}
              onClick={() => handleCancelImport()}
            >
              Hủy
            </Button>
            <Button
              className="btn_clean"
              style={{
                width: "66px",
                marginLeft: "10px",
              }}
              onClick={() => {
                handleImportPoint();
              }}
            >
              Lưu
            </Button>
          </div>
        </div>
      </Modal>
    </>
  );
};
export default ModalFileImportPoint;
