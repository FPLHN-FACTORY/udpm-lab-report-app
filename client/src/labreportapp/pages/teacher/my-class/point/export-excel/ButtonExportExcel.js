import { Button, Spin } from "antd";
import { useState } from "react";
import { TeacherExcelPointAPI } from "../../../../../api/teacher/point/excel/TeacherExcelPoint.api";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faDownload } from "@fortawesome/free-solid-svg-icons";
import { toast } from "react-toastify";
import LoadingIndicatorNoOverlay from "../../../../../helper/loadingNoOverlay";

const ButtonExportExcel = ({ idClass }) => {
  const [downloading, setDownloading] = useState(false);

  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const format = `${date.getFullYear()}-${
      date.getMonth() + 1
    }-${date.getDay()}_${date.getHours()}_${date.getMinutes()}_${date.getSeconds()}`;
    return format;
  };

  const handleExport = async () => {
    try {
      const response = await TeacherExcelPointAPI.export(idClass);
      const blob = new Blob([response.data], {
        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
      });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download =
        "BangDiem_" + convertLongToDate(new Date().getTime()) + ".xlsx";
      link.click();
      window.URL.revokeObjectURL(url);
      setDownloading(true);
      setTimeout(() => {
        setDownloading(false);
      }, 1500);
      toast.success("Export thành công !");
    } catch (error) {
      console.log(error);
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
