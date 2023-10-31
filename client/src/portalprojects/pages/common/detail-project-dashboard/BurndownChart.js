import React, { useEffect, useState } from "react";

import CanvasJSReact from "@canvasjs/react-charts";
import { DashboardApi } from "../../../api/dashboard/Dashboard.api";

var CanvasJSChart = CanvasJSReact.CanvasJSChart;

const BurndownChart = ({ item, allTodoTypeWork, listTodoComplete }) => {
  const [dateArray, setDateArray] = useState([]);

  useEffect(() => {
    if (item != null) {
      console.log(item);
      const dateA = new Date(item.startTime);
      const dateB = new Date(item.endTime);

      const timeDifference = dateB - dateA;

      const daysDifference = Math.floor(timeDifference / (1000 * 3600 * 24));

      const dataPoints = [];

      const dataTodoComplete = [];

      const workPerDay = allTodoTypeWork / daysDifference;

      for (let i = item.startTime; i < item.endTime; i += 86400000) {
        let currentDate = new Date(i);
        const day = currentDate.getDate();
        const month = currentDate.getMonth() + 1;
        const year = currentDate.getFullYear();

        const label = `${day}/${month}/${year}`;
        const dataPoint = {
          y: allTodoTypeWork,
          label: label,
        };

        allTodoTypeWork = allTodoTypeWork - workPerDay;

        dataPoints.push(dataPoint);
      }

      setDateArray(dataPoints);
    }
  }, [item, allTodoTypeWork, listTodoComplete]);

  const optionsTodoList = {
    animationEnabled: true,
    theme: "light2",
    title: {
      text: "Burndown",
    },
    axisX: {
      title: "Thời gian",
      reversed: true,
    },
    axisY: {
      title: "Số công việc còn lại",
      includeZero: true,
    },
    data: [
      {
        type: "line",
        name: "Kết quả dự kiến",
        dataPoints: dateArray.reverse(),
      },
    ],
  };

  return (
    <div>
      <div style={{ fontSize: 20, fontWeight: 500 }}>Biểu đồ Burndown</div>
      <CanvasJSChart options={optionsTodoList} />
    </div>
  );
};

export default BurndownChart;
