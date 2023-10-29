import React from "react";
import { Line } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Chart } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

const BurndownChart = () => {
  const chartData = {
    labels: [
      "2023-10-01",
      "2023-10-08",
      "2023-10-15",
      "2023-10-22",
      "2023-10-01",
      "2023-10-08",
      "2023-10-15",
      "2023-10-22",
      "2023-10-01",
      "2023-10-08",
      "2023-10-15",
      "2023-10-22",
      "2023-10-01",
      "2023-10-08",
      "2023-10-15",
      "2023-10-22",
      "2023-10-01",
      "2023-10-08",
      "2023-10-15",
      "2023-10-22",
      "2023-10-01",
      "2023-10-08",
      "2023-10-15",
      "2023-10-22",
    ],
    datasets: [
      {
        label: "Tiến độ thực tế",
        data: [20.5, 15, 10, 5],
        borderColor: "blue",
        fill: false,
      },
      {
        label: "Tiến độ dự kiến",
        data: [20, 10, 0, 0, 20],
        borderColor: "green",
        fill: false,
      },
    ],
  };

  return (
    <div>
      <div style={{ fontSize: 20, fontWeight: 500 }}>Biểu đồ Burndown</div>
      <Line
        data={chartData}
        options={{
          scales: {
            x: [
              {
                type: "category",
              },
            ],
          },
        }}
      />
    </div>
  );
};

export default BurndownChart;
