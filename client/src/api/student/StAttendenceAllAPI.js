import { request } from "../../helper/request.helper";

export class StAttendenceAPI {

  static getClassAttendenceListByStudentInClassAndSemester(filter) {
    return request({
      method: "GET",
      url: "/student/attendence",
      params: {
        idStudent: filter.idStudent,
        idSemester: filter.idSemester,
      },
    });
  }

}
