import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const AdMeetingPeriodSlice = createSlice({
  name: "adMeetingPeriodSlice",
  initialState,
  reducers: {
    SetAdMeetingPeriod: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetAdMeetingPeriod } = AdMeetingPeriodSlice.actions;

export const GetAdMeetingPeriod = (state) => state.adMeetingPeriodSlice;

export default AdMeetingPeriodSlice.reducer;
