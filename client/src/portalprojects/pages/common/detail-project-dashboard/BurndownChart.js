import React, { useEffect, useState } from "react";

import CanvasJSReact from "@canvasjs/react-charts";
import { DashboardApi } from "../../../api/dashboard/Dashboard.api";

var CanvasJSChart = CanvasJSReact.CanvasJSChart;

const BurndownChart = ({ item, allTodoTypeWork, listTodoComplete }) => {
  const [dateArray, setDateArray] = useState([]);
  const [dataTodoArray, setDataTodoArray] = useState([]);

  useEffect(() => {
    if (item != null) {
      const dateA = new Date(item.startTime);
      const dateB = new Date(item.endTime);

      const timeDifference = dateB - dateA;

      const daysDifference = Math.floor(timeDifference / (1000 * 3600 * 24));

      const dataPoints = [];

      const dataTodoComplete = [];

      const workPerDay = allTodoTypeWork / daysDifference;

      let allTodoTypeWorkCopy = allTodoTypeWork;

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

        if (i <= new Date().getTime()) {
          // eslint-disable-next-line no-loop-func
          listTodoComplete.forEach((item) => {
            let completionTimeTodo = new Date(item.completionTime);
            const dayTodo = completionTimeTodo.getDate();
            const monthTodo = completionTimeTodo.getMonth() + 1;
            const yearTodo = completionTimeTodo.getFullYear();
            if (dayTodo === day && monthTodo === month && yearTodo === year) {
              allTodoTypeWorkCopy = allTodoTypeWorkCopy - 1;
            }
          });
          const dataTodo = {
            y: allTodoTypeWorkCopy,
            label: label,
          };
          dataTodoComplete.push(dataTodo);
        }

        allTodoTypeWork = allTodoTypeWork - workPerDay;

        dataPoints.push(dataPoint);
      }

      setDateArray(dataPoints);
      setDataTodoArray(dataTodoComplete);
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
      reversed: false,
    },
    axisY: {
      title: "Số công việc còn lại",
      includeZero: true,
    },
    legend: {
      cursor: "pointer",
      itemclick: function (e) {},
    },
    data: [
      {
        type: "line",
        name: "Kết quả dự kiến",
        showInLegend: true,
        dataPoints: dateArray,
      },
      {
        type: "line",
        name: "Kết quả thực tế",
        showInLegend: true,
        dataPoints: dataTodoArray,
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
