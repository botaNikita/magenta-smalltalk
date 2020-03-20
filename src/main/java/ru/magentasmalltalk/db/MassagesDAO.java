package ru.magentasmalltalk.db;

import com.sun.istack.Nullable;
import ru.magentasmalltalk.model.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

public class MassagesDAO extends BaseDAO<Message> {
    public MassagesDAO(EntityManager manager) {
        super(manager);
    }

    public Message sendMessage(String text, List<User> users) {
        Message message = new Message();
        message.setText(text);
        message.setUsers(users);
        return persist(message);
    }

    public List<Message> findMessagesByUser(User user) {
        Objects.requireNonNull(user, "findMessagesByUser(null)");
        return manager.createQuery("from Message m join m.users u where u.id = :id", Message.class)
                .setParameter("id", user.getId())
                .getResultList();
    }
}
