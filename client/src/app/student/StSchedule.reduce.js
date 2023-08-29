import { createSlice } from "@reduxjs/toolkit";

const initialState = [];
const stScheduleSlice = createSlice({
  name: "stSchedule",
  initialState,
  reducers: {
    SetSchedule: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetSchedule } = stScheduleSlice.actions;

export const GetSchedule = (state) => state.stSchedule;

export default stScheduleSlice.reducer;

