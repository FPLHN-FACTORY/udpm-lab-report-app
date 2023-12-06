import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teMeetingRequestSlice = createSlice({
  name: "teMeetingRequestClass",
  initialState,
  reducers: {
    SetMeetingRequest: (state, action) => {
      state = action.payload;
      return state;
    },
    UpdateMeetingRequest: (state, action) => {
      let data = action.payload;
      console.log("aaaaaaaaavas");
      console.log(data);
      state.forEach((item) => {
        if (item.id === data.id) {
          item.name = data.name;
          item.meetingDate = data.meetingDate;
          item.typeMeeting = data.typeMeeting;
          item.idMeetingPeriod = data.idMeetingPeriod;
          item.meetingPeriod = data.meetingPeriod;
          item.startHour = data.startHour;
          item.startMinute = data.startMinute;
          item.endHour = data.endHour;
          item.endMinute = data.endMinute;
          item.teacher = data.teacher;
          item.statusMeetingRequest = data.statusMeetingRequest;
          item.teacherId = data.teacherId;
        }
      });
      return state;
    },
  },
});

export const { SetMeetingRequest, UpdateMeetingRequest } =
  teMeetingRequestSlice.actions;

export const GetMeetingRequest = (state) => state.teMeetingRequestClass;

export default teMeetingRequestSlice.reducer;
