// package flyxpert.flyxpert.connections;

// import flyxpert.flyxpert.Flight.Flight;
// import flyxpert.flyxpert.Main;
// import flyxpert.flyxpert.Passenger.Passenger;
// import flyxpert.flyxpert.Payment.Payment;
// import flyxpert.flyxpert.Miscellaneous.SceneSwitcher;
// import flyxpert.flyxpert.User.User;
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.scene.control.Label;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import flyxpert.flyxpert.Payment.*;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.VBox;
// import javafx.scene.paint.Color;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
// import java.sql.Timestamp;

// public class Connection {
//     private static final String JDBC_URL = "jdbc:mysql://localhost:3306/"; // Root URL for MySQL
//     private static final String DATABASE_NAME = "airline_db"; // Database name
//     private static final String JDBC_USER = "root"; // Update to your DB username
//     private static final String JDBC_PASSWORD = "Kpokera@2004"; // Update to your DB password

//     // Establishes a connection to MySQL without specifying the database
//     private static Connection getInitialConnection() throws SQLException {
//         return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//     }

//     // Establishes a connection to the specific database
//     private static Connection getConnection() throws SQLException {
//         return DriverManager.getConnection(JDBC_URL + DATABASE_NAME, JDBC_USER, JDBC_PASSWORD);
//     }

//     // Initializes the database and tables
//     private static void initializeDatabase() {
//         try (Connection conn = getInitialConnection(); Statement stmt = conn.createStatement()) {
//             // Create database if it doesn't exist
//             String createDb = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
//             stmt.executeUpdate(createDb);

//             // Use the created database
//             try (Connection dbConn = getConnection(); Statement dbStmt = dbConn.createStatement()) {
//                 // Create Flights table
//                 String createFlightsTable = """
//                     CREATE TABLE IF NOT EXISTS Flights (
//                         flight_id INT AUTO_INCREMENT PRIMARY KEY,
//                         flight_number VARCHAR(10) NOT NULL,
//                         departure VARCHAR(50) NOT NULL,
//                         arrival VARCHAR(50) NOT NULL,
//                         departure_time TIMESTAMP NOT NULL,
//                         arrival_time TIMESTAMP NOT NULL
//                     )
//                 """;
//                 dbStmt.executeUpdate(createFlightsTable);

//                 // Create Passengers table
//                 String createPassengersTable = """
//                     CREATE TABLE IF NOT EXISTS Passengers (
//                         passenger_id INT AUTO_INCREMENT PRIMARY KEY,
//                         first_name VARCHAR(50) NOT NULL,
//                         last_name VARCHAR(50) NOT NULL,
//                         email VARCHAR(100),
//                         phone_number VARCHAR(15)
//                     )
//                 """;
//                 dbStmt.executeUpdate(createPassengersTable);

//                 // Create Bookings table
//                 String createBookingsTable = """
//                     CREATE TABLE IF NOT EXISTS Bookings (
//                         booking_id INT AUTO_INCREMENT PRIMARY KEY,
//                         flight_id INT NOT NULL,
//                         passenger_id INT NOT NULL,
//                         booking_date TIMESTAMP NOT NULL,
//                         FOREIGN KEY (flight_id) REFERENCES Flights(flight_id) ON DELETE CASCADE,
//                         FOREIGN KEY (passenger_id) REFERENCES Passengers(passenger_id) ON DELETE CASCADE
//                     )
//                 """;
//                 dbStmt.executeUpdate(createBookingsTable);
//                 System.out.println("Database and tables initialized.");
//             }
//         } catch (SQLException e) {
//             System.err.println("Error initializing database: " + e.getMessage());
//         }
//     }

//     // CRUD operations for Flights
//     public static void addFlight(String flightNumber, String departure, String arrival, Timestamp departureTime, Timestamp arrivalTime) {
//         String sql = "INSERT INTO Flights (flight_number, departure, arrival, departure_time, arrival_time) VALUES (?, ?, ?, ?, ?)";
//         try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, flightNumber);
//             stmt.setString(2, departure);
//             stmt.setString(3, arrival);
//             stmt.setTimestamp(4, departureTime);
//             stmt.setTimestamp(5, arrivalTime);
//             stmt.executeUpdate();
//             System.out.println("Flight added successfully.");
//         } catch (SQLException e) {
//             System.err.println("Error adding flight: " + e.getMessage());
//         }
//     }

//     public static void viewFlights() {
//         String sql = "SELECT * FROM Flights";
//         try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
//             ResultSet rs = stmt.executeQuery();
//             while (rs.next()) {
//                 System.out.println("Flight ID: " + rs.getInt("flight_id") + ", Flight Number: " + rs.getString("flight_number"));
//             }
//         } catch (SQLException e) {
//             System.err.println("Error viewing flights: " + e.getMessage());
//         }
//     }

//     public static void main(String[] args) {
//         try {
//             // Initialize the database and tables
//             initializeDatabase();

//             // Example Usage
//             addFlight("AA123", "New York", "London", Timestamp.valueOf("2023-11-12 10:00:00"), Timestamp.valueOf("2023-11-12 20:00:00"));
//             viewFlights();

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }