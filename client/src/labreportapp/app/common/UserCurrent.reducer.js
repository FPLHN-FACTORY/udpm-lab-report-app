import { createSlice } from "@reduxjs/toolkit";

const initialState = null;

const UserCurrentSlice = createSlice({
  name: "userCurrent",
  initialState,
  reducers: {
    SetUserCurrent: (state, action) => {
      state = action.payload;
      return state;
    },
  },
});

export const { SetUserCurrent } = UserCurrentSlice.actions;

export const GetUserCurrent = (state) => state.userCurrent;

export default UserCurrentSlice.reducer;
