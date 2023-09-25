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
    return request({
      method: "GET",
      url: `/admin/teacher`,
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

  static update = (id, data) => {
    return request({
      method: "PUT",
      url: `/admin/class-managerment/update/${id}`,
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
        filter.size +
        "&levelId=" +
        filter.levelId +
        "&classSize=" +
        filter.classSize +
        "&statusClass=" +
        filter.statusClass +
        "&statusTeacherEdit=" +
        filter.statusTeacherEdit,
    });
  }
  static getAdClassDetailById(id) {
    return request({
      method: "GET",
      url: `/admin/class-managerment/information-class/${id}`,
    });
  }
  static getAdStudentClassByIdClass(id) {
    return request({
      method: "GET",
      url: `/admin/student-classes/${id}`,
    });
  }

  static getAllLevel() {
    return request({
      method: "GET",
      url: `/admin/class-managerment/level`,
    });
  }

  static randomClass(data) {
    return request({
      method: "POST",
      url: `/admin/class-managerment/random-class`,
      data: data,
    });
  }

  static exportExcel(filter) {
    return request({
      method: "GET",
      url:
        `/admin/class-managerment/export-excel` +
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
        filter.size +
        "&levelId=" +
        filter.levelId,
      responseType: "blob",
    });
  }
}
