package com.example.finalapp;

public class BookInfo {

    String title;
    String subtitle;
    String authors;
    String publisher;
    String publishedDate;
    String description;
    String pageCount;
    String thumbnail;
    String category;

    public BookInfo(String title, String subtitle, String authors, String publisher, String publishedDate, String description, String pageCount, String thumbnail, String category) {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
        this.thumbnail = thumbnail;
        this.category = category;

    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getSubtitle() {

        return subtitle;
    }

    public void setSubtitle(String subtitle) {

        this.subtitle = subtitle;
    }

    public String getAuthors() {

        return authors;
    }

    public void setAuthors(String authors) {

        this.authors = authors;
    }

    public String getPublisher() {

        return publisher;
    }

    public void setPublisher(String publisher) {

        this.publisher = publisher;
    }

    public String getPublishedDate() {

        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {

        this.publishedDate = publishedDate;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getPageCount() {

        return pageCount;
    }

    public void setPageCount(String pageCount) {

        this.pageCount = pageCount;
    }

    public String getThumbnail() {

        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {

        this.thumbnail = thumbnail;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {

        this.category = category;
    }
}
