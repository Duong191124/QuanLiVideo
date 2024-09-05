package dao;

import entity.History;

import java.util.List;

public interface HistoryDAO {
    List<History> findByUser(String username);
    List<History> findByUserAndIsLiked(String username);
    History findByUserIdAndVideoId(Integer UserId, Integer VideoId);
    History create(History entity);
    History update(History entity);
    History delete(History entity);
}
