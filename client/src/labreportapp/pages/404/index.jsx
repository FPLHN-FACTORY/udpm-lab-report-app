import React from "react";
import { Result, Button } from "antd";
import { Link } from "react-router-dom"; // Sử dụng nếu bạn muốn sử dụng đường dẫn liên kết

function NotFound() {
  return (
    <Result
      status="404"
      title="Not Found"
      subTitle="Xin lỗi, trang bạn đang tìm kiếm không tồn tại."
      extra={
        <Link to="/">
          <Button type="primary">Về trang chủ</Button>
        </Link>
      }
    />
  );
}

export default NotFound;
