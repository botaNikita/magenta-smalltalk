package ru.magentasmalltalk.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Message {
    @Id
    @GeneratedValue
    private int id;

    @Column
    @Size(max = 1000, message = "Message length shouldn't exceed 1000 chars")
    private String text;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToMany
    private List<User> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
