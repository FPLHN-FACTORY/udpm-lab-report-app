import { configureStore } from "@reduxjs/toolkit";
import teacherSemesterSliceReduce from "./admin/ClassManager.reducer";

export const store = configureStore({
  reducer: {
    teacherSemester: teacherSemesterSliceReduce,

  },
});

export const dispatch = store.dispatch;
export const getState = store.getState;
