import { configureStore } from "@reduxjs/toolkit";
import adSemesterSliceReducer from "./admin/AdSemester.reducer";
export const store = configureStore({
  reducer: {
    adSemester: adSemesterSliceReducer,
  },
});

export const dispatch = store.dispatch;
export const getState = store.getState;
