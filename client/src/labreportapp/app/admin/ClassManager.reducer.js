import { createSlice } from "@reduxjs/toolkit";
import { convertMeetingPeriodToNumber } from "../../helper/util.helper";

const initialState = [];

const adClassManagementSlice = createSlice({
  name: "adClassManagement",
  initialState,
  reducers: {
    SetMyClass: (state, action) => {
      state = action.payload;
      return state;
    },
    CreateClass: (state, action) => {
      const data = action.payload;
      let newClass = {
        stt: 1,
        id: data.id,
        code: data.code,
        nameClassPeriod: data.classPeriod,
        startHour: data.startHour,
        startMinute: data.startMinute,
        endHour: data.endHour,
        endMinute: data.endMinute,
        classSize: data.classSize,
        startTime: data.startTime,
        teacherId: data.teacherId,
        userNameTeacher: data.userNameTeacher,
        activityId: data.activityId,
        activityName: data.activityName,
        nameLevel: data.nameLevel,
        statusClass: data.statusClass,
        statusTeacherEdit: data.statusTeacherEdit,
      };
      state.forEach((item, index) => {
        item.stt = index + 1;
      });
      state.unshift(newClass);
      return state;
    },
    UpdateClass: (state, action) => {
      const data = action.payload;

      state.forEach((item) => {
        if (item.id === data.id) {
          item.code = data.code;
          item.nameClassPeriod = data.classPeriod;
          item.startHour= data.startHour;
          item.startMinute = data.startMinute;
          item.endHour = data.endHour;
          item.endMinute = data.endMinute;
          item.classSize = data.classSize;
          item.startTime = data.startTime;
          item.teacherId = data.teacherId;
          item.userNameTeacher = data.userNameTeacher;
          item.activityId = data.activityId;
          item.activityName = data.activityName;
          item.nameLevel = data.nameLevel;
          item.statusTeacherEdit = data.statusTeacherEdit;
        }
      });

      state.forEach((item, index) => {
        item.stt = index + 1;
      });
    },
  },
});

export const { CreateClass, UpdateClass, SetMyClass } =
  adClassManagementSlice.actions;
export const GetAdClassManagement = (state) => state.adClassManagement;

export default adClassManagementSlice.reducer;
