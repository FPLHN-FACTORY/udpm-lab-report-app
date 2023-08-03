import { configureStore } from "@reduxjs/toolkit";

export const store = configureStore({
  reducer: {

  },
});

export const dispatch = store.dispatch;
export const getState = store.getState;
