import { request } from "../../../../helper/request.helper";
const url = `/teacher/excel`;

export class TeacherExcelAPI {
  static export = (data) => {
    return request({
      method: "POST",
      url: url + `/export`,
      data: data,
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
