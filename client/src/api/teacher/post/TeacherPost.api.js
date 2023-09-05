import { request } from "../../../helper/request.helper";
const url = `/teacher/post`;

export class TeacherPostAPI {
  static getPagePost(data) {
    return request({
      method: "GET",
      url:
        url +
        `?idClass=` +
        data.idClass +
        `&idTeacher=` +
        data.idTeacher +
        `&page=` +
        data.page +
        `&size=` +
        data.size,
    });
  }

  static create(data) {
    return request({
      method: "POST",
      url: url,
      data: data,
    });
  }

  static update(data) {
    return request({
      method: "PUT",
      url: url,
      data: data,
    });
  }

  static delete(idPost) {
    return request({
      method: "DELETE",
      url: url + `/` + idPost,
    });
  }
}
