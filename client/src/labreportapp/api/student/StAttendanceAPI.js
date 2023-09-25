import { request } from "../../helper/request.helper";

const url = `/student/my-class`;

export class StAttendanceAPI {
  static getAllAttendanceById = (req) => {
    return request({
      method: "GET",
      url:
        url + `/attendance1?idStudent=${req.idStudent}&idClass=${req.idClass}`,
    });
  };
}
