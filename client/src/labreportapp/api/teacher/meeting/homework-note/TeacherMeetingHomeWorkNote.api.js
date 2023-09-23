import { request } from "../../../../helper/request.helper";
const url = `/teacher/meeting`;

export class TeacherMeetingHomeWorkNoteAPI {
  static updateHomeWorkAndNote = (data) => {
    return request({
      method: "PUT",
      url: url + `/hw-note-report`,
      data: data,
    });
  };
}
