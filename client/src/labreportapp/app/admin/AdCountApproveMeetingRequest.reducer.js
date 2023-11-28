import { createSlice } from "@reduxjs/toolkit";

const initialState = 0;

const AdCountApproveMeetingRequest = createSlice({
  name: "adCountApproveMeetingRequest",
  initialState,
  reducers: {
    SetAdCountApproveMeetingRequest: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetAdCountApproveMeetingRequest } =
  AdCountApproveMeetingRequest.actions;

export const GetAdCountApproveMeetingRequest = (state) => state.adCountApproveMeetingRequest;

export default AdCountApproveMeetingRequest.reducer;
