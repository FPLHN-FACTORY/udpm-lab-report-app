import { apiDanhSachSinhVien, request } from "../../../helper/request.helper";

const url = `/teacher/teams`;

export class TeacherTeamsAPI {
  static getTeamsByIdClass(idClass) {
    return request({
      method: "GET",
      url: url + `?idClass=` + idClass,
    });
  }
  static getAllInforStudent(request) {
    return apiDanhSachSinhVien({
      method: "GET",
      url: `` + request,
    });
  }
  static createTeam = (data) => {
    return request({
      method: "POST",
      url: url,
      data: data,
    });
  };
  static updateTeam = (data) => {
    return request({
      method: "PUT",
      url: url,
      data: data,
    });
  };
  static deleteById = (idTeam) => {
    return request({
      method: "DELETE",
      url: url + `/` + idTeam,
    });
  };
}
