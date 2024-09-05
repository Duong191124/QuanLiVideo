package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import constant.SessionAttr;
import dto.VideoLikedInfo;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.StartService;
import service.UserService;
import service.impl.StartSeviceImpl;
import service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/admin", "/admin/favorite"})
public class HomeControllerAdmin extends HttpServlet {
    private StartService service = new StartSeviceImpl();
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute(SessionAttr.CURRENT_USER);
        if(currentUser != null && currentUser.getAdmin() == true){
            String path = req.getServletPath();
            switch (path){
                case "/admin":
                    doGetHome(req, resp);
                    break;
                case "/admin/favorite":
                    doGetFavorite(req, resp);
                    break;
            }

        }else{
            resp.sendRedirect("index");
        }
    }

    private void doGetFavorite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        String videoHref = req.getParameter("href");
        List<User> users = userService.findUsersLikedVideoByVideoHref(videoHref);
        if(users.isEmpty()){
            resp.setStatus(400);
        }else{
            ObjectMapper mapper = new ObjectMapper();
            String dataResponse = mapper.writeValueAsString(users);
            resp.setStatus(200);
            out.print(dataResponse);
            out.flush();
        }
    }

    private void doGetHome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<VideoLikedInfo> videoLikedInfos = service.fiVideoLikedInfos();
        req.setAttribute("videoLikedInfos", videoLikedInfos);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/admin/index.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
