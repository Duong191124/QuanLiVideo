package service.impl;

import dao.UserDAO;
import dao.impl.UserDaoImpl;
import entity.User;
import service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private UserDAO dao;

    public UserServiceImpl(){
        dao = new UserDaoImpl();
    }

    @Override
    public User findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public User login(String username, String password) {
        return dao.findByUsernameAndPassword(username, password);
    }

    @Override
    public User resetPassword(String email) {
        User existUser = findByEmail(email);
        if(existUser != null){
            String newPass = String.valueOf((int) (Math.random() * ((9999-1000) + 1)) + 1000);
            existUser.setPassword(newPass);
            return dao.update(existUser);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public List<User> findAll(int pageNumber, int pageSize) {
        return dao.findAll(pageNumber, pageSize);
    }

    @Override
    public User create(String username, String password, String email) {
        User newuser = new User();
        newuser.setUsername(username);
        newuser.setPassword(password);
        newuser.setEmail(email);
        newuser.setAdmin(false);
        newuser.setActive(true);
        return dao.create(newuser);
    }

    @Override
    public User update(User entity) {
        return dao.update(entity);
    }

    @Override
    public User delete(String username) {
        User user = dao.findByUsername(username);
        user.setActive(false);
        return dao.update(user);
    }

    @Override
    public List<User> findUsersLikedVideoByVideoHref(String href) {
        Map<String, Object> parmas = new HashMap<>();
        parmas.put("videoHref", href);
        return dao.findUsersLikeByVideoHref(parmas);
    }
}
