import React from "react";
import JoditEditor from "jodit-react";
import "jodit/build/jodit.min.css";
import { memo } from "react";
import "./styleViewEditorJodit.css";

const ViewEditorJodit = ({ value }) => {
  return (
    <div style={{ minHeight: "70px", position: "relative" }}>
      <JoditEditor
        value={value}
        tabIndex={-1}
        style={{ backgroundColor: "white" }}
        className="view_editor_jodit_abc"
        config={{
          readonly: true,
          toolbar: false,
          showCharsCounter: false,
          showWordsCounter: false,
          showStatusbar: true,
          showPoweredBy: false,
        }}
      />
    </div>
  );
};

export default memo(ViewEditorJodit);
