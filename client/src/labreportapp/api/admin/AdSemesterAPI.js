import { request } from "../../helper/request.helper";

const url = `/admin/semester`;
export class AdSemesterAPI {
  static fetchAllSemester = (filter) => {
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

  static addSemester = (data) => {
    return request({
      method: "POST",
      url: url + `/add`,
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

  static deleteSemester = (id) => {
    return request({
      method: "DELETE",
      url: url + `/delete/` + id,
    });
  };
  
  static updateStatusFeedback = (id) => {
    return request({
      method: "PUT",
      url: url + `/update-status-feedback/` + id,
    });
  };

  static getAllSemesters() {
    return request({
      method: "GET",
      url: url + `/get-all-semesters`,
    });
  }
}
