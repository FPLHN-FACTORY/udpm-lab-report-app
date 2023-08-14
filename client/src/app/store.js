import { configureStore } from "@reduxjs/toolkit";
import adSemesterSliceReducer from "./admin/AdSemester.reducer";
import activityManagementSliceReducer from "./admin/activity-management/activityManagementSlice.reducer";
import teMyClassSliceReduce from "./teacher/my-class/teacherMyClassSlice.reduce";
import teSemesterSliceReduce from "./teacher/semester/teacherSemesterSlice.reduce";
import teTeamsSliceReduce from "./teacher/teams/teamsSlice.reduce";
import teacherSemesterSliceReduce from "./admin/ClassManager.reducer";

export const store = configureStore({
  reducer: {
    teMyClass: teMyClassSliceReduce,
    teSemester: teSemesterSliceReduce,
    teTeams: teTeamsSliceReduce,
    teacherSemester: teacherSemesterSliceReduce,
    activityManagement: activityManagementSliceReducer,
    adSemester: adSemesterSliceReducer,
  },
});

export const dispatch = store.dispatch;
export const getState = store.getState;
