import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const adRoleProjectSlice = createSlice({
  name: "adRoleProject",
  initialState,
  reducers: {
    SetRoleProject: (state, action) => {
      state = action.payload;
      return state;
    },
    AddRoleProject: (state, action) => {
      const data = action.payload;
      let newRoleProject = {
        stt: state.length + 1,
        id: data.id,
        name: data.name,
        description: data.description,
        roleDefault: data.roleDefault === "DEFAULT" ? 0 : 1,
      };
      state.unshift(newRoleProject);
      return state;
    },
    UpdateRoleProject: (state, action) => {
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
    DeleteRoleProject: (state, action) => {
      const idRoleProject = action.payload;
      const index = state.findIndex(
        (roleProject) => roleProject.id === idRoleProject
      );
      state.splice(index, 1);
    },
  },
});

export const {
  SetRoleProject,
  AddRoleProject,
  UpdateRoleProject,
  DeleteRoleProject,
} = adRoleProjectSlice.actions;

export const GetRoleProject = (state) => state.adRoleProject;

export default adRoleProjectSlice.reducer;
