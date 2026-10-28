package com.allur.testdrive.controller;

import com.allur.testdrive.model.Booking;
import com.allur.testdrive.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BookingService bookingService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "admin/dashboard";
    }

    @PostMapping("/booking/{id}/approve")
    public String approve(@PathVariable Long id) {
        bookingService.approveBooking(id);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/booking/{id}/reject")
    public String reject(@PathVariable Long id) {
        bookingService.rejectBooking(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/booking/{id}/license")
    public ResponseEntity<byte[]> getLicense(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);
        String filename = booking.getLicenseFilename() != null ? booking.getLicenseFilename() : "license";

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        String lower = filename.toLowerCase();
        if (lower.endsWith(".pdf"))        mediaType = MediaType.APPLICATION_PDF;
        else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) mediaType = MediaType.IMAGE_JPEG;
        else if (lower.endsWith(".png"))   mediaType = MediaType.IMAGE_PNG;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .contentType(mediaType)
                .body(booking.getLicenseData());
    }
}
