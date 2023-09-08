import axios from "axios";
import { AppConfig } from "../AppConfig";
import { toast } from "react-toastify";
// import { getToken } from "./userToken";

// request.interceptors.request.use((config) => {
//   const token = getToken();
//   if (token) {
//     config.headers.Authorization = `Bearer ${token}`;
//   }
//   return config;
// });

export const request = axios.create({
  baseURL: AppConfig.apiUrl,
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
    if (error.response && error.response.status === 400) {
      toast.error(error.response.data.message);
    }
    if (error.response && error.response.status === 404) {
      window.location.href = "/not-found";
    }
    throw error;
  }
);
