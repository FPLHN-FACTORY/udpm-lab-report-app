import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const stTeamFactorySlice = createSlice({
  name: "stTeamFactory",
  initialState,
  reducers: {
    SetTeam: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetTeam} = stTeamFactorySlice.actions;

export const GetTeam = (state) => state.stTeamFactory;

export default stTeamFactorySlice.reducer;
