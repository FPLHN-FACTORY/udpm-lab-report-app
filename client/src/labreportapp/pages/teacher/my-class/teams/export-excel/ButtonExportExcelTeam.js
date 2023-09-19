import { Button, Spin } from "antd";
import { useState } from "react";
import { toast } from "react-toastify";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faDownload } from "@fortawesome/free-solid-svg-icons";
import { TeacherExcelTeamAPI } from "../../../../../api/teacher/teams-class/excel/TeacherExcelTeam.api";

const ButtonExportExcelTeam = ({ idClass }) => {
  const [downloading, setDownloading] = useState(false);
  const handleExport = async () => {
    try {
      await TeacherExcelTeamAPI.export(idClass)
        .then((respone) => {
          setDownloading(true);
          window.open(
            `http://localhost:2509/teacher/teams/export-excel?idClass=` +
              idClass,
            "_self"
          );
          setTimeout(() => {
            setDownloading(false);
            toast.success("Export thành công !");
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
        className="btn_clear"
        style={{
          backgroundColor: "rgb(38, 144, 214)",
          color: "white",
        }}
        onClick={handleExport}
      >
        <FontAwesomeIcon icon={faDownload} style={{ marginRight: "7px" }} />
        {downloading ? "Đang tải xuống..." : "Export nhóm"}
      </Button>
    </Spin>
  );
};
export default ButtonExportExcelTeam;
