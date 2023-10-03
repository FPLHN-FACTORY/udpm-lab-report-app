import axios from "axios";
import { AppConfig } from "../../AppConfig";
import { toast } from "react-toastify";
import Cookies from "js-cookie";
import { portIdentity } from "./constants";
// import { getToken } from "./userToken";

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
    if (
      error.response &&
      (error.response.status === 401 || error.response.status === 403)
    ) {
      window.location.href = "/not-authorization";
    }
    if (error.response != null && error.response.status === 400) {
      toast.error(error.response.data.message);
    }
    if (error.response && error.response.status === 500) {
      if (error.response.data.message === "2003") {
        Cookies.remove("token");
        Cookies.remove("userCurrent");
        alert("Quyền của người dùng đã bị thay đổi. Vui lòng đăng nhập lại !");
        window.location.href = portIdentity;
      }
      if (error.response.data.message === "2002") {
        Cookies.remove("token");
        Cookies.remove("userCurrent");
        window.location.href = portIdentity;
      }
    }
    throw error;
  }
);
