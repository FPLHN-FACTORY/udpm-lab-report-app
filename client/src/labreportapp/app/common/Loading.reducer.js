import { createSlice } from "@reduxjs/toolkit";

const initialState = false;

const LoadingSlice = createSlice({
  name: "loading",
  initialState,
  reducers: {
    SetLoadingFalse: (state) => {
      state = false;
      return state;
    },
    SetLoadingTrue: (state) => {
      state = true;
      return state;
    },
  },
});

export const { SetLoadingFalse, SetLoadingTrue } = LoadingSlice.actions;

export const GetLoading = (state) => state.loading;

export default LoadingSlice.reducer;
