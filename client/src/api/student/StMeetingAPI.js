import { request } from "../../helper/request.helper";
import { sinhVienCurrent } from "../../helper/inForUser";

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
    static updateHomeWorkAndNote = (data) => {
      return request({
        method: "PUT",
        url: url + `/homeword-and-note`,
        data: data,
      });
    };
    static getTeamInMeeting(idClass, idStudent) {
      return request({
        method: "GET",
        url:
        url+
          `/get-team-meeting` +
          `?idClass=` +
          idClass +
        `&idStudent=` +
        sinhVienCurrent.id,
      });
    }
    static getRoleByIdStudent(idStudent) {
      return request({
        method: "GET",
        url: url + `/get-role?idStudent=` + idStudent,
      });
    }
}
