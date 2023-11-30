import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const LabelManagementSlice = createSlice({
  name: "labelManagement",
  initialState,
  reducers: {
    SetLabelManagement: (state, action) => {
      state = action.payload;
      return state;
    },
    RemoveLastLabel: (state) => {
      if (state.length > 0) {
        state.pop();
      }
    },
    CreateLabelManagement: (state, action) => {
      const data = action.payload;
      let newLabel = {
        stt: state.length + 1,
        id: data.id,
        code: data.code,
        name: data.name,
        colorLabel: data.colorLabel,
      };
      state.unshift(newLabel);
      return state;
    },
    UpdateLabelManagement: (state, action) => {
      const updatedLabel = action.payload;
      const index = state.findIndex((label) => label.id === updatedLabel.id);
      if (index !== -1) {
        state[index].name = updatedLabel.name;
        state[index].colorLabel = updatedLabel.colorLabel;
      }
    },
    DeleteLabel: (state, action) => {
      const id = action.payload;
      const newState = state.filter((i) => i.id !== id);
      state = newState;
      return state;
    },
  },
});

export const {
  SetLabelManagement,
  CreateLabelManagement,
  RemoveLastLabel,
  UpdateLabelManagement,
  DeleteLabel,
} = LabelManagementSlice.actions;

export const GetLabelManagement = (state) => state.labelManagement;

export default LabelManagementSlice.reducer;
