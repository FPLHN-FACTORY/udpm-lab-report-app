import React, { useState } from "react";
import JoditEditor from "jodit-react";

function Editor({ descriptions }) {
  const [content, setContent] = useState("");

  const config = {
    readonly: false,
    showCharsCounter: false,
    showWordsCounter: false,
    showXPathInStatusbar: false,
    placeholder: "Nhập bài viết",
    showFullscreen: false,
    showAbout: false,
  };

  return (
    <div>
      <JoditEditor
        value={content}
        config={config}
        onBlur={(newContent) => {
          setContent(newContent);
          descriptions(newContent);
        }}
      />
    </div>
  );
}

export default Editor;
