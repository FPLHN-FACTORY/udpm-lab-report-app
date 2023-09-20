import { Link, useParams } from "react-router-dom";
import "./style-meeting-management.css";
import {
  BookOutlined,
  ControlOutlined,
  UnorderedListOutlined,
} from "@ant-design/icons";
import { Button, Empty, Popconfirm } from "antd";
import { MeetingManagementAPI } from "../../../../api/admin/meeting-management/MeetingManagementAPI";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import { useEffect, useState } from "react";
import {
  DeleteMeeting,
  GetMeeting,
  SetMeeting,
} from "../../../../app/admin/AdMeetingManagement.reducer";
import { convertLongToDate } from "../../../../helper/convertDate";
import {
  convertMeetingPeriod,
  convertMeetingPeriodToTime,
} from "../../../../helper/util.helper";
import LoadingIndicator from "../../../../helper/loading";
import ModalCreateMeeting from "./modal-create-meeting/ModalCreateMeeting";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faExchange,
  faPencilAlt,
  faPlus,
  faPlusCircle,
  faTrash,
} from "@fortawesome/free-solid-svg-icons";
import ModalUpdateMeeting from "./modal-update-meeting/ModalUpdateMeeting";
import { toast } from "react-toastify";
import { SetTTrueToggle } from "../../../../app/admin/AdCollapsedSlice.reducer";

