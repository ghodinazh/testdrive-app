package com.allur.testdrive.service;

import com.allur.testdrive.model.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendApprovalEmail(String toEmail, Booking booking) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Ваша заявка на тест-драйв одобрена — Allur");
            message.setText(
                    "Здравствуйте, " + booking.getFirstName() + "!\n\n" +
                    "Ваша заявка на тест-драйв была одобрена.\n\n" +
                    "Детали:\n" +
                    "Дата: " + booking.getTestDriveDate() + "\n" +
                    "Место: " + booking.getLocation() + "\n\n" +
                    "Пожалуйста, приходите вовремя и возьмите с собой водительское удостоверение.\n\n" +
                    "С уважением,\nTeam Allur"
            );
            mailSender.send(message);
            log.info("Approval email sent to {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send approval email to {}: {}", toEmail, e.getMessage());
        }
    }

    public void sendReminderEmail(String toEmail, Booking booking) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Напоминание о тест-драйве завтра — Allur");
            message.setText(
                    "Здравствуйте, " + booking.getFirstName() + "!\n\n" +
                    "Напоминаем, что ваш тест-драйв запланирован на завтра.\n\n" +
                    "Детали:\n" +
                    "Дата: " + booking.getTestDriveDate() + "\n" +
                    "Место: " + booking.getLocation() + "\n\n" +
                    "Не забудьте взять с собой водительское удостоверение.\n\n" +
                    "С уважением,\nTeam Allur"
            );
            mailSender.send(message);
            log.info("Reminder email sent to {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send reminder email to {}: {}", toEmail, e.getMessage());
        }
    }
}
