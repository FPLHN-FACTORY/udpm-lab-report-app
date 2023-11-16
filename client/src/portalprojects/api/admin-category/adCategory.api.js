import { request } from "../../../labreportapp/helper/request.helper";

export class AdCategoryAPI {
  static fetchAllCategory = (name, page) => {
    return request({
      method: "GET",
      url:
        `/admin/category/search` +
        "?name=" +
        name +
        "&page=" +
        page +
        "&size=10",
    });
  };

  static create = (data) => {
    return request({
      method: "POST",
      url: `/admin/category/add`,
      data: data,
    });
  };

  static detail = (idCate) => {
    return request({
      method: "GET",
      url: `/admin/category/detail/` + idCate,
    });
  };

  static update = (idCate, data) => {
    return request({
      method: "PUT",
      url: `/admin/category/` + idCate,
      data: data,
    });
  };

  static deleteCategoryId = (idCategory) => {
    return request({
      method: "DELETE",
      url: `/admin/category/` + idCategory,
    });
  };

  
  static dowloadLog = () => {
    return request({
      method: "GET",
      url: `/admin/category/download-log`,
      responseType: "blob",
    });
  };

  static showHistory = (params) => {
    return request({
      method: "GET",
      url:  `/admin/category/history`,
      params: params,
    });
  };
}
