package com.htbinh.backend.Model;

public class NotificationModel {
    String from, toClasses, date;
    String details;

    public NotificationModel(String from, String toClasses, String date, String details) {
        this.from = from;
        this.toClasses = toClasses;
        this.date = date;
        this.details = details;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getToClasses() {
        return toClasses;
    }

    public void setToClasses(String toClasses) {
        this.toClasses = toClasses;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
