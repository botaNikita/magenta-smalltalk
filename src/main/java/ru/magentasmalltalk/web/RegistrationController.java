package ru.magentasmalltalk.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.magentasmalltalk.db.UsersDAO;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.model.UserRoles;
import ru.magentasmalltalk.web.viewmodels.RegistrationFormViewModel;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Controller
public class RegistrationController {

    @Autowired
    private UsersDAO usersDAO;

    @GetMapping(path = "/register")
    public String getRegistrationForm(ModelMap modelMap, HttpSession session) {
        if (session.getAttribute("userId") != null) {
            return "redirect:/";
        }

        RegistrationFormViewModel registrationFormViewModel = new RegistrationFormViewModel();
        registrationFormViewModel.setLogin("");
        registrationFormViewModel.setPassword("");
        registrationFormViewModel.setName("");
        List<UserRoles> userRoles = new LinkedList<>();
        userRoles.add(UserRoles.ADMIN);
        userRoles.add(UserRoles.USER);
        registrationFormViewModel.setUserRoles(userRoles);
        registrationFormViewModel.setSelectedUserRole(UserRoles.USER);

        modelMap.addAttribute("form", registrationFormViewModel);

        return "users/register";
    }

    @PostMapping(path = "/register")
    public String processRegistrationForm(@RequestParam String login,
                                          @RequestParam String password,
                                          @RequestParam String name,
                                          @RequestParam UserRoles role,
                                          HttpSession session) {
        if (session.getAttribute("userId") != null) {
            return "redirect:/";
        }

        if (login != null && password != null) {

            User user = usersDAO.createUser(login, password, name, role);

            if (user != null) {
                return "redirect:/";
            }
            session.setAttribute("userName", user.getName());
            session.setAttribute("userId", user.getId());
            session.setAttribute("isAdmin", user.getRole() == UserRoles.ADMIN);
            return "redirect:/";
        }

        return "redirect:/register";
    }
}


