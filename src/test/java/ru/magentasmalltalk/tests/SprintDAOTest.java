package ru.magentasmalltalk.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.magentasmalltalk.db.UsersDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SprintDAOTest {
    @Autowired
    public UsersDAO usersDAO;

    @Test
    public void smokeTest() {
        usersDAO.createUser("test", "test");
    }

    @Test
    public void smokeTest2() {
        usersDAO.createUser("test2", "test2");
    }
}
