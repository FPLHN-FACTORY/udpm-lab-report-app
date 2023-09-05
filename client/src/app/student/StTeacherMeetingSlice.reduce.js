import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const stMeetingSlice = createSlice({
  name: "stMeetingClass",
  initialState,
  reducers: {
    SetMeeting: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetMeeting } = stMeetingSlice.actions;

export const GetMeeting = (state) => state.stMeetingClass;

export default stMeetingSlice.reducer;
