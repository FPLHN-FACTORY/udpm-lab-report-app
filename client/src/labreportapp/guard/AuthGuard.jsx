import { useEffect } from "react";
import Cookies from "js-cookie";
import { portIdentity } from "../helper/constants";
import jwt_decode from "jwt-decode";
import { useAppDispatch, useAppSelector } from "../app/hook";
import {
  GetUserCurrent,
  SetUserCurrent,
} from "../app/common/UserCurrent.reducer";

const AuthGuard = ({ children }) => {
  const dispatch = useAppDispatch();
  useEffect(() => {
    const token = Cookies.get("token");
    window.scrollTo(0, 0);
    if (token == null) {
      window.location.href = portIdentity;
      return null;
    } else {
      const decodedToken = jwt_decode(token);
      dispatch(SetUserCurrent(decodedToken));
      Cookies.set("userCurrent", JSON.stringify(decodedToken), {
        expires: 365,
      });
    }
  }, [children]);

  return children;
};

export default AuthGuard;
