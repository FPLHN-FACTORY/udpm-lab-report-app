import React from "react";
import { Result, Button } from "antd";
import { useNavigate } from "react-router-dom"; // Sử dụng nếu bạn muốn điều hướng trang

function Forbidden() {
  const navigate = useNavigate();

  const handleGoBack = () => {
    navigate("/");
  };

  return (
    <Result
      status="403"
      title="Forbidden"
      subTitle="Xin lỗi, bạn không được phép truy cập trang này."
      extra={
        <Button type="primary" onClick={handleGoBack}>
          Về trang chủ
        </Button>
      }
    />
  );
}

export default Forbidden;