const MeetingManagment = () => {
  const { id } = useParams();

  const dispatch = useAppDispatch();
  dispatch(SetTTrueToggle());
  const [isLoading, setIsLoading] = useState(false);

  const loadDataMeeting = () => {
    MeetingManagementAPI.getAllMeetingByIdClass(id).then(
      (response) => {
        dispatch(SetMeeting(response.data.data));
        setIsLoading(false);
      },
      (error) => {
        console.log(error);
      }
    );
  };

  useEffect(() => {
    setIsLoading(true);
    loadDataMeeting();
  }, [dispatch]);

  const data = useAppSelector(GetMeeting);

  const [showModalCreateMeeting, setShowModalCreateMeeting] = useState(false);

  const handleCancelModalCreateMeeting = () => {
    setShowModalCreateMeeting(false);
  };

  const openModalCreateMeeting = () => {
    setShowModalCreateMeeting(true);
  };

  const [showModalUpdateMeeting, setShowModalUpdateMeeting] = useState(false);
  const [meetingSelected, setMeetingSelected] = useState(null);

  const handleCancelModalUpdateMeeting = () => {
    setShowModalUpdateMeeting(false);
    setMeetingSelected(null);
  };

  const openModalUpdateMeeting = (item) => {
    setMeetingSelected(item);
    setShowModalUpdateMeeting(true);
  };

  const deleteMeeting = (id) => {
    MeetingManagementAPI.deleteMeeting(id).then(
      (response) => {
        dispatch(DeleteMeeting(response.data.data));
        toast.success("Xóa thành công");
      },
      (error) => {
        toast.error(error.data.response.message);
      }
    );
  };

  return (
    <div style={{ paddingTop: "35px" }}>
      {isLoading && <LoadingIndicator />}
      <div className="title-meeting-managemnt-my-class">
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
          <span style={{ color: "gray" }}> - Quản lý lịch học</span>
        </span>
      </div>
      <div className="box-filter-meeting-management">
        <div
          className="box-filter-meeting-management-son"
          style={{ minHeight: "600px" }}
        >
          <div className="button-menu-teacher">
            <div>
              <Link
                to={`/admin/class-management/information-class/${id}`}
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
                id="menu-checked"
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                QUẢN LÝ LỊCH HỌC &nbsp;
              </Link>
              <Link
                className="custom-link"
                to={`/admin/class-management/feedback/${id}`}
                style={{
                  fontSize: "16px",
                  paddingLeft: "10px",
                  fontWeight: "bold",
                }}
              >
                FEEDBACK CỦA SINH VIÊN &nbsp;
              </Link>
              <hr />
            </div>
          </div>
          <div className="menu-teacher-search">
            <div
              style={{
                display: "flex",
                justifyContent: "space-between",
                marginTop: "20px",
              }}
            >
              <div>
                {" "}
                <span style={{ fontSize: "17px", fontWeight: "500" }}>
                  {" "}
                  <UnorderedListOutlined
                    style={{ marginRight: "10px", fontSize: "20px" }}
                  />
                  Danh sách lịch học:{" "}
                  <span
                    style={{
                      padding: "5px",
                      backgroundColor: "#007bff",
                      color: "white",
                      fontSize: "15px",
                      borderRadius: "5px",
                    }}
                  >
                    {" "}
                    {data != null ? data.length : 0} buổi học{" "}
                  </span>
                </span>
              </div>{" "}
              <div
                style={{
                  fontSize: "14px",
                  float: "right",
                  display: "flex",
                  alignItems: "center",
                }}
              >
                {" "}
                <Button
                  style={{ marginLeft: "10px" }}
                  className="btn-create-meeting"
                >
                  {" "}
                  <FontAwesomeIcon
                    icon={faExchange}
                    style={{ marginRight: "5px" }}
                  />
                  Đổi giảng viên
                </Button>
                <Button
                  style={{ marginLeft: "10px" }}
                  className="btn-create-meeting"
                >
                  {" "}
                  <FontAwesomeIcon
                    icon={faPlusCircle}
                    style={{ marginRight: "5px" }}
                  />
                  Tạo buổi học tự động
                </Button>
                <Button
                  style={{ marginLeft: "10px" }}
                  className="btn-create-meeting"
                  onClick={openModalCreateMeeting}
                >
                  <FontAwesomeIcon
                    icon={faPlus}
                    style={{ marginRight: "5px" }}
                  />
                  Thêm buổi học
                </Button>
              </div>
            </div>
            <div className="data-table">
              {data != null &&
                data.length > 0 &&
                data.map((item) => (
                  <div>
                    <div tabIndex={0} role="button" className="box-card">
                      <div className="title-left">
                        <Link>
                          <div className="box-icon">
                            <BookOutlined
                              style={{ color: "white", fontSize: 21 }}
                            />
                          </div>
                          <span
                            style={{
                              fontSize: "16px",
                              color: "black",
                            }}
                          >
                            {item.name} -{" "}
                            {item.typeMeeting === 0 ? "Online " : "Offline "} -{" "}
                            <span style={{ color: "red" }}>
                              {item.userNameTeacher}{" "}
                            </span>{" "}
                            -{" "}
                            <span style={{ fontSize: "15px" }}>
                              Sĩ số điểm danh:{" "}
                              <span style={{ color: "red" }}>15/20</span>
                            </span>
                          </span>
                        </Link>
                      </div>
                      <div className="title-right">
                        <div>
                          {" "}
                          <span>
                            {" "}
                            Ngày dạy:{" "}
                            <span
                              style={{
                                color: "red",
                                fontWeight: "500",
                              }}
                            >
                              {convertLongToDate(item.meetingDate)} -{" "}
                              {convertMeetingPeriod(item.meetingPeriod) + " "}(
                              {"" +
                                convertMeetingPeriodToTime(item.meetingPeriod)}
                              )
                            </span>
                          </span>
                        </div>
                      </div>
                      <div className="icon-right">
                        <FontAwesomeIcon
                          icon={faPencilAlt}
                          style={{
                            marginRight: "10px",
                            cursor: "pointer",
                            color: "#007bff",
                          }}
                          onClick={() => {
                            openModalUpdateMeeting(item);
                          }}
                        />
                        <Popconfirm
                          title="Xóa buổi học"
                          description="Bạn có chắc chắn muốn xóa buổi học này không?"
                          onConfirm={() => {
                            deleteMeeting(item.id);
                          }}
                          okText="Yes"
                          cancelText="No"
                        >
                          <FontAwesomeIcon
                            icon={faTrash}
                            style={{
                              marginRight: "10px",
                              marginLeft: "5px",
                              cursor: "pointer",
                              color: "#007bff",
                            }}
                          />
                        </Popconfirm>
                      </div>
                    </div>{" "}
                  </div>
                ))}
              {(data == null || data.length === 0) && (
                <div style={{ textAlign: "center", marginTop: "15px" }}>
                  <Empty
                    imageStyle={{ height: 60 }}
                    style={{
                      padding: "20px 0px 20px 0",
                    }}
                    description={<span>Không có buổi học nào</span>}
                  />
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
      <ModalCreateMeeting
        visible={showModalCreateMeeting}
        onCancel={handleCancelModalCreateMeeting}
      />
      <ModalUpdateMeeting
        item={meetingSelected}
        visible={showModalUpdateMeeting}
        onCancel={handleCancelModalUpdateMeeting}
      />
    </div>
  );
};

export default MeetingManagment;
