package com.example.database;

public class Book {

    private String id;
    private String title;
    private String author;
    private String publisher;
    private boolean isAvail;

    public Book() {
    }

    public Book(String id, String title, String author, String publisher, boolean isAvail) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isAvail = isAvail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public boolean getIsAvail() {
        return isAvail;
    }

    public void setIsAvail(boolean isAvail) {
        this.isAvail = isAvail;
    }

    @Override
    public String toString() {
        return "Book [author=" + author + ", id=" + id + ", publisher=" + publisher + ", title=" + title + ", isAvail="
                + isAvail + "]";
    }

}
