import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const AdMemberFactorySlice = createSlice({
  name: "adMemberFactory",
  initialState,
  reducers: {
    SetAdMemberFactory: (state, action) => {
      state = action.payload;
      return state;
    },
    AddAdMemberFactory: (state, action) => {
      state.forEach((item) => {
        item.stt = item.stt + 1;
      });
      if (state.length === 10) {
        state.pop();
      }
      state.unshift(action.payload);
      return state;
    },
    UpdateAdMemberFactory: (state, action) => {
      let data = action.payload;
      state.forEach((item) => {
        if (item.id === data.id) {
          item.statusMemberFactory = data.statusMemberFactory;
          item.roleMemberFactory = data.roleMemberFactory;
          item.numberTeam = data.numberTeam;
        }
      });
      return state;
    },
  },
});

export const { SetAdMemberFactory, AddAdMemberFactory, UpdateAdMemberFactory } =
  AdMemberFactorySlice.actions;

export const GetAdMemberFactory = (state) => state.adMemberFactory;

export default AdMemberFactorySlice.reducer;
