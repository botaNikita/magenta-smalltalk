package ru.magentasmalltalk.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.magentasmalltalk.model.Invitation;
import ru.magentasmalltalk.model.Seminar;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.TestConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InvitationsDAOTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private InvitationsDAO invitationsDAO;

    @Test
    @Transactional
    public void sendInvitation() {
        // data preparation
        String TEXT = "text1";
        User user1 = new User();
        user1.setLogin("login1");
        user1.setEncodedPassword("password1");
        user1.setName("name1");
        User user2 = new User();
        user2.setLogin("login2");
        user2.setEncodedPassword("password2");
        user2.setName("name2");
        Seminar seminar = new Seminar();
        seminar.setDate(new Date(2020, Calendar.DECEMBER, 25));
        seminar.setTopic("topic");
        seminar.setAuditory("test");
        seminar.setPlacesNumber(10);
        manager.persist(user1);
        manager.persist(user2);
        manager.persist(seminar);

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

//        manager.refresh(found);
    }

    @Test
    @Transactional
    public void findInvitationsByUserId() {
        String TEXT_1 = "text1";
        String TEXT_2 = "text2";

        User user1 = new User();
        user1.setLogin("login1");
        user1.setEncodedPassword("password1");
        user1.setName("name1");
        User user2 = new User();
        user2.setLogin("login2");
        user2.setEncodedPassword("password2");
        user2.setName("name2");
        User user3 = new User();
        user3.setLogin("login3");
        user3.setEncodedPassword("password3");
        user3.setName("name3");

        LinkedList<User> users1 = new LinkedList();
        users1.add(user1);
        users1.add(user2);
        LinkedList<User> users2 = new LinkedList();
        users2.add(user1);
        users2.add(user3);

        Seminar seminar1 = new Seminar();
        seminar1.setDate(new Date(2020, Calendar.JUNE, 15));
        seminar1.setTopic("topic1");
        seminar1.setAuditory("test1");
        seminar1.setPlacesNumber(50);
        Seminar seminar2 = new Seminar();
        seminar2.setDate(new Date(2020, Calendar.JULY, 25));
        seminar2.setTopic("topic2");
        seminar2.setAuditory("test2");
        seminar2.setPlacesNumber(10);

        Invitation invitation1 = new Invitation();
        invitation1.setText(TEXT_1);
        invitation1.setUsers(users1);
        invitation1.setSeminar(seminar1);
        Invitation invitation2 = new Invitation();
        invitation2.setText(TEXT_2);
        invitation2.setUsers(users2);
        invitation2.setSeminar(seminar2);

        manager.persist(user1);
        manager.persist(user2);
        manager.persist(user3);
        manager.persist(seminar1);
        manager.persist(seminar2);
        manager.persist(invitation1);
        manager.persist(invitation2);

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
    @Transactional
    public void findInvitationsBySeminarId() {
        String TEXT_1 = "text1";
        String TEXT_2 = "text2";

        User user1 = new User();
        user1.setLogin("login1");
        user1.setEncodedPassword("password1");
        user1.setName("name1");
        User user2 = new User();
        user2.setLogin("login2");
        user2.setEncodedPassword("password2");
        user2.setName("name2");
        User user3 = new User();
        user3.setLogin("login3");
        user3.setEncodedPassword("password3");
        user3.setName("name3");

        LinkedList<User> users1 = new LinkedList();
        users1.add(user1);
        users1.add(user2);
        LinkedList<User> users2 = new LinkedList();
        users2.add(user1);
        users2.add(user3);

        Seminar seminar1 = new Seminar();
        seminar1.setDate(new Date(2020, Calendar.JUNE, 15));
        seminar1.setTopic("topic1");
        seminar1.setAuditory("test1");
        seminar1.setPlacesNumber(50);
        Seminar seminar2 = new Seminar();
        seminar2.setDate(new Date(2020, Calendar.JULY, 25));
        seminar2.setTopic("topic2");
        seminar2.setAuditory("test2");
        seminar2.setPlacesNumber(10);

        Invitation invitation1 = new Invitation();
        invitation1.setText(TEXT_1);
        invitation1.setUsers(users1);
        invitation1.setSeminar(seminar1);
        Invitation invitation2 = new Invitation();
        invitation2.setText(TEXT_2);
        invitation2.setUsers(users2);
        invitation2.setSeminar(seminar2);

        manager.persist(user1);
        manager.persist(user2);
        manager.persist(user3);
        manager.persist(seminar1);
        manager.persist(seminar2);
        manager.persist(invitation1);
        manager.persist(invitation2);

        // check
        List<Invitation> found = invitationsDAO.findInvitationsBySeminarId(seminar1.getId());
        assertNotNull(found);
        assertEquals(1, found.size());
        assertNotNull(found.get(0));
        assertEquals(invitation1.getId(), found.get(0).getId());
    }
}