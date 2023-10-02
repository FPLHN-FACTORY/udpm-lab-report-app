import { request } from "../../helper/request.helper";

export class StAttendenceAPI {

  static getClassAttendenceListByStudentInClassAndSemester(filter) {
    return request({
      method: "GET",
      url: "/student/attendence",
      params: {
        idSemester: filter.idSemester,
      },
    });
  }

}
