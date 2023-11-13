import { Button, Spin, message } from "antd";
import { useState } from "react";
import { TeacherExcelPointAPI } from "../../../../../api/teacher/point/excel/TeacherExcelPoint.api";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faDownload } from "@fortawesome/free-solid-svg-icons";
import { useAppDispatch } from "../../../../../app/hook";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../../app/common/Loading.reducer";

const ButtonExportExcel = ({ idClass }) => {
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
      const response = await TeacherExcelPointAPI.export(idClass);
      const blob = new Blob([response.data], {
        type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
      });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download =
        "BangDiem_" + convertLongToDateTime(new Date().getTime()) + ".xlsx";
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
      style={{
        backgroundColor: "rgb(38, 144, 214)",
        color: "white",
        marginRight: "5px",
      }}
      onClick={handleExport}
    >
      <FontAwesomeIcon icon={faDownload} style={{ marginRight: "7px" }} />
      <span>Export bảng điểm</span>
    </Button>
  );
};
export default ButtonExportExcel;
