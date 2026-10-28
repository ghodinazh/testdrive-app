package com.allur.testdrive.service;

import com.allur.testdrive.model.Booking;
import com.allur.testdrive.model.BookingStatus;
import com.allur.testdrive.model.User;
import com.allur.testdrive.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EmailService emailService;

    public Booking createBooking(User user, String firstName, String lastName, String phone,
                                  LocalDate date, String location, MultipartFile licenseFile) throws IOException {
        Booking booking = Booking.builder()
                .user(user)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .testDriveDate(date)
                .location(location)
                .licenseData(licenseFile.getBytes())
                .licenseFilename(licenseFile.getOriginalFilename())
                .status(BookingStatus.PENDING)
                .reminderSent(false)
                .build();
        return bookingRepository.save(booking);
    }

    public void approveBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(BookingStatus.APPROVED);
        bookingRepository.save(booking);
        emailService.sendApprovalEmail(booking.getUser().getEmail(), booking);
    }

    public void rejectBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена: " + id));
    }

    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsForReminder(LocalDate date) {
        return bookingRepository.findByTestDriveDateAndStatusAndReminderSentFalse(date, BookingStatus.APPROVED);
    }

    public void markReminderSent(Booking booking) {
        booking.setReminderSent(true);
        bookingRepository.save(booking);
    }
}
