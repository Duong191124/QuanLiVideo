package dao.impl;

import dao.AbstractDAO;
import dao.VideoDAO;
import entity.Video;

import java.util.List;

public class VideoDaoImpl extends AbstractDAO<Video> implements VideoDAO {
    @Override
    public Video findById(Integer id) {
        return super.findById(Video.class, id);
    }

    @Override
    public Video findByHref(String href) {
        String sql = "SELECT o FROM Video o WHERE o.href = ?0";
        return super.findOne(Video.class, sql ,href);
    }

    @Override
    public List<Video> findAll() {
        return super.findAll(Video.class, true);
    }

    @Override
    public List<Video> findAll(int pageNumber, int pageSize) {
        return super.findAll(Video.class, true, pageNumber, pageSize);
    }
}
