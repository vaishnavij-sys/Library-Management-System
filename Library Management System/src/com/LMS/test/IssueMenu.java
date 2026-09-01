package com.LMS.test;

import java.util.List;
import java.util.Scanner;

public class IssueMenu {
    IssueDAO dao = new IssueDAOImpl();
    public void issueMenu(Scanner sc) {

        int choice;
        do {

            System.out.println("\n********** ISSUE BOOK MENU **********");
            System.out.println("1. Issue Book");
            System.out.println("2. Return Book");
            System.out.println("3. Search Issued Book");
            System.out.println("4. Display Issued Books");
            System.out.println("5. Back");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
            case 1:
                IssueBook i = new IssueBook();

                System.out.print("Enter Issue ID: ");
                i.setIssueId(sc.nextInt());

                System.out.print("Enter Book ID: ");
                i.setBookId(sc.nextInt());

                System.out.print("Enter Issuer ID: ");
                i.setIssuerId(sc.nextInt());

                sc.nextLine();

                System.out.print("Enter Issuer Name: ");
                i.setIssuerName(sc.nextLine());

                System.out.print("Enter Contact: ");
                i.setContact(sc.nextLine());

                dao.issueBook(i);
                break;

            case 2:
                System.out.print("Enter Book ID: ");
                int bookId = sc.nextInt();

                dao.returnBook(bookId);
                break;

            case 3:
                System.out.print("Enter Book ID: ");
                int searchId = sc.nextInt();

                IssueBook issue = dao.searchIssuedBook(searchId);

                if (issue != null) {
                    System.out.println(issue);
                }
                break;

            case 4:
                List<IssueBook> list = dao.displayIssuedBooks();

                for (IssueBook i1 : list) {
                    System.out.println(i1);
                }
                break;

            case 5:
                break;

            default:
                System.out.println("Invalid Choice!!!");
                break;
            }

        } while (choice != 5);
    }
}