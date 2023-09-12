import { request } from "../../../helper/request.helper";

const url = `/email`;

export class TeacherMailAPI {
  static sentMaillTeacherPostToStudent(data) {
    return request({
      method: "POST",
      url: url,
      data: data,
    });
  }
}
