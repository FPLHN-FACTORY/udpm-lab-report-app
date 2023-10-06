import { request } from "../../../helper/request.helper";

const url = `/teacher/teams`;

export class TeacherTeamsAPI {
  static getTeamsByIdClass(idClass, idMeeting) {
    return request({
      method: "GET",
      url: url + `?idClass=` + idClass + `&idMeeting=` + idMeeting,
    });
  }
  static createProjectToTeam(data) {
    return request({
      method: "PUT",
      url: url + `/create-project`,
      data: data,
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
