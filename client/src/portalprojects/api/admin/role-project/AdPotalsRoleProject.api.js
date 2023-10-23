import { request } from "../../../../labreportapp/helper/request.helper";

const url = "/admin/potals/role-project";
export class AdPotalsRoleProjectAPI {
  static getAllRoleProjectByProjId = (idProject) => {
    return request({
      method: "GET",
      url: url + "/project/" + idProject,
    });
  };
}
