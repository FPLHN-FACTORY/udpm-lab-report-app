import React, { useEffect, useState } from "react";
import JoditEditor from "jodit-react";
import { TeacherPostAPI } from "../../../../../api/teacher/post/TeacherPost.api";
import { CreatePost } from "../../../../../app/teacher/post/tePostSlice.reduce";
import { toast } from "react-toastify";
import { Button } from "antd";
import { useAppDispatch } from "../../../../../app/hook";

function Editor({ idTeacher, idClass, showCreate }) {
  const [descriptionss, setDescriptionss] = useState("<p><br></p>");
  const dispatch = useAppDispatch();

  const config = {
    readonly: false,
    showCharsCounter: false,
    showWordsCounter: false,
    showXPathInStatusbar: false,
    placeholder: "Nhập bài viết mới...",
    showFullscreen: false,
    showAbout: false,
    // enter: "Row",
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
          toast.success("Thêm bài viết thành công !");
        },
        (error) => {
          toast.error(error.response.data.message);
        }
      );
    }
  };
  // const insertBr = (value) => {
  //   const updatedValue = value.replace(/<\/p>/g, "</p></div><br/>");

  //   return updatedValue;
  // };
  // const insertDivAroundParagraphs = (value) => {
  //   const updatedValue = value
  //     .replace(/<p>/g, "<div><p>")
  //     .replace(/<\/p>/g, "</p></div>");
  //   return updatedValue;
  // };

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