import {
  BellOutlined,
  BulbOutlined,
  PoweroffOutlined,
} from "@ant-design/icons";
import { Badge, Dropdown, Menu, Switch } from "antd";
import { Link } from "react-router-dom";
import AvtDefault from "../../assets/img/328693761_727939795557043_1972102579202651860_n.jpg";
import { useEffect, useState } from "react";
import { useAppDispatch, useAppSelector } from "../../app/hook";
import { GetUserCurrent } from "../../app/common/UserCurrent.reducer";
import Cookies from "js-cookie";
import { portIdentity } from "../../helper/constants";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faPerson,
  faPersonSwimming,
  faSignOut,
  faUser,
} from "@fortawesome/free-solid-svg-icons";
import { DetailProjectAPI } from "../../../portalprojects/api/detail-project/detailProject.api";
import {
  GetCountNotification,
  SetCountNotifications,
  SetCurrentPage,
  SetListNotification,
  SetToTalPages,
} from "../../../portalprojects/app/reducer/notification/NotificationSlice.reducer";
import PopupNotification from "../../../portalprojects/component/notification/Notification";

const CommonInforUser = () => {
  const handleMenuClick = (e) => {
    if (e.key === "2") {
      Cookies.remove("token");
      window.location.href = portIdentity;
    }
  };

  const userMenu = (
    <Menu onClick={handleMenuClick}>
      <Menu.Item
        key="1"
        icon={
          <FontAwesomeIcon icon={faPersonSwimming} style={{ marginRight: 5 }} />
        }
      >
        <Link to="/role-selection">Đổi quyền</Link>
      </Menu.Item>
      <Menu.Item
        key="2"
        icon={<FontAwesomeIcon icon={faSignOut} style={{ marginRight: 5 }} />}
      >
        Đăng xuất
      </Menu.Item>
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

  const userCurrent = useAppSelector(GetUserCurrent);

  const [isOpenPopupNotification, setIsOpenPopupNotification] = useState(false);
  const [popupPositionPopupNotification, setPopupPositionPopupNotification] =
    useState({
      top: 0,
      left: 0,
    });

  const openPopupNotification = (event) => {
    const buttonPosition = event.target.getBoundingClientRect();
    setPopupPositionPopupNotification({
      top: buttonPosition.bottom + 15,
      left: buttonPosition.left - 130,
    });
    setIsOpenPopupNotification(true);
  };

  const closePopupNotification = () => {
    setIsOpenPopupNotification(false);
  };

  const dispatch = useAppDispatch();

  const loadCountNotification = () => {
    DetailProjectAPI.countNotification(userCurrent.id).then((response) => {
      dispatch(SetCountNotifications(response.data.data));
    });
  };

  const loadDataNotification = () => {
    DetailProjectAPI.fetchAllNotification(userCurrent.id, 0).then(
      (response) => {
        dispatch(SetListNotification(response.data.data.data));
        dispatch(SetCurrentPage(response.data.data.currentPage));
        dispatch(SetToTalPages(response.data.data.totalPages));
      }
    );
  };

  useEffect(() => {
    if (userCurrent != null) {
      loadCountNotification();
      loadDataNotification();
    }
  }, [userCurrent]);

  const countNotifications = useAppSelector(GetCountNotification);

  return (
    <div style={{ display: "flex", alignItems: "center" }}>
      <Badge
        count={countNotifications}
        style={{ cursor: "pointer", marginRight: 20 }}
      >
        <BellOutlined
          onClick={(e) => {
            openPopupNotification(e);
          }}
          style={{ fontSize: 20, cursor: "pointer", marginRight: 20 }}
          className="box_notification"
        />{" "}
        {isOpenPopupNotification && (
          <PopupNotification
            position={popupPositionPopupNotification}
            onClose={closePopupNotification}
          />
        )}{" "}
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
          <img
            className="img_avatar"
            src={userCurrent != null ? userCurrent.picture : ""}
            alt="avt"
          />
          <span
            className="span-name-usercurrent"
            style={{ marginLeft: 8, fontWeight: 500, color: "#4f4f4f" }}
          >
            {userCurrent != null ? userCurrent.name : ""}
          </span>{" "}
        </Link>
      </Dropdown>
    </div>
  );
};

export default CommonInforUser;
