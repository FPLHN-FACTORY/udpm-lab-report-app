import { request } from "../../helper/request.helper";

const url = `/admin/type-project`;
export class AdTypeProjectAPI {
  static fetchAllTypeProject = (filter) => {
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
  static addTypeProject = (data) => {
    return request({
      method: "POST",
      url: url + `/add`,
      data: data,
    });
  };

  static updateTypeProject = (data, id) => {
    return request({
      method: "PUT",
      url: url + `/update/` + id,
      data: data,
    });
  };

  static deleteTypeProject = (id) => {
    return request({
      method: "DELETE",
      url: url + `/delete/` + id,
    });
  };
  
}