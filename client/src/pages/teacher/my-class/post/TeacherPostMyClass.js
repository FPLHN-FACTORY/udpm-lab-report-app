import "./styleTeacherPostMyClass.css";
import { Button, Card, Dropdown, Menu, Modal, Row } from "antd";
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
import Editor from "./form-editor-create/FormEditor";
import EditorUpdate from "./form-editor-update/FormEditorUpdate";
import {
  DeletePost,
  GetPost,
  NextPagePost,
  SetPost,
} from "../../../../app/teacher/post/tePostSlice.reduce";
import { toast } from "react-toastify";
const TeacherPostMyClass = () => {
  const dispatch = useAppDispatch();
  const { idClass } = useParams();
  const [classDetail, setClassDetail] = useState({});
  const [loading, setLoading] = useState(false);
  const [showCreate, setShowCreate] = useState(false);
  const [showUpdateIndex, setShowUpdateIndex] = useState(null);
  const [showUpdate, setShowUpdate] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPage, setTotalPage] = useState(0);
  const [seeMore, setSeeMore] = useState(true);
  const [idDelete, setIdDelete] = useState("");
  const [visibleDelete, setVisibleDelete] = useState(false);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - bài viết";
    featchClass(idClass);
    featchPost(idClass);
  }, []);
  const handleSeeMore = () => {
    if (currentPage < totalPage) {
      setCurrentPage((pre) => pre + 1);
      setSeeMore(true);
    } else {
      setSeeMore(false);
    }
  };
  useEffect(() => {
    if (currentPage < totalPage) {
      setSeeMore(true);
    }
    if (currentPage === totalPage) {
      setSeeMore(false);
    }
    if (currentPage === 1) {
      return undefined;
    }
    featchNextPost(idClass);
  }, [currentPage]);

  const showHandleCreate = (value) => {
    setShowCreate(value);
  };
  const handleUpdate = async (index) => {
    setShowUpdateIndex(index);
  };
  const showHandleUpdate = (obj, value) => {
    setShowUpdate(value);
    setShowUpdateIndex(null);
  };

  const featchPost = async (idClass) => {
    setLoading(false);
    try {
      let data = {
        idClass: idClass,
        idTeacher: giangVienCurrent.id,
        page: currentPage,
        size: 8,
      };
      await TeacherPostAPI.getPagePost(data).then((responese) => {
        dispatch(SetPost(responese.data.data.data));
        setTotalPage(responese.data.data.totalPages);
        setLoading(true);
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
        size: 8,
      };
      await TeacherPostAPI.getPagePost(data).then((responese) => {
        dispatch(NextPagePost(responese.data.data.data));
        setTotalPage(responese.data.data.totalPages);
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

  const showModal = (id) => {
    setVisibleDelete(true);
    setIdDelete(id);
  };
  const handleOkDelete = () => {
    clickDelete(idDelete);
    setVisibleDelete(false);
    setIdDelete("");
  };
  const handleCancelDelete = () => {
    setVisibleDelete(false);
    setIdDelete("");
  };
  const clickDelete = async (id) => {
    try {
      await TeacherPostAPI.delete(id).then((respone) => {
        dispatch(DeletePost(respone.data.data));
        toast.success("Xóa bài viết thành công !");
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
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
      <Modal
        title="Xác nhận xóa"
        visible={visibleDelete}
        onOk={handleOkDelete}
        onCancel={handleCancelDelete}
        okText="Xóa"
        cancelText="Hủy"
      >
        <p>Bạn có chắc chắn muốn xóa bài viết?</p>
      </Modal>
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
              <hr />
            </div>
          </div>
          <div className="box-image"></div>
          <div className="box-post">
            <div className="box-post-left">
              <div className="box-infor" style={{ height: "140px" }}>
                <p
                  style={{
                    padding: "5px 0px 0px 20px",
                    fontWeight: "500",
                    fontSize: "17px",
                  }}
                >
                  Mã lớp
                </p>
                <p
                  style={{
                    padding: "5px 0px 0px 20px",
                    fontWeight: "bold",
                    fontSize: "20px",
                    color: "#c99b40",
                  }}
                >
                  {classDetail.code}
                </p>
              </div>
              <div
                className="box-infor"
                style={{ height: "140px", marginTop: "20px" }}
              >
                <p
                  style={{
                    padding: "5px 0px 0px 20px",
                    fontWeight: "500",
                    fontSize: "17px",
                  }}
                >
                  Mật khẩu
                </p>
                <p
                  style={{
                    padding: "5px 0px 0px 20px",
                    fontWeight: "bold",
                    fontSize: "20px",
                    color: "#c99b40",
                  }}
                >
                  {classDetail.passWord}
                </p>
              </div>
            </div>
            <div className="box-post-right">
              {showCreate === true ? (
                <div className="box-create-post">
                  <div className="rich-text-editor" style={{ width: "100%" }}>
                    <div
                      style={{
                        width: "100%",
                        height: "auto",
                      }}
                    >
                      <Editor
                        idTeacher={giangVienCurrent.id}
                        idClass={idClass}
                        showCreate={showHandleCreate}
                        style={{
                          color: "black !important",
                          fontSize: "13px",
                          display: "block",
                          width: "100%",
                          minHeight: "100px",
                        }}
                      />
                    </div>
                  </div>
                </div>
              ) : (
                <div
                  className="box-create-post"
                  onClick={(e) => {
                    setShowCreate(true);
                  }}
                >
                  <div
                    style={{
                      height: "80px",
                      lineHeight: "50px",
                      paddingLeft: "25px",
                    }}
                  >
                    <p
                      className="link-create"
                      style={{
                        fontSize: "16px",
                      }}
                    >
                      Thông báo nội dung nào đó cho lớp học của bạn
                    </p>
                  </div>
                </div>
              )}
              <div style={{ height: "auto", margin: "20px 0 20px 0" }}>
                {data.length > 0 ? (
                  data.map((item, index) => {
                    return (
                      <div key={item.id} style={{ marginBottom: "20px" }}>
                        {showUpdateIndex === index ? (
                          <div className="box-create-post">
                            <div
                              className="rich-text-editor"
                              style={{ width: "100%" }}
                            >
                              <div
                                style={{
                                  width: "100%",
                                  height: "auto",
                                }}
                              >
                                <EditorUpdate
                                  obj={item}
                                  showUpdate={showHandleUpdate}
                                  style={{
                                    color: "black !important",
                                    fontSize: "13px",
                                    display: "block",
                                    width: "100%",
                                    minHeight: "100px",
                                  }}
                                />
                              </div>
                            </div>
                          </div>
                        ) : (
                          <Card
                            className="box-card-one"
                            style={{
                              margin: "20px 0 20px 0",
                              height: "auto",
                            }}
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
                                          <Menu.Item
                                            key="1"
                                            onClick={() => handleUpdate(index)}
                                          >
                                            Chỉnh sửa
                                          </Menu.Item>
                                          <Menu.Item
                                            key="2"
                                            onClick={() => showModal(item.id)}
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
                          >
                            <span
                              style={{
                                display: "flex",
                                justifyContent: "space-between",
                                alignItems: "center",
                                wordWrap: "break-word",
                              }}
                              dangerouslySetInnerHTML={parsedHTML(
                                item.descriptions
                              )}
                            />
                          </Card>
                        )}
                      </div>
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
