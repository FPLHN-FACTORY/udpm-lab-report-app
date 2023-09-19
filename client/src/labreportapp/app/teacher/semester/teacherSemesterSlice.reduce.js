import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teSemesterSlice = createSlice({
  name: "teSemester",
  initialState,
  reducers: {
    SetTeacherSemester: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetTeacherSemester } = teSemesterSlice.actions;

export const GetTeacherSemester = (state) => state.teSemester;

export default teSemesterSlice.reducer;
