import NotFound from "./pages/404";
import NotAuthorized from "./pages/403";
import AuthGuard from "./guard/AuthGuard";
import "./App.css";
import { ToastContainer } from "react-toastify";
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
import StudentMyClass from "./pages/student/StudentMyClass";

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
              element={<Navigate replace to="/admin/semester-management" />}
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
              path="/admin/dashboard"
              element={
                <AuthGuard>
                  <DashBoardAdmin>
                    <AdminDashboard />
                  </DashBoardAdmin>
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
              path="/student/my-class"
              element={
                <AuthGuard>
                  <DashBoardStudent>
                    <StudentMyClass />
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
