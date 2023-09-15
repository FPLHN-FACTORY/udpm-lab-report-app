import { createSlice } from "@reduxjs/toolkit";
import { convertMeetingPeriodToNumber } from "../../helper/util.helper";

const initialState = [];

const teacherSemesterSlice = createSlice({
  name: "teacherSemester",
  initialState,
  reducers: {
    SetTeacherSemester: (state, action) => {
      state = action.payload;
      return state;
    },
    CreateClass: (state, action) => {
      const data = action.payload;
      let newCategory = {
        stt: 1,
        id: data.id,
        code: data.code,
        classPeriod: data.classPeriod,
        classSize: data.classSize,
        startTime: data.startTime,
        teacherId: data.teacherId,
        activityId: data.activityId,
      };
      state.forEach((item, index) => {
        item.stt = index + 1;
      });
      state.unshift(newCategory);
      return state;
    },
    UpdateClass: (state, action) => {
      const data = action.payload;
      console.log(data);
      state.forEach((item) => {
        if (item.id === data.id) {
          item.code = data.code;
          item.classPeriod = convertMeetingPeriodToNumber(data.classPeriod);
          item.classSize = data.classSize;
          item.startTime = data.startTime;
          item.teacherId = data.teacherId;
          item.activityId = data.activityId;
        }
      });

      state.forEach((item, index) => {
        item.stt = index + 1;
      });
    },
  },
});

export const { SetTeacherSemester, CreateClass, UpdateClass, SetMyClass } =
  teacherSemesterSlice.actions;
export const GetTeacherSemester = (state) => state.teacherSemester;

export default teacherSemesterSlice.reducer;
