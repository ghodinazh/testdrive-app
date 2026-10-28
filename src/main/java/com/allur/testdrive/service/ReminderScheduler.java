package com.allur.testdrive.service;

import com.allur.testdrive.model.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReminderScheduler {

    private final BookingService bookingService;
    private final EmailService emailService;

    // Запускается каждый день в 09:00
    @Scheduled(cron = "0 0 9 * * *")
    public void sendReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Booking> bookings = bookingService.getBookingsForReminder(tomorrow);
        log.info("Checking reminders for {} — found {} bookings", tomorrow, bookings.size());
        for (Booking booking : bookings) {
            emailService.sendReminderEmail(booking.getUser().getEmail(), booking);
            bookingService.markReminderSent(booking);
        }
    }
}
