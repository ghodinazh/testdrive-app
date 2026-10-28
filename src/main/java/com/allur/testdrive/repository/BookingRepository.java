package com.allur.testdrive.repository;

import com.allur.testdrive.model.Booking;
import com.allur.testdrive.model.BookingStatus;
import com.allur.testdrive.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findByTestDriveDateAndStatusAndReminderSentFalse(LocalDate date, BookingStatus status);
}
