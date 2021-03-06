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
import org.springframework.web.bind.annotation.PostMapping;
import ru.magentasmalltalk.db.UsersDAO;
import ru.magentasmalltalk.model.User;
import ru.magentasmalltalk.model.UserRoles;
import ru.magentasmalltalk.web.viewmodels.RegistrationFormData;
import ru.magentasmalltalk.web.viewmodels.RegistrationFormViewModel;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@Controller
public class RegistrationController {

    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private PasswordEncoder encoder;

    @ModelAttribute("form")
    public RegistrationFormViewModel createRegistrationFormViewModel() {
        RegistrationFormViewModel registrationFormViewModel = new RegistrationFormViewModel();
        registrationFormViewModel.setLogin("");
        registrationFormViewModel.setPassword("");
        registrationFormViewModel.setName("");
        registrationFormViewModel.setSelectedUserRole(UserRoles.USER);
        return registrationFormViewModel;
    }

    public RegistrationFormData createData() {
        List<UserRoles> userRoles = new LinkedList<>();
        userRoles.add(UserRoles.ADMIN);
        userRoles.add(UserRoles.USER);
        RegistrationFormData data = new RegistrationFormData();
        data.setUserRoles(userRoles);
        return data;
    }

    @GetMapping(path = "/register")
    public String getRegistrationForm(@ModelAttribute("form") RegistrationFormViewModel form,
                                      ModelMap modelMap,
                                      HttpSession session,
                                      Principal principal) {
        if (principal != null) {
            return "redirect:/";
        }
        modelMap.addAttribute("data", createData());
        return "users/register";
    }

    @PostMapping(path = "/register")
    public String processRegistrationForm(
            @Validated
            @ModelAttribute("form") RegistrationFormViewModel form,
            BindingResult validationResult,
            ModelMap modelMap,
            HttpSession session,
            Principal principal) {

        if (principal != null) {
            return "redirect:/";
        }

        modelMap.addAttribute("data", createData());

        if (validationResult.hasErrors() || form.getLogin().isEmpty() || form.getPassword().isEmpty() || form.getName().isEmpty()) {
            return "users/register";
        }

        User user = null;
        String encodedPassword = encoder.encode(form.getPassword());
        try {
            user = usersDAO.createUser(form.getLogin(), encodedPassword, form.getName(), form.getSelectedUserRole());
        } catch (Throwable cause) {
            validationResult.addError(new FieldError("form", "login", "User with login " + form.getLogin() + " found"));
        }

        if (user == null) {
            validationResult.addError(new FieldError("form", "login", "Can not create user"));
        }

        if (validationResult.hasErrors()) {
            return "users/register";
        }

        session.setAttribute("userName", user.getName());
        session.setAttribute("userId", user.getId());
        session.setAttribute("isAdmin", user.getRole() == UserRoles.ADMIN);
        return "redirect:/login";
    }
}


