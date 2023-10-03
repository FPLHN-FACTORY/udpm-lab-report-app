import { request } from "../../helper/request.helper";

export class RolesAPI {
  static getRolesUser = (idUser) => {
    return request({
      method: "GET",
      url: `/roles?idUser=${idUser}`,
    });
  };
}
