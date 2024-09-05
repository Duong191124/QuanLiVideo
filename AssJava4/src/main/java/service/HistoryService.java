package service;

import entity.History;
import entity.User;
import entity.Video;

import java.util.List;

public interface HistoryService {
    List<History> findByUser(String username);
    List<History> findByUserAndIsLiked(String username);
    History findByUserIdAndVideoId(Integer UserId, Integer VideoId);
    History create(User user, Video video);
    Boolean updateLikeOrUnlike(User user, String videoHref);
}
