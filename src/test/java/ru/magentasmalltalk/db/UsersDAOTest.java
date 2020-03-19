package ru.magentasmalltalk.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.magentasmalltalk.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class UsersDAOTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private UsersDAO usersDAO;

    @Before
    public void setUp() throws Exception {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
        usersDAO = new UsersDAO(manager);
    }

    @After
    public void tearDown() throws Exception {
        if (manager != null) {
            manager.close();
        }
        if (factory != null) {
            factory.close();
        }
    }

    @Test
    public void createUser() {
        // const
        String LOGIN = "login1";

        // check properties of returned object
        User user = usersDAO.createUser(LOGIN);
        assertNotNull(user);
        assertEquals(LOGIN, user.getLogin());
        assertNotEquals(0, user.getId());

        // check that the entity is saved in DB
        User found = manager.find(User.class, user.getId());
        assertNotNull(found);
        manager.refresh(found);
    }

    @Test
    public void findUserByLogin() {
        // const
        String LOGIN = "login2";

        // data preparation
        User user = new User(LOGIN);
        manager.getTransaction().begin();
        manager.persist(user);
        manager.getTransaction().commit();

        // check
        User found = usersDAO.findUserByLogin(LOGIN);
        assertNotNull(found);
        assertEquals(LOGIN, found.getLogin());
    }
}