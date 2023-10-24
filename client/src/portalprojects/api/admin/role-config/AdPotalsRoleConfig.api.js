import { request } from "../../../../labreportapp/helper/request.helper";

const url = "/admin/potals/role-config";
export class AdPotalsRoleConfigAPI {
  static getAllRoleConfig = () => {
    return request({
      method: "GET",
      url: url,
    });
  };
}
