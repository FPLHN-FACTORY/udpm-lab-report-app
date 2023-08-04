import axios from "axios";
import { AppConfig } from "../AppConfig";
// import { getToken } from "./userToken";

export const request = axios.create({
  baseURL: AppConfig.apiUrl,
});

export const apiDanhSachSinhVien = axios.create({
  baseURL: `https://63ddb6cff1af41051b085b6d.mockapi.io/sinh-vien`,
});

export const apiDanhSachGiangVien = axios.create({
  baseURL: `https://63ddb6cff1af41051b085b6d.mockapi.io/giang-vien`,
});