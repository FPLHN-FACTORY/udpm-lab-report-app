import React, { useEffect, useState } from "react";
import JoditEditor from "jodit-react";
import { TeacherPostAPI } from "../../../../../api/teacher/post/TeacherPost.api";
import { CreatePost } from "../../../../../app/teacher/post/tePostSlice.reduce";
import { Button, message } from "antd";
import { useAppDispatch, useAppSelector } from "../../../../../app/hook";
import { TeacherStudentClassesAPI } from "../../../../../api/teacher/student-class/TeacherStudentClasses.api";
import { TeacherMailAPI } from "../../../../../api/teacher/sent-mail/TeacherMailAPI.api";
import { GetUserCurrent } from "../../../../../app/common/UserCurrent.reducer";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../../app/common/Loading.reducer";
function Editor({ idClass, showCreate }) {
  const [descriptionss, setDescriptionss] = useState("<p><br></p>");
  const dispatch = useAppDispatch();
  const [listMail, setListMail] = useState([]);
  const [isFullscreen, setIsFullscreen] = useState(false);

  useEffect(() => {
    featchStudentClass(idClass);
    const handleFullscreenChange = () => {
      setIsFullscreen(document.fullscreenElement !== null);
    };

    document.addEventListener("fullscreenchange", handleFullscreenChange);

    return () => {
      document.removeEventListener("fullscreenchange", handleFullscreenChange);
    };
  }, []);

  const config = {
    readonly: false,
    showCharsCounter: false,
    showWordsCounter: false,
    showXPathInStatusbar: false,
    placeholder: "Nhập bài viết mới...",
    showFullscreen: !isFullscreen,
    showAbout: false,
  };

  const create = () => {
    let empty = 0;
    dispatch(SetLoadingTrue());
    if (descriptionss.trim() === "<p><br></p>") {
      message.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptionss.trim() === "<p><br></p><br>") {
      message.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptionss.trim() === "<ul><li><br></li><br></ul>") {
      message.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptionss.trim() === "<ul><li><br></li><br></ul><br>") {
      message.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptionss.trim() === "<ol><li><br></li><br></ol>") {
      message.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptionss.trim() === "<ol><li><br></li><br></ol><br>") {
      message.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (empty === 0) {
      let obj = {
        descriptions: descriptionss,
        idClass: idClass,
      };
      TeacherPostAPI.create(obj).then(
        (respone) => {
          setDescriptionss("");
          showCreate(false);
          dispatch(CreatePost(respone.data.data));
          featchStudentClass(idClass);
          message.success("Thêm bài viết thành công !");
          featchSentMaillToStudent(obj.descriptions);
          dispatch(SetLoadingFalse());
        },
        (error) => {
          dispatch(SetLoadingFalse());
          message.error(error.response.data.message);
        }
      );
    }
  };

  const featchStudentClass = async (idClass) => {
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(idClass).then(
        (responese) => {
          if (responese.data.data != null) {
            const emailList = responese.data.data.map((item) => item.email);
            setListMail(emailList);
          }
        }
      );
    } catch (error) {}
  };
  const userRedux = useAppSelector(GetUserCurrent);
  const featchSentMaillToStudent = async (body) => {
    try {
      let data = {
        titleEmail:
          "Giảng viên " + userRedux.name + " đã thêm một thông báo mới.",
        subject: "Thông báo bài viết mới",
        toEmail: listMail,
        body: body,
      };
      await TeacherMailAPI.sentMaillTeacherPostToStudent(data);
    } catch (error) {}
  };
  return (
    <div>
      <JoditEditor
        value={descriptionss || "<p><br></p>"}
        config={config}
        onBlur={(value) => {
          setDescriptionss(value);
        }}
      />
      <div style={{ paddingTop: "15px", float: "right", right: 0 }}>
        <Button
          className="btn_filter"
          style={{
            width: "66px",
          }}
          onClick={(e) => {
            showCreate(false);
            setDescriptionss("");
          }}
        >
          Hủy
        </Button>
        <Button
          className="btn_clean"
          style={{
            width: "66px",
            marginLeft: "10px",
          }}
          onClick={create}
        >
          Lưu
        </Button>
      </div>
    </div>
  );
}

export default Editor;
