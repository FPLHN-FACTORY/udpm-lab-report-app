import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const ActivityManagementSlice = createSlice({
    name: "activityManagement",
    initialState,
    reducers: {
        SetActivityManagement: (state, action) => {
            state = action.payload;
            return state;
        },
        CreateActivityManagement: (state, action) => {
            const data = action.payload;
            let newActivity = {
                stt: state.length + 1,
                id: data.id,
                name: data.name,
                startTime: data.startTime,
                endTime: data.endTime,
                level: data.level,
                semesterId: data.semesterId,
            };
            state.unshift(newActivity);
            return state;
        },
        UpdateActivityManagement: (state, action) => {
            const updateActivity = action.payload;
            const index = state.findIndex(
                (activity) => activity.id === updateActivity.id
            );
            if (index !== - 1){
                state[index].name = updateActivity.name;
                state[index].startTime = updateActivity.startTime;
                state[index].endTime = updateActivity.endTime;
                state[index].level = updateActivity.level;
                state[index].semesterId = updateActivity.semesterId;
            }
        },
    },
});

export const {SetActivityManagement, CreateActivityManagement, UpdateActivityManagement} = ActivityManagementSlice.actions;
export const GetActivityManagement = (state) => state.activityManagement;
export default ActivityManagementSlice.reducer;