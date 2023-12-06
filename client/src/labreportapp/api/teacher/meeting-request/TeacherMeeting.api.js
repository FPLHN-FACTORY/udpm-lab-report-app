import { request } from "../../../helper/request.helper";
const url = `/teacher/meeting-request`;

export class TeacherMeetingRequestAPI {
  static getAllMeetingRequest(data) {
    return request({
      method: "GET",
      url:
        url +
        `?idClass=` +
        data.idClass +
        `&statusMeetingRequest=` +
        data.statusMeetingRequest +
        `&page=` +
        data.page +
        `&size=` +
        data.size,
    });
  }
  static sendMeetingRequestAgain(data) {
    return request({
      method: "PUT",
      url: url + `/sent-again`,
      data: data,
    });
  }
  static showReasons(idClass) {
    return request({
      method: "GET",
      url: url + `/show-reasons?idClass=${idClass}`,
    });
  }

  static updateMeetingRequest(data) {
    return request({
      method: "PUT",
      url: url + `/update`,
      data: data,
    });
  }
}
