import { request } from "../../../../helper/request.helper";
const url = `/teacher/meeting/schedule`;

export class TeacherScheduleTodayAPI {
  static getAllByIdTe = (idTeacher) => {
    return request({
      method: "GET",
      url: url + `?idTeacher=` + idTeacher,
    });
  };
  static updateDescriptionMeeting = (data) => {
    return request({
      method: "PUT",
      url: url,
      data: data,
    });
  };
}
