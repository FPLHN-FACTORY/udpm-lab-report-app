import { Button } from "antd";
import { useState } from "react";
import { TeacherExcelAPI } from "../../../../../api/teacher/point/excel/TeacherExcel.api";
import { toast } from "react-toastify";
import { useAppSelector } from "../../../../../app/hook";
import { GetPoint } from "../../../../../app/teacher/point/tePointSlice.reduce";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faDownload } from "@fortawesome/free-solid-svg-icons";
import LoadingIndicatorNoOverlay from "../../../../../helper/loadingNoOverlay";

const ButtonExportExcel = () => {
  const [downloading, setDownloading] = useState(false);
  const handleExport = async () => {
    try {
      const listDataFind = data.map((item) => {
        return {
          name: item.name,
          email: item.email,
          checkPointPhase1: item.checkPointPhase1,
          checkPointPhase2: item.checkPointPhase2,
          finalPoint: item.finalPoint,
        };
      });
      let dataExport = {
        listExcel: listDataFind,
      };
      await TeacherExcelAPI.export(dataExport)
        .then((respone) => {
          setDownloading(true);
          setTimeout(() => {
            setDownloading(false);
            toast.success(
              "Export thành công, file đã được lưu vào mục Downloads !"
            );
          }, 1500);
        })
        .catch((err) => {
          toast.error("Export thất bại !");
        });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const data = useAppSelector(GetPoint);
  return (
    <Button
      style={{
        backgroundColor: "rgb(38, 144, 214)",
        color: "white",
        marginRight: "5px",
      }}
      onClick={handleExport}
    >
      <FontAwesomeIcon icon={faDownload} style={{ marginRight: "7px" }} />

      {downloading ? "Đang tải xuống..." : "Export mẫu điểm"}
      {downloading && <LoadingIndicatorNoOverlay />}
    </Button>
  );
};
export default ButtonExportExcel;
