import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teTeamFactorySlice = createSlice({
  name: "teTeamFactory",
  initialState,
  reducers: {
    SetTeam: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetTeam} = teTeamFactorySlice.actions;

export const GetTeam = (state) => state.teTeamFactory;

export default teTeamFactorySlice.reducer;
