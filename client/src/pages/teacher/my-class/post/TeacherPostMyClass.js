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
import { giangVienCurrent } from "../../../../helper/inForUser";
import RichTextEditor from "./rich-teach-edit/rich-teach-editor";
import ModalCreatePostNew from "./modal-create-new/ModalCreateNew";
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
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPage, setTotalPage] = useState(0);
  const [isLoadingMore, setIsLoadingMore] = useState(false);

  // const handleScroll = () => {
  //   if (
  //     !isLoadingMore &&
  //     window.innerHeight + window.scrollY >= document.body.offsetHeight - 100
  //   ) {
  //     if (currentPage < totalPage) {
  //       setIsLoadingMore(true);
  //       setCurrentPage((prevPage) => prevPage + 1);
  //     }
  //   }
  // };

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - bài viết";
    featchClass(idClass);
    featchPost(idClass);
    // window.addEventListener("scroll", handleScroll);
    // return () => {
    //   window.removeEventListener("scroll", handleScroll);
    // };
  }, []);

  // useEffect(() => {
  //   featchPost(idClass);
  // }, [currentPage]);

  const featchPost = async (idClass) => {
    try {
      let data = {
        idClass: idClass,
        idTeacher: giangVienCurrent.id,
        page: currentPage,
        size: 11,
      };
      await TeacherPostAPI.getPagePost(data)
        .then((responese) => {
          dispatch(SetPost(responese.data.data.data));
          setTotalPage(responese.data.data.totalPages);
          setLoading(true);
        })
        .finally(() => {
          setIsLoadingMore(false);
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
  const handleCancelModalCreateSusscess = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreate(false);
    setLoading(true);
  };
  const handleCancelModalCreateFaild = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowCreate(false);
    setLoading(true);
  };
  const handleCancelCreate = {
    handleCancelModalCreateSusscess,
    handleCancelModalCreateFaild,
  };
  const convertLongToDate = (longValue) => {
    const date = new Date(longValue);
    const format = `${date.getDate()}/${
      date.getMonth() + 1
    }/${date.getFullYear()} ${date.getHours()}:${date.getMinutes()} `;
    return format;
  };

  const menu = (
    <Menu>
      <Menu.Item key="1">Chỉnh sửa</Menu.Item>
      <Menu.Item key="2">Xóa</Menu.Item>
    </Menu>
  );

  const data = useAppSelector(GetPost);
  useEffect(() => {
    const boxRef = document.querySelector(".box-background");
    setTimeout(() => {
      const realHeight = boxRef.scrollHeight;
      boxRef.style.height = realHeight + `px`;
    }, 0);
  }, [data]);

  return (
    <>
      {!loading && <LoadingIndicator />}
      <ModalCreatePostNew
        visible={showCreate}
        onCancel={handleCancelCreate}
        idClass={idClass}
      />
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
      <div
        className="box-two-student-in-my-class"
        style={{ height: "auto", paddingBottom: "0px" }}
      >
        <div className="box-background">
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
            <div className="box-post-left">
              <div className="box-one" style={{ height: "160px" }}>
                <div>Mã lớp:</div>
                <div>{classDetail.code}</div>
              </div>
            </div>
            <div className="box-post-right">
              <div className="box-create-post">
                {showCreate === true ? (
                  <div>
                    <div
                      style={{
                        width: "100%",
                        height: "auto",
                      }}
                    >
                      {" "}
                      <RichTextEditor
                        style={{ width: "100%", height: "300" }}
                      />
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
                  </div>
                ) : (
                  <div
                    style={{
                      height: "80px",
                      lineHeight: "50px",
                      paddingLeft: "50px",
                    }}
                    onClick={(e) => {
                      // e.stopPropagation();
                      setShowCreate(true);
                    }}
                  >
                    {/* <div className="box-icon">
                      <BookOutlined style={{ color: "white", fontSize: 21 }} />
                    </div> */}
                    <p
                      style={{
                        fontSize: "16px",
                        color: "black",
                      }}
                    >
                      Thông báo nội dung nào đó cho lớp học của bạn
                    </p>
                  </div>
                )}
              </div>
              <div style={{ height: "auto", margin: "20px 0 20px 0" }}>
                {data.length > 0 ? (
                  data.map((item) => {
                    return (
                      <Card
                        style={{
                          margin: "20px 0 20px 0",
                          height: "auto",
                        }}
                        key={item.id}
                        title={
                          <>
                            <div style={{ width: "100%" }}>
                              <div style={{ width: "95%", float: "left" }}>
                                <span style={{ lineHeight: "50px" }}>
                                  {" "}
                                  {giangVienCurrent.name}{" "}
                                  <span
                                    style={{
                                      color: "gray",
                                      fontWeight: "initial",
                                      fontSize: "13px",
                                    }}
                                  >
                                    {convertLongToDate(item.createdDate)}
                                  </span>
                                </span>
                              </div>{" "}
                              <div
                                style={{ width: "5%", float: "left" }}
                                className="title-icon-drop"
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
                                    <FontAwesomeIcon
                                      icon={faEllipsisVertical}
                                    />
                                  </div>
                                </Dropdown>
                              </div>
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
                        ></div>
                        <p>{item.descriptions}</p>
                      </Card>
                    );
                  })
                ) : (
                  <p
                    style={{
                      textAlign: "center",
                      marginTop: "100px",
                      fontSize: "15px",
                    }}
                  >
                    Chưa đăng bài viết nào được đăng!
                  </p>
                )}
              </div>
            </div>{" "}
          </div>{" "}
        </div>
      </div>
    </>
  );
};
export default TeacherPostMyClass;
