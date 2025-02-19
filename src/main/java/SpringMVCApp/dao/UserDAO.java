package SpringMVCApp.dao;

import SpringMVCApp.models.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<User> index() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Transactional(readOnly = true)
    public User show(int id) {
        return em.find(User.class, id);
    }

    @Transactional
    public void save(User user) {
        em.persist(user);
    }

    @Transactional
    public void update(int id, User updateUser) {
        User user = em.find(User.class, id);
        user.setName(updateUser.getName());
        user.setAge(updateUser.getAge());
        user.setEmail(updateUser.getEmail());
        em.merge(user);
    }

    @Transactional
    public void delete(int id) {
        User user = em.find(User.class, id);
        em.remove(user);
    }

}
