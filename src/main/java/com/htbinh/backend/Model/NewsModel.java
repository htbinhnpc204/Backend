package com.htbinh.backend.Model;

public class NewsModel {
    private String title, description, date, imgLink, detailsLink;

    public NewsModel(String title, String description, String date, String imgLink, String detailsLink) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.imgLink = imgLink;
        this.detailsLink = detailsLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getDetailsLink() {
        return detailsLink;
    }

    public void setDetailsLink(String detailsLink) {
        this.detailsLink = detailsLink;
    }
}
