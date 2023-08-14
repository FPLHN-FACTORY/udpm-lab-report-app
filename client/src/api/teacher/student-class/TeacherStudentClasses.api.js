import { apiDanhSachSinhVien, request } from "../../../helper/request.helper";

const url = `/teacher/student-classes`;
export class TeacherStudentClassesAPI {
  static getAllInforStudent(request) {
    return apiDanhSachSinhVien({
      method: "GET",
      url: `` + request,
    });
  }

  static getStudentInClasses(id) {
    return request({
      method: "GET",
      url: url + `?idClass=` + id,
    });
  }

  static getStudentByIdClassAndIdTeam(data) {
    return request({
      method: "GET",
      url: url + `/team?idClass=` + data.idClass + `&idTeam=` + data.idTeam,
    });
  }
}
