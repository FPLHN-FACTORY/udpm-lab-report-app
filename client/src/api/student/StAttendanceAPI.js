import { request } from "../../helper/request.helper";

const url = `/student/my-class`;

export class StAttendanceAPI {
  static getAllAttendanceById = (id) => {
    return request({
      method: "GET",
      url: url + `/attendance?id=${id}`,
    });
  };
}
