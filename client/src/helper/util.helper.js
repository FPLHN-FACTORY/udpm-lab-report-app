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
