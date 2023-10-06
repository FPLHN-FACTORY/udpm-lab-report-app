import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const StStudentClassesSlice = createSlice({
  name: "StStudentClasses",
  initialState,
  reducers: {
    SetStStudentClasses: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetStStudentClasses } = StStudentClassesSlice.actions;

export const GetStStudentClasses = (state) => state.StStudentClasses;

export default StStudentClassesSlice.reducer;
