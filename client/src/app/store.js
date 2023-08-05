import { configureStore } from "@reduxjs/toolkit";
import teacherMyClassSliceReduce from "./teacher/my-class/teacherMyClassSlice.reduce";
import teacherSemesterSliceReduce from "./teacher/semester/teacherSemesterSlice.reduce";

export const store = configureStore({
  reducer: {
    teacherMyClass: teacherMyClassSliceReduce,
    teacherSemester: teacherSemesterSliceReduce,
  },
});

export const dispatch = store.dispatch;
export const getState = store.getState;
