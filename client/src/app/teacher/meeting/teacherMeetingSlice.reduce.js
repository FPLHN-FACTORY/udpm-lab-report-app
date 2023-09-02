import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teMeetingSlice = createSlice({
  name: "teMeetingClass",
  initialState,
  reducers: {
    SetMeeting: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetMeeting } = teMeetingSlice.actions;

export const GetMeeting = (state) => state.teMeetingClass;

export default teMeetingSlice.reducer;
