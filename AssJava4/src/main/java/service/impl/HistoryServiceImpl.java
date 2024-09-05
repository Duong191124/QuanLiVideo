package service.impl;

import dao.HistoryDAO;
import dao.impl.HistoryDaoImpl;
import entity.History;
import entity.User;
import entity.Video;
import service.HistoryService;
import service.VideoService;

import java.sql.Timestamp;
import java.util.List;

public class HistoryServiceImpl implements HistoryService {
    private HistoryDAO dao;
    private VideoService videoService = new VideoServiceImpl();
    public HistoryServiceImpl(){
        dao = new HistoryDaoImpl();
    }

    @Override
    public List<History> findByUser(String username) {
        return dao.findByUser(username);
    }

    @Override
    public List<History> findByUserAndIsLiked(String username) {
        return dao.findByUserAndIsLiked(username);
    }

    @Override
    public History findByUserIdAndVideoId(Integer UserId, Integer VideoId) {
        return dao.findByUserIdAndVideoId(UserId, VideoId);
    }

    @Override
    public History create(User user, Video video) {
        History exitshistory = findByUserIdAndVideoId(user.getId(), video.getId());
        if(exitshistory == null){
            exitshistory = new History();
            exitshistory.setUser(user);
            exitshistory.setVideo(video);
            exitshistory.setViewDate(new Timestamp(System.currentTimeMillis()));
            exitshistory.setLiked(false);
            dao.create(exitshistory);
        }

        return exitshistory;
    }

    @Override
    public Boolean updateLikeOrUnlike(User user, String videoHref) {
        Video video = videoService.findByHref(videoHref);
        History history = findByUserIdAndVideoId(user.getId(), video.getId());
        if(history.getLiked() == false){
            history.setLiked(true);
            history.setLikeDate(new Timestamp(System.currentTimeMillis()));
        }else{
            history.setLiked(false);
            history.setLikeDate(null);
        }
        History updateHistory = dao.update(history);
        return updateHistory != null ? true : false;
    }
}
