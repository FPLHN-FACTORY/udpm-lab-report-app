import { request } from "../../helper/request.helper";

const url = `/admin/level`;
export class AdLevelAPI {
  static fetchAllLevel = (filter) => {
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
  static addLevel = (data) => {
    return request({
      method: "POST",
      url: url + `/add`,
      data: data,
    });
  };

  static updateLevel = (data, id) => {
    return request({
      method: "PUT",
      url: url + `/update/` + id,
      data: data,
    });
  };

  static deleteLevel = (id) => {
    return request({
      method: "DELETE",
      url: url + `/delete/` + id,
    });
  };
  
}