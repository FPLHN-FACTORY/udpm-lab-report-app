import { request } from "../../../../helper/request.helper";
const url = `/teacher/point`;
export class TeacherExcelAPI {
  static export = (data) => {
    return request({
      method: "GET",
      url: url + `/export-excel?idClass=` + data.idClass,
    });
  };
  static import = (formData, idClass) => {
    return request({
      method: "POST",
      url: url + `/import-excel/` + idClass,
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  };
}