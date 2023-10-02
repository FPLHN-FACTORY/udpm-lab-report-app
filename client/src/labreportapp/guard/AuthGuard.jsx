import { useEffect } from "react";

const AuthGuard = ({ children, levels }) => {
  useEffect(() => {
    window.scrollTo(0, 0);
  }, [children]);

  return children;
};

export default AuthGuard;
