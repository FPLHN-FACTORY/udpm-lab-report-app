import NotFound from "./labreportapp/pages/404";
import NotAuthorized from "./labreportapp/pages/403";
import AuthGuard from "./labreportapp/guard/AuthGuard";
import "./App.css";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { AppConfig } from "./AppConfig";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { Suspense } from "react";
import DashBoardAdmin from "./labreportapp/layout/admin/DashBoardAdmin";
import DashBoardTeacher from "./labreportapp/layout/teacher/DashBoardTeacher";
import DashBoardStudent from "./labreportapp/layout/student/DashBoardStudent";
import SemesterManagement from "./labreportapp/pages/admin/semester-management/SemesterManagement";
import ActivityManagement from "./labreportapp/pages/admin/activity-management/ActivityManagement";
import ClassManagement from "./labreportapp/pages/admin/class-management/ClassManagement";
import TeacherScheduleToday from "./labreportapp/pages/teacher/schedule-today/TeacherScheduleToday";
import TeacherAttendanceMeeting from "./labreportapp/pages/teacher/schedule-today/attendance-meeting/TeacherAttendanceMeeting";
import TeacherMyClass from "./labreportapp/pages/teacher/TeacherMyClass";
import TeacherAttendanceClass from "./labreportapp/pages/teacher/my-class/attendance/TeacherAttendanceClass";
import TeacherPostMyClass from "./labreportapp/pages/teacher/my-class/post/TeacherPostMyClass";
import StudentsInMyClass from "./labreportapp/pages/teacher/my-class/students/StudentsInMyClass";
import TeamsInMyClass from "./labreportapp/pages/teacher/my-class/teams/TeamsInMyClass";
import StudentSchedule from "./labreportapp/pages/student/schedule/StudentSchedule";
import MeetingInMyClass from "./labreportapp/pages/teacher/my-class/meeting/MeetingInMyClass";
import DetailMyClassTeam from "./labreportapp/pages/student/detail-my-class/team/DetailMyClassTeam";
import StudentMyClass from "./labreportapp/pages/student/my-class/StudentMyClass";
import MeetingManagment from "./labreportapp/pages/admin/detail-class/meeting-management/MeetingManagement";
import TeamInMeeting from "./labreportapp/pages/teacher/my-class/meeting/team/TeamInMeeting";
import StMeetingMyClass from "./labreportapp/pages/student/detail-my-class/meeting/StudentMeeting";
import StTeamMeeting from "./labreportapp/pages/student/detail-my-class/meeting/detail-meeting/DetailMyClassMeeting";
import AdFactoryDeploymentStatistics from "./labreportapp/pages/admin/admin-factory-deployment-statistics/AdFactoryDeploymentStatistics";
import AdminTrackActivityMetrics from "./labreportapp/pages/admin/admin-track-activity-metrics/AdminTrackActivityMetrics";
import ClassConfiguration from "./labreportapp/pages/admin/configuration/class-configuration/ClassConfiguration";
import InformationClass from "./labreportapp/pages/admin/detail-class/information-class/InformationClass";
import TeacherPointMyClass from "./labreportapp/pages/teacher/my-class/point/TeacherPointMyClass";
import StRegisterClass from "./labreportapp/pages/student/register-class/StRegisterClass";
import StAttendance from "./labreportapp/pages/student/attendance/StAttendance";
import StPoint from "./labreportapp/pages/student/point/StPoint";
import StPostDetailClass from "./labreportapp/pages/student/detail-my-class/post/StPostDetailClass";
import StAttendanceDetailClass from "./labreportapp/pages/student/detail-my-class/attendance/StAttendanceDetailClass";
import StPointDetailClass from "./labreportapp/pages/student/detail-my-class/point/StPointDetailClass";
import TeacherDashboard from "./labreportapp/pages/teacher/dashboard/TeacherDashboard";
import { useAppDispatch } from "./portalprojects/app/hook";
import notiCusTom from "./portalprojects/assets/sounds/notification.mp3";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import {
  SetCountNotifications,
  SetCurrentPage,
  SetListNotification,
  SetToTalPages,
} from "./portalprojects/app/reducer/notification/NotificationSlice.reducer";
import ProjectManagement from "./portalprojects/pages/admin/project-management/ProjectManagement";
import { DetailProjectAPI } from "./portalprojects/api/detail-project/detailProject.api";
import CategoryManagement from "./portalprojects/pages/admin/category-management/CategoryManagement";
import LabelManagement from "./portalprojects/pages/admin/label-management/LabelManagement";
import StakeholderManagement from "./portalprojects/pages/admin/stakeholder-management/StakeholderManagement";
import DetailProject from "./portalprojects/pages/common/detail-project/DetailProject";
import DetailProjectDashBoard from "./portalprojects/pages/common/detail-project-dashboard/DetailProjectDashBoard";
import DashboardGeneral from "./portalprojects/pages/common/dashboard/Dashboard";
import PeriodProject from "./portalprojects/pages/member/period-project/PeriodProject";
import Cookies from "js-cookie";
import TemplateReport from "./labreportapp/pages/admin/configuration/template-report/TemplateReport";
import AdFeedbackDetailClass from "./labreportapp/pages/admin/detail-class/feedback/AdFeedbackDetailClass";
import DetailMeetingAttendance from "./labreportapp/pages/admin/detail-class/meeting-management/detail-meeting/DetailMeetingAttendance";
import LevelManagement from "./labreportapp/pages/admin/level-management/LevelManagement";
import RoleSelection from "./labreportapp/pages/role-selection/RoleSelection";
import jwt_decode from "jwt-decode";
import HeaderTeacherComponent from "./labreportapp/component/teacher/HeaderTeacher";
import TeacherMyProject from "./labreportapp/pages/teacher/my-project/TeacherMyProject";
import StudentMyProject from "./labreportapp/pages/student/my-project/StudentMyProject";
import ProjectTypeManagement from "./labreportapp/pages/admin/genera-management/project-type-management/ProjectTypeManagement";
import RoleManagement from "./labreportapp/pages/admin/role-management/RoleManagemen";
import GroupProjectManagement from "./labreportapp/pages/admin/workshop-project/group-project-management/GroupProjectManagement";
import TeamManagement from "./labreportapp/pages/admin/workshop-member/team-management/TeamManagement";
import MemberManagement from "./labreportapp/pages/admin/workshop-member/member-management/MemberManagement";
import MeetingPeriodConfiguration from "./labreportapp/pages/admin/configuration/meeting-period-configuration/MeetingPeriodConfiguration";
import DetailGroupProject from "./labreportapp/pages/admin/workshop-project/detail-group-project/DetailGroupProject";
import RoleFactoryManagement from "./labreportapp/pages/admin/workshop-member/role-factory-management/RoleFactoryManagement";
import DetailTeamFactory from "./labreportapp/pages/admin/workshop-member/team-management/detail-team-factory/DetailTeamFactory";
import { message } from "antd";
import TeMemberFactory from "./labreportapp/pages/teacher/member-factory/TeMemberFactory";
import TeTeamFactory from "./labreportapp/pages/teacher/team-factory/TeTeamFactory";
import StTeamFactory from "./labreportapp/pages/student/team-factory/StTeamFactory";
import StMemberFactory from "./labreportapp/pages/student/member-factory/StMemberFactory";
import TeDetailTeamFactory from "./labreportapp/pages/teacher/team-factory/detail-team-factory/TeDetailTeamFactory";
import StDetailTeamFactory from "./labreportapp/pages/student/team-factory/detail-team-factory/StDetailTeamFactory";
import AdProjectStatistics from "./labreportapp/pages/admin/project-statistics/AdProjectStatistics";
import TeacherFeedBack from "./labreportapp/pages/teacher/feed-back/TeacherFeedBack";
import AdminFeedback from "./labreportapp/pages/admin/feedback/AdminFeedback";
import AdminTeacherDashboard from "./labreportapp/pages/admin/admin-teacher-dashboard/AdminTeacherDashboard";

