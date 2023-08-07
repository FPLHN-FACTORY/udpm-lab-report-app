import moment from "moment";

export const formatDateTime = (date) => {
  const options = {
    month: "short",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  };
  const formattedDate = new Date(date).toLocaleDateString("en-US", options);
  return formattedDate;
};

export const formatDateToString = (timeString) => {
  let timeStr = new Date(timeString);
  let yearTimeStr = timeStr.getFullYear();
  let monthTimeStr = String(timeStr.getMonth() + 1).padStart(2, "0");
  let dateTimeStr = String(timeStr.getDate()).padStart(2, "0");
  let hoursTimeStr = String(timeStr.getHours()).padStart(2, "0");
  let minutesTimeStr = String(timeStr.getMinutes()).padStart(2, "0");
  let secondsTimeStr = String(timeStr.getSeconds()).padStart(2, "0");

  return `${yearTimeStr}-${monthTimeStr}-${dateTimeStr} ${hoursTimeStr}:${minutesTimeStr}:${secondsTimeStr}`;
};

export const convertDateToStringTodo = (timestamp) => {
  const date = moment(timestamp);
  const formattedTime = date.format("MMM DD");
  return formattedTime;
};
