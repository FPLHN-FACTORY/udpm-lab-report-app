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

  static detailTeam = (id) => {
    return request({
      method: "GET",
      url: url + `/detail/` + id,
    });
  };

  static detailMemberTeamFactory = (id) => {
    return request({
      method: "GET",
      url: url + `/member-team-factory/` + id,
    });
  };

  static getAllMemberFactory = () => {
    return request({
      method: "GET",
      url: url + `/all-member-factory`,
    });
  };

  static addMembers = (data) => {
    return request({
      method: "POST",
      url: url + `/add-members`,
      data: data,
    });
  };

  static deleteMemberTeamFactory = (id) => {
    return request({
      method: "DELETE",
      url: url + `/delete-member-team-factory/${id}`,
    });
  };

  static deleteListMemberTeamFactory = (data) => {
    return request({
      method: "DELETE",
      url: url + `/delete-list-member-team-factory`,
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
