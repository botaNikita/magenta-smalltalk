package ru.magentasmalltalk.db;

import org.springframework.beans.factory.annotation.Autowired;
import ru.magentasmalltalk.model.Message;
import ru.magentasmalltalk.model.User;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

public class MessagesDAO extends BaseDAO<Message> {

    @Autowired
    public MessagesDAO(EntityManager manager) {
        super(manager);
    }

    public Message sendMessage(String text, List<User> users) {
        Message message = new Message();
        message.setText(text);
        message.setUsers(users);
        message.setDate(new Date());
        return persist(message);
    }

    public List<Message> findMessagesByUserId(int id) {
        return manager.createQuery("select m from Message m join m.users u where u.id = :id", Message.class)
                .setParameter("id", id)
                .getResultList();
    }
}
