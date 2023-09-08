import NotFound from "./pages/404";
import NotAuthorized from "./pages/403";
import AuthGuard from "./guard/AuthGuard";
import "./App.css";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { AppConfig } from "./AppConfig";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { Suspense } from "react";
import DashBoardAdmin from "./layout/admin/DashBoardAdmin";
import DashBoardTeacher from "./layout/teacher/DashBoardTeacher";
import DashBoardStudent from "./layout/student/DashBoardStudent";
import SemesterManagement from "./pages/admin/semester-management/SemesterManagement";
import ActivityManagement from "./pages/admin/activity-management/ActivityManagement";
import ClassManagement from "./pages/admin/class-management/ClassManagement";
import AdminDashboard from "./pages/admin/admin-factory-deployment-statistics/AdFactoryDeploymentStatistics";
import TeacherScheduleToday from "./pages/teacher/schedule-today/TeacherScheduleToday";
import TeacherAttendanceMeeting from "./pages/teacher/schedule-today/attendance-meeting/TeacherAttendanceMeeting";
import TeacherMyClass from "./pages/teacher/TeacherMyClass";
import TeacherAttendanceClass from "./pages/teacher/my-class/attendance/TeacherAttendanceClass";
import TeacherPostMyClass from "./pages/teacher/my-class/post/TeacherPostMyClass";
import StudentsInMyClass from "./pages/teacher/my-class/students/StudentsInMyClass";
import TeamsInMyClass from "./pages/teacher/my-class/teams/TeamsInMyClass";
import StudentSchedule from "./pages/student/schedule/StudentSchedule";
import MeetingInMyClass from "./pages/teacher/my-class/meeting/MeetingInMyClass";
import DetailMyClassTeam from "./pages/student/detail-my-class/team/DetailMyClassTeam";
import StudentMyClass from "./pages/student/my-class/StudentMyClass";
import MeetingManagment from "./pages/admin/detail-class/meeting-management/MeetingManagement";
import TeamInMeeting from "./pages/teacher/my-class/meeting/team/TeamInMeeting";
import StMeetingMyClass from "./pages/student/detail-my-class/meeting/StudentMeeting";
import StTeamMeeting from "./pages/student/detail-my-class/meeting/detail-meeting/DetailMyClassMeeting";
import AdFactoryDeploymentStatistics from "./pages/admin/admin-factory-deployment-statistics/AdFactoryDeploymentStatistics";
import AdminTrackActivityMetrics from "./pages/admin/admin-track-activity-metrics/AdminTrackActivityMetrics";
import ClassConfiguration from "./pages/admin/class-configuration/ClassConfiguration";
import InformationClass from "./pages/admin/detail-class/information-class/InformationClass";
import PointManagement from "./pages/teacher/my-class/point/PointManagement";
import StRegisterClass from "./pages/student/register-class/StRegisterClass";
import StAttendance from "./pages/student/attendance/StAttendance";
import StPoint from "./pages/student/point/StPoint";
import StPostDetailClass from "./pages/student/detail-my-class/post/StPostDetailClass";
import StAttendanceDetailClass from "./pages/student/detail-my-class/attendance/StAttendanceDetailClass";
import StPointDetailClass from "./pages/student/detail-my-class/point/StPointDetailClass";
import TeacherDashboard from "./pages/teacher/dashboard/TeacherDashboard";

function App() {
  return (
    <div className="App scroll-smooth md:scroll-auto">
      <ToastContainer />
      <BrowserRouter basename={AppConfig.routerBase}>
        <Suspense>
          <Routes>
            <Route path="*" element={<NotFound />} />
            <Route path="/layout-guard-roles" element={<NotAuthorized />} />

            <Route path="/not-found" element={<NotFound />} />
            <Route path="/not-authorization" element={<NotFound />} />

            <Route
              path="/"
              element={<Navigate replace to="/teacher/schedule-today" />}
            />

            <Route
              path="/teacher"
              element={<Navigate replace to="/teacher/my-class" />}
            />

            <Route
              path="/student"
              element={<Navigate replace to="/student/my-class" />}
            />

            <Route
              path="/admin"
              element={<Navigate replace to="/admin/class-management" />}
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
              path="/admin/class-configuration"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <ClassConfiguration />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            {/* router của Hiệu trang chính schedule lesson today*/}
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
            {/* router của Hiệu trang phụ attendance meeting today*/}
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
            {/* router của Hiệu trang chính lớp của tôi*/}
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
            {/* router của Hiệu trang nhỏ post */}
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
            {/* router của Hiệu trang nhỏ thành viên lớp */}
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
            {/* router detail  attendance của Hiệu trang nhỏ my class*/}
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
            {/* router của Hiệu trang nhỏ nhóm*/}
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
                    <PointManagement />
                  </DashBoardTeacher>
                </AuthGuard>
              }
            />
            {/* router của Hiệu trang nhỏ meeting*/}
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
            {/* router detail  meeting của Hiệu trang nhỏ detail meeting*/}
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
            {/* router detail  meeting task student*/}
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
          </Routes>
        </Suspense>
      </BrowserRouter>
    </div>
  );
}

export default App;
