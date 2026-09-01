package com.LMS.test;

import java.util.List;

public interface IssueDAO {	
    void issueBook(IssueBook i);
    void returnBook(int bookId);
    IssueBook searchIssuedBook(int bookId);
    List<IssueBook> displayIssuedBooks();

}