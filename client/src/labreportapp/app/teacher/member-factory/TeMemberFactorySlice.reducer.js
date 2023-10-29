import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const TeMemberFactorySlice = createSlice({
  name: "teMemberFactory",
  initialState,
  reducers: {
    SetTeMemberFactory: (state, action) => {
      state = action.payload;
      return state;
    },
    
  },
});

export const { SetTeMemberFactory } =
TeMemberFactorySlice.actions;

export const GetTeMemberFactory = (state) => state.teMemberFactory;

export default TeMemberFactorySlice.reducer;
