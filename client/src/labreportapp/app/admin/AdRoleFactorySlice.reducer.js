import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const adRoleFactorySlice = createSlice({
  name: "adRoleFactory",
  initialState,
  reducers: {
    SetRoleFactory: (state, action) => {
      state = action.payload;
      return state;
    },
    AddRoleFactory: (state, action) => {
      const data = action.payload;
      let newRoleFactory = {
        stt: state.length + 1,
        id: data.id,
        name: data.name,
        descriptions: data.descriptions,

      };
      state.unshift(newRoleFactory);
      return state;
    },
    UpdateRoleFactory: (state, action) => {
      const updateRoleFactory = action.payload;
      const index = state.findIndex(
        (roleFactory) => roleFactory.id === updateRoleFactory.id
      );
      if (index !== -1) {
        state[index].name = updateRoleFactory.name;
        state[index].descriptions = updateRoleFactory.descriptions;


      }
    },
    DeleteRoleFactory: (state, action) => {
      const idRoleFactory = action.payload;
      const index = state.findIndex((roleFactory) => roleFactory.id === idRoleFactory);
      state.splice(index, 1);
    },
  },
});

export const { SetRoleFactory,
               AddRoleFactory,
               UpdateRoleFactory,
               DeleteRoleFactory,
             } = adRoleFactorySlice.actions;

export const GetRoleFactory = (state) => state.adRoleFactory;

export default adRoleFactorySlice.reducer;
