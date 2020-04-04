package ru.magentasmalltalk.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.magentasmalltalk.db.UsersDAO;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.model.UserRoles;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsersDAO usersDAO;

    @GetMapping("/login")
    public String loginPage(HttpSession session,
                            @RequestParam(required = false) String login) {
        if (session.getAttribute("userId") != null) {
            return "redirect:/";
        }

        return "users/login";
    }

    @PostMapping("/login")
    public String processLoginForm(HttpSession session,
                                   @RequestParam String login,
                                   @RequestParam String password) {
        if (session.getAttribute("userId") != null) {
            return "redirect:/";
        }

        User user = usersDAO.findUserByLogin(login);

        if (user == null || !password.equals(user.getPassword())) {
            return "redirect:users/login";
        }

        session.setAttribute("userName", user.getName());
        session.setAttribute("userId", user.getId());
        session.setAttribute("isAdmin", user.getRole() == UserRoles.ADMIN);

        return "redirect:/";
    }
}
