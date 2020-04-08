package ru.magentasmalltalk.db;

import com.sun.istack.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.magentasmalltalk.model.Message;
import ru.magentasmalltalk.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class MessagesDAO {

    @PersistenceContext
    protected EntityManager manager;

    @Transactional
    public Message sendMessage(String text, List<User> users) {
        Message message = new Message();
        message.setText(text);
        message.setUsers(users);
        message.setDate(new Date());

        manager.persist(message);

        return message;
    }

    @Nullable
    public List<Message> findMessagesByUserId(int id) {
        return manager.createQuery("select m from Message m join m.users u where u.id = :id", Message.class)
                .setParameter("id", id)
                .getResultList();
    }
}
