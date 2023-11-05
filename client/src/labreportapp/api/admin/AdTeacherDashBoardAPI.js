import { request } from "../../helper/request.helper";

const url = `/admin/teacher/dashboard`;
export class AdTeacherDashBoardAPI {
  static getAllTeacher = (params) => {
    return request({
      method: "GET",
      url: url,
      params: params,
    });
  };
}
