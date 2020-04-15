package ru.magentasmalltalk.db;

import com.sun.istack.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.magentasmalltalk.model.Seminar;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
public class SeminarsDAO {

    @PersistenceContext
    protected EntityManager manager;

    @Transactional
    public Seminar createSeminar(Date date, String topic, String description, String auditory, int placesNumber) {
        Seminar seminar = new Seminar();
        seminar.setDate(date);
        seminar.setTopic(topic);
        seminar.setDescription(description);
        seminar.setAuditory(auditory);
        seminar.setPlacesNumber(placesNumber);

        manager.persist(seminar);

        return seminar;
    }

    @Transactional
    public Seminar updateSeminar(Seminar newSeminar) {
        Objects.requireNonNull(newSeminar, "Seminar for update can't be null");

        int id = newSeminar.getId();
        Seminar seminar = findSeminarById(id);
        Objects.requireNonNull(seminar, "Seminar with id=\"" + id + "\" wasn't found");

        seminar.setDate(newSeminar.getDate());
        seminar.setTopic(newSeminar.getTopic());
        seminar.setDescription(newSeminar.getDescription());
        seminar.setAuditory(newSeminar.getAuditory());
        seminar.setPlacesNumber(newSeminar.getPlacesNumber());

        manager.persist(seminar);

        return seminar;
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

    @Nullable
    public List<Seminar> getAllSeminars() {
        try {
            return manager.createQuery("from Seminar s order by s.date", Seminar.class)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
