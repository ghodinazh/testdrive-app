package com.allur.testdrive.controller;

import com.allur.testdrive.model.Booking;
import com.allur.testdrive.model.User;
import com.allur.testdrive.service.BookingService;
import com.allur.testdrive.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    @GetMapping("/new")
    public String newBookingForm() {
        return "booking/form";
    }

    @PostMapping("/new")
    public String submitBooking(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phone,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate testDriveDate,
            @RequestParam String location,
            @RequestParam("licenseFile") MultipartFile licenseFile,
            Model model) {
        try {
            if (licenseFile.isEmpty()) {
                model.addAttribute("error", "Пожалуйста, загрузите файл водительского удостоверения");
                return "booking/form";
            }
            User user = userService.findByEmail(userDetails.getUsername());
            bookingService.createBooking(user, firstName, lastName, phone, testDriveDate, location, licenseFile);
            return "redirect:/booking/success";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при отправке заявки: " + e.getMessage());
            return "booking/form";
        }
    }

    @GetMapping("/success")
    public String success() {
        return "booking/success";
    }

    @GetMapping("/my")
    public String myBookings(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        List<Booking> bookings = bookingService.getBookingsByUser(user);
        model.addAttribute("bookings", bookings);
        return "booking/my-bookings";
    }
}
