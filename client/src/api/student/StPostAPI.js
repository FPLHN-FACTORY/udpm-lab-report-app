import { request } from "../../helper/request.helper";
const url = `/student/post`;

export class StudentPostAPI {
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
}