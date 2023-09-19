import { sinhVienCurrent } from "../../helper/inForUser";
import { apiDanhSachGiangVien, request } from "../../helper/request.helper";

export class StMyTeamClassAPI {
  static getTeamInClass(idClass) {
    return request({
      method: "GET",
      url: `/student/my-class/team/get-team-in-class?idClass=` + idClass,
    });
  }

  static getStudentInMyTeam(idClass, idTeam) {
    return request({
      method: "GET",
      url:
        `/student/my-class/team/get-student-in-my-team` +
        `?idClass=` +
        idClass +
        `&idTeam=` +
        idTeam,
    });
  }

  static checkStatusStudentInClass(idClass) {
    return request({
      method: "GET",
      url:
        `/student/my-class/team/check-status` +
        `?idClass=` +
        idClass +
        `&idStudent=` +
        sinhVienCurrent.id,
    });
  }

  static detailClass(idClass) {
    return request({
      method: "GET",
      url: `/student/my-class/team/detail?idClass=` + idClass,
    });
  }

  static detailTeam(id) {
    return request({
      method: "GET",
      url: `/student/my-class/team/detail-team?idTeam=` + id,
    });
  }

  static joinTeam(data) {
    return request({
      method: "PUT",
      url: `/student/my-class/team/join-team`,
      data: data,
    });
  }

  static outTeam(data) {
    return request({
      method: "PUT",
      url: `/student/my-class/team/out-team`,
      data: data,
    });
  }
}
