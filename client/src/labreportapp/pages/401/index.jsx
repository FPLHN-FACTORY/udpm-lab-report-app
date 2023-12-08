import React from "react";
import { Result, Button } from "antd";
import { Link, useNavigate } from "react-router-dom"; // Sử dụng nếu bạn muốn điều hướng trang
import { portIdentity } from "../../helper/constants";

function NotAuthorized() {
  const navigate = useNavigate();

  const handleGoBack = () => {
    navigate("/");
  };

  return (
    <Result
      status="403"
      title="Not Authorized"
      subTitle="Xin lỗi, bạn không được phép truy cập trang này."
      extra={
        <>
          <Button
            type="primary"
            style={{ marginLeft: 7 }}
            onClick={handleGoBack}
          >
            Về trang chủ
          </Button>
          <Link to={portIdentity}>
            <Button type="primary">Đăng nhập lại</Button>
          </Link>
        </>
      }
    />
  );
}

export default NotAuthorized;
