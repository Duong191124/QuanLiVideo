package service.impl;

import dao.StartDAO;
import dao.impl.StartDaoImpl;
import dto.VideoLikedInfo;
import service.StartService;

import java.util.List;

public class StartSeviceImpl implements StartService {
    private StartDAO dao;
    public StartSeviceImpl(){
        dao = new StartDaoImpl();
    }
    @Override
    public List<VideoLikedInfo> fiVideoLikedInfos() {
        return dao.fiVideoLikedInfos();
    }
}
