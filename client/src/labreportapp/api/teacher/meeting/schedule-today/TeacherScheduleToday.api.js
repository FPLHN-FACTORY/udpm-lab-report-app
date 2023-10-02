import { request } from "../../../../helper/request.helper";
const url = `/teacher/meeting/schedule`;

export class TeacherScheduleTodayAPI {
  static getAllByIdTe = () => {
    return request({
      method: "GET",
      url: url ,
    });
  };

  static getAllNowToTimeByIdTeacher = (data) => {
    return request({
      method: "GET",
      url:
        url +
        `-time?time=` +
        data.time +
        `&page=` +
        data.page +
        `&size=` +
        data.size,
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
