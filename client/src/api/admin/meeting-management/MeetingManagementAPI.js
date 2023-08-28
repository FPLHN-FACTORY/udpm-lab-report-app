import { request } from "../../../helper/request.helper";

export class MeetingManagementAPI {
  static getAllMeetingByIdClass = (idClass) => {
    return request({
      method: "GET",
      url: "/admin/meeting?idClass=" + idClass,
    });
  };

  static createMeeting = (data) => {
    return request({
      method: "POST",
      url: "/admin/meeting",
      data: data,
    });
  };

  static updateMeeting = (data) => {
    return request({
      method: "PUT",
      url: "/admin/meeting",
      data: data,
    });
  };

  static deleteMeeting = (id) => {
    return request({
      method: "DELETE",
      url: "/admin/meeting?id=" + id,
    });
  };
}
