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
import RichTextEditor from "./rich-teach-edit/RichTextEditor";
import {
  CreatePost,
  DeletePost,
  GetPost,
  NextPagePost,
  SetPost,
} from "../../../../app/teacher/post/tePostSlice.reduce";
import { create } from "lodash";
import { toast } from "react-toastify";
const TeacherPostMyClass = () => {
  const dispatch = useAppDispatch();
  const { idClass } = useParams();
  const [classDetail, setClassDetail] = useState({});
  const [loading, setLoading] = useState(false);
  const [showCreate, setShowCreate] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPage, setTotalPage] = useState(0);
  const [seeMore, setSeeMore] = useState(true);
  const [descriptions, setDescriptions] = useState("");
  const [errorDescriptions, setErrorDescriptions] = useState("");
  const [isLoadingMore, setIsLoadingMore] = useState(false);

  const handleChangeDescriptions = (newContent) => {
    setDescriptions(newContent);
  };

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - bài viết";
    featchClass(idClass);
    featchPost(idClass);
  }, []);

  useEffect(() => {
    if (currentPage < totalPage) {
      setSeeMore(true);
    }
    if (currentPage === totalPage) {
      setSeeMore(false);
    }
    featchNextPost(idClass);
  }, [currentPage]);

  const handleSeeMore = () => {
    if (currentPage < totalPage) {
      setCurrentPage((pre) => pre + 1);
      setSeeMore(true);
    } else {
      setSeeMore(false);
    }
  };
  const featchPost = async (idClass) => {
    setLoading(false);
    try {
      let data = {
        idClass: idClass,
        idTeacher: giangVienCurrent.id,
        page: currentPage,
        size: 5,
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

  const featchNextPost = async (idClass) => {
    try {
      let data = {
        idClass: idClass,
        idTeacher: giangVienCurrent.id,
        page: currentPage,
        size: 5,
      };
      await TeacherPostAPI.getPagePost(data)
        .then((responese) => {
          dispatch(NextPagePost(responese.data.data.data));
          setTotalPage(responese.data.data.totalPages);
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

  const create = () => {
    let check = 0;
    if (descriptions.trim() === "") {
      setErrorDescriptions("Nội dung không được để trống !");
      check++;
    } else {
      setErrorDescriptions("");
    }

    if (check === 0) {
      let obj = {
        idTeacher: giangVienCurrent.id,
        descriptions: descriptions,
        idClass: idClass,
      };
      TeacherPostAPI.create(obj).then(
        (respone) => {
          setDescriptions("");
          setShowCreate(false);
          dispatch(CreatePost(respone.data.data));
          toast.success("Thêm bài viết thành công !");
        },
        (error) => {
          toast.error(error.response.data.message);
        }
      );
    }
  };

  const handleDelete = async (id) => {
    try {
      await TeacherPostAPI.delete(id).then((respone) => {
        dispatch(DeletePost(respone.data.data));
        toast.success("Xóa bài viết thành công !");
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

  const parsedHTML = (content) => {
    return { __html: content };
  };

  const convertLongToDate = (longValue) => {
    const date = new Date(longValue);
    const format = `${date.getDate()}/${
      date.getMonth() + 1
    }/${date.getFullYear()} ${date.getHours()}:${date.getMinutes()} `;
    return format;
  };

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
                  <div className="rich-text-editor" style={{ width: "100%" }}>
                    Thêm bài viết
                    <div
                      style={{
                        width: "100%",
                        height: "auto",
                      }}
                    >
                      <RichTextEditor
                        descriptions={handleChangeDescriptions}
                        style={{ width: "100%", height: "300" }}
                      />
                      <span style={{ color: "red" }}>{errorDescriptions}</span>
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
                          onClick={create}
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
                      setShowCreate(true);
                    }}
                  >
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
                      <>
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
                                    overlay={
                                      <Menu>
                                        <Menu.Item key="1">Chỉnh sửa</Menu.Item>
                                        <Menu.Item
                                          key="2"
                                          onClick={() => {
                                            handleDelete(item.id);
                                          }}
                                        >
                                          Xóa
                                        </Menu.Item>
                                      </Menu>
                                    }
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
                          <span
                            style={{
                              display: "flex",
                              justifyContent: "space-between",
                              alignItems: "center",
                              wordWrap: "break-word", // Thêm thuộc tính này
                            }}
                            dangerouslySetInnerHTML={parsedHTML(
                              item.descriptions
                            )}
                          />
                        </Card>
                      </>
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
                {seeMore && (
                  <Button
                    style={{ float: "right" }}
                    type="primary"
                    // loading={isLoading}
                    // onClick={handleLoadMore}
                    icon={<i className="fas fa-arrow-down" />}
                    onClick={() => {
                      handleSeeMore();
                    }}
                  >
                    Xem thêm
                  </Button>
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
