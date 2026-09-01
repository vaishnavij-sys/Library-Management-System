package com.LMS.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IssueDAOImpl implements IssueDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public void issueBook(IssueBook i) {
        try {
            con = DBconnection.getConnection();
            String checkIssue = "SELECT*FROM issue_book WHERE issue_id=?";
            ps = con.prepareStatement(checkIssue);
            ps.setInt(1, i.getIssueId());

            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Issue ID already exists!!!");
                ps.close();
                con.close();
                return;
            }
            String checkBook = "SELECT*FROM issue_book WHERE book_id=?";
            ps = con.prepareStatement(checkBook);
            ps.setInt(1, i.getBookId());

            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Book is already issued to " + rs.getString("issuer_name"));
                ps.close();
                con.close();
                return;
            }
            String sql = "INSERT INTO issue_book(issue_id, book_id, issuer_id, issuer_name, contact) VALUES(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, i.getIssueId());
            ps.setInt(2, i.getBookId());
            ps.setInt(3, i.getIssuerId());
            ps.setString(4, i.getIssuerName());
            ps.setString(5, i.getContact());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Book issued successfully!!!");
            } else {
                System.out.println("Book not issued!!!");
            }
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void returnBook(int bookId) {
        try {
            con = DBconnection.getConnection();
            String sql = "DELETE FROM issue_book WHERE book_id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, bookId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Book returned successfully!!!");
            } else {
                System.out.println("Book was not issued!!!");
            }
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IssueBook searchIssuedBook(int bookId) {
        try {
            con = DBconnection.getConnection();
            String sql = "SELECT*FROM issue_book WHERE book_id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, bookId);

            rs = ps.executeQuery();
            IssueBook i = null;
            if (rs.next()) {
                i = new IssueBook();
                i.setIssueId(rs.getInt("issue_id"));
                i.setBookId(rs.getInt("book_id"));
                i.setIssuerId(rs.getInt("issuer_id"));
                i.setIssuerName(rs.getString("issuer_name"));
                i.setContact(rs.getString("contact"));
            } else {
                System.out.println("No issued record found!!!");
            }
            ps.close();
            con.close();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<IssueBook> displayIssuedBooks() {
        List<IssueBook> list = new ArrayList<>();
        try {
            con = DBconnection.getConnection();
            String sql = "SELECT * FROM issue_book ORDER BY issue_id ASC";
            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                IssueBook i = new IssueBook();
                i.setIssueId(rs.getInt("issue_id"));
                i.setBookId(rs.getInt("book_id"));
                i.setIssuerId(rs.getInt("issuer_id"));
                i.setIssuerName(rs.getString("issuer_name"));
                i.setContact(rs.getString("contact"));
                list.add(i);
            }

            if (list.isEmpty()) {
                System.out.println("No issued books found!!!");
            }
            ps.close();
            con.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}