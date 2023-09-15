import { request } from "../../../helper/request.helper";
const url = `/teacher/meeting`;

export class TeacherMeetingAPI {
  static countMeetingByIdClass(idClass) {
    return request({
      method: "GET",
      url: url + `/count?idClass=` + idClass,
    });
  }
  static getAllMeetingByIdClass(idClass) {
    return request({
      method: "GET",
      url: url + `?idClass=` + idClass,
    });
  }
  static getDetailByIdMeeting(idMeeting) {
    return request({
      method: "GET",
      url: url + `/detail?idMeeting=` + idMeeting,
    });
  }
  static getAndCheckMeetingById(idMeeting) {
    return request({
      method: "GET",
      url: url + `/detail-attendance?idMeeting=` + idMeeting,
    });
  }
  static getDetailHomeWorkAndNoteByIdMeetingandIdTeam(data) {
    return request({
      method: "GET",
      url:
        url + `/hw-note?idMeeting=` + data.idMeeting + `&idTeam=` + data.idTeam,
    });
  }
  static getColumnMeetingByIdClass(idClass) {
    return request({
      method: "GET",
      url: url + `/column-attendance/` + idClass,
    });
  }
}
