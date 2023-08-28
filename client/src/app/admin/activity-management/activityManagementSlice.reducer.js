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
                level: parseInt(data.levelText),
                semesterId: data.semesterId, 
                nameSemester: data.nameSemester,
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
                state[index].level = parseInt(updateActivity.level);
                state[index].semesterId = updateActivity.semesterId;
                state[index].nameSemester = updateActivity.nameSemester;
            }
        },
        DeleteActivityManagement: (state, action) => {
            const idActivity = action.payload;
            const index = state.findIndex((activity) => activity.id === idActivity);
            state.splice(index, 1);
        }
    },
});

export const {SetActivityManagement, CreateActivityManagement, UpdateActivityManagement, DeleteActivityManagement} = ActivityManagementSlice.actions;
export const GetActivityManagement = (state) => state.activityManagement;
export default ActivityManagementSlice.reducer;