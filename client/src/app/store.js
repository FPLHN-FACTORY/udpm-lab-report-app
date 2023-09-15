import { configureStore } from "@reduxjs/toolkit";
import adSemesterSliceReducer from "./admin/AdSemester.reducer";
import activityManagementSliceReducer from "./admin/activity-management/activityManagementSlice.reducer";
import tePostSliceReduce from "./teacher/post/tePostSlice.reduce";
import teMyClassSliceReduce from "./teacher/my-class/teacherMyClassSlice.reduce";
import teSemesterSliceReduce from "./teacher/semester/teacherSemesterSlice.reduce";
import teTeamsSliceReduce from "./teacher/teams/teamsSlice.reduce";
import teMeetingSliceReduce from "./teacher/meeting/teacherMeetingSlice.reduce";
import teacherSemesterSliceReduce from "./admin/ClassManager.reducer";
import teStudentClassesSliceReduce from "./teacher/student-class/studentClassesSlice.reduce";
import tePointSliceReduce from "./teacher/point/tePointSlice.reduce";
import AdMeetingManagementReducer from "./admin/AdMeetingManagement.reducer";
import teacherAttendanceMeetingSliceReduce from "./teacher/attendance-meeting-today/teacherAttendanceMeetingSlice.reduce";
import stScheduleSliceReducer from "./student/StSchedule.reduce";
import stMeetingSliceReduce from "./student/StTeacherMeetingSlice.reduce";
import AdTeacherSliceReducer from "./admin/AdTeacherSlice.reducer";
import StPostSliceReducer from "./student/StPost.reduce";
import AdCollapsedSliceReducer from "./admin/AdCollapsedSlice.reducer";
import StCollapsedSliceReducer from "./student/StCollapsedSlice.reducer";
import TeCollapsedSliceReducer from "./teacher/TeCollapsedSlice.reducer";
import teClassSliceReduce from "./teacher/my-class/teClassSlice.reduce";
export const store = configureStore({
  reducer: {
    teClass: teClassSliceReduce,
    tePoint: tePointSliceReduce,
    tePost: tePostSliceReduce,
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
    adTeacher: AdTeacherSliceReducer,
    stPost: StPostSliceReducer,
    adCollapsed: AdCollapsedSliceReducer,
    stCollapsed: StCollapsedSliceReducer,
    teCollapsed: TeCollapsedSliceReducer,
  },
});

export const dispatch = store.dispatch;
export const getState = store.getState;
