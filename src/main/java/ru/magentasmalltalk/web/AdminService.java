package ru.magentasmalltalk.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.magentasmalltalk.db.UsersRepository;

@Component
public class AdminService {
    @Autowired
    private UsersRepository usersRepository;

    @Transactional
    @Secured("ROLE_ADMIN")
    public void banLatestUsers() {
        usersRepository.findUsersWithBigIds(10).forEach(user -> user.setName("banned_" + user.getName()));
    }
}
