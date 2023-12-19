import { request } from "../../helper/request.helper";

export class TeacherAPI {
  static getAllTeacher() {
    return request({
      method: "GET",
      url: "/teacher/get-all-teacher",
    });
  }
}
