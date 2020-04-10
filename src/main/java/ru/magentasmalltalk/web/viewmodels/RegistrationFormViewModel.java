package ru.magentasmalltalk.web.viewmodels;

import ru.magentasmalltalk.model.UserRoles;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class RegistrationFormViewModel {

    @Size(min = 4, max = 50, message = "Login length should be between 4 and 50.")
    @Pattern(regexp = "[a-zA-Z-_.0-9]*", message = "Login should consist of latin letters, digits, underscores, hyphens and dots.")
    private String login;

    @Size(min = 6, message = "Password length should be minimum 6.")
    @Pattern(regexp = "[a-zA-Z-_.0-9]*", message = "Password should consist of latin letters, digits, underscores, hyphens and dots.")
    private String password;

    @Size(min = 4, message = "Length of a name should be minimum 4 chars.")
    @Pattern(regexp = "[a-zA-Z-_.0-9]*", message = "Name should consist of latin letters, digits, underscores, hyphens and dots.")
    private String name;

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

    public UserRoles getSelectedUserRole() {
        return selectedUserRole;
    }

    public void setSelectedUserRole(UserRoles selectedUserRole) {
        this.selectedUserRole = selectedUserRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationFormViewModel that = (RegistrationFormViewModel) o;
        return Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getName(), that.getName()) &&
                getSelectedUserRole() == that.getSelectedUserRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword(), getName(), getSelectedUserRole());
    }
}
