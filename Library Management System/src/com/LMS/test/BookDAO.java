package com.LMS.test;

import java.util.List;

public interface BookDAO {
    void addBook(Book b);
    void deleteBook(int id);
    Book searchBook(int id);
    List<Book> displayAllBooks();

}