import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teClassSlice = createSlice({
  name: "teClass",
  initialState,
  reducers: {
    SetClass: (state, action) => {
      state = action.payload;
      return state;
    },
    UpdateClass: (state, action) => {
      const classFind = action.payload;
      classFind.statusClass = classFind.statusClass === "OPEN" ? 0 : 1;
      classFind.passWord = classFind.password;
      state = classFind;
      return state;
    },
  },
});
export const { SetClass, UpdateClass } = teClassSlice.actions;

export const GetClass = (state) => state.teClass;

export default teClassSlice.reducer;
