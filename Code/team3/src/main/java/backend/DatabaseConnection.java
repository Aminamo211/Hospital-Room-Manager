package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Creates connections to the local Hospital Room Manager database.
 */
public final class DatabaseConnection {

    // Location and name of the MySQL database
    private static final String URL =
            "jdbc:mysql://localhost:3306/hospitalroom";

    // MySQL application account
    private static final String USER = "hospital_app";

    // Prevents this utility class from being instantiated
    private DatabaseConnection() {
    }

    /**
     * Opens a database connection using the password stored
     * in the HOSPITAL_DB_PASSWORD environment variable.
     */
    public static Connection getConnection() throws SQLException {
        String password = System.getenv("HOSPITAL_DB_PASSWORD");

        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "HOSPITAL_DB_PASSWORD environment variable is not configured"
            );
        }

        return DriverManager.getConnection(URL, USER, password);
    }
}