import { request } from "../../../../helper/request.helper";
const url = `/teacher/point`;
export class TeacherExcelPointAPI {
  static export = (idClass) => {
    return request({
      method: "GET",
      url: url + `/export-excel?idClass=` + idClass,
      responseType: "blob",
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
