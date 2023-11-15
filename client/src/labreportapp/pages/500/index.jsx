import React from "react";
import { Result, Button } from "antd";
import { Link } from "react-router-dom"; // Sử dụng nếu bạn muốn điều hướng trang
import { portIdentity } from "../../helper/constants";

function Oops() {
  return (
    <Result
      status="500"
      title="Oops"
      extra={
        <Link to={portIdentity}>
          <Button type="primary">Đăng nhập lại</Button>
        </Link>
      }
    />
  );
}

export default Oops;
