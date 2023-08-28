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
import AdminDashboard from "./pages/admin/admin-dashboard/AdminDashboard";
import TeacherMyClass from "./pages/teacher/my-class/TeacherMyClass";
import StudentsInMyClass from "./pages/teacher/my-class/students/StudentsInMyClass";
import TeamsInMyClass from "./pages/teacher/my-class/teams/TeamsInMyClass";
import StudentSchedule from "./pages/student/schedule/StudentSchedule";
import MeetingInMyClass from "./pages/teacher/my-class/meeting/MeetingInMyClass";
import DetailMeeting from "./pages/teacher/my-class/meeting/detail/DetailMeeting";
import DetailMyClassTeam from "./pages/student/detail-my-class/team/DetailMyClassTeam";
import StudentMyClass from "./pages/student/my-class/StudentMyClass";
import MeetingManagment from "./pages/admin/detail-class/meeting-management/MeetingManagement";

function App() {
  return (
    <div className="App scroll-smooth md:scroll-auto">
      <ToastContainer />
      <BrowserRouter basename={AppConfig.routerBase}>
        <Suspense>
          <Routes>
            <Route path="*" element={<NotFound />} />
            <Route path="/layout-guard-roles" element={<NotAuthorized />} />

            <Route
              path="/"
              element={<Navigate replace to="/admin/class-management" />}
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
              path="/admin/dashboard"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <AdminDashboard />
                  </DashBoardAdmin>
                </AuthGuard>
              }
            />
            {/* router của Hiệu */}
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
            {/* router của Hiệu */}
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
            {/* router của Hiệu */}
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
            {/* router của Hiệu */}
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
            {/* router detail  meeting của Hiệu */}
            <Route
              path="/teacher/my-class/meeting/detail/:idClass/:idMeeting"
              element={
                <AuthGuard>
                  <DashBoardTeacher>
                    <DetailMeeting />
                  </DashBoardTeacher>
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
          </Routes>
        </Suspense>
      </BrowserRouter>
    </div>
  );
}

export default App;
