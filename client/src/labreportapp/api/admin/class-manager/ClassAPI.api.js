import { request } from "../../../helper/request.helper";

const url = `/admin/class-managerment`;
export class ClassAPI {
  static kickStudentClassesToClass(data) {
    return request({
      method: "PUT",
      url: `/admin/class-managerment` + `/kick-st`,
      data: data,
    });
  }

  static sentStudentClassesToClass(data) {
    return request({
      method: "PUT",
      url: `/admin/class-managerment` + `/sent-st`,
      data: data,
    });
  }

  static getClassSentStudent(filter) {
    return request({
      method: "GET",
      url:
        url +
        `/class-sent` +
        `?idSemester=` +
        filter.idSemester +
        `&idActivity=` +
        filter.idActivity +
        `&idLevel=` +
        filter.idLevel +
        `&idClass=` +
        filter.idClass +
        `&countStudent=` +
        filter.countStudent +
        `&page=` +
        filter.page +
        `&size=` +
        filter.size,
    });
  }

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
        filter.levelId +
        "&classSize=" +
        filter.classSize +
        "&statusClass=" +
        filter.statusClass +
        "&statusTeacherEdit=" +
        filter.statusTeacherEdit,
      responseType: "blob",
    });
  }

  static importExcel = (formData, idSemester) => {
    return request({
      method: "POST",
      url: `/admin/class-managerment/import-excel/` + idSemester,
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  };

  static importExcelStudentsInClass(formData, id) {
    return request({
      method: "POST",
      url: `/admin/student-classes/import-excel/${id}`,
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  }

  static exportExcelStudentsInClass(id, isSample) {
    return request({
      method: "GET",
      url:
        `/admin/student-classes/export-excel/${id}` + `?isSample=${isSample}`,
      responseType: "blob",
    });
  }

  static filterClass(data) {
    return request({
      method: "GET",
      url:
        url +
        `/filter-class` +
        `?idSemester=` +
        data.idSemester +
        `&idActivity=` +
        data.idActivity,
      data: data,
    });
  }

  static showHistoryAll = (params) => {
    return request({
      method: "GET",
      url: url + `/history-all`,
      params: params,
    });
  };

  static dowloadLogAll = (idClass) => {
    return request({
      method: "GET",
      url: url + `/download-log-all?idClass=` + idClass,
      responseType: "blob",
    });
  };

  static getSemesterCurrent = () => {
    return request({
      method: "GET",
      url: url + `/semester-current`,
    });
  };

  static dowloadLogLuong = (idSemester) => {
    return request({
      method: "GET",
      url: url + `/download-log-luong?idSemester=` + idSemester,
      responseType: "blob",
    });
  };

  static showHistoryLogLuong = (filter) => {
    return request({
      method: "GET",
      url: url + `/history-log-luong`,
      params: filter,
    });
  };

  static getPointByIdClass = (idClass) => {
    return request({
      method: "GET",
      url: `/admin/class-managerment/class/get-point/` + idClass,
    });
  };

  static dowloadLog = (idSemester) => {
    return request({
      method: "GET",
      url: `/admin/class-managerment/download-log?idSemester=${idSemester}`,
      responseType: "blob",
    });
  };

  static showHistory = (params) => {
    return request({
      method: "GET",
      url: url + `/history`,
      params: params,
    });
  };
}
