package com.LMS.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    @Override
    public void addBook(Book b) {
        try {
            con = DBconnection.getConnection();
            String check = "SELECT*FROM book WHERE book_id=?";
            ps = con.prepareStatement(check);
            ps.setInt(1, b.getBookId());

            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Book ID already exists!!!");
                ps.close();
                con.close();
                return;
            }
            String sql = "INSERT INTO book(book_id, book_name, author, publisher, category) VALUES(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, b.getBookId());
            ps.setString(2, b.getBookName());
            ps.setString(3, b.getAuthor());
            ps.setString(4, b.getPublisher());
            ps.setString(5, b.getCategory());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Book added successfully!!!");
            } else {
                System.out.println("Book not added!!!");
            }
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteBook(int id) {
        try {
            con = DBconnection.getConnection();
            String sql = "DELETE FROM book WHERE book_id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Book deleted successfully!!!");
            } else {
                System.out.println("Book not found!!!");
            }
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Book searchBook(int id) {
        try {
            con = DBconnection.getConnection();
            String sql = "SELECT*FROM book WHERE book_id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            rs = ps.executeQuery();
            Book b = null;
            if (rs.next()) {
                b = new Book();
                b.setBookId(rs.getInt("book_id"));
                b.setBookName(rs.getString("book_name"));
                b.setAuthor(rs.getString("author"));
                b.setPublisher(rs.getString("publisher"));
                b.setCategory(rs.getString("category"));
            } else {
                System.out.println("Book not found!!!");
            }
            ps.close();
            con.close();
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> displayAllBooks() {
        List<Book> list = new ArrayList<>();
        try {
            con = DBconnection.getConnection();
            String sql = "SELECT*FROM book ORDER BY book_id ASC";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Book b = new Book();
                b.setBookId(rs.getInt("book_id"));
                b.setBookName(rs.getString("book_name"));
                b.setAuthor(rs.getString("author"));
                b.setPublisher(rs.getString("publisher"));
                b.setCategory(rs.getString("category"));
                list.add(b);

            }

            if (list.isEmpty()) {
                System.out.println("No Books Found!!!");
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