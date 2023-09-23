import { request } from "../../helper/request.helper";

const url = `/admin/template-report`;
export class AdTemplateReportAPI {
  static fetchTemplateReportById = () => {
    return request({
      method: "GET",
      url: url + `/36b8467g-b20e-4k60-a3f2-02124d23cgke`,
    });
  };

  static updateTemplateReportById = (data) => {
    return request({
      method: "PUT",
      url: url + `/update/36b8467g-b20e-4k60-a3f2-02124d23cgke`,
      data: data,
    });
  };
}
