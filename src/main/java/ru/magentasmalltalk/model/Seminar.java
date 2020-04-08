package ru.magentasmalltalk.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Seminar {
    @Id
    @GeneratedValue
    private int id;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @FutureOrPresent
    private Date date;

    @Column(nullable = false)
    @Size(max = 100, message = "Length of a topic field should be maximum 100 chars.")
    @Pattern(regexp = "[a-zA-Z-_.,!?:;'\"0-9]*", message = "Topic should consist of latin letters, digits and symbols -_.,!?:;'\" .")
    private String topic;

    @Column
    @Size(max = 1000, message = "Length of a topic field should be maximum 1000 chars.")
    private String description;

    @Column(nullable = false)
    @Size(max = 50, message = "Length of a topic field should be maximum 50 chars.")
    private String auditory;

    @Column
    @Positive
    @Max(9999)
    private int placesNumber;

    @OneToMany
    private List<Reservation> reservations;

    @OneToMany
    private List<Invitation> invitations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuditory() {
        return auditory;
    }

    public void setAuditory(String auditory) {
        this.auditory = auditory;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }

    public int getPlacesNumber() {
        return placesNumber;
    }

    public void setPlacesNumber(int placesNumber) {
        this.placesNumber = placesNumber;
    }
}
