import { request } from "../../../helper/request.helper";

const url = `/teacher/category`;

export class TeacherCategoryAPI {
  static getAllCategory() {
    return request({
      method: "GET",
      url: url,
    });
  }
}
