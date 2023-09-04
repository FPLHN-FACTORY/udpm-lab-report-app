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
  // const handleChange = (value) => {
  //   value = value.replace(/<ol>/g, "<ul>").replace(/<\/ol>/g, "</ul>");
  //   setContent(value);
  //   descriptions(value);
  // };
  const handleChange = (value) => {
    setContent(value);
    descriptions(value);
  };

  return (
    <div style={{ width: "100%", minHeight: "200px" }}>
      <ReactQuill ref={quillRef} value={content} onChange={handleChange} />
    </div>
  );
};

export default RichTextEditor;
