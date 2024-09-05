package dao.impl;

import constant.NameStored;
import dao.AbstractDAO;
import dao.UserDAO;
import entity.User;

import java.util.List;
import java.util.Map;

public class UserDaoImpl extends AbstractDAO<User> implements UserDAO {
    @Override
    public User findById(Integer id) {
        return super.findById(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT o FROM User o WHERE o.email = ?0";
        return super.findOne(User.class, sql, email);
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT o FROM User o WHERE o.username = ?0";
        return super.findOne(User.class, sql, username);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT o FROM User o WHERE o.username = ?0 AND o.password = ?1";
        return super.findOne(User.class, sql, username, password);
    }

    @Override
    public List<User> findUsersLikeByVideoHref(Map<String, Object> params) {
        return super.callStored(NameStored.STORE_FIND_USER_LIKED_VIDEO, params);
    }

    @Override
    public List<User> findAll() {
        return super.findAll(User.class, true);
    }

    @Override
    public List<User> findAll(int pageNumber, int pageSize) {
        return super.findAll(User.class, true, pageNumber, pageSize);
    }


}
