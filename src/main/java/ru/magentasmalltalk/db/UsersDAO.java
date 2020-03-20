package ru.magentasmalltalk.db;

import com.sun.istack.Nullable;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.model.UserRoles;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Objects;

public class UsersDAO extends BaseDAO<User> {

    public UsersDAO(EntityManager manager) {
        super(manager);
    }

    public User createUser(String login, String password) {
        return createUser(login, password, login, UserRoles.USER);
    }

    public User createUser(String login, String password, String name, UserRoles role) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setRole(role);
        return persist(user);
    }

    public User setUserName(int id, String newName){
        User user = findUserById(id);
        Objects.requireNonNull(user, "User with id=\"" + id + "\" wasn't found");

        user.setName(newName);

        return persist(user);
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
