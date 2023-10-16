import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const adTypeProjectSlice = createSlice({
  name: "adTypeProject",
  initialState,
  reducers: {
    SetTypeProject: (state, action) => {
      state = action.payload;
      return state;
    },
    AddTypeProject: (state, action) => {
      const data = action.payload;
      let newTypeProject = {
        stt: state.length + 1,
        id: data.id,
        name: data.name,
        description: data.description,

      };
      state.unshift(newTypeProject);
      return state;
    },
    UpdateTypeProject: (state, action) => {
      const updateTypeProject = action.payload;
      const index = state.findIndex(
        (typeProject) => typeProject.id === updateTypeProject.id
      );
      if (index !== -1) {
        state[index].name = updateTypeProject.name;
        state[index].description = updateTypeProject.description;

      }
    },
    DeleteTypeProject: (state, action) => {
      const idTypeProject = action.payload;
      const index = state.findIndex((typeProject) => typeProject.id === idTypeProject);
      state.splice(index, 1);
    },
  },
});

export const { SetTypeProject,
               AddTypeProject,
               UpdateTypeProject,
               DeleteTypeProject,
             } = adTypeProjectSlice.actions;

export const GetTypeProject = (state) => state.adTypeProject;

export default adTypeProjectSlice.reducer;
