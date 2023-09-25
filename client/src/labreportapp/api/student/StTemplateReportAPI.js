import { request } from "../../helper/request.helper";

const url = `/student-template-report`;

export class StudentTempalteReportAPI {
  static getTemplateReport() {
    return request({
      method: "GET",
      url: url,
    });
  }
}