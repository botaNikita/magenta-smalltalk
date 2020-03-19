package ru.magentasmalltalk.db;

import com.sun.istack.Nullable;
import ru.magentasmalltalk.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Objects;

public class UsersDAO {
    private EntityManager manager;

    public UsersDAO(EntityManager manager) {
        Objects.requireNonNull(manager, "Entity manager shouldn't be null");
        this.manager = manager;
    }

    public User createUser(String login) {
        User user = new User();
        user.setLogin(login);

        manager.getTransaction().begin();
        try {
            manager.persist(user);
        } catch (Throwable ex) {
            manager.getTransaction().rollback();
            throw ex;
        }
        manager.getTransaction().commit();

        return user;
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
