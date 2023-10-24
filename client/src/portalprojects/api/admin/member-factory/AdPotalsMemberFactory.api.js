import { request } from "../../../../labreportapp/helper/request.helper";

const url = "/admin/potals/member-factory";
export class AdPotalsMemberFactoryAPI {
  static getAllMemberActive = () => {
    return request({
      method: "GET",
      url: url,
    });
  };
}
