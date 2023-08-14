import { request } from "../../helper/request.helper";

const url = `/admin/semester`;
export class AdSemesterAPI {
  static fetchAllSemester = (name, page) => {
    return request({
      method: "GET",
      url: url +
        "/search" +
        "?name=" +
        name 
        // +
        // "&page=" +
        // page +
        // "&size=10",
    });
  };

  static addSemester = (data) => {
    return request({
      method: "POST",
      url: url + "/add",
      data: data,
    });
  };

  static updateSemester = (data, id) => {
    return request({
      method: "PUT",
      url: url + `/update/` + id,
      data: data,
    });
  };
}
