import React, { useEffect, useState } from "react";
import JoditEditor from "jodit-react";
import { TeacherPostAPI } from "../../../../../api/teacher/post/TeacherPost.api";
import { CreatePost } from "../../../../../app/teacher/post/tePostSlice.reduce";
import { toast } from "react-toastify";
import { Button } from "antd";
import { useAppDispatch } from "../../../../../app/hook";
import { TeacherStudentClassesAPI } from "../../../../../api/teacher/student-class/TeacherStudentClasses.api";
import { TeacherMailAPI } from "../../../../../api/teacher/sent-mail/TeacherMailAPI.api";
import { giangVienCurrent } from "../../../../../helper/inForUser";
function Editor({ idTeacher, idClass, showCreate }) {
  const [descriptionss, setDescriptionss] = useState("<p><br></p>");
  const dispatch = useAppDispatch();
  const [listMail, setListMail] = useState([]);
  useEffect(() => {
    featchStudentClass(idClass);
  }, []);
  const config = {
    readonly: false,
    showCharsCounter: false,
    showWordsCounter: false,
    showXPathInStatusbar: false,
    placeholder: "Nhập bài viết mới...",
    showFullscreen: false,
    showAbout: false,
  };
  const create = () => {
    let empty = 0;
    if (descriptionss.trim() === "<p><br></p>") {
      toast.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptionss.trim() === "<p><br></p><br>") {
      toast.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptionss.trim() === "<ul><li><br></li><br></ul>") {
      toast.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptionss.trim() === "<ul><li><br></li><br></ul><br>") {
      toast.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptionss.trim() === "<ol><li><br></li><br></ol>") {
      toast.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptionss.trim() === "<ol><li><br></li><br></ol><br>") {
      toast.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (empty === 0) {
      let obj = {
        idTeacher: idTeacher,
        descriptions: descriptionss,
        idClass: idClass,
      };
      TeacherPostAPI.create(obj).then(
        (respone) => {
          setDescriptionss("");
          showCreate(false);
          dispatch(CreatePost(respone.data.data));
          featchStudentClass(idClass);
          toast.success("Thêm bài viết thành công !");
          featchSentMaillToStudent(obj.descriptions);
        },
        (error) => {
          toast.error(error.response.data.message);
        }
      );
    }
  };
  const featchStudentClass = async (idClass) => {
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(idClass).then(
        (responese) => {
          const emailList = responese.data.data.map((item) => item.email);
          setListMail(emailList);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const featchSentMaillToStudent = async (body) => {
    try {
      let data = {
        titleEmail:
          "Giảng viên " + giangVienCurrent.name + " đã thêm một thông báo mới.",
        subject: "Thông báo bài viết mới",
        toEmail: listMail,
        body: body,
      };
      await TeacherMailAPI.sentMaillTeacherPostToStudent(data).then(
        (response) => {}
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  return (
    <div>
      <JoditEditor
        value={descriptionss}
        config={config}
        onBlur={(value) => {
          setDescriptionss(value);
        }}
      />
      <div style={{ paddingTop: "15px", float: "right" }}>
        <Button
          style={{
            backgroundColor: "red",
            color: "white",
          }}
          onClick={(e) => {
            showCreate(false);
            setDescriptionss("");
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
  );
}

export default Editor;
