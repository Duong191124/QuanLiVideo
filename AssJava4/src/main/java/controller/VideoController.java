package controller;

import constant.SessionAttr;
import entity.History;
import entity.User;
import entity.Video;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.HistoryService;
import service.VideoService;
import service.impl.HistoryServiceImpl;
import service.impl.VideoServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = "/video")
public class VideoController extends HttpServlet {

    private VideoService videoService = new VideoServiceImpl();
    private HistoryService historyService = new HistoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionParams = req.getParameter("action");
        String href = req.getParameter("id");
        HttpSession session = req.getSession();

        switch (actionParams){
            case"watch":
                doGetWatch(session, href, req, resp);
                break;
            case"like":
                doGetLike(session, href, req, resp);
                break;
        }
    }

    private void doGetWatch(HttpSession session, String href, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Video video = videoService.findByHref(href);
        req.setAttribute("video", video);
        User currentUser = (User) session.getAttribute(SessionAttr.CURRENT_USER);
        if(currentUser != null){
            History history = historyService.create(currentUser, video);
            req.setAttribute("Like", history.getLiked());
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/user/video-detail.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void doGetLike(HttpSession session, String href, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        User currentUser = (User) session.getAttribute(SessionAttr.CURRENT_USER);
        boolean result = historyService.updateLikeOrUnlike(currentUser, href);
        if(result == true){
            resp.setStatus(204);
        }else{
            resp.setStatus(400);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
