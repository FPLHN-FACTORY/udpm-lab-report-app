import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const AdDetailGroupProjectSlice = createSlice({
  name: "adDetailGroupProject",
  initialState,
  reducers: {
    SetAdDetailGroupProject: (state, action) => {
      state = action.payload;
      return state;
    },
    CreateAdDetailGroupProject: (state, action) => {
      let data = action.payload;
      state.unshift(data);
      return state;
    },
  },
});

export const { SetAdDetailGroupProject, CreateAdDetailGroupProject } =
  AdDetailGroupProjectSlice.actions;

export const GetAdDetailGroupProject = (state) => state.adDetailGroupProject;

export default AdDetailGroupProjectSlice.reducer;
