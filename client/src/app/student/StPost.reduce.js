import { createSlice } from "@reduxjs/toolkit";

const initialState = [];
const stPostSlice = createSlice({
    name: "stPost",
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
    },
  });
  export const { SetPost, NextPagePost } =
  stPostSlice.actions;
  
  export const GetPost = (state) => state.stPost;
  
  export default stPostSlice.reducer;