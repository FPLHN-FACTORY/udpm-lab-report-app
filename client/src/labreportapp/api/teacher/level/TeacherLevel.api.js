import { request } from "../../../helper/request.helper";
const url = `/teacher/level`;

export class TeacherLevelAPI {
  static getAllLevel = () => {
    return request({
      method: "GET",
      url: url,
    });
  };
}
