import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const projectManagementSlide = createSlice({
  name: "projectManagement",
  initialState,
  reducers: {
    SetProjectManagement: (state, action) => {
      state.length = 0;
      state.push(...action.payload);
      return state;
    },
    CreateProject: (state, action) => {
      state.unshift(action.payload);
      state.forEach((item, index) => {
        item.stt = index + 1;
      });
      return state;
    },
    UpdateProject: (state, action) => {
      const project = action.payload;
      const index = state.findIndex((item) => item.id === project.id);
      if (index !== -1) {
        let projectUpdate = state[index];
        projectUpdate.code = project.code;
        projectUpdate.name = project.name;
        projectUpdate.progress = project.progress;
        projectUpdate.statusProject = project.statusProject;
      }
    },
  },
});

export const { SetProjectManagement, CreateProject, UpdateProject } =
  projectManagementSlide.actions;

export const GetProjectManagement = (state) => state.projectManagement;

export default projectManagementSlide.reducer;
