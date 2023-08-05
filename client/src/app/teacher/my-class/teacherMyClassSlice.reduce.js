import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teacherMyClassSlice = createSlice({
  name: "teacherMyClass",
  initialState,
  reducers: {
    SetTeacherMyClass: (state, action) => {
      state = action.payload.data;
      return state;
    },
  },
});

export const { SetTeacherMyClass } = teacherMyClassSlice.actions;

export const GetTeacherMyClass = (state) => state.teacherMyClass;

export default teacherMyClassSlice.reducer;
