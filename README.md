# 📚 Library Management System

A **console-based Library Management System** developed using **Java, JDBC, and MySQL**. The project is designed to manage books and their issue/return records efficiently using the **POJO, DAO, and DAO Implementation** design approach.

The application provides basic library operations such as adding books, searching books, deleting books, displaying all books, issuing books, returning books, searching issued books, and displaying issued book records.

---

## 🚀 Features

### 📖 Book Management

The system provides the following operations for managing books:

* **Add Book**

  * Adds a new book to the library database.
  * Stores Book ID, Book Name, Author, Publisher, and Category.
  * Checks whether the Book ID already exists before inserting.

* **Search Book**

  * Searches for a book using its Book ID.
  * Displays complete book information if the book exists.

* **Delete Book**

  * Deletes a book from the database using its Book ID.
  * Displays an appropriate message if the book is not found.

* **Display All Books**

  * Retrieves all books from the database.
  * Displays books in ascending order of Book ID.

---

### 📕 Issue & Return Management

The project also manages books that are currently issued.

* **Issue Book**

  * Records the issue details of a book.
  * Stores Issue ID, Book ID, Issuer ID, Issuer Name, and Contact.
  * Prevents duplicate Issue IDs.
  * Checks whether a book is already issued before issuing it again.

* **Return Book**

  * Returns an issued book using its Book ID.
  * Removes the corresponding issue record from the database.

* **Search Issued Book**

  * Searches for an issued book using its Book ID.
  * Displays the issuer and issue details.

* **Display Issued Books**

  * Displays all currently issued books.
  * Records are displayed in ascending order of Issue ID.

---

## 🏗️ Project Architecture

The project follows a simple **DAO-based architecture**:

```text
User
  ↓
Main / IssueMenu
  ↓
DAO Interface
  ↓
DAO Implementation
  ↓
DB Connection
  ↓
MySQL Database
```

This structure separates the application's data model, database operations, and user interaction.

---

## 📂 Project Structure

```text
Library Management System
│
└── src
    └── com
        └── LMS
            └── test
                │
                ├── Book.java
                ├── BookDAO.java
                ├── BookDAOImpl.java
                │
                ├── IssueBook.java
                ├── IssueDAO.java
                ├── IssueDAOImpl.java
                │
                ├── DBconnection.java
                ├── Main.java
                └── IssueMenu.java
```

---

# 🔹 Implementation Details

## 1. POJO Classes

The project uses POJO (**Plain Old Java Object**) classes to represent the data.

### `Book.java`

The `Book` class represents a book in the library.

It contains:

```text
bookId
bookName
author
publisher
category
```

The class provides:

* Private data members
* Default constructor
* Parameterized constructor
* Getter methods
* Setter methods
* `toString()` method

Example:

```java
Book b = new Book();

b.setBookId(101);
b.setBookName("Java Programming");
b.setAuthor("James Gosling");
b.setPublisher("ABC Publications");
b.setCategory("Programming");
```

The `Book` object is then passed to the DAO layer for database operations.

---

## 2. IssueBook POJO

`IssueBook.java` represents an issued-book record.

It contains:

```text
issueId
bookId
issuerId
issuerName
contact
```

Like the `Book` class, it contains constructors, getters, setters, and a `toString()` method.

This POJO is used to transfer issue-related data between the menu layer and the DAO implementation.

---

# 🔹 DAO Layer

DAO stands for **Data Access Object**.

The DAO layer separates database-related operations from the main application logic.

Instead of directly writing SQL queries inside `Main.java`, the project defines operations through DAO interfaces.

This makes the code more organized and easier to maintain.

---

## 3. BookDAO Interface

`BookDAO.java` defines the operations that can be performed on books.

```java
public interface BookDAO {

    void addBook(Book b);

    void deleteBook(int id);

    Book searchBook(int id);

    List<Book> displayAllBooks();
}
```

### Responsibilities

| Method              | Purpose            |
| ------------------- | ------------------ |
| `addBook()`         | Adds a book        |
| `deleteBook()`      | Deletes a book     |
| `searchBook()`      | Searches a book    |
| `displayAllBooks()` | Displays all books |

The interface only defines **what operations are available**, not how they are implemented.

---

# 🔹 DAO Implementation

## 4. BookDAOImpl

`BookDAOImpl.java` implements the `BookDAO` interface.

```java
public class BookDAOImpl implements BookDAO
```

This class contains the actual JDBC and SQL implementation.

### Add Book

The application first checks whether the Book ID already exists:

```sql
SELECT * FROM book WHERE book_id=?
```

If the ID does not exist, the book is inserted using:

```sql
INSERT INTO book
(book_id, book_name, author, publisher, category)
VALUES (?, ?, ?, ?, ?)
```

`PreparedStatement` is used to pass values to the SQL query.

---

### Search Book

The application searches using:

```sql
SELECT * FROM book WHERE book_id=?
```

The returned database record is converted into a `Book` POJO.

