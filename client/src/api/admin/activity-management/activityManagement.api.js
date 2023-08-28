import { request } from "../../../helper/request.helper";

export class ActivityManagementAPI {

    static fetchAll = (filter) => {
        return request({
            method: "GET",
            url: '/admin/activity',
            params: filter,
        });
    };

    static create = (data) => {
        return request({
            method: "POST",
            url: '/admin/activity',
            data: data,
        });
    };

    static update = (data) => {
        return request({
            method: "PUT",
            url: `/admin/activity/${data.id}`,
            data: data,
        });
    };

    static delete = (id) => {
        return request({
            method: "DELETE",
            url: `/admin/activity/${id}`,
        });
    };

    static semester = () => {
        return request({
            method: "GET",
            url: '/admin/activity/activity-semester',
        })
    }
}