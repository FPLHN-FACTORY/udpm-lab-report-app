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
  static getClassSentStudent(filter) {
    return request({
      method: "GET",
      url:
        url +
        `/class-sent` +
        `?idSemester=` +
        filter.idSemester +
        `&idActivity=` +
        filter.idActivity +
        `&idLevel=` +
        filter.idLevel +
        `&idClass=` +
        filter.idClass +
        `&countStudent=` +
        filter.countStudent +
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
  static randomPass(idClass) {
    return request({
      method: "POST",
      url: url + `/pass-random`,
      params: {
        idClass: idClass,
      },
    });
  }
  static updateStatusClass(data) {
    return request({
      method: "POST",
      url: url + `/pass`,
      data: data,
    });
  }
}
