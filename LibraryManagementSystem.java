package com.mbl.lms;
import java.sql.*;

public class LibraryManagementSystem {
	
	 private Connection connection;

	    public LibraryManagementSystem() {
	        try {
	            // Establishing the database connection
	            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "username", "password");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // Add a book to the library
	    public void addBook(Book book) {
	        try {
	            String query = "INSERT INTO books (title, author, publication, availability) VALUES (?, ?, ?, ?)";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, book.getTitle());
	            statement.setString(2, book.getAuthor());
	            statement.setString(3, book.getPublication());
	            statement.setBoolean(4, true); // Assuming all new books are available

	            statement.executeUpdate();
	            System.out.println("Book added successfully.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // Update book availability status
	    public void updateBookAvailability(int bookId, boolean availability) {
	        try {
	            String query = "UPDATE books SET availability = ? WHERE id = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setBoolean(1, availability);
	            statement.setInt(2, bookId);

	            statement.executeUpdate();
	            System.out.println("Book availability updated successfully.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // Add a borrower
	    public void addBorrower(Borrower borrower) {
	        try {
	            String query = "INSERT INTO borrowers (name, contact) VALUES (?, ?)";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setString(1, borrower.getName());
	            statement.setString(2, borrower.getContact());

	            statement.executeUpdate();
	            System.out.println("Borrower added successfully.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // Generate a report of all available books
	    public void generateAvailableBooksReport() {
	        try {
	            String query = "SELECT * FROM books WHERE availability = true";
	            Statement statement = connection.createStatement();
	            ResultSet resultSet = statement.executeQuery(query);

	            System.out.println("Available Books:");
	            while (resultSet.next()) {
	                int bookId = resultSet.getInt("id");
	                String title = resultSet.getString("title");
	                String author = resultSet.getString("author");
	                String publication = resultSet.getString("publication");

	                System.out.println("Book ID: " + bookId);
	                System.out.println("Title: " + title);
	                System.out.println("Author: " + author);
	                System.out.println("Publication: " + publication);
	                System.out.println();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    // Close the database connection
	    public void closeConnection() {
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	                System.out.println("Connection closed.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 LibraryManagementSystem librarySystem = new LibraryManagementSystem();

	        // Adding a book
	        Book book = new Book("The Catcher in the Rye", "J.D. Salinger", "Little, Brown and Company");
	        librarySystem.addBook(book);

	        // Updating book availability
	        librarySystem.updateBookAvailability(1, false);

	        // Adding a borrower
	        Borrower borrower = new Borrower("John Doe", "johndoe@example.com");
	        librarySystem.addBorrower(borrower);

	        // Generating the available books report
	        librarySystem.generateAvailableBooksReport();

	        // Closing the database connection
	        librarySystem.closeConnection();

	}

}
