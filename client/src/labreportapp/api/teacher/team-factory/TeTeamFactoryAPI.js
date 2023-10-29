import { request } from "../../../helper/request.helper";

const url = `/teacher/team-factory`;

export class TeTeamFactoryAPI {
  static fetchAllTeam = (filter) => {
    return request({
      method: "GET",
      url:
        url +
        `/search?name=` +
        filter.name +
        `&page=` +
        filter.page +
        `&size=` +
        filter.size,
    });
  };

  static detailTeam = (id) => {
    return request({
      method: "GET",
      url: url + `/detail/` + id,
    });
  };

  static detailMemberTeamFactory = (id) => {
    return request({
      method: "GET",
      url: url + `/member-team-factory/` + id,
    });
  };

  static getAllMemberFactory = () => {
    return request({
      method: "GET",
      url: url + `/all-member-factory`,
    });
  };

}
