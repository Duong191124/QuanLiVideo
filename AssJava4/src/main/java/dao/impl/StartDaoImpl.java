package dao.impl;

import dao.AbstractDAO;
import dao.StartDAO;
import dto.VideoLikedInfo;
import entity.User;

import java.util.ArrayList;
import java.util.List;

public class StartDaoImpl extends AbstractDAO<Object[]> implements StartDAO {
    @Override
    public List<VideoLikedInfo> fiVideoLikedInfos() {
        String sql = "select v.id, v.title, v.href, sum(cast(h.isLiked as int)) as totalLike" +
                " from Video v left join History h on v.id = h.videoId" +
                " where v.isActive = 1" +
                " group by v.id, v.title, v.href" +
                " order by sum(cast(h.isLiked as int)) desc";
        List<Object[]> objects =  super.findManyByNativeQuery(sql);
        List<VideoLikedInfo> result = new ArrayList<>();
        objects.forEach(objects1 -> {
            VideoLikedInfo videoLikedInfo = setDataVideoInfo(objects1);
            result.add(videoLikedInfo);
        });
        return result;
    }


    private VideoLikedInfo setDataVideoInfo(Object[] objects){
        VideoLikedInfo videoLikedInfo = new VideoLikedInfo();
        videoLikedInfo.setVideoId((Integer)objects[0]);
        videoLikedInfo.setTitle((String) objects[1]);
        videoLikedInfo.setHref((String) objects[2]);
        videoLikedInfo.setTotalLike(objects[3] == null ? 0 : (Integer) objects[3]);
        return videoLikedInfo;
    }
}
