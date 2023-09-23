import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teTeamsSlice = createSlice({
  name: "teTeams",
  initialState,
  reducers: {
    SetTeams: (state, action) => {
      state = action.payload;
      return state;
    },
    CreateTeam: (state, action) => {
      const data = action.payload;
      let newTeam = {
        id: data.id,
        code: data.code,
        name: data.name,
        subjectName: data.subjectName,
        classId: data.classId,
        createdDate: data.createdDate,
        idProject: data.projectId,
      };
      state.unshift(newTeam);
      return state;
    },
    DeleteTeam: (state, action) => {
      const team = action.payload;
      const newData = state.filter((item) => item.id !== team.id);
      state = newData;
      return state;
    },
    UpdateTeam: (state, action) => {
      const team = action.payload;
      const index = state.findIndex((item) => item.id === team.id);
      if (index !== -1) {
        let teamUpdate = state[index];
        teamUpdate.id = team.id;
        teamUpdate.code = team.code;
        teamUpdate.name = team.name;
        teamUpdate.subjectName = team.subjectName;
        teamUpdate.classId = team.classId;
        teamUpdate.createdDate = team.createdDate;
        teamUpdate.idProject = team.projectId;
      }
      return state;
    },
  },
});
export const { SetTeams, CreateTeam, DeleteTeam, UpdateTeam } =
  teTeamsSlice.actions;

export const GetTeams = (state) => state.teTeams;

export default teTeamsSlice.reducer;
