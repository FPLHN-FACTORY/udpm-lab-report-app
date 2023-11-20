import { request } from "../../../helper/request.helper";
const url = `/teacher/point`;

export class TeacherPointAPI {
  static getPointByIdClass = (idClas) => {
    return request({
      method: "GET",
      url: url + `/get/` + idClas,
    });
  };

  static createOrUpdate = (data) => {
    return request({
      method: "POST",
      url: url,
      data: data,
    });
  };

  static getAllCategory = () => {
    return request({
      method: "GET",
      url: "/teacher/add-honey",
    });
  };

  static addHoney = (idClass, categoryId) => {
    return request({
      method: "POST",
      url: `/teacher/add-honey?idClass=${idClass}&categoryId=${categoryId}`,
    });
  };
}
