import { request } from "../../../helper/request.helper";
const url = `/teacher/class`;

export class TeacherMyClassAPI {
  static getAllMyClass(filter) {
    return request({
      method: "GET",
      url:
        url +
        `?idTeacher=` +
        filter.idTeacher +
        `&idSemester=` +
        filter.idSemester +
        `&idActivity=` +
        filter.idActivity +
        `&code=` +
        filter.code +
        `&name=` +
        filter.name +
        `&classPeriod=` +
        filter.classPeriod +
        `&level=` +
        filter.level +
        `&page=` +
        filter.page +
        `&size=` +
        filter.size,
    });
  }
  static detailMyClass(idClass) {
    return request({
      method: "GET",
      url: url + `/` + idClass,
    });
  }
}
