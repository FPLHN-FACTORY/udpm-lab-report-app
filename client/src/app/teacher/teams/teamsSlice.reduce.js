import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teamsSlice = createSlice({
  name: "teams",
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
      };
      state.unshift(newTeam);
      return state;
    },
    // AddStudentClassJoin: (state, action) => {
    //   const studentClass = action.payload;
    //   const index = state.findIndex((item) => item.id === studentClass.id);
    //   console.log("reduceeeee");
    //   console.log(state[index]);
    //   const newData = state.filter((item) => item.id !== studentClass.id);
    //   state = newData;
    // },
  },
});

export const { SetTeams, CreateTeam } = teamsSlice.actions;

export const GetTeams = (state) => state.teams;

export default teamsSlice.reducer;
