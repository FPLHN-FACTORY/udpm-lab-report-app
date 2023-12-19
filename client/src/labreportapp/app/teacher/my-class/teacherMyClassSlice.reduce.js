import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teMyClassSlice = createSlice({
  name: "teMyClass",
  initialState,
  reducers: {
    SetTeacherMyClass: (state, action) => {
      state = action.payload.data;
      return state;
    },
    UpdateFiledClass: (state, action) => {
      const data = action.payload;

      state.forEach((item) => {
        if (item.id === data.id) {
          // item.code = data.code;
          item.classPeriod = data.classPeriod;
          item.startHour = data.startHour;
          item.startMinute = data.startMinute;
          item.endHour = data.endHour;
          item.endMinute = data.endMinute;
          item.classSize = data.classSize;
          item.startTime = data.startTime;
          item.teacherId = data.teacherId;
          item.userNameTeacher = data.userNameTeacher;
          // item.activityId = data.activityId;
          // item.activityName = data.activityName;
          // item.nameLevel = data.nameLevel;
          // item.statusTeacherEdit = data.statusTeacherEdit;
        }
      });

      // state.forEach((item, index) => {
      //   item.stt = index + 1;
      // });
    },
  },
});

export const { SetTeacherMyClass, UpdateFiledClass } = teMyClassSlice.actions;

export const GetTeacherMyClass = (state) => state.teMyClass;

export default teMyClassSlice.reducer;
