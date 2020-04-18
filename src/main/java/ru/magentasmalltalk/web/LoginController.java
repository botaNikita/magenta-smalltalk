package ru.magentasmalltalk.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.magentasmalltalk.db.UsersDAO;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.model.UserRoles;
import ru.magentasmalltalk.web.viewmodels.LoginFormViewModel;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class LoginController {

    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private PasswordEncoder encoder;

    @ModelAttribute("form")
    public LoginFormViewModel createLoginFormViewModel() {
        LoginFormViewModel loginFormViewModel = new LoginFormViewModel();
        loginFormViewModel.setLogin("");
        loginFormViewModel.setPassword("");
        return loginFormViewModel;
    }

    @GetMapping("/login")
    public String loginPage(@ModelAttribute("form") LoginFormViewModel form,
                            HttpSession session,
                            Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }

        return "users/login";
    }

    //@PostMapping("/login")
    public String processLoginForm(@Validated
                                   @ModelAttribute("form") LoginFormViewModel form,
                                   BindingResult validationResult,
                                   ModelMap modelMap,
                                   HttpSession session) {

        if (session.getAttribute("userId") != null) {
            return "redirect:/";
        }

        if (form.getLogin().isEmpty() || form.getPassword().isEmpty()) {
            return "users/login";
        }

        User user = null;
        try {
            user = usersDAO.findUserByLogin(form.getLogin());
        } catch (Throwable cause) {
            validationResult.addError(new FieldError("form", "login", "User with login " + form.getLogin() + " not found"));
        }

        if (user == null) {
            validationResult.addError(new FieldError("form", "login", "Can not find user"));
        } else if (!encoder.matches(form.getPassword(), user.getEncodedPassword())) {
            validationResult.addError(new FieldError("form", "password", "Incorrect password"));
        }

        if (validationResult.hasErrors()) {
            return "users/login";
        }

        session.setAttribute("userName", user.getName());
        session.setAttribute("userId", user.getId());
        session.setAttribute("isAdmin", user.getRole() == UserRoles.ADMIN);
        return "redirect:/";
    }
}
