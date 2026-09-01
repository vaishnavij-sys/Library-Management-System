package com.LMS.test;

public class IssueBook {
    private int issueId;
    private int bookId;
    private int issuerId;
    private String issuerName;
    private String contact;

    public IssueBook() {
        super();
    }

    public IssueBook(int issueId, int bookId, int issuerId, String issuerName, String contact) {
        super();
        this.issueId = issueId;
        this.bookId = bookId;
        this.issuerId = issuerId;
        this.issuerName = issuerName;
        this.contact = contact;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(int issuerId) {
        this.issuerId = issuerId;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "\nIssue ID: " + issueId + "\n" +"Book ID: " + bookId + "\n" +"Issuer ID: " + issuerId + "\n" +"Issuer Name: " + issuerName + "\n" +"Contact: " + contact;
    }

}