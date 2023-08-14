import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teMyClassSlice = createSlice({
  name: "teMyClass",
  initialState,
  reducers: {
    SetTeacherMyClass: (state, action) => {
      state = action.payload.data;
      return state;
    },
  },
});

export const { SetTeacherMyClass } = teMyClassSlice.actions;

export const GetTeacherMyClass = (state) => state.teMyClass;

export default teMyClassSlice.reducer;
