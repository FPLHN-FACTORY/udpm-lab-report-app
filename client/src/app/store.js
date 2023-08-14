import { configureStore } from "@reduxjs/toolkit";
import activityManagementSliceReducer from "./admin/activity-management/activityManagementSlice.reducer";

export const store = configureStore({
  reducer: {
    activityManagement: activityManagementSliceReducer,
  },
});

export const dispatch = store.dispatch;
export const getState = store.getState;
