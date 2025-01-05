package com.example.finalapp;

public class BookInfoTwo {
    String title, subtitle, publisher, publishedDate, description, thumbnail, author, category, pageCount;

    public BookInfoTwo(String title, String subtitle, String publisher, String publishedDate, String description, String thumbnail, String author, String category, String pageCount) {
        this.title = title;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.thumbnail = thumbnail;
        this.author = author;
        this.category = category;
        this.pageCount = pageCount;

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

    public String getThumbnail() {

        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {

        this.thumbnail = thumbnail;
    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {

        this.author = author;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {

        this.category = category;
    }

    public String getPageCount() {

        return pageCount;
    }

    public void setPageCount(String pageCount) {

        this.pageCount = pageCount;
    }
}
