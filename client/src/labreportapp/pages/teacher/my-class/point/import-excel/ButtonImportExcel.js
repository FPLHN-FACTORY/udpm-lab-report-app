import { Button, Spin } from "antd";
import { useState } from "react";
import { TeacherExcelPointAPI } from "../../../../../api/teacher/point/excel/TeacherExcelPoint.api";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUpload } from "@fortawesome/free-solid-svg-icons";
import { toast } from "react-toastify";

const ButtonImportExcel = ({ idClass }) => {
  const [downloading, setDownloading] = useState(false);
  const [inputFile, setInputFile] = useState("");
  const handleFileChange = async (e) => {
    try {
      const selectedFile = e.target.files[0];
      if (selectedFile) {
        const formData = new FormData();
        formData.append("multipartFile", selectedFile);
        await TeacherExcelPointAPI.import(formData, idClass)
          .then((response) => {
            setTimeout(() => {
              if (response.data.data.status === true) {
                setInputFile("");
                toast.success(
                  "Import điểm sinh viên thành công, vui lòng chờ !",
                  {
                    position: toast.POSITION.TOP_CENTER,
                  }
                );
              } else {
                toast.error(
                  "Import thất bại, " +
                    response.data.data.message +
                    ", vui lòng chờ giây lát !",
                  {
                    position: toast.POSITION.TOP_CENTER,
                  }
                );
              }
            }, 200);
            setDownloading(true);
            setTimeout(() => {
              window.open(
                `http://localhost:3000/teacher/my-class/point/` + idClass,
                "_self"
              );
              setDownloading(false);
            }, 6400);
          })
          .catch((error) => {
            alert("Lỗi hệ thống, vui lòng F5 lại trang !");
          });
      }
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  return (
    <>
      <Spin spinning={downloading}>
        <Button
          style={{
            backgroundColor: "rgb(38, 144, 214)",
            color: "white",
          }}
          onClick={() => {
            document.getElementById("fileInput").click();
          }}
        >
          <FontAwesomeIcon icon={faUpload} style={{ marginRight: "7px" }} />
          {downloading ? (
            "Đang tải lên..."
          ) : (
            <>
              Import bảng điểm
              <input
                id="fileInput"
                type="file"
                accept=".xlsx"
                onChange={handleFileChange}
                style={{
                  display: "none",
                }}
              />
            </>
          )}
        </Button>
      </Spin>
    </>
  );
};
export default ButtonImportExcel;
