package com.LMS.test;

public class Book {
    private int bookId;
    private String bookName;
    private String author;
    private String publisher;
    private String category;

    public Book() {
        super();
    }

    public Book(int bookId, String bookName, String author, String publisher, String category) {
        super();
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "\nBook ID: " + bookId + "\n" +"Book Name: " + bookName + "\n" +"Author: " + author + "\n" +"Publisher: " + publisher + "\n" +"Category: " + category;
    }

}