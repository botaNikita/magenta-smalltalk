package ru.magentasmalltalk.web.viewmodels;

import javax.validation.constraints.*;
import java.util.Date;

public class SeminarViewModel {

    private int id;

    @FutureOrPresent
    private Date date;

    @Size(max = 100, message = "Length of a topic field should be maximum 100 chars.")
    @Pattern(regexp = "[a-zA-Z-_.,!?:;'\"0-9]*", message = "Topic should consist of latin letters, digits and symbols -_.,!?:;'\" .")
    private String topic;

    @Size(max = 1000, message = "Length of a topic field should be maximum 1000 chars.")
    private String description;

    @Size(max = 50, message = "Length of a topic field should be maximum 50 chars.")
    private String auditory;

    @Positive
    @Max(9999)
    private int placesNumber;

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

    public int getPlacesNumber() {
        return placesNumber;
    }

    public void setPlacesNumber(int placesNumber) {
        this.placesNumber = placesNumber;
    }
}
