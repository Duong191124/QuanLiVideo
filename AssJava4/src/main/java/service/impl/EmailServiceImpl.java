package service.impl;

import entity.User;
import jakarta.servlet.ServletContext;
import service.EmailService;
import ultil.SendEmailUtil;

public class EmailServiceImpl implements EmailService {

    private static final String EMAIL_WELCOME_SUBJECT = "Welcome to Online Entertainment";
    private static final String EMAIL_FORGOT_PASSWORD = "Online Entertainment - New Password";

    @Override
    public void sendMail(ServletContext context, User recipient, String type) {
        String host = context.getInitParameter("host");
        String port = context.getInitParameter("port");
        String user = context.getInitParameter("user");
        String pass = context.getInitParameter("pass");
        try {
            String content = null;
            String subject = null;
            switch (type){
                case "welcome":
                    subject = EMAIL_WELCOME_SUBJECT;
                    content = "Dear" + recipient.getUsername() + "Non";
                    break;
                case "forgot":
                    subject = EMAIL_FORGOT_PASSWORD;
                    content = "Dear" + recipient.getUsername() + " Quen pass ha con pass moi day cu: " + recipient.getPassword();
                    break;
                default:
                    subject = "Online Entertainment";
                    content = "Wrong!";
            }
            SendEmailUtil.sendEmail(host, port, user, pass, recipient.getEmail(), subject, content);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
