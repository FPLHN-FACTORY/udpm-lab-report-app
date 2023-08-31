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

  static findGiangVien(idGiangVien) {
    return apiDanhSachGiangVien({
      method: "GET",
      url: `/` + idGiangVien,
    });
  }
}
