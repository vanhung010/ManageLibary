package library.data;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConection {

    private static Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5432/library_management";
    private static final String USER = "postgres";
    private static final String PASS = "mw500wtl";

    private DBConection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                //tải driver
                Class.forName("org.postgresql.Driver");
                //kết nối
                connection = DriverManager.getConnection(URL, USER, PASS);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
