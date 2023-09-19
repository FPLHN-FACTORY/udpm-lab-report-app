import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const adTeacherSlice = createSlice({
  name: "adTeacher",
  initialState,
  reducers: {
    SetAdTeacher: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetAdTeacher } = adTeacherSlice.actions;

export const GetAdTeacher = (state) => state.adTeacher;

export default adTeacherSlice.reducer;
