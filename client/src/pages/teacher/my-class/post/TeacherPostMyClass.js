import "./styleTeacherPostMyClass.css";
import { Button, Card, Dropdown, Menu } from "antd";
import { useEffect } from "react";
import { useState } from "react";
import { useParams } from "react-router";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherPostAPI } from "../../../../api/teacher/post/TeacherPost.api";
import LoadingIndicator from "../../../../helper/loading";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEllipsisVertical, faHome } from "@fortawesome/free-solid-svg-icons";
import { BookOutlined } from "@ant-design/icons";
import { giangVienCurrent } from "../../../../helper/inForUser";
import RichTextEditor from "./rich-teach-edit/rich-teach-editor";
import {
  GetPost,
  SetPost,
} from "../../../../app/teacher/post/tePostSlice.reduce";

const TeacherPostMyClass = () => {
  const dispatch = useAppDispatch();
  const { idClass } = useParams();
  const [classDetail, setClassDetail] = useState({});
  const [loading, setLoading] = useState(false);
  const [showCreate, setShowCreate] = useState(false);

  const [items, setItems] = useState([]);
  const [displayedItems, setDisplayedItems] = useState([]);
  const [itemsPerPage, setItemsPerPage] = useState(10);
  const [currentPage, setCurrentPage] = useState(1);

  const [check, setCheck] = useState(false);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - bài viết";
    featchClass(idClass);
    featchPost(idClass);
  }, []);

  useEffect(() => {
    if (check === true) {
      window.scrollTo(0, 0);
      document.title = "Bảng điều khiển - bài viết";

      featchPost(idClass);
    }
  }, [check]);
  // useEffect(() => {
  //   const start = (currentPage - 1) * itemsPerPage;
  //   const end = start + itemsPerPage;
  //   setDisplayedItems(items.slice(start, end));
  // }, [currentPage, items]);

  // const handleScroll = () => {
  //   if (
  //     window.innerHeight + window.scrollY >=
  //     document.body.offsetHeight - 100
  //   ) {
  //     setCurrentPage((prevPage) => prevPage + 1);
  //   }
  // };

  // useEffect(() => {
  //   window.addEventListener("scroll", handleScroll);
  //   return () => {
  //     window.removeEventListener("scroll", handleScroll);
  //   };
  // }, []);

  const featchPost = async (idClass) => {
    try {
      let data = {
        idClass: idClass,
        idTeacher: giangVienCurrent.id,
        page: currentPage,
        size: 5,
      };
      await TeacherPostAPI.getPagePost(data).then((responese) => {
        dispatch(SetPost(responese.data.data.data));
        setCheck(true);
        setLoading(true);
      });
    } catch (error) {
      alert(error.message);
    }
  };
  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        setClassDetail(responese.data.data);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  const convertLongToDate = (longValue) => {
    const date = new Date(longValue);
    const format = `${date.getDate()}/${
      date.getMonth() + 1
    }/${date.getFullYear()} thời gian ${date.getHours()}: ${date.getMinutes()} `;
    return format;
  };

  const menu = (
    <Menu>
      <Menu.Item key="1">Chỉnh sửa</Menu.Item>
      <Menu.Item key="2">Xóa</Menu.Item>
    </Menu>
  );
  const data = useAppSelector(GetPost);
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div className="box-one">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <FontAwesomeIcon
              icon={faHome}
              style={{ color: "#00000", fontSize: "23px" }}
            />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>{" "}
            <span style={{ color: "gray", fontSize: "14px" }}>
              {" "}
              - Thành viên
            </span>
          </span>
        </Link>
      </div>
      <div className="box-two-student-in-my-class">
        <div className="box-two-student-in-my-class-son">
          <div className="button-menu">
            <div>
              <Link
                to={`/teacher/my-class/post/${idClass}`}
                id="menu-checked"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                BÀI VIẾT &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/students/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                THÀNH VIÊN TRONG LỚP &nbsp;
              </Link>
              <Link
                to={`/teacher/my-class/teams/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                QUẢN LÝ NHÓM &nbsp;
              </Link>{" "}
              <Link
                to={`/teacher/my-class/meeting/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                BUỔI HỌC &nbsp;
              </Link>{" "}
              <Link
                to={`/teacher/my-class/attendance/${idClass}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM DANH &nbsp;
              </Link>
              <Link
                to=""
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM &nbsp;
              </Link>
              <div
                className="box-center"
                style={{
                  height: "28.5px",
                  width: "auto",
                  backgroundColor: "#007bff",
                  color: "white",
                  borderRadius: "5px",
                  float: "right",
                }}
              >
                {" "}
                <span style={{ fontSize: "14px", padding: "10px" }}>
                  {classDetail.code}
                </span>
              </div>
              <hr />
            </div>
          </div>
          <div
            className="box-image"
            style={{ height: 200, backgroundColor: "blue" }}
          >
            <span style={{ fontSize: 18, color: "white" }}>
              {" "}
              {classDetail.code}
            </span>
          </div>
          <div className="box-post">
            <div
              className="box-post-left"
              style={{
                width: "20%",
                height: 800,
                //backgroundColor: "green"
              }}
            >
              <div className="box-one" style={{ height: "160px" }}>
                <div>Mã lớp:</div>
                <div>{classDetail.code}</div>
              </div>
            </div>
            <div
              className="box-post-right"
              style={{
                width: "78%",
                marginLeft: "2%",
                height: 800,
                // backgroundColor: "yellow",
              }}
            >
              <div
                className="box-one"
                style={{ minHeight: "200px", width: "100%", height: "auto" }}
              >
                {showCreate === true ? (
                  <div style={{ width: "100%" }}>
                    {" "}
                    <RichTextEditor style={{ width: "100%", height: "300" }} />
                    <div style={{ paddingTop: "15px", float: "right" }}>
                      <Button
                        style={{
                          backgroundColor: "red",
                          color: "white",
                        }}
                        onClick={(e) => {
                          e.stopPropagation();
                          setShowCreate(false);
                        }}
                      >
                        Hủy
                      </Button>
                      <Button
                        style={{
                          backgroundColor: "rgb(61, 139, 227)",
                          color: "white",
                        }}
                      >
                        Đăng
                      </Button>
                    </div>
                  </div>
                ) : (
                  <div
                    className="title-left"
                    onClick={(e) => {
                      e.stopPropagation();
                      setShowCreate(true);
                    }}
                  >
                    <div className="flex-container">
                      <div className="title-icon">
                        <div className="box-icon">
                          <BookOutlined
                            style={{ color: "white", fontSize: 21 }}
                          />
                        </div>
                      </div>
                      <p
                        className="title-text"
                        style={{
                          fontSize: "16px",
                          color: "black",
                        }}
                      >
                        Thông báo nội dung nào đó cho lớp học
                      </p>
                    </div>
                    <div style={{ paddingTop: "15px" }}></div>
                  </div>
                )}
              </div>
              <div style={{ height: "auto", marginTop: "20px" }}>
                {data.map((item) => {
                  <Card
                    key={item.id}
                    title={
                      <>
                        <div>{giangVienCurrent.name}</div>{" "}
                        <div
                          className="title-icon-drop"
                          style={{ textAlign: "right" }}
                        >
                          <Dropdown
                            overlay={menu}
                            trigger={["click"]}
                            className="box-drop"
                          >
                            <div
                              className="box-icon-drop"
                              style={{ backgroundColor: "white" }}
                            >
                              <FontAwesomeIcon icon={faEllipsisVertical} />
                            </div>
                          </Dropdown>
                        </div>
                      </>
                    }
                    className="box-card-one"
                  >
                    <div
                      style={{
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                      }}
                    >
                      <p>{convertLongToDate(item.createdDate)}</p>
                    </div>
                    <p>{item.descriptions}</p>
                  </Card>;
                })}

                <p
                  style={{
                    textAlign: "center",
                    marginTop: "100px",
                    fontSize: "15px",
                  }}
                >
                  Chưa đăng bài viết nào được đăng!
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default TeacherPostMyClass;
