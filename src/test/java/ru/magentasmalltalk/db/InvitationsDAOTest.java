package ru.magentasmalltalk.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.magentasmalltalk.model.Invitation;
import ru.magentasmalltalk.model.Message;
import ru.magentasmalltalk.model.Seminar;
import ru.magentasmalltalk.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class InvitationsDAOTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private InvitationsDAO invitationsDAO;

    @Before
    public void setUp() {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
        invitationsDAO = new InvitationsDAO(manager);
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
    public void sendInvitation() {
        // data preparation
        String TEXT = "text1";
        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        Seminar seminar = new Seminar();
        seminar.setTopic("topic");
        manager.getTransaction().begin();
        manager.persist(user1);
        manager.persist(user2);
        manager.persist(seminar);
        manager.getTransaction().commit();

        // check properties of returned object
        LinkedList<User> users = new LinkedList();
        users.add(user1);
        users.add(user2);
        Invitation invitation = invitationsDAO.sendInvitation(seminar, TEXT, users);
        assertNotNull(invitation);
        assertEquals(TEXT, invitation.getText());
        assertNotNull(invitation.getUsers());
        assertEquals(2, invitation.getUsers().size());
        assertNotNull(invitation.getUsers().get(0));
        assertEquals(user1.getId(), invitation.getUsers().get(0).getId());
        assertNotNull(invitation.getUsers().get(1));
        assertEquals(user2.getId(), invitation.getUsers().get(1).getId());
        assertNotNull(invitation.getSeminar());
        assertEquals(seminar.getId(), invitation.getSeminar().getId());
        assertNotEquals(0, invitation.getId());

        // check that the entity is saved in DB
        Invitation found = manager.find(Invitation.class, invitation.getId());
        assertNotNull(found);
        manager.refresh(found);
    }

    @Test
    public void findInvitationsByUserId() {
        String TEXT_1 = "text1";
        String TEXT_2 = "text2";

        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        User user3 = new User();
        user3.setLogin("login3");
        user3.setPassword("password3");

        LinkedList<User> users1 = new LinkedList();
        users1.add(user1);
        users1.add(user2);
        LinkedList<User> users2 = new LinkedList();
        users2.add(user1);
        users2.add(user3);

        Seminar seminar1 = new Seminar();
        seminar1.setTopic("topic1");
        Seminar seminar2 = new Seminar();
        seminar2.setTopic("topic2");

        Invitation invitation1 = new Invitation();
        invitation1.setText(TEXT_1);
        invitation1.setUsers(users1);
        invitation1.setSeminar(seminar1);
        Invitation invitation2 = new Invitation();
        invitation2.setText(TEXT_2);
        invitation2.setUsers(users2);
        invitation2.setSeminar(seminar2);

        manager.getTransaction().begin();
        manager.persist(user1);
        manager.persist(user2);
        manager.persist(user3);
        manager.persist(seminar1);
        manager.persist(seminar2);
        manager.persist(invitation1);
        manager.persist(invitation2);
        manager.getTransaction().commit();

        // check
        List<Invitation> found = invitationsDAO.findInvitationsByUserId(user1.getId());
        assertNotNull(found);
        assertEquals(2, found.size());
        assertNotNull(found.get(0));
        assertEquals(invitation1.getId(), found.get(0).getId());
        assertNotNull(found.get(1));
        assertEquals(invitation2.getId(), found.get(1).getId());
    }

    @Test
    public void findInvitationsBySeminarId() {
        String TEXT_1 = "text1";
        String TEXT_2 = "text2";

        User user1 = new User();
        user1.setLogin("login1");
        user1.setPassword("password1");
        User user2 = new User();
        user2.setLogin("login2");
        user2.setPassword("password2");
        User user3 = new User();
        user3.setLogin("login3");
        user3.setPassword("password3");

        LinkedList<User> users1 = new LinkedList();
        users1.add(user1);
        users1.add(user2);
        LinkedList<User> users2 = new LinkedList();
        users2.add(user1);
        users2.add(user3);

        Seminar seminar1 = new Seminar();
        seminar1.setTopic("topic1");
        Seminar seminar2 = new Seminar();
        seminar2.setTopic("topic2");

        Invitation invitation1 = new Invitation();
        invitation1.setText(TEXT_1);
        invitation1.setUsers(users1);
        invitation1.setSeminar(seminar1);
        Invitation invitation2 = new Invitation();
        invitation2.setText(TEXT_2);
        invitation2.setUsers(users2);
        invitation2.setSeminar(seminar2);

        manager.getTransaction().begin();
        manager.persist(user1);
        manager.persist(user2);
        manager.persist(user3);
        manager.persist(seminar1);
        manager.persist(seminar2);
        manager.persist(invitation1);
        manager.persist(invitation2);
        manager.getTransaction().commit();

        // check
        List<Invitation> found = invitationsDAO.findInvitationsBySeminarId(seminar1.getId());
        assertNotNull(found);
        assertEquals(1, found.size());
        assertNotNull(found.get(0));
        assertEquals(invitation1.getId(), found.get(0).getId());
    }
}