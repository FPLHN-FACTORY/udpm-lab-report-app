import { request } from "../../helper/request.helper";

export class StFeedBackAPI {
  static checkFeedBack() {
    return request({
      method: "GET",
      url: "/student/feedback/check",
    });
  }

  static getAllClassFeedback() {
    return request({
      method: "GET",
      url: "/student/feedback/get-all-class-feedback",
    });
  }

  static getSemesterCurrent() {
    return request({
      method: "GET",
      url: "/student/feedback/get-semester-current",
    });
  }

  static createFeedBack(data) {
    return request({
      method: "POST",
      url: "/student/feedback/create-feedback",
      data: data,
    });
  }
}
