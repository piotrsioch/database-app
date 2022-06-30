package sample.LoggedIn.Calendar;

import java.util.Date;

public class CalendarEvent {
    private int id;
    private String importance;
    private String type;
    private String title;
    private String details;
    private Date date;
    private int hour;
    private int minutes;

    public CalendarEvent(String importance, String type, String title, String details, Date date, int hour, int minutes) {
        this.importance = importance;
        this.type = type;
        this.title = title;
        this.details = details;
        this.date = date;
        this.hour = hour;
        this.minutes = minutes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
