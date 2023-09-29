
import { request } from "../../helper/request.helper";

const url = `/admin/feed-back`;

export class AdminFeedBackAPI {
static getAllFeedBackByIdClass(idClass) {
    return request({
      method: "GET",
      url: url + `/get/` + idClass,
    });
  }
  static getStudentInClasses(id) {
    return request({
      method: "GET",
      url: url + `/get-student?idClass=` + id,
    });
  }
}