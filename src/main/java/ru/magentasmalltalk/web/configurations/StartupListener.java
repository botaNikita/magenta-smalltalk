package ru.magentasmalltalk.web.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.magentasmalltalk.db.UsersDAO;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.model.UserRoles;

@Component
public class StartupListener {

    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private PasswordEncoder encoder;

    @EventListener
    @Transactional
    public void applicationStarted(ContextRefreshedEvent event) {
        User user = usersDAO.findUserByLogin("admin");
        if (user == null) {
            usersDAO.createUser("admin", encoder.encode("admin123"), "Admin", UserRoles.ADMIN);
        }
    }
}
