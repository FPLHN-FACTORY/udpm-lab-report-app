import React, { Component } from "react";
import { Editor, EditorState } from "draft-js";

class MyEditor extends Component {
  constructor(props) {
    super(props);
    this.state = {
      editorState: EditorState.createEmpty(), // Tạo một trạng thái ban đầu trống
    };
  }

  onChange = (editorState) => {
    this.setState({ editorState });
  };

  render() {
    return (
      <div>
        <Editor editorState={this.state.editorState} onChange={this.onChange} />
      </div>
    );
  }
}

export default MyEditor;
