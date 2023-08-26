import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teacherAttendanceMeetingSlice = createSlice({
  name: "teAttendanceMeeting",
  initialState,
  reducers: {
    SetAttendanceMeeting: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetAttendanceMeeting } = teacherAttendanceMeetingSlice.actions;

export const GetAttendanceMeeting = (state) => state.teAttendanceMeeting;

export default teacherAttendanceMeetingSlice.reducer;
