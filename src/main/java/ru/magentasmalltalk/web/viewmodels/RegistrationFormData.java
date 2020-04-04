package ru.magentasmalltalk.web.viewmodels;

import ru.magentasmalltalk.model.UserRoles;

import java.util.List;

public class RegistrationFormData {
    private List<UserRoles> userRoles;

    public List<UserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoles> userRoles) {
        this.userRoles = userRoles;
    }
}
