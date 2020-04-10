package ru.magentasmalltalk.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.magentasmalltalk.model.Seminar;
import ru.magentasmalltalk.TestConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SeminarsDAOTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private SeminarsDAO seminarsDAO;

    @Test
    @Transactional
    public void createSeminar() {
        // const
        Date DATE = new Date(2020, Calendar.MAY, 15);
        String TOPIC = "topic1";
        String DESCRIPTION = "description1";
        String AUDITORY = "auditory1";
        int PLACES_NUMBER = 50;

        // check properties of returned object
        Seminar seminar = seminarsDAO.createSeminar(DATE, TOPIC, DESCRIPTION, AUDITORY, PLACES_NUMBER);
        assertNotNull(seminar);
        assertEquals(DATE, seminar.getDate());
        assertEquals(TOPIC, seminar.getTopic());
        assertEquals(DESCRIPTION, seminar.getDescription());
        assertEquals(AUDITORY, seminar.getAuditory());
        assertEquals(PLACES_NUMBER, seminar.getPlacesNumber());
        assertNotEquals(0, seminar.getId());

        // check that the entity is saved in DB
        Seminar found = manager.find(Seminar.class, seminar.getId());
        assertNotNull(found);

//        manager.refresh(found);
    }

    @Test
    @Transactional
    public void updateSeminar() {
        // const
        Date DATE = new Date(2020, Calendar.JUNE, 10);
        String TOPIC = "topic2";
        String DESCRIPTION = "description2";
        String AUDITORY = "place2";
        int PLACES_NUMBER = 20;

        Date NEW_DATE = new Date(2020, Calendar.JULY, 15);
        String NEW_TOPIC = "topic3";
        String NEW_DESCRIPTION = "description3";
        String NEW_AUDITORY = "place3";
        int NEW_PLACES_NUMBER = 35;

        // data preparation
        Seminar seminar = new Seminar();
        seminar.setDate(DATE);
        seminar.setTopic(TOPIC);
        seminar.setDescription(DESCRIPTION);
        seminar.setAuditory(AUDITORY);
        seminar.setPlacesNumber(PLACES_NUMBER);
        manager.persist(seminar);

        seminar.setDate(NEW_DATE);
        seminar.setTopic(NEW_TOPIC);
        seminar.setDescription(NEW_DESCRIPTION);
        seminar.setAuditory(NEW_AUDITORY);
        seminar.setPlacesNumber(NEW_PLACES_NUMBER);

        // check
        Seminar updated = seminarsDAO.updateSeminar(seminar);
        assertNotNull(updated);
        assertEquals(seminar.getId(), updated.getId());
        assertEquals(NEW_DATE, updated.getDate());
        assertEquals(NEW_TOPIC, updated.getTopic());
        assertEquals(NEW_DESCRIPTION, updated.getDescription());
        assertEquals(NEW_AUDITORY, updated.getAuditory());
        assertEquals(NEW_PLACES_NUMBER, updated.getPlacesNumber());
    }

    @Test
    @Transactional
    public void findSeminarById() {
        // const
        Date DATE = new Date(2020, Calendar.DECEMBER, 30);
        String TOPIC = "topic4";
        String DESCRIPTION = "description4";
        String AUDITORY = "place4";
        int PLACES_NUMBER = 15;

        // data preparation
        Seminar seminar = new Seminar();
        seminar.setDate(DATE);
        seminar.setTopic(TOPIC);
        seminar.setDescription(DESCRIPTION);
        seminar.setAuditory(AUDITORY);
        seminar.setPlacesNumber(PLACES_NUMBER);
        manager.persist(seminar);

        // check
        Seminar found = seminarsDAO.findSeminarById(seminar.getId());
        assertNotNull(found);
        assertEquals(seminar.getId(), found.getId());
        assertEquals(DATE, found.getDate());
        assertEquals(TOPIC, found.getTopic());
        assertEquals(DESCRIPTION, found.getDescription());
        assertEquals(AUDITORY, found.getAuditory());
        assertEquals(PLACES_NUMBER, found.getPlacesNumber());
    }
}