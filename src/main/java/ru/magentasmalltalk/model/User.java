package ru.magentasmalltalk.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    @Size(min = 4, max = 50, message = "Login length should be between 4 and 50.")
    @Pattern(regexp = "[a-zA-Z-_.0-9]*", message = "Login should consist of latin letters, digits, underscores, hyphens and dots.")
    private String login;

    @Column(nullable = false)
    @Size(min = 6, message = "Password length should be minimum 6.")
    @Pattern(regexp = "[a-zA-Z-_.0-9]*", message = "Password should consist of latin letters, digits, underscores, hyphens and dots.")
    private String password;

    @Column(nullable = false)
    @Size(min = 4, message = "Length of a name should be minimum 4 chars.")
    @Pattern(regexp = "[a-zA-Z-_.0-9]*", message = "Name should consist of latin letters, digits, underscores, hyphens and dots.")
    private String name;

    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @OneToMany
    private List<Reservation> reservations;

    public User() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}