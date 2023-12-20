import { createSlice } from "@reduxjs/toolkit";

const initialState = false;

const DPDetailProjectCheckRole = createSlice({
  name: "checkRole",
  initialState,
  reducers: {
    SetCheckRole: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetCheckRole } = DPDetailProjectCheckRole.actions;

export const GetCheckRole = (state) => state.checkRole;

export default DPDetailProjectCheckRole.reducer;
