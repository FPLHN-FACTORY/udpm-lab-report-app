import React from "react";
import { Result, Button } from "antd";
import { Link, useLocation } from "react-router-dom"; // Sử dụng nếu bạn muốn sử dụng đường dẫn liên kết
import { portIdentity } from "../../helper/constants";

function NotAceptable() {
  const location = useLocation();
  const status = location.pathname.split("=")[1];
  let message = "";
  if (status === "session-expired") {
    message = "Phiên đăng nhập hết hạn";
  }
  if (status === "role-has-change") {
    message = "Quyền truy cập của bạn đã bị thay đổi";
  }
  return (
    <Result
      status="404"
      title="Not Aceptable!"
      subTitle={message}
      extra={
        <Link to={portIdentity}>
          <Button type="primary">Đăng nhập lại</Button>
        </Link>
      }
    />
  );
}

export default NotAceptable;
