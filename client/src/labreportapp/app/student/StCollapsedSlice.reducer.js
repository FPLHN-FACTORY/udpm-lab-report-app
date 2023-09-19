import { createSlice } from "@reduxjs/toolkit";

const initialState = false;

const StCollapsedSlice = createSlice({
  name: "stCollapsed",
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
  StCollapsedSlice.actions;

export const GetStCollapsed = (state) => state.stCollapsed;

export default StCollapsedSlice.reducer;




