import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teLevelSlice = createSlice({
  name: "teLevel",
  initialState,
  reducers: {
    SetLevel: (state, action) => {
      state = action.payload.data;
      return state;
    },
  },
});

export const { SetLevel } = teLevelSlice.actions;

export const GetLevel = (state) => state.teLevel;

export default teLevelSlice.reducer;
