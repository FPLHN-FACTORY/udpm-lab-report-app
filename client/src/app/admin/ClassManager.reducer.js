import { createSlice } from "@reduxjs/toolkit";

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
        stt: state.length + 1,
        id: data.id,
        code: data.code,
        name: data.name,
        classPeriod: data.classPeriod,
        classSize: data.classSize,
        startTime: data.startTime,
        teacherId: data.teacherId,
        activityId: data.activityId
      };
      state.unshift(newCategory);
      return state;
    },
   
    
  },
}
);

export const { SetTeacherSemester ,CreateClass,SetMyClass} = teacherSemesterSlice.actions;
export const GetTeacherSemester = (state) => state.teacherSemester;



export default teacherSemesterSlice.reducer;
