package ru.magentasmalltalk.db;

import com.sun.istack.Nullable;
import ru.magentasmalltalk.model.Invitation;
import ru.magentasmalltalk.model.Message;
import ru.magentasmalltalk.model.Seminar;
import ru.magentasmalltalk.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

public class InvitationsDAO extends BaseDAO<Invitation> {
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

    public List<Invitation> findInvitationsByUser(User user) {
        Objects.requireNonNull(user, "findInvitationsByUser(null)");
        return manager.createQuery("from Invitation i join i.users u where u.id = :id", Invitation.class)
                .setParameter("id", user.getId())
                .getResultList();
    }

    public List<Invitation> findInvitationsBySeminar(Seminar seminar) {
        Objects.requireNonNull(seminar, "findInvitationsByUser(null)");
        return manager.createQuery("from Invitation i join i.seminar s where s.id = :id", Invitation.class)
                .setParameter("id", seminar.getId())
                .getResultList();
    }
}
