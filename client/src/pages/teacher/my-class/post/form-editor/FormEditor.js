import React, { useState } from "react";
import JoditEditor from "jodit-react";
import { TeacherPostAPI } from "../../../../../api/teacher/post/TeacherPost.api";
import { CreatePost } from "../../../../../app/teacher/post/tePostSlice.reduce";
import { toast } from "react-toastify";
import { Button } from "antd";
import { useAppDispatch } from "../../../../../app/hook";

function Editor({ idTeacher, idClass, showCreate }) {
  const [descriptionss, setDescriptionss] = useState("");
  const [errorDescriptions, setErrorDescriptions] = useState("");
  const dispatch = useAppDispatch();

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
    let check = 0;
    if (descriptionss.trim() === "") {
      setErrorDescriptions("Nội dung không được để trống !");
      check++;
    } else {
      setErrorDescriptions("");
    }
    if (check === 0) {
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

  const handleEditorBlur = (value) => {
    if (value.trim() === "") {
      setErrorDescriptions("Nội dung không được để trống !");
    } else {
      setErrorDescriptions("");
      setDescriptionss(value);
    }
  };

  return (
    <div>
      <JoditEditor
        value={descriptionss}
        config={config}
        onBlur={(value) => {
          setDescriptionss(value);
          handleEditorBlur(value);
        }}
      />
      <span style={{ color: "red" }}>{errorDescriptions}</span>
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
