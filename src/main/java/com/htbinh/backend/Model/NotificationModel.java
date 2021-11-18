package com.htbinh.backend.Model;

public class NotificationModel {
    String from, toClasses, date, detailsLink;

    public NotificationModel(String from, String toClasses, String date, String detailsLink) {
        this.from = from;
        this.toClasses = toClasses;
        this.date = date;
        this.detailsLink = detailsLink;
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

    public String getDetailsLink() {
        return detailsLink;
    }

    public void setDetailsLink(String detailsLink) {
        this.detailsLink = detailsLink;
    }
}
