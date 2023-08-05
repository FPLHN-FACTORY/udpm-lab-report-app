import { request } from "../../../helper/request.helper";

const url = `/teacher`;

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
}
