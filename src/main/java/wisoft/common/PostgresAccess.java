package wisoft.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresAccess {

    private static Connection conn = null;

    public static Connection setConnection() {
        final String url = "jdbc:postgresql://localhost:15432/exercise";
        final String username = "juntaek";
        final String password = "qo43mKo3Kv7zbUE";

        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, username, password);
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }

}