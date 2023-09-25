export const convertMeetingPeriod = (meetingPeriod) => {
  let meetingPeriodStr = "";

  switch (meetingPeriod) {
    case 0:
      meetingPeriodStr = "Ca 1";
      break;
    case 1:
      meetingPeriodStr = "Ca 2";
      break;
    case 2:
      meetingPeriodStr = "Ca 3";
      break;
    case 3:
      meetingPeriodStr = "Ca 4";
      break;
    case 4:
      meetingPeriodStr = "Ca 5";
      break;
    case 5:
      meetingPeriodStr = "Ca 6";
      break;
    case 6:
      meetingPeriodStr = "Ca 7";
      break;
    case 7:
      meetingPeriodStr = "Ca 8";
      break;
    case 8:
      meetingPeriodStr = "Ca 9";
      break;
    case 9:
      meetingPeriodStr = "Ca 10";
      break;
    default:
      meetingPeriodStr = "";
  }

  return meetingPeriodStr;
};

export const convertMeetingPeriodToNumber = (meetingPeriodStr) => {
  switch (meetingPeriodStr) {
    case "CA_1":
      return 0;
    case "CA_2":
      return 1;
    case "CA_3":
      return 2;
    case "CA_4":
      return 3;
    case "CA_5":
      return 4;
    case "CA_6":
      return 5;
    case "CA_7":
      return 6;
    case "CA_8":
      return 7;
    case "CA_9":
      return 8;
    case "CA_10":
      return 9;
    default:
      return -1;
  }
};

export const convertMeetingPeriodToTime = (meetingPeriod) => {
  let meetingPeriodStr = "";

  switch (meetingPeriod) {
    case 0:
      meetingPeriodStr = "7:15 - 9:15";
      break;
    case 1:
      meetingPeriodStr = "9:25 - 11:25";
      break;
    case 2:
      meetingPeriodStr = "12:00 - 14:00";
      break;
    case 3:
      meetingPeriodStr = "14:10 - 16:10";
      break;
    case 4:
      meetingPeriodStr = "16:20 - 18:20";
      break;
    case 5:
      meetingPeriodStr = "18:30 - 20:30";
      break;
    case 6:
      meetingPeriodStr = "20:40 - 22:40";
      break;
    case 7:
      meetingPeriodStr = "22:50 - 00:50";
      break;
    case 8:
      meetingPeriodStr = "01:00 - 3:00";
      break;
    case 9:
      meetingPeriodStr = "03:10 - 05:10";
      break;
    default:
      meetingPeriodStr = "";
  }

  return meetingPeriodStr;
};
export const convertStatusMeetingByDateAndPeriod = (
  meetingDate,
  meetingPeriod
) => {
  const currentDate = new Date();

  // Chuyển đổi meetingPeriod thành một đối tượng mô tả khung giờ
  const meetingPeriods = {
    0: { start: "7:15", end: "9:15" },
    1: { start: "9:25", end: "11:25" },
    2: { start: "12:00", end: "14:00" },
    3: { start: "14:10", end: "16:10" },
    4: { start: "16:20", end: "18:20" },
    5: { start: "18:30", end: "20:30" },
    6: { start: "20:40", end: "22:40" },
    7: { start: "22:50", end: "00:50" },
    8: { start: "01:00", end: "3:00" },
    9: { start: "03:10", end: "05:10" },
  };

  if (meetingDate > currentDate) {
    return true; // Ca học đã diễn ra
  } else if (meetingDate < currentDate) {
    return false; // Ca học chưa diễn ra
  } else {
    const caHocTime = meetingPeriods[meetingPeriod];
    const caHocStartDate = new Date(
      currentDate.getFullYear(),
      currentDate.getMonth(),
      currentDate.getDate(),
      parseInt(caHocTime.start.split(":")[0]),
      parseInt(caHocTime.start.split(":")[1])
    );
    const caHocEndDate = new Date(
      currentDate.getFullYear(),
      currentDate.getMonth(),
      currentDate.getDate(),
      parseInt(caHocTime.end.split(":")[0]),
      parseInt(caHocTime.end.split(":")[1])
    );

    if (currentDate >= caHocStartDate && currentDate <= caHocEndDate) {
      return true;
    } else {
      return false;
    }
  }
};
