package ru.magentasmalltalk.db;

import org.springframework.beans.factory.annotation.Autowired;
import ru.magentasmalltalk.model.Invitation;
import ru.magentasmalltalk.model.Seminar;
import ru.magentasmalltalk.model.User;

import javax.persistence.EntityManager;
import java.util.List;

public class InvitationsDAO extends BaseDAO<Invitation> {

    @Autowired
    protected InvitationsDAO(EntityManager manager) {
        super(manager);
    }

    public Invitation sendInvitation(Seminar seminar, String text, List<User> users) {
        Invitation invitation = new Invitation();
        invitation.setSeminar(seminar);
        invitation.setText(text);
        invitation.setUsers(users);
        return persist(invitation);
    }

    public List<Invitation> findInvitationsByUserId(int id) {
        return manager.createQuery("select i from Invitation i join i.users u where u.id = :id", Invitation.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Invitation> findInvitationsBySeminarId(int id) {
        return manager.createQuery("select i from Invitation i join i.seminar s where s.id = :id", Invitation.class)
                .setParameter("id", id)
                .getResultList();
    }
}
