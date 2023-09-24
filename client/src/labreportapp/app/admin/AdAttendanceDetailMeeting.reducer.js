import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const AdAttendanceDetailMeetingSlice = createSlice({
  name: "adAttendanceDetailMeeting",
  initialState,
  reducers: {
    SetAdAttendance: (state, action) => {
      state = action.payload;
      return state;
    },
    UpdateAdAttendance: (state, action) => {
      let data = action.payload;
      state.forEach((item) => {
        if (item.id === data.id) {
          item.status = data.status;
        }
      });
      return state;
    },
  },
});

export const { SetAdAttendance, UpdateAdAttendance } =
  AdAttendanceDetailMeetingSlice.actions;
export const GetAdAttendanceDetailMeeting = (state) =>
  state.adAttendanceDetailMeeting;

export default AdAttendanceDetailMeetingSlice.reducer;
