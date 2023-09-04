import React from "react";
import {
  Modal,
  Row,
  Col,
  Input,
  Button,
  Select,
  Space,
  Tooltip,
  Table,
  Dropdown,
  Menu,
} from "antd";
import { useEffect, useState } from "react";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
import LoadingIndicator from "../../../../../helper/loading";
import RichTextEditor from "../rich-teach-edit/rich-teach-editor";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEllipsisVertical } from "@fortawesome/free-solid-svg-icons";
const { Option } = Select;

const ModalCreatePostNew = ({ visible, onCancel, idClass }) => {
  const [descriptions, setCode] = useState("");
  const [loading, setLoading] = useState(false);
  const dispatch = useAppDispatch();

  // const featchAllMyClass = async (giangVienCurrent) => {
  //   setLoading(false);
  //   let filter = {
  //     idTeacher: giangVienCurrent.id,
  //     idActivity: idActivitiSearch,
  //     idSemester: idSemesterSeach,
  //     code: codeSearch,
  //     name: nameSearch,
  //     classPeriod: classPeriodSearch,
  //     level: levelSearch,
  //     page: current,
  //     size: 10,
  //   };
  //   try {
  //     await TeacherMyClassAPI.getAllMyClass(filter).then((respone) => {
  //       dispatch(SetTeacherMyClass(respone.data.data));
  //       setTotalPages(parseInt(respone.data.data.totalPages));
  //       setListMyClass(respone.data.data.data);
  //       setLoading(true);
  //     });
  //   } catch (error) {
  //     alert("Vui lòng F5 lại trang !");
  //   }
  // };

  const cancelSuccess = () => {
    onCancel.handleCancelModalCreateSusscess();
  };
  const cancelFaild = () => {
    onCancel.handleCancelModalCreateFaild();
  };
  const menu = (
    <Menu>
      <Menu.Item key="1">Chỉnh sửa</Menu.Item>
      <Menu.Item key="2">Xóa</Menu.Item>
    </Menu>
  );

  const create = () => {
    let check = 0;
    if (descriptions.trim() === "") {
      check++;
    }

    // if (check === 0) {
    //   let teamNew = {
    //     code: code,
    //     name: name,
    //     classId: idClass + ``,
    //   };
    //   TeacherTeamsAPI.createTeam(teamNew).then(
    //     (respone) => {
    //       toast.success("Thêm thành công !");

    //       cancelSuccess();
    //     },
    //     (error) => {
    //       toast.error(error.response.data.message);
    //     }
    //   );
    // }
  };

  return (
    <>
      {loading && <LoadingIndicator />}
      <Modal
        open={visible}
        onCancel={cancelFaild}
        width={850}
        footer={null}
        bodyStyle={{ overflow: "hidden" }}
      >
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>Thêm bài viết </span>
        </div>
        <>
          <div style={{ marginTop: "15px", borderBottom: "1px solid black" }}>
            <Row>
              <span>Dành cho</span>
              <Dropdown overlay={menu} trigger={["click"]} className="box-drop">
                <div
                  className="box-icon-drop"
                  style={{ backgroundColor: "white" }}
                >
                  <FontAwesomeIcon icon={faEllipsisVertical} />
                </div>
              </Dropdown>
            </Row>
            <Row gutter={16} style={{ marginBottom: "15px" }}>
              <RichTextEditor />
            </Row>
          </div>
        </>
        <div
          style={{
            textAlign: "right",
            marginTop: "20px",
          }}
        >
          <>
            <div style={{ paddingTop: "15px" }}>
              <Button
                style={{
                  backgroundColor: "rgb(61, 139, 227)",
                  color: "white",
                }}
                onClick={create}
              >
                Thêm
              </Button>
              <Button
                style={{
                  backgroundColor: "red",
                  color: "white",
                }}
                onClick={cancelFaild}
              >
                Hủy
              </Button>
            </div>
          </>
        </div>
      </Modal>
    </>
  );
};

export default ModalCreatePostNew;
