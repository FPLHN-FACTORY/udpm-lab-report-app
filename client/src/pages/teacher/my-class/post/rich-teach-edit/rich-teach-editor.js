import React, { useState } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

const RichTextEditor = () => {
  const [content, setContent] = useState("");

  const handleChange = (value) => {
    setContent(value);
  };

  return (
    <div className="rich-text-editor" style={{ width: "100%" }}>
      <ReactQuill value={content} onChange={handleChange} />
    </div>
  );
};

export default RichTextEditor;
