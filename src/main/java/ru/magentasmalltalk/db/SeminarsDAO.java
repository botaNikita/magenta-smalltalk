package ru.magentasmalltalk.db;

import com.sun.istack.Nullable;
import ru.magentasmalltalk.model.Seminar;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Date;
import java.util.Objects;

public class SeminarsDAO extends BaseDAO<Seminar> {

    public SeminarsDAO(EntityManager manager) {
        super(manager);
    }

    public Seminar createSeminar(Date date, String topic, String description, String place) {
        Seminar seminar = new Seminar();
        seminar.setDate(date);
        seminar.setTopic(topic);
        seminar.setDescription(description);
        seminar.setPlace(place);
        return persist(seminar);
    }

    public Seminar updateSeminar(Seminar newSeminar) {
        Objects.requireNonNull(newSeminar, "Seminar for update can't be null");

        int id = newSeminar.getId();
        Seminar seminar = findSeminarById(id);
        Objects.requireNonNull(seminar, "Seminar with id=\"" + id + "\" wasn't found");

        seminar.setDate(newSeminar.getDate());
        seminar.setTopic(newSeminar.getTopic());
        seminar.setDescription(newSeminar.getDescription());
        seminar.setPlace(newSeminar.getPlace());

        return persist(seminar);
    }

    @Nullable
    public Seminar findSeminarById(int id) {
        try {
            return manager.createQuery("from Seminar s where s.id = :id", Seminar.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
