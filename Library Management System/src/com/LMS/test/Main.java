package com.LMS.test;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BookDAO dao = new BookDAOImpl();

        int choice;
        do {
            System.out.println("\n********** LIBRARY MANAGEMENT SYSTEM **********");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Display Books");
            System.out.println("5. Issue Book Menu");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
            case 1:
                Book b = new Book();

                System.out.print("Enter Book ID: ");
                b.setBookId(sc.nextInt());

                sc.nextLine();

                System.out.print("Enter Book Name: ");
                b.setBookName(sc.nextLine());

                System.out.print("Enter Author: ");
                b.setAuthor(sc.nextLine());

                System.out.print("Enter Publisher: ");
                b.setPublisher(sc.nextLine());

                System.out.print("Enter Category: ");
                b.setCategory(sc.nextLine());

                dao.addBook(b);
                break;

            case 2:
                System.out.print("Enter Book ID: ");
                int id = sc.nextInt();

                Book book = dao.searchBook(id);

                if (book != null) {
                    System.out.println(book);
                }
                break;

            case 3:
                System.out.print("Enter Book ID: ");
                int deleteId = sc.nextInt();

                dao.deleteBook(deleteId);
                break;

            case 4:
                List<Book> list = dao.displayAllBooks();

                for (Book b1 : list) {
                    System.out.println(b1);
                }
                break;

            case 5:
                IssueMenu im = new IssueMenu();
                im.issueMenu(sc);

                break;

            case 6:
                System.out.println("Thank you for using Library Management System!");
                break;

            default:
                System.out.println("Invalid Choice!!!");
                break;
            }

        } while (choice != 6);

        sc.close();
    }
}