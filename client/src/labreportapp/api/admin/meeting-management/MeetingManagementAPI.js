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

  static changeTeacher = (data) => {
    return request({
      method: "POST",
      url: "/admin/meeting/change-teacher",
      data: data,
    });
  };

  static createMeetingAuto = (data) => {
    return request({
      method: "POST",
      url: "/admin/meeting/create-meeting-auto",
      data: data,
    });
  };

  static detailMeeting = (id) => {
    return request({
      method: "GET",
      url: "/admin/meeting/detail?id=" + id,
    });
  };

  static getAttendanceByIdMeeting = (idMeeting, idClass) => {
    return request({
      method: "GET",
      url: "/admin/attendance?idMeeting=" + idMeeting + "&idClass=" + idClass,
    });
  };

  static updateAttendance = (data) => {
    return request({
      method: "POST",
      url: "/admin/attendance",
      data: data,
    });
  };
}
