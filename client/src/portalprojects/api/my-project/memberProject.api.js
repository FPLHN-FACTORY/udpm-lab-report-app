import { request } from "../../../labreportapp/helper/request.helper";

export class MemberProjectAPI {
  static fetchAll = (idProject) => {
    return request({
      method: "GET",
      url: `/member/member-project/` + idProject,
    });
  };
}
