import { request } from "../../helper/request.helper";
import { apiDanhSachSinhVien } from "../../helper/request.helper";

export class StMyTeamClassAPI {

    static getAllTeamMyClass(filter) {
        return request({
          method: "GET",
          url:
          `/student/my-class/team` +
            `?idClass=` +
            filter.idClass +
            `&idStudent=` +
            filter.idStudent,
        });
      }
      static getAllMyTeam(filter) {
        return request({
          method: "GET",
          url:
          `/student/my-class/team/my-person` +
            `?idClass=` +
            filter.idClass +
            `&idTeam=` +
            filter.idTeam,
        });
      }
      static fetchAllStudent = () => {
        return apiDanhSachSinhVien({
          method: "GET",
          url: ``,
        });
      };

      static getAllTeamByStNotJoin(filter) {
        return request({
          method: "GET",
          url:
          `/student/my-class/team/my-person-not-join-team` +
            `?idClass=` +
            filter.idClass +
            `&idStudent=` +
            filter.idStudent,
        });
      }

}
