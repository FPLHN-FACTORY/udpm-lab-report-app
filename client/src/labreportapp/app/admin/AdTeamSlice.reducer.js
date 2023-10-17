import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const adTeamSlice = createSlice({
  name: "adTeam",
  initialState,
  reducers: {
    SetTeam: (state, action) => {
      state = action.payload;
      return state;
    },
    AddTeam: (state, action) => {
      const data = action.payload;
      let newTeam = {
        stt: state.length + 1,
        id: data.id,
        name: data.name,
        subjectName: data.subjectName,

      };
      state.unshift(newTeam);
      return state;
    },
    UpdateTeam: (state, action) => {
      const updateTeam = action.payload;
      const index = state.findIndex(
        (team) => team.id === updateTeam.id
      );
      if (index !== -1) {
        state[index].name = updateTeam.name;
        state[index].subjectName = updateTeam.subjectName;


      }
    },
    DeleteTeam: (state, action) => {
      const idTeam = action.payload;
      const index = state.findIndex((team) => team.id === idTeam);
      state.splice(index, 1);
    },
  },
});

export const { SetTeam,
               AddTeam,
               UpdateTeam,
               DeleteTeam,
             } = adTeamSlice.actions;

export const GetTeam = (state) => state.adTeam;

export default adTeamSlice.reducer;
