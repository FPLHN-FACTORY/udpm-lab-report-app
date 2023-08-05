import { request } from "../../../helper/request.helper";

const url = `/teacher/semester`;

export class TeacherSemesterAPI {
  static getAllSemesters() {
    return request({
      method: "GET",
      url: url,
    });
  }
}
