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
}
