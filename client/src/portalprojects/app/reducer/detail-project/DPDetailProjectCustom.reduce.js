import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const detailProjectCustomSlide = createSlice({
  name: "detailProjectCustom",
  initialState,
  reducers: {
    SetProjectCustom: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetProjectCustom } = detailProjectCustomSlide.actions;

export const GetProjectCustom = (state) => state.detailProjectCustom;

export default detailProjectCustomSlide.reducer;
