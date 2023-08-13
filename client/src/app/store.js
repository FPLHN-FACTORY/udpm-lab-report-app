import { configureStore } from "@reduxjs/toolkit";
import teacherMyClassSliceReduce from "./teacher/my-class/teacherMyClassSlice.reduce";
import teacherSemesterSliceReduce from "./teacher/semester/teacherSemesterSlice.reduce";
import studentClassesSliceReduce from "./teacher/student-class/studentClassesSlice.reduce";
import teamsSliceReduce from "./teacher/teams/teamsSlice.reduce";
export const store = configureStore({
  reducer: {
    teacherMyClass: teacherMyClassSliceReduce,
    teacherSemester: teacherSemesterSliceReduce,
    studentClasses: studentClassesSliceReduce,
    teams: teamsSliceReduce,
  },
});

export const dispatch = store.dispatch;
export const getState = store.getState;
