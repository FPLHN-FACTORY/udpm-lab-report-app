import { configureStore } from "@reduxjs/toolkit";
import adSemesterSliceReducer from "./admin/AdSemester.reducer";
import activityManagementSliceReducer from "./admin/activity-management/activityManagementSlice.reducer";
import teMyClassSliceReduce from "./teacher/my-class/teacherMyClassSlice.reduce";
import teSemesterSliceReduce from "./teacher/semester/teacherSemesterSlice.reduce";
import teTeamsSliceReduce from "./teacher/teams/teamsSlice.reduce";
import teMeetingSliceReduce from "./teacher/meeting/teacherMeetingSlice.reduce";
import teacherSemesterSliceReduce from "./admin/ClassManager.reducer";
import teStudentClassesSliceReduce from "./teacher/student-class/studentClassesSlice.reduce";
import AdMeetingManagementReducer from "./admin/AdMeetingManagement.reducer";
import teacherAttendanceMeetingSliceReduce from "./teacher/attendance-meeting-today/teacherAttendanceMeetingSlice.reduce";
import stScheduleSliceReducer from "./student/StSchedule.reduce";
import stMeetingSliceReduce from "./student/StTeacherMeetingSlice.reduce";


export const store = configureStore({
  reducer: {
    teMyClass: teMyClassSliceReduce,
    teSemester: teSemesterSliceReduce,
    teTeams: teTeamsSliceReduce,
    teacherSemester: teacherSemesterSliceReduce,
    teStudentClasses: teStudentClassesSliceReduce,
    teMeetingClass: teMeetingSliceReduce,
    teAttendanceMeeting: teacherAttendanceMeetingSliceReduce,
    activityManagement: activityManagementSliceReducer,
    adSemester: adSemesterSliceReducer,
    adMeetingManagement: AdMeetingManagementReducer,
    stSchedule: stScheduleSliceReducer,
    stMeetingClass: stMeetingSliceReduce,

  },
});

export const dispatch = store.dispatch;
export const getState = store.getState;
