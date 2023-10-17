import { request } from "../../helper/request.helper";

const url = `/admin/team`;

export class AdTeamAPI {
  static fetchAllTeam = (filter) => {
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
  static addTeam = (data) => {
    return request({
      method: "POST",
      url: url + `/add`,
      data: data,
    });
  };

  static updateTeam = (data, id) => {
    return request({
      method: "PUT",
      url: url + `/update/` + id,
      data: data,
    });
  };

  static deleteTeam = (id) => {
    return request({
      method: "DELETE",
      url: url + `/delete/` + id,
    });
  };

  
}