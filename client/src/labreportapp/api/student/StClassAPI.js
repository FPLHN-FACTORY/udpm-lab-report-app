import { request } from "../../helper/request.helper";

export class StClassAPI {

  static getClassByCriteriaAndIsActive(filter) {
    return request({
      method: "GET",
      url: "/student/class",
      params: {
        semesterId: filter.semesterId,
        activityId: filter.activityId,
        code: filter.code,
        classPeriod: filter.classPeriod,
        level: filter.level,
      },
    });
  }

  static studentJoinClass(filter) {
    return request({
      method: "POST",
      url: "/student/class/join",
      params: {
        idStudent: filter.idStudent,
        idClass: filter.idClass,
      },
    });
  }
}