For example:

```java
Book b = new Book();

b.setBookId(rs.getInt("book_id"));
b.setBookName(rs.getString("book_name"));
b.setAuthor(rs.getString("author"));
b.setPublisher(rs.getString("publisher"));
b.setCategory(rs.getString("category"));
```

---

### Delete Book

A book is deleted using:

```sql
DELETE FROM book WHERE book_id=?
```

The application checks the number of affected rows to determine whether the deletion was successful.

---

### Display Books

All books are retrieved using:

```sql
SELECT * FROM book ORDER BY book_id ASC
```

Each database row is converted into a `Book` object and stored in:

```java
List<Book>
```

The list is then returned to the menu layer.

---

# 🔹 Issue DAO Layer

## 5. IssueDAO Interface

`IssueDAO.java` defines operations related to issued books.

```java
public interface IssueDAO {

    void issueBook(IssueBook i);

    void returnBook(int bookId);

    IssueBook searchIssuedBook(int bookId);

    List<IssueBook> displayIssuedBooks();
}
```

### Responsibilities

| Method                 | Purpose                 |
| ---------------------- | ----------------------- |
| `issueBook()`          | Issues a book           |
| `returnBook()`         | Returns a book          |
| `searchIssuedBook()`   | Searches an issued book |
| `displayIssuedBooks()` | Displays issued books   |

---

# 🔹 Issue DAO Implementation

## 6. IssueDAOImpl

`IssueDAOImpl.java` implements `IssueDAO` and contains the database operations for issuing and returning books.

### Issue Book

Before issuing a book, the application performs two checks.

#### Check 1: Duplicate Issue ID

```sql
SELECT * FROM issue_book WHERE issue_id=?
```

If the Issue ID already exists, the operation is stopped.

#### Check 2: Book Already Issued

```sql
SELECT * FROM issue_book WHERE book_id=?
```

If the book is already present in the issue table, the application displays the issuer's name and prevents another issue.

If both checks pass, the record is inserted:

```sql
INSERT INTO issue_book
(issue_id, book_id, issuer_id, issuer_name, contact)
VALUES (?, ?, ?, ?, ?)
```

---

### Return Book

When a book is returned, its issue record is removed using:

```sql
DELETE FROM issue_book WHERE book_id=?
```

If a record is deleted successfully, the system displays:

```text
Book returned successfully!!!
```

---

### Search Issued Book

The system searches the issue table using:

```sql
SELECT * FROM issue_book WHERE book_id=?
```

The result is converted into an `IssueBook` POJO.

---

### Display Issued Books

All issue records are retrieved using:

```sql
SELECT * FROM issue_book ORDER BY issue_id ASC
```

The records are converted into `IssueBook` objects and stored in a list.

---

# 🔌 Database Connectivity

## 7. DBconnection.java

The `DBconnection` class is responsible for establishing the connection between Java and MySQL.

The project uses:

* **JDBC**
* **MySQL JDBC Driver**
* `DriverManager`
* `Connection`

The connection is created using:

```java
Class.forName("com.mysql.cj.jdbc.Driver");

con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/librarymanagementsystem",
    "root",
    "password"
);
```

> **Important:** Replace the database username and password with your own credentials before running the project. Do not commit real database passwords to GitHub.

---

# 🗄️ Database Design

The application works with two main tables.

## `book`

| Column      | Description           |
| ----------- | --------------------- |
| `book_id`   | Unique ID of the book |
| `book_name` | Name of the book      |
| `author`    | Author of the book    |
| `publisher` | Publisher name        |
| `category`  | Book category         |

---

## `issue_book`

| Column        | Description                       |
| ------------- | --------------------------------- |
| `issue_id`    | Unique issue record ID            |
| `book_id`     | ID of issued book                 |
| `issuer_id`   | ID of the person issuing the book |
| `issuer_name` | Name of the issuer                |
| `contact`     | Contact information               |

The `book_id` is used to identify which book has been issued.

---

# 🖥️ User Interface

The project uses a **console-based menu system**.

When the application starts, the user sees:

```text
********** LIBRARY MANAGEMENT SYSTEM **********

1. Add Book
2. Search Book
3. Delete Book
4. Display Books
5. Issue Book Menu
6. Exit
```

The user selects an option by entering the corresponding number.

---

## Issue Book Menu

The issue management section provides:

```text
********** ISSUE BOOK MENU **********

1. Issue Book
2. Return Book
3. Search Issued Book
4. Display Issued Books
5. Back
```

This makes the system simple and easy to operate through the command line.

---

# 🔄 Application Flow

### Adding a Book

```text
User enters book details
        ↓
Book POJO created
        ↓
BookDAO reference
        ↓
BookDAOImpl
        ↓
JDBC PreparedStatement
        ↓
MySQL Database
        ↓
Success / Error Message
```

### Issuing a Book

