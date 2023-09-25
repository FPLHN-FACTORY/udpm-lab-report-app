import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const adLevelSlice = createSlice({
  name: "adLevel",
  initialState,
  reducers: {
    SetLevel: (state, action) => {
      state = action.payload;
      return state;
    },
    AddLevel: (state, action) => {
      const data = action.payload;
      let newLevel = {
        stt: state.length + 1,
        id: data.id,
        name: data.name,
      };
      state.unshift(newLevel);
      return state;
    },
    UpdateLevel: (state, action) => {
      const updateLevel = action.payload;
      const index = state.findIndex(
        (level) => level.id === updateLevel.id
      );
      if (index !== -1) {
        state[index].name = updateLevel.name;
      }
    },
    DeleteLevel: (state, action) => {
      const idLevel = action.payload;
      const index = state.findIndex((level) => level.id === idLevel);
      state.splice(index, 1);
    },
  },
});

export const { SetLevel, AddLevel, UpdateLevel, DeleteLevel } =
adLevelSlice.actions;

export const GetLevel = (state) => state.adLevel;

export default adLevelSlice.reducer;
