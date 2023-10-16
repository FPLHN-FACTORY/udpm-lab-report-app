import { request } from "../../helper/request.helper";

const url = `/admin/group-project`;

export class AdGroupProjectAPI {
  static getAllGroupProject(filter) {
    return request({
      method: "GET",
      url: url,
      params: filter,
    });
  }

  static updateGroupProject(data) {
    const formData = new FormData();
    formData.append("id", data.id);
    formData.append("name", data.name);
    formData.append("descriptions", data.descriptions);
    if (typeof data.file !== "undefined" && !Array.isArray(data.file)) {
      formData.append("file", data.file);
    }

    return request({
      method: "PUT",
      url: url,
      data: formData,
    });
  }

  static createGroupProject(data) {
    const formData = new FormData();
    formData.append("name", data.name);
    formData.append("descriptions", data.descriptions);
    formData.append("file", data.file);
    return request({
      method: "POST",
      url: url,
      data: formData,
    });
  }
}
