package ru.magentasmalltalk.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.magentasmalltalk.model.Seminar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class SeminarsDAOTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private SeminarsDAO seminarsDAO;

    @Before
    public void setUp() {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
        seminarsDAO = new SeminarsDAO(manager);
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
    public void createSeminar() {
        // const
        Date DATE = new Date(2020, Calendar.MAY, 15);
        String TOPIC = "topic1";
        String DESCRIPTION = "description1";
        String PLACE = "place1";

        // check properties of returned object
        Seminar seminar = seminarsDAO.createSeminar(DATE, TOPIC, DESCRIPTION, PLACE);
        assertNotNull(seminar);
        assertEquals(DATE, seminar.getDate());
        assertEquals(TOPIC, seminar.getTopic());
        assertEquals(DESCRIPTION, seminar.getDescription());
        assertEquals(PLACE, seminar.getPlace());
        assertNotEquals(0, seminar.getId());

        // check that the entity is saved in DB
        Seminar found = manager.find(Seminar.class, seminar.getId());
        assertNotNull(found);
        manager.refresh(found);
    }

    @Test
    public void updateSeminar() {
        // const
        Date DATE = new Date(2020, Calendar.JUNE, 10);
        String TOPIC = "topic2";
        String DESCRIPTION = "description2";
        String PLACE = "place2";

        Date NEW_DATE = new Date(2020, Calendar.JULY, 15);
        String NEW_TOPIC = "topic3";
        String NEW_DESCRIPTION = "description3";
        String NEW_PLACE = "place3";

        // data preparation
        Seminar seminar = new Seminar();
        seminar.setDate(DATE);
        seminar.setTopic(TOPIC);
        seminar.setDescription(DESCRIPTION);
        seminar.setPlace(PLACE);
        manager.getTransaction().begin();
        manager.persist(seminar);
        manager.getTransaction().commit();

        seminar.setDate(NEW_DATE);
        seminar.setTopic(NEW_TOPIC);
        seminar.setDescription(NEW_DESCRIPTION);
        seminar.setPlace(NEW_PLACE);

        // check
        Seminar updated = seminarsDAO.updateSeminar(seminar);
        assertNotNull(updated);
        assertEquals(seminar.getId(), updated.getId());
        assertEquals(NEW_DATE, updated.getDate());
        assertEquals(NEW_TOPIC, updated.getTopic());
        assertEquals(NEW_DESCRIPTION, updated.getDescription());
        assertEquals(NEW_PLACE, updated.getPlace());
    }

    @Test
    public void findSeminarById() {
        // const
        Date DATE = new Date(2020, Calendar.DECEMBER, 30);
        String TOPIC = "topic4";
        String DESCRIPTION = "description4";
        String PLACE = "place4";

        // data preparation
        Seminar seminar = new Seminar();
        seminar.setDate(DATE);
        seminar.setTopic(TOPIC);
        seminar.setDescription(DESCRIPTION);
        seminar.setPlace(PLACE);
        manager.getTransaction().begin();
        manager.persist(seminar);
        manager.getTransaction().commit();

        // check
        Seminar found = seminarsDAO.findSeminarById(seminar.getId());
        assertNotNull(found);
        assertEquals(seminar.getId(), found.getId());
        assertEquals(DATE, found.getDate());
        assertEquals(TOPIC, found.getTopic());
        assertEquals(DESCRIPTION, found.getDescription());
        assertEquals(PLACE, found.getPlace());
    }
}