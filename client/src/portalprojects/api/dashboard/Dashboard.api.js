import { request } from "../../../labreportapp/helper/request.helper";

export class DashboardApi {
  static fetchAllTodoList = (idProject) => {
    return request({
      method: "GET",
      url: `/member/todo-list/` + idProject,
    });
  };

  static detailPeriod = (idPeriod) => {
    return request({
      method: "GET",
      url: `/member/period/detail/` + idPeriod,
    });
  };

  static dashboardAll = (projectId, idPeriod) => {
    return request({
      method: "GET",
      url: `/member/todo/dashboard-all?projectId=${projectId}&periodId=${idPeriod}`,
    });
  };

  static getAllTodoTypeWork = (projectId, idPeriod) => {
    return request({
      method: "GET",
      url: `/member/todo/get-all-todo-type-work?projectId=${projectId}&periodId=${idPeriod}`,
    });
  };

  static getAllTodoComplete = (projectId, idPeriod) => {
    return request({
      method: "GET",
      url: `/member/todo/get-all-todo-complete?projectId=${projectId}&periodId=${idPeriod}`,
    });
  };
}
