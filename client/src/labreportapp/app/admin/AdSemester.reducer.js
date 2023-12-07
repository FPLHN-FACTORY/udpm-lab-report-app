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
        startTimeStudent: data.startTimeStudent,
        endTimeStudent: data.endTimeStudent,
        statusFeedback: 0,
      };
      state.unshift(newSemester);
      return state;
    },
    RemoveLastSemester: (state) => {
      if (state.length > 0) {
        state.pop();
        return state;
      }
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
        state[index].startTimeStudent = updateSemester.startTimeStudent;
        state[index].endTimeStudent = updateSemester.endTimeStudent;
      }
    },
    UpdateStatusFeedback: (state, action) => {
      const idUpdateSemester = action.payload;
      const index = state.findIndex(
        (semester) => semester.id === idUpdateSemester
      );
      if (index !== -1) {
        state[index].statusFeedback = 1;
      }
    },
    UpdateCloseStatusFeedback: (state, action) => {
      const idUpdateSemester = action.payload;
      const index = state.findIndex(
        (semester) => semester.id === idUpdateSemester
      );
      if (index !== -1) {
        state[index].statusFeedback = 0;
      }
    },
    DeleteSemester: (state, action) => {
      const idSemester = action.payload;
      const index = state.findIndex((semester) => semester.id === idSemester);
      state.splice(index, 1);
    },
  },
});

export const {
  SetSemester,
  AddSemester,
  RemoveLastSemester,
  UpdateSemester,
  DeleteSemester,
  UpdateStatusFeedback,
  UpdateCloseStatusFeedback
} = adSemesterSlice.actions;

export const GetSemester = (state) => state.adSemester;

export default adSemesterSlice.reducer;
