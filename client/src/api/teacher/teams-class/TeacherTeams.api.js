import { apiDanhSachSinhVien, request } from "../../../helper/request.helper";

const url = `/teacher/teams`;

export class TeacherTeamsAPI {
  static getTeamsByIdClass(id) {
    return request({
      method: "GET",
      url: url + `?idClass=` + id,
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
}
