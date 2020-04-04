package ru.magentasmalltalk.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.magentasmalltalk.db.UsersDAO;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.model.UserRoles;

@Component
public class StartupListener {

    @Autowired
    private UsersDAO usersDAO;

    @EventListener
    public void applicationStarted(ContextRefreshedEvent event) {
        User user = usersDAO.findUserByLogin("admin");
        if (user == null) {
            usersDAO.createUser("admin", "admin", "Admin", UserRoles.ADMIN);
        }
    }
}
