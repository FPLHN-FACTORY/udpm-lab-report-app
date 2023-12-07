import { request } from "../../helper/request.helper";

const url = `/admin/meeting-request`;
export class AdMeetingRequestAPI {
  static getAllClassHaveMeetingRequest(filter) {
    return request({
      method: "GET",
      url:
        url +
        `/get-all-class-have-meeting-request` +
        `?idTeacher=` +
        filter.idTeacher +
        `&idSemester=` +
        filter.idSemester +
        `&idActivity=` +
        filter.idActivity +
        `&code=` +
        filter.code +
        `&classPeriod=` +
        filter.classPeriod +
        `&page=` +
        filter.page +
        `&size=` +
        filter.size +
        "&levelId=",
    });
  }

  static countClassHaveMeetingRequest() {
    return request({
      method: "GET",
      url: url + `/count-class-have-meeting-request`,
    });
  }

  static getAllMeetingRequestByIdClass(id) {
    return request({
      method: "GET",
      url: url + `/get-all-meeting-request-by-id-class?id=` + id,
    });
  }

  static approveMeetingRequest(listIdMeetingRequest) {
    return request({
      method: "POST",
      url: url + `/approve-meeting-request`,
      data: listIdMeetingRequest,
    });
  }

  static noApproveMeetingRequest(listIdMeetingRequest) {
    return request({
      method: "POST",
      url: url + `/no-approve-meeting-request`,
      data: listIdMeetingRequest,
    });
  }

  static approveClass(listIdClass) {
    return request({
      method: "POST",
      url: url + `/approve-class`,
      data: listIdClass,
    });
  }

  static noApproveClass(listIdClass) {
    return request({
      method: "POST",
      url: url + `/no-approve-class`,
      data: listIdClass,
    });
  }

  static addReasons(idClass, reasons) {
    return request({
      method: "PUT",
      url: url + `/add-reasons?idClass=` + idClass + `&reasons=` + reasons,
    });
  }

  static addReasonsClass(data) {
    return request({
      method: "PUT",
      url: url + `/add-reasons-class`,
      data: data,
    });
  }

  static dowloadLog = (idSemester) => {
    return request({
      method: "GET",
      url: url + `/download-log?idSemester=${idSemester}`,
      responseType: "blob",
    });
  };

  static showHistory = (filter) => {
    return request({
      method: "GET",
      url: url + `/history?page=${filter.page}&size=${filter.size}&idSemester=${filter.idSemester}`,
    });
  };
}
