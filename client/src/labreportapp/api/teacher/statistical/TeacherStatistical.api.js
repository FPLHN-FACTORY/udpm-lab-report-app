import { request } from "../../../helper/request.helper";

const url = `/teacher/statistical`;

export class TeacherStatisticalAPI {
  static getStatistical(data) {
    return request({
      method: "GET",
      url:
        url +
        `?idSemester=` +
        data.idSemester +
        `&idActivity=` +
        data.idActivity +
        `&page=` +
        data.page +
        `&size=` +
        data.size,
    });
  }
  static getCountClass(data) {
    return request({
      method: "GET",
      url:
        url +
        `/count-class?idSemester=` +
        data.idSemester +
        `&idActivity=` +
        data.idActivity +
        `&page=` +
        data.page +
        `&size=` +
        data.size,
    });
  }
  static export = (idClass) => {
    return request({
      method: "GET",
      url: url + `/export-excel-class?idClass=` + idClass,
      responseType: "blob",
    });
  };
}