```text
User enters issue details
        ↓
IssueBook POJO created
        ↓
IssueDAO
        ↓
IssueDAOImpl
        ↓
Check Issue ID
        ↓
Check Book Availability
        ↓
Insert Issue Record
        ↓
MySQL Database
```

---

# 🛡️ Error & Validation Handling

The project includes basic validation at the database-operation level.

### Duplicate Book ID

If a Book ID already exists:

```text
Book ID already exists!!!
```

### Book Not Found

If a book does not exist:

```text
Book not found!!!
```

### Duplicate Issue ID

If an Issue ID already exists:

```text
Issue ID already exists!!!
```

### Already Issued Book

If a book is already issued:

```text
Book is already issued to [Issuer Name]
```

### Invalid Menu Choice

For an invalid menu option:

```text
Invalid Choice!!!
```

---

# 🧰 Technologies Used

| Technology            | Purpose                             |
| --------------------- | ----------------------------------- |
| **Java**              | Application development             |
| **JDBC**              | Java-database connectivity          |
| **MySQL**             | Data storage                        |
| **OOP**               | Object-oriented programming         |
| **POJO**              | Data representation                 |
| **DAO Pattern**       | Database operation abstraction      |
| **PreparedStatement** | Executing parameterized SQL queries |
| **Eclipse**           | Development environment             |

---

# 📌 Key Concepts Demonstrated

This project demonstrates practical implementation of:

* Java OOP
* Classes and Objects
* Encapsulation
* Constructors
* Getters and Setters
* Interfaces
* DAO Design Pattern
* POJO Classes
* JDBC
* MySQL Database Connectivity
* SQL CRUD Operations
* `PreparedStatement`
* `ResultSet`
* `ArrayList`
* Exception Handling
* Console-based Menu Programming

---

# ▶️ How to Run

## 1. Clone the Repository

```bash
git clone <your-repository-url>
```

## 2. Open the Project

Open the project in **Eclipse IDE** or another Java IDE.

## 3. Create MySQL Database

Create the database:

```sql
CREATE DATABASE librarymanagementsystem;
```

Then create the required tables:

```sql
CREATE TABLE book (
    book_id INT PRIMARY KEY,
    book_name VARCHAR(100),
    author VARCHAR(100),
    publisher VARCHAR(100),
    category VARCHAR(100)
);
```

```sql
CREATE TABLE issue_book (
    issue_id INT PRIMARY KEY,
    book_id INT,
    issuer_id INT,
    issuer_name VARCHAR(100),
    contact VARCHAR(20)
);
```

## 4. Configure Database Connection

Open:

```text
DBconnection.java
```

Update:

```java
DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/librarymanagementsystem",
    "root",
    "your_password"
);
```

Replace `your_password` with your local MySQL password.

## 5. Add MySQL JDBC Driver

Make sure the **MySQL Connector/J** dependency or JAR is available in the project classpath.

## 6. Run the Application

Run:

```text
Main.java
```

The Library Management System menu will appear in the console.

---

# 📸 Example Operations

### Add Book

```text
Enter Book ID: 101
Enter Book Name: Java Programming
Enter Author: James Gosling
Enter Publisher: ABC Publications
Enter Category: Programming

Book added successfully!!!
```

### Search Book

```text
Enter Book ID: 101

Book ID: 101
Book Name: Java Programming
Author: James Gosling
Publisher: ABC Publications
Category: Programming
```

### Issue Book

```text
Enter Issue ID: 1
Enter Book ID: 101
Enter Issuer ID: 501
Enter Issuer Name: Rahul
Enter Contact: 9876543210

Book issued successfully!!!
```

---

# 🎯 Project Objective

The main objective of this project is to develop a simple and efficient library management application while applying core Java and database concepts.

The project demonstrates how a Java application can communicate with a MySQL database using JDBC and how the **POJO + DAO architecture** can be used to separate data representation from database operations.

---

# 🔮 Future Enhancements

The project can be extended with additional features such as:

* 👤 Student/Member Management
* 🔐 Admin Login and Authentication
* 📅 Issue and Return Dates
* ⏰ Due-Date Tracking
* 💰 Fine Calculation for Late Returns
* 🔎 Search by Book Name or Author
* 📊 Library Reports
* 📚 Book Availability Status
* 🖥️ GUI using Java Swing or JavaFX
* 🌐 Web-based version using Spring Boot
* 🔒 Improved database security
* 📝 Logging and better exception handling

---

# 👩‍💻 Learning Outcomes

By developing this project, the following practical skills are demonstrated:

* Building a Java application from scratch
* Connecting Java applications with MySQL
* Performing database CRUD operations
* Designing POJO classes
* Implementing DAO interfaces
* Creating DAO implementation classes
* Working with JDBC APIs
* Using SQL queries with `PreparedStatement`
* Managing database results using `ResultSet`
* Applying object-oriented programming principles
* Structuring a Java project into separate responsibilities

---

# 📄 License

This project is created for **educational and learning purposes**.

Feel free to modify and extend the project for your own learning and portfolio.
