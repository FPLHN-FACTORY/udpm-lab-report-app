import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const AdGroupProjectSlice = createSlice({
  name: "adGroupProject",
  initialState,
  reducers: {
    SetAdGroupProject: (state, action) => {
      state = action.payload;
      return state;
    },
    CreateAdGroupProject: (state, action) => {
      let data = action.payload;
      state.unshift(data);
      return state;
    },
    UpdateAdGroupProject: (state, action) => {
      let data = action.payload;
      state.forEach((item) => {
        if (item.id === data.id) {
          item.name = data.name;
          item.descriptions = data.descriptions;
          item.backgroundImage = data.backgroundImage;
        }
      });
      return state;
    },
  },
});

export const { SetAdGroupProject, UpdateAdGroupProject, CreateAdGroupProject } =
  AdGroupProjectSlice.actions;

export const GetAdGroupProject = (state) => state.adGroupProject;

export default AdGroupProjectSlice.reducer;
