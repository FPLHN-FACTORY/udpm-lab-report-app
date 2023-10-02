import { sinhVienCurrent } from "../../helper/inForUser";
import { request } from "../../helper/request.helper";

export class StFeedBackAPI {
  static checkFeedBack() {
    return request({
      method: "GET",
      url: "/student/feedback/check?userId=" + sinhVienCurrent.id,
    });
  }

  static getAllClassFeedback() {
    return request({
      method: "GET",
      url:
        "/student/feedback/get-all-class-feedback?studentId=" +
        sinhVienCurrent.id,
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
