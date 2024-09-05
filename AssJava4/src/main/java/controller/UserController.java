package controller;

import constant.SessionAttr;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.EmailService;
import service.UserService;
import service.impl.EmailServiceImpl;
import service.impl.UserServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/logout", "/register", "/forgot", "/change"})
public class UserController extends HttpServlet {

    private UserService userService = new UserServiceImpl();
    private EmailService emailService = new EmailServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String path = req.getServletPath();
        switch (path){
            case "/login":
                doGetLogin(req, resp);
                break;
            case "/logout":
                doGetLogout(session, req, resp);
                break;
            case "/register":
                doGetRegister(req, resp);
                break;
            case "/forgot":
                doGetForgot(req, resp);
                break;
        }
    }

    private void doGetLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/user/login.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void doGetRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/user/register.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void doGetForgot(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/user/forgot-password.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void doGetLogout(HttpSession session,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        session.removeAttribute(SessionAttr.CURRENT_USER);
        resp.sendRedirect("index");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String path = req.getServletPath();
        switch (path){
            case "/login":
                doPostLogin(session, req, resp);
                break;
            case "/register":
                doPostRegister(session, req, resp);
                break;
            case "/forgot":
                doPostForgot(session, req, resp);
                break;
            case "/change":
                doPostChange(session, req, resp);
                break;
        }
    }

    private void doPostLogin(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.login(username, password);

        if(user != null){
            session.setAttribute(SessionAttr.CURRENT_USER, user);
            resp.sendRedirect("index");
        }else{
            resp.sendRedirect("login");
        }
    }

    private void doPostRegister(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        User user = userService.create(username, password, email);

        if(user != null){
            emailService.sendMail(getServletContext(), user, "welcome to summer rift");
            session.setAttribute(SessionAttr.CURRENT_USER, user);
            resp.sendRedirect("index");
        }else{
            resp.sendRedirect("register");
        }
    }

    private void doPostForgot(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String email = req.getParameter("email");
        User userNewPass = userService.resetPassword(email);
        if(userNewPass != null){
            emailService.sendMail(getServletContext(), userNewPass, "forgot");
            resp.setStatus(204);
        }else{
            resp.setStatus(400);
        }
    }

    private void doPostChange(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String currentPass = req.getParameter("current_password");
        String newPass = req.getParameter("new_password");
        User currentUser = (User) session.getAttribute(SessionAttr.CURRENT_USER);
        if(currentUser.getPassword().equals(currentPass)){
            currentUser.setPassword(newPass);
            User updateUser = userService.update(currentUser);
            if(updateUser != null){
                session.setAttribute(SessionAttr.CURRENT_USER, updateUser);
                resp.setStatus(204);
            }else{
                resp.setStatus(400);
            }
        }else{
            resp.setStatus(400);
        }
    }
}
