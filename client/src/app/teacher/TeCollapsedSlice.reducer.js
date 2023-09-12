import { createSlice } from "@reduxjs/toolkit";

const initialState = false;

const TeCollapsedSlice = createSlice({
  name: "teCollapsed",
  initialState,
  reducers: {
    SetFalseToggle: (state, action) => {
      state = false;
      return state;
    },
    SetTTrueToggle: (state, action) => {
      state = true;
      return state;
    },
    Toggle: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetFalseToggle, SetTTrueToggle, Toggle } =
  TeCollapsedSlice.actions;

export const GetTeCollapsed = (state) => state.teCollapsed;

export default TeCollapsedSlice.reducer;
