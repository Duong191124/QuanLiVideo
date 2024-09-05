package dao.impl;

import dao.AbstractDAO;
import dao.HistoryDAO;
import entity.History;

import java.util.List;

public class HistoryDaoImpl extends AbstractDAO<History> implements HistoryDAO {
    @Override
    public List<History> findByUser(String username) {
        String sql = "SELECT o FROM History o WHERE o.user.username = ?0 AND o.video.isActive = 1" +
                " ORDER BY o.viewDate DESC";
        return super.findMany(History.class, sql, username);
    }


    @Override
    public List<History> findByUserAndIsLiked(String username) {
        String sql = "SELECT o FROM History o WHERE o.user.username = ?0 AND o.isLiked = 1" +
                " AND o.video.isActive = 1" +
                " ORDER BY o.viewDate DESC";
        return super.findMany(History.class, sql, username);
    }

    @Override
    public History findByUserIdAndVideoId(Integer userId, Integer videoId) {
        String sql = "SELECT o FROM History o " +
                "INNER JOIN o.user u " +
                "INNER JOIN o.video v " +
                "WHERE u.id = ?0 AND v.id = ?1 AND v.isActive = 1";
        return super.findOne(History.class, sql, userId, videoId);
    }


}
