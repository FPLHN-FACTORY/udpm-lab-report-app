import { request } from "../../helper/request.helper";

export class AdClassCongigurationAPI {
  static getAll = () => {
    return request({
      method: "GET",
      url: `/admin/class-configuration`,
    });
  };

  static update = (data) => {
    return request({
      method: "PUT",
      url: `/admin/class-configuration`,
      data: data,
    });
  };

  static dowloadLog = () => {
    return request({
      method: "GET",
      url: `/admin/class-configuration/download-log`,
      responseType: "blob",
    });
  };

  static showHistory = (params) => {
    return request({
      method: "GET",
      url: `/admin/class-configuration/history`,
      params: params,
    });
  };
}
