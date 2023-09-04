import React, { useEffect, useRef, useState } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

const RichTextEditor = ({ descriptions }) => {
  const [content, setContent] = useState("");
  const quillRef = useRef(null);

  useEffect(() => {
    if (quillRef.current) {
      const quillEditor = quillRef.current.getEditor();
      quillEditor.focus();
    }
  }, []);
  const handleChange = (value) => {
    setContent(value);
    descriptions(value);
  };
  const formats = [
    "header",
    "font",
    "size", // Thêm định dạng kích thước chữ
    "bold",
    "italic",
    "underline",
    "list",
    "link",
  ];

  const modules = {
    toolbar: {
      container: [
        [
          { header: "1" },
          { header: "2" },
          { font: ["Arial", "Times New Roman"] },
        ],
        [{ list: "ordered" }, { list: "bullet" }],
        ["bold", "italic", "underline"],
        ["link"],
        ["size"], // Thêm tùy chọn kích thước chữ
      ],
    },
  };
  return (
    <div style={{ width: "100%", minHeight: "200px" }}>
      <ReactQuill
        ref={quillRef}
        value={content}
        onChange={handleChange}
        modules={modules}
        formats={formats}
      />
    </div>
  );
};

export default RichTextEditor;
