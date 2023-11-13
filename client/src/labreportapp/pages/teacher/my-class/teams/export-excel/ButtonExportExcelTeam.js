import { Button, Spin, message } from "antd";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faDownload } from "@fortawesome/free-solid-svg-icons";
import { TeacherExcelTeamAPI } from "../../../../../api/teacher/teams-class/excel/TeacherExcelTeam.api";
import { useAppDispatch } from "../../../../../app/hook";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../../app/common/Loading.reducer";

const ButtonExportExcelTeam = ({ idClass }) => {
  const dispatch = useAppDispatch();

  const convertLongToDateTime = (dateLong) => {
    const date = new Date(dateLong);
    const format = `${date.getFullYear()}-${
      date.getMonth() + 1
    }-${date.getDay()}_${date.getHours()}_${date.getMinutes()}_${date.getSeconds()}`;
    return format;
  };
  const handleExport = async () => {
    try {
      dispatch(SetLoadingTrue());
      const response = await TeacherExcelTeamAPI.export(idClass);
      const blob = new Blob([response.data], {
        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
      });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download =
        "DanhSachNhom_" + convertLongToDateTime(new Date().getTime()) + ".xlsx";
      link.click();
      window.URL.revokeObjectURL(url);
      dispatch(SetLoadingFalse());
      message.success("Export thành công !");
    } catch (error) {
      dispatch(SetLoadingFalse());
    }
  };
  return (
    <Button
      className="btn_clear"
      style={{
        backgroundColor: "rgb(38, 144, 214)",
        color: "white",
      }}
      onClick={handleExport}
    >
      <FontAwesomeIcon icon={faDownload} style={{ marginRight: "7px" }} />
      <span>Export nhóm</span>
    </Button>
  );
};
export default ButtonExportExcelTeam;
