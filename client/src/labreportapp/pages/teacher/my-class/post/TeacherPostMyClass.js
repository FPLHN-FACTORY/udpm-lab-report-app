import "./styleTeacherPostMyClass.css";
import { Button, Card, Col, Dropdown, Menu, Popconfirm, Row } from "antd";
import { useEffect } from "react";
import { useState } from "react";
import { useParams } from "react-router";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { TeacherPostAPI } from "../../../../api/teacher/post/TeacherPost.api";
import LoadingIndicator from "../../../../helper/loading";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faArrowsRotate,
  faCopy,
  faEllipsisVertical,
  faExpand,
  faLock,
  faLockOpen,
} from "@fortawesome/free-solid-svg-icons";
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
import ViewEditorJodit from "../../../../helper/editor/ViewEditorJodit";
import { ControlOutlined } from "@ant-design/icons";
import { SetTTrueToggle } from "../../../../app/teacher/TeCollapsedSlice.reducer";
import {
  GetClass,
  SetClass,
  UpdateClass,
} from "../../../../app/teacher/my-class/teClassSlice.reduce";
import ModalFullScreen from "./modal-full-screen/ModalFullScreen";

const TeacherPostMyClass = () => {
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const { idClass } = useParams();
  const [loading, setLoading] = useState(false);
  const [clickShow, setClickShow] = useState(0);
  const [showFullScreen, setShowFullScreen] = useState(false);
  const [showCreate, setShowCreate] = useState(false);
  const [showUpdateIndex, setShowUpdateIndex] = useState(null);
  const [showUpdate, setShowUpdate] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPage, setTotalPage] = useState(0);
  const [seeMore, setSeeMore] = useState(true);
  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - Bài đăng";
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
        if (responese.data.data.totalPages === 0) {
          setSeeMore(false);
        }
        setLoading(true);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
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
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        dispatch(SetClass(responese.data.data));
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
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
  const handleRandomPass = async () => {
    try {
      await TeacherMyClassAPI.randomPass(idClass).then((respone) => {
        dispatch(UpdateClass(respone.data.data));
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const handleUpdateStatusClass = async () => {
    try {
      let objApi = {
        idClass: idClass,
        status: classDetail.statusClass === 0 ? 1 : 0,
      };
      await TeacherMyClassAPI.updateStatusClass(objApi).then((respone) => {
        if (objApi.status === 0) {
          toast.success("Mở lớp thành công !");
        } else {
          toast.success("Khóa lớp thành công !");
        }
        dispatch(UpdateClass(respone.data.data));
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const copyToClipboard = () => {
    const tempInput = document.createElement("input");
    tempInput.value = classDetail.passWord;
    document.body.appendChild(tempInput);
    tempInput.select();
    tempInput.setSelectionRange(0, 99999);
    document.execCommand("copy");
    document.body.removeChild(tempInput);
    toast.success("Đã sao chép mật khẩu vào bộ nhớ đệm !");
  };
  const convertLongToDate = (longValue) => {
    const date = new Date(longValue);
    const format = `${date.getDate()}/${
      date.getMonth() + 1
    }/${date.getFullYear()} ${date.getHours()}:${date.getMinutes()} `;
    return format;
  };
  const handleFullScreen = (value) => {
    setClickShow(value);
    setShowFullScreen(true);
  };
  const handleCancelModal = () => {
    document.querySelector("body").style.overflowX = "hidden";
    setShowFullScreen(false);
  };
  const classDetail = useAppSelector(GetClass);
  const data = useAppSelector(GetPost);
  return (
    <>
      {!loading && <LoadingIndicator />}
      <ModalFullScreen
        visible={showFullScreen}
        onCancel={handleCancelModal}
        item={clickShow === 0 ? classDetail.code : classDetail.passWord}
      />
      <div className="box-one">
        <Link to="/teacher/my-class" style={{ color: "black" }}>
          <span style={{ fontSize: "18px", paddingLeft: "20px" }}>
            <ControlOutlined style={{ fontSize: "22px" }} />
            <span style={{ marginLeft: "10px", fontWeight: "500" }}>
              Bảng điều khiển
            </span>
            <span style={{ color: "gray", fontSize: "14px" }}> - Bài đăng</span>
          </span>
        </Link>
      </div>
      <div
        className="box-two-student-in-my-class"
        style={{ height: "auto", paddingBottom: "25px" }}
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
                BÀI ĐĂNG &nbsp;
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
                THÔNG TIN LỚP HỌC &nbsp;
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
              </Link>
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
                to={`/teacher/my-class/point/${idClass}`}
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
          <div className="box-image" style={{ marginBottom: "20px" }}>
            {" "}
            <span className="textCode"> {classDetail.code}</span>
          </div>
          <div className="box-post">
            <div className="box-post-left">
              <div className="box-infor" style={{ height: "140px" }}>
                <span className="title-main">Tên lớp</span>
                <p className="infor-main">
                  {classDetail.code}
                  <FontAwesomeIcon
                    style={{ paddingLeft: 12 }}
                    size="22"
                    icon={faExpand}
                    onClick={() => handleFullScreen(0)}
                  />
                </p>
              </div>
              <div
                className="box-infor"
                style={{ height: "140px", marginTop: "20px" }}
              >
                <div className="title-main">
                  <Row gutter={24}>
                    <Col span={20}>
                      <span>Mã tham gia</span>
                    </Col>
                    <Col span={4}>
                      <span>
                        <Dropdown
                          overlay={
                            <Menu>
                              <Menu.Item
                                key="1"
                                onClick={() => handleRandomPass()}
                              >
                                <FontAwesomeIcon
                                  icon={faArrowsRotate}
                                  style={{ paddingRight: 10 }}
                                  className="icon"
                                />
                                <span>Đặt lại mật khẩu</span>
                              </Menu.Item>
                              <Popconfirm
                                title="Thông báo"
                                description={
                                  classDetail.statusClass === 0
                                    ? "Bạn có chắc chắn muốn khóa lớp không ?"
                                    : "Bạn có chắc chắn muốn mở lớp không ?"
                                }
                                onConfirm={() => {
                                  handleUpdateStatusClass();
                                }}
                                okText="Có"
                                cancelText="Không"
                              >
                                <Menu.Item key="2">
                                  {classDetail.statusClass === 0 ? (
                                    <>
                                      <FontAwesomeIcon
                                        icon={faLock}
                                        style={{ paddingRight: 10 }}
                                        className="icon"
                                      />
                                      <span>Khóa lớp</span>
                                    </>
                                  ) : (
                                    <>
                                      <FontAwesomeIcon
                                        icon={faLockOpen}
                                        style={{ paddingRight: 10 }}
                                        className="icon"
                                      />
                                      <span>Mở lớp</span>
                                    </>
                                  )}
                                </Menu.Item>
                              </Popconfirm>
                              <Menu.Item key="3">
                                <div onClick={copyToClipboard}>
                                  <FontAwesomeIcon
                                    icon={faCopy}
                                    className="icon"
                                    style={{ paddingRight: 10 }}
                                  />
                                  <span>Sao chép</span>
                                </div>
                              </Menu.Item>
                            </Menu>
                          }
                          trigger={["click"]}
                          className="box-drop"
                          style={{ marginTop: 1 }}
                        >
                          <div style={{ backgroundColor: "white" }}>
                            <FontAwesomeIcon icon={faEllipsisVertical} />
                          </div>
                        </Dropdown>
                      </span>
                    </Col>
                  </Row>
                </div>
                <p className="infor-main">
                  {classDetail.passWord}
                  <FontAwesomeIcon
                    style={{ paddingLeft: 12 }}
                    size="22"
                    icon={faExpand}
                    onClick={() => handleFullScreen(1)}
                  />
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
                          height: "300px",
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
                      paddingLeft: "25px",
                      display: "flex",
                      alignItems: "center",
                    }}
                  >
                    <span
                      className="link-create"
                      style={{
                        fontSize: "16px",
                      }}
                    >
                      Thông báo nội dung nào đó cho lớp học của bạn
                    </span>
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
                                          marginLeft: "7px",
                                        }}
                                      >
                                        {convertLongToDate(item.createdDate)}
                                      </span>
                                    </span>
                                  </div>
                                  <div
                                    style={{ float: "left" }}
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
                                          </Menu.Item>{" "}
                                          <Popconfirm
                                            title="Xóa học kỳ"
                                            description="Bạn có chắc chắn muốn xóa bài viết này không ?"
                                            onConfirm={() => {
                                              clickDelete(item.id);
                                            }}
                                            okText="Có"
                                            cancelText="Không"
                                          >
                                            <Menu.Item key="2">Xóa</Menu.Item>
                                          </Popconfirm>
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
                            <ViewEditorJodit value={item.descriptions} />
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
                      color: "red",
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
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default TeacherPostMyClass;