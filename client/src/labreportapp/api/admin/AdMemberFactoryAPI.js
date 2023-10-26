import { request } from "../../helper/request.helper";

const url = `/admin/member-factory`;

export class AdMemberFactoryAPI {
  static getPage(filter) {
    return request({
      method: "GET",
      url: url,
      params: filter,
    });
  }

  static getRoles() {
    return request({
      method: "GET",
      url: url + "/roles",
    });
  }

  static getTeams() {
    return request({
      method: "GET",
      url: url + "/teams",
    });
  }

  static getNumberMemberFactory() {
    return request({
      method: "GET",
      url: url + "/number-member-factory",
    });
  }

  static addMemberFactory(email) {
    return request({
      method: "POST",
      url: url + "?email=" + email,
    });
  }

  static detailMemberFactory(id) {
    return request({
      method: "GET",
      url: url + "/detail?id=" + id,
    });
  }

  static updateMemberFactory(data) {
    return request({
      method: "PUT",
      url: url,
      data: data,
    });
  }

  static exportTemplateExcel() {
    return request({
      method: "GET",
      url: url + "/export-template-excel",
      responseType: "blob",
    });
  }

  static exportExcel(filter) {
    return request({
      method: "GET",
      url: url + "/export-excel",
      responseType: "blob",
      params: filter,
    });
  }

  static importExcelMemberFactory(formData) {
    return request({
      method: "POST",
      url: url + `/import-excel`,
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  }
}
