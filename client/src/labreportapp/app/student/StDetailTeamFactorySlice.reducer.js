import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const StDetailTeamSlice = createSlice({
  name: "stDetailTeamFactory",
  initialState,
  reducers: {
    SetStDetailTeam: (state, action) => {
      state = action.payload;
      return state;
    },
    SearchStDetailTeam: (state, action) => {
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
    SetStDetailTeam,
    SearchStDetailTeam,
} = StDetailTeamSlice.actions;

export const GetStDetailTeam = (state) => state.stDetailTeamFactory;

export default StDetailTeamSlice.reducer;
