import { Link, useLocation, useNavigate } from "react-router-dom";
import "./styleHeaderDetailProject.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome, faCogs, faRepeat } from "@fortawesome/free-solid-svg-icons";
import { CommonAPI } from "../../../api/commonAPI";
import { MemberProjectAPI } from "../../../api/my-project/memberProject.api";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import {
  GetMemberProject,
  SetMemberProject,
} from "../../../app/reducer/detail-project/DPMemberProject.reducer";
import { useEffect, useState } from "react";
import {
  GetProject,
  SetError,
} from "../../../app/reducer/detail-project/DPProjectSlice.reducer";
import { Input, Tooltip } from "antd";
import {
  faEye,
  faSave,
  faClipboard,
  faTable,
  faChartBar,
  faSort,
  faFilter,
  faBars,
} from "@fortawesome/free-solid-svg-icons";
import Image from "../../../helper/img/Image";
import PopupPeriod from "./popup/popup-period/PopupPeriod";
import MenuRight from "./menu-right/MenuRight";
import { memo } from "react";
import { ProjectOutlined, TableOutlined } from "@ant-design/icons";
import PopupFilter from "./popup/filter/PopupFilter";
import PopupSort from "./popup/sort/PopupSort";
import PopupMemberManagement from "./popup/member-management/PopupMemberManagement";
import { GetUserCurrent } from "../../../../labreportapp/app/common/UserCurrent.reducer";

