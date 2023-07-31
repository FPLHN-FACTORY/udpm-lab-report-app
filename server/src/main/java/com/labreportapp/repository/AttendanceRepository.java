package com.labreportapp.repository;

import com.labreportapp.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author thangncph26123
 */
@Repository(AttendanceRepository.NAME)
public interface AttendanceRepository extends JpaRepository<Attendance, String> {

    String NAME = "BaseAttendanceRepository";
}
