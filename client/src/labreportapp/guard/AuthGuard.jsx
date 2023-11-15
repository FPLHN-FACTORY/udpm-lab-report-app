import { useEffect } from "react";
import Cookies from "js-cookie";
import { portIdentity } from "../helper/constants";
import jwt_decode from "jwt-decode";
import { useAppDispatch } from "../app/hook";
import { SetUserCurrent } from "../app/common/UserCurrent.reducer";
import { RolesAPI } from "../api/common/RolesAPI";

const AuthGuard = ({ children }) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    const token = Cookies.get("token");
    window.scrollTo(0, 0);

    if (token == null || token === undefined) {
      window.location.href = portIdentity;
      return null;
    } else {
      const decodedToken = jwt_decode(token);
      dispatch(SetUserCurrent(decodedToken));
      Cookies.set("userCurrent", JSON.stringify(decodedToken), {
        expires: 365,
      });
      getRolesUser(decodedToken);
    }
  }, []);

  const getRolesUser = (decodedToken) => {
    RolesAPI.getRolesUser(decodedToken.id).then(
      (response) => {
        return children;
      },
      (error) => {
        return null;
      }
    );
  };

  return children;
};

export default AuthGuard;
