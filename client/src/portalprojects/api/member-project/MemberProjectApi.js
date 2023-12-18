import { request } from "../../../labreportapp/helper/request.helper";

export class MemberProjectApi {
  static fetchAllCategory = () => {
    return request({
      method: "GET",
      url: `/member/project/list`,
    });
  };

  static detailUpdate = (id) => {
    return request({
      method: "GET",
      url: `/member/project/detail-update/` + id,
    });
  };

  static getAllGroupToProjectManagement() {
    return request({
      method: "GET",
      url: "/member/project/get-all",
    });
  }
}
