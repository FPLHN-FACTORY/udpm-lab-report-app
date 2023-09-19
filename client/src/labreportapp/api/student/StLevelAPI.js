import { request } from "../../helper/request.helper";

export class StLevelAPI {
  static getLevelAll() {
    return request({
      method: "GET",
      url: "/student/my-class/level",
    });
  }
}
