import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const AdMeetingRequestSlice = createSlice({
  name: "adMeetingRequest",
  initialState,
  reducers: {
    SetAdMeetingRequest: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetAdMeetingRequest } = AdMeetingRequestSlice.actions;

export const GetAdMeetingRequest = (state) => state.adMeetingRequest;

export default AdMeetingRequestSlice.reducer;
