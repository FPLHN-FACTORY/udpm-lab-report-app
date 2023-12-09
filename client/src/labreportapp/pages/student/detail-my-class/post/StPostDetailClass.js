import "./style-post-detail-class.css";
import { Button, Card, Empty, Spin } from "antd";
import { useEffect } from "react";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { StudentPostAPI } from "../../../../api/student/StPostAPI";
import LoadingIndicator from "../../../../helper/loading";
import { TeacherMyClassAPI } from "../../../../api/teacher/my-class/TeacherMyClass.api";
import { Link } from "react-router-dom";
import {
  GetPost,
  NextPagePost,
  SetPost,
} from "../../../../app/student/StPost.reduce";
import ViewEditorJodit from "../../../../helper/editor/ViewEditorJodit";
import { ControlOutlined } from "@ant-design/icons";
import { SetTTrueToggle } from "../../../../app/student/StCollapsedSlice.reducer";
import { GetUserCurrent } from "../../../../app/common/UserCurrent.reducer";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAnglesRight } from "@fortawesome/free-solid-svg-icons";
import { convertDateLongToString } from "../../../../helper/util.helper";
import { StClassAPI } from "../../../../api/student/StClassAPI";

const StPostDetailClass = () => {
  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const { id } = useParams();
  const [classDetail, setClassDetail] = useState({});
  const [loading, setLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPage, setTotalPage] = useState(0);
  const [seeMore, setSeeMore] = useState(true);
  const [dowloading, setDownloading] = useState(false);
  const navigate = useNavigate();

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
        size: 8,
      };
      await StudentPostAPI.getPagePost(data).then((responese) => {
        dispatch(SetPost(responese.data.data.data));
        setTotalPage(responese.data.data.totalPages);
        if (responese.data.data.totalPages === 0) {
          setSeeMore(false);
        }
        setLoading(true);
      });
    } catch (error) {}
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
        size: 8,
      };
      await StudentPostAPI.getPagePost(data).then((responese) => {
        dispatch(NextPagePost(responese.data.data.data));
        setTotalPage(responese.data.data.totalPages);
      });
    } catch (error) {}
  };
  const featchClass = async (idClass) => {
    try {
      await StClassAPI.detailMyClass(idClass).then((responese) => {
        setClassDetail(responese.data.data);
      });
    } catch (error) {
    }
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
  const userRedux = useAppSelector(GetUserCurrent);
  return (
    <>
      {!loading && <LoadingIndicator />}
      <div style={{ paddingTop: "35px" }} className="student-post">
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
            <div>
              <div className="box-all">
                <div className="box-image">
                  <span className="textCode"> {classDetail.code}</span>
                </div>
                <div className="box-post">
                  <div className="box-post">
                    <div
                      style={{
                        height: "auto",
                        margin: "5px 0 20px 0",
                        width: "80%",
                      }}
                    >
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
                                      <div
                                        style={{ width: "95%", float: "left" }}
                                      >
                                        <span style={{ lineHeight: "50px" }}>
                                          {" "}
                                          {item.teacherName}
                                          <span
                                            style={{
                                              color: "gray",
                                              fontWeight: "initial",
                                              fontSize: "13px",
                                              marginLeft: "7px",
                                            }}
                                          >
                                            {convertDateLongToString(
                                              item.createdDate
                                            )}
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
                        <Empty
                          imageStyle={{ height: 60 }}
                          description={
                            <span>Chưa có bài viết nào được đăng</span>
                          }
                        />
                      )}
                      {seeMore && (
                        <Spin
                          spinning={dowloading}
                          style={{ marginTop: "10px" }}
                        >
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
                  </div>{" "}
                </div>{" "}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default StPostDetailClass;
