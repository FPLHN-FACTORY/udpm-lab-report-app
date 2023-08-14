import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const adSemesterSlice = createSlice({
  name: "adSemester",
  initialState,
  reducers: {
    SetSemester: (state, action) => {
      state = action.payload;
      return state;
    },
    AddSemester: (state, action) => {
      const data = action.payload;
      let newSemester = {
        stt: state.length + 1,
        id: data.id,
        name: data.name,
        startTime: data.startTime,
        endTime: data.endTime,
      };
      state.unshift(newSemester);
      return state;
    },
    UpdateSemester: (state, action) => {
      const updateSemester = action.payload;
      const index = state.findIndex(
        (semester) => semester.id === updateSemester.id
      );
      if (index !== -1) {
        state[index].name = updateSemester.name;
        state[index].startTime = updateSemester.startTime;
        state[index].endTime = updateSemester.endTime;
      }
    },
  },
});

export const { SetSemester, AddSemester, UpdateSemester } =
  adSemesterSlice.actions;

export const GetSemester = (state) => state.adSemester;

export default adSemesterSlice.reducer;
