import { request } from "../../../../helper/request.helper";
const url = `/teacher/point`;
export class TeacherExcelAPI {
  static export = (data) => {
    return request({
      method: "GET",
      url: url + `/export-excel` + `?idClass=` + data.idClass,
      // data: data,
    });
  };
  // static import = (data) => {
  //   return request({
  //     method: "POST",
  //     url: url,
  //     data: data,
  //   });
  // };
}
