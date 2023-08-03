import {
  BellOutlined,
  BulbOutlined,
  PoweroffOutlined,
} from "@ant-design/icons";
import { Badge, Dropdown, Menu, Switch } from "antd";
import { Link } from "react-router-dom";
import AvtDefault from "../../assets/img/328693761_727939795557043_1972102579202651860_n.jpg";
import { useState } from "react";

const CommonInforUser = () => {
  const handleMenuClick = (e) => {
    if (e.key === "3") {
      console.log("Logout clicked");
    }
  };

  const userMenu = (
    <Menu onClick={handleMenuClick}>
      <Menu.Item key="2">Thông tin người dùng</Menu.Item>
      <Menu.Item key="3">Đăng xuất</Menu.Item>
    </Menu>
  );

  const [darkMode, setDarkMode] = useState(true);

  const toggleDarkMode = (checked) => {
    setDarkMode(checked);
    // const elements = document.querySelectorAll("*");
    // if (checked) {
    //   elements.forEach((element) => {
    //     element.classList.add("dark-mode");
    //     element.classList.remove("light-mode");
    //   });
    // } else {
    //   elements.forEach((element) => {
    //     element.classList.remove("dark-mode");
    //     element.classList.add("light-mode");
    //   });
    // }
  };

  return (
    <>
      <Badge>
        <BellOutlined
          style={{ fontSize: 20, marginRight: 16 }}
          className="box_notification"
        />
      </Badge>
      <div style={{ marginRight: 16 }}>
        <Switch
          checkedChildren={<BulbOutlined />}
          unCheckedChildren={<PoweroffOutlined />}
          checked={darkMode}
          onChange={toggleDarkMode}
        />
      </div>
      <Dropdown overlay={userMenu} trigger={["click"]}>
        <Link
          to="/user"
          className="ant-dropdown-link"
          onClick={(e) => e.preventDefault()}
          style={{ display: "flex", alignItems: "center" }}
        >
          <img className="img_avatar" src={AvtDefault} alt="User Avatar" />
          <span
            className="span-name-usercurrent"
            style={{ marginLeft: 8, fontWeight: 500, color: "#4f4f4f" }}
          >
            Nguyễn Công Thắng
          </span>
        </Link>
      </Dropdown>
    </>
  );
};

export default CommonInforUser;
