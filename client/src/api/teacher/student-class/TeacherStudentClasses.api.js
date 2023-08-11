import { apiDanhSachSinhVien, request } from "../../../helper/request.helper";

const url = `/teacher/student-classes`;
// truy xuất nhiều bằng cách https://63ddb6cff1af41051b085b6d.mockapi.io/sinh-vien?id= jdajjsajdsaj | ádadad
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
}
