import { request } from "../../helper/request.helper";

const url = `/student/member-factory`;

export class StMemberFactoryAPI {
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

}
