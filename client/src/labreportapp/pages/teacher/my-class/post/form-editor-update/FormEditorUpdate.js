import React, { useState } from "react";
import JoditEditor from "jodit-react";
import { TeacherPostAPI } from "../../../../../api/teacher/post/TeacherPost.api";
import { UpdatePost } from "../../../../../app/teacher/post/tePostSlice.reduce";
import { toast } from "react-toastify";
import { Button, message } from "antd";
import { useAppDispatch } from "../../../../../app/hook";
import { useEffect } from "react";

function EditorUpdate({ obj, showUpdate }) {
  const [descriptions, setDescriptions] = useState("");
  const dispatch = useAppDispatch();
  useEffect(() => {
    setDescriptions(obj.descriptions);
  }, []);
  const config = {
    readonly: false,
    showCharsCounter: false,
    showWordsCounter: false,
    showXPathInStatusbar: false,
    placeholder: "Chỉnh sửa bài viết...",
    showFullscreen: false,
    showAbout: false,
  };
  const update = () => {
    let empty = 0;
    if (descriptions.trim() === "<p><br></p>") {
      message.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptions.trim() === "<p><br></p><br>") {
      message.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptions.trim() === "<ul><li><br></li><br></ul>") {
      message.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptions.trim() === "<ul><li><br></li><br></ul><br>") {
      message.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptions.trim() === "<ol><li><br></li><br></ol>") {
      message.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (descriptions.trim() === "<ol><li><br></li><br></ol><br>") {
      message.error("Nội dung bài viết không được trống !");
      empty++;
    }
    if (empty === 0) {
      let objUpdate = {
        id: obj.id,
        descriptions: descriptions,
      };
      TeacherPostAPI.update(objUpdate).then(
        (respone) => {
          setDescriptions("");
          showUpdate(false);
          dispatch(UpdatePost(respone.data.data));
          message.success("Sửa bài viết thành công !");
        },
        (error) => {
          message.error(error.response.data.message);
        }
      );
    }
  };
  return (
    <div>
      <JoditEditor
        value={descriptions}
        config={config}
        onBlur={(value) => {
          setDescriptions(value);
        }}
      />
      <div style={{ paddingTop: "15px", float: "right", right: 0 }}>
        <Button
          className="btn_filter"
          style={{
            width: "100px",
          }}
          onClick={(e) => {
            showUpdate(false);
          }}
        >
          Hủy
        </Button>
        <Button
          className="btn_clean"
          style={{
            width: "100px",
            marginLeft: "10px",
          }}
          onClick={update}
        >
          Cập nhật
        </Button>
      </div>
    </div>
  );
}
export default EditorUpdate;
