import axios from "axios";
import { AppConfig } from "../AppConfig";
// import { getToken } from "./userToken";

export const request = axios.create({
  baseURL: AppConfig.apiUrl,
});

export const requestCommon = axios.create({
  baseURL: `https://63ddb6c1f1af41051b085a9b.mockapi.io/member`,
});
export const requestCommonStakeHolder = axios.create({
  baseURL: `https://63ddb6c1f1af41051b085a9b.mockapi.io/user`,
});
export const requestCommonTeacher = axios.create({
  baseURL: `https://63ddb6cff1af41051b085b6d.mockapi.io/giang-vien`,
});