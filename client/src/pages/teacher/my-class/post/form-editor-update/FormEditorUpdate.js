import React, { useState } from "react";
import JoditEditor from "jodit-react";
import { TeacherPostAPI } from "../../../../../api/teacher/post/TeacherPost.api";
import { UpdatePost } from "../../../../../app/teacher/post/tePostSlice.reduce";
import { toast } from "react-toastify";
import { Button } from "antd";
import { useAppDispatch } from "../../../../../app/hook";
import { useEffect } from "react";

function EditorUpdate({ obj, showUpdate }) {
  const [descriptions, setDescriptions] = useState("");
  const [errorDescriptions, setErrorDescriptions] = useState("");
  const dispatch = useAppDispatch();
  useEffect(() => {
    setDescriptions(obj.descriptions);
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

  const update = () => {
    let check = 0;
    if (descriptions.trim() === "") {
      setErrorDescriptions(" Nội dung không được để trống !");
      check++;
    } else {
      setErrorDescriptions("");
    }
    if (check === 0) {
      let objUpdate = {
        id: obj.id,
        descriptions: descriptions,
      };
      TeacherPostAPI.update(objUpdate).then(
        (respone) => {
          setDescriptions("");
          showUpdate(false);
          dispatch(UpdatePost(respone.data.data));
          toast.success("Sửa bài viết thành công !");
        },
        (error) => {
          toast.error(error.response.data.message);
        }
      );
    }
  };
  const handleEditorBlurUpdate = (value) => {
    if (value.trim() === "") {
      setErrorDescriptions("Nội dung không được để trống !");
    } else {
      setErrorDescriptions("");
      setDescriptions(value);
    }
  };
  return (
    <div>
      <JoditEditor
        value={descriptions}
        config={config}
        onBlur={(value) => {
          setDescriptions(value);
          handleEditorBlurUpdate(value);
        }}
      />
      <span style={{ color: "red" }}>{errorDescriptions}</span>
      <div style={{ paddingTop: "15px", float: "right" }}>
        <Button
          style={{
            backgroundColor: "red",
            color: "white",
          }}
          onClick={() => {
            showUpdate(false);
            setDescriptions("");
          }}
        >
          Hủy
        </Button>
        <Button
          style={{
            backgroundColor: "rgb(61, 139, 227)",
            color: "white",
          }}
          onClick={update}
        >
          Sửa
        </Button>
      </div>
    </div>
  );
}

export default EditorUpdate;
