import { Button, Input, Modal, Upload, message } from "antd";
import { TeacherExcelTeamAPI } from "../../../../../api/teacher/teams-class/excel/TeacherExcelTeam.api";
import { useEffect, useState } from "react";
import { useAppDispatch } from "../../../../../app/hook";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../../app/common/Loading.reducer";
import { InboxOutlined } from "@ant-design/icons";
import { TeacherMeetingAPI } from "../../../../../api/teacher/meeting/TeacherMeeting.api";
const { Dragger } = Upload;

const ModalFileImportMeeting = ({ visible, onCancel, idClass }) => {
  const [inputFileKey, setInputFileKey] = useState(0);
  const [selectedFile, setSelectedFile] = useState(null);

  const dispatch = useAppDispatch();
  useEffect(() => {
    if (visible) {
      setSelectedFile(null);
      setInputFileKey((prevKey) => prevKey + 1);
    } else {
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

  const handleImport = async () => {
    if (!selectedFile) {
      message.warning("Vui lòng chọn file excel trước khi import !");
      return;
    }
    dispatch(SetLoadingTrue());
    const formData = new FormData();
    formData.append("multipartFile", selectedFile);
    try {
      await TeacherMeetingAPI.import(formData, idClass).then((response) => {
        if (response.data.data.status === true) {
          message.success(response.data.data.message);
        } else {
          message.error(response.data.data.message);
        }
        setSelectedFile(null);
        setInputFileKey((prevKey) => prevKey + 1);
        onCancel();
        dispatch(SetLoadingFalse());
      });
    } catch (error) {
      dispatch(SetLoadingFalse());
    }
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
          <span style={{ fontSize: "18px" }}>Yêu cầu tạo buổi học:</span>
        </div>
        <div style={{ paddingTop: "20px" }}>
          <Input
            key={inputFileKey}
            type="file"
            accept=".xlsx,.xls"
            onChange={(e) => {
              handleFileChange(e);
            }}
            placeholder="Chọn file"
          />
          <div style={{ marginTop: 8 }}>
            <span style={{ color: "red" }}>
              (*) Lưu ý: File excel mẫu của chức năng import là file excel được
              chỉnh sửa sau khi sử dụng chức năng export
            </span>
          </div>
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
                width: "100px",
                marginLeft: "10px",
              }}
              onClick={() => {
                handleImport();
              }}
            >
              Gửi yêu cầu
            </Button>
          </div>
        </div>
      </Modal>
    </>
  );
};
export default ModalFileImportMeeting;