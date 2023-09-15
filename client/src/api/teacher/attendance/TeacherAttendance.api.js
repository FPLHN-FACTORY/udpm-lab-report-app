import { request } from "../../../helper/request.helper";
const url = `/teacher/attendance`;

export class TeacherAttendanceAPI {
  static getAttendanceByIdMeeting = (meetingId) => {
    return request({
      method: "GET",
      url: url + `/get/` + meetingId,
    });
  };
  static createOrUpdate = (data) => {
    return request({
      method: "POST",
      url: url,
      data: data,
    });
  };
  static getAllAttendanceByIdClass = (idClass) => {
    return request({
      method: "GET",
      url: url + `/attendance-all/` + idClass,
    });
  };
}
