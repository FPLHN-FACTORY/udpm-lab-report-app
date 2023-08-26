import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teacherAttendanceMeetingSlice = createSlice({
  name: "teAttendanceMeeting",
  initialState,
  reducers: {
    SetAttendanceMeeting: (state, action) => {
      state = action.payload;
      return state;
    },
    UpdateAttendanceMeeting: (state, action) => {
      const listNew = action.payload;
      const listOld = state;
      const dataNew = listOld.map((itemOld) => {
        const matchingItem = listNew.find(
          (itemNew) =>
            itemNew.meetingId === itemOld.idMeeting &&
            itemNew.studentId === itemOld.idStudent
        );
        if (matchingItem) {
          return {
            ...itemOld,
            statusAttendance: matchingItem.statusAttendance,
          };
        }
        return itemOld;
      });
      state = dataNew;
      return state;
    },
  },
});

export const { SetAttendanceMeeting, UpdateAttendanceMeeting } =
  teacherAttendanceMeetingSlice.actions;

export const GetAttendanceMeeting = (state) => state.teAttendanceMeeting;

export default teacherAttendanceMeetingSlice.reducer;
