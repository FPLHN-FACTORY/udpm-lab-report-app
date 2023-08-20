import { request } from "../../../helper/request.helper";
import { apiDanhSachGiangVien } from "../../../helper/request.helper";

export class ClassAPI {
  static fetchAllClass = () => {
    return request({
      method: "GET",
      url: `/admin/class-managerment`,
    });
  };

  static fetchAllTeacher = () => {
    return apiDanhSachGiangVien({
      method: "GET",
      url: ``,
    });
  };

  static fetchAllByCondition = (code, classPeriod, idTeacher) => {
    return apiDanhSachGiangVien({
      method: "GET",
      url:
        `/admin/class-managerment/find-by-condition` +
        code +
        classPeriod +
        idTeacher,
    });
  };

  static fetchAllSemester = () => {
    return request({
      method: "GET",
      url: `/admin/class-managerment/semester/getAll`,
    });
  };

  static getAllActivityByIdSemester(id) {
    return request({
      method: "GET",
      url: `/admin/class-managerment/id-semester?idSemester=` + id,
    });
  }

  static getAllMyClassByIdSemester(id) {
    return request({
      method: "GET",
      url: `/admin/class-managerment/get-all/id-semester?idSemester=` + id,
    });
  }
  static create = (data) => {
    return request({
      method: "POST",
      url: `/admin/class-managerment/add`,
      data: data,
    });
  };
  static getAllMyClass(filter) {
    return request({
      method: "GET",
      url:
      `/admin/class-managerment/getAllSearch` +
        `?idTeacher=` +
        filter.idTeacher +
        `&idSemester=` +
        filter.idSemester +
        `&idActivity=` +
        filter.idActivity +
        `&code=` +
        filter.code +
        `&classPeriod=` +
        filter.classPeriod +
        `&page=` +
        filter.page +
        `&size=` +
        filter.size,
    });
  }
}
