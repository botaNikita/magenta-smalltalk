package ru.magentasmalltalk.db;

import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import ru.magentasmalltalk.model.Reservation;
import ru.magentasmalltalk.model.ReservationStatus;
import ru.magentasmalltalk.model.Seminar;
import ru.magentasmalltalk.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class ReservationsDAO extends BaseDAO<Reservation> {

    @Autowired
    public ReservationsDAO(EntityManager manager) {
        super(manager);
    }

    public Reservation createReservation(Seminar seminar, User user) {
        Reservation reservation = new Reservation();
        reservation.setSeminar(seminar);
        reservation.setUser(user);
        reservation.setStatus(ReservationStatus.CREATED);
        return persist(reservation);
    }

    public void removeReservation(int id) {
        Reservation reservation = findReservationById(id);
        reservation.setStatus(ReservationStatus.REMOVED);
        persist(reservation);
    }

    @Nullable
    public Reservation findReservationById(int id) {
        try {
            return manager.createQuery("from Reservation r where r.id = :id", Reservation.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
