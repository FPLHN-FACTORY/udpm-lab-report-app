import "./style-post-detail-class.css";
import { Button, Card, Dropdown, Menu, Modal, Row } from "antd";
import { useEffect } from "react";
import { useState } from "react";
import { useParams } from "react-router";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { StudentPostAPI } from "../../../../api/student/StPostAPI";
import LoadingIndicator from "../../../../helper/loading";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEllipsisVertical, faHome } from "@fortawesome/free-solid-svg-icons";
import {
  GetPost,
  NextPagePost,
  SetPost,
} from "../../../../app/student/StPost.reduce";
import { toast } from "react-toastify";
import JoditEditor from "jodit-react";
import ViewEditorJodit from "../../../../helper/editor/ViewEditorJodit";
import { ControlOutlined } from "@ant-design/icons";
import { giangVienCurrent } from "../../../../helper/inForUser";
import { SetTTrueToggle } from "../../../../app/student/StCollapsedSlice.reducer";

const StPostDetailClass = () => {
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const { id } = useParams();
  const [classDetail, setClassDetail] = useState({});
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPage, setTotalPage] = useState(0);
  const [seeMore, setSeeMore] = useState(true);

  useEffect(() => {
    window.scrollTo(0, 0);
    document.title = "Bảng điều khiển - Bài đăng";
    featchClass(id);
    featchPost(id);
  }, []);

  const featchPost = async (id) => {
    setLoading(false);
    try {
      let data = {
        idClass: id,
        page: currentPage,
        idTeacher: giangVienCurrent.id,
        size: 8,
      };
      await StudentPostAPI.getPagePost(data).then((responese) => {
        dispatch(SetPost(responese.data.data.data));

        setTotalPage(responese.data.data.totalPages);
        setLoading(true);
      });
    } catch (error) {
      alert(error.message);
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
    featchNextPost(id);
  }, [currentPage]);
  const featchNextPost = async (id) => {
    try {
      let data = {
        idClass: id,
        page: currentPage,
        idTeacher: giangVienCurrent.id,
        size: 8,
      };
      await StudentPostAPI.getPagePost(data).then((responese) => {
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
        console.log(responese.data.data);
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const convertLongToDate = (longValue) => {
    const date = new Date(longValue);
    const format = `${date.getDate()}/${
      date.getMonth() + 1
    }/${date.getFullYear()} ${date.getHours()}:${date.getMinutes()} `;
    return format;
  };
  const handleSeeMore = () => {
    if (currentPage < totalPage) {
      setCurrentPage((pre) => pre + 1);
      setSeeMore(true);
    } else {
      setSeeMore(false);
    }
  };

  const data = useAppSelector(GetPost);
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div style={{ paddingTop: "35px" }}>
        <div className="title-student-my-class">
          <span style={{ paddingLeft: "20px" }}>
            <ControlOutlined style={{ fontSize: "22px" }} />
            <span
              style={{
                fontSize: "18px",
                marginLeft: "10px",
                fontWeight: "500",
              }}
            >
              Bảng điều khiển
            </span>
            <span style={{ color: "gray" }}> - Bài đăng</span>
          </span>
        </div>
        <div
          className="box-students-detail-my-class"
          style={{ padding: "20px" }}
        >
          <div
            className="button-menu-student-detail-my-class"
            style={{ minHeight: "600px" }}
          >
            <div>
              <Link
                id="menu-checked"
                to={`/student/my-class/post/${id}`}
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                  fontWeight: "bold",
                }}
              >
                BÀI ĐĂNG
              </Link>
              <Link
                to={`/student/my-class/team/${id}`}
                className="custom-link"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                  fontWeight: "bold",
                }}
              >
                THÔNG TIN LỚP HỌC
              </Link>
              <Link
                className="custom-link"
                to={`/student/my-class/meeting/${id}`}
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                  fontWeight: "bold",
                }}
              >
                DANH SÁCH BUỔI HỌC
              </Link>
              <Link
                className="custom-link"
                to={`/student/my-class/attendance/${id}`}
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                  fontWeight: "bold",
                }}
              >
                ĐIỂM DANH
              </Link>
              <Link
                className="custom-link"
                to={`/student/my-class/point/${id}`}
                style={{
                  fontSize: "16px",
                  fontWeight: "bold",
                  paddingLeft: "10px",
                  paddingRight: "10px",
                }}
              >
                ĐIỂM
              </Link>
              <hr />
            </div>
            <div className="box-image">
              <h1 className="textCode"> {classDetail.code}</h1>
            </div>
            <div className="box-post">
              <div className="box-post">
                <div style={{ height: "auto", margin: "20px 0 20px 0" }}>
                  {data.length > 0 ? (
                    data.map((item) => {
                      return (
                        <div key={item.id} style={{ marginBottom: "20px" }}>
                          <Card
                            className="box-card-one"
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
                                          marginLeft: "7px",
                                        }}
                                      >
                                        {convertLongToDate(item.createdDate)}
                                      </span>
                                    </span>
                                  </div>{" "}
                                  <div
                                    style={{ float: "left" }}
                                    className="title-icon-drop"
                                  ></div>
                                </div>
                              </>
                            }
                          >
                            <ViewEditorJodit value={item.descriptions} />
                          </Card>
                        </div>
                      );
                    })
                  ) : (
                    <div style={{ width: "100%", textAlign: "center" }}>
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
                    </div>
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
      </div>
    </>
  );
};

export default StPostDetailClass;
