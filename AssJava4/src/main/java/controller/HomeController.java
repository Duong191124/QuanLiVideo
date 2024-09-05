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

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/index", "/favorite", "/history"})
public class HomeController extends HttpServlet {

    private VideoService videoService = new VideoServiceImpl();
    private static final int VIDEO_MAX_PAGE_SIZE = 4;
    private HistoryService historyService = new HistoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String path = req.getServletPath();
        switch (path){
            case "/index":
                doGetIndex(req, resp);
                break;
            case "/favorite":
                doGetFavorite(session, req, resp);
                break;
            case "/history":
                doGetHistory(session,req, resp);
                break;
        }
    }

    private void doGetIndex(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Video> countVideo = videoService.findAll();
        int maxPage = (int) Math.ceil(countVideo.size() / (double) VIDEO_MAX_PAGE_SIZE);
        req.setAttribute("maxPage", maxPage);
        String pageNumber = req.getParameter("page");
        List<Video> videos;
        if(pageNumber == null || maxPage < Integer.valueOf(pageNumber)){
            videos = videoService.findAll(1, VIDEO_MAX_PAGE_SIZE);
            req.setAttribute("currentPage", 1);
        }else{
            videos = videoService.findAll(Integer.valueOf(pageNumber), VIDEO_MAX_PAGE_SIZE);
            req.setAttribute("currentPage", Integer.valueOf(pageNumber));
        }
        req.setAttribute("videos", videos);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/user/index.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void doGetFavorite(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) session.getAttribute(SessionAttr.CURRENT_USER);
        List<History> historyList = historyService.findByUserAndIsLiked(user.getUsername());
        List<Video> videos = new ArrayList<>();
        historyList.forEach(item -> videos.add(item.getVideo()));
        req.setAttribute("videos", videos);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/user/favorite.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void doGetHistory(HttpSession session,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) session.getAttribute(SessionAttr.CURRENT_USER);
        List<History> historyList = historyService.findByUser(user.getUsername());
        List<Video> videos = new ArrayList<>();
        historyList.forEach(item -> videos.add(item.getVideo()));
        req.setAttribute("videos", videos);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/views/user/history.jsp");
        requestDispatcher.forward(req, resp);
    }


}
