import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const DPRoleProjectSlice = createSlice({
  name: "meRoleProject",
  initialState,
  reducers: {
    SetMeRoleProject: (state, action) => {
      state = action.payload;
      return state;
    },
    AddMeRoleProject: (state, action) => {
      const data = action.payload;
      let newRoleProject = {
        id: data.id,
        name: data.name,
        description: data.description,
        roleDefault: data.roleDefault === "DEFAULT" ? 0 : 1,
        projectId: data.projectId,
      };
      state.unshift(newRoleProject);
      return state;
    },
    UpdateMeRoleProject: (state, action) => {
      const updateRoleProject = action.payload;
      const index = state.findIndex(
        (roleProject) => roleProject.id === updateRoleProject.id
      );
      if (index !== -1) {
        state[index].name = updateRoleProject.name;
        state[index].description = updateRoleProject.description;
        state[index].roleDefault =
          updateRoleProject.roleDefault === "DEFAULT" ? 0 : 1;
      }
    },
    DeleteMeRoleProject: (state, action) => {
      const idRoleProject = action.payload;
      const index = state.findIndex(
        (roleProject) => roleProject.id === idRoleProject
      );
      state.splice(index, 1);
    },
  },
});

export const {
  SetMeRoleProject,
  AddMeRoleProject,
  UpdateMeRoleProject,
  DeleteMeRoleProject,
} = DPRoleProjectSlice.actions;

export const GetMeRoleProject = (state) => state.meRoleProject;

export default DPRoleProjectSlice.reducer;
