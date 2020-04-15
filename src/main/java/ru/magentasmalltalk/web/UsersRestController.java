package ru.magentasmalltalk.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.magentasmalltalk.db.UsersRepository;
import ru.magentasmalltalk.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsersRestController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/api/users")
    public List<User> getUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        for (User user : usersRepository.findAll()) {
            allUsers.add(user);
        }
        return allUsers;
    }

    @GetMapping("/api/users-paged")
    public List<User> getSortedUsers(String loginTemplate, int pageNumber) {
        Page<User> page = usersRepository.findUsersByLoginIsLike(loginTemplate, PageRequest.of(pageNumber - 1, 5));
        return page.get().collect(Collectors.toList());
    }
}
