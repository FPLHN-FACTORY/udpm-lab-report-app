import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const TeDetailTeamSlice = createSlice({
  name: "teDetailTeamFactory",
  initialState,
  reducers: {
    SetTeDetailTeam: (state, action) => {
      state = action.payload;
      return state;
    },
    SearchTeDetailTeam: (state, action) => {
      const { value } = action.payload;
      return state.filter((item) => {
        return (
          item.name.toLowerCase().includes(value.toLowerCase()) ||
          item.userName.toLowerCase().includes(value.toLowerCase())
        );
      });
    },
  },
});

export const {
  SetTeDetailTeam,
  SearchTeDetailTeam,
} = TeDetailTeamSlice.actions;

export const GetTeDetailTeam = (state) => state.teDetailTeamFactory;

export default TeDetailTeamSlice.reducer;
