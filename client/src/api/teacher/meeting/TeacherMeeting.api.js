import { request } from "../../../helper/request.helper";
const url = `/teacher/meeting`;

export class TeacherMeetingAPI {
  static getAllMeetingByIdClassAndIdTeam(data) {
    return request({
      method: "GET",
      url: url + `?idClass=` + data.idClass + `&idTeam=` + data.idTeam,
      //url: `http://localhost:2509/teacher/meeting?idClass=a9c0ce5b-039e-456a-909d-dabec3e7b231&idTeam=3e01b3f8-cedd-4308-9108-0ef43c3f10fa`,
    });
  }
  static getDetailByIdMeeting(idMeeting) {
    return request({
      method: "GET",
      url: url`?idMeeting=` + idMeeting,
    });
  }
}
