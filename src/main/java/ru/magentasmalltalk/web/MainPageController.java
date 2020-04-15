package ru.magentasmalltalk.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String mainPage(Principal principal, ModelMap model) {
        model.addAttribute("username", principal.getName());
        return "index";
    }
}
