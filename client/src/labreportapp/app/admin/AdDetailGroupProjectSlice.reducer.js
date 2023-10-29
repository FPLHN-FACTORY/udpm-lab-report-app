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
    CreateAddProjectToAdDetailGroupProject: (state, action) => {
      let data = action.payload;
      let project = {
        id: data.id,
        name: data.name,
        backGroundColor: data.backGroundColor,
        backGroundImage: data.backGroundImage,
        descriptions: data.descriptions,
      };
      state.unshift(project);
      return state;
    },
  },
});

export const {
  SetAdDetailGroupProject,
  CreateAdDetailGroupProject,
  CreateAddProjectToAdDetailGroupProject,
} = AdDetailGroupProjectSlice.actions;

export const GetAdDetailGroupProject = (state) => state.adDetailGroupProject;

export default AdDetailGroupProjectSlice.reducer;
