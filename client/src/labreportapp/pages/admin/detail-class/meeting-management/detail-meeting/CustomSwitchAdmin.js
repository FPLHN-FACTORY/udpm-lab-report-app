import React, { useEffect, useState } from "react";
import { useAppDispatch } from "../../../../../app/hook";
import { UpdateAdAttendance } from "../../../../../app/admin/AdAttendanceDetailMeeting.reducer";
const CustomSwitchAdmin = ({ leftLabel, rightLabel, status, items }) => {
  const dispatch = useAppDispatch();
  const [checked, setChecked] = useState(true);

  useEffect(() => {
    status === 0 ? setChecked(true) : setChecked(false);
  }, []);

  useEffect(() => {
    const updatedItems = {
      ...items,
      status: checked ? 0 : 1,
    };
    dispatch(UpdateAdAttendance(updatedItems));
  }, [checked]);

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
};

export default CustomSwitchAdmin;
