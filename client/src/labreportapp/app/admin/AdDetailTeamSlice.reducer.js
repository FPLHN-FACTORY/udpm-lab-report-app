import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const AdDetailTeamSlice = createSlice({
  name: "adDetailTeam",
  initialState,
  reducers: {
    SetAdDetailTeam: (state, action) => {
      state = action.payload;
      return state;
    },
    DeleteMemberTeamFactory: (state, action) => {
      let id = action.payload;
      state.forEach((item, index) => {
        if (item.idMemberTeamFactory === id) {
          state.splice(index, 1);
        }
      });
      return state;
    },
    DeleteListMemberTeamFactory: (state, action) => {
      const listId = action.payload;
      const updatedState = state.filter(
        (item) => !listId.includes(item.idMemberTeamFactory)
      );
      return updatedState;
    },
    SearchAdDetailTeam: (state, action) => {
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
  SetAdDetailTeam,
  SearchAdDetailTeam,
  DeleteMemberTeamFactory,
  DeleteListMemberTeamFactory,
} = AdDetailTeamSlice.actions;

export const GetAdDetailTeam = (state) => state.adDetailTeam;

export default AdDetailTeamSlice.reducer;
