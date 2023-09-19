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
        meetingPeriod: convertMeetingPeriodToNumber(data.meetingPeriod),
        typeMeeting: data.typeMeeting === "ONLINE" ? 0 : 1,
        address: data.address,
        descriptions: data.descriptions,
      };
      state.unshift(obj);
      return state;
    },
    UpdateMeeting: (state, action) => {
      let data = action.payload;
      state.forEach((item) => {
        if (item.id === data.id) {
          item.name = data.name;
          item.meetingDate = data.meetingDate;
          item.meetingPeriod = convertMeetingPeriodToNumber(data.meetingPeriod);
          item.typeMeeting = data.typeMeeting === "ONLINE" ? 0 : 1;
          item.address = data.address;
          item.descriptions = data.descriptions;
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
      return state;
    },
  },
});

export const { SetMeeting, CreateMeeting, UpdateMeeting, DeleteMeeting } =
  adMeetingManagementSlice.actions;

export const GetMeeting = (state) => state.adMeetingManagement;

export default adMeetingManagementSlice.reducer;
