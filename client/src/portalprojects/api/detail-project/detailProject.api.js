import { request } from "../../../labreportapp/helper/request.helper";

export class DetailProjectAPI {
  static findProjectById = (idProject) => {
    return request({
      method: "GET",
      url: `/member/project/detail-project/` + idProject,
    });
  };

  static findAllPeriodProject = (idProject) => {
    return request({
      method: "GET",
      url: `/member/period/` + idProject,
    });
  };

  static fetchAllBoard = (filter) => {
    return request({
      method: "POST",
      url: "/member/todo",
      data: filter,
    });
  };

  static fetchAllLabelProject = (idProject) => {
    return request({
      method: "GET",
      url: `/member/label/list?idProject=` + idProject,
    });
  };

  static fetchAllNotification = (memberId, page) => {
    return request({
      method: "GET",
      url: `/member/notification-member?memberId=` + memberId + "&page=" + page,
    });
  };

  static countNotification = (memberId) => {
    return request({
      method: "GET",
      url: `/member/notification-member/count?memberId=` + memberId,
    });
  };

  static updateStatusNotification = (idNotificationMember) => {
    return request({
      method: "PUT",
      url:
        `/member/notification-member/update-status?idNotificationMember=` +
        idNotificationMember,
    });
  };

  static updateAllStatusNotification = (memberId) => {
    return request({
      method: "PUT",
      url: `/member/notification-member/update-all-status?memberId=` + memberId,
    });
  };

  static createNotification = (obj) => {
    return request({
      method: "POST",
      url: `/member/notification`,
      data: obj,
    });
  };

  static getAllMemberTeam = (idProject) => {
    return request({
      method: "GET",
      url: `/member/member-project/get-all-member-team/` + idProject,
    });
  };

  static getAllRoleProject = (idProject) => {
    return request({
      method: "GET",
      url: `/member/role-project?idProject=` + idProject,
    });
  };

  static createRoleProject = (data) => {
    return request({
      method: "POST",
      url: `/member/role-project`,
      data: data,
    });
  };

  static updateRoleProject = (data) => {
    return request({
      method: "PUT",
      url: `/member/role-project`,
      data: data,
    });
  };

  static deleteRoleProject = (id) => {
    return request({
      method: "DELETE",
      url: `/member/role-project?id=` + id,
    });
  };
}
