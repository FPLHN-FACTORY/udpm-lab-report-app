import { request } from "../../helper/request.helper";

const url = `/admin/template-report`;
export class AdTemplateReportAPI {
  static fetchTemplateReportById = () => {
    return request({
      method: "GET",
      url: url,
    });
  };

  static updateTemplateReportById = (data) => {
    return request({
      method: "PUT",
      url: url + `/update`,
      data: data,
    });
  };

  static dowloadLog = () => {
    return request({
      method: "GET",
      url: url + `/download-log`,
      responseType: "blob",
    });
  };

  static showHistory = (params) => {
    return request({
      method: "GET",
      url: url + `/history`,
      params: params,
    });
  };
}
