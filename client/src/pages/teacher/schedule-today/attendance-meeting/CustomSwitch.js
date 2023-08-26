import React, { useEffect, useState } from "react";
import "./CustomSwitch.css";
import { useAppDispatch, useAppSelector } from "../../../../app/hook";
import {
  SetAttendanceMeeting,
  GetAttendanceMeeting,
} from "../../../../app/teacher/attendance-meeting-today/teacherAttendanceMeetingSlice.reduce";

function CustomSwitch({ leftLabel, rightLabel, status, items }) {
  const dispatch = useAppDispatch();
  const data = useAppSelector(GetAttendanceMeeting);
  const [checked, setChecked] = useState(true);
  useEffect(() => {
    status === "YES" ? setChecked(true) : setChecked(false);
  }, []);

  useEffect(() => {
    const dataNew = data.map((item1) => {
      if (
        item1.idMeeting === items.idMeeting &&
        item1.idStudent === items.idStudent
      ) {
        return {
          idMeeting: item1.idMeeting,
          nameMeeting: item1.nameMeeting,
          idStudent: item1.idStudent,
          ...item1,
          statusAttendance: checked === true ? "YES" : "NO",
        };
      }
      return item1;
    });
    dispatch(SetAttendanceMeeting(dataNew));
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
}

export default CustomSwitch;
