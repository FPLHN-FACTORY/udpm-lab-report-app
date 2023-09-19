import { configureStore } from "@reduxjs/toolkit";
import adSemesterSliceReducer from "./admin/AdSemester.reducer";
import activityManagementSliceReducer from "./admin/activity-management/activityManagementSlice.reducer";
import tePostSliceReduce from "./teacher/post/tePostSlice.reduce";
import teMyClassSliceReduce from "./teacher/my-class/teacherMyClassSlice.reduce";
import teSemesterSliceReduce from "./teacher/semester/teacherSemesterSlice.reduce";
import teTeamsSliceReduce from "./teacher/teams/teamsSlice.reduce";
import teMeetingSliceReduce from "./teacher/meeting/teacherMeetingSlice.reduce";
import adClassManagement from "./admin/ClassManager.reducer";
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
//
import boardSliceReducer from "../../portalprojects/app/reducer/detail-project/DPBoardSlice.reducer";
import myProjectSliceReducer from "../../portalprojects/app/reducer/my-project/myProjectSlice.reducer";
import memberProjectSliceReducer from "../../portalprojects/app/reducer/detail-project/DPMemberProject.reducer";
import projectSliceReducer from "../../portalprojects/app/reducer/detail-project/DPProjectSlice.reducer";
import PeriodSliceReducer from "../../portalprojects/app/reducer/detail-project/DPPeriodSlice.reducer";
import periodProjectSliceReducer from "../../portalprojects/app/reducer/member/period-project/periodProjectSlice.reducer";
import StompClientReducer from "../../portalprojects/app/reducer/detail-project/StompClient.reducer";
import DPDetailTodoSliceReducer from "../../portalprojects/app/reducer/detail-project/DPDetailTodoSlice.reducer";
import DPLabelProjectReducer from "../../portalprojects/app/reducer/detail-project/DPLabelProject.reducer";
import adCategorySliceReducer from "../../portalprojects/app/reducer/admin/category-management/adCategorySlice.reducer";
import labelManagementSliceReducer from "../../portalprojects/app/reducer/admin/label-management/labelManagementSlice.reducer";
import projectManagementSlideReducer from "../../portalprojects/app/reducer/admin/project-management/projectManagementSlide.reducer";
import memberProjectManagementReducer from "../../portalprojects/app/reducer/admin/member-project-management/memberProjectManagement.reduce";
import categoryProjectManagementReducer from "../../portalprojects/app/reducer/admin/category-project-management/categoryProjectManagement.reduce";
import StakeholderReducer from "../../portalprojects/app/reducer/admin/stakeholder-management/Stakeholder.reducer";
import NotificationSliceReducer from "../../portalprojects/app/reducer/notification/NotificationSlice.reducer";

export const store = configureStore({
  reducer: {
    teClass: teClassSliceReduce,
    tePoint: tePointSliceReduce,
    tePost: tePostSliceReduce,
    teMyClass: teMyClassSliceReduce,
    teSemester: teSemesterSliceReduce,
    teTeams: teTeamsSliceReduce,
    adClassManagement: adClassManagement,
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
    ////
    board: boardSliceReducer,
    myProject: myProjectSliceReducer,
    memberProject: memberProjectSliceReducer,
    project: projectSliceReducer,
    memberPeriod: PeriodSliceReducer,
    periodProject: periodProjectSliceReducer,
    stompClient: StompClientReducer,
    detailTodo: DPDetailTodoSliceReducer,
    labelProject: DPLabelProjectReducer,
    adStakeholderManagement: StakeholderReducer,
    adCategory: adCategorySliceReducer,
    labelManagement: labelManagementSliceReducer,
    projectManagement: projectManagementSlideReducer,
    memberProjectManagement: memberProjectManagementReducer,
    categoryProjectManagement: categoryProjectManagementReducer,
    notification: NotificationSliceReducer,
  },
});

export const dispatch = store.dispatch;
export const getState = store.getState;