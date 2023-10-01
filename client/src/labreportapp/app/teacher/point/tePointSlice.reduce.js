import { createSlice } from "@reduxjs/toolkit";

const initialState = [];

const tePointSlice = createSlice({
  name: "tePoint",
  initialState,
  reducers: {
    SetPoint: (state, action) => {
      let newData = action.payload.map((item) => {
        let rateAttended = parseFloat(
          (parseFloat(item.numberOfSessionAttended) /
            parseFloat(item.numberOfSession)) *
            100
        );
        let ratePoint = parseFloat(
          (parseFloat(item.checkPointPhase1) +
            parseFloat(item.checkPointPhase2)) /
            2
        );
        return {
          ...item,
          statusTeam:
            rateAttended >= item.maximumNumberOfBreaks &&
            ratePoint >= item.pointMin
              ? 0
              : 1,
        };
      });
      state = newData;
      return state;
    },
    UpdatePoint: (state, action) => {
      const point = action.payload;
      const index = state.findIndex(
        (item) => item.idStudent === point.studentId
      );
      if (index !== -1) {
        let update = state[index];
        let rateAttended = parseFloat(
          (parseFloat(point.numberOfSessionAttended) /
            parseFloat(point.numberOfSession)) *
            100
        );
        let ratePoint = parseFloat(
          (parseFloat(point.checkPointPhase1) +
            parseFloat(point.checkPointPhase2)) /
            2
        );
        update.id = point.id;
        update.checkPointPhase1 = point.checkPointPhase1;
        update.checkPointPhase2 = point.checkPointPhase2;
        update.finalPoint = point.finalPoint;
        update.statusTeam =
          rateAttended >= point.maximumNumberOfBreaks &&
          ratePoint >= point.pointMin
            ? 0
            : 1;
      }
      return state;
    },
  },
});
export const { SetPoint, UpdatePoint } = tePointSlice.actions;

export const GetPoint = (state) => state.tePoint;

export default tePointSlice.reducer;
