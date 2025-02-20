package SpringMVCApp.dao;

import SpringMVCApp.models.User;

import java.util.List;

public interface UserDao {
    void save(User user);
    User show(int id);
    void update(int id, User updateUser);
    void delete(int id);
    List<User> index();
}
