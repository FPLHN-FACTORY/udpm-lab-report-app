import React from "react";
import { Result, Button } from "antd";
import { useNavigate } from "react-router-dom"; // Sử dụng nếu bạn muốn điều hướng trang

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
        <Button type="primary" onClick={handleGoBack}>
          Về trang chủ
        </Button>
      }
    />
  );
}

export default NotAuthorized;
