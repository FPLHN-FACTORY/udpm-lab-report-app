import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const StMemberFactorySlice = createSlice({
  name: "stMemberFactory",
  initialState,
  reducers: {
    SetStMemberFactory: (state, action) => {
      state = action.payload;
      return state;
    },
    
  },
});

export const { SetStMemberFactory } =
StMemberFactorySlice.actions;

export const GetStMemberFactory = (state) => state.stMemberFactory;

export default StMemberFactorySlice.reducer;
