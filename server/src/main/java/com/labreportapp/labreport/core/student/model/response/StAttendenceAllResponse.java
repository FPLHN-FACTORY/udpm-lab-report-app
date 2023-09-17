package com.labreportapp.labreport.core.student.model.response;

import com.labreportapp.labreport.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

public interface StAttendenceAllResponse extends IsIdentified {

  @Value("#{target.stt}")
  Integer getStt();

  @Value("#{target.name}")
  String getName();

  @Value("#{target.meeting_date}")
  Long getMeetingDate();

  @Value("#{target.meeting_period}")
  Integer getMeetingPeriod();

  @Value("#{target.type_meeting}")
  Integer getTypeMeeting();

  @Value("#{target.status}")
  Integer getStatus();

}
