package ru.magentasmalltalk.db;

import com.sun.istack.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.magentasmalltalk.model.Invitation;
import ru.magentasmalltalk.model.Seminar;
import ru.magentasmalltalk.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class InvitationsDAO {

    @PersistenceContext
    protected EntityManager manager;

    @Transactional
    public Invitation sendInvitation(Seminar seminar, String text, List<User> users) {
        Invitation invitation = new Invitation();
        invitation.setSeminar(seminar);
        invitation.setText(text);
        invitation.setUsers(users);

        manager.persist(invitation);

        return invitation;
    }

    @Nullable
    public List<Invitation> findInvitationsByUserId(int id) {
        return manager.createQuery("select i from Invitation i join i.users u where u.id = :id", Invitation.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Nullable
    public List<Invitation> findInvitationsBySeminarId(int id) {
        return manager.createQuery("select i from Invitation i join i.seminar s where s.id = :id", Invitation.class)
                .setParameter("id", id)
                .getResultList();
    }
}
