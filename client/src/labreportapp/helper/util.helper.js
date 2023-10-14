export const areRolesEqual = (roleOld, roleNew) => {
  if (typeof roleOld === "string") {
    roleOld = [roleOld];
  }

  if (typeof roleNew === "string") {
    roleNew = [roleNew];
  }

  return JSON.stringify(roleOld) === JSON.stringify(roleNew);
};

export const checkInToAttend = (
  meetingDate,
  startHour,
  startMinute,
  endHour,
  endMinute
) => {
  // true là trong ca, false là ngoài ca
  const currentDate = new Date();
  const currentDateFormat = currentDate.setHours(0, 0, 0, 0);
  const meetingDateObject = new Date(meetingDate);
  const meetingDateFormat = meetingDateObject.setHours(0, 0, 0, 0);

  if (meetingDateFormat > currentDateFormat) {
    return false;
  } else if (meetingDateFormat < currentDateFormat) {
    return false;
  } else {
    const timeCurrent = new Date();
    const caHocStartTime = new Date(
      currentDate.getFullYear(),
      currentDate.getMonth(),
      currentDate.getDate(),
      startHour,
      startMinute
    );
    const caHocEndTime = new Date(
      currentDate.getFullYear(),
      currentDate.getMonth(),
      currentDate.getDate(),
      endHour,
      endMinute
    );
    if (caHocStartTime <= timeCurrent && timeCurrent <= caHocEndTime) {
      return true;
    } else {
      return false;
    }
  }
};

export const convertStatusByHourMinute = (
  meetingDate,
  startHour,
  startMinute,
  endHour,
  endMinute
) => {
  // true là tương lai, false là chưa tới
  const currentDate = new Date();
  const currentDateFormat = currentDate.setHours(0, 0, 0, 0);
  const meetingDateObject = new Date(meetingDate);
  const meetingDateFormat = meetingDateObject.setHours(0, 0, 0, 0);

  if (meetingDateFormat > currentDateFormat) {
    return true;
  } else if (meetingDateFormat < currentDateFormat) {
    return false;
  } else {
    const timeCurrent = new Date();
    const caHocStartTime = new Date(
      currentDate.getFullYear(),
      currentDate.getMonth(),
      currentDate.getDate(),
      startHour,
      startMinute
    );
    const caHocEndTime = new Date(
      currentDate.getFullYear(),
      currentDate.getMonth(),
      currentDate.getDate(),
      endHour,
      endMinute
    );
    if (caHocStartTime <= timeCurrent && timeCurrent <= caHocEndTime) {
      return true;
    } else if (timeCurrent < caHocStartTime) {
      return true;
    } else if (timeCurrent > caHocEndTime) {
      return false;
    }
    return true;
  }
};

export const convertDateLongToString = (long) => {
  const date = new Date(long);
  const day = String(date.getDate()).padStart(2, "0");
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const year = date.getFullYear();
  const format = `${day}/${month}/${year}`;
  return format;
};

function formatTime(hour, minute) {
  const formattedHour = hour < 10 ? `0${hour}` : hour;
  const formattedMinute = minute < 10 ? `0${minute}` : minute;
  return `${formattedHour}:${formattedMinute}`;
}
export function convertHourAndMinuteToString(
  startHour,
  startMinute,
  endHour,
  endMinute
) {
  const startTime = formatTime(startHour, startMinute);
  const endTime = formatTime(endHour, endMinute);
  return `${startTime} - ${endTime}`;
}
