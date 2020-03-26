package ru.magentasmalltalk.db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.magentasmalltalk.model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class ReservationsDAOTest {
    private EntityManagerFactory factory;
    private EntityManager manager;
    private ReservationsDAO reservationsDAO;

    @Before
    public void setUp() {
        factory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        manager = factory.createEntityManager();
        reservationsDAO = new ReservationsDAO(manager);
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
    public void createReservation() {
        // data preparation
        User user = new User();
        user.setLogin("test login");
        user.setPassword("test password");
        Seminar seminar = new Seminar();
        seminar.setTopic("test topic");
        manager.getTransaction().begin();
        manager.persist(user);
        manager.persist(seminar);
        manager.getTransaction().commit();

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
        manager.refresh(found);
    }

    @Test
    public void removeReservation() {
        // const
        User user = new User();
        user.setLogin("test login 2");
        user.setPassword("test password 2");
        Seminar seminar = new Seminar();
        seminar.setTopic("test topic 2");
        Reservation reservation = new Reservation();
        reservation.setSeminar(seminar);
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.CREATED);
        manager.getTransaction().begin();
        manager.persist(user);
        manager.persist(seminar);
        manager.persist(reservation);
        manager.getTransaction().commit();

        // action
        reservationsDAO.removeReservation(reservation.getId());

        // check
        Reservation removed = manager.find(Reservation.class, reservation.getId());
        assertNotNull(removed);
        manager.refresh(removed);
        assertNotNull(removed.getUser());
        assertEquals(user.getId(), removed.getUser().getId());
        assertNotNull(removed.getSeminar());
        assertEquals(seminar.getId(), removed.getSeminar().getId());
        assertEquals(ReservationStatus.REMOVED, removed.getStatus());
    }

    @Test
    public void findReservationById() {
        // data preparation
        User user = new User();
        user.setLogin("test login 3");
        user.setPassword("test password 3");
        Seminar seminar = new Seminar();
        seminar.setTopic("test topic 3");
        Reservation reservation = new Reservation();
        reservation.setSeminar(seminar);
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.CREATED);
        manager.getTransaction().begin();
        manager.persist(user);
        manager.persist(seminar);
        manager.persist(reservation);
        manager.getTransaction().commit();

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