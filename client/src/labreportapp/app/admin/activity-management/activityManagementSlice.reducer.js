import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const ActivityManagementSlice = createSlice({
  name: "activityManagement",
  initialState,
  reducers: {
    SetActivityManagement: (state, action) => {
      state = action.payload;
      return state;
    },
    CreateActivityManagement: (state, action) => {
      const data = action.payload;
      let newActivity = {
        stt: state.length + 1,
        id: data.id,
        code: data.code,
        name: data.name,
        startTime: data.startTime,
        endTime: data.endTime,
        level: data.levelNameItem,
        semesterId: data.semesterId,
        nameSemester: data.nameSemester,
      };
      state.unshift(newActivity);
      return state;
    },
    UpdateActivityManagement: (state, action) => {
      const updateActivity = action.payload;
      const index = state.findIndex(
        (activity) => activity.id === updateActivity.id
      );
      if (index !== -1) {
        state[index].code = updateActivity.code;
        state[index].name = updateActivity.name;
        state[index].startTime = updateActivity.startTime;
        state[index].endTime = updateActivity.endTime;
        state[index].level = updateActivity.levelText;
        state[index].nameSemester = updateActivity.nameSemesterText;
      }
    },
    DeleteActivityManagement: (state, action) => {
      const deletedId = action.payload;
      return state.filter((activity) => activity.id !== deletedId);
    },
  },
});

export const {
  SetActivityManagement,
  CreateActivityManagement,
  UpdateActivityManagement,
  DeleteActivityManagement,
} = ActivityManagementSlice.actions;
export const GetActivityManagement = (state) => state.activityManagement;
export default ActivityManagementSlice.reducer;
