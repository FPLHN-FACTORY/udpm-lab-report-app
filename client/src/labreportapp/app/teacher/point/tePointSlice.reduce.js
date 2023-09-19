import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const tePointSlice = createSlice({
  name: "tePoint",
  initialState,
  reducers: {
    SetPoint: (state, action) => {
      state = action.payload;
      return state;
    },
    CreatePoint: (state, action) => {
      const data = action.payload;
      let newTeam = {
        id: data.id,
        descriptions: data.descriptions,
        createdDate: data.createdDate,
      };
      state.unshift(newTeam);
      return state;
    },
    UpdatePoint: (state, action) => {
      const point = action.payload;
      const index = state.findIndex((item) => item.id === point.id);
      if (index !== -1) {
        let update = state[index];
        update.id = point.id;
        update.checkPointPhase1 = point.checkPointPhase1;
        update.checkPointPhase2 = point.checkPointPhase2;
        update.finalPoint = point.finalPoint;
      }
      return state;
    },
    DeletePoint: (state, action) => {
      const team = action.payload;
      const newData = state.filter((item) => item.id !== team.id);
      state = newData;
      return state;
    },
  },
});
export const { SetPoint, CreatePoint, UpdatePoint, DeletePoint } =
  tePointSlice.actions;

export const GetPoint = (state) => state.tePoint;

export default tePointSlice.reducer;
