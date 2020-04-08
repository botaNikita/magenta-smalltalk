package ru.magentasmalltalk.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.magentasmalltalk.model.Reservation;
import ru.magentasmalltalk.model.ReservationStatus;
import ru.magentasmalltalk.model.Seminar;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.TestConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationsDAOTest {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private ReservationsDAO reservationsDAO;

    @Test
    @Transactional
    public void createReservation() {
        // data preparation
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("testpassword");
        user.setName("testname");
        Seminar seminar = new Seminar();
        seminar.setDate(new Date(2020, Calendar.JULY, 12));
        seminar.setTopic("testtopic");
        seminar.setAuditory("testauditory");
        seminar.setPlacesNumber(15);
        manager.persist(user);
        manager.persist(seminar);

        // check properties of returned object
        Reservation reservation = reservationsDAO.createReservation(seminar, user);
        assertNotNull(reservation);
        assertNotEquals(0, reservation.getId());
        assertNotNull(reservation.getSeminar());
        assertEquals(seminar.getId(), reservation.getSeminar().getId());
        assertEquals(seminar.getTopic(), reservation.getSeminar().getTopic());
        assertNotNull(reservation.getUser());
        assertEquals(user.getId(), reservation.getUser().getId());
        assertEquals(user.getLogin(), reservation.getUser().getLogin());

        // check that the entity is saved in DB
        Reservation found = manager.find(Reservation.class, reservation.getId());
        assertNotNull(found);
    }

    @Test
    @Transactional
    public void removeReservation() {
        // const
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("testpassword");
        user.setName("testname");
        Seminar seminar = new Seminar();
        seminar.setDate(new Date(2020, Calendar.JULY, 12));
        seminar.setTopic("testtopic");
        seminar.setAuditory("testauditory");
        seminar.setPlacesNumber(15);
        Reservation reservation = new Reservation();
        reservation.setSeminar(seminar);
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.CREATED);
        manager.persist(user);
        manager.persist(seminar);
        manager.persist(reservation);

        // action
        reservationsDAO.removeReservation(reservation.getId());

        // check
        Reservation removed = manager.find(Reservation.class, reservation.getId());
        assertNotNull(removed);
        assertNotNull(removed.getUser());
        assertEquals(user.getId(), removed.getUser().getId());
        assertNotNull(removed.getSeminar());
        assertEquals(seminar.getId(), removed.getSeminar().getId());
        assertEquals(ReservationStatus.REMOVED, removed.getStatus());
    }

    @Test
    @Transactional
    public void findReservationById() {
        // data preparation
        User user = new User();
        user.setLogin("testlogin");
        user.setPassword("testpassword");
        user.setName("testname");
        Seminar seminar = new Seminar();
        seminar.setDate(new Date(2020, Calendar.JULY, 12));
        seminar.setTopic("testtopic");
        seminar.setAuditory("testauditory");
        seminar.setPlacesNumber(15);
        Reservation reservation = new Reservation();
        reservation.setSeminar(seminar);
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.CREATED);
        manager.persist(user);
        manager.persist(seminar);
        manager.persist(reservation);

        // check
        Reservation found = reservationsDAO.findReservationById(reservation.getId());
        assertNotNull(found);
        assertNotNull(found.getUser());
        assertEquals(user.getId(), found.getUser().getId());
        assertNotNull(found.getSeminar());
        assertEquals(seminar.getId(), found.getSeminar().getId());
        assertEquals(ReservationStatus.CREATED, found.getStatus());
    }
}