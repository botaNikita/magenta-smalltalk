package ru.magentasmalltalk.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.magentasmalltalk.model.Message;
import ru.magentasmalltalk.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class MessagesDAOTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private MessagesDAO messagesDAO;

    @Before
    public void setUp() {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
        messagesDAO = new MessagesDAO(manager);
    }

    @After
    public void tearDown() {
        if (manager != null) {
            manager.close();
        }
        if (factory != null) {
            factory.close();
        }
    }

    @Test
    public void sendMessage() {
        // data preparation
        String TEXT = "text1";
        User user1 = new User();
        user1.setLogin("login1");
        User user2 = new User();
        user2.setLogin("login2");
        manager.getTransaction().begin();
        manager.persist(user1);
        manager.persist(user2);
        manager.getTransaction().commit();

        // check properties of returned object
        List<User> users = new LinkedList();
        users.add(user1);
        users.add(user2);
        Message message = messagesDAO.sendMessage(TEXT, users);
        assertNotNull(message);
        assertEquals(TEXT, message.getText());
        assertNotNull(message.getUsers());
        assertEquals(2, message.getUsers().size());
        assertNotNull(message.getUsers().get(0));
        assertEquals(user1.getId(), message.getUsers().get(0).getId());
        assertNotNull(message.getUsers().get(1));
        assertEquals(user2.getId(), message.getUsers().get(1).getId());
        assertNotEquals(0, message.getId());

        // check that the entity is saved in DB
        Message found = manager.find(Message.class, message.getId());
        assertNotNull(found);
        manager.refresh(found);
    }

    @Test
    public void findMessagesByUserId() {
        String TEXT_1 = "text1";
        String TEXT_2 = "text2";

        User user1 = new User();
        user1.setLogin("login1");
        User user2 = new User();
        user2.setLogin("login2");
        User user3 = new User();
        user3.setLogin("login3");

        List<User> users1 = new LinkedList();
        users1.add(user1);
        users1.add(user2);
        List<User> users2 = new LinkedList();
        users2.add(user1);
        users2.add(user3);

        Message message1 = new Message();
        message1.setText(TEXT_1);
        message1.setUsers(users1);
        Message message2 = new Message();
        message2.setText(TEXT_2);
        message2.setUsers(users2);

        manager.getTransaction().begin();
        manager.persist(user1);
        manager.persist(user2);
        manager.persist(user3);
        manager.persist(message1);
        manager.persist(message2);
        manager.getTransaction().commit();

        // check
        List<Message> found = messagesDAO.findMessagesByUserId(user1.getId());
        assertNotNull(found);
        assertEquals(2, found.size());
        assertNotNull(found.get(0));
        assertEquals(message1.getId(), found.get(0).getId());
        assertNotNull(found.get(1));
        assertEquals(message2.getId(), found.get(1).getId());
    }
}