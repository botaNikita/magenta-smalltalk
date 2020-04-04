package ru.magentasmalltalk.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String processLogout(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            session.removeAttribute("userName");
            session.removeAttribute("userId");
            session.removeAttribute("isAdmin");
        }
        return "redirect:/";
    }
}
