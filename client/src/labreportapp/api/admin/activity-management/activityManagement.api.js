import { request } from "../../../helper/request.helper";

export class ActivityManagementAPI {
  static fetchAll = (filter) => {
    return request({
      method: "GET",
      url:
        "/admin/activity?name=" +
        filter.name +
        "&level=" +
        filter.level +
        "&semesterId=" +
        filter.semesterId +
        "&page=" +
        filter.page,
    });
  };

  static create = (data) => {
    return request({
      method: "POST",
      url: "/admin/activity",
      data: data,
    });
  };

  static update = (data) => {
    return request({
      method: "PUT",
      url: `/admin/activity/${data.id}`,
      data: data,
    });
  };

  static delete = (id) => {
    return request({
      method: "DELETE",
      url: `/admin/activity/${id}`,
    });
  };

  static semester = () => {
    return request({
      method: "GET",
      url: "/admin/activity/activity-semester",
    });
  };

  static level = () => {
    return request({
      method: "GET",
      url: "/admin/activity/activity-level",
    });
  };

  static getAllActivityByIdSemester(id) {
    return request({
      method: "GET",
      url: `/admin/activity/id-semester?semesterId=` + id,
    });
  }

  static dowloadLog = () => {
    return request({
      method: "GET",
      url: `/admin/activity/download-log`,
      responseType: "blob",
    });
  };

  static showHistory = (params) => {
    return request({
      method: "GET",
      url: `/admin/activity/history`,
      params: params,
    });
  };
}
