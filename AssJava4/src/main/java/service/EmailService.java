package service;

import entity.User;
import jakarta.servlet.ServletContext;

public interface EmailService {
    void sendMail(ServletContext context, User recipient, String type);
}
