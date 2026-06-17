package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	private static final Properties properties =
            new Properties();

    static {

        try {

            Class.forName("org.postgresql.Driver");

            try (InputStream inputStream =
                         DBConnection.class
                                 .getClassLoader()
                                 .getResourceAsStream("db.properties")) {

                if (inputStream == null) {
                    throw new RuntimeException(
                            "db.properties not found");
                }

                properties.load(inputStream);
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to initialize database connection",
                    e);
        }
    }

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password")
        );
    }
}
