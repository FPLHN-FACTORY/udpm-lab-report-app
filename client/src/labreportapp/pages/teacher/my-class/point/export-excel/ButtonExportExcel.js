import { Button, Spin } from "antd";
import { useState } from "react";
import { TeacherExcelAPI } from "../../../../../api/teacher/point/excel/TeacherExcelPoint.api";
import { toast } from "react-toastify";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faDownload } from "@fortawesome/free-solid-svg-icons";

const ButtonExportExcel = ({ idClass }) => {
  const [downloading, setDownloading] = useState(false);
  const handleExport = async () => {
    try {
      await TeacherExcelAPI.export(idClass)
        .then((respone) => {
          window.open(
            `http://localhost:2509/teacher/point/export-excel?idClass=` +
              idClass,
            "_self"
          );
          setDownloading(true);
          setTimeout(() => {
            setDownloading(false);
            toast.success("Export bảng điểm thành công !");
          }, 1000);
        })
        .catch((err) => {
          toast.error("Export thất bại !");
        });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  return (
    <Spin spinning={downloading}>
      <Button
        style={{
          backgroundColor: "rgb(38, 144, 214)",
          color: "white",
          marginRight: "5px",
        }}
        onClick={handleExport}
      >
        <FontAwesomeIcon icon={faDownload} style={{ marginRight: "7px" }} />
        {downloading ? "Đang tải xuống..." : "Export bảng điểm"}
      </Button>
    </Spin>
  );
};
export default ButtonExportExcel;
