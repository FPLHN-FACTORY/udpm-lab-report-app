import { request } from "../../../helper/request.helper";

const url = `/teacher/activity`;

export class TeacherActivityAPI {
  static getAllActivityByIdSemester(id) {
    return request({
      method: "GET",
      url: url + `/id-semester?idSemester=` + id,
    });
  }
}