function App() {
  const dispatch = useAppDispatch();
  const socket = new SockJS(
    "http://localhost:2509/portal-projects-websocket-endpoint"
  );
  let stompClientAll = Stomp.over(socket, {
    heartbeatIncoming: 10000,
    heartbeatOutgoing: 10000,
  });

  stompClientAll.onWebSocketClose(() => {
    message.info("Mất kết nối đến máy chủ !");
  });

  const playNotificationSound = () => {
    const audio = new Audio(notiCusTom);
    audio.play();
  };

  const previousURL = window.location.search;
  const previousURLParams = new URLSearchParams(previousURL);
  const tokenFromPreviousURL = previousURLParams.get("Token");
  if (tokenFromPreviousURL) {
    Cookies.set("token", tokenFromPreviousURL, { expires: 365 });
  }

  let tokenCookies = Cookies.get("token");
  if (tokenCookies) {
    const userCurrent = jwt_decode(tokenCookies);
    stompClientAll.connect({}, () => {
      stompClientAll.subscribe(
        "/portal-projects/create-notification/" + userCurrent.id,
        (response) => {
          message.success("Bạn có thông báo mới");
          playNotificationSound();

          DetailProjectAPI.countNotification(userCurrent.id).then(
            (response) => {
              dispatch(SetCountNotifications(response.data.data));
            }
          );

          DetailProjectAPI.fetchAllNotification(userCurrent.id, 0).then(
            (response) => {
              dispatch(SetListNotification(response.data.data.data));
              dispatch(SetCurrentPage(response.data.data.currentPage));
              dispatch(SetToTalPages(response.data.data.totalPages));
            }
          );
        }
      );
      if (userCurrent != null && userCurrent.role.includes("ADMIN")) {
        stompClientAll.subscribe(
          "/portal-projects/update-meeting",
          (response) => {
            message.success("Thông báo : " + response.body);
          }
        );
      }
    });
  }

  return (
    <div className="App scroll-smooth md:scroll-auto">
      <ToastContainer />
      <BrowserRouter basename={AppConfig.routerBase}>
        <Suspense>
          <Routes>
            <Route path="*" element={<NotFound />} />
            <Route path="/layout-guard-roles" element={<NotAuthorized />} />

            <Route path="/not-found" element={<NotFound />} />
            <Route path="/not-authorization" element={<NotAuthorized />} />

            <Route
              path="/"
              element={<Navigate replace to="/role-selection" />}
            />

            <Route
              path="/teacher"
              element={<Navigate replace to="/teacher/schedule-today" />}
            />

            <Route
              path="/student"
              element={<Navigate replace to="/student/my-class" />}
            />

            <Route
              path="/admin"
              element={<Navigate replace to="/admin/factory-deployment-statistics" />}
            />
            <Route
              path="/role-selection"
              element={
                <AuthGuard>
                  <RoleSelection />
                </AuthGuard>
              }
            />

            <Route
              path="/admin/semester-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <SemesterManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/activity-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <ActivityManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/class-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <ClassManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/class-management/meeting-management/:id"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <MeetingManagment />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/class-management/meeting-management/attendance/:id"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <DetailMeetingAttendance />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/class-management/information-class/:id"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <InformationClass />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/template-report"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <TemplateReport />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/meeting-period-configuration"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <MeetingPeriodConfiguration />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/level-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <LevelManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/class-management/feedback/:id"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <AdFeedbackDetailClass />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/factory-deployment-statistics"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <AdFactoryDeploymentStatistics />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/track-activity-metrics"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <AdminTrackActivityMetrics />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/project-statistics"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <AdProjectStatistics />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/class-configuration"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <ClassConfiguration />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/schedule-today"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeacherScheduleToday />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/dashboard"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeacherDashboard />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/feedback"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeacherFeedBack />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/schedule-today/attendance/:idMeeting"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeacherAttendanceMeeting />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/my-class"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeacherMyClass />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/member-factory"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeMemberFactory />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/member-factory/:id"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeDetailTeamFactory />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/team-factory"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeTeamFactory />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/student/team-factory"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StTeamFactory />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />
            <Route
              path="/student/member-factory/:id"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StDetailTeamFactory />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />
            <Route
              path="/student/member-factory"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StMemberFactory />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/my-class/post/:idClass"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeacherPostMyClass />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/my-class/students/:idClass"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <StudentsInMyClass />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/my-class/attendance/:idClass"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeacherAttendanceClass />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/my-class/teams/:idClass"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeamsInMyClass />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/my-class/point/:idClass"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeacherPointMyClass />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/my-class/meeting/:idClass"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <MeetingInMyClass />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/my-class/meeting/detail/:idMeeting"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeamInMeeting />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/student/register-class"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StRegisterClass />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />
            <Route
              path="/student/attendance"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StAttendance />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />
            <Route
              path="/student/point"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StPoint />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />
            <Route
              path="/student/my-class"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StudentMyClass />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />
            <Route
              path="/student/schedule"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StudentSchedule />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />
            <Route
              path="/student/my-class/team/:id"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <DetailMyClassTeam />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />
            {/* router student meeting*/}
            <Route
              path="/student/my-class/meeting/:id"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StMeetingMyClass />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />
            <Route
              path="/student/my-class/post/:id"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StPostDetailClass />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />
            <Route
              path="/student/my-class/attendance/:id"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StAttendanceDetailClass />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />
            <Route
              path="/student/my-class/point/:id"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StPointDetailClass />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />

            <Route
              path="/student/my-class/meeting/detail/:idMeeting"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StTeamMeeting />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />

            <Route
              path="/admin/project-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <ProjectManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/category-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <CategoryManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/label-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <LabelManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/stakeholder-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <StakeholderManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/type-project-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <ProjectTypeManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/role-project-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <RoleManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/role-factory-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <RoleFactoryManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/group-project-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <GroupProjectManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/group-project-management/:id"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <DetailGroupProject />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/feedback"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <AdminFeedback />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/teacher-dashboard"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <AdminTeacherDashboard />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/team-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <TeamManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/team-management/:id"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <DetailTeamFactory />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/admin/member-management"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <MemberManagement />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            <Route
              path="/detail-project/:id"
              element={
                <AuthGuard>
                  <HeaderTeacherComponent />
                  <DetailProject />
                </AuthGuard>
              }
            />
            <Route
              path="/detail-project/table/:id"
              element={
                <AuthGuard>
                  <HeaderTeacherComponent />
                  <DetailProject />
                </AuthGuard>
              }
            />
            <Route
              path="/detail-project/dashboard/:id"
              element={
                <AuthGuard>
                  <HeaderTeacherComponent />
                  <DetailProjectDashBoard />
                </AuthGuard>
              }
            />
            <Route
              path="/projects/dashboard"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <DashboardGeneral />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/teacher/my-project"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <TeacherMyProject />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            <Route
              path="/student/my-project"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StudentMyProject />
                  </DashBoardStudent>
                </AuthGuard>
              }
            />
            <Route
              path="/period-project/:id"
              element={
                <AuthGuard>
                  <HeaderTeacherComponent />
                  <PeriodProject />
                </AuthGuard>
              }
            />
          </Routes>
        </Suspense>
      </BrowserRouter>
    </div>
  );
}

export default App;
