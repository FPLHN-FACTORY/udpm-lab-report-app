import { request } from "../../helper/request.helper";

export class StPointAllAPI {
  static getClassPointListByStudentInClassAndSemester(filter) {
    return request({
      method: "GET",
      url: "/student/point",
      params: {
        idStudent: filter.idStudent,
        idSemester: filter.idSemester,
      },
    });
  }
}
