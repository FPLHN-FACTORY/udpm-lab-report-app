import axios from "axios";
import { AppConfig } from "../AppConfig";
// import { getToken } from "./userToken";

export const request = axios.create({
  baseURL: AppConfig.apiUrl,
});
