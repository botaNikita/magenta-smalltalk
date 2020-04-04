package ru.magentasmalltalk.db;

import com.sun.istack.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.model.UserRoles;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Objects;

@Repository
public class UsersDAO {

    @PersistenceContext
    protected EntityManager manager;

    @Transactional
    public User createUser(String login, String password) {
        return createUser(login, password, login, UserRoles.USER);
    }

    @Transactional
    public User createUser(String login, String password, String name, UserRoles role) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setRole(role);

        manager.persist(user);

        return user;
    }

    @Transactional
    public User setUserName(int id, String newName){
        User user = findUserById(id);
        Objects.requireNonNull(user, "User with id=\"" + id + "\" wasn't found");
        user.setName(newName);

        manager.persist(user);

        return user;
    }

    @Nullable
    public User findUserById(int id) {
        try {
            return manager.createQuery("from User u where u.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Nullable
    public User findUserByLogin(String login) {
        try {
            return manager.createQuery("from User u where u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
