import axios from "axios";
import { AppConfig } from "../AppConfig";
import { toast } from "react-toastify";
// import { getToken } from "./userToken";

export const request = axios.create({
  baseURL: AppConfig.apiUrl,
});

request.interceptors.request.use((config) => {
  const token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjI0MzVjN2Q1LTliZWMtNDVhYy05YmZlLTA4ZGJhODc1MjNmZSIsIm5hbWUiOiJOZ3V54buFbiBDw7RuZyBUaOG6r25nIiwiZW1haWwiOiJ0aGFuZ25jcGgyNjEyM0BmcHQuZWR1LnZuIiwidXNlck5hbWUiOiJ0aGFuZ25jcGgyNjEyMyIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQWNIVHRmclNnaTJDTDZtSHNRZEQtWl94ZTdRak01RU51eDBucjBEaXRDVnRYMXdiM009czk2LWMiLCJpZFRyYWluaW5nRmFjaWxpdHkiOiI3OTZhNGZhNC04YWFiLTQyYzQtOWYzNS04NzBiYjAwMDVhZjEiLCJsb2NhbEhvc3QiOiJodHRwOi8vbG9jYWxob3N0OjA5MjUiLCJyb2xlIjoiU1RVREVOVCIsInJvbGVOYW1lcyI6IlNpbmggdmnDqm4iLCJuYmYiOjE2OTQ5MzM3NzIsImV4cCI6MTY5NzUyNTc3MiwiaWF0IjoxNjk0OTMzNzcyLCJpc3MiOiJodHRwczovL2xvY2FsaG9zdDo0OTA1MyIsImF1ZCI6Imh0dHBzOi8vbG9jYWxob3N0OjQ5MDUzIn0.P5h3jBHKVxbMxMZ36R413gDrnGtGsVDBLSS-PDQiT6Y";
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
    if (error.response && error.response.status === 400) {
      toast.error(error.response.data.message);
    }
    if (error.response && error.response.status === 404) {
      window.location.href = "/not-found";
    }
    throw error;
  }
);