const HeaderDetailProject = () => {
  const dispatch = useAppDispatch();
  const detailProject = useAppSelector(GetProject);
  const showMembers = 5;
  const listMemberProject = useAppSelector(GetMemberProject);
  const extraMembers = listMemberProject.length - showMembers;
  const [isMenuRightVisible, setIsMenuRightVisible] = useState(false);

  useEffect(() => {
    if (
      detailProject != null &&
      detailProject !== undefined &&
      Object.keys(detailProject).length > 0
    ) {
      fetchDataMemberProject();
    }

    const addedStyles = [];

    const addStyle = (cssText) => {
      const style = document.createElement("style");
      style.textContent = cssText;
      document.head.appendChild(style);
      addedStyles.push(style);
    };

    addStyle(`
      .title_logo,
      .span-name-usercurrent,
      .box_notification {
        color: rgb(255, 255, 255) !important;
      }

      .ant-layout-header {
        background-color: rgba(168, 168, 168, 0.177) !important;
        color: white !important;
      }
    `);

    return () => {
      addedStyles.forEach((style) => {
        style.parentNode.removeChild(style);
      });
    };
  }, [detailProject]);

  useEffect(() => {
    document.querySelector("body").style.backgroundImage =
      "url(" + detailProject.backgroundImage + ")";

    document.querySelector("body").style.backgroundColor =
      detailProject.backgroundColor;
  }, [detailProject.backgroundImage, detailProject.backgroundColor]);

  const fetchDataMemberProject = async () => {
    try {
      const resMP = await MemberProjectAPI.fetchAll(detailProject.id);
      const listMemberProject = resMP.data.data;
      dispatch(SetMemberProject(listMemberProject));
    } catch (error) {
      dispatch(SetError("Lỗi hệ thống, vui lòng ấn F5 để tải lại trang"));
    }
  };

  const [isOpenPopupPeriod, setIsOpenPopupPeriod] = useState(false);
  const [popupPositionPopupPeriod, setPopupPositionPopupPeriod] = useState({
    top: 0,
    left: 0,
  });

  const openPopupPeriod = (event) => {
    const buttonPosition = event.target.getBoundingClientRect();
    setPopupPositionPopupPeriod({
      top: buttonPosition.bottom + 5,
      left: buttonPosition.left,
    });
    setIsOpenPopupPeriod(true);
  };

  const closePopupPeriod = () => {
    setIsOpenPopupPeriod(false);
  };

  const handleToggleMenuRight = () => {
    setIsMenuRightVisible(!isMenuRightVisible);
  };

  const location = useLocation();

  const [isTableVisible, setIsTableVisible] = useState(0);

  useEffect(() => {
    if (location.pathname.includes("/table")) {
      setIsTableVisible(1);
    } else if (location.pathname.includes("period-project")) {
      setIsTableVisible(2);
    } else if (location.pathname.includes("detail-project/dashboard")) {
      setIsTableVisible(3);
    } else {
      setIsTableVisible(0);
    }
  }, [location]);

  /*{" "}
        <span className="box_setting">
          <FontAwesomeIcon icon={faSave} /> Lưu ý
        </span>{" "}
        */

  const [showInputChangeNameProject, setShowInputChangeNameProject] =
    useState(false);
  const [inputValue, setInputValue] = useState("");
  useEffect(() => {
    setInputValue(detailProject.name);
  }, [detailProject]);

  const handleSpanClick = () => {
    setShowInputChangeNameProject(true);
  };

  const handleInputBlur = () => {
    setShowInputChangeNameProject(false);
    console.log("Input value:", inputValue);
  };

  const handleInputChange = (e) => {
    setInputValue(e.target.value);
  };

  const [isOpenPopupFilter, setIsOpenPopupFilter] = useState(false);
  const [popupPositionPopupFilter, setPopupPositionPopupFilter] = useState({
    top: 0,
    left: 0,
  });

  const openPopupFilter = (event) => {
    const buttonPosition = event.target.getBoundingClientRect();
    setPopupPositionPopupFilter({
      top: buttonPosition.bottom + 5,
      left: buttonPosition.left - 155,
    });
    setIsOpenPopupFilter(true);
  };

  const closePopupFilter = () => {
    setIsOpenPopupFilter(false);
  };

  const [isOpenPopupSort, setIsOpenPopupSort] = useState(false);
  const [popupPositionPopupSort, setPopupPositionPopupSort] = useState({
    top: 0,
    left: 0,
  });

  const openPopupSort = (event) => {
    const buttonPosition = event.target.getBoundingClientRect();
    setPopupPositionPopupSort({
      top: buttonPosition.bottom + 5,
      left: buttonPosition.left - 155,
    });
    setIsOpenPopupSort(true);
  };

  const closePopupSort = () => {
    setIsOpenPopupSort(false);
  };

  const [isOpenPopupMemberManagement, setIsOpenPopupMemberManagement] =
    useState(false);
  const [
    popupPositionPopupMemberManagement,
    setPopupPositionPopupMemberManagement,
  ] = useState({
    top: 0,
    left: 0,
  });

  const openPopupMemberManagement = (event) => {
    document.querySelector("body").style.overflowX = "hidden";
    const buttonPosition = event.target.getBoundingClientRect();
    setPopupPositionPopupMemberManagement({
      top: buttonPosition.bottom + 5,
      left: buttonPosition.left - 155,
    });
    setIsOpenPopupMemberManagement(true);
  };

  const closePopupMemberManagement = () => {
    document.querySelector("body").style.overflowX = "auto";
    setIsOpenPopupMemberManagement(false);
  };

  const navigate = useNavigate();

  const handlePeriodProject = () => {
    navigate("/period-project/" + detailProject.id);
  };

  const userCurrent = useAppSelector(GetUserCurrent);
  const [checkUrl, setCheckUrl] = useState("");

  useEffect(() => {
    if (userCurrent != null) {
      if (userCurrent.role.includes("TEACHER")) {
        setCheckUrl("/teacher/my-project");
        return;
      } else {
        setCheckUrl("/student/my-project");
        return;
      }
    }
  }, [userCurrent]);

  return (
    <div className="header-style">
      <div className="left_header">
        <Link to={checkUrl} style={{ color: "white", textDecoration: "none" }}>
          <FontAwesomeIcon icon={faHome} /> Danh sách dự án{" "}
        </Link>{" "}
        <span style={{ marginLeft: "5px", marginRight: "5px" }}> / </span>{" "}
        {showInputChangeNameProject ? (
          <Input
            style={{
              width: "200px",
            }}
            autoFocus={true}
            value={inputValue}
            onChange={handleInputChange}
            onPressEnter={handleInputBlur}
          />
        ) : (
          <span
            style={{
              color: "white",
              textDecoration: "none",
              cursor: "pointer",
            }}
            onClick={handleSpanClick}
          >
            <ProjectOutlined /> {inputValue} |
          </span>
        )}
        <Link
          to={`/detail-project/${detailProject.id}`}
          style={{ color: "white", textDecoration: "none" }}
        >
          <span
            className={`box_setting ${isTableVisible === 0 ? "active" : ""}`}
          >
            <ProjectOutlined /> Board
          </span>
        </Link>
        <Link
          to={`/detail-project/table/${detailProject.id}`}
          style={{ color: "white", textDecoration: "none" }}
        >
          {" "}
          <span
            className={`box_setting ${isTableVisible === 1 ? "active" : ""}`}
          >
            <TableOutlined /> Table
          </span>{" "}
        </Link>
        {isTableVisible === 2 || isTableVisible === 3 ? (
          <span
            className={`box_setting ${isTableVisible === 2 ? "active" : ""}`}
            onClick={handlePeriodProject}
          >
            <FontAwesomeIcon icon={faRepeat} /> Giai đoạn
          </span>
        ) : (
          <span
            className={`box_setting ${isTableVisible === 2 ? "active" : ""}`}
            onClick={openPopupPeriod}
          >
            <FontAwesomeIcon icon={faRepeat} /> Giai đoạn
          </span>
        )}
        {(isTableVisible === 2 || isTableVisible === 3) && (
          <Link
            style={{ color: "white", textDecoration: "none" }}
            to={`/detail-project/dashboard/${detailProject.id}`}
          >
            <span
              className={`box_setting ${isTableVisible === 3 ? "active" : ""}`}
            >
              <FontAwesomeIcon icon={faChartBar} /> Thống kê
            </span>
          </Link>
        )}
        {isOpenPopupPeriod && (
          <PopupPeriod
            position={popupPositionPopupPeriod}
            onClose={closePopupPeriod}
          />
        )}
      </div>
      <div className="right_header">
        {" "}
        {isTableVisible !== 2 && isTableVisible !== 3 && (
          <>
            <Link
              style={{ color: "white", textDecoration: "none" }}
              to={`/detail-project/dashboard/${detailProject.id}`}
            >
              <span className="box_setting">
                <FontAwesomeIcon icon={faChartBar} /> Thống kê
              </span>
            </Link>
            <span className="box_setting" onClick={openPopupSort}>
              <FontAwesomeIcon icon={faSort} /> Sắp xếp
            </span>
            <span className="box_setting" onClick={openPopupFilter}>
              <FontAwesomeIcon icon={faFilter} /> Bộ lọc
            </span>
            <div style={{ marginLeft: "7px" }}> | </div>
            <div className="box_setting_member">
              {listMemberProject.slice(0, showMembers).map((item, index) => (
                <Image
                  marginRight={-5}
                  name={item.name + " " + item.userName}
                  key={index}
                  url={item.picture}
                  picxel={30}
                />
              ))}

              {extraMembers > 0 && (
                <span className="extra_quantity_member">+{extraMembers}</span>
              )}
              <Tooltip title="Xem chi tiết thành viên trong dự án">
                <span className="span_eye" onClick={openPopupMemberManagement}>
                  <FontAwesomeIcon icon={faEye} size="1x" />
                </span>
              </Tooltip>
            </div>
            <span> | </span>
            <span className="box_setting_menu" onClick={handleToggleMenuRight}>
              <FontAwesomeIcon icon={faBars} size="1x" />
            </span>
            {isMenuRightVisible && <MenuRight />}
            {isOpenPopupFilter && (
              <PopupFilter
                position={popupPositionPopupFilter}
                onClose={closePopupFilter}
              />
            )}{" "}
            {isOpenPopupSort && (
              <PopupSort
                position={popupPositionPopupSort}
                onClose={closePopupSort}
              />
            )}{" "}
            {isOpenPopupMemberManagement && (
              <PopupMemberManagement
                position={popupPositionPopupMemberManagement}
                onClose={closePopupMemberManagement}
              />
            )}{" "}
          </>
        )}
      </div>
    </div>
  );
};

export default memo(HeaderDetailProject);
