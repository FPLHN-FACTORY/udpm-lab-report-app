import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teStudentClassesSlice = createSlice({
  name: "teCtudentClasses",
  initialState,
  reducers: {
    SetStudentClasses: (state, action) => {
      state = action.payload;
      return state;
    },

    AddStudentClassNotJoin: (state, action) => {
      const listRemove = action.payload.map((item) => item.id);
      const newState = state.filter((item) => !listRemove.includes(item.id));
      state = newState;
      return state;
    },
  },
});

export const { SetStudentClasses, AddStudentClassNotJoin } =
  teStudentClassesSlice.actions;

export const GetStudentClasses = (state) => state.teStudentClasses;

export default teStudentClassesSlice.reducer;
