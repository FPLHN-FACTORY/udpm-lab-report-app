import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const teStudentClassesSlice = createSlice({
  name: "teCtudentClasses",
  initialState,
  reducers: {
    SetStudentClasses: (state, action) => {
      state = action.payload;
      return state;
    },
    // CreateProject: (state, action) => {
    //   const data = action.payload;
    //   let newProject = {
    //     id: data.id,
    //     name: data.name,
    //     progress: data.progress,
    //     backGroundImage: data.backgroundImage,
    //     backGroundColor: data.backgroundColor,
    //     code: data.code,
    //     descriptions: data.descriptions,
    //     startTime: data.startTime,
    //     endTime: data.endTime,
    //     statusProject:
    //       data.statusProject === "DA_DIEN_RA"
    //         ? "0"
    //         : data.statusProject === "DANG_DIEN_RA"
    //         ? "1"
    //         : "2",
    //   };
    //   state.unshift(newProject);
    //   return state;
    // },
    AddStudentClassNotJoin: (state, action) => {
      const listRemove = action.payload.map((item) => item.id);
      const newState = state.filter((item) => !listRemove.includes(item.id));
      state = newState;
      console.log("đákakadksađá");
      console.log(state);
      return state;
    },
    // getStudentByIdTeam: (state, action) => {
    //   const id = action.payload.id;
    //   const newState = state.filter((item) => item.id === id);
    //   state = newState;
    //   return state;
    // },
  },
});

export const { SetStudentClasses, AddStudentClassNotJoin } =
  teStudentClassesSlice.actions;

export const GetStudentClasses = (state) => state.teStudentClasses;

export default teStudentClassesSlice.reducer;
