import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teacherSemesterSlice = createSlice({
  name: "teacherSemester",
  initialState,
  reducers: {
    SetTeacherSemester: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetTeacherSemester } = teacherSemesterSlice.actions;

export const GetTeacherSemester = (state) => state.teacherSemester;

export default teacherSemesterSlice.reducer;