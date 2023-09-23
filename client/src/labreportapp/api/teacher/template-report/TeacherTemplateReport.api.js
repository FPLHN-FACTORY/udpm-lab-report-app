import { request } from "../../../helper/request.helper";

const url = `/teacher-template-report`;

export class TeacherTempalteReportAPI {
  static getTemplateReport() {
    return request({
      method: "GET",
      url: url,
    });
  }
}
