import { request } from "../../helper/request.helper";

const url = `/admin/template-report`;
export class AdTemplateReportAPI {
  static fetchTemplateReportById = () => {
    return request({
      method: "GET",
      url: url + `/b95b0477-7c5c-41ed-9588-1478ee7cae30`,
    });
  };

  static updateTemplateReportById = (data) => {
    return request({
      method: "PUT",
      url: url + `/update/b95b0477-7c5c-41ed-9588-1478ee7cae30`,
      data: data,
    });
  };
}
