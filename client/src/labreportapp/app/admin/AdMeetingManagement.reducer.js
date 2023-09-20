import { createSlice } from "@reduxjs/toolkit";
import { convertMeetingPeriodToNumber } from "../../helper/util.helper";

const initialState = [];

const adMeetingManagementSlice = createSlice({
  name: "adMeetingManagement",
  initialState,
  reducers: {
    SetMeeting: (state, action) => {
      state = action.payload;
      return state;
    },
    CreateMeeting: (state, action) => {
      let data = action.payload;
      let obj = {
        id: data.id,
        name: data.name,
        meetingDate: data.meetingDate,
        meetingPeriod: data.meetingPeriod,
        typeMeeting: data.typeMeeting,
        address: data.address,
        teacherId: data.teacherId,
        userNameTeacher: data.userNameTeacher,
        soDiemDanh: null,
        descriptions: data.descriptions,
      };
      state.unshift(obj);
      state.sort((a, b) => {
        if (a.meetingDate === b.meetingDate) {
          return b.meetingPeriod - a.meetingPeriod;
        }
        return new Date(b.meetingDate) - new Date(a.meetingDate);
      });
      for (let index = 0; index < state.length; index++) {
        let nameSuffix = state.length - index;
        state[index].name = "Buổi " + nameSuffix;
      }
      return state;
    },
    UpdateMeeting: (state, action) => {
      let data = action.payload;
      state.forEach((item) => {
        if (item.id === data.id) {
          item.name = data.name;
          item.meetingDate = data.meetingDate;
          item.meetingPeriod = data.meetingPeriod;
          item.typeMeeting = data.typeMeeting;
          item.address = data.address;
          item.descriptions = data.descriptions;
          item.teacherId = data.teacherId;
          item.userNameTeacher = data.userNameTeacher;
        }
      });
      return state;
    },
    DeleteMeeting: (state, action) => {
      let data = action.payload;
      state.forEach((item, index) => {
        if (item.id === data) {
          state.splice(index, 1);
        }
      });
      state.sort((a, b) => {
        if (a.meetingDate === b.meetingDate) {
          return b.meetingPeriod - a.meetingPeriod;
        }
        return new Date(b.meetingDate) - new Date(a.meetingDate);
      });
      for (let index = 0; index < state.length; index++) {
        let nameSuffix = state.length - index;
        state[index].name = "Buổi " + nameSuffix;
      }
      return state;
    },
  },
});

export const { SetMeeting, CreateMeeting, UpdateMeeting, DeleteMeeting } =
  adMeetingManagementSlice.actions;

export const GetMeeting = (state) => state.adMeetingManagement;

export default adMeetingManagementSlice.reducer;
