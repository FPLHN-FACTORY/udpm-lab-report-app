import { request } from "../../helper/request.helper";
const url = `/student/meeting`;
export class StudentMeetingAPI {
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
    static getDetailHomeWorkAndNoteByIdMeetingandIdTeam(data) {
      return request({
        method: "GET",
        url:
          url + `/homeword-and-note?idMeeting=` + data.idMeeting + `&idTeam=` + data.idTeam,
      });
    }
}
