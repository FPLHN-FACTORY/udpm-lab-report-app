import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const adMeetingPeriodConfigurationSlice = createSlice({
  name: "adMeetingPeriodConfiguration",
  initialState,
  reducers: {
    SetMeetingPeriodConfiguration: (state, action) => {
      state = action.payload;
      return state;
    },
    AddMeetingPeriodConfiguration: (state, action) => {
      const data = action.payload;
      let newMeetingPeriod = {
        stt: state.length + 1,
        id: data.id,
        name: data.name,
        startHour: data.startHour,
        startMinute: data.startMinute,
        endHour: data.endHour,
        endMinute: data.endMinute,
      };
      state.unshift(newMeetingPeriod);
      return state;
    },
    RemoveLastMeetingPeriodConfiguration: (state) => {
      if (state.length > 0) {
        state.pop();
      }
    },
    UpdateMeetingPeriodConfiguration: (state, action) => {
      const updateMeetingPeriod = action.payload;
      const index = state.findIndex(
        (meetingPeriod) => meetingPeriod.id === updateMeetingPeriod.id
      );
      if (index !== -1) {
        state[index].name = updateMeetingPeriod.name;
        state[index].startHour = updateMeetingPeriod.startHour;
        state[index].startMinute = updateMeetingPeriod.startMinute;
        state[index].endHour = updateMeetingPeriod.endHour;
        state[index].endMinute = updateMeetingPeriod.endMinute;
      }
    },
    DeleteMeetingPeriodConfiguration: (state, action) => {
      const idMeetingPeriod = action.payload;
      const index = state.findIndex(
        (meetingPeriod) => meetingPeriod.id === idMeetingPeriod
      );
      state.splice(index, 1);
    },
  },
});

export const {
  SetMeetingPeriodConfiguration,
  RemoveLastMeetingPeriodConfiguration,
  AddMeetingPeriodConfiguration,
  UpdateMeetingPeriodConfiguration,
  DeleteMeetingPeriodConfiguration,
} = adMeetingPeriodConfigurationSlice.actions;

export const GetMeetingPeriodConfiguration = (state) =>
  state.adMeetingPeriodConfiguration;

export default adMeetingPeriodConfigurationSlice.reducer;
