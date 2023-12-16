import axios from "axios";
import { AppConfig } from "../../AppConfig";
import Cookies from "js-cookie";
import { dispatch } from "../app/store";
import { message } from "antd";
import { SetLoadingFalse } from "../app/common/Loading.reducer";
import { useNavigate } from "react-router";

export const request = axios.create({
  baseURL: AppConfig.apiUrl,
});

request.interceptors.request.use((config) => {
  const token = Cookies.get("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

request.interceptors.response.use(
  (response) => response,
  (error) => {
    console.log(error);
    dispatch(SetLoadingFalse());
    if (error.response && error.response.status === 401) {
      // window.location.href = "/not-authorization";
    }
    if (error.response && error.response.status === 404) {
      if (error.response.data === "") {
        message.error("File log không tồn tại");
      } else {
        window.location.href = "/not-found";
      }
    }
    if (error.response && error.response.status === 403) {
      window.location.href = "/forbidden";
    }
    if (error.response && error.response.status === 406) {
      window.location.href = "/not-aceptable/status=" + error.response.data;
    }
    if (error.response && error.response.status === 400) {
      message.error(error.response.data.message);
    }
    if (error.response && error.response.status === 500) {
      message.error(error.response.data.message);
      // window.location.href = "/error";
    }
    throw error;
  }
);
