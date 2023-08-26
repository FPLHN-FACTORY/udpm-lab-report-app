import React, { useEffect, useState } from "react";
import "./CustomSwitch.css"; // Import your custom CSS

function CustomSwitch({ leftLabel, rightLabel, status }) {
  const [checked, setChecked] = useState(true);
  useEffect(() => {
    if (status === 0) {
      setChecked(true);
    } else {
      setChecked(false);
    }
  }, []);

  return (
    <div
      className="custom-switch"
      onClick={() => {
        setChecked(!checked);
      }}
    >
      <div
        className={checked === true ? "checked-status-left" : "not-active"}
        onClick={() => setChecked(true)}
      >
        {checked && leftLabel}
      </div>
      <div
        className={checked === false ? "checked-status-right" : "not-active"}
        onClick={() => setChecked(false)}
      >
        {!checked && rightLabel}
      </div>
    </div>
  );
}

export default CustomSwitch;
