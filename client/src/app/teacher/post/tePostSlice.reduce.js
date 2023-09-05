import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const tePostSlice = createSlice({
  name: "tePost",
  initialState,
  reducers: {
    SetPost: (state, action) => {
      state = action.payload;
      return state;
    },
    NextPagePost: (state, action) => {
      state = [...state, ...action.payload];
      return state;
    },
    CreatePost: (state, action) => {
      const data = action.payload;
      let newTeam = {
        id: data.id,
        descriptions: data.descriptions,
        createdDate: data.createdDate,
      };
      state.unshift(newTeam);
      return state;
    },
    UpdatePost: (state, action) => {
      const team = action.payload;
      const index = state.findIndex((item) => item.id === team.id);
      if (index !== -1) {
        let update = state[index];
        update.id = team.id;
        update.descriptions = team.descriptions;
        update.createdDate = team.createdDate;
      }
      return state;
    },
    DeletePost: (state, action) => {
      const team = action.payload;
      const newData = state.filter((item) => item.id !== team.id);
      state = newData;
      return state;
    },
  },
});
export const { SetPost, NextPagePost, CreatePost, UpdatePost, DeletePost } =
  tePostSlice.actions;

export const GetPost = (state) => state.tePost;

export default tePostSlice.reducer;
