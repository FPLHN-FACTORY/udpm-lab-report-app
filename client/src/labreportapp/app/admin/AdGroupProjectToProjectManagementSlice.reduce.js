import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const AdGroupProjectToProjectManagementSlice = createSlice({
  name: "adGroupProjectToProjectManagement",
  initialState,
  reducers: {
    SetAdGroupProjecytAll: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetAdGroupProjecytAll } =
  AdGroupProjectToProjectManagementSlice.actions;

export const GetAdGroupProjecytAll = (state) => state.adGroupProject;

export default AdGroupProjectToProjectManagementSlice.reducer;
