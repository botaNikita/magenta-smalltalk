package ru.magentasmalltalk.web.viewmodels;

import ru.magentasmalltalk.model.UserRoles;

import java.util.List;
import java.util.Objects;

public class RegistrationFormViewModel {
    private String login;
    private String password;
    private String name;
    private List<UserRoles> userRoles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationFormViewModel that = (RegistrationFormViewModel) o;
        return Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getUserRoles(), that.getUserRoles()) &&
                getSelectedUserRole() == that.getSelectedUserRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword(), getName(), getUserRoles(), getSelectedUserRole());
    }

    private UserRoles selectedUserRole;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoles> userRoles) {
        this.userRoles = userRoles;
    }

    public UserRoles getSelectedUserRole() {
        return selectedUserRole;
    }

    public void setSelectedUserRole(UserRoles selectedUserRole) {
        this.selectedUserRole = selectedUserRole;
    }
}
