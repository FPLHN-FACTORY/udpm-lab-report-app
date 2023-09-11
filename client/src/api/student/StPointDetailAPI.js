import { sinhVienCurrent } from "../../helper/inForUser";
import { request } from "../../helper/request.helper";

export class StPointDetailAPI {
  static getPointDetail(idClass) {
    return request({
      method: "GET",
      url:
        `/student/my-class/point` +
        `?idClass=` +
        idClass +
        `&idStudent=` +
        sinhVienCurrent.id,
    });
  }
}
