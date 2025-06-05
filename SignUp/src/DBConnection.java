import java.sql.*;

public class DBConnection {
    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:users.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}