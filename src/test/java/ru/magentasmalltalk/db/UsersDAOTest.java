package ru.magentasmalltalk.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.model.UserRoles;
import ru.magentasmalltalk.tests.TestConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UsersDAOTest {

    @Autowired
    private UsersDAO usersDAO;

    @PersistenceContext
    private EntityManager manager;

    @Test
    @Transactional
    public void createUser() {
        // const
        String LOGIN = "login1";
        String PASSWORD = "password1";

        // check properties of returned object
        User user = usersDAO.createUser(LOGIN, PASSWORD);
        assertNotNull(user);
        assertEquals(LOGIN, user.getLogin());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(LOGIN, user.getName());
        assertEquals(UserRoles.USER, user.getRole());
        assertNotEquals(0, user.getId());

        // check that the entity is saved in DB
        User found = manager.find(User.class, user.getId());
        assertNotNull(found);
        manager.refresh(found);
    }

    @Test
    @Transactional
    public void createUserFull() {
        // const
        String LOGIN = "login2";
        String PASSWORD = "password2";
        String NAME = "name2";

        // check properties of returned object
        User user = usersDAO.createUser(LOGIN, PASSWORD, NAME, UserRoles.ADMIN);
        assertNotNull(user);
        assertEquals(LOGIN, user.getLogin());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(NAME, user.getName());
        assertEquals(UserRoles.ADMIN, user.getRole());
        assertNotEquals(0, user.getId());

        // check that the entity is saved in DB
        User found = manager.find(User.class, user.getId());
        assertNotNull(found);
        manager.refresh(found);
    }

    @Test
    @Transactional
    public void findUserById() {
        // const
        String LOGIN = "login3";
        String PASSWORD = "password3";

        // data preparation
        User user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        manager.persist(user);

        // check
        User found = usersDAO.findUserById(user.getId());
        assertNotNull(found);
        assertEquals(user.getId(), found.getId());
        assertEquals(LOGIN, found.getLogin());
        assertEquals(PASSWORD, found.getPassword());
    }

    @Test
    @Transactional
    public void findUserByLogin() {
        // const
        String LOGIN = "login4";
        String PASSWORD = "password4";

        // data preparation
        User user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        manager.persist(user);

        // check
        User found = usersDAO.findUserByLogin(LOGIN);
        assertNotNull(found);
        assertEquals(user.getId(), found.getId());
        assertEquals(LOGIN, found.getLogin());
        assertEquals(PASSWORD, found.getPassword());
    }

    @Test
    @Transactional
    public void setUserName() {
        // const
        String LOGIN = "login5";
        String PASSWORD = "password5";
        String NAME = "name5";
        String NEW_NAME = "newName";

        // data preparation
        User user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setName(NAME);
        manager.persist(user);

        // check
        User updated = usersDAO.setUserName(user.getId(), NEW_NAME);
        assertNotNull(updated);
        assertEquals(user.getId(), updated.getId());
        assertEquals(LOGIN, updated.getLogin());
        assertEquals(PASSWORD, updated.getPassword());
        assertEquals(NEW_NAME, updated.getName());
    }
}