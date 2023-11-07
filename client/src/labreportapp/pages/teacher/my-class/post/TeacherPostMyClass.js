import "./styleTeacherPostMyClass.css";
import {
  Button,
  Card,
  Dropdown,
  Empty,
  Menu,
  Popconfirm,
  Row,
  Spin,
  message,
} from "antd";
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
  faAnglesRight,
  faArrowsRotate,
  faCopy,
  faEllipsisVertical,
  faExpand,
  faLock,
  faLockOpen,
} from "@fortawesome/free-solid-svg-icons";
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
import { GetUserCurrent } from "../../../../app/common/UserCurrent.reducer";
import { useNavigate } from "react-router-dom";

const TeacherPostMyClass = () => {

  const dispatch = useAppDispatch();
  const userRedux = useAppSelector(GetUserCurrent);
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
  const [dowloading, setDownloading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    window.scrollTo(0, 0);
    featchClass(idClass);
    featchPost(idClass);
    return () => {
      dispatch(SetPost([]));
    };
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
    } catch (error) {}
  };

  const featchNextPost = async (idClass) => {
    try {
      let data = {
        idClass: idClass,
        page: currentPage,
        size: 8,
      };
      await TeacherPostAPI.getPagePost(data).then((responese) => {
        dispatch(NextPagePost(responese.data.data.data));
        setTotalPage(responese.data.data.totalPages);
      });
    } catch (error) {}
  };

  const featchClass = async (idClass) => {
    try {
      await TeacherMyClassAPI.detailMyClass(idClass).then((responese) => {
        dispatch(SetClass(responese.data.data));
        document.title = "Bài đăng | " + responese.data.data.code;
      });
    } catch (error) {
      setTimeout(() => {
        navigate("/teacher/my-class");
      }, [1000]);
    }
  };

  const clickDelete = async (id) => {
    try {
      await TeacherPostAPI.delete(id).then((respone) => {
        dispatch(DeletePost(respone.data.data));
        message.success("Xóa bài viết thành công !");
      });
    } catch (error) {}
  };

  const handleRandomPass = async () => {
    try {
      await TeacherMyClassAPI.randomPass(idClass).then((respone) => {
        dispatch(UpdateClass(respone.data.data));
      });
    } catch (error) {}
  };

  const handleUpdateStatusClass = async () => {
    try {
      let objApi = {
        idClass: idClass,
        status: classDetail.statusClass === 0 ? 1 : 0,
      };
      await TeacherMyClassAPI.updateStatusClass(objApi).then((respone) => {
        if (objApi.status === 0) {
          message.success("Mở lớp thành công !");
        } else {
          message.success("Khóa lớp thành công !");
        }
        dispatch(UpdateClass(respone.data.data));
      });
    } catch (error) {}
  };

  const copyToClipboard = () => {
    const tempInput = document.createElement("input");
    tempInput.value = classDetail.passWord;
    document.body.appendChild(tempInput);
    tempInput.select();
    tempInput.setSelectionRange(0, 99999);
    document.execCommand("copy");
    document.body.removeChild(tempInput);
    message.success("Đã sao chép mật khẩu vào bộ nhớ đệm !");
  };
  
  const convertLongToDate = (dateLong) => {
    const date = new Date(dateLong);
    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();
    const hour = String(date.getHours() + 1).padStart(2, "0");
    const minute = String(date.getMinutes() + 1).padStart(2, "0");
    const format = `${day}/${month}/${year}` + ` ${hour}:${minute}`;
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

  const imageUrl =
    "https://png.pngtree.com/background/20220714/original/pngtree-hand-drawn-blackboard-background-at-the-beginning-of-school-picture-image_1620722.jpg";
  const isOnlineImage =
    imageUrl.startsWith("http") || imageUrl.startsWith("https");
  return (
    <div className="teacher-post">
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
          <div>
            <div className="box-all">
              <div className="box-image">
                <span className="textCode"> {classDetail.code}</span>
              </div>
              <div className="box-post">
                <div className="box-post-left">
                  <div className="box-infor" style={{ height: "140px" }}>
                    <Row>
                      <span className="title-main">Tên lớp</span>
                    </Row>
                    <p className="infor-main">
                      {classDetail.code}
                      <FontAwesomeIcon
                        style={{ paddingLeft: "12px" }}
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
                      <div style={{ float: "left", paddingLeft: "3px" }}>
                        <span>Mã tham gia</span>
                      </div>
                      <div style={{ float: "right", paddingRight: "15%" }}>
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
                      </div>
                    </div>
                    <p className="infor-main" style={{ paddingTop: "20px" }}>
                      {classDetail.passWord}
                      <FontAwesomeIcon
                        style={{ paddingLeft: "12px" }}
                        icon={faExpand}
                        onClick={() => handleFullScreen(1)}
                      />
                    </p>
                  </div>
                </div>
                <div className="box-post-right">
                  {showCreate === true ? (
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
                          <Editor
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
                  <div
                    style={{
                      height: "auto",
                      margin: "20px 0 20px 0",
                      width: "100%",
                    }}
                  >
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
                                      <div
                                        style={{
                                          float: "left",
                                        }}
                                      >
                                        <span style={{ lineHeight: "50px" }}>
                                          {item.teacherName}
                                          <span
                                            style={{
                                              color: "gray",
                                              fontWeight: "initial",
                                              fontSize: "13px",
                                              marginLeft: "7px",
                                            }}
                                          >
                                            {convertLongToDate(
                                              item.createdDate
                                            )}
                                          </span>
                                        </span>
                                      </div>
                                      <div
                                        style={{ float: "right" }}
                                        className="title-icon-drop"
                                      >
                                        <Dropdown
                                          overlay={
                                            <Menu>
                                              <Menu.Item
                                                key="1"
                                                onClick={() =>
                                                  handleUpdate(index)
                                                }
                                              >
                                                Chỉnh sửa
                                              </Menu.Item>{" "}
                                              <Popconfirm
                                                title="Xóa bài viết"
                                                description="Bạn có chắc chắn muốn xóa bài viết này không ?"
                                                onConfirm={() => {
                                                  clickDelete(item.id);
                                                }}
                                                okText="Có"
                                                cancelText="Không"
                                              >
                                                <Menu.Item key="2">
                                                  Xóa
                                                </Menu.Item>
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
                      <div style={{ paddingTop: "50px" }}>
                        <Empty
                          imageStyle={{ height: "60px" }}
                          description={
                            <span>Chưa có bài viết nào được đăng</span>
                          }
                        />
                      </div>
                    )}
                    {seeMore && (
                      <Spin spinning={dowloading} style={{ marginTop: "10px" }}>
                        <Button
                          className="see-more"
                          style={{
                            float: "right",
                            backgroundColor: "rgb(38, 144, 214)",
                            color: "white",
                          }}
                          onClick={() => {
                            setDownloading(true);
                            setTimeout(() => {
                              setDownloading(false);
                              handleSeeMore();
                            }, 1000);
                          }}
                        >
                          <span
                            style={{
                              paddingRight: "7px",
                              color: "white",
                              fontWeight: "bold",
                            }}
                          >
                            Xem thêm{" "}
                          </span>
                          <FontAwesomeIcon icon={faAnglesRight} size="lg" />
                        </Button>{" "}
                      </Spin>
                    )}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default TeacherPostMyClass;
