import { request } from "../../helper/request.helper";

export class StMyClassAPI {
  static getAllSemesters() {
    return request({
      method: "GET",
      url: "/student/semester",
    });
  }

  static getAllActivityByIdSemester(semesterId) {
    return request({
      method: "GET",
      url: "/student/activity?semesterId=" + semesterId,
    });
  }

  static getAllClass(filter) {
    return request({
      method: "GET",
      url: "/student/my-class",
      params: filter,
    });
  }
}
