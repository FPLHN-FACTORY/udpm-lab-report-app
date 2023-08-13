// import { createSlice } from "@reduxjs/toolkit";

// const initialState = [];

// const studentClassesSlice = createSlice({
//   name: "studentClasses",
//   initialState,
//   reducers: {
//     SetStudentClassesNotJoin: (state, action) => {
//       state = action.payload;
//       return state;
//     },
//     // CreateProject: (state, action) => {
//     //   const data = action.payload;
//     //   let newProject = {
//     //     id: data.id,
//     //     name: data.name,
//     //     progress: data.progress,
//     //     backGroundImage: data.backgroundImage,
//     //     backGroundColor: data.backgroundColor,
//     //     code: data.code,
//     //     descriptions: data.descriptions,
//     //     startTime: data.startTime,
//     //     endTime: data.endTime,
//     //     statusProject:
//     //       data.statusProject === "DA_DIEN_RA"
//     //         ? "0"
//     //         : data.statusProject === "DANG_DIEN_RA"
//     //         ? "1"
//     //         : "2",
//     //   };
//     //   state.unshift(newProject);
//     //   return state;
//     // },
//     AddStudentClassNotJoin: (state, action) => {
//       const listRemove = action.payload.map((item) => item.id);
//       const newState = state.filter((item) => !listRemove.includes(item.id));
//       state = newState;
//       console.log("đákakadksađá");
//       console.log(state);
//       return state;
//     },
//   },
// });

// export const { SetStudentClassesNotJoin, AddStudentClassNotJoin } =
//   studentClassesSlice.actions;

// export const GetStudentClasses = (state) => state.studentClasses;

// export default studentClassesSlice.reducer;
