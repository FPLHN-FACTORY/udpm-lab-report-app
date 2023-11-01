import { request } from "../../../helper/request.helper";
const url = `/teacher/feedback`;

export class TeacherFeedbackAPI {
  static getAllFeedbackByIdClass = (idClass) => {
    return request({
      method: "GET",
      url: url + `/filter-class`,
      params: {
        idClass: idClass,
      },
    });
  };
}
