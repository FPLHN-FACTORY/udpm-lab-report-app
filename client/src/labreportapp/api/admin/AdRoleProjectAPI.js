import { request } from "../../helper/request.helper";

const url = `/admin/role-config`;

export class AdRoleProjectAPI {
  static fetchAllRoleProject = (filter) => {
    return request({
      method: "GET",
      url:
        url +
        `/search?name=` +
        filter.name +
        `&page=` +
        filter.page +
        `&size=` +
        filter.size,
    });
  };
  static addRoleProject = (data) => {
    return request({
      method: "POST",
      url: url + `/add`,
      data: data,
    });
  };

  static updateRoleProject = (data, id) => {
    return request({
      method: "PUT",
      url: url + `/update/` + id,
      data: data,
    });
  };

  static deleteRoleProject = (id) => {
    return request({
      method: "DELETE",
      url: url + `/delete/` + id,
    });
  };

  
}